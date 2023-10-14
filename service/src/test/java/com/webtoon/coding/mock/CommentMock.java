package com.webtoon.coding.mock;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.dto.request.ContentsCommentRequest;

public class CommentMock {

    private static Long userId = 1L;

    private static Long contentsId = 1L;

    private static String DEFAULT_COMMENT = "";

    private static Evaluation type = Evaluation.GOOD;

    public static Comment createdMock(User user, Contents contents) {
        return Comment.of(DEFAULT_COMMENT, type, user, contents);
    }

    public static ContentsCommentRequest createdStoreDTO() {
        return new ContentsCommentRequest(userId, contentsId, type, DEFAULT_COMMENT);
    }
}
