package com.webtoon.coding.web.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.service.contents.ContentsService;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.dto.SelectContentsStoreDTO;
import com.webtoon.coding.dto.TopContents;
import com.webtoon.coding.dto.UpdatedContentsStoreDTO;
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
  public ResponseEntity<List<TopContents>> getTopContents(@Valid @NotNull Evaluation type) {

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
