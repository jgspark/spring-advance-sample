package com.webtoon.coding.infra.repository.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.dto.view.TopContents;

import java.util.List;

public interface ContentsSupport {
  List<TopContents> findTopByLimitAndType(Integer limit, Evaluation type);
}
