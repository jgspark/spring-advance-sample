package com.webtoon.coding.domain.comment;

import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
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

    public void write(CommentVerifier verifier) {
        verifier.verify(this);
    }

    public static Comment of(String comment, Evaluation type, User user, Contents contents) {
        return Comment.builder()
                .id(new CommentKey(user.getId(), contents.getId()))
                .comment(comment)
                .type(type)
                .user(user)
                .contents(contents)
                .build();
    }
}
