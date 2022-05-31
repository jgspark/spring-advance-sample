package com.lezhin.coding.mock;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.service.dto.SelectContentsStoreDTO;

public class DtoMock {

  public static SelectContentsStoreDTO getSelectContentsStoreDTO() {
    return new SelectContentsStoreDTO(0, 10, ContentsType.FREE);
  }
}
