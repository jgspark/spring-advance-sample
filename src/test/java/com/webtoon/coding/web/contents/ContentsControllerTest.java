package com.webtoon.coding.web.contents;

import com.webtoon.coding.base.BaseTestController;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.contents.Policy;
import com.webtoon.coding.dto.request.PageContentsRequest;
import com.webtoon.coding.dto.request.UpdatedContentsRequest;
import com.webtoon.coding.dto.view.ContentsInfo;
import com.webtoon.coding.dto.view.TopContents;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.DtoMock;
import com.webtoon.coding.mock.JsonUtil;
import com.webtoon.coding.service.contents.ContentsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContentsController.class)
class ContentsControllerTest extends BaseTestController {

    @MockBean
    private ContentsService contentsService;

    @Nested
    @DisplayName("좋아요가 가장 많은 작품 3개 API")
    public class GetTopContents {

        @Test
        @DisplayName("는 성공적으로 동작")
        void testGetTopContents() throws Exception {

            List<TopContents> mocks = ContentsMock.createdTopContentsList();

            when(contentsService.getTopContents(any())).thenReturn(mocks);

            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/top-contents").param("type", Evaluation.GOOD.name())
                    .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                    .andDo(print());

            verify(contentsService, times(1)).getTopContents(any());

            action.andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]['id']").value(mocks.get(0).getId()))
                    .andExpect(jsonPath("$[0]['name']").value(mocks.get(0).getName()))
                    .andExpect(jsonPath("$[0]['author']").value(mocks.get(0).getAuthor()))
                    .andExpect(jsonPath("$[0]['type']").value(mocks.get(0).getType().name()))
                    .andExpect(jsonPath("$[0]['coin']").value(mocks.get(0).getCoin()))
                    // fixme : 고칠것
//                    .andExpect(jsonPath("$[0]['openDate']").value(mocks.get(0).getOpenDate()))
                    .andExpect(jsonPath("$[0]['sum']").value(mocks.get(0).getSum()));
        }

