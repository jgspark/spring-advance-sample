package com.lezhin.coding.service;

import com.lezhin.coding.domain.content.Comment;
import com.lezhin.coding.service.dto.CommentStoreDTO;

public interface CommentService {

    Comment createdComment(CommentStoreDTO dto);
}
