package com.lezhin.coding.mock;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.domain.support.CommentKey;

public class CommentMock {

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

}
