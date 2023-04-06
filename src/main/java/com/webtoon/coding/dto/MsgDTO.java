package com.webtoon.coding.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MsgDTO {
  private String code;

  private String message;
}
