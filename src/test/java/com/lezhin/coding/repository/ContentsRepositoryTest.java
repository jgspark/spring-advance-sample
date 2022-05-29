package com.lezhin.coding.repository;

import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.mock.ContentsMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ContentsRepositoryTest {

  @Autowired private ContentsRepository contentsRepository;

  private Contents contents;

  @BeforeEach
  void init() {
    contents = contentsRepository.saveAndFlush(ContentsMock.createdMock());
    contentsRepository.flush();
  }
}
