package com.lezhin.coding.domain;

import com.lezhin.coding.domain.support.AbstractBaseDate;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
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
