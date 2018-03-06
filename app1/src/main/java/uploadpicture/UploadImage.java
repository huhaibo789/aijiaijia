package uploadpicture;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import android.util.Log;
/** * * 实现文件上传的工具类
 * @Title:
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2011
 * @Company:易程科技股份有限公司
 * @Date:2012-7-2
 * @author longgangbai
 * @version 1.0
 */
public class UploadImage {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000; //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
    public static final String SUCCESS="1"; public static final String FAILURE="0";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    /** * android上传文件到服务器
     * @param file 需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file,String RequestURL,String aa ) {
        Log.i("fans", "uploadFile: "+file);
        Log.i("fans1", "uploadFile: "+RequestURL);
        Log.i("heihei", "uploadFile: "+aa);
      //  String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
      //  String CONTENT_TYPE = "multipart/form-data"; //内容类型
        String PREFIX = "--" ;
        String LINE_END = "\r\n";
        try {
            URL url = new URL(RequestURL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection(); conn.setReadTimeout(TIME_OUT); conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false); //不允许使用缓存
            conn.setRequestMethod("POST"); //请求方式
            conn.setRequestProperty("Charset", CHARSET);
            // 取得sessionid.
            String cookieval = conn.getHeaderField("set-cookie");
           /* if(!cookieval.equals("null")) {
             String   sessionid = cookieval.substring(0, cookieval.indexOf(";"));
                conn.setRequestProperty("cookie",sessionid);
                Log.i("tongkused", "loginByPost: "+sessionid);
            }*/

            Log.i("tongkusede1", "loginByPost: "+cookieval);
            //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            Log.i("aiyayou", "uploadFile: "+"juye");
            Log.i("heiyas", "uploadFile: "+file);
            if(!file.equals("null")) {
                Log.i("zhuyi3", "uploadFile: "+aa);
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam=conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY); sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\""+aa + "\"; filename=\""+file.getName()+"\""+LINE_END);
                Log.i("zhuyi", "uploadFile: "+aa);
                Log.i("zhuyi2", "uploadFile: "+file.getName());
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                Log.i("zhaozisha1", "uploadFile: "+dos);
                /**
                 * 获取响应码 200=成功
                 * 当响应成功，获取响应的流
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int res = conn.getResponseCode();
                            Log.i("baozheng", "run: "+res);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
             /*   int res = conn.getResponseCode();
                Log.e("suindue", "response code:"+res);
                if(res==200)
                {
                    return SUCCESS;
                }*/
            }else {
                Log.i("heiyas4", "uploadFile: "+file);
            }
        } catch (MalformedURLException e)
        { e.printStackTrace(); }
        catch (IOException e)
        { e.printStackTrace(); }
        return FAILURE;
    }
}