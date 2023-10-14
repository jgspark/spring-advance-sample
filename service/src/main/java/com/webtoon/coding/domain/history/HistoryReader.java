package com.webtoon.coding.domain.history;

import com.webtoon.coding.dto.entity.PageHistoryUser;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryReader implements HistoryCustomReader {

    private final HistoryRepository historyRepository;

    @Override
    public <T> Page<T> getAll(Pageable pageable, Class<T> type) {

        if (Objects.isNull(type)) {
            throw new RuntimeException("type is not null");
        }

        return historyRepository.findAllProjectedBy(pageable, type);
    }

    @Override
    public Page<HistoryUser> getHistories(PageHistoryUser dto) {
        return historyRepository.findByCreatedDateBetweenAndContents_AdultType(dto.getPageable(), dto.getStartDate(),
                dto.getEndDate(), dto.getAdult(), dto.getCount());
    }

}
