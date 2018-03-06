package com.example.administrator.myyushi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Urlutil.Utils;
import adapter.Messageadapter;
import adapter.youhuiadapter;
import bean.Messagebean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import util.Myapp;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Messagebean.ResponseJsonBean>  messagebean1=new ArrayList<>();
    private ArrayList<Messagebean> message3=new ArrayList<>();
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.search_tv_title)
    TextView searchTvTitle;
    @Bind(R.id.search_lv)
    ListView searchLv;
    private Handler handle=new Handler();
    private Messageadapter messageadapater;
    private RequestQueue queue;
    private ArrayList<String> title=new ArrayList<>();
    private  ArrayList<String> content=new ArrayList<>();
    private  ArrayList<String> time=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
       initData();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setlistener();
                setadapter();
            }
        },500);

    }

    private void setadapter() {
        ImageLoader loader=((Myapp)SearchActivity.this.getApplication()).getLoader();
        messageadapater=new Messageadapter(SearchActivity.this,loader,title,content,time);
        searchLv.setAdapter(messageadapater);
    }

    private void setlistener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        title.clear();
        content.clear();
        time.clear();
        queue = Volley.newRequestQueue(SearchActivity.this);
        String urls="https://api.aijiaijia.com/api_messages";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwusc", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            Log.i("heng", "onResponse: "+result3);
                            if (result3.equals("0")) {
                                Toast.makeText(SearchActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry=resposeobject.getJSONArray("list");
                                for (int i = 0; i <jsonarry.length() ; i++) {
                                    JSONObject object=jsonarry.getJSONObject(i);
                                    String message_content=object.getString("message_content");
                                    content.add(message_content);
                                    String message_createtime=object.getString("message_createtime");
                                    time.add(message_createtime);
                                    String message_title=object.getString("message_title");
                                    title.add(message_title);
                                }
                            } else if (result3.equals("6")) {
                                Intent intent=new Intent(SearchActivity.this,LoginActivity.class);
                                SearchActivity.this.startActivity(intent);
                                Toast.makeText(SearchActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page","1");
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
}
