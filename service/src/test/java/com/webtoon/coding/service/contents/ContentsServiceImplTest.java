package com.webtoon.coding.service.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.common.Reader;
import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.contents.ContentsCustomReader;
import com.webtoon.coding.domain.contents.Policy;
import com.webtoon.coding.dto.request.PageContentsRequest;
import com.webtoon.coding.dto.request.UpdatedContentsRequest;
import com.webtoon.coding.dto.view.ContentsInfo;
import com.webtoon.coding.dto.view.TopContents;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.DateMock;
import com.webtoon.coding.mock.DtoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContentsServiceImplTest {

    private ContentsService contentsService;

    @Mock
    private Reader<Contents> contentReader;

    @Mock
    private ContentsCustomReader contentsCustomReader;

    @Mock
    private Verifier<Contents> contentsVerifier;

    @BeforeEach
    public void init() {
        this.contentsService = new ContentsServiceImpl(contentReader, contentsCustomReader, contentsVerifier);
    }

    @Nested
    @DisplayName("업데이트 하는 로직은")
    public class GetTopContents {

        @Test
        @DisplayName("정상적으로 조회를 한다.")
        public void testGetTopContentsSuccess() throws Exception {

            List<TopContents> mocks = ContentsMock.createdTopContentsList();

            when(contentsCustomReader.getTopAllByType(anyInt(), any(Evaluation.class))).thenReturn(mocks);

            List<TopContents> entities = contentsService.getTopContents(Evaluation.GOOD);

            verify(contentsCustomReader, times(1)).getTopAllByType(anyInt(), any(Evaluation.class));

            assertArrayEquals(entities.toArray(), mocks.toArray());
        }

    }

    @Nested
    @DisplayName("업데이트 하는 로직은")
    public class UpdatedTypeAndCoin {

        @Test
        @DisplayName("정상적으로 동작을 한다.")
        public void testUpdatedTypeAndCoinSuccess() {

            Contents mock = ContentsMock.createdMock();

            when(contentReader.get(any())).thenReturn(mock);

            long id = 1L;
            UpdatedContentsRequest request = new UpdatedContentsRequest(Policy.PAGAR, "100");

            Optional<Contents> entityOptional = contentsService.updatedTypeAndCoin(id, request);

            verify(contentReader, times(1)).get(any());

            Contents entity = entityOptional.get();

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), request.getType());
            assertEquals(entity.getCoin(), request.getCoin());
            assertEquals(entity.getOpenDate(), mock.getOpenDate());
        }

    }

    @Nested
    @DisplayName("컨텐츠 조회 메소드는")
    public class GetContents {

        @Test
        @DisplayName("정상적으로 조회를 한다.")
        public void testGetContentsSuccess() {

            Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

            when(contentsCustomReader.getAll(any(), eq(ContentsInfo.class))).thenReturn(mocks);

            PageContentsRequest dto = DtoMock.getSelectContentsStoreDTO();

            Page<ContentsInfo> entities = contentsService.getContents(dto);

            verify(contentsCustomReader, times(1)).getAll(any(), eq(ContentsInfo.class));

            assertArrayEquals(entities.getContent().toArray(new ContentsInfo[0]),
                    mocks.getContent().toArray(new ContentsInfo[0]));
        }

    }

    @Nested
    @DisplayName("하나만 조회 하는 메소드는")
    public class GetContentsOne {

        @Test
        @DisplayName("정상적으로 조회를 한다.")
        public void testGetContentsOne() {

            Optional<ContentsInfo> mockOptional = Optional.of(ContentsMock.getContentsInfo());

            when(contentsCustomReader.get(any(), eq(ContentsInfo.class))).thenReturn(mockOptional);

            Optional<ContentsInfo> entityOptional = contentsService.getContentsOne(1L);

            verify(contentsCustomReader, times(1)).get(any(), eq(ContentsInfo.class));

            ContentsInfo entity = entityOptional.get();
            ContentsInfo mock = mockOptional.get();

            assertEquals(mockOptional, entityOptional);
            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), mock.getCoin());
            assertEquals(DateMock.changedFormatDate(entity.getOpenDate()),
                    DateMock.changedFormatDate(mock.getOpenDate()));
        }

    }

}
