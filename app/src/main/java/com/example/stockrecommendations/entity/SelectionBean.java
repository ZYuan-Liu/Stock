package com.example.stockrecommendations.entity;

import java.util.ArrayList;
import java.util.List;

public class SelectionBean {
    private String leftTitle;
    private List<String> rightDatas;
    private String leftCode;

    public String getLeftCode() {
        return leftCode;
    }

    public void setLeftCode(String leftCode) {
        this.leftCode = leftCode;
    }

    public String getLeftTitle() {
        return leftTitle == null ? "" : leftTitle;
    }

    public List<String> getRightDatas() {
        if (rightDatas == null) {
            return new ArrayList<>();
        }
        return rightDatas;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public void setRightDatas(List<String> rightDatas) {
        this.rightDatas = rightDatas;
    }
}
