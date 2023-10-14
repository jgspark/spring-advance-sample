package com.webtoon.coding.domain.user;

import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.core.exception.NoDataException;
import com.webtoon.coding.domain.common.Reader;
import com.webtoon.coding.infra.repository.user.UserRepository;
import com.webtoon.coding.mock.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("유저 리더 클레스에서")
@ExtendWith(MockitoExtension.class)
class UserReaderTest {

    private Reader<User> userReader;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userReader = new UserReader(userRepository);
    }

    @Nested
    @DisplayName("단일 조회 메소드에서는")
    public class GetMethod {

        @Test
        @DisplayName("성공을 하게 된다.")
        public void testGetSuccess() {

            Optional<User> userOptional = Optional.of(UserMock.createdMock());

            final Long userId = 1L;

            when(userRepository.findById(any())).thenReturn(userOptional);

            User entity = userReader.get(userId);

            User mock = userOptional.get();

            verify(userRepository, times(1)).findById(any());

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getUserName(), mock.getUserName());
            assertEquals(entity.getUserEmail(), mock.getUserEmail());
            assertEquals(entity.getGender(), mock.getGender());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getRegisterDate(), mock.getRegisterDate());
        }

        @Test
        @DisplayName("실패를 하게 된다.")
        public void testGetFail() {

            final Long userId = 1L;

            when(userRepository.findById(any())).thenReturn(Optional.empty());

            NoDataException e = assertThrows(NoDataException.class, () -> userReader.get(userId));

            verify(userRepository, times(1)).findById(any());

            assertEquals(e.getMsgType(), MsgType.NoUserData);
            assertEquals(e.getMessage(), MsgType.NoUserData.getMessage());
        }

    }

}
