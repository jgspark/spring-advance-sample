package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.ContentsType;

import java.util.Date;

public interface ContentsInfo {

  Long getId();

  String getName();

  String getAuthor();

  ContentsType getType();

  String getCoin();

  Date getOpenDate();
}
