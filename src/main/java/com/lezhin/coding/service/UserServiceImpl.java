package com.lezhin.coding.service;

import com.lezhin.coding.repository.CommentRepository;
import com.lezhin.coding.repository.HistoryRepository;
import com.lezhin.coding.repository.UserRepository;
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
