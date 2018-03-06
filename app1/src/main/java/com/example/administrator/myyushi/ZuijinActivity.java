package com.example.administrator.myyushi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Urlutil.Utils;
import adapter.zhantingadapter2;
import bean.zuijinbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import utils.FileUtils23;

public class ZuijinActivity extends AppCompatActivity {

    @Bind(R.id.zuijin_iv)
    ImageView zuijinIv;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    @Bind(R.id.zuijn_lv1)
    ListView zuijnLv1;
    private RequestQueue queue;
    private  String message;
    private   zhantingadapter2  zta2;
    private ArrayList<zuijinbean.ResponseJsonBean.ListBean>  zuijnbean=new ArrayList<>();
    private ArrayList<String> huan1=new ArrayList<>();
    private ArrayList<String> huan2=new ArrayList<>();
    private ArrayList<String> huan3=new ArrayList<>();
    private ArrayList<String> huan4=new ArrayList<>();
    private ArrayList<String> huan5=new ArrayList<>();
    private ArrayList<String> huan6=new ArrayList<>();
    private Handler handle=new Handler();
    String weizhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuijin);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        postdata();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setadapter();
            }
        },500);
      //  initData();
       Intent intent=getIntent();
        message=intent.getStringExtra("infor");
        weizhi=intent.getStringExtra("weizhi1");
        Log.i("ederrr", "onCreate: "+weizhi);
        setlistener();
   /*    FileUtils23 file1=new FileUtils23();
        message=file1.readDataFromFile(this);*/

    }

    private void setadapter() {

        zta2=new zhantingadapter2(ZuijinActivity.this,huan1,huan2,huan3,weizhi);
        zuijnLv1.setAdapter(zta2);

    }

    private void setlistener() {
        zuijinIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void postdata() {
        huan1.clear();
        huan2.clear();
        huan3.clear();
        huan4.clear();
        huan5.clear();
        queue = Volley.newRequestQueue(this);
        String urls="https://api.aijiaijia.com/api_displayhall";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu56sd我的大春", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            Log.i("heng", "onResponse: "+result3);
                            if (result3.equals("0")) {
                                Toast.makeText(ZuijinActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry=resposeobject.getJSONArray("list");
                                for (int i = 0; i <jsonarry.length() ; i++) {
                                     JSONObject object=jsonarry.getJSONObject(i);
                                    String mendian_name=object.getString("mendian_name");
                                    String mendian_address=object.getString("mendian_address");
                                    String mendian_phone=object.getString("mendian_phone");
                                    String mendian_latitude=object.getString("mendian_latitude");
                                    String mendian_longitude=object.getString("mendian_longitude");
                                    String mendian_id=object.getString("mendian_id");
                                    huan1.add(mendian_name);
                                    huan2.add(mendian_address);
                                    huan3.add(mendian_phone);
                                    huan4.add( mendian_latitude);
                                    huan5.add(mendian_longitude);
                                    huan6.add(mendian_id);

                                }
                                zuijnLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent5=new Intent();
                                        intent5.putExtra("result8", huan1.get(position));
                                        intent5.putExtra("mendian_latitude",huan4.get(position));
                                        intent5.putExtra("mendian_longitude",huan5.get(position));
                                        intent5.putExtra("mendian_id",huan6.get(position));
                                        intent5.putExtra("mendian_address",huan2.get(position));
                                        intent5.putExtra("weizhi", String.valueOf(position));
                                        setResult(6,intent5);
                                        finish();
                                    }
                                });
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
                Log.i("idmdmk", "getParams: "+message);
                if(message!=null){
                    map.put("page","1");
                    try {
                        String str=new String(message.getBytes("utf-8"),"ISO-8859-1");
                        map.put("cityname",str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }


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
