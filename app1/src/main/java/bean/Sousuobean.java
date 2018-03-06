package bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class Sousuobean {

    private ResponseJsonBean responseJson;

    public ResponseJsonBean getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(ResponseJsonBean responseJson) {
        this.responseJson = responseJson;
    }

    public static class ResponseJsonBean {
        /**
         * brand_id : 13
         * brand_name : LG new
         */

        private List<ListBrandBean> list_brand;
        /**
         * id : 709
         * num : null
         * orderid : null
         * product_attribute_parm : null
         * product_attribute_type : null
         * product_bn : S0090020084R5-1
         * product_infoarea : null
         * product_infoarea_illustrate : null
         * product_max_point : null
         * product_name : 汉斯格雅·高级花洒-飞羽系列-清凉水龙头
         * product_nowprice : 699.0
         * product_pic : http://192.168.0.222:80/shoptrs_p/web/images/product/2016-07-13/pro_902725.jpg
         * product_pic_height : 640
         * product_pic_width : 640
         * product_pics : null
         * product_pics_3da : null
         * product_pics_3db : null
         * product_price : 500.0
         * product_stock : null
         * product_temppic : null
         * promotion_label : null
         * t : 13
         */

        private List<ListBean> list;
        /**
         * style_id : 6
         * style_name : 古风经典
         */

        private List<ListStyleBean> list_style;

        public List<ListBrandBean> getList_brand() {
            return list_brand;
        }

        public void setList_brand(List<ListBrandBean> list_brand) {
            this.list_brand = list_brand;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<ListStyleBean> getList_style() {
            return list_style;
        }

        public void setList_style(List<ListStyleBean> list_style) {
            this.list_style = list_style;
        }

        public static class ListBrandBean {
            private int brand_id;
            private String brand_name;

            public int getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(int brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }
        }

        public static class ListBean {
            private int id;
            private Object num;
            private Object orderid;
            private Object product_attribute_parm;
            private Object product_attribute_type;
            private String product_bn;
            private Object product_infoarea;
            private Object product_infoarea_illustrate;
            private Object product_max_point;
            private String product_name;
            private double product_nowprice;
            private String product_pic;
            private String product_pic_height;
            private String product_pic_width;
            private Object product_pics;
            private Object product_pics_3da;
            private Object product_pics_3db;
            private double product_price;
            private Object product_stock;
            private Object product_temppic;
            private Object promotion_label;
            private int t;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getNum() {
                return num;
            }

            public void setNum(Object num) {
                this.num = num;
            }

            public Object getOrderid() {
                return orderid;
            }

            public void setOrderid(Object orderid) {
                this.orderid = orderid;
            }

            public Object getProduct_attribute_parm() {
                return product_attribute_parm;
            }

            public void setProduct_attribute_parm(Object product_attribute_parm) {
                this.product_attribute_parm = product_attribute_parm;
            }

            public Object getProduct_attribute_type() {
                return product_attribute_type;
            }

            public void setProduct_attribute_type(Object product_attribute_type) {
                this.product_attribute_type = product_attribute_type;
            }

            public String getProduct_bn() {
                return product_bn;
            }

            public void setProduct_bn(String product_bn) {
                this.product_bn = product_bn;
            }

            public Object getProduct_infoarea() {
                return product_infoarea;
            }

            public void setProduct_infoarea(Object product_infoarea) {
                this.product_infoarea = product_infoarea;
            }

            public Object getProduct_infoarea_illustrate() {
                return product_infoarea_illustrate;
            }

            public void setProduct_infoarea_illustrate(Object product_infoarea_illustrate) {
                this.product_infoarea_illustrate = product_infoarea_illustrate;
            }

            public Object getProduct_max_point() {
                return product_max_point;
            }

            public void setProduct_max_point(Object product_max_point) {
                this.product_max_point = product_max_point;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public double getProduct_nowprice() {
                return product_nowprice;
            }

            public void setProduct_nowprice(double product_nowprice) {
                this.product_nowprice = product_nowprice;
            }

            public String getProduct_pic() {
                return product_pic;
            }

            public void setProduct_pic(String product_pic) {
                this.product_pic = product_pic;
            }

            public String getProduct_pic_height() {
                return product_pic_height;
            }

            public void setProduct_pic_height(String product_pic_height) {
                this.product_pic_height = product_pic_height;
            }

            public String getProduct_pic_width() {
                return product_pic_width;
            }

            public void setProduct_pic_width(String product_pic_width) {
                this.product_pic_width = product_pic_width;
            }

            public Object getProduct_pics() {
                return product_pics;
            }

            public void setProduct_pics(Object product_pics) {
                this.product_pics = product_pics;
            }

            public Object getProduct_pics_3da() {
                return product_pics_3da;
            }

            public void setProduct_pics_3da(Object product_pics_3da) {
                this.product_pics_3da = product_pics_3da;
            }

            public Object getProduct_pics_3db() {
                return product_pics_3db;
            }

            public void setProduct_pics_3db(Object product_pics_3db) {
                this.product_pics_3db = product_pics_3db;
            }

            public double getProduct_price() {
                return product_price;
            }

            public void setProduct_price(double product_price) {
                this.product_price = product_price;
            }

            public Object getProduct_stock() {
                return product_stock;
            }

            public void setProduct_stock(Object product_stock) {
                this.product_stock = product_stock;
            }

            public Object getProduct_temppic() {
                return product_temppic;
            }

            public void setProduct_temppic(Object product_temppic) {
                this.product_temppic = product_temppic;
            }

            public Object getPromotion_label() {
                return promotion_label;
            }

            public void setPromotion_label(Object promotion_label) {
                this.promotion_label = promotion_label;
            }

            public int getT() {
                return t;
            }

            public void setT(int t) {
                this.t = t;
            }
        }

        public static class ListStyleBean {
            private int style_id;
            private String style_name;

            public int getStyle_id() {
                return style_id;
            }

            public void setStyle_id(int style_id) {
                this.style_id = style_id;
            }

            public String getStyle_name() {
                return style_name;
            }

            public void setStyle_name(String style_name) {
                this.style_name = style_name;
            }
        }
    }
}
