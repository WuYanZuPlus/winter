package com.jianghu.winter.config.remote;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author daniel.hu
 * @date 2018/11/8 14:48
 */
@Setter
@Getter
@ToString
@SuppressWarnings("unused")
public class RemoteResponse<T> {

    /** 响应状态码 */
    @JsonAlias({"status"})
    private String code;

    /** 响应的错误信息 */
    @JsonAlias({"msg", "errorInfo", "errorMsg"})
    private String message;

    /** 返回数据 */
    @JsonAlias({"content", "body", "accessToken"})
    private T data;

    /** 调用结果 */
    @JsonAlias("ok")
    private boolean success;

    public boolean fail() {
        return !success;
    }

    @JsonProperty("head")
    private void unpackNameFromNestedObject(Map<String, String> head) {
        code = head.get("code");
        message = head.get("msg");
    }

    public static <T> RemoteResponse<T> build(String statusCode, String message) {
        RemoteResponse<T> response = new RemoteResponse<>();
        response.setCode(statusCode);
        response.setMessage(message);
        return response;
    }

    public static <T> RemoteResponse<T> failure() {
        return RemoteResponse.build("-1", "服务响应异常");
    }

}
