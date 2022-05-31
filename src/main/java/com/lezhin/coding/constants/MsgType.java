package com.lezhin.coding.constants;

public enum MsgType {
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
