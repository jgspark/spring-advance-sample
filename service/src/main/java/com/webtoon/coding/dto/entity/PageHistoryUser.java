package com.webtoon.coding.dto.entity;

import com.webtoon.coding.domain.contents.Adult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageHistoryUser {

    private Pageable pageable;

    private Date startDate;

    private Date endDate;

    private Adult adult;

    private Long count;

    public static PageHistoryUser of(Pageable pageable, Date startDate, Date endDate, Adult adult, Long count) {
        return new PageHistoryUser(pageable, startDate, endDate, adult, count);
    }

}
