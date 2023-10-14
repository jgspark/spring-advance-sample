package com.webtoon.coding.core.exception;

public class DateUtilException extends BaseException {

    public DateUtilException(MsgType msgType) {
        super(msgType, msgType.getMessage());
    }

}
