package com.webtoon.coding.service.comment;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.dto.CommentStoreDTO;

public interface CommentService {

    Comment createdComment(CommentStoreDTO dto);
}
