package com.webtoon.coding.exception;

import com.webtoon.coding.dto.MsgDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(Exception.class)
  public MsgDTO handler(Exception e) {
    MsgType msgType = MsgType.ServerError;
    e.printStackTrace();
    return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
  }

  @ExceptionHandler(BaseException.class)
  public MsgDTO handler(BaseException e) {
    MsgType msgType = e.getMsgType();
    e.printStackTrace();
    return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public MsgDTO handler(HttpMessageNotReadableException e) {
    MsgType msgType = MsgType.EmptyRequestBody;
    e.printStackTrace();
    return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public MsgDTO handler(BindException e) {
    MsgType msgType = MsgType.EmptyRequestBody;
    e.printStackTrace();
    return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
  }
}
