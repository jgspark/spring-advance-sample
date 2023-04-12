package com.webtoon.coding.dto.layer;

import com.webtoon.coding.domain.contents.Policy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageContents {

    private Policy type;

    private PageRequest pageRequest;

    public static PageContents of(Policy type, PageRequest pageRequest) {
        return new PageContents(type, pageRequest);
    }
}
