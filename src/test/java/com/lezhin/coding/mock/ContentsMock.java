package com.lezhin.coding.mock;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.service.dto.TopContents;

import java.util.List;

public class ContentsMock {

  private static Long DEFAULT_ID = 1L;

  private static String DEFAULT_NAME = "구원하소서";

  private static String DEFAULT_AUTHOR = "1230";

  private static ContentsType DEFAULT_TYPE = ContentsType.FREE;

  private static String DEFAULT_COIN = null;

  public static Contents createdMock() {
    return Contents.builder()
        .id(DEFAULT_ID)
        .name(DEFAULT_NAME)
        .author(DEFAULT_AUTHOR)
        .type(DEFAULT_TYPE)
        .coin(DEFAULT_COIN)
        .openDate(DateMock.getMockDate())
        .build();
  }

  public static TopContents createdTopContents() {
    Contents mock = createdMock();
    return new TopContents(
        mock.getId(),
        mock.getName(),
        mock.getAuthor(),
        mock.getType(),
        mock.getCoin(),
        mock.getOpenDate(),
        1);
  }

  public static List<TopContents> createdTopContentsList() {
    return List.of(createdTopContents());
  }
}
