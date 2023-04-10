package com.webtoon.coding.service;

import com.webtoon.coding.exception.NoDataException;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.CommentMock;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.infra.repository.comment.CommentRepository;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import com.webtoon.coding.service.comment.CommentService;
import com.webtoon.coding.service.comment.CommentServiceImpl;
import com.webtoon.coding.dto.request.ContentsCommentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

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
  @DisplayName("댓글 저장 테스트 케이스")
  void createdComment_success() {

    Optional<User> userOptional = Optional.of(UserMock.createdMock());

    Optional<Contents> contentsOptional = Optional.of(ContentsMock.createdMock());

    Comment mock = CommentMock.createdMock(userOptional.get(), contentsOptional.get());

    BDDMockito.given(userRepository.findById(any())).willReturn(userOptional);

    BDDMockito.given(contentsRepository.findById(any())).willReturn(contentsOptional);

    BDDMockito.given(commentRepository.save(any())).willReturn(mock);

    ContentsCommentRequest dto = CommentMock.createdStoreDTO();

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

  @Test
  @DisplayName("no user data exception 테스트 케이스")
  void createdComment_NoUserData() {

    Optional<User> userOptional = Optional.ofNullable(null);

    Optional<Contents> contentsOptional = Optional.of(ContentsMock.createdMock());

    Comment mock = CommentMock.createdMock(User.builder().build(), contentsOptional.get());

    BDDMockito.given(userRepository.findById(any())).willReturn(userOptional);

    BDDMockito.given(contentsRepository.findById(any())).willReturn(contentsOptional);

    BDDMockito.given(commentRepository.save(any())).willReturn(mock);

    ContentsCommentRequest dto = CommentMock.createdStoreDTO();

    org.junit.jupiter.api.Assertions.assertThrows(
        NoDataException.class, () -> commentService.createdComment(dto));
  }

  @Test
  @DisplayName("no contents data exception 테스트 케이스")
  void createdComment_NoContentsData() {

    Optional<User> userOptional = Optional.of(UserMock.createdMock());

    Optional<Contents> contentsOptional = Optional.ofNullable(null);

    Comment mock = CommentMock.createdMock(User.builder().build(), Contents.builder().build());

    BDDMockito.given(userRepository.findById(any())).willReturn(userOptional);

    BDDMockito.given(contentsRepository.findById(any())).willReturn(contentsOptional);

    BDDMockito.given(commentRepository.save(any())).willReturn(mock);

    ContentsCommentRequest dto = CommentMock.createdStoreDTO();

    org.junit.jupiter.api.Assertions.assertThrows(
        NoDataException.class, () -> commentService.createdComment(dto));
  }
}
