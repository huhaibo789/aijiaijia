package bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class fenleibean {


    private ResponseJsonBean responseJson;

    public ResponseJsonBean getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(ResponseJsonBean responseJson) {
        this.responseJson = responseJson;
    }

    public static class ResponseJsonBean {
        /**
         * categorieName : 配件
         * categorieParentId : 0
         * categoriePic : http://imgs.aijiaijia.com/category/2016-08-12/pro_34873c.jpg
         * id : 5
         * sub : [{"categoriePic":"http://imgs.aijiaijia.com/mobile/product/2016-05-24/pro_7c6daa.png","id":"http://www.baidu.com","issingle":0,"mc_id":21,"mc_isshow":null,"mc_link":"http://www.baidu.com","mc_modle":1,"mc_pic":"http://imgs.aijiaijia.com/mobile/product/2016-05-24/pro_7c6daa.png","mc_pic_identifier":1,"product_name":null,"product_nowprice":null,"product_price":null,"singlepid":0,"sub":null,"subtitle":null,"title":null},{"categoriePic":"http://imgs.aijiaijia.com/mobile/product/2016-08-16/pro_24b02c.png","id":"http://www.baidu.com","issingle":0,"mc_id":22,"mc_isshow":null,"mc_link":"http://www.baidu.com","mc_modle":1,"mc_pic":"http://imgs.aijiaijia.com/mobile/product/2016-08-16/pro_24b02c.png","mc_pic_identifier":2,"product_name":null,"product_nowprice":null,"product_price":null,"singlepid":0,"sub":null,"subtitle":null,"title":null}]
         */

        private List<ListBigBean> list_big;

        public List<ListBigBean> getList_big() {
            return list_big;
        }

        public void setList_big(List<ListBigBean> list_big) {
            this.list_big = list_big;
        }

        public static class ListBigBean {
            private String categorieName;
            private int categorieParentId;
            private String categoriePic;
            private int id;
            /**
             * categoriePic : http://imgs.aijiaijia.com/mobile/product/2016-05-24/pro_7c6daa.png
             * id : http://www.baidu.com
             * issingle : 0
             * mc_id : 21
             * mc_isshow : null
             * mc_link : http://www.baidu.com
             * mc_modle : 1
             * mc_pic : http://imgs.aijiaijia.com/mobile/product/2016-05-24/pro_7c6daa.png
             * mc_pic_identifier : 1
             * product_name : null
             * product_nowprice : null
             * product_price : null
             * singlepid : 0
             * sub : null
             * subtitle : null
             * title : null
             */

            private List<SubBean> sub;

            public String getCategorieName() {
                return categorieName;
            }

            public void setCategorieName(String categorieName) {
                this.categorieName = categorieName;
            }

            public int getCategorieParentId() {
                return categorieParentId;
            }

            public void setCategorieParentId(int categorieParentId) {
                this.categorieParentId = categorieParentId;
            }

            public String getCategoriePic() {
                return categoriePic;
            }

            public void setCategoriePic(String categoriePic) {
                this.categoriePic = categoriePic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<SubBean> getSub() {
                return sub;
            }

            public void setSub(List<SubBean> sub) {
                this.sub = sub;
            }

            public static class SubBean {
                private String categorieName;
                private String categoriePic;
                private String id;
                private int issingle;
                private int mc_id;
                private Object mc_isshow;
                private String mc_link;
                private int mc_modle;
                private String mc_pic;
                private int mc_pic_identifier;
                private Object product_name;
                private Object product_nowprice;
                private Object product_price;
                private int singlepid;
                private Object sub;
                private Object subtitle;
                private Object title;

                @Override
                public String toString() {
                    return "SubBean{" +
                            "categorieName='" + categorieName + '\'' +
                            ", categoriePic='" + categoriePic + '\'' +
                            ", id='" + id + '\'' +
                            ", issingle=" + issingle +
                            ", mc_id=" + mc_id +
                            ", mc_isshow=" + mc_isshow +
                            ", mc_link='" + mc_link + '\'' +
                            ", mc_modle=" + mc_modle +
                            ", mc_pic='" + mc_pic + '\'' +
                            ", mc_pic_identifier=" + mc_pic_identifier +
                            ", product_name=" + product_name +
                            ", product_nowprice=" + product_nowprice +
                            ", product_price=" + product_price +
                            ", singlepid=" + singlepid +
                            ", sub=" + sub +
                            ", subtitle=" + subtitle +
                            ", title=" + title +
                            '}';
                }

                public String getCategorieName() {
                    return categorieName;
                }

                public void setCategorieName(String categorieName) {
                    this.categorieName = categorieName;
                }

                public String getCategoriePic() {
                    return categoriePic;
                }

                public void setCategoriePic(String categoriePic) {
                    this.categoriePic = categoriePic;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getIssingle() {
                    return issingle;
                }

                public void setIssingle(int issingle) {
                    this.issingle = issingle;
                }

                public int getMc_id() {
                    return mc_id;
                }

                public void setMc_id(int mc_id) {
                    this.mc_id = mc_id;
                }

                public Object getMc_isshow() {
                    return mc_isshow;
                }

                public void setMc_isshow(Object mc_isshow) {
                    this.mc_isshow = mc_isshow;
                }

                public String getMc_link() {
                    return mc_link;
                }

                public void setMc_link(String mc_link) {
                    this.mc_link = mc_link;
                }

                public int getMc_modle() {
                    return mc_modle;
                }

                public void setMc_modle(int mc_modle) {
                    this.mc_modle = mc_modle;
                }

                public String getMc_pic() {
                    return mc_pic;
                }

                public void setMc_pic(String mc_pic) {
                    this.mc_pic = mc_pic;
                }

                public int getMc_pic_identifier() {
                    return mc_pic_identifier;
                }

                public void setMc_pic_identifier(int mc_pic_identifier) {
                    this.mc_pic_identifier = mc_pic_identifier;
                }

                public Object getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(Object product_name) {
                    this.product_name = product_name;
                }

                public Object getProduct_nowprice() {
                    return product_nowprice;
                }

                public void setProduct_nowprice(Object product_nowprice) {
                    this.product_nowprice = product_nowprice;
                }

                public Object getProduct_price() {
                    return product_price;
                }

                public void setProduct_price(Object product_price) {
                    this.product_price = product_price;
                }

                public int getSinglepid() {
                    return singlepid;
                }

                public void setSinglepid(int singlepid) {
                    this.singlepid = singlepid;
                }

                public Object getSub() {
                    return sub;
                }

                public void setSub(Object sub) {
                    this.sub = sub;
                }

                public Object getSubtitle() {
                    return subtitle;
                }

                public void setSubtitle(Object subtitle) {
                    this.subtitle = subtitle;
                }

                public Object getTitle() {
                    return title;
                }

                public void setTitle(Object title) {
                    this.title = title;
                }
            }
        }
    }
}
