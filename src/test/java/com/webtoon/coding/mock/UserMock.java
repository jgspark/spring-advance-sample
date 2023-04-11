package com.webtoon.coding.mock;

import com.webtoon.coding.domain.contents.Adult;
import com.webtoon.coding.domain.user.Gender;
import com.webtoon.coding.domain.user.User;

public class UserMock {

  private static Long DEFAULT_ID = 1L;

  private static String DEFAULT_USERNAME = "tester";

  private static String DEFAULT_USEREMAIL = "test@naver.com";

  private static Gender DEFAULT_GENDER = Gender.M;

  private static Adult DEFAULT_TYPE = Adult.ADULT;

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
