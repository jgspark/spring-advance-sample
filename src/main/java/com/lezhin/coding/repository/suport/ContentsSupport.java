package com.lezhin.coding.repository.suport;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.service.dto.TopContents;

import java.util.List;

public interface ContentsSupport {

    List<TopContents> findTopByLimitAndType(Integer limit, EvaluationType type);
}
