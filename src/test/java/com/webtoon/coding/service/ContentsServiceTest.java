package com.webtoon.coding.service;

import com.webtoon.coding.domain.contents.ContentsReader;
import com.webtoon.coding.domain.contents.Policy;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.DateMock;
import com.webtoon.coding.mock.DtoMock;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.service.contents.ContentsService;
import com.webtoon.coding.service.contents.ContentsServiceImpl;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.dto.SelectContentsStoreDTO;
import com.webtoon.coding.dto.TopContents;
import com.webtoon.coding.dto.request.UpdatedContentsRequest;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContentsServiceTest {

    private ContentsService contentsService;

    @Mock
    private ContentsRepository contentsRepository;

    @Mock
    private ContentsReader contentReader;

    @BeforeEach
    void init() {
        contentsService = new ContentsServiceImpl(contentsRepository, contentReader);
    }

    @Test
    @DisplayName("top3 로직 테스트 케이스")
    void getTopContents() {

        List<TopContents> mocks = ContentsMock.createdTopContentsList();

        BDDMockito.given(contentsRepository.findTopByLimitAndType(any(), any())).willReturn(mocks);

        List<TopContents> entities = contentsService.getTopContents(Evaluation.GOOD);

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

    @Test
    @DisplayName("컨텐츠 타입 테스트 케이스")
    void updatedTypeAndCoin() {

        Optional<Contents> mockOptional = Optional.of(ContentsMock.createdMock());

        final Long id = 1L;

        final UpdatedContentsRequest dto = new UpdatedContentsRequest(Policy.PAGAR, 100);

        BDDMockito.given(contentsRepository.findById(any())).willReturn(mockOptional);

        Optional<Contents> entityOptional = contentsService.updatedTypeAndCoin(id, dto);

        Contents entity = entityOptional.get();

        Contents mock = mockOptional.get();

        Assertions.assertEquals(entity.getId(), mock.getId());
        Assertions.assertEquals(entity.getName(), mock.getName());
        Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
        Assertions.assertEquals(entity.getType(), dto.getType());
        Assertions.assertEquals(entity.getCoin(), dto.getCoin().toString());
        Assertions.assertEquals(entity.getOpenDate(), mock.getOpenDate());
    }

    @Test
    @DisplayName("컨텐츠 목록 조회 테스트 케이스")
    void getContents() {

        SelectContentsStoreDTO dto = DtoMock.getSelectContentsStoreDTO();

        Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

        BDDMockito.given(contentsRepository.findByType(any(), any(), eq(ContentsInfo.class)))
                .willReturn(mocks);

        Page<ContentsInfo> entities = contentsService.getContents(dto);

        BDDMockito.then(contentsRepository).should().findByType(any(), any(), eq(ContentsInfo.class));

        List<ContentsInfo> mockContent = mocks.getContent();

        List<ContentsInfo> entitiesContent = entities.getContent();

        Assertions.assertEquals(entitiesContent.size(), mockContent.size());

        ContentsInfo entity = entitiesContent.get(0);

        ContentsInfo mock = mockContent.get(0);

        Assertions.assertEquals(entity.getName(), mock.getName());
        Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
        Assertions.assertEquals(entity.getType(), mock.getType());
        Assertions.assertEquals(entity.getCoin(), mock.getCoin());
        Assertions.assertEquals(
                DateMock.changedFormatDate(entity.getOpenDate()),
                DateMock.changedFormatDate(mock.getOpenDate()));
    }

    @Test
    @DisplayName("하나만 조회 테스트 케이스")
    void getContentsOne() {

        Optional<ContentsInfo> mockOptional = Optional.of(ContentsMock.getContentsInfo());

        BDDMockito.given(contentsRepository.findById(any(), eq(ContentsInfo.class)))
                .willReturn(mockOptional);

        Optional<ContentsInfo> entityOptional = contentsService.getContentsOne(1L);

        BDDMockito.then(contentsRepository).should().findById(any(), eq(ContentsInfo.class));

        Assertions.assertEquals(entityOptional.isPresent(), mockOptional.isPresent());

        ContentsInfo entity = entityOptional.get();

        ContentsInfo mock = mockOptional.get();

        Assertions.assertEquals(entity.getName(), mock.getName());
        Assertions.assertEquals(entity.getAuthor(), mock.getAuthor());
        Assertions.assertEquals(entity.getType(), mock.getType());
        Assertions.assertEquals(entity.getCoin(), mock.getCoin());
        Assertions.assertEquals(
                DateMock.changedFormatDate(entity.getOpenDate()),
                DateMock.changedFormatDate(mock.getOpenDate()));
    }
}
