package com.lezhin.coding.mock;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.domain.Contents;

public class ContentsMock {

  private static Long DEFAULT_ID = 1L;

  private static String DEFAULT_NAME = "구원하소서";

  private static String DEFAULT_AUTHOR = "1230";

  private static ContentsType DEFAULT_TYPE = ContentsType.FREE;

  private static String DEFAULT_COIN = null;

  private static String DEFAULT_OPENDATE = "2021/01/11";

  public static Contents createdMock() {
    return Contents.builder()
        .id(DEFAULT_ID)
        .name(DEFAULT_NAME)
        .author(DEFAULT_AUTHOR)
        .type(DEFAULT_TYPE)
        .coin(DEFAULT_COIN)
        .openDate(DEFAULT_OPENDATE)
        .build();
  }
}
