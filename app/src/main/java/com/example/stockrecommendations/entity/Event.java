package com.example.stockrecommendations.entity;

/**
 * Created by lzy123 on 2019/8/24.
 * code=1  标签页数据返回
 */

public class Event<T> {
    private int code;   //code=1 标签选股   code=2 删除自选
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
