package com.zizo.carteeng.common.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorException extends RuntimeException {
    private final ErrorCode errorCode;
}