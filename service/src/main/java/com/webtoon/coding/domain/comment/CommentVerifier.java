package com.webtoon.coding.domain.comment;

import com.webtoon.coding.core.exception.DomainException;
import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.domain.common.Verifier;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommentVerifier implements Verifier<Comment> {

    @Override
    public void verify(Comment comment) {

        if (isNotIncludeSymbol(comment.getComment())) {
            throw new DomainException(MsgType.CommentDataException);
        }

        if (ObjectUtils.isEmpty(comment.getType())) {
            throw new DomainException(MsgType.EvaluationDataException);
        }
    }

    private boolean isNotIncludeSymbol(String comment) {

        if (StringUtils.isEmpty(comment)) {
            return true;
        }

        if (StringUtils.isBlank(comment)) {
            return false;
        }

        Pattern pattern2 = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

        return pattern2.matcher(comment).find();
    }

}
