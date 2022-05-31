package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class SelectContentsStoreDTO extends PageDTO {

  @NotNull @Getter private ContentsType type;

  public SelectContentsStoreDTO(Integer page, Integer size, ContentsType type) {
    super(page, size);
    this.type = type;
  }
}
