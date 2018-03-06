package Urlutil;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.LoginActivity;
import com.example.administrator.myyushi.NewsousuoActivity;
import com.example.administrator.myyushi.WebviewActivity;
import com.example.administrator.myyushi.h5BaseActivity;
import com.example.administrator.myyushi.h5detailsActivity;
import com.example.administrator.myyushi.h5exciseActivity;
import com.example.administrator.myyushi.h5newSousuoActivity;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu2;
import bean.gouwu3;
import bean.h5gouwu;
import utils.DBHelper4;
import utils.DBHelper5;
import utils.DBHelper6;
import utils.FileUtils;
import utils.FileUtils1;
import utils.FileUtils19;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.FileUtils29;
import utils.Fileutils18;

/**
 * Created by misshu on 2017/7/5/005.
 */
public class wevb {
    private RequestQueue queue;
    String result13;
    private Context context;
    private String jieguo = "0";
    ArrayList<String> h5id=new ArrayList<>();
    ArrayList<String> h5idnum=new ArrayList<>();
    ArrayList<String> h5skuid=new ArrayList<>();
    ArrayList<String> h5skuisok=new ArrayList<>();
    List<h5gouwu> h5shop= new ArrayList<>();
    private JSONObject resposeobject;
    String wangzhi;
    public  void web(final Context context, final String url){
        this.context=context;
        Log.i("hahss", "web: "+url);
        postshuju(context);
        if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?weblink")){
            Log.i("gdgdye", "web: "+"hds");
            String[] strArray = url.split("weblink=");
            String name=strArray[strArray.length-1];  //分割的网址
            String[] strname=name.split("webTitle="); //分割标题
            String title=strname[strname.length-1]; //标题
            if(name.contains("https://h5.aijiaijia.com/fastCreate2.html?")){
                Log.i("hushaa1", "web: "+"dssss");
                FileUtils29 file=new FileUtils29();
                String locationcityname=file.readDataFromFile(context);
                String url1="https://h5.aijiaijia.com/fastCreate2.html?gid=0"+"&locationcity="+locationcityname;
                Intent intent = new Intent(context, WebviewActivity.class);
                try {
                    String Title = URLDecoder.decode(title, "UTF-8");
                    intent.putExtra("shoping", url1);
                    Log.i("hushaa", "web: "+url1);
                    intent.putExtra("xingming", Title);
                    context.startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(name.contains("https://h5.aijiaijia.com/sceneshop?")){
                Log.i("meizid", "web: "+"dsdesd");
                FileUtils29 file=new FileUtils29();
                String locationcityname=file.readDataFromFile(context);
                String wangzhi=strname[strname.length-2];//字网址
                String url1=wangzhi+"&&locationcity="+locationcityname;
                Intent intent = new Intent(context, WebviewActivity.class);
                try {
                    String Title = URLDecoder.decode(title, "UTF-8");
                    intent.putExtra("shoping", url1);
                    intent.putExtra("xingming", Title);
                    context.startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    String strUTF8 = URLDecoder.decode(name, "UTF-8");
                    String Title = URLDecoder.decode(title, "UTF-8");
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra("shoping", strUTF8);
                    Log.i("hushaa2", "web: "+strUTF8);
                    intent.putExtra("xingming", Title);
                    context.startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }


        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?pid")){
            String[] strArray = url.split("pid=");
            String uid=strArray[strArray.length-1]; //pid
            Intent intent = new Intent(context, h5detailsActivity.class);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?brandid")){
            Log.i("weids", "web: "+url);
            String[] strArray = url.split("brandid=");
            String brandid=strArray[strArray.length-1]; //brandid
            try {
                String strUTF8 = URLDecoder.decode(brandid, "UTF-8");
                Intent intent = new Intent(context, h5newSousuoActivity.class);
                intent.putExtra("brandid", strUTF8);
                context.startActivity(intent);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?keyword")){
            String[] strArray = url.split("keyword=");
            String keyword=strArray[strArray.length-1];
            try {
                String strUTF8 = URLDecoder.decode(keyword, "UTF-8");
                Intent intent = new Intent(context, NewsousuoActivity.class);
                intent.putExtra("etfind", strUTF8);
                intent.putExtra("isflag","1");
                context.startActivity(intent);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?CustomerService")){
            FileUtils ful = new FileUtils();
            String dd = ful.readDataFromFile(context);
            String ww ="商品";
            String title = "在线客服";
            // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
            // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
            ConsultSource source = new ConsultSource(dd, ww, "custom information string");
            // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
            Unicorn.openServiceActivity(context, // 上下文
                    title, // 聊天窗口的标题
                    source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
            );
        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/api_login")){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);

        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?balance")){
            Log.i("heiheis", "web: "+url);
            final String[] strArray = url.split("balance=");
            queue = Volley.newRequestQueue(context);
            String urls = "https://api.aijiaijia.com/api_sellorder_statistics";
            StringRequest post = new StringRequest(
                    StringRequest.Method.POST,
                    urls,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str = response.toString().trim();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(str);
                                JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                result13 = resposeobject.getString("op");
                                if(result13.equals("1")&&url.matches(".*?[\\d]+.*?")){
                                    h5id.clear();
                                    h5idnum.clear();
                                    h5skuid.clear();
                                    h5skuisok.clear();
                                    int count=0;
                                    String info = strArray[strArray.length - 1];
                                    Log.i("chunren", "shouldOverrideUrlLoading: "+info);
                                    String[] h5info= info.split("PN");
                                    Log.i("chunren1", "shouldOverrideUrlLoading: "+h5info);
                                    String newnum=h5info[h5info.length-1]; //num
                                    String newid=h5info[h5info.length-2]; //id
                                    String[] h5proid= newid.split("p");
                                     /*   String[] h5num=newnum.split("n");*/
                                    String[] h5num=newnum.split("NS");
                                    String skuall=h5num[h5num.length-1];
                                    String skunum=h5num[h5num.length-2];
                                    String[] skunum1=skunum.split("n");
                                    String[] skusplit=skuall.split("SI");
                                    String skuid=skusplit[skusplit.length-2];
                                    String isok=skusplit[skusplit.length-1];
                                    String[] isoks=isok.split("i");
                                    String[] skuids=skuid.split("s");
                                    Log.i("zhaneca", "shouldOverrideUrlLoading: "+h5proid);
                                    Log.i("huhu", "shouldOverrideUrlLoading: "+newnum);
                                    Log.i("huhua", "shouldOverrideUrlLoading: "+newid);
                                    Log.i("caoji", "shouldOverrideUrlLoading: "+h5proid.length);
                                    for (int i = 0; i <h5proid.length ; i++) {
                                        Log.i("heihewi", "shouldOverrideUrlLoading: "+h5proid.length);
                                        h5id.add(h5proid[i]);
                                        h5idnum.add(skunum1[i]);
                                        h5skuid.add(skuids[i]);
                                        h5skuisok.add(isoks[i]);
                                        Log.i("qizha", "shouldOverrideUrlLoading: "+h5id);
                                        Log.i("qizha1", "shouldOverrideUrlLoading: "+h5idnum);
                                        Log.i("qizha2", "shouldOverrideUrlLoading: "+h5skuid);
                                        Log.i("qizha3", "shouldOverrideUrlLoading: "+h5skuisok);
                                    }
                                    DBHelper6 hell = new DBHelper6(context);
                                    h5shop = hell.queryAll();
                                    if (h5shop.size() != 0) {
                                        for (int j = 0; j < h5shop.size(); j++) {
                                            hell.delete(h5shop.get(j).get_id());
                                        }
                                    }
                                    if (h5id!= null &&h5id.size() > 0) {
                                        for (int i = 0; i < h5id.size(); i++) {
                                            count++;
                                            DBHelper6 helper5 = new DBHelper6(context);
                                            helper5.insert(new h5gouwu(h5id.get(i),String.valueOf(h5idnum.get(i)),String.valueOf(h5skuid.get(i)),String.valueOf(h5skuisok.get(i))));
                                        }
                                    }
                                    DBHelper5 hel5=new DBHelper5(context);
                                    List<gouwu3> goud = new ArrayList<>();
                                    goud=hel5.queryAll();
                                    if(goud.size()!=0){
                                        for (int i = 0; i <goud.size() ; i++) {
                                            hel5.delete(goud.get(i).get_id());
                                        }
                                    }
                                    DBHelper4 hel4=new DBHelper4(context);
                                    List<gouwu2> gou2 = new ArrayList<>();
                                    gou2=hel4.queryAll();
                                    if(gou2.size()!=0){
                                        for (int j = 0; j <gou2.size() ; j++) {
                                            hel4.delete(gou2.get(j).get_id());
                                        }
                                    }
                                    FileUtils1 ggsa = new FileUtils1();
                                    ggsa.saveDataToFile(context, String.valueOf(count));
                                    String ss = "请选择";
                                    String ss1 = "Null";
                                    String ss2 = "Null";
                                    String aa = "没有使用积分";
                                    FileUtils19 qq = new FileUtils19();
                                    qq.saveDataToFile(context, aa);
                                    FileUtils21 kk1 = new FileUtils21();
                                    kk1.saveDataToFile(context, ss2);
                                    FileUtils20 kk = new FileUtils20();
                                    kk.saveDataToFile(context, ss1);
                                    Fileutils18 gg = new Fileutils18();
                                    gg.saveDataToFile(context, ss);
                                    Intent intent = new Intent(context, h5BaseActivity.class);
                                    context.startActivity(intent);
                                }else if(result13.equals("6")){
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                }else {
                                    Toast.makeText(context, "至少选择一件商品", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("cookie", Constant.localCookie);
                        Log.d("调试88", "headers----------------" + headers);
                        return headers;
                    } else {
                        return super.getHeaders();
                    }
                }
            };

            queue.add(post);
            Log.i("aifddfg", "shouldOverrideUrlLoading: "+"balance");
        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?addcart")){
            String[] strArray = url.split("addcart=");
            final String name = strArray[strArray.length - 1];
            jieguo="0";
            queue = Volley.newRequestQueue(context);
            String infourl = "https://api.aijiaijia.com/api_sellorder_statistics";
            StringRequest post = new StringRequest(
                    StringRequest.Method.POST,
                    infourl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str = response.toString().trim();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(str);
                                JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                result13 = resposeobject.getString("op");
                                String[] stre=name.split("PN");
                                wangzhi=stre[stre.length-2];
                                postdata1();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("cookie", Constant.localCookie);
                        Log.d("调试88", "headers----------------" + headers);
                        return headers;
                    } else {
                        return super.getHeaders();
                    }
                }
            };

            queue.add(post);
        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?nearshop=1")){
            Intent intent=new Intent(context, h5exciseActivity.class);
            intent.putExtra("nearshop","1");
            context.startActivity(intent);

        }else if(url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?nearshop=2")){
            Intent intent=new Intent(context, h5exciseActivity.class);
            intent.putExtra("nearshop","2");
            context.startActivity(intent);
        }
    }

    private void postdata1() {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_shopcart";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("zuimeidd", "onResponse: "+str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            resposeobject = jsonObject.getJSONObject("responseJson");
                            Log.i("zuimeid", "onResponse: "+result13);
                            if (result13.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                String id1 = String.valueOf(wangzhi);
                                Log.i("zuimei", "onResponse: "+id1);
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String s7 = object.getString("pid");
                                    Log.i("qihea", "onResponse: "+s7);
                                    if (id1.equals(s7)) {
                                        Log.i("zuimei1", "onResponse: "+id1);
                                        jieguo = "1";
                                        Toast toast = Toast.makeText(context, "购物车已有该商品", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                                if (!jieguo.equals("1")) {
                                    Log.i("zuimei2", "onResponse: "+id1);
                                    if (wangzhi != null) {
                                        final String id = String.valueOf(wangzhi);
                                        queue = Volley.newRequestQueue(context);
                                        String url = "https://api.aijiaijia.com/api_shopcart_add";
                                        StringRequest post = new StringRequest(
                                                StringRequest.Method.POST,
                                                url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        String str = response.toString().trim();
                                                        JSONObject jsonObject = null;
                                                        try {
                                                            jsonObject = new JSONObject(str);
                                                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                                            String result3 = resposeobject.getString("op");
                                                            Log.i("zhabis", "onResponse: "+result3);
                                                            if (result3.equals("0")) {
                                                                Toast.makeText(context, "加入失败", Toast.LENGTH_SHORT).show();
                                                            } else if (result3.equals("1")) {
                                                                // setAnim(v);
                                                                Toast.makeText(context, "加入成功", Toast.LENGTH_SHORT).show();

                                                            } else if (result3.equals("6")) {
                                                                Toast toast = Toast.makeText(context, "未登录", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                                Intent intent = new Intent(context, LoginActivity.class);
                                                                context.startActivity(intent);
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                    }
                                                }
                                        ) {
                                            //通过重写此对象的getParams方法设置请求条件
                                            //将所有的请求条件存入返回值的map对象中
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> map = new HashMap<>();
                                                Log.i("aiya", "getParams: "+wangzhi);
                                                Log.i("zhanan", "getParams: "+id);
                                                Log.i("zhanan", "getParams: "+map);
                                                map.put("pids", id);
                                                map.put("nums", "1");
                                                return map;
                                            }

                                            @Override
                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                                                    HashMap<String, String> headers = new HashMap<String, String>();
                                                    headers.put("cookie", Constant.localCookie);
                                                    Log.d("调试88", "headers----------------" + headers);
                                                    return headers;
                                                } else {
                                                    return super.getHeaders();
                                                }
                                            }
                                        };
                                        queue.add(post);
                                    } else {
                                        Toast toast = Toast.makeText(context, "请检查网络!!!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }

                                }
                            } else {
                                Log.i("ufnfj", "onResponse: "+"dsde");
                                Toast toast = Toast.makeText(context, "未登录", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsda", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };
        queue.add(post);
    }

    private void postshuju(Context context) {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_sellorder_statistics";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            result13 = resposeobject.getString("op");
                            Log.i("jiayou", "onResponse: "+result13);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };

        queue.add(post);
    }

}
