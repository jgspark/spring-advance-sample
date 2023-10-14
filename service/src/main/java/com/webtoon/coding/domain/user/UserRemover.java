package com.webtoon.coding.domain.user;

import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.core.exception.NoDataException;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.infra.repository.comment.CommentRepository;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final HistoryRepository historyRepository;

    public void remove(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new NoDataException(MsgType.NoUserData));

        List<Comment> comments = commentRepository.findByUser(user);

        if (!comments.isEmpty()) {
            commentRepository.deleteById_UserId(user.getId());
        }

        List<History> histories = historyRepository.findByUser(user);

        if (!histories.isEmpty()) {
            historyRepository.deleteByUser_Id(user.getId());
        }

        userRepository.delete(user);
    }

}
