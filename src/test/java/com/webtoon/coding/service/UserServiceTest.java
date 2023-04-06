package com.webtoon.coding.service;

import com.webtoon.coding.repository.comment.CommentRepository;
import com.webtoon.coding.repository.history.HistoryRepository;
import com.webtoon.coding.repository.user.UserRepository;
import com.webtoon.coding.service.user.UserService;
import com.webtoon.coding.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

  private UserService userService;

  @Mock private UserRepository userRepository;

  @Mock private HistoryRepository historyRepository;

  @Mock private CommentRepository commentRepository;

  @BeforeEach
  void init() {
    this.userService = new UserServiceImpl(commentRepository, historyRepository, userRepository);
  }

  @Test
  @DisplayName("삭제 로직 테스트 케이스")
  void removeUser() {

    BDDMockito.willDoNothing().given(commentRepository).deleteById_UserId(any());
    BDDMockito.willDoNothing().given(historyRepository).deleteByUser_Id(any());
    BDDMockito.willDoNothing().given(userRepository).deleteById(any());

    userService.removeUser(1L);

    BDDMockito.then(commentRepository).should().deleteById_UserId(any());

    BDDMockito.then(historyRepository).should().deleteByUser_Id(any());

    BDDMockito.then(userRepository).should().deleteById(any());
  }
}
