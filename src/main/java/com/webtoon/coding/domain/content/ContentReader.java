package com.webtoon.coding.domain.content;

import com.webtoon.coding.domain.core.Reader;
import com.webtoon.coding.exception.MsgType;
import com.webtoon.coding.exception.NoDataException;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ContentReader implements Reader<Contents> {

    private final ContentsRepository contentsRepository;

    @Override
    @Transactional(readOnly = true)
    public Contents get(Long id) {
        return contentsRepository.findById(id).orElseThrow(() -> new NoDataException(MsgType.NoContentsData));
    }
}
