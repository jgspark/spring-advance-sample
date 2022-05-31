package com.lezhin.coding.domain;

import com.lezhin.coding.config.exption.DomainException;
import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.constants.MsgType;
import com.lezhin.coding.domain.support.CommentKey;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.regex.Pattern;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "contents_id"}))
public class Comment {

  @EmbeddedId private CommentKey id;

  @Column(nullable = false)
  private EvaluationType type;

  // 특수 문자 불가
  private String comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("contentsId")
  @JoinColumn(name = "contents_id", insertable = false, updatable = false)
  private Contents contents;

  @PrePersist
  void prePersist() {
    checkedComment();
  }

  @PreUpdate
  void preUpdate() {
    checkedComment();
  }

  @Transient
  public void checkedComment() {
    if (isIncludeSymbol(this.comment)) {
      throw new DomainException(MsgType.CommentDataException);
    }
  }

  private boolean isIncludeSymbol(String comment) {

    if (Objects.isNull(comment) || comment.isBlank()) {
      return false;
    }

    Pattern pattern2 = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

    boolean isPattern = pattern2.matcher(comment).find();

    return isPattern;
  }
}
