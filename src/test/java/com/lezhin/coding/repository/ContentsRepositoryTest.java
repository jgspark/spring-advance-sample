package com.lezhin.coding.repository;

import com.lezhin.coding.config.JPAConfiguration;
import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.mock.CommentMock;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.mock.UserMock;
import com.lezhin.coding.service.dto.TopContents;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
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

      List<TopContents> entities = contentsRepository.findTopByLimitAndType(3, EvaluationType.GOOD);

      Assertions.assertEquals(entities.size(), 1);

      TopContents entity = entities.get(0);

      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), mock.getCoin());
      Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
      Assertions.assertEquals(entity.getSum(), 1);
    }

    @Test
    @DisplayName("싫어요 top 3 로직 테스트 케이스")
    void findTopByLimitAndType_badComment() {

      List<TopContents> entities = contentsRepository.findTopByLimitAndType(3, EvaluationType.BAD);

      Assertions.assertEquals(entities.size(), 1);

      TopContents entity = entities.get(0);

      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), mock.getCoin());
      Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
      Assertions.assertEquals(entity.getSum(), 0);
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

      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
      Assertions.assertEquals(entity.getType(), mock.getType());
      Assertions.assertEquals(entity.getCoin(), "0");
      Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
    }

    @Test
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
      Assertions.assertEquals(entity.getType(), ContentsType.PAGAR);
      Assertions.assertEquals(entity.getCoin(), mockCoin);
      Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
    }
  }
}
