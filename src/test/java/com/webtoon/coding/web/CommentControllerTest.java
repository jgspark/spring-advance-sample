package com.webtoon.coding.web;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.mock.CommentMock;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.UserMock;
import com.webtoon.coding.service.comment.CommentService;
import com.webtoon.coding.dto.request.ContentsCommentRequest;
import com.webtoon.coding.util.JsonUtil;
import com.webtoon.coding.web.comment.CommentController;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

  private MockMvc mockMvc;

  @MockBean private CommentService commentService;

  @BeforeEach
  void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new CommentController(commentService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
  }

  @Test
  @DisplayName("특정 사용자가 해댱 작품에 대한 평가를 할 수 있는 API 테스트 케이스")
  void writeComment() throws Exception {

    Comment mock = CommentMock.createdMock(UserMock.createdMock(), ContentsMock.createdMock());

//    BDDMockito.given(commentService.createdComment(any())).willReturn(mock);

    ContentsCommentRequest dto = CommentMock.createdStoreDTO();

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/comment")
                    .content(JsonUtil.convertObjectToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

//    BDDMockito.then(commentService).should().createdComment(any());

    action
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$['id']['userId']").value(mock.getId().getUserId()))
        .andExpect(jsonPath("$['id']['contentsId']").value(mock.getId().getContentsId()))
        .andExpect(jsonPath("$['type']").value(mock.getType().name()))
        .andExpect(jsonPath("$['comment']").value(mock.getComment()));
  }
}
