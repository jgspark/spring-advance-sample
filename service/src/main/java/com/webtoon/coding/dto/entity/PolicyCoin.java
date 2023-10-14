package com.webtoon.coding.dto.entity;

import com.webtoon.coding.domain.contents.Policy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PolicyCoin {

    private Policy type;

    private String coin;

    public static PolicyCoin of(Policy type, String coin) {
        return new PolicyCoin(type, coin);
    }
}
