package bean;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡海波 on 2016/9/23.
 */
public class UserService {
    /**
     * 验证用户登录是否合法
     * 返回值：请求是否成功
     */
    public static boolean check(String name, String pass) {
        String path="http://135.32.89.17:8080/lss/UserServlet";
        //将用户名和密码放入HashMap中
        Map<String,String> params=new HashMap<String,String>();
        params.put("userName", name);
        params.put("passWord", pass);
        try {
            return sendGETRequest(path,params,"UTF-8");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    private static boolean sendGETRequest(String path,
                                          Map<String, String> params,String encode) throws MalformedURLException, IOException {
        StringBuilder url=new StringBuilder(path);
        url.append("?");
        for(Map.Entry<String, String> entry:params.entrySet())
        {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(),encode));
            url.append("&");
        }
        //删掉最后一个&
        url.deleteCharAt(url.length()-1);
        HttpURLConnection conn=(HttpURLConnection)new URL(url.toString()).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode()==200)
        {
            return true;
        }
        return false;
    }
}
