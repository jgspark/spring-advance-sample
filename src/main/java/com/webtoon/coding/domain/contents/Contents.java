package com.webtoon.coding.domain.contents;

import com.webtoon.coding.exception.DomainException;
import com.webtoon.coding.exception.MsgType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
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
  private Policy type;

  // 성인 유무
  private Adult adult;

  // 금액
  @Column(nullable = false)
  private String coin;

  // 서비스 제공일
  private Date openDate;

  @PrePersist
  void prePersist() {
    checkedTypeAndCoin();
  }

  @PreUpdate
  void preUpdate() {
    checkedTypeAndCoin();
  }

  @Transient
  public void changedFreeType() {
    this.type = Policy.FREE;
  }

  @Transient
  public void changedPagar() {
    this.type = Policy.PAGAR;
  }

  @Transient
  public void setCoin(String coin) {
    this.coin = coin;
  }

  @Transient
  public void checkedTypeAndCoin() {
    if (Policy.FREE.equals(this.type)) {
      this.coin = "0";
    } else if (Policy.PAGAR.equals(this.type) && !Objects.isNull(this.coin)) {

      int coinNum = Integer.parseInt(this.coin);

      if (100 > coinNum || 500 < coinNum) {
        throw new DomainException(MsgType.CoinDataException);
      }
    }
  }
}
