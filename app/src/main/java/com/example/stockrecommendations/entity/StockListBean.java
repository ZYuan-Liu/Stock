package com.example.stockrecommendations.entity;

import java.util.List;

public class StockListBean {


    private List<Result> result;
    private String stateCode;
    private int totalCount;

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public class Result {
        private String code;
        private String name;
        private String jc;
        private String category;
        private String subcategory;
        private String hybk;
        private String dqbk;
        private String gsgm;
        private float syl;
        private float sjl;
        private float gxl;
        private float jzcsyl;
        private float zcfzl;
        private float yylrl;
        private float jlrxjlbl;
        private float zzcjlrl;
        private float ldfzzzc;
        private float xjlzzc;
        private int p1;
        private int p2;
        private int p3;
        private int p4;
        private int p5;
        private int p4_rule;
        private int p5_rule;
        private float score_1;
        private float score_2;
        private float score_3;
        private float score_4;

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setJc(String jc) {
            this.jc = jc;
        }

        public String getJc() {
            return jc;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setHybk(String hybk) {
            this.hybk = hybk;
        }

        public String getHybk() {
            return hybk;
        }

        public void setDqbk(String dqbk) {
            this.dqbk = dqbk;
        }

        public String getDqbk() {
            return dqbk;
        }

        public void setGsgm(String gsgm) {
            this.gsgm = gsgm;
        }

        public String getGsgm() {
            return gsgm;
        }

        public void setSyl(float syl) {
            this.syl = syl;
        }

        public float getSyl() {
            return syl;
        }

        public void setSjl(float sjl) {
            this.sjl = sjl;
        }

        public float getSjl() {
            return sjl;
        }

        public void setGxl(float gxl) {
            this.gxl = gxl;
        }

        public float getGxl() {
            return gxl;
        }

        public void setJzcsyl(float jzcsyl) {
            this.jzcsyl = jzcsyl;
        }

        public float getJzcsyl() {
            return jzcsyl;
        }

        public void setZcfzl(float zcfzl) {
            this.zcfzl = zcfzl;
        }

        public float getZcfzl() {
            return zcfzl;
        }

        public void setYylrl(float yylrl) {
            this.yylrl = yylrl;
        }

        public float getYylrl() {
            return yylrl;
        }

        public void setJlrxjlbl(float jlrxjlbl) {
            this.jlrxjlbl = jlrxjlbl;
        }

        public float getJlrxjlbl() {
            return jlrxjlbl;
        }

        public void setZzcjlrl(float zzcjlrl) {
            this.zzcjlrl = zzcjlrl;
        }

        public float getZzcjlrl() {
            return zzcjlrl;
        }

        public void setLdfzzzc(float ldfzzzc) {
            this.ldfzzzc = ldfzzzc;
        }

        public float getLdfzzzc() {
            return ldfzzzc;
        }

        public void setXjlzzc(float xjlzzc) {
            this.xjlzzc = xjlzzc;
        }

        public float getXjlzzc() {
            return xjlzzc;
        }

        public void setP1(int p1) {
            this.p1 = p1;
        }

        public int getP1() {
            return p1;
        }

        public void setP2(int p2) {
            this.p2 = p2;
        }

        public int getP2() {
            return p2;
        }

        public void setP3(int p3) {
            this.p3 = p3;
        }

        public int getP3() {
            return p3;
        }

        public void setP4(int p4) {
            this.p4 = p4;
        }

        public int getP4() {
            return p4;
        }

        public void setP5(int p5) {
            this.p5 = p5;
        }

        public int getP5() {
            return p5;
        }

        public void setP4_rule(int p4_rule) {
            this.p4_rule = p4_rule;
        }

        public int getP4_rule() {
            return p4_rule;
        }

        public void setP5_rule(int p5_rule) {
            this.p5_rule = p5_rule;
        }

        public int getP5_rule() {
            return p5_rule;
        }

        public void setScore_1(float score_1) {
            this.score_1 = score_1;
        }

        public float getScore_1() {
            return score_1;
        }

        public void setScore_2(float score_2) {
            this.score_2 = score_2;
        }

        public float getScore_2() {
            return score_2;
        }

        public void setScore_3(float score_3) {
            this.score_3 = score_3;
        }

        public float getScore_3() {
            return score_3;
        }

        public void setScore_4(float score_4) {
            this.score_4 = score_4;
        }

        public float getScore_4() {
            return score_4;
        }

    }
}
