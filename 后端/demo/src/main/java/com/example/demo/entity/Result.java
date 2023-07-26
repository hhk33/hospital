package com.example.demo.entity;


import java.util.HashMap;
import java.util.Map;

public class Result {
    private boolean success;
    private int code;
    private String message;
    private Map<String,Object> data = new HashMap<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static Result success(){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(500);
        result.setMessage("error");
        return result;
    }

    public static Result overdue(){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(401);
        result.setMessage("overdue");
        return result;
    }

    public Result data(String key, Object val){
        this.data.put(key,val);
        return this;
    }
}
