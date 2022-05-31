package com.lezhin.coding.utils;

import com.lezhin.coding.config.exption.DateUtilException;
import com.lezhin.coding.constants.MsgType;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

  private DateUtil() {}

  public static Date plus(Date date, Integer plusNum) {

    if (plusNum < 1) throw new DateUtilException(MsgType.PlusNumberException);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, plusNum);
    return calendar.getTime();
  }

  public static Date minus(Date date, Integer minusNum) {

    if (minusNum > 0) throw new DateUtilException(MsgType.MinusNumberException);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, minusNum);
    return calendar.getTime();
  }
}
