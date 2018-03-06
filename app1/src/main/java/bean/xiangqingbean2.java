package bean;

import java.util.List;

/**
 * Created by 胡海波 on 2016/8/22.
 */
public class xiangqingbean2 {

    /**
     * list_product_detail : ["http://api.aijiaijia.com/api_products_gethtml?pid=5"]
     * list_hasab : 1
     * list_product : [{"id":5,"num":null,"orderid":null,"product_attribute_parm":"[\" \\u7ecf\\u51783\\u7cfb\",\"\\u53cc\\u82b1\\u6d12\\u9f99\\u5934\",\"\\u6c49\\u65af\\u683c\\u96c5\",\"\\u5355\\u628a\\u53cc\\u63a7\",\"\\u53ef\\u65cb\\u8f6c\\u5e26\\u5347\\u964d\",\"\\u6302\\u5899\\u5f0f\",\"\\u540c\\u57ce\\u7269\\u6d41\\u9001\\u8d27\\u4e0a\\u95e8\",\"\\u5c40\\u90e8\\u94dc\",\" \\u7ecf\\u51782\\u7cfb\"]","product_attribute_type":"[\"\\u578b\\u53f7\",\"\\u6dcb\\u6d74\\u82b1\\u6d12\\u9f99\\u5934\\u7c7b\\u578b\",\"\\u54c1\\u724c\",\"\\u51b7\\u70ed\\u6c34\\u63a7\\u5236\\u7c7b\\u578b\",\"\\u82b1\\u6d12\\u652f\\u67b6\\u7c7b\\u578b\",\"\\u5b89\\u88c5\\u65b9\\u5f0f\",\"\\u540c\\u57ce\\u670d\\u52a1\",\"\\u6750\\u8d28\",\"\\u578b\\u53f7\"]","product_bn":"26623","product_infoarea":"","product_infoarea_illustrate":"","product_max_point":1000,"product_name":"汉斯格雅 飞雨select E120恒温龙头淋浴花洒套餐26623+1","product_nowprice":7777,"product_pic":"http://imgs.aijiaijia.com/product/2016-08-12/pro_fec3f5.jpg","product_pic_height":null,"product_pic_width":null,"product_pics":"[\"2016-08-12\\/pro_fec3f5.jpg\",\"2016-08-12\\/pro_4222c6.jpg\",\"2016-08-12\\/pro_91bf2f.jpg\",\"2016-08-12\\/pro_c0a44c.jpg\",\"2016-08-12\\/pro_1d5108.jpg\"]","product_pics_3da":null,"product_pics_3db":null,"product_price":9999,"product_stock":20,"product_temppic":"2016-08-12/pro_fec3f5.jpg","promotion_label":null,"t":null}]
     * list_product_aftersaleservice : ["http://api.aijiaijia.com/api_products_gethtmlofaftersaleservice?pid=5"]
     * list_product_attribute_type : ["型号","淋浴花洒龙头类型","品牌","冷热水控制类型","花洒支架类型","安装方式","同城服务","材质","型号"]
     * list_product_attribute_parm : [" 经典3系","双花洒龙头","汉斯格雅","单把双控","可旋转带升降","挂墙式","同城物流送货上门","局部铜"," 经典2系"]
     * list_rate : [0]
     * list_comment : []
     * list_product_urlpics : ["http://imgs.aijiaijia.com/product/2016-08-12/pro_fec3f5.jpg","http://imgs.aijiaijia.com/product/2016-08-12/pro_4222c6.jpg","http://imgs.aijiaijia.com/product/2016-08-12/pro_91bf2f.jpg","http://imgs.aijiaijia.com/product/2016-08-12/pro_c0a44c.jpg","http://imgs.aijiaijia.com/product/2016-08-12/pro_1d5108.jpg"]
     */

    private ResponseJsonBean responseJson;

    public ResponseJsonBean getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(ResponseJsonBean responseJson) {
        this.responseJson = responseJson;
    }

    public static class ResponseJsonBean {
        private int list_hasab;
        private List<String> list_product_detail;
        /**
         * id : 5
         * num : null
         * orderid : null
         * product_attribute_parm : [" \u7ecf\u51783\u7cfb","\u53cc\u82b1\u6d12\u9f99\u5934","\u6c49\u65af\u683c\u96c5","\u5355\u628a\u53cc\u63a7","\u53ef\u65cb\u8f6c\u5e26\u5347\u964d","\u6302\u5899\u5f0f","\u540c\u57ce\u7269\u6d41\u9001\u8d27\u4e0a\u95e8","\u5c40\u90e8\u94dc"," \u7ecf\u51782\u7cfb"]
         * product_attribute_type : ["\u578b\u53f7","\u6dcb\u6d74\u82b1\u6d12\u9f99\u5934\u7c7b\u578b","\u54c1\u724c","\u51b7\u70ed\u6c34\u63a7\u5236\u7c7b\u578b","\u82b1\u6d12\u652f\u67b6\u7c7b\u578b","\u5b89\u88c5\u65b9\u5f0f","\u540c\u57ce\u670d\u52a1","\u6750\u8d28","\u578b\u53f7"]
         * product_bn : 26623
         * product_infoarea :
         * product_infoarea_illustrate :
         * product_max_point : 1000
         * product_name : 汉斯格雅 飞雨select E120恒温龙头淋浴花洒套餐26623+1
         * product_nowprice : 7777.0
         * product_pic : http://imgs.aijiaijia.com/product/2016-08-12/pro_fec3f5.jpg
         * product_pic_height : null
         * product_pic_width : null
         * product_pics : ["2016-08-12\/pro_fec3f5.jpg","2016-08-12\/pro_4222c6.jpg","2016-08-12\/pro_91bf2f.jpg","2016-08-12\/pro_c0a44c.jpg","2016-08-12\/pro_1d5108.jpg"]
         * product_pics_3da : null
         * product_pics_3db : null
         * product_price : 9999.0
         * product_stock : 20
         * product_temppic : 2016-08-12/pro_fec3f5.jpg
         * promotion_label : null
         * t : null
         */

