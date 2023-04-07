package com.example.miniProj2.util.error.customexception;

import com.example.miniProj2.util.error.code.AuthenticationSearchError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchCustomException extends RuntimeException{
    private AuthenticationSearchError authenticationSearchError;

    private String key;
    private String telcoTxId;
    private String reqTxId;
    private String certTxId;

    public SearchCustomException(AuthenticationSearchError authenticationSearchError) {
        this.authenticationSearchError = authenticationSearchError;
    }

    public SearchCustomException(AuthenticationSearchError authenticationSearchError, String telcoTxId, String reqTxId, String certTxId) {
        this.authenticationSearchError = authenticationSearchError;
        this.telcoTxId = telcoTxId;
        this.reqTxId = reqTxId;
        this.certTxId = certTxId;
    }
}
