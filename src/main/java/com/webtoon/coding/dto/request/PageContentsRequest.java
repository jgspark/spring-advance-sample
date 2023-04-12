package com.webtoon.coding.dto.request;

import com.webtoon.coding.domain.contents.Policy;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class PageContentsRequest extends PagingRequest {

  @NotNull @Getter private Policy type;

  public PageContentsRequest(Integer page, Integer size, Policy type) {
    super(page, size);
    this.type = type;
  }
}
