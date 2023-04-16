package com.webtoon.coding.web.history;

import com.webtoon.coding.core.exception.RestExceptionHandler;
import com.webtoon.coding.dto.request.PagingRequest;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.HistoryMock;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.service.history.HistoryService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @BeforeEach
    void init() {
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(new HistoryController(historyService))
                        .setControllerAdvice(new RestExceptionHandler())
                        .addFilter(new CharacterEncodingFilter("UTF-8", true))
                        .build();
    }

    @Test
    @DisplayName("작품별 조회 API")
    void getHistories() throws Exception {

        Page<HistoryInfo> mocks =
                HistoryMock.createdPageList(UserMock.createdMock(), ContentsMock.createdMock());

        BDDMockito.given(historyService.getHistories(any())).willReturn(mocks);

        PagingRequest pagingRequest = new PagingRequest(0, 10);

        ResultActions action =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.get("/histories")
                                        .param("page", pagingRequest.getPage().toString())
                                        .param("size", pagingRequest.getSize().toString())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        BDDMockito.then(historyService).should().getHistories(any());

        action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['content'][0]['id']").value(mocks.getContent().get(0).getId()))
                .andExpect(
                        jsonPath("$['content'][0]['userName']").value(mocks.getContent().get(0).getUserName()))
                .andExpect(
                        jsonPath("$['content'][0]['contentsName']")
                                .value(mocks.getContent().get(0).getContentsName()))
                .andExpect(
                        jsonPath("$['content'][0]['contentsType']")
                                .value(mocks.getContent().get(0).getContentsType().name()))
                .andExpect(
                        jsonPath("$['content'][0]['createdDate']")
                                .value(mocks.getContent().get(0).getCreatedDate()));
    }

    @Test
    @DisplayName("최근 1주일간 가입자 중 성인 작품 조회 3개 이상인 API")
    void getHistoriesByAdultUser() throws Exception {

        PagingRequest pagingRequest = new PagingRequest(0, 10);

        Page<HistoryUser> mocks =
                HistoryMock.createPageHistoryUser(UserMock.createdMock(), ContentsMock.createdMock());

        BDDMockito.given(historyService.getHistoriesByAdultUser(any())).willReturn(mocks);

        ResultActions action =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.get("/histories/adult-users")
                                        .param("page", pagingRequest.getPage().toString())
                                        .param("size", pagingRequest.getSize().toString())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        BDDMockito.then(historyService).should().getHistoriesByAdultUser(any());

        action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['content'][0]['id']").value(mocks.getContent().get(0).getId()))
                .andExpect(
                        jsonPath("$['content'][0]['userName']").value(mocks.getContent().get(0).getUserName()))
                .andExpect(
                        jsonPath("$['content'][0]['userEmail']")
                                .value(mocks.getContent().get(0).getUserEmail()))
                .andExpect(
                        jsonPath("$['content'][0]['gender']")
                                .value(mocks.getContent().get(0).getGender().name()))
                .andExpect(
                        jsonPath("$['content'][0]['type']").value(mocks.getContent().get(0).getType().name()))
                .andExpect(
                        jsonPath("$['content'][0]['registerDate']")
                                .value(mocks.getContent().get(0).getRegisterDate()));
    }
}
