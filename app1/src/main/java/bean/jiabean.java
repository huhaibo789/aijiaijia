package bean;

/**
 * Created by 胡海波 on 2016/9/24.
 */
public class jiabean {

    /**
     * op : 1
     * trsuid : 46
     * trsutype : null
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
        private int trsuid;
        private Object trsutype;

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public int getTrsuid() {
            return trsuid;
        }

        public void setTrsuid(int trsuid) {
            this.trsuid = trsuid;
        }

        public Object getTrsutype() {
            return trsutype;
        }

        public void setTrsutype(Object trsutype) {
            this.trsutype = trsutype;
        }
    }
}
