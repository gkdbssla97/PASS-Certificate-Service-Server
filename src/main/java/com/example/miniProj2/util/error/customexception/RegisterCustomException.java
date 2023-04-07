package com.example.miniProj2.util.error.customexception;

import com.example.miniProj2.util.error.code.AuthenticationRegistrationError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterCustomException extends RuntimeException{
    private AuthenticationRegistrationError authenticationRegistrationError;
    private String key;

    public RegisterCustomException(AuthenticationRegistrationError authenticationRegistrationError) {
        this.authenticationRegistrationError = authenticationRegistrationError;
    }
}
