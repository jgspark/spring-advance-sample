package com.lezhin.coding.web;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.service.ContentsService;
import com.lezhin.coding.service.dto.TopContents;
import com.lezhin.coding.service.dto.UpdatedContentsStoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContentsController {

  private final ContentsService contentsService;

  @GetMapping("/top-contents")
  public ResponseEntity<List<TopContents>> getTopContents(EvaluationType type) {

    List<TopContents> data = contentsService.getTopContents(type);

    if (data.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    return ResponseEntity.status(HttpStatus.OK).body(data);
  }

  @PatchMapping("contents/{id}")
  public ResponseEntity<Contents> patchContents(
      @PathVariable Long id, @RequestBody UpdatedContentsStoreDTO dto) {

    Optional<Contents> data = contentsService.updatedTypeAndCoin(id, dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    return ResponseEntity.ok().body(data.get());
  }
}
