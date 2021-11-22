package com.lmxdawn.example.util;

import com.lmxdawn.example.enums.ResultEnum;
import com.lmxdawn.example.res.BaseRes;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果的操作类
 */
public class ResultVOUtils {

    /**
     * 成功时返回
     * @param data 返回的data对象
     * @return {@link BaseRes}
     */
    public static BaseRes success(Object data) {
        BaseRes<Object> baseResponse = new BaseRes<>();
        baseResponse.setCode(0);
        baseResponse.setMessage("success");
        baseResponse.setData(data);
        return baseResponse;
    }

    /**
     * 成功时返回
     * @return {@link BaseRes}
     */
    public static BaseRes success() {
        Map<Object, Object> data = new HashMap<>();
        return success(data);
    }

    /**
     * 错误时返回
     * @param code 错误码
     * @param message 错误信息
     * @return {@link BaseRes}
     */
    public static BaseRes error(Integer code, String message) {
        BaseRes<Object> baseResponse = new BaseRes<>();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        Map<Object, Object> data = new HashMap<>();
        baseResponse.setData(data);
        return baseResponse;
    }

    /**
     * 错误时返回
     * @param resultEnum 错误枚举类
     * @return {@link BaseRes}
     */
    public static BaseRes error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(), resultEnum.getMessage());
    }

    /**
     * 错误时返回
     * @param resultEnum 错误枚举类
     * @param message 错误的信息
     * @return {@link BaseRes}
     */
    public static BaseRes error(ResultEnum resultEnum, String message) {
        return error(resultEnum.getCode(), message);
    }

}