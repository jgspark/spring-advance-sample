package com.webtoon.coding.web.comment;

import com.webtoon.coding.base.BaseTestController;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.dto.request.ContentsCommentRequest;
import com.webtoon.coding.mock.CommentMock;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.JsonUtil;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.service.comment.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CommentController.class)
class CommentControllerTest extends BaseTestController {

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("특정 사용자가 해댱 작품에 대한 평가를 할 수 있는 API 테스트 케이스")
    void test_write_comment_200() throws Exception {

        Comment mock = CommentMock.createdMock(UserMock.createdMock(), ContentsMock.createdMock());

        when(commentService.created(any())).thenReturn(mock);

        ContentsCommentRequest dto = CommentMock.createdStoreDTO();

        ResultActions action =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.post("/comment")
                                        .content(JsonUtil.convertObjectToJson(dto))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        verify(commentService, times(1)).created(any());

        action
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$['id']['userId']").value(mock.getId().getUserId()))
                .andExpect(jsonPath("$['id']['contentsId']").value(mock.getId().getContentsId()))
                .andExpect(jsonPath("$['type']").value(mock.getType().name()))
                .andExpect(jsonPath("$['comment']").value(mock.getComment()))
        ;
    }
}
