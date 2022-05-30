package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedContentsStoreDTO {

  private ContentsType type;

  private Integer coin;
}
