package com.webtoon.coding.dto.request;

import com.webtoon.coding.domain.contents.Policy;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class PageContentsRequest extends PagingRequest {

    @NotNull
    private final Policy type;

    public PageContentsRequest(Integer page, Integer size, Policy type) {
        super(page, size);
        this.type = type;
    }
}
