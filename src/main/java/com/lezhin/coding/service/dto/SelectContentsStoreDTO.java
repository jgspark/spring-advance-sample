package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;
import lombok.Getter;

public class SelectContentsStoreDTO extends PageDTO {

  @Getter private ContentsType type;

  public SelectContentsStoreDTO(Integer page, Integer size, ContentsType type) {
    super(page, size);
    this.type = type;
  }
}
