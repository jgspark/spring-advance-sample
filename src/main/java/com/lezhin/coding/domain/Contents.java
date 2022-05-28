package com.lezhin.coding.domain;

import com.lezhin.coding.constants.ContentsType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contents {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 작품명
  private String name;

  // 작가
  private String author;

  // 작품 타입 (무료/유료)
  private ContentsType type;

  // 금액
  private String coin;

  // 서비스 제공일
  private String openDate;
}
