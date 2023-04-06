package com.webtoon.coding.dto;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.domain.comment.CommentKey;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentStoreDTO {

  @NotNull
  private Long userId;

  @NotNull
  private Long contentsId;
  @NotNull
  private Evaluation type;
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
