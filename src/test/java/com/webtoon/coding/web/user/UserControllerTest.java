package com.webtoon.coding.web.user;

import com.webtoon.coding.base.BaseTestController;
import com.webtoon.coding.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseTestController {

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("유저 삭제 API - 200")
    void test_secession_user_200() throws Exception {

        doNothing().when(userService).removeUser(any());

        ResultActions action =
                mockMvc
                        .perform(
                                post("/user/secession")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        verify(userService , times(1)).removeUser(any());

        action.andExpect(status().isOk());
    }
}
