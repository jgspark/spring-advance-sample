package com.lezhin.coding.repository;

import com.lezhin.coding.config.JPAConfiguration;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.History;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.mock.HistoryMock;
import com.lezhin.coding.mock.UserMock;
import com.lezhin.coding.service.dto.HistoryInfo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JPAConfiguration.class)
class HistoryRepositoryTest {

  @Autowired private HistoryRepository historyRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ContentsRepository contentsRepository;

  private User user;

  private Contents contents;

  @BeforeEach
  void init() {
    user = userRepository.saveAndFlush(UserMock.createdMock());
    contents = contentsRepository.saveAndFlush(ContentsMock.createdMock());
  }

  @Test
  @DisplayName("히스토리 저장 로직 테스트 케이스")
  void save() {

    History mock = HistoryMock.createdMock(user, contents);

    History entity = historyRepository.save(mock);

    Assertions.assertEquals(entity.getUser().getId(), mock.getUser().getId());
    Assertions.assertEquals(entity.getContents().getId(), entity.getContents().getId());
    Assertions.assertNotNull(entity.getCreatedDate());
  }

  @Nested
  class Select {

    private History history;

    @BeforeEach
    void init() {
      history = historyRepository.save(HistoryMock.createdMock(user, contents));
      historyRepository.flush();
    }

    @Test
    @DisplayName("조회 이력 API 테스트 케이스")
    void findAllProjectedBy() {

      Page<HistoryInfo> mocks = HistoryMock.createdPageList(user, contents);

      PageRequest pageable = PageRequest.of(0, 10);

      Page<HistoryInfo> entities =
          historyRepository.findAllProjectedBy(pageable, HistoryInfo.class);

      List<HistoryInfo> mockContent = mocks.getContent();

      List<HistoryInfo> entitiesContent = entities.getContent();

      Assertions.assertEquals(entitiesContent.size(), mockContent.size());

      HistoryInfo entity = entitiesContent.get(0);

      HistoryInfo mock = mockContent.get(0);

      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getUserName(), mock.getUserName());
      Assertions.assertEquals(entity.getContentsName(), mock.getContentsName());
      Assertions.assertEquals(entity.getContentsType(), mock.getContentsType());
      Assertions.assertNotNull(entity.getCreatedDate());
    }
  }
}
