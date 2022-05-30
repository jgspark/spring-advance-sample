package com.lezhin.coding.mock;

import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.History;
import com.lezhin.coding.domain.User;

public class HistoryMock {

  public static History createdMock(User user, Contents contents) {
    return History.builder().user(user).contents(contents).build();
  }
}
