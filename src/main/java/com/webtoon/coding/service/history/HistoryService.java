package com.webtoon.coding.service.history;

import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.dto.request.PagingRequest;
import org.springframework.data.domain.Page;

public interface HistoryService {

  Page<HistoryInfo> getHistories(PagingRequest dto);

  Page<HistoryUser> getHistoriesByAdultUser(PagingRequest dto);
}
