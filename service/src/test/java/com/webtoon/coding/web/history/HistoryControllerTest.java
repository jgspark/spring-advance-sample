package com.webtoon.coding.web.history;

import com.webtoon.coding.base.BaseTestController;
import com.webtoon.coding.dto.request.PagingRequest;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.HistoryMock;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.service.history.HistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryController.class)
class HistoryControllerTest extends BaseTestController {

    @MockBean
    private HistoryService historyService;

    @Test
    @DisplayName("작품 별 히스토리 조회 API - 200")
    public void test_get_histories_200() throws Exception {

        Page<HistoryInfo> mocks =
                HistoryMock.createdPageList(UserMock.createdMock(), ContentsMock.createdMock());

        when(historyService.getHistories(any())).thenReturn(mocks);

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

        verify(historyService, times(1)).getHistories(any());

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
    @DisplayName("작품 별 히스토리 조회 API - 204")
    public void test_get_histories_204() throws Exception {

        when(historyService.getHistories(any())).thenReturn(Page.empty());

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

        verify(historyService, times(1)).getHistories(any());

        action
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("최근 1주일간 가입자 중 성인 작품 조회 3개 이상인 API - 200")
    public void test_get_histories_by_adult_user_200() throws Exception {

        PagingRequest pagingRequest = new PagingRequest(0, 10);

        Page<HistoryUser> mocks =
                HistoryMock.createPageHistoryUser(UserMock.createdMock(), ContentsMock.createdMock());

        when(historyService.getHistoriesByAdultUser(any())).thenReturn(mocks);

        ResultActions action =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.get("/histories/adult-users")
                                        .param("page", pagingRequest.getPage().toString())
                                        .param("size", pagingRequest.getSize().toString())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        verify(historyService, times(1)).getHistoriesByAdultUser(any());

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
                        jsonPath("$['content'][0]['type']").value(mocks.getContent().get(0).getType().name()));
        // FIXME : 고칠것
//                    .andExpect(
//                            jsonPath("$['content'][0]['registerDate']")
//                                    .value(mocks.getContent().get(0).getRegisterDate()));
    }

    @Test
    @DisplayName("최근 1주일간 가입자 중 성인 작품 조회 3개 이상인 API - 204")
    public void test_get_histories_by_adult_user_204() throws Exception {

        PagingRequest pagingRequest = new PagingRequest(0, 10);

        when(historyService.getHistoriesByAdultUser(any())).thenReturn(Page.empty());

        ResultActions action =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.get("/histories/adult-users")
                                        .param("page", pagingRequest.getPage().toString())
                                        .param("size", pagingRequest.getSize().toString())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        verify(historyService, times(1)).getHistoriesByAdultUser(any());

        action.andExpect(status().is2xxSuccessful());
    }
}
