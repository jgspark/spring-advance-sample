package com.webtoon.coding.exception;

import com.webtoon.coding.dto.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public MsgDTO handler(Exception e) {
        MsgType msgType = MsgType.ServerError;
        log.error(e.getMessage(), e.getCause(), e);
        return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
    }

    @ExceptionHandler(BaseException.class)
    public MsgDTO handler(BaseException e) {
        MsgType msgType = e.getMsgType();
        log.error(e.getMessage(), e.getCause(), e);
        return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public MsgDTO handler(HttpMessageNotReadableException e) {
        MsgType msgType = MsgType.EmptyRequestBody;
        log.error(e.getMessage(), e.getCause(), e);
        return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public MsgDTO handler(BindException e) {
        MsgType msgType = MsgType.EmptyRequestBody;
        log.error(e.getMessage(), e.getCause(), e);
        return MsgDTO.builder().code(msgType.getCode()).message(msgType.getMessage()).build();
    }
}
