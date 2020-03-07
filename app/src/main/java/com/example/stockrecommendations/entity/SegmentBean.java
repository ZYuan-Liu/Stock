package com.example.stockrecommendations.entity;

import com.google.gson.annotations.SerializedName;

public class SegmentBean {   //板块请求的bean
    private int rc;

    private int rt;

    private int svr;

    private int lt;

    private int full;

    private Data data;

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public int getSvr() {
        return svr;
    }

    public void setSvr(int svr) {
        this.svr = svr;
    }

    public int getLt() {
        return lt;
    }

    public void setLt(int lt) {
        this.lt = lt;
    }

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private int total;

        private Diff diff;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public Diff getDiff() {
            return diff;
        }

        public void setDiff(Diff diff) {
            this.diff = diff;
        }

        public class Diff {
            @SerializedName("0")   //东方财富的接口json key里有数字
                    One one;
            @SerializedName("1")
            Two two;
            @SerializedName("2")
            Three three;

            public One getOne() {
                return one;
            }

            public void setOne(One one) {
                this.one = one;
            }

            public Two getTwo() {
                return two;
            }

            public void setTwo(Two two) {
                this.two = two;
            }

            public Three getThree() {
                return three;
            }

            public void setThree(Three three) {
                this.three = three;
            }

            public class One {
                private float f3;  //板块涨幅

                private String f14; //板块名称

                private String f128;//领涨股名

                private String f140;//领涨股代码

                private int f141;

                private float f136;//板块领涨股涨幅

                public float getF3() {
                    return f3;
                }

                public void setF3(float f3) {
                    this.f3 = f3;
                }

                public String getF14() {
                    return f14;
                }

                public void setF14(String f14) {
                    this.f14 = f14;
                }

                public String getF128() {
                    return f128;
                }

                public void setF128(String f128) {
                    this.f128 = f128;
                }

                public String getF140() {
                    return f140;
                }

                public void setF140(String f140) {
                    this.f140 = f140;
                }

                public int getF141() {
                    return f141;
                }

                public void setF141(int f141) {
                    this.f141 = f141;
                }

                public float getF136() {
                    return f136;
                }

                public void setF136(float f136) {
                    this.f136 = f136;
                }
            }

            public class Two {
                private float f3;  //板块涨幅

                private String f14; //板块名称

                private String f128;//领涨股名

                private String f140;//领涨股代码

                private int f141;

                private float f136;//板块领涨股涨幅

                public float getF3() {
                    return f3;
                }

                public void setF3(float f3) {
                    this.f3 = f3;
                }

                public String getF14() {
                    return f14;
                }

                public void setF14(String f14) {
                    this.f14 = f14;
                }

                public String getF128() {
                    return f128;
                }

                public void setF128(String f128) {
                    this.f128 = f128;
                }

                public String getF140() {
                    return f140;
                }

                public void setF140(String f140) {
                    this.f140 = f140;
                }

                public int getF141() {
                    return f141;
                }

                public void setF141(int f141) {
                    this.f141 = f141;
                }

                public float getF136() {
                    return f136;
                }

                public void setF136(float f136) {
                    this.f136 = f136;
                }
            }

            public class Three {
                private float f3;  //板块涨幅

                private String f14; //板块名称

                private String f128;//领涨股名

                private String f140;//领涨股代码

                private int f141;

                private float f136;//板块领涨股涨幅

                public float getF3() {
                    return f3;
                }

                public void setF3(float f3) {
                    this.f3 = f3;
                }

                public String getF14() {
                    return f14;
                }

                public void setF14(String f14) {
                    this.f14 = f14;
                }

                public String getF128() {
                    return f128;
                }

                public void setF128(String f128) {
                    this.f128 = f128;
                }

                public String getF140() {
                    return f140;
                }

                public void setF140(String f140) {
                    this.f140 = f140;
                }

                public int getF141() {
                    return f141;
                }

                public void setF141(int f141) {
                    this.f141 = f141;
                }

                public float getF136() {
                    return f136;
                }

                public void setF136(float f136) {
                    this.f136 = f136;
                }
            }


        }
    }
}
