package com.example.stockrecommendations.entity;

public class LoginBean {

    /**
     * stateCode : 1
     * user_id : 1
     * errMsg : 用户名与密码不匹配
     */

    private int stateCode;
    private int user_id;
    private String errMsg;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
