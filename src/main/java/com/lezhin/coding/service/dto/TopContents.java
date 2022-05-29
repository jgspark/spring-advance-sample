package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;

public interface TopContents {

  Long getId();

  String getName();

  String getAuthor();

  ContentsType getType();

  String getCoin();

  String getOpenDate();
}
