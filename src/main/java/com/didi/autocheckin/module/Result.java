package com.didi.autocheckin.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 *
 * @author didi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public static Result success() {
        Result jsonData = new Result();
        jsonData.setCode(200);
        jsonData.setMsg("成功");
        return jsonData;
    }

    public static Result success(String msg) {
        Result jsonData = new Result();
        jsonData.setCode(200);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static Result success(Object object) {
        Result jsonData = new Result();
        jsonData.setData(object);
        jsonData.setCode(200);
        jsonData.setMsg("成功");
        return jsonData;
    }

    public static Result success(String msg, Object object) {
        Result jsonData = new Result();
        jsonData.setData(object);
        jsonData.setCode(200);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static Result fail(Integer code, String message) {
        Result jsonData = new Result();
        jsonData.setCode(code);
        jsonData.setMsg(message);
        return jsonData;
    }

    public static Result fail(String msg, String data) {
        Result jsonData = new Result();
        jsonData.setMsg(msg);
        jsonData.setData(data);
        return jsonData;
    }

    public static Result fail(String msg, Object data) {
        Result jsonData = new Result();
        jsonData.setMsg(msg);
        jsonData.setData(data);
        return jsonData;
    }

    public static Result fail(String msg) {
        Result jsonData = new Result();
        jsonData.setMsg(msg);
        jsonData.setCode(400);
        return jsonData;
    }
}
