package com.webtoon.coding.domain.history;

import com.webtoon.coding.domain.contents.Adult;
import com.webtoon.coding.dto.entity.PageHistoryUser;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.HistoryMock;
import com.webtoon.coding.mock.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("히스토리 읽기 클래스는")
@ExtendWith(MockitoExtension.class)
class HistoryReaderTest {

    @Mock
    private HistoryRepository historyRepository;

    private HistoryCustomReader historyCustomReader;

    @BeforeEach
    public void init() {
        historyCustomReader = new HistoryReader(historyRepository);
    }

    @Nested
    @DisplayName("히스토리 전체 데이터를 읽는다면")
    public class GetAll {

        @Test
        @DisplayName("성공적으로 작동이 된다.")
        public void testGetAll() {

            Page<HistoryInfo> mocks =
                    HistoryMock.createdPageList(UserMock.createdMock(), ContentsMock.createdMock());

            when(historyRepository.findAllProjectedBy(any(), eq(HistoryInfo.class)))
                    .thenReturn(mocks);

            Page<HistoryInfo> entity = historyCustomReader.getAll(PageRequest.of(0, 10), HistoryInfo.class);

            verify(historyRepository, times(1)).findAllProjectedBy(any(), eq(HistoryInfo.class));

            assertArrayEquals(mocks.getContent().toArray(new HistoryInfo[0]), entity.getContent().toArray(new HistoryInfo[0]));
        }

        @Test
        @DisplayName("ClassType 을 정의하지 않으면 실패 한다.")
        public void testGetAllFail() {
            RuntimeException e = assertThrows(RuntimeException.class, () -> historyCustomReader.getAll(PageRequest.of(0, 10), null));
            assertEquals(e.getMessage(), "type is not null");
        }
    }

    @Nested
    @DisplayName("히스토리 전체 데이터를 읽는다면2 ")
    public class GetHistories {

        @Test
        @DisplayName("성공적으로 작동이 된다.")
        public void testGetHistories() {

            Page<HistoryUser> mocks =
                    HistoryMock.createPageHistoryUser(UserMock.createdMock(), ContentsMock.createdMock());

            when(historyRepository.findByCreatedDateBetweenAndContents_AdultType(any(), any(), any(), any(), anyLong()))
                    .thenReturn(mocks);

            PageHistoryUser dto = PageHistoryUser.of(PageRequest.of(0, 10), new Date(), new Date(), Adult.NONE_ADULT, 3L);

            Page<HistoryUser> entity = historyCustomReader.getHistories(dto);

            verify(historyRepository, times(1))
                    .findByCreatedDateBetweenAndContents_AdultType(any(), any(), any(), any(), anyLong());

            assertArrayEquals(
                    entity.getContent().toArray(new HistoryUser[0]),
                    mocks.getContent().toArray(new HistoryUser[0])
            );
        }
    }
}
