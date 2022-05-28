package com.lezhin.coding.repository;

import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.mock.CommentMock;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.mock.UserMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ContentsRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Autowired private ContentsRepository contentsRepository;
  @Autowired private CommentRepository commentRepository;

  private User user;

  private Contents contents;

  @BeforeEach
  void init() {
    user = userRepository.saveAndFlush(UserMock.createdMock());
    contents = contentsRepository.saveAndFlush(ContentsMock.createdMock());
  }

  @Test
  @Disabled("단일 작품 평가 저장")
  void save() {

    Comment mock = CommentMock.createdMock(user, contents);

    Comment entity = commentRepository.save(mock);

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);

    Assertions.assertEquals(entity.getId().getUserId(), mock.getUser().getId());
    Assertions.assertEquals(entity.getId().getContentsId(), mock.getId().getContentsId());
    Assertions.assertEquals(entity.getComment(), mock.getComment());
  }

  @Test
  @DisplayName("유니크 키 테스트 케이스")
  void save_overlap() {

    Comment mock1 = CommentMock.createdMock(user, contents);

    commentRepository.save(mock1);

    commentRepository.flush();

    Comment mock2 = CommentMock.createdMock(user, contents);

    commentRepository.saveAndFlush(mock2);
  }
}
