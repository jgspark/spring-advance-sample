package com.webtoon.coding.repository;

import com.webtoon.coding.config.JPAConfiguration;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JPAConfiguration.class)
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  private User mock;

  @BeforeEach
  void init() {
    mock = userRepository.save(UserMock.createdMock());
    userRepository.flush();
  }

  @Test
  @DisplayName("유저 삭제")
  void deleteById() {
    userRepository.deleteById(mock.getId());
    userRepository.flush();
  }
}
