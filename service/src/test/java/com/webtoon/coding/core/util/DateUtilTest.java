package com.webtoon.coding.core.util;

import com.webtoon.coding.core.exception.DateUtilException;
import com.webtoon.coding.core.exception.MsgType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("날자 유틸 클레스에서")
class DateUtilTest {

    @Nested
    @DisplayName("날짜를 증가하는 메소드는")
    public class Plus {

        @Test
        @DisplayName("성공적으로 동작한다.")
        public void testPlusSuccess() {

            Date now = getMockDate("2023-03-13");

            Date mock = getMockDate("2023-03-14");

            Date date = DateUtil.plus(now, 1);

            assertEquals(date, mock);
        }

        @Test
        @DisplayName("실패 한다.")
        public void testPlusFail() {

            Date now = getMockDate("2023-03-14");

            DateUtilException e = assertThrows(DateUtilException.class, () -> DateUtil.plus(now, 0));

            assertEquals(e.getMsgType(), MsgType.PlusNumberException);
            assertEquals(e.getMessage(), MsgType.PlusNumberException.getMessage());
        }
    }

    @Nested
    @DisplayName("날짜를 감소하는 메소드는")
    public class Minus {

        @Test
        @DisplayName("성공적으로 동작한다.")
        public void testMinusSuccess() {

            Date now = getMockDate("2023-03-14");

            Date mock = getMockDate("2023-03-13");

            Date date = DateUtil.minus(now, -1);

            assertEquals(date, mock);
        }

        @Test
        @DisplayName("실패 한다.")
        public void testMinusFail() {

            Date now = getMockDate("2023-03-14");

            DateUtilException e = assertThrows(DateUtilException.class, () -> DateUtil.minus(now, 0));

            assertEquals(e.getMsgType(), MsgType.MinusNumberException);
            assertEquals(e.getMessage(), MsgType.MinusNumberException.getMessage());
        }

    }

    private Date getMockDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
