package com.lmxdawn.example.handler;

import com.lmxdawn.example.enums.ResultEnum;
import com.lmxdawn.example.exception.JsonException;
import com.lmxdawn.example.res.BaseRes;
import com.lmxdawn.example.util.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 错误回调
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 拦截API异常
    @ExceptionHandler(value = JsonException.class)
    public BaseRes handlerJsonException(JsonException e) {
        // 返回对应的错误信息
        return ResultVOUtils.error(e.getCode(), e.getMessage());
    }

    // 拦截API异常
    @ExceptionHandler(value = RuntimeException.class)
    public BaseRes handlerRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        // 返回对应的错误信息
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
    }

}
