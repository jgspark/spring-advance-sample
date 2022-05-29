package com.lezhin.coding.config.exption;

import com.lezhin.coding.constants.MsgType;

public class NoDataException extends BaseException {
  public NoDataException(MsgType msgType) {
    super(msgType);
  }
}
