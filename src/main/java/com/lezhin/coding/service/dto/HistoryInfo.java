package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface HistoryInfo {

  Long getId();

  @Value("#{target.user.userName}")
  String getUserName();

  @Value("#{target.contents.name}")
  String getContentsName();

  @Value("#{target.contents.type}")
  ContentsType getContentsType();

  Date getCreatedDate();
}
