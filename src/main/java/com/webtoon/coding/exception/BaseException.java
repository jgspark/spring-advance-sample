package com.webtoon.coding.exception;

import com.webtoon.coding.exception.MsgType;

public class BaseException extends RuntimeException {

  private final MsgType msgType;

  public BaseException(MsgType msgType) {
    this.msgType = msgType;
  }

  public MsgType getMsgType() {
    return msgType;
  }
}
