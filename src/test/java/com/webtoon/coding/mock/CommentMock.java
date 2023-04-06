package com.webtoon.coding.mock;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.domain.comment.CommentKey;
import com.webtoon.coding.dto.request.ContentsCommentRequest;

public class CommentMock {

  private static Long userId = 1L;

  private static Long contentsId = 1L;

  private static String DEFAULT_COMMENT = "";

  private static Evaluation type = Evaluation.GOOD;

  public static Comment createdMock(User user, Contents contents) {
    return Comment.builder()
        .id(new CommentKey(user.getId(), contents.getId()))
        .type(type)
        .comment(DEFAULT_COMMENT)
        .contents(contents)
        .user(user)
        .build();
  }

  public static ContentsCommentRequest createdStoreDTO() {
    return new ContentsCommentRequest(userId, contentsId, type, DEFAULT_COMMENT);
  }
}
