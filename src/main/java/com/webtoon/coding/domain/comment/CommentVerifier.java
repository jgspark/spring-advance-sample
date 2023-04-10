package com.webtoon.coding.domain.comment;

import com.webtoon.coding.dto.model.comment.ContentsComment;
import com.webtoon.coding.exception.DomainException;
import com.webtoon.coding.exception.MsgType;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class CommentVerifier {

    public void verify(ContentsComment comment) {

        if (isNotIncludeSymbol(comment.getComment())) {
            throw new DomainException(MsgType.CommentDataException);
        }
    }

    private boolean isNotIncludeSymbol(String comment) {

        if (Objects.isNull(comment) || comment.isBlank()) {
            return false;
        }

        Pattern pattern2 = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

        return pattern2.matcher(comment).find();
    }
}
