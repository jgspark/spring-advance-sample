package com.lezhin.coding.mock;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.domain.History;
import com.lezhin.coding.domain.User;
import com.lezhin.coding.service.dto.HistoryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public class HistoryMock {

  private static final Long id = 1L;

  public static History createdMock(User user, Contents contents) {
    return History.builder().id(id).user(user).contents(contents).build();
  }

  public static Page<HistoryInfo> createdPageList(User user, Contents contents) {

    List<HistoryInfo> list = List.of(new HistoryInfoTemp(createdMock(user, contents)));

    PageRequest pageable = PageRequest.of(0, 10);

    return new PageImpl<>(list, pageable, 1);
  }

  static class HistoryInfoTemp implements HistoryInfo {

    private final History history;

    HistoryInfoTemp(History history) {
      this.history = history;
    }

    @Override
    public Long getId() {
      return history.getId();
    }

    @Override
    public String getUserName() {
      return history.getUser().getUserName();
    }

    @Override
    public String getContentsName() {
      return history.getContents().getName();
    }

    @Override
    public ContentsType getContentsType() {
      return history.getContents().getType();
    }

    @Override
    public Date getCreatedDate() {
      return history.getCreatedDate();
    }
  }
}
