package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class bindingActivity extends AppCompatActivity {

    @Bind(R.id.title_rl)
    RelativeLayout titleRl;
    @Bind(R.id.bing_view)
    View bingView;
    @Bind(R.id.login_zcnum1)
    EditText loginZcnum1;
    @Bind(R.id.login_yanzheng1)
    EditText loginYanzheng1;
    @Bind(R.id.huoqu_tv)
    TextView huoquTv;
    @Bind(R.id.login_shuzi1)
    EditText loginShuzi1;
    @Bind(R.id.bing_view1)
    View bingView1;
    @Bind(R.id.binding_descrip)
    TextView bindingDescrip;
    @Bind(R.id.login_zhuce2)
    Button loginZhuce2;
    @Bind(R.id.bind_iv)
    ImageView bindIv;
    private RequestQueue queue;
    String result1;
    private TimeCount time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        initdata();
        time = new TimeCount(60000, 1000);
        StatusBarCompat.setStatusBarColor(bindingActivity.this, Color.parseColor("#fbd23a"), true);

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
                String aa = loginZcnum1.getText().toString().trim();
                String aa1 = loginYanzheng1.getText().toString().trim();
                String aa2 = loginShuzi1.getText().toString().trim();
                if (!TextUtils.isEmpty(aa) && !TextUtils.isEmpty(aa1) && !TextUtils.isEmpty(aa2)) {
                    if (aa.length() == 11 && aa1.length() == 6 && aa2.length() >= 6) {
                        Log.i("hyrbf", "afterTextChanged: " + aa.length() + aa1.toString() + aa2.length());
                        loginZhuce2.setClickable(true);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#fbd23a"));
                    } else {
                        loginZhuce2.setClickable(false);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                } else {
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
                String aa = loginZcnum1.getText().toString().trim();
                String aa1 = loginYanzheng1.getText().toString().trim();
                String aa2 = loginShuzi1.getText().toString().trim();
                if (!TextUtils.isEmpty(aa) && !TextUtils.isEmpty(aa1) && !TextUtils.isEmpty(aa2)) {
                    if (aa.length() == 11 && aa1.length() == 6 && aa2.length() >= 6) {
                        Log.i("hyrbf", "afterTextChanged: " + aa.length() + aa1.toString() + aa2.length());
                        loginZhuce2.setClickable(true);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#fbd23a"));
                    } else {
                        loginZhuce2.setClickable(false);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                } else {
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
                String aa = loginZcnum1.getText().toString().trim();
                String aa1 = loginYanzheng1.getText().toString().trim();
                String aa2 = loginShuzi1.getText().toString().trim();
                if (!TextUtils.isEmpty(aa) && !TextUtils.isEmpty(aa1) && !TextUtils.isEmpty(aa2)) {
                    if (aa.length() == 11 && aa1.length() == 6 && aa2.length() >= 6) {
                        Log.i("hyrbf", "afterTextChanged: " + aa.length() + aa1.toString() + aa2.length());
                        loginZhuce2.setClickable(true);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#fbd23a"));
                    } else {
                        loginZhuce2.setClickable(false);
                        loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                } else {
                    loginZhuce2.setClickable(false);
                    loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        titleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        huoquTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = loginZcnum1.getText().toString().trim();
                if (TextUtils.isEmpty(aa) || aa.length() != 11) {
                    Toast toast = Toast.makeText(bindingActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                } else {
                    postByStringRequest();

                }
                //postByStringRequest();


            }
        });
        loginZhuce2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = loginZcnum1.getText().toString().trim();
                String aa1 = loginYanzheng1.getText().toString().trim();
                String aa2 = loginShuzi1.getText().toString().trim();
                if (TextUtils.isEmpty(aa) && TextUtils.isEmpty(aa1) && TextUtils.isEmpty(aa2)) {
                    loginZhuce2.setClickable(false);
                    loginZhuce2.setBackgroundColor(Color.parseColor("#B4B4B4"));
                } else {
                    String temp = loginZcnum1.getText().toString().trim();
                    if (temp.length() < 6) {
                        Toast toast = Toast.makeText(bindingActivity.this, "密码个数必须大于6位", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        postByStringRequest2();

                    }
                }


            }
        });
    }

    private void postByStringRequest2() {
        final String aa = loginZcnum1.getText().toString().trim();
        final String aa1 = loginYanzheng1.getText().toString().trim();
        final String aa2 = loginShuzi1.getText().toString().trim();
        queue = Volley.newRequestQueue(bindingActivity.this);
        String url = "https://api.aijiaijia.com/api_thirdbingtophone";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfs", "onResponse: post  success " + response);
                        String ss = response.toString().trim();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            Log.i("oidsmds", "onResponse: "+response.toString());
                            String result=resposeobject.getString("op");
                            Log.i("kdffd", "onResponse: "+result);
                            if(result.equals("0")){
                                Toast toast = Toast.makeText(bindingActivity.this,  "绑定失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }else if(result.equals("1")){
                                Toast.makeText(bindingActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                                Intent intent5 = new Intent();
                              //  intent5.putExtra("result7", phone1);
                                setResult(20, intent5);
                                finish();

                            }else if(result.equals("2")){
                                Toast toast = Toast.makeText(bindingActivity.this,  "用户名已绑定", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }else if(result.equals("3")){
                                Toast toast = Toast.makeText(bindingActivity.this, "验证码错误", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }else if(result.equals("4")){
                                Toast.makeText(bindingActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
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
                        Toast toast = Toast.makeText(bindingActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", aa);
                Log.i("dsd", "getParams: "+aa);
                map.put("smscode",aa1);
                Log.i("sdd", "getParams: "+aa1);
                map.put("password",aa2);
                Log.i("dede", "getParams: "+aa2);
                Log.i("uyshss", "getParams: "+map.toString());
                return map;
            }
            //重写parseNetworkResponse方法，返回的数据中 Set-Cookie:***************;
            //其中**************为session id

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

    private void postByStringRequest() {
        final String user=loginZcnum1.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl = "https://api.aijiaijia.com/api_postphonenum_getsmscode";
        //POST方式更加安全
        StringRequest stringrequest = new StringRequest(Request.Method.POST, JSONDataUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response1) {
                        // TODO Auto-generated method stub
                        JSONObject jsonObject = null;
                      /*  Log.i("cddd", "onResponse: "+response);
                        if(response==null||response==""){
                            Toast.makeText(zhuceActivity.this, "此号码已注册存在", Toast.LENGTH_SHORT).show();
                        }else if(response.equals("null")){
                            Toast.makeText(zhuceActivity.this, "此号码已注册存在", Toast.LENGTH_SHORT).show();
                        }*/
                        try {
                            jsonObject = new JSONObject(response1);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            Log.i("ifdmf", "onResponse: "+response1);
                            result1=resposeobject.getString("op");
                            if(result1.equals("2")){
                                Toast  toast = Toast.makeText(bindingActivity.this,
                                        "此号码已绑定", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                // Toast.makeText(zhuceActivity.this, "此号码已注册存在", Toast.LENGTH_SHORT).show();
                            }else if(result1.equals("1")){
                                time.start();
                            }else if(result1.equals(">1")){
                                Toast  toast = Toast.makeText(bindingActivity.this,
                                        "获取验证码失败", Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(bindingActivity.this, "网络未连接", Toast.LENGTH_SHORT);
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
                Log.i("isjss", "getParams: "+user.toString());
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

        requestQueue.add(stringrequest);
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发

            huoquTv.setText("重新验证");
            huoquTv.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            huoquTv.setClickable(false);
            huoquTv.setText(millisUntilFinished /1000+"秒后重新获取");
        }
    }
}
