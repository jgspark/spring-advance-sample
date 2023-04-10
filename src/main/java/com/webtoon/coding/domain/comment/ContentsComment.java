package com.webtoon.coding.domain.comment;

import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;

/**
 * 협력 : Comment
 * 의존 : User 와 Contents 에 의존적이다.
 * 책임 : CommentVerifier (체크하라)
 * 역할 : CommentVerifier (체크하라)
 */
public class ContentsComment {

    private final String comment;

    private final Evaluation type;

    private final User user;

    private final Contents contents;

    public static ContentsComment of(
            String comment,
            Evaluation type,
            User user,
            Contents contents
    ) {
        return new ContentsComment(comment, type, user, contents);
    }

    private ContentsComment(String comment, Evaluation type, User user, Contents contents) {
        this.comment = comment;
        this.type = type;
        this.user = user;
        this.contents = contents;
    }

    public Comment created(CommentVerifier verifier) {

        verifier.verify(this);

        return Comment.builder()
                .id(new CommentKey(user.getId(), contents.getId()))
                .comment(this.comment)
                .type(this.type)
                .user(user)
                .contents(contents)
                .build();
    }

    String getComment() {
        return comment;
    }

    Evaluation getType() {
        return type;
    }
}
