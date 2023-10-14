package com.webtoon.coding.domain.contents;

import com.webtoon.coding.core.exception.DomainException;
import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.domain.common.Verifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("컨텐츠 검증 클래스에서")
class ContentsVerifierTest {

    private Verifier<Contents> verifier;

    @BeforeEach
    public void init() {
        verifier = new ContentsVerifier();
    }

    @Nested
    @DisplayName("검증하는 메소드는")
    public class Verify {

        @ParameterizedTest(name = "코인값은 {0} 과 타입은 {1} 으로 정상적으로 동작한다.")
        @MethodSource("com.webtoon.coding.domain.contents.ContentsVerifierTest#testVerifySuccessArgs")
        public void testVerifySuccess(String coin, Policy type) {

            Contents contents = Contents.builder().coin(coin).type(type).build();

            assertDoesNotThrow(() -> verifier.verify(contents));
        }

        @ParameterizedTest(name = "코인값은 {0} 과 타입은 {1} 으로 실패한다.")
        @MethodSource("com.webtoon.coding.domain.contents.ContentsVerifierTest#testVerifyFailArgs")
        public void testVerifyFail(String coin, Policy type) {

            Contents contents = Contents.builder().coin(coin).type(type).build();

            DomainException e = assertThrows(DomainException.class, () -> verifier.verify(contents));

            assertEquals(e.getMsgType(), MsgType.CoinDataException);
            assertEquals(e.getMessage(), MsgType.CoinDataException.getMessage());
        }

    }

    public static Stream<Arguments> testVerifySuccessArgs() {
        return Stream.of(Arguments.of("100", Policy.PAGAR), Arguments.of("200", Policy.PAGAR),
                Arguments.of("300", Policy.PAGAR), Arguments.of("400", Policy.PAGAR), Arguments.of("500", Policy.PAGAR),
                Arguments.of("0", Policy.FREE));
    }

    public static Stream<Arguments> testVerifyFailArgs() {
        return Stream.of(Arguments.of(null, null), Arguments.of("100", Policy.FREE), Arguments.of("99", Policy.PAGAR),
                Arguments.of("501", Policy.PAGAR));
    }

}
