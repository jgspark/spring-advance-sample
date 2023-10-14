package com.webtoon.coding.service;

import com.webtoon.coding.domain.user.UserRemover;
import com.webtoon.coding.service.user.UserService;
import com.webtoon.coding.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRemover userRemover;

    @BeforeEach
    void init() {
        this.userService = new UserServiceImpl(userRemover);
    }

    @Test
    @DisplayName("삭제 로직 테스트 케이스")
    void removeUser() {

        doNothing().when(userRemover).remove(any());

        userService.removeUser(1L);

        verify(userRemover, times(1)).remove(any());

    }

}
