package com.webtoon.coding.service.comment;

import com.webtoon.coding.exception.NoDataException;
import com.webtoon.coding.exception.MsgType;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.repository.comment.CommentRepository;
import com.webtoon.coding.repository.contents.ContentsRepository;
import com.webtoon.coding.repository.user.UserRepository;
import com.webtoon.coding.dto.CommentStoreDTO;
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

    final User user =
        userRepository
            .findById(dto.getUserId())
            .orElseThrow(() -> new NoDataException(MsgType.NoUserData));

    final Contents contents =
        contentsRepository
            .findById(dto.getContentsId())
            .orElseThrow(() -> new NoDataException(MsgType.NoContentsData));

    return commentRepository.save(dto.toEntity(user, contents));
  }
}
