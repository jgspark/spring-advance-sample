package com.webtoon.coding.domain.history;

import com.webtoon.coding.dto.entity.PageHistoryUser;
import com.webtoon.coding.dto.view.HistoryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryCustomReader {

    <T> Page<T> getAll(Pageable pageable, Class<T> type);

    Page<HistoryUser> getHistories(PageHistoryUser dto);

}
