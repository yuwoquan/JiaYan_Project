package com.example.administrator.jiayan_project.enity.search;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26/026.
 */

public class SearchResultBean {


    /**
     * date : [{"id":1,"originalimg":"/static/img/dinner_img/1c32385f012ab97b02f8a91ee7d3fddd.jpg","dinnername":"鸿门宴","salesum":999,"subname":"项羽鸿门设宴","price":999}]
     * code : 200
     */

    private int code;
    private List<DateBean> date;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DateBean> getDate() {
        return date;
    }

    public void setDate(List<DateBean> date) {
        this.date = date;
    }

    public static class DateBean {
        /**
         * id : 1
         * originalimg : /static/img/dinner_img/1c32385f012ab97b02f8a91ee7d3fddd.jpg
         * dinnername : 鸿门宴
         * salesum : 999
         * subname : 项羽鸿门设宴
         * price : 999
         */

        private int id;
        private String originalimg;
        private String dinnername;
        private int salesum;
        private String subname;
        private int price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginalimg() {
            return originalimg;
        }

        public void setOriginalimg(String originalimg) {
            this.originalimg = originalimg;
        }

        public String getDinnername() {
            return dinnername;
        }

        public void setDinnername(String dinnername) {
            this.dinnername = dinnername;
        }

        public int getSalesum() {
            return salesum;
        }

        public void setSalesum(int salesum) {
            this.salesum = salesum;
        }

        public String getSubname() {
            return subname;
        }

        public void setSubname(String subname) {
            this.subname = subname;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
