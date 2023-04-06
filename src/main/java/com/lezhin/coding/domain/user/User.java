package com.lezhin.coding.domain.user;

import com.lezhin.coding.constants.AdultType;
import com.lezhin.coding.constants.GenderType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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
  @CreatedDate private Date registerDate;
}
