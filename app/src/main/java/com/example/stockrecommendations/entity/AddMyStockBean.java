package com.example.stockrecommendations.entity;

public class AddMyStockBean {

    /**
     * errMsg : 用户不存在
     * stateCode : 10003
     */

    private String errMsg;
    private String stateCode;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
