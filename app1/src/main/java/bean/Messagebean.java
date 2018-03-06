package bean;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class Messagebean {

    /**
     * op : 6
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

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }
    }

    @Override
    public String toString() {
        return "Messagebean{" +
                "responseJson=" + responseJson +
                '}';
    }
}
