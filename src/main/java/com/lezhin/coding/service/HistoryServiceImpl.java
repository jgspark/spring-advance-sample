package com.lezhin.coding.service;

import com.lezhin.coding.repository.HistoryRepository;
import com.lezhin.coding.service.dto.HistoryInfo;
import com.lezhin.coding.service.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private final HistoryRepository historyRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<HistoryInfo> getHistories(PageDTO dto) {
    return historyRepository.findAllProjectedBy(dto.getPageRequest(), HistoryInfo.class);
  }
}
