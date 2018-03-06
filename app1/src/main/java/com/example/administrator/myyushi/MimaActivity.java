package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MimaActivity extends AppCompatActivity {

    @Bind(R.id.login_biaoti1)
    TextView loginBiaoti1;
    @Bind(R.id.login_left1)
    ImageView loginLeft1;
    @Bind(R.id.login_zcnum1)
    EditText loginZcnum1;
    @Bind(R.id.login_yanzheng1)
    EditText loginYanzheng1;
    @Bind(R.id.login_shuzi1)
    EditText loginShuzi1;
    @Bind(R.id.login_zhuce2)
    Button loginZhuce2;
   @Bind(R.id.huoqu_tv)
   TextView huoqu_tv;
    private TimeCount time;
    private RequestQueue queue;
    public static volatile String localCookie = null;
    public String result1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mima);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String result=intent.getStringExtra("phone");
        if(result!=null){
            loginZcnum1.setText(result);
        }
        queue = Volley.newRequestQueue(this);
        initdata();
        time = new TimeCount(60000, 1000);
        StatusBarCompat.setStatusBarColor(MimaActivity.this, Color.parseColor("#222222"), true);
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发

            huoqu_tv.setText("重新验证");
            huoqu_tv.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            huoqu_tv.setClickable(false);
            huoqu_tv.setText(millisUntilFinished /1000+"秒后重新获取");
        }
    }
    private void initdata() {
        loginZcnum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String aa=loginZcnum1.getText().toString().trim();
                String aa1= loginYanzheng1.getText().toString().trim();
                String aa2=loginShuzi1.getText().toString().trim();
                if(!TextUtils.isEmpty(aa)&&!TextUtils.isEmpty(aa1)&&!TextUtils.isEmpty(aa2)){
                    if(aa.length()==11&&aa1.length()==6&&aa2.length()>=6){
                        loginZhuce2.setClickable(true);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#fbd23a"));
                    }else {
                        loginZhuce2.setClickable(false);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                }else {
                    loginZhuce2.setClickable(false);
                    loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        loginYanzheng1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String aa=loginZcnum1.getText().toString().trim();
                String aa1= loginYanzheng1.getText().toString().trim();
                String aa2=loginShuzi1.getText().toString().trim();
                if(!TextUtils.isEmpty(aa)&&!TextUtils.isEmpty(aa1)&&!TextUtils.isEmpty(aa2)){
                    if(aa.length()==11&&aa1.length()==6&&aa2.length()>=6){
                        loginZhuce2.setClickable(true);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#fbd23a"));
                    }else {
                        loginZhuce2.setClickable(false);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                }else {
                    loginZhuce2.setClickable(false);
                    loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
      loginShuzi1.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
              String aa=loginZcnum1.getText().toString().trim();
              String aa1= loginYanzheng1.getText().toString().trim();
              String aa2=loginShuzi1.getText().toString().trim();
              if(!TextUtils.isEmpty(aa)&&!TextUtils.isEmpty(aa1)&&!TextUtils.isEmpty(aa2)){
                  if(aa.length()==11&&aa1.length()==6&&aa2.length()>=6){
                      loginZhuce2.setClickable(true);
                      loginZhuce2.setBackgroundColor(Color.parseColor("#fbd23a"));
                  }else {
                      loginZhuce2.setClickable(false);
                      loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                  }
              }else {
                  loginZhuce2.setClickable(false);
                  loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
              }
          }
      });
        loginLeft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        huoqu_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa=loginZcnum1.getText().toString().trim();
                if(TextUtils.isEmpty(aa)||aa.length()!=11){
                    Toast toast = Toast.makeText(MimaActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }else {
                    postByStringRequest();
                }
                //postByStringRequest();
            }
        });
        loginZhuce2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa=loginZcnum1.getText().toString().trim();
                String aa1= loginYanzheng1.getText().toString().trim();
                String aa2=loginShuzi1.getText().toString().trim();
                if(TextUtils.isEmpty(aa)&&TextUtils.isEmpty(aa1)&&TextUtils.isEmpty(aa2)){
                    loginZhuce2.setClickable(false);
                    loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }else {
                    String temp=loginZcnum1.getText().toString().trim();
                    if(temp.length()<6){
                        Toast toast = Toast.makeText(MimaActivity.this, "密码个数必须大于6位", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else {
                        postByStringRequest2();
                    }
                }
            }
        });
    }
    private void postByStringRequest2() {
        final String aa=loginZcnum1.getText().toString().trim();
        final String aa1= loginYanzheng1.getText().toString().trim();
        final String aa2=loginShuzi1.getText().toString().trim();
        RequestQueue request = Volley.newRequestQueue(this);
        String JSONDataUrl = "https://api.aijiaijia.com/api_resetpassword";
        //POST方式更加安全
        StringRequest  fff = new StringRequest(Request.Method.POST, JSONDataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(response1);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String result=resposeobject.getString("op");
                            if(result.equals("0")){
                                Toast toast = Toast.makeText(MimaActivity.this, "修改失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }else if(result.equals("1")){
                                Toast toast = Toast.makeText(MimaActivity.this, "修改成功", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                finish();
                            }else if(result.equals("2")){
                                Toast toast = Toast.makeText(MimaActivity.this,  "用户不存在", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }else if(result.equals("3")){
                                Toast toast = Toast.makeText(MimaActivity.this, "验证码错误", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }else if(result.equals("4")){
                                Toast toast = Toast.makeText(MimaActivity.this, "请获取验证码", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                Toast toast = Toast.makeText(MimaActivity.this,   "网络未链接", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", aa);
                map.put("smscode",aa1);
                map.put("password",aa2);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试8", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };
        request.add(fff);

    }
    private void postByStringRequest() {
        final String user=loginZcnum1.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl2 = "https://api.aijiaijia.com/api_postphonenum_getsmscode";
        //POST方式更加安全
        StringRequest stringrequest = new StringRequest(Request.Method.POST, JSONDataUrl2,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            result1=resposeobject.getString("op");
                           if(result1.equals("1")){
                               time.start();// 开始计时
                           }else if(result1.equals("0")){
                               Toast toast = Toast.makeText(MimaActivity.this,   "获取验证码失败", Toast.LENGTH_SHORT);
                               toast.setGravity(Gravity.CENTER, 0, 0);
                               toast.show();
                               //Toast.makeText(MimaActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                Toast toast = Toast.makeText(MimaActivity.this,   "获取验证码失败", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        }) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", user);
                map.put("type","2");
                return map;
            }
            //重写parseNetworkResponse方法，返回的数据中 Set-Cookie:***************;
            //其中**************为session id
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                Response<String> superResponse = super
                        .parseNetworkResponse(response);
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                Log.i("caojingdan", "parseNetworkResponse: "+rawCookies);
                //Constant是一个自建的类，存储常用的全局变量
                Constant.localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                Log.d("sessionid", "sessionid----------------" + Constant.localCookie);
                return superResponse;
            }
        };
        requestQueue.add(stringrequest);


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
