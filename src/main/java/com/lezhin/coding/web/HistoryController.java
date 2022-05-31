package com.lezhin.coding.web;

import com.lezhin.coding.service.HistoryService;
import com.lezhin.coding.service.dto.HistoryInfo;
import com.lezhin.coding.service.dto.HistoryUser;
import com.lezhin.coding.service.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HistoryController {

  private final HistoryService historyService;

  @GetMapping("histories")
  public ResponseEntity<Page<HistoryInfo>> getHistories(PageDTO dto) {
    Page<HistoryInfo> data = historyService.getHistories(dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(data);
  }

  @GetMapping("histories/adult-users")
  public ResponseEntity<Page<HistoryUser>> getHistoriesByAdultUser(PageDTO dto) {

    Page<HistoryUser> data = historyService.getHistoriesByAdultUser(dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(data);
  }
}
