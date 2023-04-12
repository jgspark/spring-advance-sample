package com.webtoon.coding.domain.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.dto.layer.PageContents;

import java.util.List;
import java.util.Optional;

import com.webtoon.coding.dto.view.TopContents;
import org.springframework.data.domain.Page;

public interface ContentsCustomReader {
    <T> Optional<T> get(Long id, Class<T> type);

    <T> Page<T> getAll(PageContents dto, Class<T> type);

    List<TopContents> getTopAllByType(int top, Evaluation type);
}
