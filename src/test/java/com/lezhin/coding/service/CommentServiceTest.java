package com.lezhin.coding.service;

import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.mock.CommentMock;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.mock.UserMock;
import com.lezhin.coding.repository.CommentRepository;
import com.lezhin.coding.repository.ContentsRepository;
import com.lezhin.coding.repository.UserRepository;
import com.lezhin.coding.service.dto.CommentStoreDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommentServiceTest {

  private CommentService commentService;

  @Mock private CommentRepository commentRepository;

  @Mock private ContentsRepository contentsRepository;

  @Mock private UserRepository userRepository;

  @BeforeEach
  void init() {

    this.commentService =
        new CommentServiceImpl(commentRepository, userRepository, contentsRepository);
  }

  @Test
  void createdComment() {

    Optional<User> userOptional = Optional.of(UserMock.createdMock());

    Optional<Contents> contentsOptional = Optional.of(ContentsMock.createdMock());

    Comment mock = CommentMock.createdMock(userOptional.get(), contentsOptional.get());

    BDDMockito.given(userRepository.findById(any())).willReturn(userOptional);

    BDDMockito.given(contentsRepository.findById(any())).willReturn(contentsOptional);

    BDDMockito.given(commentRepository.save(any())).willReturn(mock);

    CommentStoreDTO dto = CommentMock.createdStoreDTO();

    Comment entity = commentService.createdComment(dto);

    BDDMockito.then(userRepository).should().findById(any());

    BDDMockito.then(contentsRepository).should().findById(any());

    BDDMockito.then(commentRepository).should().save(any());

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);

    org.junit.jupiter.api.Assertions.assertEquals(
        entity.getId().getUserId(), mock.getUser().getId());
    org.junit.jupiter.api.Assertions.assertEquals(
        entity.getId().getContentsId(), mock.getId().getContentsId());
    org.junit.jupiter.api.Assertions.assertEquals(entity.getComment(), mock.getComment());
  }
}
