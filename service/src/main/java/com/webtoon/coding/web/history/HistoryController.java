package com.webtoon.coding.web.history;

import com.webtoon.coding.dto.request.PagingRequest;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.service.history.HistoryService;
import jakarta.validation.Valid;
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
  public ResponseEntity<Page<HistoryInfo>> getHistories(@Valid PagingRequest dto) {
    Page<HistoryInfo> data = historyService.getHistories(dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(data);
  }

  @GetMapping("histories/adult-users")
  public ResponseEntity<Page<HistoryUser>> getHistoriesByAdultUser(@Valid PagingRequest dto) {

    Page<HistoryUser> data = historyService.getHistoriesByAdultUser(dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(data);
  }
}
