package com.lezhin.coding.repository;

import com.lezhin.coding.config.JPAConfiguration;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.History;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.mock.HistoryMock;
import com.lezhin.coding.mock.UserMock;
import org.junit.jupiter.api.Assertions;
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
class HistoryRepositoryTest {

  @Autowired private HistoryRepository historyRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ContentsRepository contentsRepository;

  private User user;

  private Contents contents;

  @BeforeEach
  void init() {
    user = userRepository.saveAndFlush(UserMock.createdMock());
    contents = contentsRepository.saveAndFlush(ContentsMock.createdMock());
  }

  @Test
  @DisplayName("히스토리 저장 로직 테스트 케이스")
  void save() {

    History mock = HistoryMock.createdMock(user, contents);

    History entity = historyRepository.save(mock);

    Assertions.assertEquals(entity.getUser().getId(), mock.getUser().getId());
    Assertions.assertEquals(entity.getContents().getId(), entity.getContents().getId());
    Assertions.assertNotNull(entity.getCreatedDate());
  }
}
