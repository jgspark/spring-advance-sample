package com.lezhin.coding.web;

import com.lezhin.coding.domain.content.Comment;
import com.lezhin.coding.service.CommentService;
import com.lezhin.coding.service.dto.CommentStoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("comment")
  @ResponseStatus(HttpStatus.CREATED)
  public Comment writeComment(@RequestBody @Valid CommentStoreDTO dto) {
    return commentService.createdComment(dto);
  }
}
