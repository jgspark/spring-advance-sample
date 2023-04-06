package com.lezhin.coding.mock;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.content.Comment;
import com.lezhin.coding.domain.content.Contents;
import com.lezhin.coding.domain.user.User;
import com.lezhin.coding.domain.support.CommentKey;
import com.lezhin.coding.service.dto.CommentStoreDTO;

public class CommentMock {

  private static Long userId = 1L;

  private static Long contentsId = 1L;

  private static String DEFAULT_COMMENT = "";

  private static EvaluationType type = EvaluationType.GOOD;

  public static Comment createdMock(User user, Contents contents) {
    return Comment.builder()
        .id(new CommentKey(user.getId(), contents.getId()))
        .type(type)
        .comment(DEFAULT_COMMENT)
        .contents(contents)
        .user(user)
        .build();
  }

  public static CommentStoreDTO createdStoreDTO() {
    return new CommentStoreDTO(userId, contentsId, type, DEFAULT_COMMENT);
  }
}
