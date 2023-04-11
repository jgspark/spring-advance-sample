package com.webtoon.coding.service.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.ContentReader;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.contents.ContentsVerifier;
import com.webtoon.coding.domain.contents.PolicyCoin;
import com.webtoon.coding.domain.core.Reader;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.dto.SelectContentsStoreDTO;
import com.webtoon.coding.dto.TopContents;
import com.webtoon.coding.dto.request.UpdatedContentsRequest;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final ContentsRepository contentsRepository;

    private final Reader<Contents> contentReader;

    private final ContentsVerifier contentsVerifier;

    @Override
    @Transactional(readOnly = true)
    public List<TopContents> getTopContents(Evaluation type) {
        return contentsRepository.findTopByLimitAndType(3, type);
    }

    @Override
    @Transactional
    public Optional<Contents> updatedTypeAndCoin(Long id, UpdatedContentsRequest request) {

        Contents entity = contentReader.get(id);

        entity.changeDetail(contentsVerifier, PolicyCoin.of(request.getType(), request.getCoin()));

        return Optional.of(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentsInfo> getContents(SelectContentsStoreDTO dto) {

        PageRequest pageRequest = dto.getPageRequest();

        if (Objects.isNull(dto.getType())) {
            return contentsRepository.findAllProjectedBy(pageRequest, ContentsInfo.class);
        }
        return contentsRepository.findByType(pageRequest, dto.getType(), ContentsInfo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContentsInfo> getContentsOne(Long id) {
        return contentReader.get(id, ContentsInfo.class);
    }
}
