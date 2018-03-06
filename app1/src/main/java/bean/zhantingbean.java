package bean;

import java.util.List;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class zhantingbean {

    /**
     * op : 1
     * list : [{"mendian_address":"无锡市新区金城东路18号居然之家1楼展厅019号","mendian_id":14,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_d23e4b.png","mendian_latitude":31.562445,"mendian_longitude":120.353546,"mendian_name":"居然之家店","mendian_phone":"0510-88233870"},{"mendian_address":"无锡市滨湖区红星美凯龙至尊MALL一楼展厅","mendian_id":13,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_058d2e.png","mendian_latitude":31.494188,"mendian_longitude":120.290306,"mendian_name":"红星美凯龙二店","mendian_phone":"13771525648"},{"mendian_address":"无锡市锡山区东团结南路1号红星美凯龙一期一楼展厅","mendian_id":12,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_2701ec.png","mendian_latitude":31.57727,"mendian_longitude":120.39266,"mendian_name":"红星美凯龙一店","mendian_phone":"0510-88251710"},{"mendian_address":"无锡市崇安区锡沪路280号华夏家居港二楼B072","mendian_id":11,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_d9fd62.png","mendian_latitude":31.58561,"mendian_longitude":120.341934,"mendian_name":"华夏家居港店","mendian_phone":"0510-85529855"},{"mendian_address":"武汉市江汉区发展大道欧亚达国际建材馆1楼汉斯格雅","mendian_id":10,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_487aab.png","mendian_latitude":30.614216,"mendian_longitude":114.25317,"mendian_name":"欧亚达店","mendian_phone":"027-85305238"},{"mendian_address":"武汉市武昌区徐东大街岳家嘴居然之家C门1层汉斯格雅","mendian_id":9,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_67828c.png","mendian_latitude":30.57472,"mendian_longitude":114.36025,"mendian_name":"居然之家店","mendian_phone":"027-59002921"},{"mendian_address":"武汉市硚口区解放大道201号南国大家装Ｃ座1层汉斯格雅","mendian_id":8,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_88e8fe.png","mendian_latitude":30.586927,"mendian_longitude":114.22615,"mendian_name":"大武汉店","mendian_phone":"027-83316677"},{"mendian_address":"青岛市市北区同德路8号居然之家一楼017","mendian_id":7,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_9e1500.png","mendian_latitude":36.117393,"mendian_longitude":120.40079,"mendian_name":"居然之家店","mendian_phone":"0532-80911015"},{"mendian_address":"济南市历山北路居然之家A厅一楼","mendian_id":6,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_edff0e.png","mendian_latitude":36.691578,"mendian_longitude":117.045265,"mendian_name":"居然之家店","mendian_phone":"0531-55592900"},{"mendian_address":"济南市北园大街红星美凯龙中厅一楼","mendian_id":5,"mendian_image":"http://imgs.aijiaijia.com/mendian/2016-08-24/pro_56c7d9.png","mendian_latitude":36.691143,"mendian_longitude":117.04631,"mendian_name":"红星美凯龙店","mendian_phone":"0531-55530096"}]
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
         * mendian_address : 无锡市新区金城东路18号居然之家1楼展厅019号
         * mendian_id : 14
         * mendian_image : http://imgs.aijiaijia.com/mendian/2016-08-24/pro_d23e4b.png
         * mendian_latitude : 31.562445
         * mendian_longitude : 120.353546
         * mendian_name : 居然之家店
         * mendian_phone : 0510-88233870
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
        }
    }
}
