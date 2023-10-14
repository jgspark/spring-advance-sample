package com.webtoon.coding.domain.contents;

import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.dto.entity.PolicyCoin;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
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

    public void changeDetail(Verifier<Contents> verifier, PolicyCoin policyCoin) {

        if (Policy.FREE.equals(policyCoin.getType())) {
            freeType();
        }
        else {
            pagarType(policyCoin.getCoin());
        }

        verifier.verify(this);
    }

    @Transient
    private void freeType() {
        this.type = Policy.FREE;
        this.coin = "0";
    }

    @Transient
    private void pagarType(String coin) {
        this.type = Policy.PAGAR;
        this.coin = coin;
    }

}
