package com.webtoon.coding.mock;

import com.webtoon.coding.domain.content.Adult;
import com.webtoon.coding.domain.content.Policy;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.dto.TopContents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Date;
import java.util.List;

public class ContentsMock {

  private static Long DEFAULT_ID = 1L;

  private static String DEFAULT_NAME = "구원하소서";

  private static String DEFAULT_AUTHOR = "1230";

  private static Policy DEFAULT_TYPE = Policy.FREE;

  private static Adult adult = Adult.ADULT;

  private static String DEFAULT_COIN = null;

  public static Contents createdMock() {
    return Contents.builder()
        .id(DEFAULT_ID)
        .name(DEFAULT_NAME)
        .author(DEFAULT_AUTHOR)
        .type(DEFAULT_TYPE)
        .coin(DEFAULT_COIN)
        .adult(adult)
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

  public static ContentsInfo getContentsInfo() {
    return new ContentsInfoTemp(createdMock());
  }

  public static Page<ContentsInfo> getPageContentsInfo() {
    List<ContentsInfo> list = List.of(getContentsInfo());
    return new PageImpl<>(list, DtoMock.getSelectContentsStoreDTO().getPageRequest(), list.size());
  }

  static class ContentsInfoTemp implements ContentsInfo {

    private final Contents contents;

    ContentsInfoTemp(Contents contents) {
      this.contents = contents;
    }

    @Override
    public Long getId() {
      return contents.getId();
    }

    @Override
    public String getName() {
      return contents.getName();
    }

    @Override
    public String getAuthor() {
      return contents.getAuthor();
    }

    @Override
    public Policy getType() {
      return contents.getType();
    }

    @Override
    public String getCoin() {
      return contents.getCoin();
    }

    @Override
    public Date getOpenDate() {
      return contents.getOpenDate();
    }
  }
}
