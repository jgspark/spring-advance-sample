package com.webtoon.coding.service.contents;

import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.contents.ContentsCustomReader;
import com.webtoon.coding.dto.entity.PolicyCoin;
import com.webtoon.coding.domain.common.Reader;
import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.dto.entity.PageContents;
import com.webtoon.coding.dto.request.PageContentsRequest;
import com.webtoon.coding.dto.request.UpdatedContentsRequest;
import com.webtoon.coding.dto.view.ContentsInfo;
import com.webtoon.coding.dto.view.TopContents;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final Reader<Contents> contentReader;

    private final ContentsCustomReader contentsCustomReader;

    private final Verifier<Contents> contentsVerifier;

    @Override
    @Transactional(readOnly = true)
    public List<TopContents> getTopContents(Evaluation type) {
        return contentsCustomReader.getTopAllByType(3, type);
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
    public Page<ContentsInfo> getContents(PageContentsRequest dto) {
        return contentsCustomReader.getAll(PageContents.of(dto.getType(), dto.getPageRequest()), ContentsInfo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContentsInfo> getContentsOne(Long id) {
        return contentsCustomReader.get(id, ContentsInfo.class);
    }
}
