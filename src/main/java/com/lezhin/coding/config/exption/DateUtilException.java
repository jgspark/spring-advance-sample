package com.lezhin.coding.config.exption;

import com.lezhin.coding.constants.MsgType;

public class DateUtilException extends BaseException{

    public DateUtilException(MsgType msgType) {
        super(msgType);
    }
}
