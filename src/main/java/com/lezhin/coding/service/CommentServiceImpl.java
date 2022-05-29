package com.lezhin.coding.service;

import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.repository.CommentRepository;
import com.lezhin.coding.repository.ContentsRepository;
import com.lezhin.coding.repository.UserRepository;
import com.lezhin.coding.service.dto.CommentStoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  private final UserRepository userRepository;

  private final ContentsRepository contentsRepository;

  @Override
  @Transactional
  public Comment createdComment(CommentStoreDTO dto) {

    final User user = userRepository.findById(dto.getUserId()).orElseThrow();

    final Contents contents = contentsRepository.findById(dto.getContentsId()).orElseThrow();

    return commentRepository.save(dto.toEntity(user, contents));
  }
}
