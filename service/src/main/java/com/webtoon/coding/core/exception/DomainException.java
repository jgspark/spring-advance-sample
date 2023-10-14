package com.webtoon.coding.core.exception;

public class DomainException extends BaseException {
  public DomainException(MsgType msgType) {
    super(msgType , msgType.getMessage());
  }
}
