package com.webtoon.coding.domain.comment;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentKey implements Serializable {

    private Long userId;

    private Long contentsId;

    public static CommentKey of(Long userId, Long contentsId) {
        return new CommentKey(userId, contentsId);
    }
}
