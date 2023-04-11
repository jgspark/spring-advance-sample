package com.webtoon.coding.domain.contents;

import com.webtoon.coding.domain.core.Reader;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.exception.MsgType;
import com.webtoon.coding.exception.NoDataException;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContentReader implements Reader<Contents> {

    private final ContentsRepository contentsRepository;

    @Override
    @Transactional(readOnly = true)
    public Contents get(Long id) {
        return contentsRepository.findById(id).orElseThrow(() -> new NoDataException(MsgType.NoContentsData));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> Optional<T> get(Long id, Class<T> type) {
        return contentsRepository.findById(id, type);
    }
}
