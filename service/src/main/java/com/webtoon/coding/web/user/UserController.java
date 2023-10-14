package com.webtoon.coding.web.user;

import com.webtoon.coding.core.resolver.UserIdentifier;
import com.webtoon.coding.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/secession")
    public void secession(UserIdentifier identifier) {
        userService.removeUser(identifier.userId());
    }

}
