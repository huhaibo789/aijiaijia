package com.example.administrator.myyushi;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Urlutil.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import utils.DataCleanManager;
import utils.FileUtils32;
import utils.FileUtils33;
import utils.FileUtils37;
import utils.FileUtils6;
import utils.FileUtis36;

public class ShezhiActivity extends AppCompatActivity {

    @Bind(R.id.shezhi)
    ImageView shezhi;
    @Bind(R.id.shezhi1)
    TextView shezhi1;
    @Bind(R.id.person_tv)
    RelativeLayout personTv;
    @Bind(R.id.shouhuo_tv)
    RelativeLayout shouhuoTv;
    @Bind(R.id.login_mima)
    RelativeLayout loginMima;
    @Bind(R.id.shoucang)
    RelativeLayout shoucang;
    @Bind(R.id.clear)
    RelativeLayout clear;
    @Bind(R.id.guanyu)
    RelativeLayout guanyu;
    @Bind(R.id.tuichu)
    RelativeLayout tuichu;
    @Bind(R.id.cache_tv)
    TextView cacheTv;
    @Bind(R.id.banben_version)
    TextView banbenVersion;
    @Bind(R.id.chaoyou_iv)
    ImageView chaoyouIv;
    @Bind(R.id.ly_tuihuo)
    RelativeLayout lyTuihuo;
    @Bind(R.id.shezhi_rl)
    RelativeLayout shezhiRl;
    private boolean flag;
    private RequestQueue queue;
    String clickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);
        ButterKnife.bind(this);
        DataCleanManager manager = new DataCleanManager();
        try {
            String ss = manager.getTotalCacheSize(ShezhiActivity.this);
            cacheTv.setText("已使用" + ss);
        } catch (Exception e) {
            e.printStackTrace();
        }

        queue = Volley.newRequestQueue(this);
        PackageManager manager3;
        PackageInfo info = null;
        manager3 = this.getPackageManager();

        try {
            info = manager3.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {


            e.printStackTrace();

        }
        String ss = info.versionName;
        banbenVersion.setText("v " + ss);
        setListener();
        StatusBarCompat.setStatusBarColor(ShezhiActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void postqingqiu() {
        queue = Volley.newRequestQueue(ShezhiActivity.this);
        String url = "https://api.aijiaijia.com/api_userinfo";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjianef", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                if (clickname.equals("person")) {
                                    Intent intent = new Intent(ShezhiActivity.this, PersonActivity.class);
                                    startActivity(intent);
                                } else if (clickname.equals("deliver")) {
                                    Intent intent1 = new Intent(ShezhiActivity.this, Shouhuo1Activity.class);
                                    startActivity(intent1);
                                } else if (clickname.equals("password")) {
                                    Intent intent = new Intent(ShezhiActivity.this, XiugaiActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (clickname.equals("collect")) {
                                    Intent intent = new Intent(ShezhiActivity.this, ShoucangActivity.class);
                                    startActivity(intent);
                                } else if (clickname.equals("tuihuo")) {
                                    String url = "https://url.aijiaijia.com/customer.html";
                                    Intent intent = new Intent(ShezhiActivity.this, WebviewActivity.class);
                                    intent.putExtra("name3", url);
                                    intent.putExtra("xingming", "退换货说明");
                                    startActivity(intent);
                                }
                            } else if (result3.equals("6")) {
                                Intent intent = new Intent(ShezhiActivity.this, LoginActivity.class);
                                startActivity(intent);

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

    private void setListener() {
        Log.i("hahsd", "setListener: " + "kjd");
        lyTuihuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("hahsd", "setListener: " + clickname);
                clickname = "tuihuo";
                postqingqiu();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager manager = new DataCleanManager();
                manager.clearAllCache(ShezhiActivity.this);

                try {
                    String ss = manager.getTotalCacheSize(ShezhiActivity.this);
                    cacheTv.setText("已使用" + ss);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        personTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hahsd1", "setListener: " + clickname);
                clickname = "person";
                postqingqiu();

            }
        });
        shezhiRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent();
                setResult(6, intent5);
                finish();
            }
        });
        shouhuoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hahsd2", "setListener: " + clickname);
                clickname = "deliver";
                postqingqiu();

            }
        });
        guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ShezhiActivity.this, WebviewActivity.class);
                String douzi = Utils.TYPE_GUANYU_URL;
                intent3.putExtra("name1", douzi);
                intent3.putExtra("xingming", "关于我们");
                startActivity(intent3);
            }
        });
        loginMima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hahsd3", "setListener: " + clickname);
                clickname = "password";
                postqingqiu();
            }
        });
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickname = "collect";
                postqingqiu();
            }
        });
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(ShezhiActivity.this);
                String url = "https://api.aijiaijia.com/api_userinfo";
                StringRequest post = new StringRequest(
                        StringRequest.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String str = response.toString().trim();
                                Log.i("jianjianef", "onResponse: " + str);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(str);
                                    JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                    String result3 = resposeobject.getString("op");
                                    if (result3.equals("1")) {
                                        // 建造者模式，将对象初始化的步骤抽取出来，让建造者来实现，设置完所有属性之后再创建对象
                                        // 这里使用的Context必须是Activity对象
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ShezhiActivity.this);
                                        // 链式编程
                                        builder.setTitle("提示")
                                                .setMessage("确认退出")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        String url = "https://api.aijiaijia.com/trsshopapi/api_logout";
                                                        StringRequest post = new StringRequest(
                                                                StringRequest.Method.POST,
                                                                url,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        Log.i("shuoshuo", "onResponse: post  success " + response);
                                                                        String str = response.toString().trim();
                                                                        JSONObject jsonObject = null;
                                                                        try {
                                                                            jsonObject = new JSONObject(str);
                                                                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                                                            String result3 = resposeobject.getString("op");
                                                                            if (result3.equals("1")) {
                                                                                Toast.makeText(ShezhiActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                                                                                FileUtils32 fils = new FileUtils32();
                                                                                fils.saveDataToFile(ShezhiActivity.this, "null");
                                                                                FileUtils33 fil = new FileUtils33();
                                                                                fil.saveDataToFile(ShezhiActivity.this, "null");
                                                                                FileUtis36 fl = new FileUtis36();
                                                                                fl.saveDataToFile(ShezhiActivity.this,"nologin");
                                                                                FileUtils37 file=new FileUtils37();
                                                                                file.saveDataToFile(ShezhiActivity.this,"nologin");
                                                                                FileUtils6 ff = new FileUtils6();
                                                                                ff.saveDataToFile(ShezhiActivity.this, "nologin");   //用户的uid
                                                                                String ss = "登录/注册";
                                                                                Intent intent5 = new Intent();
                                                                                intent5.putExtra("result8", ss);
                                                                                setResult(6, intent5);
                                                                                finish();
                                                                            } else if (result3.equals("0")) {
                                                                                Toast.makeText(ShezhiActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                    }
                                                                },
                                                                new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Log.i("caozuo", "onErrorResponse: " + error.getMessage());
                                                                    }
                                                                }
                                                        ) {
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

                                                        queue.add(post);

                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setCancelable(false); // 能否通过点击对话框以外的区域（包括返回键）关闭对话框
                                        // 通过建造者创建Dialog对象
                                        // AlertDialog dialog = builder.create();
                                        // dialog.show();
                                        // 以上两行代码可以简化成以下这一行代码
                                        builder.show();
                                    } else if (result3.equals("6")) {
                                        Toast.makeText(ShezhiActivity.this, "你没登录", Toast.LENGTH_SHORT).show();
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
