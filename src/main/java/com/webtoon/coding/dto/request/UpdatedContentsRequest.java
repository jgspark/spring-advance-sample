package com.webtoon.coding.dto.request;

import com.webtoon.coding.domain.contents.Policy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedContentsRequest {

    @NotNull
    private Policy type;

    private String coin;

}
