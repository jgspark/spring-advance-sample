package com.webtoon.coding.domain.history;

import com.webtoon.coding.domain.common.Writer;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HistoryWriter implements Writer<History> {

    private final HistoryRepository historyRepository;

    @Override
    @Transactional
    public History save(History history) {
        return historyRepository.save(history);
    }
}
