package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.appointmentadapter;
import adapter.shoucangadapter;
import bean.gouwu1;
import bean.gouwu3;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.DBHelper3;
import utils.DBHelper5;

public class AppointmentActivity extends AppCompatActivity {
    @Bind(R.id.end_iv)
    ImageView endIv;
    @Bind(R.id.end_xian)
    View endXian;
    @Bind(R.id.appoint_lv)
    ListView appointLv;
    private appointmentadapter adapter;
    private RequestQueue queue;
    private ArrayList<String> picture=new ArrayList<>();
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> product=new ArrayList<>();
    private  ArrayList<String> nowprice=new ArrayList<>();
    private ArrayList<String> beforeprice=new ArrayList<>();
    private ArrayList<String> num1=new ArrayList<>();
    private List<gouwu3> goud = new ArrayList<>();
    private ArrayList<String> ide=new ArrayList<>();
    private  ArrayList<String> num=new ArrayList<>();
    private String id;
    private String nums="1";
    private   String message;
    private  String ids;
    Handler handle=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String information=intent.getStringExtra("gouwu");
        message=intent.getStringExtra("message");
        ids=intent.getStringExtra("id");
        if(information.equals("2")){
            postdata();
        }else if(information.equals("3")){
            Log.i("ifjs", "onCreate: "+"单品");
            postdata1();
        }
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setadapter();
                setlistener();
            }
        },1000);


    }
    private void setlistener() {
        endIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setadapter() {
        ImageLoader loader=((Myapp)AppointmentActivity.this.getApplication()).getLoader();
       adapter=new appointmentadapter(AppointmentActivity.this,loader,picture,title, product, nowprice,beforeprice, num1);
        appointLv.setAdapter(adapter);
    }
    private void postdata1() {
        picture.clear();
        title.clear();
        product.clear();
        nowprice.clear();
        beforeprice.clear();
        num.clear();
        queue = Volley.newRequestQueue(AppointmentActivity.this);
        String urls = "https://api.aijiaijia.com/api_appointorder_hasproducts";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu56sdee", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            Log.i("heng", "onResponse: " + result3);
                            if (result3.equals("0")) {
                                Toast.makeText(AppointmentActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("product_list");
                                for (int i = 0; i <jsonarry.length() ; i++) {
                                    JSONObject object=jsonarry.getJSONObject(i);
                                    String dizhi=object.getString("product_pic");
                                    picture.add(dizhi);
                                    Log.i("yuy1", "onResponse: "+dizhi);
                                    String title1=object.getString("product_name");
                                    title.add(title1);
                                    String producation=object.getString("product_bn");
                                    product.add(producation);
                                    String nowprice1=object.getString("product_nowprice");
                                    nowprice.add(nowprice1);
                                    String price=object.getString("product_price");
                                    beforeprice.add(price);
                                    String number=object.getString("num");
                                    num1.add(number);
                                }
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", "1");
                map.put("cityname", message);
                Log.i("eide", "getParams: " + message);
                if (ids != null) {
                    map.put("pids", ids);
                }
                map.put("nums", "1");
                return map;
            }

        };
        queue.add(post);
    }
    private void postdata() {
       picture.clear();
        title.clear();
        product.clear();
        nowprice.clear();
        beforeprice.clear();
        num.clear();
        queue = Volley.newRequestQueue(this);
        String url="https://api.aijiaijia.com/api_appointorder_hasproducts";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("shoucang566", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        JSONObject jsonobject=null;
                        try {
                            jsonobject=new JSONObject(str);
                            JSONObject resposeobject =   jsonobject.getJSONObject("responseJson");
                            JSONArray jsonarry=resposeobject.getJSONArray("product_list");
                            for (int i = 0; i <jsonarry.length() ; i++) {
                                JSONObject object=jsonarry.getJSONObject(i);
                                String dizhi=object.getString("product_pic");
                                picture.add(dizhi);
                                Log.i("yuy1", "onResponse: "+dizhi);
                                String title1=object.getString("product_name");
                                title.add(title1);
                                String producation=object.getString("product_bn");
                                product.add(producation);
                                String nowprice1=object.getString("product_nowprice");
                                nowprice.add(nowprice1);
                                String price=object.getString("product_price");
                                beforeprice.add(price);
                                String number=object.getString("num");
                                num1.add(number);


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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                DBHelper5 helper5 = new DBHelper5(AppointmentActivity.this);
                goud = helper5.queryAll();
                for (int i = 0; i <goud.size() ; i++) {
                    ide.add(goud.get(i).getProductid());
                    num.add(goud.get(i).getNum());
                }
                String str=ide.toString();
                int len=str.length()-1;
                String regex="\\s*,\\s*";
                id= str.substring(1,len).replaceAll(regex,",");
                map.put("page", "1");
                map.put("cityname", message);
                map.put("pids", id);
                String number=num.toString();
                int len1=number.length()-1;
                String regex1="\\s*,\\s*";
                nums= number.substring(1,len1).replaceAll(regex1,",");
                map.put("nums",nums);
                Log.i("pigu", "getParams: "+nums);
                return map;
            }
        };

        queue.add(post);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(this);
    }
}
