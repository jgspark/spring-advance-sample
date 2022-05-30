package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
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
