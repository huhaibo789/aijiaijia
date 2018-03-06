package bean;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by 胡海波 on 2016/9/27.
 */
public class NetUtil2 {
    public static String LoginOfPost(String shuju,String shuju2){
        HttpURLConnection conn=null;
        try {
            URLDecoder.decode("utf-8");
            //URL url=new URL("http://api.aijiaijia.com/api_getKey");
            URL url=new URL("http://api.aijiaijia.com/api_coupons_all");
            conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            //post请求的参数
            //String data="username="+username+"&password="+password;
            String data="page="+shuju+"&trsuid="+shuju2;
            Log.i("cklt1", "LoginOfPost: "+data.toString());
            OutputStream out=conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();


            conn.connect();
            int code=conn.getResponseCode();
            if(code==200){
                InputStream is=conn.getInputStream();
                String state=getStringFromInputStream(is);
                Log.i("cklt", "LoginOfPost: "+state.toString());
                return state;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }



        return null;
    }
    /**
     * 根据输入流返回一个字符串
     * @param is
     * @return
     * @throws Exception
     */
    private static String getStringFromInputStream(InputStream is) throws Exception{

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buff=new byte[1024];

        int len=-1;
        while((len=is.read(buff))!=-1){
            baos.write(buff, 0, len);
        }
        is.close();
        String html=baos.toString();
        Log.i("wuyulunbi", "getStringFromInputStream: "+html);
        baos.close();


        return html;
    }
}
