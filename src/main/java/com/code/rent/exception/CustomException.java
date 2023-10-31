package com.code.rent.exception;

import com.code.rent.constants.CodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author HeXin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
    public Integer code;

    public CustomException(Integer code) {
        this.code = code;
    }

    public CustomException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public CustomException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public CustomException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public CustomException() {
        this.code = CodeEnum.SYSTEM_ERROR.getCode();
    }

    public CustomException(String message) {
        super(message);
        this.code = CodeEnum.SYSTEM_ERROR.getCode();
    }

    public CustomException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }

    public CustomException(Throwable cause) {
        super(cause);
        this.code = 502;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = 502;
    }
}