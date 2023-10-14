package com.webtoon.coding.core.exception;

public class BaseException extends RuntimeException {

    private MsgType msgType;

    public BaseException(MsgType msgType) {
        this.msgType = msgType;
    }

    public BaseException(MsgType msgType, String message) {
        super(message);
        this.msgType = msgType;
    }

    public BaseException(String message) {
        super(message);
    }

    public MsgType getMsgType() {
        return msgType;
    }
}
