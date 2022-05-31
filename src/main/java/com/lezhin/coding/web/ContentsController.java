package com.lezhin.coding.web;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.service.ContentsService;
import com.lezhin.coding.service.dto.ContentsInfo;
import com.lezhin.coding.service.dto.SelectContentsStoreDTO;
import com.lezhin.coding.service.dto.TopContents;
import com.lezhin.coding.service.dto.UpdatedContentsStoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContentsController {

  private final ContentsService contentsService;

  @GetMapping("top-contents")
  public ResponseEntity<List<TopContents>> getTopContents(@Valid @NotNull EvaluationType type) {

    List<TopContents> data = contentsService.getTopContents(type);

    if (data.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    return ResponseEntity.status(HttpStatus.OK).body(data);
  }

  @PatchMapping("contents/{id}")
  public ResponseEntity<Contents> patchContents(
      @PathVariable Long id, @RequestBody @Valid UpdatedContentsStoreDTO dto) {

    Optional<Contents> data = contentsService.updatedTypeAndCoin(id, dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    return ResponseEntity.ok().body(data.get());
  }

  @GetMapping("contents")
  public ResponseEntity<Page<ContentsInfo>> getContents(@Valid SelectContentsStoreDTO dto) {
    Page<ContentsInfo> data = contentsService.getContents(dto);
    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.ok(data);
  }

  @GetMapping("contents/{id}")
  public ResponseEntity<ContentsInfo> getContentsOne(@PathVariable Long id) {
    Optional<ContentsInfo> data = contentsService.getContentsOne(id);
    if (data.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    return ResponseEntity.ok(data.get());
  }
}
