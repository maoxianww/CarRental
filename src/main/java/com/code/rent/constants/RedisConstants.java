package com.code.rent.constants;

import java.util.concurrent.TimeUnit;

public enum RedisConstants {
    ;
    /**
     * 键
     */
    private final String key;
    /**
     * 过期时间
     */
    private final Long ttl;

    /**
      * 时间单位
      */
    private final TimeUnit timeUnit;

    RedisConstants(String key, Long ttl, TimeUnit timeUnit) {
        this.key = key;
        this.ttl = ttl;
        this.timeUnit = timeUnit;
    }

    public String getKey() {
        return key;
    }

    public Long getTtl() {
        return ttl;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
