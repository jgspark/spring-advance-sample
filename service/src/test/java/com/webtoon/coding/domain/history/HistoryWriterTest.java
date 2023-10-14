package com.webtoon.coding.domain.history;

import com.webtoon.coding.domain.common.Writer;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.user.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("히스토리 작성 클래스는")
@ExtendWith(MockitoExtension.class)
class HistoryWriterTest {

    @Mock
    private HistoryRepository historyRepository;

    private Writer<History> historyWriter;

    @BeforeEach
    public void init() {
        this.historyWriter = new HistoryWriter(historyRepository);
    }

    @Nested
    @DisplayName("저장 메소드는")
    public class Save {

        @Test
        public void testSaveSuccess() {

            User user = UserMock.createdMock();

            Contents contents = ContentsMock.createdMock();

            History mock = HistoryMock.createdMock(user, contents);

            when(historyRepository.save(any())).thenReturn(mock);

            History entity = historyWriter.save(mock);

            verify(historyRepository, times(1)).save(any());

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getUser(), mock.getUser());
            assertEquals(entity.getContents(), mock.getContents());
            assertEquals(entity.getCreatedDate(), mock.getCreatedDate());
        }
    }
}
