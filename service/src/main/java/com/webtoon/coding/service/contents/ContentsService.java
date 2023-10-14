package com.webtoon.coding.service.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.dto.request.PageContentsRequest;
import com.webtoon.coding.dto.request.UpdatedContentsRequest;
import com.webtoon.coding.dto.view.ContentsInfo;
import com.webtoon.coding.dto.view.TopContents;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContentsService {

    List<TopContents> getTopContents(Evaluation type);

    Optional<Contents> updatedTypeAndCoin(Long id, UpdatedContentsRequest dto);

    Page<ContentsInfo> getContents(PageContentsRequest dto);

    Optional<ContentsInfo> getContentsOne(Long id);

}
