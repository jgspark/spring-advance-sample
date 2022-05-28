package com.lezhin.coding.domain;

import com.lezhin.coding.constants.AdultType;
import com.lezhin.coding.constants.GenderType;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 유저이름
  private String userName;

  // 유저메일
  private String userEmail;

  // 성별타입
  private GenderType gender;

  // 성인유무 (유형)
  private AdultType type;

  // 서비스 등록일
  private String registerDate;
}
