package com.code.rent.constants;

/**
 * @author HeXin
 */

public enum CodeEnum {
    /**
      * 成功标志
      *
      */
    SUCCESS(200, "成功！"),

    /**
      * 参数异常
      *
      */
    PARAMETER_ERROR(400, "参数异常！"),

    /**
      * 未登录
      *
      */
    NOT_LOGIN(300, "未登陆，请登陆后再进行操作！"),

    /**
      * 登录过期
      *
      */
    LOGIN_EXPIRED(305, "登录已过期，请重新登陆！"),

    /**
      * 系统维护
      *
      */
    SYSTEM_REPAIR(501, "系统维护中，请稍后！"),

    /**
      * 服务异常
      *
      */
    FAIL(500, "服务异常！"),

    /**
      * 用户不存在
      */
    USER_NOT_FOUND(255,"用户不存在"),

    /**
      * 密码错误
      */
    PASSWORD_ERROR(290,"密码错误"),
    /**
      * 系统异常
      */
    SYSTEM_ERROR(502,"服务器异常！"),
    /**
      * 没有权限
      *
      */
    NOT_AUTHORITY(403,"没有操作权限！");

    private final int code;
    private final String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
