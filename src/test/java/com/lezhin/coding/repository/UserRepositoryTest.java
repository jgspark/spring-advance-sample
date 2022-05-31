package com.lezhin.coding.repository;

import com.lezhin.coding.config.JPAConfiguration;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.mock.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

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
