package com.webtoon.coding.domain.contents;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.MODULE)
public class PolicyCoin {

    private Policy type;

    private String coin;

    public static PolicyCoin of(Policy type, String coin) {
        return new PolicyCoin(type, coin);
    }
}
