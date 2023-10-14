package com.webtoon.coding.service;

import com.webtoon.coding.domain.history.HistoryCustomReader;
import com.webtoon.coding.dto.request.PagingRequest;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.HistoryMock;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.service.history.HistoryService;
import com.webtoon.coding.service.history.HistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HistoryServiceTest {

    private HistoryService historyService;

    @Mock
    private HistoryCustomReader historyCustomReader;

    @BeforeEach
    void init() {
        historyService = new HistoryServiceImpl(historyCustomReader);
    }

    @Test
    @DisplayName("작품별 조회 이력 api")
    void getHistories() {

        Page<HistoryInfo> mocks =
                HistoryMock.createdPageList(UserMock.createdMock(), ContentsMock.createdMock());

        when(historyCustomReader.getAll(any(), eq(HistoryInfo.class)))
                .thenReturn(mocks);

        PagingRequest dto = new PagingRequest(0, 10);

        Page<HistoryInfo> entities = historyService.getHistories(dto);

        verify(historyCustomReader, times(1)).getAll(any(), eq(HistoryInfo.class));

        assertArrayEquals(mocks.getContent().toArray(new HistoryInfo[0]), entities.getContent().toArray(new HistoryInfo[0]));
    }

    @Test
    @DisplayName("최근 1주일 등록된 사용자 중 성인 조회 api 테스트 케이스")
    void getHistoriesByAdultUser() {

        Page<HistoryUser> mocks =
                HistoryMock.createPageHistoryUser(UserMock.createdMock(), ContentsMock.createdMock());

        when(historyCustomReader.getHistories(any())).thenReturn(mocks);

        PagingRequest dto = new PagingRequest(0, 10);

        Page<HistoryUser> entities = historyService.getHistoriesByAdultUser(dto);

        verify(historyCustomReader, times(1))
                .getHistories(any());

        assertArrayEquals(mocks.getContent().toArray(new HistoryUser[0]), entities.getContent().toArray(new HistoryUser[0]));
    }
}
