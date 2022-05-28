package com.lezhin.coding.domain.support;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CommentKey implements Serializable {

  private Long userId;

  private Long contentsId;
}
