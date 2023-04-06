package com.webtoon.coding.web.comment;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.service.comment.CommentService;
import com.webtoon.coding.dto.request.ContentsCommentRequest;
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
  public Comment writeComment(@RequestBody @Valid ContentsCommentRequest request) {
    return commentService.created(request);
  }
}
