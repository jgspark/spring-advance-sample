package com.webtoon.coding.service.comment;

import com.webtoon.coding.domain.comment.CommentVerifier;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.dto.model.comment.ContentsComment;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.repository.comment.CommentRepository;
import com.webtoon.coding.repository.contents.ContentsRepository;
import com.webtoon.coding.repository.user.UserRepository;
import com.webtoon.coding.dto.request.ContentsCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ContentsRepository contentsRepository;

    private final CommentVerifier verifier;

    @Override
    @Transactional
    public Comment created(ContentsCommentRequest dto) {

        Optional<User> userOptional = userRepository.findById(dto.getUserId());

        Optional<Contents> contentsOptional = contentsRepository.findById(dto.getContentsId());

        // 컨텐츠 코멘트 객체
        ContentsComment contentsComment = ContentsComment.of(userOptional, contentsOptional, dto.getComment(), dto.getType());

        // 컨텐츠 코멘트 객체는 생성을한다.
        Comment entity = contentsComment.created(verifier);

        return commentRepository.save(entity);
    }
}
