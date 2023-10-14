package com.webtoon.coding.service.comment;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.dto.request.ContentsCommentRequest;

public interface CommentService {

    Comment created(ContentsCommentRequest dto);
}
