package com.lezhin.coding.mock;

import com.lezhin.coding.domain.user.User;
import com.lezhin.coding.service.dto.HistoryUser;

public class HistoryUserTemp extends HistoryUser {

  public HistoryUserTemp(User user) {
    super(
        user.getId(),
        user.getUserName(),
        user.getUserEmail(),
        user.getGender(),
        user.getType(),
            user.getRegisterDate());
  }
}
