package com.webtoon.coding.dto;

import com.webtoon.coding.domain.contents.Policy;
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

  private Policy type;

  private String coin;

  private Date openDate;

  private Integer sum;
}
