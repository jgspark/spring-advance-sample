package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedContentsStoreDTO {

  @NotNull
  private ContentsType type;

  private Integer coin;
}
