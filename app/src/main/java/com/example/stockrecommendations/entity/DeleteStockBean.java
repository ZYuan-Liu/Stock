package com.example.stockrecommendations.entity;

public class DeleteStockBean {

    /**
     * stateCode : 10000
     * delCount : 1
     * errMsg : dd
     */

    private String stateCode;
    private int delCount;
    private String errMsg;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getDelCount() {
        return delCount;
    }

    public void setDelCount(int delCount) {
        this.delCount = delCount;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
