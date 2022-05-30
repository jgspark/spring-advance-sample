package com.lezhin.coding.service;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.repository.ContentsRepository;
import com.lezhin.coding.service.dto.TopContents;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContentsServiceTest {

  private ContentsService contentsService;

  @Mock private ContentsRepository contentsRepository;

  @BeforeEach
  void init() {
    contentsService = new ContentsServiceImpl(contentsRepository);
  }

  @Test
  @DisplayName("top3 로직 테스트 케이스")
  void getTopContents() {

    List<TopContents> mocks = ContentsMock.createdTopContentsList();

    BDDMockito.given(contentsRepository.findTopByLimitAndType(any(), any())).willReturn(mocks);

    List<TopContents> entities = contentsService.getTopContents(EvaluationType.GOOD);

    Assertions.assertEquals(entities.size(), mocks.size());

    TopContents entity = entities.get(0);

    TopContents mock = mocks.get(0);

    BDDMockito.then(contentsRepository).should().findTopByLimitAndType(any(), any());

    Assertions.assertEquals(entity.getId(), mock.getId());
    Assertions.assertEquals(entity.getName(), mock.getName());
    Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
    Assertions.assertEquals(entity.getType(), mock.getType());
    Assertions.assertEquals(entity.getCoin(), mock.getCoin());
    Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
    Assertions.assertEquals(entity.getSum(), mock.getSum());
  }
}
