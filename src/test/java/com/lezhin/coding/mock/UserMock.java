package com.lezhin.coding.mock;

import com.lezhin.coding.constants.AdultType;
import com.lezhin.coding.constants.GenderType;
import com.lezhin.coding.domain.user.User;

public class UserMock {

  private static Long DEFAULT_ID = 1L;

  private static String DEFAULT_USERNAME = "tester";

  private static String DEFAULT_USEREMAIL = "test@naver.com";

  private static GenderType DEFAULT_GENDER = GenderType.M;

  private static AdultType DEFAULT_TYPE = AdultType.ADULT;

  public static User createdMock() {
    return User.builder()
        .id(DEFAULT_ID)
        .userName(DEFAULT_USERNAME)
        .userEmail(DEFAULT_USEREMAIL)
        .gender(DEFAULT_GENDER)
        .type(DEFAULT_TYPE)
        .registerDate(DateMock.getMockDate())
        .build();
  }
}
