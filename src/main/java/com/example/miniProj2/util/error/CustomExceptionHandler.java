package com.example.miniProj2.util.error;

import com.example.miniProj2.util.error.code.CommonError;
import com.example.miniProj2.util.error.customexception.CommonCustomException;
import com.example.miniProj2.util.error.customexception.RegisterCustomException;
import com.example.miniProj2.util.error.customexception.SearchCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(RegisterCustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionRegister(RegisterCustomException e) {
        return ErrorResponseEntity.toResponseEntityRegister(e.getAuthenticationRegistrationError(), e.getKey());
    }
    @ExceptionHandler(SearchCustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionSearch(SearchCustomException e) {
        return ErrorResponseEntity.toResponseEntitySearch(e.getAuthenticationSearchError(), e.getKey(), e.getTelcoTxId(), e.getReqTxId(), e.getCertTxId());
    }
    @ExceptionHandler(CommonCustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionCommon(CommonCustomException e) {
        return ErrorResponseEntity.toResponseEntityCommon(e.getCommonError());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionHttpMessageNotReadable() {
        return ErrorResponseEntity.toResponseEntityCommon(CommonError.REQUESTED_BODY_FORMAT_ERROR);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionHttpRequestMethodNotSupported() {
        return ErrorResponseEntity.toResponseEntityCommon(CommonError.UNSUPPORTED_HTTP_METHOD);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionMethodArgumentNotValid() {
        return ErrorResponseEntity.toResponseEntityCommon(CommonError.REQUESTED_BODY_FORMAT_ERROR);
    }
    @ExceptionHandler(SQLSyntaxErrorException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionSQLSyntaxError() {
        return ErrorResponseEntity.toResponseEntityCommon(CommonError.TEMPORARY_ERROR_REQUEST);
    }
    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExceptionIllegalState() {
        return ErrorResponseEntity.toResponseEntityCommon(CommonError.TEMPORARY_ERROR_REQUEST);
    }
}
