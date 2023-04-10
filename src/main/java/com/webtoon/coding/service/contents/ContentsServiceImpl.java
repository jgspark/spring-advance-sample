package com.webtoon.coding.service.contents;

import com.webtoon.coding.domain.content.Policy;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.dto.ContentsInfo;
import com.webtoon.coding.dto.SelectContentsStoreDTO;
import com.webtoon.coding.dto.TopContents;
import com.webtoon.coding.dto.UpdatedContentsStoreDTO;
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

  @Override
  @Transactional(readOnly = true)
  public List<TopContents> getTopContents(Evaluation type) {
    return contentsRepository.findTopByLimitAndType(3, type);
  }

  @Override
  @Transactional
  public Optional<Contents> updatedTypeAndCoin(Long id, UpdatedContentsStoreDTO dto) {

    Optional<Contents> updatedEntity = contentsRepository.findById(id);

    if (updatedEntity.isPresent()) {

      Contents entity = updatedEntity.get();

      if (Policy.FREE.equals(dto.getType())) {
        entity.changedFreeType();
      } else {
        entity.changedPagar();
        entity.setCoin(dto.getCoin().toString());
      }
    }

    return updatedEntity;
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
    return contentsRepository.findById(id, ContentsInfo.class);
  }
}
