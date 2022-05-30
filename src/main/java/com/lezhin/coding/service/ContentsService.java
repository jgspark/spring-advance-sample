package com.lezhin.coding.service;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.service.dto.TopContents;

import java.util.List;

public interface ContentsService {

  List<TopContents> getTopContents(EvaluationType type);
}
