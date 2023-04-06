package com.webtoon.coding.dto.model.comment;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.comment.CommentKey;
import com.webtoon.coding.domain.comment.CommentVerifier;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;

import java.util.Optional;

public class ContentsComment {

    private Optional<User> userOptional;

    private Optional<Contents> contentsOptional;

    private final String comment;

    private final Evaluation type;

    public static ContentsComment of(Optional<User> userOptional, Optional<Contents> contentsOptional, String comment, Evaluation type) {
        return new ContentsComment(userOptional, contentsOptional, comment, type);
    }

    private ContentsComment(Optional<User> userOptional, Optional<Contents> contentsOptional, String comment, Evaluation type) {
        this.userOptional = userOptional;
        this.contentsOptional = contentsOptional;
        this.comment = comment;
        this.type = type;
    }

    public Comment created(CommentVerifier verifier) {

        // 체크를 한다.
        verifier.verify(this);

        // 체크가 끝나면 Comment 객체에 책임 위임?
        User user = userOptional.get();

        Contents contents = contentsOptional.get();

        return Comment.builder()
                .id(new CommentKey(user.getId(), contents.getId()))
                .comment(this.comment)
                .type(this.type)
                .user(user)
                .contents(contents)
                .build();
    }

    public Optional<User> getUserOptional() {
        return userOptional;
    }

    public Optional<Contents> getContentsOptional() {
        return contentsOptional;
    }

    public String getComment() {
        return comment;
    }
}
