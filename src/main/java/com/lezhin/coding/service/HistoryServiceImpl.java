package com.lezhin.coding.service;

import com.lezhin.coding.constants.AdultType;
import com.lezhin.coding.repository.HistoryRepository;
import com.lezhin.coding.service.dto.HistoryInfo;
import com.lezhin.coding.service.dto.HistoryUser;
import com.lezhin.coding.service.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private final HistoryRepository historyRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<HistoryInfo> getHistories(PageDTO dto) {
    return historyRepository.findAllProjectedBy(dto.getPageRequest(), HistoryInfo.class);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<HistoryUser> getHistoriesByAdultUser(PageDTO dto) {

    Date startDate = new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000);

    Date endDate = new Date();

    return historyRepository.findByCreatedDateBetweenAndContents_AdultType(
        dto.getPageRequest(), startDate, endDate, AdultType.ADULT, 3L);
  }
}
