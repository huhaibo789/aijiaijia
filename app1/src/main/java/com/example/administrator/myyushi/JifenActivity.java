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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import utils.FileUtils24;
import utils.FileUtils9;

public class JifenActivity extends AppCompatActivity {
    @Bind(R.id.picture_iv)
    ImageView pictureIv;
    @Bind(R.id.jifen_tv)
    TextView jifenTv;
    @Bind(R.id.jifen_tv1)
    TextView jifenTv1;
    @Bind(R.id.chakan_jifen)
    TextView chakanJifen;
    LoadingDialog dialog;
    private  String jj2;
    private Handler handler = new Handler();
    private RequestQueue queue;
    public static volatile String localCookie = null;
    private String url3 = "https://api.aijiaijia.com/api_point";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifen);
        ButterKnife.bind(this);
      /*  FileUtils24 file=new FileUtils24();
        String result=file.readDataFromFile(this);
        if(result.equals("登录")){
            Intent intent=new Intent(JifenActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else {

        }*/
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        getVolley(url3);
        setlistener();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#fbd23a"), true);
    }

    private void getVolley(final String isUrl) {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl = isUrl;
        JsonObjectRequest objectRequest = new JsonObjectRequest(JSONDataUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject1) {
                        if(jsonObject1!=null){
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            },500);
                        }
                        //取到的jsonObject数据在这里处理操作
                        Log.i("xujimeng", "onResponse: " + jsonObject1);
                        JSONObject resposeobject = null;
                        try {

                            resposeobject = jsonObject1.getJSONObject("responseJson");
                            String jj=resposeobject.getString("list_point_title");
                            jj2=resposeobject.getString("list_point_infomation");
                            jifenTv1.setText(jj);
                             JSONObject jsonObject2=resposeobject.getJSONObject("list_user");
                              String jj1=jsonObject2.getString("user_points");
                            FileUtils9  ff=new FileUtils9();
                            ff.saveDataToFile(JifenActivity.this,jj1);
                            jifenTv.setText(jj1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAGArray", volleyError.getMessage(), volleyError);
            }
        }) {
            //重写getHeaders 默认的key为cookie，value则为localCookie
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };
        requestQueue.add(objectRequest);
    }


    private void setlistener() {
        pictureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chakanJifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(JifenActivity.this,WebviewActivity.class);
                intent.putExtra("name1",jj2);
                startActivity(intent);
            }
        });
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
