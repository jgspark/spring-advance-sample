package com.webtoon.coding.domain.history;

import com.webtoon.coding.domain.common.AbstractBaseDate;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.user.User;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = true)
public class History extends AbstractBaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;

    public static History of(User user, Contents contents) {
        return new History(user, contents);
    }

    private History(User user, Contents contents) {
        this.user = user;
        this.contents = contents;
    }
}
