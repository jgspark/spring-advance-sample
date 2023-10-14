package com.webtoon.coding.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

@Getter
@AllArgsConstructor
public class PagingRequest {

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    public PageRequest getPageRequest() {
        return PageRequest.of(this.page, this.size);
    }

}
