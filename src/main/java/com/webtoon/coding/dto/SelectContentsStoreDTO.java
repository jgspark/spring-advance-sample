package com.webtoon.coding.dto;

import com.webtoon.coding.domain.content.Policy;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class SelectContentsStoreDTO extends PageDTO {

  @NotNull @Getter private Policy type;

  public SelectContentsStoreDTO(Integer page, Integer size, Policy type) {
    super(page, size);
    this.type = type;
  }
}
