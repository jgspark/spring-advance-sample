package com.webtoon.coding.dto.response;

import com.webtoon.coding.core.exception.MsgType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("에러 응답 클래스에서")
class ErrorResponseTest {

    @Test
    @DisplayName("생성 메소드는 성공 한다.")
    public void testOfSuccess() {

        MsgType type = MsgType.ServerError;

        ErrorResponse response = ErrorResponse.of(type.getCode(), type.getMessage());

        assertEquals(type.getCode(), response.getCode());
        assertEquals(type.getMessage(), response.getMessage());
    }

    @ParameterizedTest(name = "생성 메소드는 코드가 {0} 이거면서, 메세지가 {1} 이면 실패 한다.")
    @MethodSource("getArgs")
    public void testOfFail(String code, String message) {
        assertThrows(IllegalArgumentException.class, () -> ErrorResponse.of(code, message));
    }

    private static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, MsgType.ServerError.getMessage()),
                Arguments.of(MsgType.ServerError.getCode(), null)
        );
    }
}
