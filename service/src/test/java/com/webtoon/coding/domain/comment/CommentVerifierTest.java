package com.webtoon.coding.domain.comment;

import com.webtoon.coding.core.exception.DomainException;
import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.args.comment.CommentVerifyFailCommentArgs;
import com.webtoon.coding.mock.args.comment.CommentVerifyFailTypeArgs;
import com.webtoon.coding.mock.args.comment.CommentVerifySuccessArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("체크 클레스에서")
class CommentVerifierTest {

    private Verifier<Comment> commentVerifier;

    @BeforeEach
    public void init() {
        commentVerifier = new CommentVerifier();
    }

    @Nested
    @DisplayName("체크 메소드는")
    public class Verify {

        @DisplayName("실패를 한다.")
        @ParameterizedTest(name = "댓글은 {0} 이다.")
        @ArgumentsSource(CommentVerifyFailCommentArgs.class)
        public void testVerifyFailByCommentCase(String comment) {

            Comment contentsComment = Comment.of(comment, Evaluation.BAD, User.builder().build(),
                    Contents.builder().build());

            DomainException e = assertThrows(DomainException.class, () -> commentVerifier.verify(contentsComment));

            assertEquals(e.getMsgType(), MsgType.CommentDataException);
            assertEquals(e.getMessage(), MsgType.CommentDataException.getMessage());
        }

        @DisplayName("실패를 한다.")
        @ParameterizedTest(name = "평가는 {0} 이다.")
        @ArgumentsSource(CommentVerifyFailTypeArgs.class)
        public void testVerifyFailByTypeCase(Evaluation type) {

            Comment contentsComment = Comment.of("하하핳", type, User.builder().build(), Contents.builder().build());

            DomainException e = assertThrows(DomainException.class, () -> commentVerifier.verify(contentsComment));

            assertEquals(e.getMsgType(), MsgType.EvaluationDataException);
            assertEquals(e.getMessage(), MsgType.EvaluationDataException.getMessage());
        }

        @DisplayName("성공를 한다.")
        @ParameterizedTest(name = "댓글은 {0} 이다.")
        @ArgumentsSource(CommentVerifySuccessArgs.class)
        public void testVerifySuccess(String comment) {

            Comment contentsComment = Comment.of(comment, Evaluation.GOOD, User.builder().build(),
                    Contents.builder().build());

            assertDoesNotThrow(() -> commentVerifier.verify(contentsComment));
        }

    }

}
