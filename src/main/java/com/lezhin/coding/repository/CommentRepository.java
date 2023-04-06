package com.lezhin.coding.repository;

import com.lezhin.coding.domain.content.Comment;
import com.lezhin.coding.domain.support.CommentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentKey> {

  void deleteById_UserId(Long userId);
}
