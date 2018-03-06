package bean;

import java.util.List;

/**
 * Created by 胡海波 on 2016/9/1.
 */
public class zuijinbean {

    /**
     * op : 1
     * list : [{"mendian_address":"济南市北园大街银座家居中心店东厅一楼","mendian_id":4,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_c75d04.png","mendian_latitude":36.68492,"mendian_longitude":117.01452,"mendian_name":"银座中心店","mendian_phone":"0531-55559959"},{"mendian_address":"南京市卡子门大街29号红星美凯龙1F-A8032","mendian_id":3,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_a0f999.png","mendian_latitude":31.988361,"mendian_longitude":118.79923,"mendian_name":"红星美凯龙店","mendian_phone":"025-82266698"},{"mendian_address":"南京市江东中路88号金陵国际家居1F-8213","mendian_id":1,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_b28451.png","mendian_latitude":32.094784,"mendian_longitude":118.762794,"mendian_name":"金陵国际家具店","mendian_phone":"025-86557185"},{"mendian_address":"南京市江东中路80号金盛国际家居1F-A1-3","mendian_id":2,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_dfa96f.png","mendian_latitude":32.035336,"mendian_longitude":118.73603,"mendian_name":"金盛国际家居店","mendian_phone":"025-86569755"}]
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
        /**
         * mendian_address : 济南市北园大街银座家居中心店东厅一楼
         * mendian_id : 4
         * mendian_image : http://imgs.aijiaijia.com/mendian/2016-08-24/pro_c75d04.png
         * mendian_latitude : 36.68492
         * mendian_longitude : 117.01452
         * mendian_name : 银座中心店
         * mendian_phone : 0531-55559959
         */

        private List<ListBean> list;

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
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

            @Override
            public String toString() {
                return "ListBean{" +
                        "mendian_address='" + mendian_address + '\'' +
                        ", mendian_id=" + mendian_id +
                        ", mendian_image='" + mendian_image + '\'' +
                        ", mendian_latitude=" + mendian_latitude +
                        ", mendian_longitude=" + mendian_longitude +
                        ", mendian_name='" + mendian_name + '\'' +
                        ", mendian_phone='" + mendian_phone + '\'' +
                        '}';
            }
        }
    }
}
