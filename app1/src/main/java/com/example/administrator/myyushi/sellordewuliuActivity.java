package com.example.administrator.myyushi;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.wuliuadapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class sellordewuliuActivity extends AppCompatActivity {
    @Bind(R.id.selorderfininsh_iv)
    ImageView selorderfininshIv;
    @Bind(R.id.sellorder_idf)
    ImageView sellorderIdf;
    @Bind(R.id.pic_iv)
    ImageView picIv;
    @Bind(R.id.wuliu_lv)
    ListView wuliuLv;
    @Bind(R.id.quan_wuliu)
    RelativeLayout quanWuliu;
    @Bind(R.id.shibuhshi)
    TextView shibuhshi;
    @Bind(R.id.status)
    RelativeLayout status;
    @Bind(R.id.wuliu_miaoxu)
    TextView wuliuMiaoxu;
    @Bind(R.id.wuliu_tv)
    TextView wuliuTv;
    @Bind(R.id.order_tv)
    TextView orderTv;
    @Bind(R.id.wuliu_rl)
    RelativeLayout wuliuRl;
    private RequestQueue queue;
    private String ordersn;
    private String ordersn1;
    private wuliuadapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    private Handler handle = new Handler();
    String tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellordewuliu);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ordersn = intent.getStringExtra("sn");
        ordersn1 = intent.getStringExtra("sn1");
        if (ordersn != null) {
            Log.i("haha", "onCreate: " + ordersn);
            postdata();
        } else {
            Log.i("zhege", "onCreate: " + ordersn1);
            postdata1();
        }

        setlistener();
        StatusBarCompat.setStatusBarColor(sellordewuliuActivity.this, Color.parseColor("#fbd23a"), true);

    }

    private void postdata1() {
        list.clear();
        queue = Volley.newRequestQueue(this);
        String url = "https://api.aijiaijia.com/api_sellordertracedetail";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("skfdvcor", "onResponse: post  success " + response);
                        String ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            String company=resposeobject.getString("company");
                            String express_sn=resposeobject.getString("express_sn");
                             tel=resposeobject.getString("tel");
                            if(tel!=null){
                                wuliuTv.setText("电话:"+tel);
                            }
                            if(company!=null){
                                shibuhshi.setText("快递公司:"+company);
                            }
                            if(express_sn!=null){
                                orderTv.setText("快递单号:"+express_sn);
                            }
                            if (op.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("new_list");
                                if (jsonarry.length() == 0) {
                                    quanWuliu.setVisibility(View.VISIBLE);
                                    shibuhshi.setVisibility(View.GONE);
                                    picIv.setVisibility(View.GONE);
                                    wuliuLv.setVisibility(View.GONE);
                                    return;
                                } else {
                                    quanWuliu.setVisibility(View.GONE);
                                    shibuhshi.setVisibility(View.VISIBLE);
                                    picIv.setVisibility(View.VISIBLE);
                                    wuliuLv.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < jsonarry.length(); i++) {
                                        JSONObject object = jsonarry.getJSONObject(i);
                                        list.add(object.getString("first"));
                                    }
                                    setadapter();
                                }

                            } else if (op.equals("21")) {
                                Toast.makeText(sellordewuliuActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("6")) {
                                Toast.makeText(sellordewuliuActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("ordersn", ordersn1);
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
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wuliuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +tel));
                if (ActivityCompat.checkSelfPermission(sellordewuliuActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent1);
            }
        });
        sellorderIdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordersn != null) {
                    String dd = ordersn;
                    String ww = "全部订单";
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(dd, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(sellordewuliuActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                } else {
                    String dd = ordersn1;
                    String ww = "全部订单";
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(dd, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(sellordewuliuActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                }

            }
        });
    }

    private void setadapter() {
        adapter = new wuliuadapter(sellordewuliuActivity.this, list);
        Log.i("huhu", "setadapter: " + list.toString());
        wuliuLv.setAdapter(adapter);
    }

    private void postdata() {
        list.clear();
        queue = Volley.newRequestQueue(this);
        String url = "https://api.aijiaijia.com/api_appointordertracedetail";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("skfdvcor", "onResponse: post  success " + response);
                        String ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(sellordewuliuActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                JSONArray jsonarry = resposeobject.getJSONArray("new_list");
                                if (jsonarry.length() == 0) {
                                    quanWuliu.setVisibility(View.VISIBLE);
                                    shibuhshi.setVisibility(View.GONE);
                                    picIv.setVisibility(View.GONE);
                                    wuliuLv.setVisibility(View.GONE);
                                    return;
                                } else {
                                    quanWuliu.setVisibility(View.GONE);
                                    shibuhshi.setVisibility(View.VISIBLE);
                                    picIv.setVisibility(View.VISIBLE);
                                    wuliuLv.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < jsonarry.length(); i++) {
                                        JSONObject object = jsonarry.getJSONObject(i);
                                        list.add(object.getString("first"));
                                    }
                                    setadapter();
                                }

                            } else if (op.equals("21")) {
                                Toast.makeText(sellordewuliuActivity.this, "失败", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("6")) {
                                Toast.makeText(sellordewuliuActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("ordersn", ordersn);
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