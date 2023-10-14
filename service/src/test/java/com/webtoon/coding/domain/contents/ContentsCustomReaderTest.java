package com.webtoon.coding.domain.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.dto.entity.PageContents;
import com.webtoon.coding.dto.view.ContentsInfo;
import com.webtoon.coding.dto.view.TopContents;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.mock.ContentsMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("커스텀 컨텐츠 리더 클레스에서")
@ExtendWith(MockitoExtension.class)
class ContentsCustomReaderTest {

    private ContentsCustomReader contentsCustomReader;

    @Mock
    private ContentsRepository contentsRepository;

    @BeforeEach
    public void init() {
        this.contentsCustomReader = new ContentsReader(contentsRepository);
    }

    @Nested
    @DisplayName("단일 조회 메소드는")
    public class Get {

        @Test
        @DisplayName("정상적으로 동작을 한다.")
        public void testGetSuccess() {

            Optional<ContentsInfo> mockOptional = Optional.of(ContentsMock.getContentsInfo());

            when(contentsRepository.findById(any(), eq(ContentsInfo.class))).thenReturn(mockOptional);

            Optional<ContentsInfo> entityOptional = contentsCustomReader.get(getId(), ContentsInfo.class);

            verify(contentsRepository, times(1)).findById(any(), eq(ContentsInfo.class));

            ContentsInfo entity = entityOptional.get();

            ContentsInfo mock = mockOptional.get();

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), mock.getCoin());
            assertEquals(entity.getOpenDate(), mock.getOpenDate());
        }

        @Test
        @DisplayName("빈값을 리턴 하면 fail이 리턴 된다.")
        public void testGetFailByEmptyCase() {

            when(contentsRepository.findById(any(), eq(ContentsInfo.class))).thenReturn(Optional.empty());

            Optional<ContentsInfo> entityOptional = contentsCustomReader.get(getId(), ContentsInfo.class);

            verify(contentsRepository, times(1)).findById(any(), eq(ContentsInfo.class));

            assertFalse(entityOptional.isPresent());

        }

    }

    @Nested
    @DisplayName("전체 조회 메소드는")
    public class GetAll {

        @Test
        @DisplayName("정상적으로 동작을 한다.")
        public void testGetAllSuccess() {

            Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

            when(contentsRepository.findAllProjectedBy(any(), eq(ContentsInfo.class))).thenReturn(mocks);

            Page<ContentsInfo> pageEntities = contentsCustomReader.getAll(getPageContents(), ContentsInfo.class);

            verify(contentsRepository, times(1)).findAllProjectedBy(any(), eq(ContentsInfo.class));

            assertArrayEquals(pageEntities.getContent().toArray(new ContentsInfo[0]),
                    mocks.getContent().toArray(new ContentsInfo[0]));

        }

        @Test
        @DisplayName("Null 를 리턴을 하면 Null 이 리턴 되게 된다.")
        public void testGetAllFailByNullCase() {

            when(contentsRepository.findAllProjectedBy(any(), eq(ContentsInfo.class))).thenReturn(null);

            Page<ContentsInfo> pageEntities = contentsCustomReader.getAll(getPageContents(), ContentsInfo.class);

            verify(contentsRepository, times(1)).findAllProjectedBy(any(), eq(ContentsInfo.class));

            assertNull(pageEntities);
        }

    }

    @Nested
    @DisplayName("상위 N개를 조회를 하는 메소드는")
    public class GetTopAllByType {

        @Test
        public void testGetTopAllByTypeBySuccess() {

            List<TopContents> mocks = ContentsMock.createdTopContentsList();

            when(contentsRepository.findTopByLimitAndType(any(), any())).thenReturn(mocks);

            List<TopContents> entities = contentsCustomReader.getTopAllByType(getTop(), Evaluation.GOOD);

            verify(contentsRepository, times(1)).findTopByLimitAndType(any(), any());

            assertArrayEquals(entities.toArray(new TopContents[0]), mocks.toArray(new TopContents[0]));
        }

    }

    private static long getId() {
        return 1L;
    }

    private static PageRequest getPage() {
        return PageRequest.of(0, 10);
    }

    private static PageContents getPageContents() {
        return PageContents.of(null, getPage());
    }

    private static int getTop() {
        return 3;
    }

}
