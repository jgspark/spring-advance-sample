package com.webtoon.coding.domain.history;

import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.domain.history.AbstractBaseDate;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(
    of = {"id"},
    callSuper = true)
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
}
