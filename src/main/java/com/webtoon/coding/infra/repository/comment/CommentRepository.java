package com.webtoon.coding.infra.repository.comment;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.comment.CommentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentKey> {

  void deleteById_UserId(Long userId);
}
