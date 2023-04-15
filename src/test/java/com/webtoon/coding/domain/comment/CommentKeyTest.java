package com.webtoon.coding.domain.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("코멘트 Id 클레스에서")
class CommentKeyTest {

    @Nested
    @DisplayName("생성 매소드는")
    public class Of {

        @Test
        @DisplayName("성공적이다.")
        public void testOfSuccess() {

            long userId = 1000;

            long contentsId = 100;

            CommentKey commentKey = CommentKey.of(userId, contentsId);

            assertEquals(commentKey.getUserId(), userId);
            assertEquals(commentKey.getContentsId(), contentsId);
        }

    }
}
