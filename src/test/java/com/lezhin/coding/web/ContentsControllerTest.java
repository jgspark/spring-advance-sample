package com.lezhin.coding.web;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.content.Contents;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.mock.DtoMock;
import com.lezhin.coding.service.ContentsService;
import com.lezhin.coding.service.dto.ContentsInfo;
import com.lezhin.coding.service.dto.SelectContentsStoreDTO;
import com.lezhin.coding.service.dto.TopContents;
import com.lezhin.coding.service.dto.UpdatedContentsStoreDTO;
import com.lezhin.coding.utils.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContentsController.class)
class ContentsControllerTest {

  private MockMvc mockMvc;

  @MockBean private ContentsService contentsService;

  @BeforeEach
  void init() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(new ContentsController(contentsService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
  }

  @Test
  @DisplayName("좋아요가 가장 많은 작품 3개 API")
  void getTopContents() throws Exception {

    List<TopContents> mocks = ContentsMock.createdTopContentsList();

    BDDMockito.given(contentsService.getTopContents(any())).willReturn(mocks);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/top-contents")
                    .param("type", EvaluationType.GOOD.name())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(contentsService).should().getTopContents(any());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]['id']").value(mocks.get(0).getId()))
        .andExpect(jsonPath("$[0]['name']").value(mocks.get(0).getName()))
        .andExpect(jsonPath("$[0]['author']").value(mocks.get(0).getAuthor()))
        .andExpect(jsonPath("$[0]['type']").value(mocks.get(0).getType().name()))
        .andExpect(jsonPath("$[0]['coin']").value(mocks.get(0).getCoin()))
        .andExpect(jsonPath("$[0]['openDate']").value(mocks.get(0).getOpenDate()))
        .andExpect(jsonPath("$[0]['sum']").value(mocks.get(0).getSum()));
  }

  @Test
  @DisplayName("특정 작품을 유료, 무료로 변경 할 수 있는 API")
  void patchContents() throws Exception {

    final UpdatedContentsStoreDTO dto = new UpdatedContentsStoreDTO(ContentsType.PAGAR, 100);

    Optional<Contents> mockOptional = Optional.of(ContentsMock.createdMock());

    BDDMockito.given(contentsService.updatedTypeAndCoin(any(), any())).willReturn(mockOptional);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.patch("/contents/" + 1)
                    .content(JsonUtil.convertObjectToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(contentsService).should().updatedTypeAndCoin(any(), any());

    Contents mock = mockOptional.get();

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['name']").value(mock.getName()))
        .andExpect(jsonPath("$['author']").value(mock.getAuthor()))
        .andExpect(jsonPath("$['type']").value(mock.getType().name()))
        .andExpect(jsonPath("$['adultType']").value(mock.getAdultType().name()))
        .andExpect(jsonPath("$['coin']").value(mock.getCoin()))
        .andExpect(jsonPath("$['openDate']").value(mock.getOpenDate()));
  }

  @Test
  @DisplayName("작품 전체 조회 API")
  void getContents() throws Exception {

    SelectContentsStoreDTO dto = DtoMock.getSelectContentsStoreDTO();

    Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

    BDDMockito.given(contentsService.getContents(any())).willReturn(mocks);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/contents")
                    .param("type", dto.getType().name())
                    .param("page", dto.getPage().toString())
                    .param("size", dto.getSize().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(contentsService).should().getContents(any());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['content'][0]['id']").value(mocks.getContent().get(0).getId()))
        .andExpect(jsonPath("$['content'][0]['name']").value(mocks.getContent().get(0).getName()))
        .andExpect(
            jsonPath("$['content'][0]['type']").value(mocks.getContent().get(0).getType().name()))
        .andExpect(
            jsonPath("$['content'][0]['openDate']").value(mocks.getContent().get(0).getOpenDate()))
        .andExpect(
            jsonPath("$['content'][0]['author']").value(mocks.getContent().get(0).getAuthor()))
        .andExpect(jsonPath("$['content'][0]['coin']").value(mocks.getContent().get(0).getCoin()));
  }

  @Test
  @DisplayName("컨텐츠 하나만 조회 API")
  void getContentsOne() throws Exception {

    Optional<ContentsInfo> mockOptional = Optional.of(ContentsMock.getContentsInfo());

    BDDMockito.given(contentsService.getContentsOne(any())).willReturn(mockOptional);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/contents/" + 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(contentsService).should().getContentsOne(any());

    ContentsInfo mock = mockOptional.get();

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['name']").value(mock.getName()))
        .andExpect(jsonPath("$['author']").value(mock.getAuthor()))
        .andExpect(jsonPath("$['type']").value(mock.getType().name()))
        .andExpect(jsonPath("$['openDate']").value(mock.getOpenDate()));
  }
}
