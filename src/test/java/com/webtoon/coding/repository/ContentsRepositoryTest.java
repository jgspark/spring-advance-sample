package com.webtoon.coding.repository;

import com.webtoon.coding.config.JPAConfiguration;
import com.webtoon.coding.domain.content.Policy;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.*;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.dto.SelectContentsStoreDTO;
import com.webtoon.coding.dto.TopContents;
import com.webtoon.coding.repository.comment.CommentRepository;
import com.webtoon.coding.repository.contents.ContentsRepository;
import com.webtoon.coding.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JPAConfiguration.class)
class ContentsRepositoryTest {

  @Autowired private UserRepository userRepository;
  @Autowired private ContentsRepository contentsRepository;
  @Autowired private CommentRepository commentRepository;

  @BeforeEach
  void init() {}

  @Test
  @DisplayName("컨텐츠 저장 로직 테스트 케이스")
  void save() {

    Contents mock = ContentsMock.createdMock();

    Contents entity = contentsRepository.save(mock);

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);

    Assertions.assertEquals(entity.getId(), mock.getId());
    Assertions.assertEquals(entity.getName(), mock.getName());
    Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
    Assertions.assertEquals(entity.getType(), mock.getType());
    Assertions.assertEquals(entity.getCoin(), "0");
    Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
  }

  @Nested
  class Select {

    private User user;

    private Contents mock;

    private Comment comment;

    @BeforeEach
    void init() {
      user = userRepository.saveAndFlush(UserMock.createdMock());
      mock = contentsRepository.saveAndFlush(ContentsMock.createdMock());
      comment = commentRepository.save(CommentMock.createdMock(user, mock));
      commentRepository.flush();
    }

    @Test
    @DisplayName("좋아요 top 3 로직 테스트 케이스")
    void findTopByLimitAndType_goodComment() {

      List<TopContents> entities = contentsRepository.findTopByLimitAndType(3, Evaluation.GOOD);

      Assertions.assertEquals(entities.size(), 1);

      TopContents entity = entities.get(0);

      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), mock.getCoin());
      Assertions.assertEquals(
          DateMock.changedFormatDate(entity.getOpenDate()),
          DateMock.changedFormatDate(mock.getOpenDate()));
      Assertions.assertEquals(entity.getSum(), 1);
    }

    @Test
    @DisplayName("싫어요 top 3 로직 테스트 케이스")
    void findTopByLimitAndType_badComment() {

      List<TopContents> entities = contentsRepository.findTopByLimitAndType(3, Evaluation.BAD);

      Assertions.assertEquals(entities.size(), 1);

      TopContents entity = entities.get(0);

      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), mock.getCoin());
      Assertions.assertEquals(
          DateMock.changedFormatDate(entity.getOpenDate()),
          DateMock.changedFormatDate(mock.getOpenDate()));
      Assertions.assertEquals(entity.getSum(), 0);
    }

    @Test
    @DisplayName("타입별 조회 로직 free type")
    void findByType_freeType() {

      Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

      SelectContentsStoreDTO dto = DtoMock.getSelectContentsStoreDTO();

      Page<ContentsInfo> entities =
          contentsRepository.findByType(
              dto.getPageRequest(), Policy.FREE, ContentsInfo.class);

      List<ContentsInfo> mockContent = mocks.getContent();

      List<ContentsInfo> entitiesContent = entities.getContent();

      Assertions.assertEquals(entitiesContent.size(), mockContent.size());

      ContentsInfo entity = entitiesContent.get(0);

      ContentsInfo mock = mockContent.get(0);

      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), "0");
      Assertions.assertEquals(
          DateMock.changedFormatDate(entity.getOpenDate()),
          DateMock.changedFormatDate(mock.getOpenDate()));
    }

    @Test
    @DisplayName("전체 조회 로직 테스트 케이스")
    void findAllProjectedBy() {
      Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

      SelectContentsStoreDTO dto = DtoMock.getSelectContentsStoreDTO();

      Page<ContentsInfo> entities =
          contentsRepository.findAllProjectedBy(dto.getPageRequest(), ContentsInfo.class);

      List<ContentsInfo> mockContent = mocks.getContent();

      List<ContentsInfo> entitiesContent = entities.getContent();

      Assertions.assertEquals(entitiesContent.size(), mockContent.size());

      ContentsInfo entity = entitiesContent.get(0);

      ContentsInfo mock = mockContent.get(0);

      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), "0");
      Assertions.assertEquals(
          DateMock.changedFormatDate(entity.getOpenDate()),
          DateMock.changedFormatDate(mock.getOpenDate()));
    }

    @Test
    @DisplayName("하나의 컨텐츠 조회")
    void findById() {

      Optional<ContentsInfo> entityOptional =
          contentsRepository.findById(mock.getId(), ContentsInfo.class);

      Assertions.assertTrue(entityOptional.isPresent());

      ContentsInfo entity = entityOptional.get();

      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), "0");
      Assertions.assertEquals(
          DateMock.changedFormatDate(entity.getOpenDate()),
          DateMock.changedFormatDate(mock.getOpenDate()));
    }

    @AfterEach
    void clear() {
      userRepository.delete(user);
      contentsRepository.delete(mock);
      commentRepository.delete(comment);
    }
  }

  @Nested
  class Update {

    private Contents mock;

    @BeforeEach
    void init() {
      mock = contentsRepository.save(ContentsMock.createdMock());
      contentsRepository.flush();
    }

    @Test
    @DisplayName("무료 타입 컨텐츠 업데이트")
    void updateFreeType() {

      Optional<Contents> entityOptional = contentsRepository.findById(mock.getId());

      Assertions.assertTrue(entityOptional.isPresent());

      Contents entity = entityOptional.get();

      entity.changedFreeType();

      contentsRepository.flush();

      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), "0");
      Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
    }

    @Test
    @DisplayName("유료 타입 컨텐츠 업데이트")
    void updatedPagar() {

      final String mockCoin = "100";

      Optional<Contents> entityOptional = contentsRepository.findById(mock.getId());

      Assertions.assertTrue(entityOptional.isPresent());

      Contents entity = entityOptional.get();

      entity.changedPagar();

      entity.setCoin(mockCoin);

      contentsRepository.flush();

      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), Policy.PAGAR);
      Assertions.assertEquals(entity.getCoin(), mockCoin);
      Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
    }

    @AfterEach
    void clear() {
      contentsRepository.delete(mock);
    }
  }
}
