package com.lezhin.coding.service;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.repository.ContentsRepository;
import com.lezhin.coding.service.dto.TopContents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

  private final ContentsRepository contentsRepository;

  @Override
  public List<TopContents> getTopContents(EvaluationType type) {
    return contentsRepository.findTopByLimitAndType(3, type);
  }
}
