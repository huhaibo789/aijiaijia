package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SexActivity extends AppCompatActivity {
    @Bind(R.id.dismiss)
    TextView dismiss;
    @Bind(R.id.gender_save)
    TextView genderSave;
    @Bind(R.id.boy_iv)
    ImageView boyIv;
    @Bind(R.id.girl_iv)
    ImageView girlIv;
    private RequestQueue queue;
    private String gender;
    String gender3;
    boolean flag;
   boolean  index=false;
    private Handler handle=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        gender3=intent.getStringExtra("sex");
        if(gender3.equals("男")){
            boyIv.setImageResource(R.mipmap.chosi);
            girlIv.setImageResource(R.mipmap.chose);
        }else {
            boyIv.setImageResource(R.mipmap.chose);
            girlIv.setImageResource(R.mipmap.chosi);
        }
        setlistener();
        StatusBarCompat.setStatusBarColor(SexActivity.this, Color.parseColor("#fbd23a"), true);
    }
    private void setlistener() {
        boyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender3.equals("男")){
                    boyIv.setImageResource(R.mipmap.chose);
                    girlIv.setImageResource(R.mipmap.chosi);
                    gender3="女";
                }else {
                    boyIv.setImageResource(R.mipmap.chosi);
                    girlIv.setImageResource(R.mipmap.chose);
                    gender3="男";
                }

            }
        });
        girlIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender3.equals("男")){
                    boyIv.setImageResource(R.mipmap.chose);
                    girlIv.setImageResource(R.mipmap.chosi);
                    gender3="女";
                }else {
                    boyIv.setImageResource(R.mipmap.chosi);
                    girlIv.setImageResource(R.mipmap.chose);
                    gender3="男";
                }
            }
        });
       /* boyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){

                    boyIv.setImageResource(R.mipmap.chosi);
                    girlIv.setImageResource(R.mipmap.chose);
                    flag=true;
                    index=false;
                    gender="男";
                }else if(flag){
                    boyIv.setImageResource(R.mipmap.chose);
                    girlIv.setImageResource(R.mipmap.chose);
                    flag=false;
                    index=false;

                }
            }
        });*/
      /*  girlIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!index){
                    girlIv.setImageResource(R.mipmap.chosi);
                    boyIv.setImageResource(R.mipmap.chose);
                    index=true;
                    flag=false;
                    gender="女";
                }else {
                    girlIv.setImageResource(R.mipmap.chose);
                    boyIv.setImageResource(R.mipmap.chose);
                    index=false;
                    flag=false;
                }
            }
        });*/
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        genderSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postshuju();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent5=new Intent();
                        intent5.putExtra("result3", "1");
                        setResult(7,intent5);
                        finish();
                    }
                },500);

            }
        });

    }

    private void postshuju() {
        queue = Volley.newRequestQueue(SexActivity.this);
        String url = "https://api.aijiaijia.com/api_userinfo_savegender";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjianrd", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                Toast.makeText(SexActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("6")) {
                                Toast.makeText(SexActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                try {
                    String str=new String(gender3.getBytes("utf-8"),"ISO-8859-1");
                    map.put("gender",str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

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
