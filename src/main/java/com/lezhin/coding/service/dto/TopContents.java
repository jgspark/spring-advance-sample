package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor

@NoArgsConstructor
public class TopContents {

  private Long id;

  private String name;

  private String author;

  private ContentsType type;

  private String coin;

  private Date openDate;

  private Integer sum;
}
