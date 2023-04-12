package com.webtoon.coding.mock;

import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.dto.view.HistoryUser;

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
