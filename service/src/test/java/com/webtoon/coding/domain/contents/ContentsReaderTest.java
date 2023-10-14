package com.webtoon.coding.domain.contents;

import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.core.exception.NoDataException;
import com.webtoon.coding.domain.common.Reader;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.mock.ContentsMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("컨텐츠 리더 클레스에서")
@ExtendWith(MockitoExtension.class)
class ContentsReaderTest {

    private Reader<Contents> contentsReader;

    @Mock
    private ContentsRepository contentsRepository;

    @BeforeEach
    public void init() {
        contentsReader = new ContentsReader(contentsRepository);
    }

    @Nested
    @DisplayName("단일 조회 메소드에서는")
    public class GetMethod {

        @Test
        @DisplayName("성공을 하게 된다.")
        public void testGetSuccess() {

            Long id = 1L;

            Optional<Contents> contentsOptional = Optional.of(ContentsMock.createdMock());

            when(contentsRepository.findById(any())).thenReturn(contentsOptional);

            Contents entity = contentsReader.get(id);

            verify(contentsRepository, times(1)).findById(any());

            Contents contents = contentsOptional.get();

            assertEquals(entity.getId(), contents.getId());
            assertEquals(entity.getName(), contents.getName());
            assertEquals(entity.getAuthor(), contents.getAuthor());
            assertEquals(entity.getType(), contents.getType());
            assertEquals(entity.getAdult(), contents.getAdult());
            assertEquals(entity.getOpenDate(), contents.getOpenDate());

        }

        @Test
        @DisplayName("실패를 하게 된다.")
        public void testGetFail() {
            Long id = 1L;

            when(contentsRepository.findById(any())).thenReturn(Optional.empty());

            NoDataException e = assertThrows(NoDataException.class, () -> contentsReader.get(id));

            verify(contentsRepository, times(1)).findById(any());

            assertEquals(e.getMsgType(), MsgType.NoContentsData);
            assertEquals(e.getMsgType().getMessage(), MsgType.NoContentsData.getMessage());
        }
    }
}
