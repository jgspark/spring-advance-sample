package com.webtoon.coding.dto.entity;

import com.webtoon.coding.domain.contents.Adult;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageHistoryUserTest {

    @Test
    public void testOf() {

        PageRequest pagerequest = PageRequest.of(0, 10);

        Date now = new Date();

        Adult adult = Adult.NONE_ADULT;

        long count = 3L;

        PageHistoryUser entity = PageHistoryUser.of(PageRequest.of(0, 10), now, now, adult, count);

        assertEquals(pagerequest, entity.getPageable());
        assertEquals(now, entity.getStartDate());
        assertEquals(now, entity.getEndDate());
        assertEquals(adult, entity.getAdult());
        assertEquals(count, entity.getCount());
    }

}
