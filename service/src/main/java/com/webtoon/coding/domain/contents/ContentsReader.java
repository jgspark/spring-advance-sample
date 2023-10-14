package com.webtoon.coding.domain.contents;

import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.core.exception.NoDataException;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.common.Reader;
import com.webtoon.coding.dto.entity.PageContents;
import com.webtoon.coding.dto.view.TopContents;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsReader implements Reader<Contents>, ContentsCustomReader {

    private final ContentsRepository contentsRepository;

    @Override
    public Contents get(Long id) {
        return contentsRepository.findById(id).orElseThrow(() -> new NoDataException(MsgType.NoContentsData));
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> type) {
        return contentsRepository.findById(id, type);
    }

    @Override
    public <T> Page<T> getAll(PageContents dto, Class<T> type) {

        PageRequest pageRequest = dto.getPageRequest();

        if (Objects.isNull(dto.getType())) {
            return contentsRepository.findAllProjectedBy(pageRequest, type);
        }

        return contentsRepository.findByType(pageRequest, dto.getType(), type);
    }

    @Override
    public List<TopContents> getTopAllByType(int top, Evaluation type) {
        return contentsRepository.findTopByLimitAndType(top, type);
    }
}