        private List<ListProductBean> list_product;
        private List<String> list_product_aftersaleservice;
        private List<String> list_product_attribute_type;
        private List<String> list_product_attribute_parm;
        private List<Integer> list_rate;
        private List<?> list_comment;
        private List<String> list_product_urlpics;

        public int getList_hasab() {
            return list_hasab;
        }

        public void setList_hasab(int list_hasab) {
            this.list_hasab = list_hasab;
        }

        public List<String> getList_product_detail() {
            return list_product_detail;
        }

        public void setList_product_detail(List<String> list_product_detail) {
            this.list_product_detail = list_product_detail;
        }

        public List<ListProductBean> getList_product() {
            return list_product;
        }

        public void setList_product(List<ListProductBean> list_product) {
            this.list_product = list_product;
        }

        public List<String> getList_product_aftersaleservice() {
            return list_product_aftersaleservice;
        }

        public void setList_product_aftersaleservice(List<String> list_product_aftersaleservice) {
            this.list_product_aftersaleservice = list_product_aftersaleservice;
        }

        public List<String> getList_product_attribute_type() {
            return list_product_attribute_type;
        }

        public void setList_product_attribute_type(List<String> list_product_attribute_type) {
            this.list_product_attribute_type = list_product_attribute_type;
        }

        public List<String> getList_product_attribute_parm() {
            return list_product_attribute_parm;
        }

        public void setList_product_attribute_parm(List<String> list_product_attribute_parm) {
            this.list_product_attribute_parm = list_product_attribute_parm;
        }

        public List<Integer> getList_rate() {
            return list_rate;
        }

        public void setList_rate(List<Integer> list_rate) {
            this.list_rate = list_rate;
        }

        public List<?> getList_comment() {
            return list_comment;
        }

        public void setList_comment(List<?> list_comment) {
            this.list_comment = list_comment;
        }

        public List<String> getList_product_urlpics() {
            return list_product_urlpics;
        }

        public void setList_product_urlpics(List<String> list_product_urlpics) {
            this.list_product_urlpics = list_product_urlpics;
        }

        public static class ListProductBean {
            private int id;
            private Object num;
            private Object orderid;
            private String product_attribute_parm;
            private String product_attribute_type;
            private String product_bn;
            private String product_infoarea;
            private String product_infoarea_illustrate;
            private int product_max_point;
            private String product_name;
            private double product_nowprice;
            private String product_pic;
            private Object product_pic_height;
            private Object product_pic_width;
            private String product_pics;
            private Object product_pics_3da;
            private Object product_pics_3db;
            private double product_price;
            private int product_stock;
            private String product_temppic;
            private Object promotion_label;
            private Object t;

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

            public String getProduct_attribute_parm() {
                return product_attribute_parm;
            }

            public void setProduct_attribute_parm(String product_attribute_parm) {
                this.product_attribute_parm = product_attribute_parm;
            }

            public String getProduct_attribute_type() {
                return product_attribute_type;
            }

            public void setProduct_attribute_type(String product_attribute_type) {
                this.product_attribute_type = product_attribute_type;
            }

            public String getProduct_bn() {
                return product_bn;
            }

            public void setProduct_bn(String product_bn) {
                this.product_bn = product_bn;
            }

            public String getProduct_infoarea() {
                return product_infoarea;
            }

            public void setProduct_infoarea(String product_infoarea) {
                this.product_infoarea = product_infoarea;
            }

            public String getProduct_infoarea_illustrate() {
                return product_infoarea_illustrate;
            }

            public void setProduct_infoarea_illustrate(String product_infoarea_illustrate) {
                this.product_infoarea_illustrate = product_infoarea_illustrate;
            }

            public int getProduct_max_point() {
                return product_max_point;
            }

            public void setProduct_max_point(int product_max_point) {
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

            public Object getProduct_pic_height() {
                return product_pic_height;
            }

            public void setProduct_pic_height(Object product_pic_height) {
                this.product_pic_height = product_pic_height;
            }

            public Object getProduct_pic_width() {
                return product_pic_width;
            }

            public void setProduct_pic_width(Object product_pic_width) {
                this.product_pic_width = product_pic_width;
            }

            public String getProduct_pics() {
                return product_pics;
            }

            public void setProduct_pics(String product_pics) {
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

            public int getProduct_stock() {
                return product_stock;
            }

            public void setProduct_stock(int product_stock) {
                this.product_stock = product_stock;
            }

            public String getProduct_temppic() {
                return product_temppic;
            }

            public void setProduct_temppic(String product_temppic) {
                this.product_temppic = product_temppic;
            }

            public Object getPromotion_label() {
                return promotion_label;
            }

            public void setPromotion_label(Object promotion_label) {
                this.promotion_label = promotion_label;
            }

            public Object getT() {
                return t;
            }

            public void setT(Object t) {
                this.t = t;
            }
        }
    }
}
