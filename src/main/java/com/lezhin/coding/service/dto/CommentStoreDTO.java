package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.domain.support.CommentKey;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentStoreDTO {

  private Long userId;

  private Long contentsId;

  private EvaluationType type;
  private String comment;

  public Comment toEntity(User user, Contents contents) {
    return Comment.builder()
        .id(new CommentKey(user.getId(), contents.getId()))
        .comment(this.comment)
        .type(this.type)
        .user(user)
        .contents(contents)
        .build();
  }
}
