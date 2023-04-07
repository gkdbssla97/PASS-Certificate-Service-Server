package com.example.miniProj2.util.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthenticationSearchError {
    MISSING_REQUIRED_ITEM(4101, "필수항목 %s이 누락되었습니다.", "PACPR", HttpStatus.BAD_REQUEST,"%s", "%s", "%s"),
    INVALID_VALUE(4102, "%s이 유효하지 않습니다.", "PACPR", HttpStatus.BAD_REQUEST,"%s", "%s", "%s"),
    OUT_OF_DATE_OR_NO_MATCHING_DATA(4103, "인증 요청 유효기간이 만료되었거나 일치하는 데이터가 없습니다.", "PACPR", HttpStatus.BAD_REQUEST),
    TEMPORARY_ERROR(4199, "일시적인 오류가 발생했습니다. 잠시 후 다시 요청해주세요.", "PACPR", HttpStatus.INTERNAL_SERVER_ERROR,
    "%s", "%s", "%s");

    private final Integer errorCd;
    private final String errorMessage;
    private final String errorPointCd;
    private final HttpStatus httpStatus;

    private String telcoTxId;
    private String reqTxId;
    private String certTxId;

    AuthenticationSearchError(Integer errorCd, String errorMessage, String errorPointCd, HttpStatus httpStatus) {
        this.errorCd = errorCd;
        this.errorMessage = errorMessage;
        this.errorPointCd = errorPointCd;
        this.httpStatus = httpStatus;
    }
}
