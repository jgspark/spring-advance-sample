package com.webtoon.coding.exception;

public class NoDataException extends BaseException {
  public NoDataException(MsgType msgType) {
    super(msgType);
  }
}