package com.webtoon.coding.dto;

import com.webtoon.coding.domain.content.Policy;

import java.util.Date;

public interface ContentsInfo {

  Long getId();

  String getName();

  String getAuthor();

  Policy getType();

  String getCoin();

  Date getOpenDate();
}
