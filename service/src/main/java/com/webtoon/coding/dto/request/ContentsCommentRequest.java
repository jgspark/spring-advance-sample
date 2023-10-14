package com.webtoon.coding.dto.request;

import com.webtoon.coding.domain.comment.Evaluation;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentsCommentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long contentsId;

    @NotNull
    private Evaluation type;

    private String comment;

}
