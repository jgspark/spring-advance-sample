package com.webtoon.coding.service.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.dto.SelectContentsStoreDTO;
import com.webtoon.coding.dto.TopContents;
import com.webtoon.coding.dto.UpdatedContentsStoreDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContentsService {

  List<TopContents> getTopContents(Evaluation type);

  Optional<Contents> updatedTypeAndCoin(Long id, UpdatedContentsStoreDTO dto);

  Page<ContentsInfo> getContents(SelectContentsStoreDTO dto);

  Optional<ContentsInfo> getContentsOne(Long id);
}
