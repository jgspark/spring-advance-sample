package com.lezhin.coding.web;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.mock.ContentsMock;
import com.lezhin.coding.service.ContentsService;
import com.lezhin.coding.service.dto.TopContents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

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
}
