package com.webtoon.coding.domain.comment;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@AllArgsConstructor
@EqualsAndHashCode(of = {"userId", "contentsId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentKey implements Serializable {

  private Long userId;

  private Long contentsId;
}
