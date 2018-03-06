package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.Map;

import Alilogin.AuthResult;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import utils.FileUtils24;
import utils.FileUtils26;
import utils.FileUtils33;
import utils.FileUtils37;
import utils.FileUtils38;
import utils.FileUtils39;
import utils.FileUtils40;
import utils.FileUtils41;
import utils.FileUtils42;
import utils.FileUtils6;
import utils.FileUtis36;


public class LoginActivity extends AppCompatActivity implements PlatformActionListener {
    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.login_number)
    EditText loginNumber;
    @Bind(R.id.login_ly)
    LinearLayout loginLy;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.login_ly1)
    LinearLayout loginLy1;
    @Bind(R.id.login_denglu)
    Button loginDenglu;
    @Bind(R.id.login_zhuce)
    TextView loginZhuce;
    @Bind(R.id.login_forget)
    TextView loginForget;
    @Bind(R.id.other_tv)
    TextView otherTv;
    @Bind(R.id.view_ligon)
    View viewLigon;
    @Bind(R.id.alilogin)
    ImageView alilogin;
    @Bind(R.id.qqlogin)
    ImageView qqlogin;
    @Bind(R.id.weixinlogin)
    ImageView weixinlogin;
    @Bind(R.id.sinalogin)
    ImageView sinalogin;
    private String shuzi;
    String names;
    private String address = "https://api.aijiaijia.com/api_login";
    private RequestQueue queue;
    String headImageUrl = null;//头像
    String token; //年龄
    String gender;//性别
    String userId; //用户Id
    String name = null;//用户名
    Handler handlesa = new Handler();
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private static final int COMPLETED = 3;
    private static final int COMPLETED1 = 4;
    Platform wechat;
    String unionid;
    String qquniode;
    Platform qq;
    int biao = 0;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(LoginActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                        Log.i("chusheng", "handleMessage: " + authResult.getAuthCode());
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(LoginActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(LoginActivity.this);
        ButterKnife.bind(this);
        FileUtils24 file = new FileUtils24();
        String phone = file.readDataFromFile(LoginActivity.this);
        if (phone != null) {
            loginNumber.setText(phone);
        }
        Intent intent = getIntent();
        shuzi = intent.getStringExtra("shouye");
        queue = Volley.newRequestQueue(this);
        initview();
        setlistener();
        StatusBarCompat.setStatusBarColor(LoginActivity.this, Color.parseColor("#222222"), true);
    }

    private void setlistener() {
        alilogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /* if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA2_PRIVATE)
                          || TextUtils.isEmpty(TARGET_ID)) {
                      new AlertDialog.Builder(LoginActivity.this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                              .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialoginterface, int i) {
                                  }
                              }).show();
                      return;
                  }
                  boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                  Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID,rsa2);
                  String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
                  String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                  String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey,rsa2);
                  Log.i("heie", "authV2: "+sign);
                  final String authInfo = info + "&" + sign;
                  Runnable authRunnable = new Runnable() {

                      @Override
                      public void run() {
                          // 构造AuthTask 对象
                          AuthTask authTask = new AuthTask(LoginActivity.this);
                          // 调用授权接口，获取授权结果
                          Map<String, String> result = authTask.authV2(authInfo, true);
                          Log.i("heihiew", "run: "+authInfo);
                          Log.i("heihiew1", "run: "+result);
                          Message msg = new Message();
                          msg.what = SDK_AUTH_FLAG;
                          msg.obj = result;
                          mHandler.sendMessage(msg);
                      }
                  };

                  // 必须异步调用
                  Thread authThread = new Thread(authRunnable);
                  authThread.start();
*/
            }

        });
        sinalogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform sinweibo = ShareSDK.getPlatform(LoginActivity.this, SinaWeibo.NAME);
               /* sinweibo.SSOSetting(true);*/
               /* if(sinweibo.isValid()){
                    sinweibo.removeAccount();
                }
                sinweibo.SSOSetting(true);

                if (!sinweibo.isClientValid()) {

                    Toast.makeText(LoginActivity.this,
                            "微博未安装,请先安装微博",
                            Toast.LENGTH_LONG).show();
                }*/
                authorize(sinweibo);
            }
        });
        qqlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qq = ShareSDK.getPlatform(LoginActivity.this, QQ.NAME);
              /*  String exportData = qq.getDb().exportData();//获取到db里面所有的内容
                Log.i("jhshdsd", "onClick: "+exportData);*/

               /* if(qq.isValid()){
                    qq.removeAccount();
                }
                qq.SSOSetting(true);
                if(!qq.isClientValid()){
                    Toast.makeText(LoginActivity.this,
                            "QQ未安装,请先安装QQ",
                            Toast.LENGTH_LONG).show();
                }*/

                authorize(qq);

            }
        });

        weixinlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechat = ShareSDK.getPlatform(LoginActivity.this, Wechat.NAME);
               /* if(wechat.isValid()){
                    wechat.removeAccount();
                }
                wechat.SSOSetting(true);
                if (!wechat.isClientValid()) {

                    Toast.makeText(LoginActivity.this,

                            "微信未安装,请先安装微信",

                            Toast.LENGTH_LONG).show();

                }*/
                authorize(wechat);
            }
        });
    }

    private void getQQUnionid(Platform qq) {
        StringRequest getString = new StringRequest(
                StringRequest.Method.GET,  //设置请求方式（如果是get请求，那么此参数可省略）
                "https://graph.qq.com/oauth2.0/me?access_token=" + qq.getDb().getToken() + "&unionid=1", //设置连接网址
                new Response.Listener<String>() {
                    //一旦成功读取此数据后，调用此方法
                    //参数：网络连接读取结果
                    @Override
                    public void onResponse(String response) {
                        Log.i("chusnegbs", "onResponse: success!!  " + response);
                        String[] strArray = response.split("unionid\":\"");
                        String name = strArray[strArray.length - 1];
                        String[] zifu = name.split("\"");
                        qquniode = zifu[zifu.length - 2];
                        Log.i("huhuh", "onResponse: " + qquniode);
                        qqpostthirdlogin();
                    }
                },
                new Response.ErrorListener() {
                    //一旦连接出错，调用此方法，
                    //参数：出错原因
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("chuesee", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        //3. 将请求对象添加到请求队列中执行加载操作
        queue.add(getString);
       /* Log.i("zhanan", "getQQUnionid: "+qq.getDb().getToken());
        queue = Volley.newRequestQueue(LoginActivity.this);
        String url="https://graph.qq.com/oauth2.0/me?access_token=" + qq.getDb().getToken()+"&unionid=1";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu88552", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        Log.i("dasf", "onResponse: "+str);
                        *//*byte[] data = response.data;
                        String s = new String(data);

                        String[] split = s.split(":");
                        s = split[split.length - 1];
                        split = s.split("\"");
                        s = split[1];
                        unionid = s;*//*
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
        queue.add(post);*/

    }

    private void qqpostthirdlogin() {
        FileUtils38 thirdfile = new FileUtils38();
        thirdfile.saveDataToFile(LoginActivity.this, names);
        FileUtils39 fileuniode = new FileUtils39();
        fileuniode.saveDataToFile(LoginActivity.this, qquniode);
        FileUtils40 filegender = new FileUtils40();
        filegender.saveDataToFile(LoginActivity.this, gender);
        FileUtils41 filenick = new FileUtils41();
        filenick.saveDataToFile(LoginActivity.this, name);
        FileUtils42 fileimg = new FileUtils42();
        fileimg.saveDataToFile(LoginActivity.this, headImageUrl);
        queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "https://api.aijiaijia.com/api_thirdlogin";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("hahsaddse", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");

                            String result3 = resposeobject.getString("op");
                            JSONObject json = resposeobject.getJSONObject("list_user");
                            String aa1 = json.getString("id");
                            FileUtis36 fil = new FileUtis36();
                            fil.saveDataToFile(LoginActivity.this, aa1);
                            FileUtils6 ff = new FileUtils6();
                            ff.saveDataToFile(LoginActivity.this, aa1);   //用户的uid
                            Log.i("aiyas1df", "onResponse: " + aa1);
                            if (result3.equals("1")) {
                                if (shuzi != null && shuzi.equals("1")) {
                                    finish();
                                } else {
                                    Log.i("aiyas5", "onResponse: " + shuzi);
                                    Intent intent5 = new Intent();
                                    intent5.putExtra("result7", "1");
                                    setResult(5, intent5);
                                    finish();
                                }
                            } else if (result3.equals("6")) {
                                Intent intent = new Intent(LoginActivity.this, bindingActivity.class);
                                startActivityForResult(intent, 20);

                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
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
                //微博登录
                Map<String, String> map = new HashMap<>();
                map.put("type", names);
                if (names.equals("weixin") && unionid != null) {
                    map.put("uid", unionid);
                } else if (names.equals("qq") && qquniode != null) {
                    Log.i("fuwuqi", "getParams: " + qquniode);
                    map.put("uid", qquniode);
                } else {
                    Log.i("fuwuqi1", "getParams: " + qquniode);
                    map.put("uid", userId);
                }
                map.put("fromdevice", "2");
                try {
                    String str = new String(gender.getBytes("utf-8"), "ISO-8859-1");
                    String str1 = new String(name.getBytes("utf-8"), "ISO-8859-1");
                    map.put("gender", str);
                    map.put("nickname", str1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put("avatar", headImageUrl);
                Log.i("caodaos", "getParams: " + map);
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
                Log.i("caojingdan", "parseNetworkResponse: " + rawCookies);
                //Constant是一个自建的类，存储常用的全局变量
                Constant.localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                FileUtils33 files = new FileUtils33();
                files.saveDataToFile(LoginActivity.this, Constant.localCookie);
                Log.d("sessionid", "sessionid----------------" + Constant.localCookie);
                return superResponse;
            }
        };

        queue.add(post);
    }

    //第三方登录要数据不要功能
    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }
        plat.setPlatformActionListener(this);
//开启SSO授权
        // plat.SSOSetting(false);
        plat.SSOSetting(false);
        // plat.authorize();
        plat.showUser(null);
    }

    private void initview() {
        loginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String jieguo = loginNumber.getText().toString().trim();
                String mima = loginPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(jieguo) && !TextUtils.isEmpty(mima)) {
                    if (jieguo.length() == 11 && mima.length() >= 6) {

                        loginDenglu.setClickable(true);
                        loginDenglu.setBackgroundColor(Color.parseColor("#fbd23a"));
                    } else {
                        loginDenglu.setClickable(false);
                        loginDenglu.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                } else {
                    loginDenglu.setClickable(false);
                    loginDenglu.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String jieguo = loginNumber.getText().toString().trim();
                String mima = loginPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(jieguo) && !TextUtils.isEmpty(mima)) {
                    if (jieguo.length() == 11 && mima.length() >= 6) {
                        loginDenglu.setClickable(true);
                        loginDenglu.setBackgroundColor(Color.parseColor("#fbd23a"));
                    } else {
                        loginDenglu.setClickable(false);
                        loginDenglu.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                } else {
                    loginDenglu.setClickable(false);
                    loginDenglu.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        loginZhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, zhuceActivity.class);
                startActivity(intent);
            }
        });
        loginForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this, MimaActivity.class);
                FileUtils24 file = new FileUtils24();
                String phone = file.readDataFromFile(LoginActivity.this);
                if (phone != null) {
                    intent1.putExtra("phone", phone);
                    startActivity(intent1);
                } else {
                    startActivity(intent1);
                }
            }
        });
    }

    public void tclick(View view) {
        final String phone1 = loginNumber.getText().toString().trim();
        if (phone1.length() != 11) {
            Toast toast = Toast.makeText(LoginActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        final String password1 = loginPassword.getText().toString().trim();
        Log.i("hahas", "tclick: " + password1);
        FileUtils26 file = new FileUtils26();
        file.saveDataToFile(LoginActivity.this, password1);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl = "https://api.aijiaijia.com/api_login";
        //POST方式更加安全
        StringRequest stringrequest = new StringRequest(Request.Method.POST, JSONDataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        //就是在主线程上操作,弹出结果
                        Log.i("haslou", "onResponse: " + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String ss = resposeobject.getString("op");
                            String ss1 = resposeobject.getString("trsuid");
                            if (ss.equals("1")) {
                                FileUtils6 ff = new FileUtils6();
                                ff.saveDataToFile(LoginActivity.this, ss1);   //用户的uid
                                FileUtils24 file = new FileUtils24();
                                file.saveDataToFile(LoginActivity.this, phone1); //用户登录电话
                                FileUtils37 files = new FileUtils37();
                                files.saveDataToFile(LoginActivity.this, phone1);
                                Log.i("hahas1", "tclick: " + phone1);
                                Toast toast = Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                if (shuzi != null && shuzi.equals("1")) {
                                    finish();
                                } else {
                                    Intent intent5 = new Intent();
                                    intent5.putExtra("result7", phone1);
                                    setResult(5, intent5);
                                    finish();

                                }
                            } else {
                                Toast toast = Toast.makeText(LoginActivity.this, "密码或账号不对", Toast.LENGTH_SHORT);
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
                Toast toast = Toast.makeText(LoginActivity.this, "密码或账号不对", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                // Toast.makeText(LoginActivity.this, "密码或账号不对", Toast.LENGTH_SHORT).show();
            }
        }) {

            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("dish", "getParams: " + "daolema");
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone1);
                map.put("password", password1);
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
                Log.i("caojingdan", "parseNetworkResponse: " + rawCookies);
                //Constant是一个自建的类，存储常用的全局变量
                Constant.localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                Log.d("sessionid", "sessionid----------------" + Constant.localCookie);
                return superResponse;
            }
        };
        requestQueue.add(stringrequest);
    }

    private Handler dhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Toast.makeText(LoginActivity.this, gender, Toast.LENGTH_SHORT).show();
            } else if (msg.what == COMPLETED1) {
                Toast.makeText(LoginActivity.this, "成功拉", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void openToast(String strMsg) {
        Toast.makeText(this, strMsg, Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(LoginActivity.this);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (i == Platform.ACTION_USER_INFOR) {
            Log.i("keyisd", "onComplete: " + "chunhuo");
            PlatformDb platDb = platform.getDb();
            if (platform.getName().equals(Wechat.NAME)) {
                // 通过DB获取各种数据
                name = null;
                unionid = wechat.getDb().get("unionid");
                token = platDb.getToken();
                userId = platDb.getUserId();
                name = platDb.getUserName();
                gender = platDb.getUserGender();
                Log.i("hahsha", "onComplete: " + gender);
                Log.i("huhsuades", "onComplete: " + userId);
                headImageUrl = platDb.getUserIcon();
                if ("m".equals(gender) || "男".equals(gender)) {
                    gender = "男";
                } else {
                    gender = "女";
                }
                names = "weixin";
                if (name != null) {
                    postthirdlogin();
                } else {
                    return;
                }


            } else if (platform.getName().equals(SinaWeibo.NAME)) {
                //微博登录
                name = null;
                userId = platDb.getUserId();
                gender = platDb.getUserGender();
                Log.i("hsusbe", "onComplete: " + gender);
                Log.i("zhaixsd", "onComplete: " + userId);
                name = platDb.getUserName();
                headImageUrl = platDb.getUserIcon();
                if ("m".equals(gender)) {
                    gender = "男";
                } else {
                    gender = "女";
                }
                names = "sina";
                if (name != null) {
                    postthirdlogin();
                } else {
                    return;
                }


            } else if (platform.getName().equals(QQ.NAME)) {
                // QQ登录
                name = null;
                userId = platDb.getUserId();
                name = platDb.getUserName();
                gender = platDb.getUserGender();
                headImageUrl = platDb.getUserIcon();
              /*  Log.i("hsuhdues", "onComplete: "+userId);
               // name = hashMap.get("nickname").toString(); // 名字
                Log.i("hsuhdues1", "onComplete: "+name);
               // gender = hashMap.get("gender").toString(); // 性别
                Log.i("hahahsa", "onComplete: "+gender);
               // headImageUrl = hashMap.get("figureurl_qq_2").toString(); // 头像figureurl_qq_2 中等图，figureurl_qq_1缩略图
                Log.i("hahahsa1", "onComplete: "+headImageUrl);
                String city = hashMap.get("city").toString(); // 城市
                String province = hashMap.get("province").toString(); // 省份*/
                //  getUserInfo(name, headImageUrl);
                names = "qq";
                if ("m".equals(gender)) {
                    gender = "男";
                } else {
                    gender = "女";
                }
                getQQUnionid(qq);
               /* if(name!=null){
                    postthirdlogin();
                }else {
                    return;
                }*/


            }
        }

    }

    private void postthirdlogin() {
        FileUtils38 thirdfile = new FileUtils38();
        thirdfile.saveDataToFile(LoginActivity.this, names);
        FileUtils39 fileuniode = new FileUtils39();
        if (names != null && names.equals("weixin")) {
            fileuniode.saveDataToFile(LoginActivity.this, unionid);
        } else {
            fileuniode.saveDataToFile(LoginActivity.this, userId);
        }
        FileUtils40 filegender = new FileUtils40();
        filegender.saveDataToFile(LoginActivity.this, gender);
        FileUtils41 filenick = new FileUtils41();
        filenick.saveDataToFile(LoginActivity.this, name);
        FileUtils42 fileimg = new FileUtils42();
        fileimg.saveDataToFile(LoginActivity.this, headImageUrl);
        queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "https://api.aijiaijia.com/api_thirdlogin";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("hahsaddse", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");

                            String result3 = resposeobject.getString("op");
                            JSONObject json = resposeobject.getJSONObject("list_user");
                            Log.i("liuxue", "onResponse: " + json);
                            String aa1 = json.getString("id");
                            FileUtis36 fil = new FileUtis36();
                            fil.saveDataToFile(LoginActivity.this, aa1);
                            FileUtils6 ff = new FileUtils6();
                            ff.saveDataToFile(LoginActivity.this, aa1);   //用户的uid
                            Log.i("aiyas1df", "onResponse: " + aa1);
                            if (result3.equals("1")) {

                                if (shuzi != null && shuzi.equals("1")) {
                                    finish();
                                } else {
                                    Log.i("aiyas5", "onResponse: " + shuzi);
                                    Intent intent5 = new Intent();
                                    intent5.putExtra("result7", "1");
                                    setResult(5, intent5);
                                    finish();
                                }
                            } else if (result3.equals("6")) {
                                Intent intent = new Intent(LoginActivity.this, bindingActivity.class);
                                startActivityForResult(intent, 20);

                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
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
                //微博登录
                Map<String, String> map = new HashMap<>();
                map.put("type", names);
                if (names.equals("weixin") && unionid != null) {
                    map.put("uid", unionid);
                } else if (names.equals("qq") && qquniode != null) {
                    Log.i("fuwuqi", "getParams: " + qquniode);
                    map.put("uid", qquniode);
                } else {
                    Log.i("fuwuqi1", "getParams: " + qquniode);
                    map.put("uid", userId);
                }
                map.put("fromdevice", "2");
                try {
                    String str = new String(gender.getBytes("utf-8"), "ISO-8859-1");
                    String str1 = new String(name.getBytes("utf-8"), "ISO-8859-1");
                    map.put("gender", str);
                    map.put("nickname", str1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put("avatar", headImageUrl);
                Log.i("caodaos", "getParams: " + map);
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
                Log.i("caojingdan", "parseNetworkResponse: " + rawCookies);
                //Constant是一个自建的类，存储常用的全局变量
                Constant.localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                FileUtils33 files = new FileUtils33();
                files.saveDataToFile(LoginActivity.this, Constant.localCookie);
                Log.d("sessionid", "sessionid----------------" + Constant.localCookie);
                return superResponse;
            }
        };

        queue.add(post);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("aimas1", "onActivityResult: " + requestCode);
        if (requestCode == 20) {
            Log.i("aimas", "onActivityResult: " + "hsasa");
            Intent intent5 = new Intent();
            //  intent5.putExtra("result7", phone1);
            setResult(5, intent5);
            finish();
        }
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.i("keyui", "onCancel: " + "hasd");
    }
/*
    private void getUserInfo(final String name, final String imgUrl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }*/
}
