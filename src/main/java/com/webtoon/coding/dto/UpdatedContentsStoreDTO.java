package com.webtoon.coding.dto;

import com.webtoon.coding.domain.contents.Policy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedContentsStoreDTO {

  @NotNull
  private Policy type;

  private Integer coin;
}
