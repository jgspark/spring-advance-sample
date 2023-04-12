package com.webtoon.coding.mock;

import com.webtoon.coding.domain.contents.Policy;
import com.webtoon.coding.dto.request.PageContentsRequest;

public class DtoMock {

  public static PageContentsRequest getSelectContentsStoreDTO() {
    return new PageContentsRequest(0, 10, Policy.FREE);
  }
}
