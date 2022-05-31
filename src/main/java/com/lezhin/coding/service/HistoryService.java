package com.lezhin.coding.service;

import com.lezhin.coding.service.dto.HistoryInfo;
import com.lezhin.coding.service.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface HistoryService {

  Page<HistoryInfo> getHistories(PageDTO dto);
}
