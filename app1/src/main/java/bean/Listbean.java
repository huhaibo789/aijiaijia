package bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class Listbean {


    private ResponseJsonBean responseJson;

    public ResponseJsonBean getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(ResponseJsonBean responseJson) {
        this.responseJson = responseJson;
    }

    public static class ResponseJsonBean {
        /**
         * categoriePic : http://192.168.0.222/shoptrs_p/web/images/mobile/activity/2016-07-13/pro_d2b6ab.png
         * id : http://www.163.com
         * issingle : 0
         * mc_id : 1
         * mc_isshow : null
         * mc_link : http://www.163.com
         * mc_modle : 1
         * mc_pic : http://192.168.0.222/shoptrs_p/web/images/mobile/activity/2016-07-13/pro_d2b6ab.png
         * mc_pic_identifier : 1
         * product_name : null
         * product_nowprice : null
         * product_price : null
         * singlepid : 0
         * sub : null
         * subtitle : null
         * title : null
         */

        private List<ListActivitylistBean> list_activitylist;
        /**
         * advpic : null
         * advtitle : {"categoriePic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-13/pro_fb8009.png","id":null,"issingle":null,"mc_id":null,"mc_isshow":null,"mc_link":null,"mc_modle":null,"mc_pic":null,"mc_pic_identifier":null,"product_name":null,"product_nowprice":null,"product_price":null,"singlepid":null,"sub":null,"subtitle":"广告二标题","title":"广告一标题test"}
         * productlist : [{"categoriePic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_7848f9.png","id":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","issingle":1,"mc_id":58,"mc_isshow":null,"mc_link":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","mc_modle":6,"mc_pic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_7848f9.png","mc_pic_identifier":3,"product_name":"汉斯格雅·高级花洒-飞羽系列-清凉水龙头","product_nowprice":699,"product_price":500,"singlepid":709,"sub":null,"subtitle":null,"title":null},{"categoriePic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_24d643.png","id":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","issingle":1,"mc_id":59,"mc_isshow":null,"mc_link":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","mc_modle":6,"mc_pic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_24d643.png","mc_pic_identifier":3,"product_name":"汉斯格雅·高级花洒-飞羽系列-清凉水龙头","product_nowprice":699,"product_price":500,"singlepid":709,"sub":null,"subtitle":null,"title":null},{"categoriePic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_1da111.png","id":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","issingle":1,"mc_id":64,"mc_isshow":null,"mc_link":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","mc_modle":6,"mc_pic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_1da111.png","mc_pic_identifier":3,"product_name":"汉斯格雅·高级花洒-飞羽系列-清凉水龙头","product_nowprice":699,"product_price":500,"singlepid":709,"sub":null,"subtitle":null,"title":null},{"categoriePic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_4fa533.png","id":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","issingle":1,"mc_id":65,"mc_isshow":null,"mc_link":"http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709","mc_modle":6,"mc_pic":"http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_4fa533.png","mc_pic_identifier":3,"product_name":"汉斯格雅·高级花洒-飞羽系列-清凉水龙头","product_nowprice":699,"product_price":500,"singlepid":709,"sub":null,"subtitle":null,"title":null}]
         */

        private List<ListBigBean> list_big;
        /**
         * categoriePic : null
         * id : http://192.168.0.222/shoptrs_p/web/bundles/crm/uploads/lunbo/2016-06-23/pro_017906.png
         * issingle : 0
         * mc_id : null
         * mc_isshow : null
         * mc_link : https://www.baidu.com
         * mc_modle : null
         * mc_pic : null
         * mc_pic_identifier : null
         * product_name : null
         * product_nowprice : null
         * product_price : null
         * singlepid : 0
         * sub : null
         * subtitle : null
         * title : null
         */

        private List<ListCarouselBean> list_carousel;

        public List<ListActivitylistBean> getList_activitylist() {
            return list_activitylist;
        }

        public void setList_activitylist(List<ListActivitylistBean> list_activitylist) {
            this.list_activitylist = list_activitylist;
        }

        public List<ListBigBean> getList_big() {
            return list_big;
        }



        public void setList_big(List<ListBigBean> list_big) {
            this.list_big = list_big;
        }

        public List<ListCarouselBean> getList_carousel() {
            return list_carousel;
        }

        public void setList_carousel(List<ListCarouselBean> list_carousel) {
            this.list_carousel = list_carousel;
        }

        public static class ListActivitylistBean {
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
                return "ListActivitylistBean{" +
                        "categoriePic='" + categoriePic + '\'' +
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

        public static class ListBigBean {
            private AdvpicBean advpic;
            /**
             * categoriePic : http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-13/pro_fb8009.png
             * id : null
             * issingle : null
             * mc_id : null
             * mc_isshow : null
             * mc_link : null
             * mc_modle : null
             * mc_pic : null
             * mc_pic_identifier : null
             * product_name : null
             * product_nowprice : null
             * product_price : null
             * singlepid : null
             * sub : null
             * subtitle : 广告二标题
             * title : 广告一标题test
             */

            private AdvtitleBean advtitle;
            /**
             * categoriePic : http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_7848f9.png
             * id : http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709
             * issingle : 1
             * mc_id : 58
             * mc_isshow : null
             * mc_link : http://192.168.0.222:8080/trsshopapi/api_products_info?pid=709
             * mc_modle : 6
             * mc_pic : http://192.168.0.222/shoptrs_p/web/images/mobile/product/2016-07-14/pro_7848f9.png
             * mc_pic_identifier : 3
             * product_name : 汉斯格雅·高级花洒-飞羽系列-清凉水龙头
             * product_nowprice : 699.0
             * product_price : 500.0
             * singlepid : 709
             * sub : null
             * subtitle : null
             * title : null
             */

            private List<ProductlistBean> productlist;

         /*   public Object getAdvpic() {
                return advpic;
            }*/

          /*  public void setAdvpic(Object advpic) {
                this.advpic = advpic;
            }*/

            public AdvpicBean getAdvpic() {
                return advpic;
            }

            public void setAdvpic(AdvpicBean advpic) {
                this.advpic = advpic;
            }

            public AdvtitleBean getAdvtitle() {
                return advtitle;
            }

            public void setAdvtitle(AdvtitleBean advtitle) {
                this.advtitle = advtitle;
            }

            public List<ProductlistBean> getProductlist() {
                return productlist;
            }

            public void setProductlist(List<ProductlistBean> productlist) {
                this.productlist = productlist;
            }
           public  static  class  AdvpicBean{
               private String categoriePic;
               private Object id;
               private Object issingle;
               private Object mc_id;
               private Object mc_isshow;
               private Object mc_link;
               private Object mc_modle;
               private Object mc_pic;
               private Object mc_pic_identifier;
               private Object product_name;
               private Object product_nowprice;
               private Object product_price;
               private Object singlepid;
               private Object sub;
               private String subtitle;
               private String title;

               public String getTitle() {
                   return title;
               }

               public void setTitle(String title) {
                   this.title = title;
               }

               public String getCategoriePic() {
                   return categoriePic;
               }

               public void setCategoriePic(String categoriePic) {
                   this.categoriePic = categoriePic;
               }

               public Object getId() {
                   return id;
               }

               public void setId(Object id) {
                   this.id = id;
               }

               public Object getIssingle() {
                   return issingle;
               }

               public void setIssingle(Object issingle) {
                   this.issingle = issingle;
               }

               public Object getMc_id() {
                   return mc_id;
               }

               public void setMc_id(Object mc_id) {
                   this.mc_id = mc_id;
               }

               public Object getMc_isshow() {
                   return mc_isshow;
               }

               public void setMc_isshow(Object mc_isshow) {
                   this.mc_isshow = mc_isshow;
               }

               public Object getMc_link() {
                   return mc_link;
               }

               public void setMc_link(Object mc_link) {
                   this.mc_link = mc_link;
               }

               public Object getMc_modle() {
                   return mc_modle;
               }

               public void setMc_modle(Object mc_modle) {
                   this.mc_modle = mc_modle;
               }

               public Object getMc_pic() {
                   return mc_pic;
               }

               public void setMc_pic(Object mc_pic) {
                   this.mc_pic = mc_pic;
               }

               public Object getMc_pic_identifier() {
                   return mc_pic_identifier;
               }

               public void setMc_pic_identifier(Object mc_pic_identifier) {
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

               public Object getSinglepid() {
                   return singlepid;
               }

               public void setSinglepid(Object singlepid) {
                   this.singlepid = singlepid;
               }

               public Object getSub() {
                   return sub;
               }

               public void setSub(Object sub) {
                   this.sub = sub;
               }

               public String getSubtitle() {
                   return subtitle;
               }

               public void setSubtitle(String subtitle) {
                   this.subtitle = subtitle;
               }

               @Override
               public String toString() {
                   return "AdvpicBean{" +
                           "categoriePic='" + categoriePic + '\'' +
                           ", id=" + id +
                           ", issingle=" + issingle +
                           ", mc_id=" + mc_id +
                           ", mc_isshow=" + mc_isshow +
                           ", mc_link=" + mc_link +
                           ", mc_modle=" + mc_modle +
                           ", mc_pic=" + mc_pic +
                           ", mc_pic_identifier=" + mc_pic_identifier +
                           ", product_name=" + product_name +
                           ", product_nowprice=" + product_nowprice +
                           ", product_price=" + product_price +
                           ", singlepid=" + singlepid +
                           ", sub=" + sub +
                           ", subtitle='" + subtitle + '\'' +
                           ", title='" + title + '\'' +
                           '}';
               }
           }
            public static class AdvtitleBean {
                private String categoriePic;
                private Object id;
                private Object issingle;
                private Object mc_id;
                private Object mc_isshow;
                private Object mc_link;
                private Object mc_modle;
                private Object mc_pic;
                private Object mc_pic_identifier;
                private Object product_name;
                private Object product_nowprice;
                private Object product_price;
                private Object singlepid;
                private Object sub;
                private String subtitle;
                private String title;

                @Override
                public String toString() {
                    return "AdvtitleBean{" +
                            "categoriePic='" + categoriePic + '\'' +
                            ", id=" + id +
                            ", issingle=" + issingle +
                            ", mc_id=" + mc_id +
                            ", mc_isshow=" + mc_isshow +
                            ", mc_link=" + mc_link +
                            ", mc_modle=" + mc_modle +
                            ", mc_pic=" + mc_pic +
                            ", mc_pic_identifier=" + mc_pic_identifier +
                            ", product_name=" + product_name +
                            ", product_nowprice=" + product_nowprice +
                            ", product_price=" + product_price +
                            ", singlepid=" + singlepid +
                            ", sub=" + sub +
                            ", subtitle='" + subtitle + '\'' +
                            ", title='" + title + '\'' +
                            '}';

                }

                public String getCategoriePic() {
                    return categoriePic;
                }

                public void setCategoriePic(String categoriePic) {
                    this.categoriePic = categoriePic;
                }

                public Object getId() {
                    return id;
                }

                public void setId(Object id) {
                    this.id = id;
                }

                public Object getIssingle() {
                    return issingle;
                }

                public void setIssingle(Object issingle) {
                    this.issingle = issingle;
                }

                public Object getMc_id() {
                    return mc_id;
                }

                public void setMc_id(Object mc_id) {
                    this.mc_id = mc_id;
                }

                public Object getMc_isshow() {
                    return mc_isshow;
                }

                public void setMc_isshow(Object mc_isshow) {
                    this.mc_isshow = mc_isshow;
                }

                public Object getMc_link() {
                    return mc_link;
                }

                public void setMc_link(Object mc_link) {
                    this.mc_link = mc_link;
                }

                public Object getMc_modle() {
                    return mc_modle;
                }

                public void setMc_modle(Object mc_modle) {
                    this.mc_modle = mc_modle;
                }

                public Object getMc_pic() {
                    return mc_pic;
                }

                public void setMc_pic(Object mc_pic) {
                    this.mc_pic = mc_pic;
                }

                public Object getMc_pic_identifier() {
                    return mc_pic_identifier;
                }

                public void setMc_pic_identifier(Object mc_pic_identifier) {
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

                public Object getSinglepid() {
                    return singlepid;
                }

                public void setSinglepid(Object singlepid) {
                    this.singlepid = singlepid;
                }

                public Object getSub() {
                    return sub;
                }

                public void setSub(Object sub) {
                    this.sub = sub;
                }

                public String getSubtitle() {
                    return subtitle;
                }

                public void setSubtitle(String subtitle) {
                    this.subtitle = subtitle;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public static class ProductlistBean {
                private String categoriePic;
                private String id;
                private int issingle;
                private int mc_id;
                private Object mc_isshow;
                private String mc_link;
                private int mc_modle;
                private String mc_pic;
                private int mc_pic_identifier;
                private String product_name;
                private double product_nowprice;
                private double product_price;
                private int singlepid;
                private Object sub;
                private Object subtitle;
                private Object title;

                @Override
                public String toString() {
                    return "ProductlistBean{" +
                            "categoriePic='" + categoriePic + '\'' +
                            ", id='" + id + '\'' +
                            ", issingle=" + issingle +
                            ", mc_id=" + mc_id +
                            ", mc_isshow=" + mc_isshow +
                            ", mc_link='" + mc_link + '\'' +
                            ", mc_modle=" + mc_modle +
                            ", mc_pic='" + mc_pic + '\'' +
                            ", mc_pic_identifier=" + mc_pic_identifier +
                            ", product_name='" + product_name + '\'' +
                            ", product_nowprice=" + product_nowprice +
                            ", product_price=" + product_price +
                            ", singlepid=" + singlepid +
                            ", sub=" + sub +
                            ", subtitle=" + subtitle +
                            ", title=" + title +
                            '}';
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

                public double getProduct_price() {
                    return product_price;
                }

                public void setProduct_price(double product_price) {
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

        public static class ListCarouselBean {
            private Object categoriePic;
            private String id;
            private int issingle;
            private Object mc_id;
            private Object mc_isshow;
            private String mc_link;
            private Object mc_modle;
            private Object mc_pic;
            private Object mc_pic_identifier;
            private Object product_name;
            private Object product_nowprice;
            private Object product_price;
            private int singlepid;
            private Object sub;
            private Object subtitle;
            private Object title;

            @Override
            public String toString() {
                return "ListCarouselBean{" +
                        "categoriePic=" + categoriePic +
                        ", id='" + id + '\'' +
                        ", issingle=" + issingle +
                        ", mc_id=" + mc_id +
                        ", mc_isshow=" + mc_isshow +
                        ", mc_link='" + mc_link + '\'' +
                        ", mc_modle=" + mc_modle +
                        ", mc_pic=" + mc_pic +
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

            public Object getCategoriePic() {
                return categoriePic;
            }

            public void setCategoriePic(Object categoriePic) {
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

            public Object getMc_id() {
                return mc_id;
            }

            public void setMc_id(Object mc_id) {
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

            public Object getMc_modle() {
                return mc_modle;
            }

            public void setMc_modle(Object mc_modle) {
                this.mc_modle = mc_modle;
            }

            public Object getMc_pic() {
                return mc_pic;
            }

            public void setMc_pic(Object mc_pic) {
                this.mc_pic = mc_pic;
            }

            public Object getMc_pic_identifier() {
                return mc_pic_identifier;
            }

            public void setMc_pic_identifier(Object mc_pic_identifier) {
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
