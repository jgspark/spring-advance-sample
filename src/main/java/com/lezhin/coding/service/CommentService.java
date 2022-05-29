package com.lezhin.coding.service;

import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.service.dto.CommentStoreDTO;

public interface CommentService {

    Comment createdComment(CommentStoreDTO dto);
}
