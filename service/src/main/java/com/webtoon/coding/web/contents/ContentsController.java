package com.webtoon.coding.web.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.dto.request.PageContentsRequest;
import com.webtoon.coding.dto.request.UpdatedContentsRequest;
import com.webtoon.coding.dto.view.ContentsInfo;
import com.webtoon.coding.dto.view.TopContents;
import com.webtoon.coding.service.contents.ContentsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;

    @GetMapping("top-contents")
    public ResponseEntity<List<TopContents>> getTopContents(@Valid @NotNull Evaluation type) {
        List<TopContents> data = contentsService.getTopContents(type);
        return data.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PatchMapping("contents/{id}")
    public ResponseEntity<Contents> patchContents(@PathVariable Long id,
            @RequestBody @Valid UpdatedContentsRequest request) {

        Optional<Contents> data = contentsService.updatedTypeAndCoin(id, request);

        return data.map(contents -> ResponseEntity.ok().body(contents))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }

    @GetMapping("contents")
    public ResponseEntity<Page<ContentsInfo>> getContents(@Valid PageContentsRequest dto) {
        Page<ContentsInfo> data = contentsService.getContents(dto);
        return data.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(data);
    }

    @GetMapping("contents/{id}")
    public ResponseEntity<ContentsInfo> getContentsOne(@PathVariable Long id) {
        Optional<ContentsInfo> data = contentsService.getContentsOne(id);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

}
