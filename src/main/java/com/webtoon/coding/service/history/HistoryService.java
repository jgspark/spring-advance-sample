package com.webtoon.coding.service.history;

import com.webtoon.coding.dto.HistoryInfo;
import com.webtoon.coding.dto.HistoryUser;
import com.webtoon.coding.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface HistoryService {

  Page<HistoryInfo> getHistories(PageDTO dto);

  Page<HistoryUser> getHistoriesByAdultUser(PageDTO dto);
}
