package com.lezhin.coding.service;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.service.dto.ContentsInfo;
import com.lezhin.coding.service.dto.SelectContentsStoreDTO;
import com.lezhin.coding.service.dto.TopContents;
import com.lezhin.coding.service.dto.UpdatedContentsStoreDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContentsService {

  List<TopContents> getTopContents(EvaluationType type);

  Optional<Contents> updatedTypeAndCoin(Long id, UpdatedContentsStoreDTO dto);

  Page<ContentsInfo> getContents(SelectContentsStoreDTO dto);
}
