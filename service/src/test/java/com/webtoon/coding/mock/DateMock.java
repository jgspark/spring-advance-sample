package com.webtoon.coding.mock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMock {

  public static Date getMockDate() {
    String dateStr = "2021년 06월 19일 21시 05분 07초";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
    try {
      return formatter.parse(dateStr);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static String changedFormatDate(Date date) {
    // 2021-06-19 21:05:07.0
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return dateFormat.format(date);
  }
}
