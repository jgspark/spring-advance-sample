package com.webtoon.coding.domain.comment;

import com.webtoon.coding.core.exception.DomainException;
import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.mock.args.comment.CommentVerifyFailCommentArgs;
import com.webtoon.coding.mock.args.comment.CommentVerifyFailTypeArgs;
import com.webtoon.coding.mock.args.comment.CommentVerifySuccessArgs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("코멘트 도메인에서")
class CommentTest {

    @Mock
    private Verifier<Comment> verifier;

    @Nested
    @DisplayName("생성 메소드는")
    public class Of {

        @Test
        public void testOfSuccess() {

            String comment = "hello";

            Evaluation type = Evaluation.GOOD;

            User user = UserMock.createdMock();

            Contents contents = ContentsMock.createdMock();

            Comment entity = Comment.of(comment, type, user, contents);

            assertEquals(entity.getComment(), comment);
            assertEquals(entity.getType(), type);
            assertEquals(entity.getUser(), user);
            assertEquals(entity.getContents(), contents);
        }

    }

    @Nested
    @DisplayName("작성 메소드는")
    public class Write {

        @DisplayName("성공를 한다.")
        @ParameterizedTest(name = "댓글은 {0} 이다.")
        @ArgumentsSource(CommentVerifySuccessArgs.class)
        public void testWriteSuccess(String stringArgs) {

            Comment comment = Comment.of(stringArgs, Evaluation.GOOD, User.builder().build(),
                    Contents.builder().build());

            doNothing().when(verifier).verify(any());

            assertDoesNotThrow(() -> comment.write(verifier));

            verify(verifier, times(1)).verify(any());
        }

        @DisplayName("실패를 한다.")
        @ParameterizedTest(name = "댓글은 {0} 이다.")
        @ArgumentsSource(CommentVerifyFailCommentArgs.class)
        public void testWriteFailByCommentArgs(String stringArgs) {

            Comment comment = Comment.of(stringArgs, Evaluation.GOOD, User.builder().build(),
                    Contents.builder().build());

            doThrow(new DomainException(MsgType.CommentDataException)).when(verifier).verify(any());

            DomainException e = assertThrows(DomainException.class, () -> comment.write(verifier));

            verify(verifier, times(1)).verify(any());

            assertEquals(e.getMsgType(), MsgType.CommentDataException);
            assertEquals(e.getMessage(), MsgType.CommentDataException.getMessage());
        }

        @DisplayName("실패를 한다.")
        @ParameterizedTest(name = "평가는 {0} 이다.")
        @ArgumentsSource(CommentVerifyFailTypeArgs.class)
        public void testWriteFailByTypeArgs(Evaluation type) {

            Comment comment = Comment.of("test", type, User.builder().build(), Contents.builder().build());

            doThrow(new DomainException(MsgType.EvaluationDataException)).when(verifier).verify(any());

            DomainException e = assertThrows(DomainException.class, () -> comment.write(verifier));

            verify(verifier, times(1)).verify(any());

            assertEquals(e.getMsgType(), MsgType.EvaluationDataException);
            assertEquals(e.getMessage(), MsgType.EvaluationDataException.getMessage());
        }

    }

}
