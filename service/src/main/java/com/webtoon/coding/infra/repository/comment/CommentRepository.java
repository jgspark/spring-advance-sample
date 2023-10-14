package com.webtoon.coding.infra.repository.comment;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.comment.CommentKey;
import com.webtoon.coding.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentKey> {

    List<Comment> findByUser(User user);

    void deleteById_UserId(Long userId);

}
