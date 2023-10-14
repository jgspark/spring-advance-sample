package com.webtoon.coding.dto.request;

import com.webtoon.coding.domain.contents.Policy;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedContentsRequest {

    @NotNull
    private Policy type;

    private String coin;

}
