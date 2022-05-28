package com.lezhin.coding.domain;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.support.CommentKey;

import javax.persistence.*;

@Entity
public class Comment {

  @EmbeddedId private CommentKey id;

  @Column(nullable = false)
  private EvaluationType evaluation;

  private String comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("contentsId")
  @JoinColumn(name = "contents_id", insertable = false, updatable = false)
  private Contents contents;
}
