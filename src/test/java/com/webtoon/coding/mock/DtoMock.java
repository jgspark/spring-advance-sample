package com.webtoon.coding.mock;

import com.webtoon.coding.domain.content.Policy;
import com.webtoon.coding.dto.SelectContentsStoreDTO;

public class DtoMock {

  public static SelectContentsStoreDTO getSelectContentsStoreDTO() {
    return new SelectContentsStoreDTO(0, 10, Policy.FREE);
  }
}
