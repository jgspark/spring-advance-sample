package com.lezhin.coding.domain;

import com.lezhin.coding.config.exption.DomainException;
import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.constants.MsgType;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
  @Column(nullable = false)
  private String coin;

  // 서비스 제공일
  private String openDate;

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
    this.type = ContentsType.FREE;
  }

  @Transient
  public void changedPagar() {
    this.type = ContentsType.PAGAR;
  }

  @Transient
  public void setCoin(String coin) {
    this.coin = coin;
  }

  @Transient
  public void checkedTypeAndCoin() {
    if (ContentsType.FREE.equals(this.type)) {
      this.coin = "0";
    } else if (ContentsType.PAGAR.equals(this.type) && !Objects.isNull(this.coin)) {

      int coinNum = Integer.parseInt(this.coin);

      if (100 > coinNum || 500 < coinNum) {
        throw new DomainException(MsgType.CoinDataException);
      }
    }
  }
}
