package com.example.miniProj2.util.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommonError {
    NO_PERMISSION(9000, "권한이 없습니다.", "PACPR", HttpStatus.UNAUTHORIZED),
    NO_REQUESTED_BODY(9001, "요청 Body가 없습니다.", "PACPR", HttpStatus.BAD_REQUEST),
    REQUESTED_BODY_FORMAT_ERROR(9002, "요청 Body 형식이 잘못되었습니다.", "PACPR", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_HTTP_METHOD(9003, "지원하지 않는 HTTP Method입니다.", "PACPR", HttpStatus.BAD_REQUEST),
    TEMPORARY_ERROR_INQUIRY(9004, "일시적인 오류가 발생했습니다. 담당자에게 문의하세요.", "PACPR", HttpStatus.BAD_REQUEST),
    TEMPORARY_ERROR_REQUEST(9099, "일시적인 오류가 발생했습니다. 잠시 후 다시 요청해주세요.", "PACPR", HttpStatus.INTERNAL_SERVER_ERROR);

    private final Integer errorCd;
    private final String errorMessage;
    private final String errorPointCd;
    private final HttpStatus httpStatus;
}
