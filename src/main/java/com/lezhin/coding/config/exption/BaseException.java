package com.lezhin.coding.config.exption;

import com.lezhin.coding.constants.MsgType;

public class BaseException extends RuntimeException {

  private final MsgType msgType;

  public BaseException(MsgType msgType) {
    this.msgType = msgType;
  }

  public MsgType getMsgType() {
    return msgType;
  }
}
