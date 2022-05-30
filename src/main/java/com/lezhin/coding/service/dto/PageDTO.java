package com.lezhin.coding.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

@Getter
@AllArgsConstructor
public class PageDTO {

  private Integer page;

  private Integer size;

  public PageRequest getPageRequest() {
    return PageRequest.of(this.page, this.size);
  }
}
