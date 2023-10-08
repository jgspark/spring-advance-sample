package com.webtoon.coding.core.resolver;

public record UserIdentifier(Long userId) {

    public static String HEADER_KEY = "X-USER-ID";

    public static UserIdentifier of(Long userId) {
        return new UserIdentifier(userId);
    }
}
