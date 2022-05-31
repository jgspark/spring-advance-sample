package com.lezhin.coding.service.dto;

import com.lezhin.coding.constants.AdultType;
import com.lezhin.coding.constants.GenderType;
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

  private GenderType gender;

  private AdultType type;

  private Date registerDate;
}
