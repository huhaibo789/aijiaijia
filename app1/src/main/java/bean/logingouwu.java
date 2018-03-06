package bean;

import java.util.List;

/**
 * Created by 胡海波 on 2016/9/30.
 */
public class logingouwu {

    private ResponseJsonBean responseJson;

    public ResponseJsonBean getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(ResponseJsonBean responseJson) {
        this.responseJson = responseJson;
    }

    public static class ResponseJsonBean {
        /**
         * id : 455
         * num : 1
         * pid : 12
         * product_bn : 265514
         * product_name : 汉斯格雅 飞雨150超大花洒恒温淋浴花洒套餐高端升降带置物架龙头265514+276100+286780
         * product_nowprice : 3899.0
         * product_pic : http://imgs.aijiaijia.com/product/2016-08-24/pro_cc69a4.jpg
         * product_pics : null
         * product_price : 6899
         * uid : null
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int id;
            private int num;
            private int pid;
            private String product_bn;
            private String product_name;
            private String product_nowprice;
            private String product_pic;
            private Object product_pics;
            private String product_price;
            private Object uid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getProduct_bn() {
                return product_bn;
            }

            public void setProduct_bn(String product_bn) {
                this.product_bn = product_bn;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_nowprice() {
                return product_nowprice;
            }

            public void setProduct_nowprice(String product_nowprice) {
                this.product_nowprice = product_nowprice;
            }

            public String getProduct_pic() {
                return product_pic;
            }

            public void setProduct_pic(String product_pic) {
                this.product_pic = product_pic;
            }

            public Object getProduct_pics() {
                return product_pics;
            }

            public void setProduct_pics(Object product_pics) {
                this.product_pics = product_pics;
            }

            public String getProduct_price() {
                return product_price;
            }

            public void setProduct_price(String product_price) {
                this.product_price = product_price;
            }

            public Object getUid() {
                return uid;
            }

            public void setUid(Object uid) {
                this.uid = uid;
            }
        }
    }
}
