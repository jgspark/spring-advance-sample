package com.webtoon.coding.core.util;

import com.webtoon.coding.core.exception.DateUtilException;
import com.webtoon.coding.core.exception.MsgType;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {
    }

    public static Date plus(Date date, int plusNum) {

        if (plusNum < 1) throw new DateUtilException(MsgType.PlusNumberException);

        return getTime(date, plusNum);
    }

    public static Date minus(Date date, int minusNum) {

        if (minusNum > -1) throw new DateUtilException(MsgType.MinusNumberException);

        return getTime(date, minusNum);
    }

    private static Date getTime(Date date, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, number);
        return calendar.getTime();
    }
}
