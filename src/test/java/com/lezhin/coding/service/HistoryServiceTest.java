package com.lezhin.coding.service;

import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.mock.HistoryMock;
import com.lezhin.coding.mock.UserMock;
import com.lezhin.coding.repository.HistoryRepository;
import com.lezhin.coding.service.dto.HistoryInfo;
import com.lezhin.coding.service.dto.HistoryUser;
import com.lezhin.coding.service.dto.PageDTO;
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
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HistoryServiceTest {

  private HistoryService historyService;

  @Mock private HistoryRepository historyRepository;

  @BeforeEach
  void init() {
    historyService = new HistoryServiceImpl(historyRepository);
  }

  @Test
  @DisplayName("작품별 조회 이력 api")
  void getHistories() {

    Page<HistoryInfo> mocks =
        HistoryMock.createdPageList(UserMock.createdMock(), ContentsMock.createdMock());

    BDDMockito.given(historyRepository.findAllProjectedBy(any(), eq(HistoryInfo.class)))
        .willReturn(mocks);

    PageDTO dto = new PageDTO(0, 10);

    Page<HistoryInfo> entities = historyService.getHistories(dto);

    BDDMockito.then(historyRepository).should().findAllProjectedBy(any(), eq(HistoryInfo.class));

    List<HistoryInfo> mockContent = mocks.getContent();

    List<HistoryInfo> entitiesContent = entities.getContent();

    Assertions.assertEquals(entitiesContent.size(), mockContent.size());

    HistoryInfo entity = entitiesContent.get(0);

    HistoryInfo mock = mockContent.get(0);

    Assertions.assertEquals(entity.getId(), mock.getId());
    Assertions.assertEquals(entity.getUserName(), mock.getUserName());
    Assertions.assertEquals(entity.getContentsName(), mock.getContentsName());
    Assertions.assertEquals(entity.getContentsType(), mock.getContentsType());
  }

  @Test
  @DisplayName("최근 1주일 등록된 사용자 중 성인 조회 api 테스트 케이스")
  void getHistoriesByAdultUser() {

    Page<HistoryUser> mocks =
        HistoryMock.createPageHistoryUser(UserMock.createdMock(), ContentsMock.createdMock());

    BDDMockito.given(
            historyRepository.findByCreatedDateBetweenAndContents_AdultType(
                any(), any(), any(), any(), anyLong()))
        .willReturn(mocks);

    PageDTO dto = new PageDTO(0, 10);

    Page<HistoryUser> entities = historyService.getHistoriesByAdultUser(dto);

    BDDMockito.then(historyRepository)
        .should()
        .findByCreatedDateBetweenAndContents_AdultType(any(), any(), any(), any(), anyLong());

    List<HistoryUser> mockContent = mocks.getContent();

    List<HistoryUser> entitiesContent = entities.getContent();

    Assertions.assertEquals(entitiesContent.size(), mockContent.size());

    HistoryUser entity = entitiesContent.get(0);

    HistoryUser mock = mockContent.get(0);

    Assertions.assertEquals(entity.getId(), mock.getId());
    Assertions.assertEquals(entity.getUserName(), mock.getUserName());
    Assertions.assertEquals(entity.getUserEmail(), mock.getUserEmail());
    Assertions.assertEquals(entity.getGender(), mock.getGender());
    Assertions.assertEquals(entity.getType(), mock.getType());
    Assertions.assertEquals(entity.getRegisterDate(), mock.getRegisterDate());
  }
}
