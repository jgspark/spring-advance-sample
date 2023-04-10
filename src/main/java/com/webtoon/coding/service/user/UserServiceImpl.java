package com.webtoon.coding.service.user;

import com.webtoon.coding.infra.repository.comment.CommentRepository;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final CommentRepository commentRepository;

  private final HistoryRepository historyRepository;

  private final UserRepository userRepository;

  @Override
  @Transactional
  public void removeUser(Long id) {



    commentRepository.deleteById_UserId(id);
    historyRepository.deleteByUser_Id(id);
    userRepository.deleteById(id);
  }
}
