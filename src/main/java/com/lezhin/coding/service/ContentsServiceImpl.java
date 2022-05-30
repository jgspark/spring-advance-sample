package com.lezhin.coding.service;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.repository.ContentsRepository;
import com.lezhin.coding.service.dto.TopContents;
import com.lezhin.coding.service.dto.UpdatedContentsStoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

  private final ContentsRepository contentsRepository;

  @Override
  @Transactional(readOnly = true)
  public List<TopContents> getTopContents(EvaluationType type) {
    return contentsRepository.findTopByLimitAndType(3, type);
  }

  @Override
  @Transactional
  public Optional<Contents> updatedTypeAndCoin(Long id, UpdatedContentsStoreDTO dto) {

    Optional<Contents> updatedEntity = contentsRepository.findById(id);

    if (updatedEntity.isPresent()) {

      Contents entity = updatedEntity.get();

      if (ContentsType.FREE.equals(dto.getType())) {
        entity.changedFreeType();
      } else {
        entity.changedPagar();
        entity.setCoin(dto.getCoin().toString());
      }
    }

    return updatedEntity;
  }
}
