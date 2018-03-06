package com.example.administrator.myyushi;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Xiangqingadapter;
import bean.gouwu2;
import bean.gouwu3;
import bean.xiangqingbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import request.BiZhiRequest;
import request.LoadingDialog;
import util.Myapp;
import utils.FileUtils;
import utils.FileUtils23;

public class Sousuo2Activity extends AppCompatActivity implements Runnable,View.OnClickListener,PopupWindow.OnDismissListener,LocationSource, AMapLocationListener
       {
    @Bind(R.id.search_xiangqing)
    RecyclerView searchXiangqing;
    String fas = "1";
    LoadingDialog dialog;
    String status;
    @Bind(R.id.xingqing_finish)
    ImageView xingqingFinish;
    @Bind(R.id.kefu_xiangqing)
    ImageView kefuXiangqing;
    @Bind(R.id.shop_xiangqing)
    ImageView shopXiangqing;
    @Bind(R.id.text_chart_num)
    TextView textChartNum;
    @Bind(R.id.share_iv1)
    ImageView shareIv1;
    @Bind(R.id.now_kefu)
    RadioButton nowKefu;
    @Bind(R.id.arrive_iv)
    RadioButton arriveIv;
    @Bind(R.id.shop_iv)
    RadioButton shopIv;
    @Bind(R.id.buy_iv)
    Button buyIv;
    @Bind(R.id.bottom_radioGroup)
    RadioGroup bottomRadioGroup;
    @Bind(R.id.toubu_ry)
    RelativeLayout toubury;
    @Bind(R.id.xingqing_finishd)
    ImageView xingqingfinishd;
    @Bind(R.id.dd)
    RelativeLayout dd;

    private ArrayList<String> logins7 = new ArrayList<>();
    private JSONObject resposeobject;
    private List<gouwu2> gou23 = new ArrayList<>();
    private List<gouwu3> gouwu24 = new ArrayList<>();
    private Xiangqingadapter adapter1;
    private RequestQueue queue;
    private String jieguo = "0";
    private String jieguo1 = "0";
    private ArrayList<String> details = new ArrayList<>();
    private ArrayList<String> details1 = new ArrayList<>();
    private ArrayList<String> detail2 = new ArrayList<>();
    private ArrayList<String> detail3 = new ArrayList<>();
    private ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean> xqbean = new ArrayList<>();
    private ArrayList<String> wuyu = new ArrayList<>();
    public AMapLocationClientOption  mLocationOption=null;   //声明mLocationOption对象，定位参数
    private AMapLocationClient  mLocationClient = null;    //声明AMapLocationClient类对象，定位发起端
    private Context context;
    private int type;
    String url;
    private ViewGroup anim_mask_layout;//动画层
    private Handler handle = new Handler();
    String result13;
           private String message;
    public h5popwindow popWindow;
           private PopupWindow pop;
           //声明mListener对象，定位监听器
           private OnLocationChangedListener mListener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo3);
        ButterKnife.bind(this);
        location();//定位
        stat();
       // popWindow = new h5popwindow(Sousuo2Activity.this);
        //popWindow.setOnItemClickListener((h5popwindow.OnItemClickListener) Sousuo2Activity.this);
        postdata();
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        new Thread(this).start();
        queue = Volley.newRequestQueue(this);
        //setadapter();
        setlistener();
    }

           private void location() {
               //初始化定位
               mLocationClient = new AMapLocationClient(Sousuo2Activity.this);
               //设置定位回调监听
               mLocationClient.setLocationListener(this);
               //初始化定位参数
               mLocationOption = new AMapLocationClientOption();
               //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
               mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
               //设置是否返回地址信息（默认返回地址信息）
               mLocationOption.setNeedAddress(true);
               //设置是否只定位一次,默认为false
               mLocationOption.setOnceLocation(true);
               //设置是否强制刷新WIFI，默认为强制刷新
               mLocationOption.setWifiActiveScan(true);
               //设置是否允许模拟位置,默认为false，不允许模拟位置
               mLocationOption.setMockEnable(false);
               //设置定位间隔,单位毫秒,默认为2000ms
               mLocationOption.setInterval(2000);
               //给定位客户端对象设置定位参数
               mLocationClient.setLocationOption(mLocationOption);
               //启动定位
               mLocationClient.startLocation();
           }

           private void postdata() {
        queue = Volley.newRequestQueue(Sousuo2Activity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_statistics";
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
                            result13 = resposeobject.getString("op");
                            if (result13.equals("1")) {
                                textChartNum.setVisibility(View.VISIBLE);
                                String result4 = resposeobject.getString("shopcard_num");
                                if (!result4.equals("0")) {
                                    textChartNum.setBackgroundResource(R.drawable.bubblet);
                                    textChartNum.setText(result4);
                                } else {
                                    textChartNum.setVisibility(View.INVISIBLE);
                                }

                            } else {
                                textChartNum.setVisibility(View.INVISIBLE);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

    private void stat() {
        // 打开调试开关，正式版本请关闭，以免影响性能
        StatService.setDebugOn(true);

        // 设置APP_KEY，APP_KEY是从mtj官网获取，建议通过manifest.xml配置
        StatService.setAppKey("adddb320ad");
        // 设置渠道，建议通过manifest.xml配置
        StatService.setAppChannel(this, "Baidu Market", true);
        // 打开异常收集开关，默认收集java层异常，如果有嵌入SDK提供的so库，则可以收集native crash异常
        StatService.setOn(this, StatService.EXCEPTION_LOG);
        // 如果没有页面和事件埋点，此代码必须设置，否则无法完成接入
        // 设置发送策略，建议使用 APP_START
        // 发送策略，取值 取值 APP_START、SET_TIME_INTERVAL、ONCE_A_DAY
        // 备注，SET_TIME_INTERVAL与ONCE_A_DAY，如果APP退出或者进程死亡，则不会发送
        // 建议此代码不要在Application中设置，否则可能因为进程重启等造成启动次数高，具体见web端sdk帮助中心
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
    }
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();// 获得Window界面的最顶层
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        //animLayout.setId();
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vp, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private void setAnim(final View v, int[] start_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);
        View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
        int[] end_location = new int[2];// 存储动画结束位置的X,Y坐标
        textChartNum.getLocationInWindow(end_location);// 将购物车的位置存储起来
        // 计算位移
        int endX = end_location[0] - start_location[0];// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());// 设置此动画的加速曲线。默认为一个线性插值。
        translateAnimationX.setRepeatCount(0);// 动画重复的次数
        translateAnimationX.setFillAfter(true);
        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复次数
        translateAnimationY.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationX);
        set.addAnimation(translateAnimationY);
        set.setDuration(500);
        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                v.setVisibility(View.GONE);
            }
        });
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "scale", 1.0F, 1.5F, 1.0f)//
                .setDuration(500);//
        anim.setStartDelay(500);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                shopXiangqing.setScaleX(cVal);
                shopXiangqing.setScaleY(cVal);
                textChartNum.setScaleX(cVal);
                textChartNum.setScaleY(cVal);
            }
        });
    }

    public void setAnim(View view) {
        // TODO Auto-generated method stub
        int[] start_location = new int[2];// 一个整型数组用来存储按钮在屏幕的X,Y坐标
        view.getLocationInWindow(start_location);// 购买按钮在屏幕中的坐标
        ImageView buyImg = new ImageView(this);// 动画的小圆圈
        buyImg.setImageResource(R.drawable.round);// 设置buyImg的图片
        setAnim(buyImg, start_location);
    }

    private void setlistener() {



        if (status != null) {
            if (status.equals("1")) {
                buyIv.setClickable(true);
                buyIv.setBackgroundColor(Color.parseColor("#149985"));
                buyIv.setText("立即购买");
                buyIv.setVisibility(View.VISIBLE);
                buyIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // popWindow.showAsDropDown(v);
                        setBackgroundAlpha(0.5f);
                        /*if (xqbean != null) {
                            final String id = String.valueOf(xqbean.get(0).getId());
                            queue = Volley.newRequestQueue(Sousuo2Activity.this);
                            String url = "https://api.aijiaijia.com/api_point";
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
                                                if (result3.equals("1")) {
                                                    String dizhi1 = wuyu.get(0).toString();
                                                    String ss3 = xqbean.get(0).getProduct_name();
                                                    String ss4 = String.valueOf(xqbean.get(0).getProduct_nowprice());
                                                    String ss5 = String.valueOf(xqbean.get(0).getProduct_price());
                                                    String ss6 = String.valueOf(xqbean.get(0).getId());
                                                    DBHelper4 helper5 = new DBHelper4(Sousuo2Activity.this);
                                                    gou23 = helper5.queryAll();
                                                    for (int i = 0; i < gou23.size(); i++) {
                                                        helper5.delete(gou23.get(i).get_id());
                                                    }
                                                    helper5.insert(new gouwu2(dizhi1, ss3, ss4, ss5, ss6));
                                                    DBHelper5 gelper6 = new DBHelper5(Sousuo2Activity.this);
                                                    gouwu24 = gelper6.queryAll();
                                                    if (gouwu24.size() != 0) {
                                                        for (int i = 0; i < gouwu24.size(); i++) {
                                                            gelper6.delete(gouwu24.get(i).get_id());
                                                        }
                                                    }
                                                    String ss = "请选择";
                                                    String ss1 = "Null";
                                                    String ss2 = "Null";
                                                    String aa = "没有使用积分";
                                                    FileUtils19 qq = new FileUtils19();
                                                    qq.saveDataToFile(Sousuo2Activity.this, aa);
                                                    FileUtils21 kk1 = new FileUtils21();
                                                    kk1.saveDataToFile(Sousuo2Activity.this, ss2);
                                                    FileUtils20 kk = new FileUtils20();
                                                    kk.saveDataToFile(Sousuo2Activity.this, ss1);
                                                    Fileutils18 gg = new Fileutils18();
                                                    gg.saveDataToFile(Sousuo2Activity.this, ss);
                                                    Intent intent = new Intent(Sousuo2Activity.this, BaseActivity.class);
                                                    startActivity(intent);
                                                } else if (result3.equals("6")) {
                                                    Toast.makeText(Sousuo2Activity.this, "未登录", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Sousuo2Activity.this, LoginActivity.class);
                                                    startActivityForResult(intent,5);
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
                        }*/

                    }
                });
            }

        } else {
            buyIv.setBackgroundColor(Color.parseColor("#DADADA"));
            buyIv.setText("已下架");
            buyIv.setClickable(false);


        }
        xingqingfinishd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchXiangqing.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    xingqingfinishd.setVisibility(View.VISIBLE);
                    toubury.setVisibility(View.GONE);
                } else if (!recyclerView.canScrollVertically(1)) {

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (firstItemPosition == 0) {
                        xingqingfinishd.setVisibility(View.VISIBLE);
                        toubury.setVisibility(View.GONE);
                    } else {
                        xingqingfinishd.setVisibility(View.GONE);
                        toubury.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        nowKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils ful = new FileUtils();
                String dd = ful.readDataFromFile(Sousuo2Activity.this);
                if (xqbean.size() != 0) {
                    String ww = xqbean.get(0).getProduct_name();
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(dd, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(Sousuo2Activity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                } else {
                    Toast toast = Toast.makeText(Sousuo2Activity.this, "请检查网络!!!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });
        shopXiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(Sousuo2Activity.this);
                String url = "https://api.aijiaijia.com/api_userinfo";
                StringRequest post = new StringRequest(
                        StringRequest.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String str = response.toString().trim();
                                Log.i("frggg", "onResponse: " + str);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(str);
                                    JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                    String result3 = resposeobject.getString("op");
                                    if (result3.equals("6")) {
                                        Toast.makeText(Sousuo2Activity.this, "未登录", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Sousuo2Activity.this, LoginActivity.class);
                                        startActivityForResult(intent, 5);

                                    } else {
                                        Intent intent = new Intent(Sousuo2Activity.this, GouwuActivity1.class);
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
                            }
                        }
                ) {
                    //通过重写此对象的getParams方法设置请求条件
                    //将所有的请求条件存入返回值的map对象中
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
        shareIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        kefuXiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = String.valueOf(xqbean.get(0).getId());
                queue = Volley.newRequestQueue(Sousuo2Activity.this);
                String urls = "https://api.aijiaijia.com/api_collection_add";
                StringRequest post = new StringRequest(
                        StringRequest.Method.POST,
                        urls,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String str = response.toString().trim();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(str);
                                    JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                    String result3 = resposeobject.getString("op");
                                    Log.i("heng", "onResponse: " + result3);
                                    if (result3.equals("0")) {
                                        Toast.makeText(Sousuo2Activity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    } else if (result3.equals("1")) {
                                        Toast.makeText(Sousuo2Activity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    } else if (result3.equals("6")) {
                                        Intent intent = new Intent(Sousuo2Activity.this, LoginActivity.class);
                                        startActivityForResult(intent, 5);
                                        Toast.makeText(Sousuo2Activity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                        map.put("pids", id);
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
        });
        xingqingFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arriveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (xqbean.size() != 0) {
                    String ss6 = String.valueOf(xqbean.get(0).getId());
                    Intent intent = new Intent(Sousuo2Activity.this, HasproductActivity.class);
                    intent.putExtra("id", ss6);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(Sousuo2Activity.this, "请检查网络!!!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        shopIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                postdata1();
            }
        });
    }

    private void postdata1() {
        queue = Volley.newRequestQueue(Sousuo2Activity.this);
        String url = "https://api.aijiaijia.com/api_shopcart";
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
                            resposeobject = jsonObject.getJSONObject("responseJson");
                            if (result13.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                String id1 = String.valueOf(xqbean.get(0).getId());
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String s7 = object.getString("pid");
                                    if (id1.equals(s7)) {
                                        jieguo = "1";
                                        Toast toast = Toast.makeText(Sousuo2Activity.this, "购物车已有该商品", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                                if (!jieguo.equals("1")) {
                                    if (xqbean.size() != 0) {
                                        final String id = String.valueOf(xqbean.get(0).getId());
                                        queue = Volley.newRequestQueue(Sousuo2Activity.this);
                                        String url = "https://api.aijiaijia.com/api_shopcart_add";
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
                                                            if (result3.equals("0")) {
                                                                Toast.makeText(Sousuo2Activity.this, "加入失败", Toast.LENGTH_SHORT).show();
                                                            } else if (result3.equals("1")) {
                                                                // setAnim(v);
                                                                Toast.makeText(Sousuo2Activity.this, "加入成功", Toast.LENGTH_SHORT).show();
                                                                postdata();
                                                                adapter1.notifyDataSetChanged();

                                                            } else if (result3.equals("6")) {
                                                                Toast toast = Toast.makeText(Sousuo2Activity.this, "未登录", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                                Intent intent = new Intent(Sousuo2Activity.this, LoginActivity.class);
                                                                startActivityForResult(intent, 5);
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                    }
                                                }
                                        ) {
                                            //通过重写此对象的getParams方法设置请求条件
                                            //将所有的请求条件存入返回值的map对象中
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> map = new HashMap<>();
                                                map.put("pids", id);
                                                map.put("nums", "1");
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
                                    } else {
                                        Toast toast = Toast.makeText(Sousuo2Activity.this, "请检查网络!!!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }

                                }
                            } else {
                                Toast toast = Toast.makeText(Sousuo2Activity.this, "未登录", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent intent = new Intent(Sousuo2Activity.this, LoginActivity.class);
                                startActivityForResult(intent, 5);
                                return;
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

    private void showShare() {
        Log.i("husd", "showShare: " + xqbean.get(0).getId());
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(xqbean.get(0).getProduct_name());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId());
        // text是分享文本，所有平台都需要这个字段
        oks.setText("【有人@我】质量这么好的商品,快戳我看看吧!");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(xqbean.get(0).getProduct_pic());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(xqbean.get(0).getProduct_name());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(xqbean.get(0).getProduct_name());
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId());
        // 启动分享GUI
        oks.show(this);
    }


    private void setadapter() {
        ImageLoader loader = ((Myapp) getApplication()).getLoader();
        adapter1 = new Xiangqingadapter(Sousuo2Activity.this, loader, xqbean, wuyu, details, details1, detail2, detail3);
        searchXiangqing.setLayoutManager(new LinearLayoutManager(this));
        searchXiangqing.setAdapter(adapter1);
    }

    private void initdata() {
        getxqlist();
    }

    private void getxqlist() {
        FileUtils ful = new FileUtils();
        String dd = ful.readDataFromFile(this);
        wuyu.clear();
        xqbean.clear();
        BiZhiRequest<xiangqingbean> request = new BiZhiRequest<xiangqingbean>(xiangqingbean.class, dd, new Response.Listener<xiangqingbean>() {
            @Override
            public void onResponse(xiangqingbean response) {
                if (response != null && response.getResponseJson().getList_product() != null) {
                    xqbean.addAll(response.getResponseJson().getList_product());
                    status = String.valueOf(xqbean.get(0).getProduct_status());
                    setlistener();
                    //adapter1.updatexqbean(xqbean);
                    // adapter1.notifyDataSetChanged();
                }
                if (response != null && response.getResponseJson().getList_product_urlpics() != null) {
                    wuyu.addAll(response.getResponseJson().getList_product_urlpics());
                    // adapter1.updateurl(wuyu);
                    //  adapter1.notifyDataSetChanged();
                }
                if (response != null && response.getResponseJson().getList_product_detail() != null) {
                    details.addAll(response.getResponseJson().getList_product_detail());
                    //  adapter1.updatedetail(details);
                    // adapter1.notifyDataSetChanged();
                }
                if (response != null && response.getResponseJson().getList_product_aftersaleservice() != null) {
                    details1.addAll(response.getResponseJson().getList_product_aftersaleservice());
                    //adapter1.updatedetail1(details1);
                    // adapter1.notifyDataSetChanged();
                }
                if (response != null && response.getResponseJson().getList_product_attribute_type() != null) {
                    detail2.addAll(response.getResponseJson().getList_product_attribute_type());
                    //adapter1.updatedetail2(detail2);
                    // adapter1.notifyDataSetChanged();

                }
                if (response != null && response.getResponseJson().getList_product_attribute_parm() != null) {
                    detail3.addAll(response.getResponseJson().getList_product_attribute_parm());
                    //adapter1.updetedetail3(detail3);
                    // adapter1.notifyDataSetChanged();
                }
                setadapter();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(Sousuo2Activity.this, "请检查网络！！！", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                });
        queue.add(request);
    }


    @Override
    public void run() {
        try {

            if (xqbean != null) {
                Thread.sleep(1000);
                dialog.dismiss();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(Sousuo2Activity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(Sousuo2Activity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5) {
            if (data != null) {
                String result2 = data.getStringExtra("result7");
                postdata();
                initdata();
                setadapter();
            }
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDismiss() {

    }

           @Override
           public void onLocationChanged(AMapLocation aMapLocation) {
               String aa = aMapLocation.toString();
               String data = new Gson().toJson(aa);
               Log.i("weinihao", "onLocationChanged: "+"laile");
               if (aMapLocation != null) {
                   Log.i("weinihao", "onLocationChanged: "+"laile1");
                   if (aMapLocation.getErrorCode() == 0) {
                       Log.i("weinihao", "onLocationChanged: "+"laile2");
                       //定位成功回调信息，设置相关消息
                       aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                       aMapLocation.getLatitude();//获取纬度
                       aMapLocation.getLongitude();//获取经度
                       aMapLocation.getAccuracy();//获取精度信息
                       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                       Date date = new Date(aMapLocation.getTime());
                       df.format(date);//定位时间
                       aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                       aMapLocation.getCountry();//国家信息
                       aMapLocation.getProvince();//省信息
                       message = aMapLocation.getCity();//城市信息
                       Log.i("xianshi", "onLocationChanged: "+message);
                       if(message!=null){
                           Intent intent = getIntent();
                           String result = intent.getStringExtra("uid");
                           url = "https://api.aijiaijia.com/api_products_info?" + "pid=" + result+"&&"+"cityname="+message;
                           if (result != null) {
                               FileUtils ful = new FileUtils();
                               ful.saveDataToFile(this, url);
                           }
                           initdata();
                       }
                       aMapLocation.getDistrict();//城区信息
                       aMapLocation.getStreet();//街道信息
                       aMapLocation.getStreetNum();//街道门牌号信息
                       aMapLocation.getCityCode();//城市编码
                       aMapLocation.getAdCode();//地区编码
                       // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置

                       }else {
                       Log.i("weinihao", "onLocationChanged: "+"laile3");
                       //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                       Log.e("AmapError", "location Error, ErrCode:"
                               + aMapLocation.getErrorCode() + ", errInfo:"
                               + aMapLocation.getErrorInfo());
                       Toast.makeText(Sousuo2Activity.this, "定位失败", Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                   }


                   }
               }


           @Override
           public void activate(OnLocationChangedListener onLocationChangedListener) {
               mListener = onLocationChangedListener;
           }

           @Override
           public void deactivate() {
               mListener = null;
           }
       }
