package com.lezhin.coding.repository;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.domain.support.CommentKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentKey> {

  @EntityGraph(attributePaths = {"contents"})
  List<Comment> findTop3ByType(EvaluationType type);
}
