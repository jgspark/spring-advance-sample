package com.webtoon.coding.dto;

import com.webtoon.coding.domain.contents.Policy;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface HistoryInfo {

  Long getId();

  @Value("#{target.user.userName}")
  String getUserName();

  @Value("#{target.contents.name}")
  String getContentsName();

  @Value("#{target.contents.type}")
  Policy getContentsType();

  Date getCreatedDate();
}
