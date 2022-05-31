package com.lezhin.coding.web;

import com.lezhin.coding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @DeleteMapping("/user/{id}")
  public void removeUser(@PathVariable Long id) {
    userService.removeUser(id);
  }
}
