package com.webtoon.coding.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class PageDTO {

  @NotNull private Integer page;

  @NotNull private Integer size;

  public PageRequest getPageRequest() {
    return PageRequest.of(this.page, this.size);
  }
}
