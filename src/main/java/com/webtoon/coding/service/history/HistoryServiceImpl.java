package com.webtoon.coding.service.history;

import com.webtoon.coding.domain.contents.Adult;
import com.webtoon.coding.domain.history.HistoryCustomReader;
import com.webtoon.coding.dto.entity.PageHistoryUser;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.dto.request.PagingRequest;
import com.webtoon.coding.core.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryCustomReader historyCustomReader;

    @Override
    @Transactional(readOnly = true)
    public Page<HistoryInfo> getHistories(PagingRequest dto) {
        return historyCustomReader.getAll(dto.getPageRequest(), HistoryInfo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoryUser> getHistoriesByAdultUser(PagingRequest dto) {
        Date startDate = DateUtil.minus(new Date(), -7);
        Date endDate = DateUtil.plus(new Date(), 1);
        return historyCustomReader.getHistories(PageHistoryUser.of(dto.getPageRequest(), startDate, endDate, Adult.ADULT, 3L));
    }
}
