package com.webtoon.coding.domain.comment;

import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.user.User;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "contents_id"}))
public class Comment {

    @EmbeddedId
    private CommentKey id;

    @Column(nullable = false)
    private Evaluation type;

    // 특수 문자 불가
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contentsId")
    @JoinColumn(name = "contents_id", insertable = false, updatable = false)
    private Contents contents;

    public void write(Verifier<Comment> verifier) {
        verifier.verify(this);
    }

    public static Comment of(String comment, Evaluation type, User user, Contents contents) {
        CommentKey id = CommentKey.of(user.getId(), contents.getId());
        return new Comment(id, type, comment, user, contents);
    }
}
