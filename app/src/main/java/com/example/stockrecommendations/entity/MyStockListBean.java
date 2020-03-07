package com.example.stockrecommendations.entity;

import java.util.List;

public class MyStockListBean {

    /**
     * result : [{"collectid":34,"username":"111111","code":"002219","name":"恒康医疗","industry":"中成药","area":"甘肃","concept":null,"changeperc":"10.18","open":"2.74","preclose":"2.75","high":"3.03","low":"2.62","volume":"61.85","amount":"17523.72","trade":"3.03"},{"collectid":35,"username":"111111","code":"300194","name":"福安药业","industry":"化学制药","area":"重庆","concept":null,"changeperc":"10.04","open":"5.00","preclose":"4.98","high":"5.48","low":"5.00","volume":"107.71","amount":"56684.10","trade":"5.48"},{"collectid":36,"username":"111111","code":"600082","name":"海泰发展","industry":"园区开发","area":"天津","concept":null,"changeperc":"-2.63","open":"4.07","preclose":"4.19","high":"4.13","low":"4.04","volume":"4.37","amount":"1781.25","trade":"4.08"}]
     * stateCode : 10000
     * totalCount : 4
     */

    private String stateCode;
    private int totalCount;
    private List<ResultBean> result;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * collectid : 34
         * username : 111111
         * code : 002219
         * name : 恒康医疗
         * industry : 中成药
         * area : 甘肃
         * concept : null
         * changeperc : 10.18
         * open : 2.74
         * preclose : 2.75
         * high : 3.03
         * low : 2.62
         * volume : 61.85
         * amount : 17523.72
         * trade : 3.03
         */

        private int collectid;
        private String username;
        private String code;
        private String name;
        private String industry;
        private String area;
        private Object concept;
        private String changeperc;
        private String open;
        private String preclose;
        private String high;
        private String low;
        private String volume;
        private String amount;
        private String trade;

        public int getCollectid() {
            return collectid;
        }

        public void setCollectid(int collectid) {
            this.collectid = collectid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public Object getConcept() {
            return concept;
        }

        public void setConcept(Object concept) {
            this.concept = concept;
        }

        public String getChangeperc() {
            return changeperc;
        }

        public void setChangeperc(String changeperc) {
            this.changeperc = changeperc;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getPreclose() {
            return preclose;
        }

        public void setPreclose(String preclose) {
            this.preclose = preclose;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }
    }
}
