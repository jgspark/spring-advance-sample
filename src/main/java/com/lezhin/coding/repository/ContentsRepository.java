package com.lezhin.coding.repository;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Contents;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {

  @EntityGraph(attributePaths = {"commentSet"})
  <T> List<T> findByCommentSet_Type(EvaluationType evaluationType, Class<T> type);
}
