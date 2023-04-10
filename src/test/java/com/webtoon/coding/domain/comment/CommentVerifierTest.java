package com.webtoon.coding.domain.comment;

import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.exception.DomainException;
import com.webtoon.coding.exception.MsgType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("체크 클레스에서")
class CommentVerifierTest {

    private CommentVerifier commentVerifier;

    @BeforeEach
    public void init() {
        commentVerifier = new CommentVerifier();
    }

    @Nested
    @DisplayName("체크 메소드는")
    public class Verify {

        @ParameterizedTest(name = "댓글은 {0} 이기 때문에 실패를 한다.")
        @MethodSource("com.webtoon.coding.domain.comment.CommentVerifierTest#verifyFailByCommentCaseArgs")
        public void testVerifyFailByCommentCase(String comment) {

            ContentsComment contentsComment = ContentsComment.of(
                    comment,
                    Evaluation.BAD,
                    User.builder().build(),
                    Contents.builder().build()
            );

            DomainException e = assertThrows(DomainException.class, () -> commentVerifier.verify(contentsComment));

            assertEquals(e.getMsgType(), MsgType.CommentDataException);
            assertEquals(e.getMessage(), MsgType.CommentDataException.getMessage());
        }

        @ParameterizedTest(name = "평가는 {0} 이기 때문에 실패를 하게 된다.")
        @MethodSource("com.webtoon.coding.domain.comment.CommentVerifierTest#verifyFailByTypeCaseArgs")
        public void testVerifyFailByTypeCase(Evaluation type) {

            ContentsComment contentsComment = ContentsComment.of(
                    "하하핳",
                    type,
                    User.builder().build(),
                    Contents.builder().build()
            );

            DomainException e = assertThrows(DomainException.class, () -> commentVerifier.verify(contentsComment));

            assertEquals(e.getMsgType(), MsgType.EvaluationDataException);
            assertEquals(e.getMessage(), MsgType.EvaluationDataException.getMessage());
        }

        @Test
        @DisplayName("성공을 하게 된다.")
        public void testVerifySuccess() {

            ContentsComment comment = ContentsComment.of(
                    "hello",
                    Evaluation.GOOD,
                    User.builder().build(),
                    Contents.builder().build()
            );

            assertDoesNotThrow(() -> commentVerifier.verify(comment));
        }

    }

    private static Stream<Arguments> verifyFailByCommentCaseArgs() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of("hello^^"),
                Arguments.of("he!!llo"),
                Arguments.of("!!hello")
        );
    }

    private static Stream<Arguments> verifyFailByTypeCaseArgs() {
        return Stream.of(
                Arguments.of((Object) null)
        );
    }
}