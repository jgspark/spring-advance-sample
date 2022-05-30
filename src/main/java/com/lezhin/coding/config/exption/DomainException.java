package com.lezhin.coding.config.exption;

import com.lezhin.coding.constants.MsgType;

public class DomainException extends BaseException {
  public DomainException(MsgType msgType) {
    super(msgType);
  }
}
