package com.webtoon.coding.repository.history;

import com.webtoon.coding.domain.content.Adult;
import com.webtoon.coding.dto.HistoryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface HistorySupport {

    Page<HistoryUser> findByCreatedDateBetweenAndContents_AdultType(Pageable pageable, Date startDate, Date endDate, Adult adult, Long count);
}
