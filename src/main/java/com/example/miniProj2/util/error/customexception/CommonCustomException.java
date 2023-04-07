package com.example.miniProj2.util.error.customexception;

import com.example.miniProj2.util.error.code.CommonError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommonCustomException extends RuntimeException{
    private CommonError commonError;
}
