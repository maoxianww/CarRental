package com.code.rent.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.code.rent.common.Result;
import com.code.rent.constants.CodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author HeXin
 */
@Slf4j
@RestControllerAdvice
public class AllExceptionHandler {

    /**
      * 未登录异常
      */
    @ExceptionHandler(NotLoginException.class)
    public Result handleNotException(NotLoginException ex){
        log.error("异常："+ex);
        return Result.fail().message("登录信息失效，请重新登录！").code(250);
    }

    /**
      * 处理当前用户没有权限异常
      */
    @ExceptionHandler(NotPermissionException.class)
    public Result handleNotPermissionException(NotPermissionException ex) {
        log.error("异常："+ex);
        return Result.fail(CodeEnum.NOT_AUTHORITY);
    }
    /**
      * 服务器异常
      */
    @ExceptionHandler(RuntimeException.class)
    public Result deException(RuntimeException ex){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        log.error("端口" + request.getServletPath() + "发生异常:", ex);
        log.error("发生异常:", ex);
        return Result.fail().message(CodeEnum.SYSTEM_REPAIR.getMsg()+"异常信息："+ ex.getMessage()).code(CodeEnum.SYSTEM_REPAIR.getCode());
    }
}
