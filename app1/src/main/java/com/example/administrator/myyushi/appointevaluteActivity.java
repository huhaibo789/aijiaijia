package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.appointevaluteadapter;
import adapter.shopguide;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;

public class appointevaluteActivity extends AppCompatActivity {
    @Bind(R.id.appointyuyue_iv)
    ImageView appointyuyueIv;
    @Bind(R.id.apponitYuyue_kefu)
    ImageView apponitYuyueKefu;
    @Bind(R.id.appointyuyue_view)
    View appointyuyueView;
    @Bind(R.id.appoint_lv)
    ListView appointLv;
    private RequestQueue queue;
    private String ss;
    private String result;
    private String result1;
    private ArrayList<String> ordersn=new ArrayList<>();
    private ArrayList<String> picture=new ArrayList<>();
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> pid=new ArrayList<>();
    private appointevaluteadapter evaluteadapter;
    private String different;
    Handler handle=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointevalute);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        result = intent.getStringExtra("orderid");
        result1=intent.getStringExtra("orderid1");
        if(result!=null){
            postdata();
            different="1";
        }else {
            postdata1();
            different="2";
        }
        setlistener();
        StatusBarCompat.setStatusBarColor(appointevaluteActivity.this, Color.parseColor("#fbd23a"), true);
    }
    private void postdata1() {
        picture.clear();
        ordersn.clear();
        title.clear();
        pid.clear();
        queue = Volley.newRequestQueue(appointevaluteActivity.this);
        String url = "https://api.aijiaijia.com/api_comments_sellproductlist";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                JSONArray jsonarry=resposeobject.getJSONArray("waitforcommentproducts");
                                for (int i = 0; i <jsonarry.length() ; i++) {
                                    JSONObject object=jsonarry.getJSONObject(i);
                                    picture.add(object.getString("product_pic"));
                                    ordersn.add(object.getString("order_sn"));
                                    title.add(object.getString("product_name"));
                                    pid.add(object.getString("id"));
                                }
                                setadapter();
                            } else if (op.equals("6")) {
                                Toast.makeText(appointevaluteActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("oid", result1);
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
    }
    private void setlistener() {
        appointyuyueIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        apponitYuyueKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://api.aijiaijia.com/api_comments_appointproductlist?"+"oid="+result;
                String ww="导购订单";
                String title = "在线客服";
                // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                ConsultSource source = new ConsultSource(url, ww, "custom information string");
                // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                Unicorn.openServiceActivity(appointevaluteActivity.this, // 上下文
                        title, // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
            }
        });

    }
    private void setadapter() {
        ImageLoader loader = ((Myapp) appointevaluteActivity.this.getApplication()).getLoader();
        if(result!=null){
            evaluteadapter = new appointevaluteadapter(appointevaluteActivity.this, loader,different,pid,result,picture,ordersn,title);
        }else {
            evaluteadapter = new appointevaluteadapter(appointevaluteActivity.this, loader,different,pid,result1,picture,ordersn,title);
        }
        appointLv.setAdapter(evaluteadapter);
    }
    private void postdata() {
        picture.clear();
        ordersn.clear();
        title.clear();
        pid.clear();
        queue = Volley.newRequestQueue(appointevaluteActivity.this);
        String url = "https://api.aijiaijia.com/api_comments_appointproductlist";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfse", "onResponse: post  success " + response);
                        ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(appointevaluteActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                                JSONArray jsonarry=resposeobject.getJSONArray("waitforcommentproducts");
                                for (int i = 0; i <jsonarry.length() ; i++) {
                                    JSONObject object=jsonarry.getJSONObject(i);
                                     picture.add(object.getString("product_pic"));
                                     ordersn.add(object.getString("order_sn"));
                                     title.add(object.getString("product_name"));
                                     pid.add(object.getString("id"));
                                }
                                setadapter();
                            } else if (op.equals("6")) {
                                Toast.makeText(appointevaluteActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("oid", result);
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
