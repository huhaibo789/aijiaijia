package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import utils.FileUtils27;

public class zhuceActivity extends AppCompatActivity {
    @Bind(R.id.login_biaoti)
    TextView loginBiaoti;
    @Bind(R.id.login_left)
    ImageView loginLeft;
    @Bind(R.id.login_zcnum)
    EditText loginZcnum;
    @Bind(R.id.login_yanzheng)
    EditText loginYanzheng;
    @Bind(R.id.login_shuzi)
    EditText loginShuzi;
    @Bind(R.id.login_service)
    TextView loginService;
    @Bind(R.id.login_zhuce1)
    Button loginZhuce1;
   @Bind(R.id.get_yanzheng)
   TextView  getyanzheng;
    @Bind(R.id.show_tv)
    TextView showtv;
    private Handler handler=new Handler();
    private TimeCount time;
    private RequestQueue queue;
    public static volatile String localCookie = null;
   public String result1;
    String coloss="2";
    String rawCookies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        setlistener1();
        time = new TimeCount(60000, 1000);
        StatusBarCompat.setStatusBarColor(zhuceActivity.this, Color.parseColor("#222222"), true);




    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发

           getyanzheng.setText("重新验证");
            getyanzheng.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            getyanzheng.setClickable(false);
            getyanzheng.setText(millisUntilFinished /1000+"秒后重新获取");
        }
    }





    private void setlistener1() {
        loginService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://url.aijiaijia.com/agreement.html";
                Intent intent=new Intent(zhuceActivity.this,WebviewActivity.class);
                intent.putExtra("service",url);
                intent.putExtra("servicetitle","用户协议");
                startActivity(intent);


            }
        });
        loginZcnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 String aa=loginZcnum.getText().toString().trim();
                String aa1=loginYanzheng.getText().toString().trim();
                String aa2=loginShuzi.getText().toString().trim();

                if(!TextUtils.isEmpty(aa)&&!TextUtils.isEmpty(aa1)&&!TextUtils.isEmpty(aa2)){
                    if(aa.length()==11&&aa1.length()==6&&aa2.length()>=6){
                        loginZhuce1.setClickable(true);
                        loginZhuce1.setBackgroundColor(Color.parseColor("#149985"));
                    }else {
                        loginZhuce1.setClickable(false);
                        loginZhuce1.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                }else {
                    loginZhuce1.setClickable(false);
                    loginZhuce1.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        loginYanzheng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String aa=loginZcnum.getText().toString().trim();
                String aa1=loginYanzheng.getText().toString().trim();
                String aa2=loginShuzi.getText().toString().trim();

                if(!TextUtils.isEmpty(aa)&&!TextUtils.isEmpty(aa1)&&!TextUtils.isEmpty(aa2)){
                    if(aa.length()==11&&aa1.length()==6&&aa2.length()>=6){
                        loginZhuce1.setClickable(true);
                        loginZhuce1.setBackgroundColor(Color.parseColor("#149985"));
                    }else {
                        loginZhuce1.setClickable(false);
                        loginZhuce1.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                }else {
                    loginZhuce1.setClickable(false);
                    loginZhuce1.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        loginShuzi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String aa=loginZcnum.getText().toString().trim();
                String aa1=loginYanzheng.getText().toString().trim();
                String aa2=loginShuzi.getText().toString().trim();
                 if(!TextUtils.isEmpty(aa)&&!TextUtils.isEmpty(aa1)&&!TextUtils.isEmpty(aa2)){
                     if(aa.length()==11&&aa1.length()==6&&aa2.length()>=6){
                         Log.i("hyrbf", "afterTextChanged: "+aa.length()+aa1.toString()+aa2.length());
                         loginZhuce1.setClickable(true);
                         loginZhuce1.setBackgroundColor(Color.parseColor("#fbd23a"));
                     }else {
                         loginZhuce1.setClickable(false);
                         loginZhuce1.setBackgroundColor(Color.parseColor("#B4B4B4"));
                     }
                 }else {
                     loginZhuce1.setClickable(false);
                     loginZhuce1.setBackgroundColor(Color.parseColor("#B4B4B4"));
                 }

            }
        });
        loginLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       getyanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa=loginZcnum.getText().toString().trim();
                if(TextUtils.isEmpty(aa)||aa.length()!=11){
                    Toast toast = Toast.makeText(zhuceActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }else {
                    postByStringRequest();
                }

                Log.i("tianas", "onClick: "+rawCookies);
                //Toast.makeText(zhuceActivity.this, "此号码已注册存在", Toast.LENGTH_SHORT).show();

          handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       /* FileUtils27 file1=new FileUtils27();
                        String zilaio=file1.readDataFromFile(zhuceActivity.this);
                        Log.i("hgujrf", "run: "+zilaio);
                        if(zilaio.equals("null")){
                            Toast.makeText(zhuceActivity.this, "此号码已注册", Toast.LENGTH_SHORT).show();
                        }*/
                      /*  if(rawCookies.equals("null")){
                            Toast.makeText(zhuceActivity.this, "此号码已注册", Toast.LENGTH_SHORT).show();
                        }else {

                        }*/

                    }
                },2000);


              /*  handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(result1.equals("2")){
                            Toast.makeText(zhuceActivity.this, "此号码已注册", Toast.LENGTH_SHORT).show();
                        }else {

                        }
                    }
                },2000);
*/
               /* if(result1!=null){
                    if(result1.equals("2")){
                        Toast.makeText(zhuceActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                    }else {
                       // 开始计时
                    }
                }
*/

            }
        });
        loginZhuce1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa=loginZcnum.getText().toString().trim();
                String aa1=loginYanzheng.getText().toString().trim();
                String aa2=loginShuzi.getText().toString().trim();
                if(TextUtils.isEmpty(aa)&&TextUtils.isEmpty(aa1)&&TextUtils.isEmpty(aa2)){
                    loginZhuce1.setClickable(false);
                    loginZhuce1.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }else {
                    String temp=loginShuzi.getText().toString();
                    if(temp.length()<6){
                        Toast.makeText(zhuceActivity.this, "密码个数必须大于6位", Toast.LENGTH_SHORT).show();
                    }else {
                        postByStringRequest1();

                    }
                }




            }
        });
        showtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(zhuceActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void postByStringRequest1() {
        final String aa=loginZcnum.getText().toString().trim();
        final String aa1=loginYanzheng.getText().toString().trim();
        final String aa2=loginShuzi.getText().toString().trim();
        queue = Volley.newRequestQueue(zhuceActivity.this);
        String url = "https://api.aijiaijia.com/api_register";
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
                                Toast toast = Toast.makeText(zhuceActivity.this,  "注册失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }else if(result.equals("1")){
                                Toast.makeText(zhuceActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                postdata();
                                finish();
                            }else if(result.equals("2")){
                                Toast toast = Toast.makeText(zhuceActivity.this,  "用户名已存在", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }else if(result.equals("3")){
                                Toast toast = Toast.makeText(zhuceActivity.this,   "验证码错误", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }else if(result.equals("4")){
                                Toast.makeText(zhuceActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
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
                        Toast toast = Toast.makeText(zhuceActivity.this, "网络未连接", Toast.LENGTH_SHORT);
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
                map.put("fromdevice","2");
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

    private void postdata() {
        queue = Volley.newRequestQueue(zhuceActivity.this);
        String url = "https://api.aijiaijia.com/api_userinfo_savenickname";
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
                int  n = (int)(Math.random() * (999999 - 100000));
                int m = n + 100000;
                String sata="游客"+String.valueOf(m);
                try {

                    String str=new String(sata.getBytes("utf-8"),"ISO-8859-1");
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


    private void postByStringRequest() {
        final String user=loginZcnum.getText().toString();
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
                              Toast  toast = Toast.makeText(zhuceActivity.this,
                                      "此号码已注册存在", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                               // Toast.makeText(zhuceActivity.this, "此号码已注册存在", Toast.LENGTH_SHORT).show();
                            }else if(result1.equals("1")){
                                time.start();
                            }else if(result1.equals(">1")){
                                Toast  toast = Toast.makeText(zhuceActivity.this,
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
                Toast toast = Toast.makeText(zhuceActivity.this, "网络未连接", Toast.LENGTH_SHORT);
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
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                for(String response1 : response.headers.keySet()){
                    if(response1.contains("Set-Cookie")){
                        //拿到session
                     String   cookies = response.headers.get(response1);
                        Constant.localCookie = cookies.substring(0, cookies
                                .indexOf(";"));
                       Log.i("info",cookies);
                        break;
                    }
                }
                return super.parseNetworkResponse(response);

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
