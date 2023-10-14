package com.webtoon.coding.dto.response;

import com.mysema.commons.lang.Assert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private final String code;

    private final String message;

    public static ErrorResponse of(String code, String message) {

        Assert.notNull(code, "code is null");
        Assert.notNull(message, "message is null");

        return new ErrorResponse(code, message);
    }
}
