package com.lezhin.coding.web;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.service.ContentsService;
import com.lezhin.coding.service.dto.TopContents;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentsController {

  private final ContentsService contentsService;

  @GetMapping("/top-contents")
  public ResponseEntity<List<TopContents>> getTopContents(EvaluationType type) {

    List<TopContents> data = contentsService.getTopContents(type);

    if (data.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(data);

    return ResponseEntity.status(HttpStatus.OK).body(data);
  }
}
