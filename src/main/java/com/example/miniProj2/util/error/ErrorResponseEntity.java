package com.example.miniProj2.util.error;

import com.example.miniProj2.util.error.code.AuthenticationRegistrationError;
import com.example.miniProj2.util.error.code.AuthenticationSearchError;
import com.example.miniProj2.util.error.code.CommonError;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {

    private int errorCode;
    private String message;
    private String errorPointCd;
    private HttpStatus httpStatus;
    private String telcoTxId;
    private String reqTxId;
    private String certTxId;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntityRegister(AuthenticationRegistrationError e, String key) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .errorCode(e.getErrorCd())
                        .message(String.format(e.getErrorMessage(), key))
                        .errorPointCd(e.getErrorPointCd())
                        .httpStatus(e.getHttpStatus())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponseEntity> toResponseEntitySearch(AuthenticationSearchError e, String key,
                                                                             String telcoTxId, String reqTxId, String certTxId) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .errorCode(e.getErrorCd())
                        .message(String.format(e.getErrorMessage(), key))
                        .errorPointCd(e.getErrorPointCd())
                        .httpStatus(e.getHttpStatus())
                        .telcoTxId(telcoTxId)
                        .reqTxId(reqTxId)
                        .certTxId(certTxId)
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponseEntity> toResponseEntityCommon(CommonError e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .errorCode(e.getErrorCd())
                        .message(e.getErrorMessage())
                        .errorPointCd(e.getErrorPointCd())
                        .httpStatus(e.getHttpStatus())
                        .build()
                );
    }

}
