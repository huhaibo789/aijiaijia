package com.example.administrator.myyushi;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.evaluateadapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.FileUtils;

public class evaluateActivity extends AppCompatActivity {

    @Bind(R.id.finish_iv)
    ImageView finishIv;
    @Bind(R.id.xiangqing_tv)
    TextView xiangqingTv;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.listview_evaluate)
    ListView listviewEvaluate;
    private RequestQueue queue;
    private ArrayList<String> content=new ArrayList<>();
    private ArrayList<String> nickname=new ArrayList<>();
    private ArrayList<String> time=new ArrayList<>();
    private ArrayList<String> merchants=new ArrayList<>();
    private ArrayList<String> merchantstime=new ArrayList<>();
    private evaluateadapter adapter;
    private Handler handle=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        initdata();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                number.setText("("+content.size()+")");
                setadapter();
                setlistener();

            }
        },500);
        StatusBarCompat.setStatusBarColor(evaluateActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void setlistener() {
        finishIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setadapter() {
        ImageLoader loader = ((Myapp) evaluateActivity.this.getApplication()).getLoader();
        adapter = new evaluateadapter(evaluateActivity.this, loader,content,nickname,time,merchants,merchantstime);
        listviewEvaluate.setAdapter(adapter);
    }

    private void initdata() {
        FileUtils fu=new FileUtils();
        final String id=fu.readDataFromFile(this);
        queue = Volley.newRequestQueue(evaluateActivity.this);
        Log.i("xiapeng7", "onClick: "+id);
        String url="https://api.aijiaijia.com/api_comments";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu565", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        Log.i("dasf", "onResponse: "+str);
                        try {
                            JSONObject jsonObject =new JSONObject(str);
                            JSONObject  responseobject=jsonObject.getJSONObject("responseJson");
                            JSONArray jsonarry=responseobject.getJSONArray("list");
                            for (int i = 0; i <jsonarry.length() ; i++) {
                                JSONObject  object=jsonarry.getJSONObject(i);
                                String  data=object.getString("content");
                                 content.add(data);
                                String shangjia=object.getString("reply_content");
                                merchants.add(shangjia);
                                String data1=object.getString("showname");
                                 nickname.add(data1);
                                String shangjiatime=object.getString("reply_time");
                                merchantstime.add(shangjiatime);
                                String data2=object.getString("time");
                                time.add(data2);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                                        /* try {
                                             jsonObject = new JSONObject(str);
                                             JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                                             String result3 = resposeobject.getString("op");
                                             Log.i("ggks", "onResponse: "+result3);
                                             JSONArray jsonarry=resposeobject.getJSONArray("list");
                                             for (int i = 0; i <jsonarry.length() ; i++) {
                                                 JSONObject object=jsonarry.getJSONObject(i);
                                                 String dizhi=object.getString("product_pic");
                                                 Log.i("yuy", "onResponse: "+dizhi);
                                                 String num=object.getString("num");
                                                 Log.i("yuy1", "onResponse: "+num);
                                                 String ss2=object.getString("product_name");
                                                 Log.i("yuy2", "onResponse: "+ss2);
                                                 String hh5=String.valueOf(object.getString("product_nowprice"));
                                                 Log.i("yuy3", "onResponse: "+hh5);
                                                 String hh6=String.valueOf(object.getString("product_price"));
                                                 Log.i("yuy6", "onResponse: "+hh6);
                                                 DBHelper2 helper2=new DBHelper2(context);
                                                 helper2.insert(new gouwu(id,dizhi,num,ss2,hh5,hh6));

                                                 gou34=helper2.queryAll();
                                                 Log.i("shdau", "onResponse: "+gou34);
                                             }

                                             if (result3.equals("0")) {
                                                 Toast.makeText(context, "加入失败", Toast.LENGTH_SHORT).show();
                                             } else if (result3.equals("1")) {
                                                 Toast.makeText(context, "加入成功", Toast.LENGTH_SHORT).show();
                                             } else if (result3.equals("6")) {
                                                 Log.i("hongxin", "onResponse: "+result3);
                                                 Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
                                                 Intent intent=new Intent(context,LoginActivity.class);
                                                 context.startActivity(intent);

                                             }




                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }*/







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
                map.put("readpid",id);
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
