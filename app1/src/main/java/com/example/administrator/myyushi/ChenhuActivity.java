package com.example.administrator.myyushi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChenhuActivity extends AppCompatActivity {


    @Bind(R.id.miss_tv)
    TextView missTv;
    @Bind(R.id.save_tv)
    TextView saveTv;
    @Bind(R.id.chenhu1)
    EditText chenhu1;
    private RequestQueue queue;
    private  String nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chenhu);
        ButterKnife.bind(this);
        for (int i = 0; i <100 ; i++) {
           int  n = (int)(Math.random() * (999999 - 100000));
           int m = n + 100000;
        }
        setlistener();
        Intent intent=getIntent();
        String chenhu=intent.getStringExtra("chenhu");
        chenhu1.setText(chenhu);
        chenhu1.setTextColor(Color.parseColor("#222222"));
        initData();
        StatusBarCompat.setStatusBarColor(ChenhuActivity.this, Color.parseColor("#fbd23a"), true);
    }
    private void setlistener() {
        chenhu1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             nickname=chenhu1.getText().toString().trim();

            }
        });
    }
    private void postshuju() {
        queue = Volley.newRequestQueue(ChenhuActivity.this);
        String url = "https://api.aijiaijia.com/api_userinfo_savenickname";
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
                            if(result3.equals("1")){
                                Toast.makeText(ChenhuActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                Intent intent5=new Intent();
                                intent5.putExtra("result4", "1");
                                setResult(6,intent5);
                                finish();
                            }else if(result3.equals("6")){
                                Toast.makeText(ChenhuActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                    String str=new String(nickname.getBytes("utf-8"),"ISO-8859-1");
                    map.put("nickname", str);

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
    private void initData() {
        missTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      saveTv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(TextUtils.isEmpty(nickname)){
                  Toast.makeText(ChenhuActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                  return;
              }
              postshuju();
          }
      });
    }
    public static boolean isName(String name) {
        if (name.length() == 0) {
            return false;
        }
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
            Matcher m = p.matcher(name);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;

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
