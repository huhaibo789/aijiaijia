package bean;

import java.util.List;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class huasabean {

    /**
     * op : 1
     * zu_id : 7
     * displayhall_list : {"mendian_address":"无锡市新区金城东路18号居然之家1楼展厅019号","mendian_id":14,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_d23e4b.png","mendian_latitude":31.562445,"mendian_longitude":120.353546,"mendian_name":"居然之家店","mendian_phone":"0510-88233870"}
     * zu_list : [{"attr_zuid":7,"attr_name":"品牌","attr_value":"花洒,浴缸,龙头"},{"attr_zuid":7,"attr_name":"风格","attr_value":"现代,经典,流行"}]
     */

    private ResponseJsonBean responseJson;

    public ResponseJsonBean getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(ResponseJsonBean responseJson) {
        this.responseJson = responseJson;
    }

    public static class ResponseJsonBean {
        private String op;
        private int zu_id;
        /**
         * mendian_address : 无锡市新区金城东路18号居然之家1楼展厅019号
         * mendian_id : 14
         * mendian_image : http://imgs.aijiaijia.com/mendian/2016-08-24/pro_d23e4b.png
         * mendian_latitude : 31.562445
         * mendian_longitude : 120.353546
         * mendian_name : 居然之家店
         * mendian_phone : 0510-88233870
         */

        private DisplayhallListBean displayhall_list;
        /**
         * attr_zuid : 7
         * attr_name : 品牌
         * attr_value : 花洒,浴缸,龙头
         */

        private List<ZuListBean> zu_list;

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public int getZu_id() {
            return zu_id;
        }

        public void setZu_id(int zu_id) {
            this.zu_id = zu_id;
        }

        public DisplayhallListBean getDisplayhall_list() {
            return displayhall_list;
        }

        public void setDisplayhall_list(DisplayhallListBean displayhall_list) {
            this.displayhall_list = displayhall_list;
        }

        public List<ZuListBean> getZu_list() {
            return zu_list;
        }

        public void setZu_list(List<ZuListBean> zu_list) {
            this.zu_list = zu_list;
        }

        public static class DisplayhallListBean {
            private String mendian_address;
            private int mendian_id;
            private String mendian_image;
            private double mendian_latitude;
            private double mendian_longitude;
            private String mendian_name;
            private String mendian_phone;

            public String getMendian_address() {
                return mendian_address;
            }

            public void setMendian_address(String mendian_address) {
                this.mendian_address = mendian_address;
            }

            public int getMendian_id() {
                return mendian_id;
            }

            public void setMendian_id(int mendian_id) {
                this.mendian_id = mendian_id;
            }

            public String getMendian_image() {
                return mendian_image;
            }

            public void setMendian_image(String mendian_image) {
                this.mendian_image = mendian_image;
            }

            public double getMendian_latitude() {
                return mendian_latitude;
            }

            public void setMendian_latitude(double mendian_latitude) {
                this.mendian_latitude = mendian_latitude;
            }

            public double getMendian_longitude() {
                return mendian_longitude;
            }

            public void setMendian_longitude(double mendian_longitude) {
                this.mendian_longitude = mendian_longitude;
            }

            public String getMendian_name() {
                return mendian_name;
            }

            public void setMendian_name(String mendian_name) {
                this.mendian_name = mendian_name;
            }

            public String getMendian_phone() {
                return mendian_phone;
            }

            public void setMendian_phone(String mendian_phone) {
                this.mendian_phone = mendian_phone;
            }
        }

        public static class ZuListBean {
            private int attr_zuid;
            private String attr_name;
            private String attr_value;

            public int getAttr_zuid() {
                return attr_zuid;
            }

            public void setAttr_zuid(int attr_zuid) {
                this.attr_zuid = attr_zuid;
            }

            public String getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public String getAttr_value() {
                return attr_value;
            }

            public void setAttr_value(String attr_value) {
                this.attr_value = attr_value;
            }
        }
    }
}
