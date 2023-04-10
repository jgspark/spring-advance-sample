package com.webtoon.coding.domain.comment;

import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.UserMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("컨텐츠 코멘트 클레스에서는")
class ContentsCommentTest {

    private final CommentVerifier verifier = mock(CommentVerifier.class);

    @Nested
    @DisplayName("생성 메소드는")
    public class CreatedMethod {

        @Test
        @DisplayName("성공을 하게 된다.")
        public void testCreatedSuccess() {

            String comment = "test";

            Evaluation type = Evaluation.GOOD;

            User user = UserMock.createdMock();

            Contents contents = ContentsMock.createdMock();

            ContentsComment comments = ContentsComment.of(
                    comment,
                    type,
                    user,
                    contents);

            Comment entity = assertDoesNotThrow(() -> comments.created(verifier));

            verify(verifier, times(1)).verify(any());

            assertEquals(entity.getComment(), comment);
            assertEquals(entity.getUser(), user);
            assertEquals(entity.getContents(), contents);
            assertEquals(entity.getType(), type);
        }

    }

}
