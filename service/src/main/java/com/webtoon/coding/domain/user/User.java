package com.webtoon.coding.domain.user;

import com.webtoon.coding.domain.contents.Adult;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Entity(name = "USERS")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저이름
    private String userName;

    // 유저메일
    private String userEmail;

    // 성별타입
    private Gender gender;

    // 성인유무 (유형)
    private Adult type;

    // 서비스 등록일
    @CreatedDate
    private Date registerDate;

}
