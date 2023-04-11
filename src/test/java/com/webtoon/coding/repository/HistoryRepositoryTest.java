package com.webtoon.coding.repository;

import com.webtoon.coding.config.JPAConfiguration;
import com.webtoon.coding.domain.contents.Adult;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.HistoryMock;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.dto.HistoryInfo;
import com.webtoon.coding.dto.HistoryUser;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import com.webtoon.coding.util.DateUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JPAConfiguration.class)
class HistoryRepositoryTest {

  @Autowired private HistoryRepository historyRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ContentsRepository contentsRepository;

  private User user;

  private Contents contents;

  private History entity;

  @BeforeEach
  void init() {
    user = userRepository.saveAndFlush(UserMock.createdMock());
    contents = contentsRepository.saveAndFlush(ContentsMock.createdMock());
  }

  @Test
  @DisplayName("히스토리 저장 로직 테스트 케이스")
  void save() {

    History mock = HistoryMock.createdMock(user, contents);

    entity = historyRepository.save(mock);

    Assertions.assertEquals(entity.getUser().getId(), mock.getUser().getId());
    Assertions.assertEquals(entity.getContents().getId(), entity.getContents().getId());
    Assertions.assertNotNull(entity.getCreatedDate());
  }

  @Nested
  class Select {

    private List<History> mocks;

    @BeforeEach
    void init() {

      mocks =
          historyRepository.saveAll(
              List.of(
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents)));

      historyRepository.flush();
    }

    @Test
    @DisplayName("조회 이력 API 테스트 케이스")
    void findAllProjectedBy() {

      PageRequest pageable = PageRequest.of(0, 10);

      Page<HistoryInfo> entities =
          historyRepository.findAllProjectedBy(pageable, HistoryInfo.class);

      Assertions.assertFalse(entities.isEmpty());

      List<HistoryInfo> entitiesContent = entities.getContent();

      entitiesContent.forEach(
          entity -> {
            History mock =
                mocks.stream()
                    .filter(m -> m.getId().equals(entity.getId()))
                    .findFirst()
                    .orElseThrow();

            Assertions.assertEquals(entity.getUserName(), mock.getUser().getUserName());
            Assertions.assertEquals(entity.getContentsName(), mock.getContents().getName());
            Assertions.assertEquals(entity.getContentsType(), mock.getContents().getType());
          });
    }
  }

  @Nested
  class SelectHistoryUser {

    private List<History> mocks;

    @BeforeEach
    void init() {

      mocks =
          historyRepository.saveAll(
              List.of(
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents),
                  HistoryMock.createdMock(user, contents)));

      historyRepository.flush();
    }

    @Test
    @DisplayName("최근 1주일 등록된 사용자 중 성인 조회 api 테스트 케이스")
    void findByCreatedDateBetweenAndContents_AdultType() {

      Date startDate = DateUtil.minus(new Date(), -7);

      Date endDate = DateUtil.plus(new Date(), 1);

      PageRequest pageable = PageRequest.of(0, 10);

      Page<HistoryUser> entities =
          historyRepository.findByCreatedDateBetweenAndContents_AdultType(
              pageable, startDate, endDate, Adult.ADULT, 3L);

      Assertions.assertFalse(entities.isEmpty());

      List<HistoryUser> entitiesContent = entities.getContent();

      entitiesContent.forEach(
          entity -> {
            History mock =
                mocks.stream()
                    .filter(m -> m.getId().equals(entity.getId()))
                    .findFirst()
                    .orElseThrow();

            Assertions.assertEquals(entity.getUserName(), mock.getUser().getUserName());
            Assertions.assertEquals(entity.getUserEmail(), mock.getUser().getUserEmail());
            Assertions.assertEquals(entity.getGender(), mock.getUser().getGender());
            Assertions.assertEquals(entity.getType(), mock.getUser().getType());
          });
    }
  }

  @Nested
  class Delete {

    private History mock;

    @Test
    void init() {
      mock = HistoryMock.createdMock(user, contents);
    }

    @Test
    @DisplayName("히스토리 유저별 삭제")
    void deleteByUser_Id() {
      historyRepository.deleteByUser_Id(user.getId());
      historyRepository.flush();
    }
  }
}
