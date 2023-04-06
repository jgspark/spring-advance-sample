package com.webtoon.coding.exception;

import lombok.Getter;

@Getter
public enum MsgType {
  EmptyParameter("S003", "empty parameter"),
  EmptyRequestBody("S002", "request body no data"),
  ServerError("S001", "server error"),
  MinusNumberException("DU002", "minus number is over number"),
  PlusNumberException("DU001", "plus number is negative"),
  CoinDataException("D001", "coin data does not meet the conditions"),
  CommentDataException("D002", "comment data is include special symbol"),
  NoUserData("D001", "user data not found"),
  NoContentsData("D002", "contents data not found");
  private final String code;

  private final String message;

  MsgType(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
