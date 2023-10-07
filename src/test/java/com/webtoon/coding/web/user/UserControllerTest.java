package com.webtoon.coding.web.user;

import com.webtoon.coding.base.BaseTestController;
import com.webtoon.coding.service.user.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseTestController {

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("유저 삭제 API")
    void removeUser() throws Exception {

        doNothing().when(userService).removeUser(any());

        ResultActions action =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.delete("/user/" + 1)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        verify(userService , times(1)).removeUser(any());

        action.andExpect(status().isOk());
    }
}
