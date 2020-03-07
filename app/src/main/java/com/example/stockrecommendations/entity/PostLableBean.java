package com.example.stockrecommendations.entity;

import java.util.List;

public class PostLableBean {


    /**
     * currPage : 1
     * labels : [{"supLab":"gsgm","subLab":"中型企业"},{"supLab":"gsgm","subLab":"小型企业"}]
     */

    private int currPage;
    private List<LabelsBean> labels;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsBean> labels) {
        this.labels = labels;
    }

    public static class LabelsBean {
        /**
         * supLab : gsgm
         * subLab : 中型企业
         */

        private String supLab;
        private String subLab;

        public String getSupLab() {
            return supLab;
        }

        public void setSupLab(String supLab) {
            this.supLab = supLab;
        }

        public String getSubLab() {
            return subLab;
        }

        public void setSubLab(String subLab) {
            this.subLab = subLab;
        }
    }
}