        @Test
        @DisplayName("컨텐츠가 없다면 204 를 반환 한다.")
        void testGetTopContentsByEmpty() throws Exception {

            when(contentsService.getTopContents(any())).thenReturn(Collections.emptyList());

            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/top-contents")
                    .param("type", Evaluation.GOOD.name())
                    .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                    .andDo(print());

            verify(contentsService, times(1)).getTopContents(any());

            action.andExpect(status().is2xxSuccessful());
        }

    }

    @Nested
    @DisplayName("특정 작품을 유료, 무료로 변경 할 수 있는 API")
    public class PatchContents {

        @Test
        @DisplayName("는 성공적으로 동작을 한다.")
        void testPatchContents() throws Exception {

            final UpdatedContentsRequest dto = new UpdatedContentsRequest(Policy.PAGAR, "100");

            Optional<Contents> mockOptional = Optional.of(ContentsMock.createdMock());

            when(contentsService.updatedTypeAndCoin(any() , any())).thenReturn(mockOptional);

            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.patch("/contents/" + 1)
                    .content(JsonUtil.convertObjectToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")).andDo(print());

            verify(contentsService , times(1))
                    .updatedTypeAndCoin(any() , any());

            Contents mock = mockOptional.get();

            action.andExpect(status().isOk())
                    .andExpect(jsonPath("$['id']").value(mock.getId()))
                    .andExpect(jsonPath("$['name']").value(mock.getName()))
                    .andExpect(jsonPath("$['author']").value(mock.getAuthor()))
                    .andExpect(jsonPath("$['type']").value(mock.getType().name()))
                    .andExpect(jsonPath("$['adult']").value(mock.getAdult().name()))
                    .andExpect(jsonPath("$['coin']").value(mock.getCoin()));
                    // FIXME : 고칠것
//                    .andExpect(jsonPath("$['openDate']").value(mock.getOpenDate()));

        }

        @Test
        @DisplayName("는 데이터가 없다면, 204 를 반환한다.")
        void testPatchContentsByEmptyCase() throws Exception {

            final UpdatedContentsRequest dto = new UpdatedContentsRequest(Policy.PAGAR, "100");

            when(contentsService.updatedTypeAndCoin(any() , any())).thenReturn(Optional.empty());

            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.patch("/contents/" + 1)
                    .content(JsonUtil.convertObjectToJson(dto)).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                    .andDo(print());

            verify(contentsService , times(1)).updatedTypeAndCoin(any() , any());

            action.andExpect(status().is2xxSuccessful());
        }


    }


    @Nested
    @DisplayName("작품 전체 조회 API")
    public class GetContents {
    @Test
    @DisplayName("는 정상적으로 동작한다.")
    void testGetContents() throws Exception {

        PageContentsRequest dto = DtoMock.getSelectContentsStoreDTO();

        Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

        when(contentsService.getContents(any())).thenReturn(mocks);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/contents")
                .param("type", dto.getType().name())
                .param("page", dto.getPage().toString())
                .param("size", dto.getSize().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print());

        verify(contentsService , times(1)).getContents(any());

        action.andExpect(status().isOk())
                .andExpect(jsonPath("$['content'][0]['id']").value(mocks.getContent().get(0).getId()))
                .andExpect(jsonPath("$['content'][0]['name']").value(mocks.getContent().get(0).getName()))
                .andExpect(jsonPath("$['content'][0]['type']").value(mocks.getContent().get(0).getType().name()))
                // fixme : 고칠것
//                .andExpect(jsonPath("$['content'][0]['openDate']").value(mocks.getContent().get(0).getOpenDate()))
                .andExpect(jsonPath("$['content'][0]['author']").value(mocks.getContent().get(0).getAuthor()))
                .andExpect(jsonPath("$['content'][0]['coin']").value(mocks.getContent().get(0).getCoin()));
    }
    @Test
    @DisplayName("는 빈데이터를 반환을 하면 204를 반환을 한다.")
    void testGetContentsByEmptyCase() throws Exception {

        PageContentsRequest dto = DtoMock.getSelectContentsStoreDTO();

        when(contentsService.getContents(any())).thenReturn(Page.empty());

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/contents")
                .param("type", dto.getType().name())
                .param("page", dto.getPage().toString())
                .param("size", dto.getSize().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print());

        verify(contentsService , times(1)).getContents(any());

        action.andExpect(status().is2xxSuccessful());
    }


    }

    @Nested
    @DisplayName("컨텐츠 하나만 조회 API")
    public class GetContentsOne {

        @Test
        @DisplayName("는 정상적으로 동작을 한다.")
        void testGetContentsOne() throws Exception {

            Optional<ContentsInfo> mockOptional = Optional.of(ContentsMock.getContentsInfo());

            when(contentsService.getContentsOne(any())).thenReturn(mockOptional);

            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/contents/" + 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andDo(print());


            verify(contentsService , times(1)).getContentsOne(any());

            ContentsInfo mock = mockOptional.get();

            action.andExpect(status().isOk())
                    .andExpect(jsonPath("$['id']").value(mock.getId()))
                    .andExpect(jsonPath("$['name']").value(mock.getName()))
                    .andExpect(jsonPath("$['author']").value(mock.getAuthor()))
                    .andExpect(jsonPath("$['type']").value(mock.getType().name()));
            // fixme : 고칠것
//                    .andExpect(jsonPath("$['openDate']").value(mock.getOpenDate()));
        }

        @Test
        @DisplayName("는 빈데이터를 리턴하면 204를 반환을 한다.")
        void testGetContentsOneByEmptyCase() throws Exception {

            Optional<ContentsInfo> mockOptional = Optional.of(ContentsMock.getContentsInfo());

            when(contentsService.getContentsOne(any())).thenReturn(Optional.empty());

            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/contents/" + 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andDo(print());


            verify(contentsService , times(1)).getContentsOne(any());

            ContentsInfo mock = mockOptional.get();

            action.andExpect(status().is2xxSuccessful());
        }

    }

}
