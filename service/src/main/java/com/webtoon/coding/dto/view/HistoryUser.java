package com.webtoon.coding.dto.view;

import com.webtoon.coding.domain.contents.Adult;
import com.webtoon.coding.domain.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryUser {

    private Long id;

    private String userName;

    private String userEmail;

    private Gender gender;

    private Adult type;

    private Date registerDate;

}
