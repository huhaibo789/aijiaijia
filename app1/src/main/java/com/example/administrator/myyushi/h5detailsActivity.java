package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Urlutil.LatitudeConstant;
import Urlutil.Longtitudecookie;
import adapter.h5detailActivityadapter;
import bean.gouwu2;
import bean.gouwu3;
import bean.showtime;
import bean.xiangqingbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import request.BiZhiRequest;
import request.LoadingDialog;
import util.Myapp;
import utils.DBHelper7;
import utils.FileUtils;
import utils.FileUtils29;
import utils.FileUtils6;
import view.SearchPagerGaoview2;

public class h5detailsActivity extends AppCompatActivity implements View.OnClickListener, PopupWindow.OnDismissListener,PlatformActionListener,LocationSource, AMapLocationListener {
    String fas = "1";
    LoadingDialog dialog;
    String status;
    String lable;
    @Bind(R.id.searchmoregaoview2)
    SearchPagerGaoview2 searchmoregaoview2;
    @Bind(R.id.xq_tv1)
    TextView xqTv1;
    @Bind(R.id.yuanjia_tv)
    TextView yuanjiaTv;
    @Bind(R.id.xq_iv1)
    ImageView xqIv1;
    @Bind(R.id.fenxiang)
    ImageView fenxiang;
    @Bind(R.id.newgouwu)
    ImageView newgouwu;
    @Bind(R.id.newtext_chart_num)
    TextView newtextChartNum;
    @Bind(R.id.new3d)
    ImageView new3d;
    @Bind(R.id.xq_tv)
    TextView xqTv;
    @Bind(R.id.ptlm)
    PullUpToLoadMore ptlm;
    @Bind(R.id.yaoqiu_tv1)
    TextView yaoqiuTv1;
    @Bind(R.id.yaoqiu_tv2)
    TextView yaoqiuTv2;
    @Bind(R.id.yaoqiu_tv3)
    TextView yaoqiuTv3;
    @Bind(R.id.shangpin_tv)
    TextView shangpinTv;
    @Bind(R.id.xiangqing_more)
    TextView xiangqingMore;
    @Bind(R.id.commodity_ly)
    RelativeLayout commodityLy;
    @Bind(R.id.layout9)
    TabLayout layout9;

    /*@Bind(R.id.guige_lv2)
    mylistview guigeLv2;
    @Bind(R.id.guige_lv1)
    mylistview guigeLv1;*/
    @Bind(R.id.now_kefu)
    Button nowKefu;
    @Bind(R.id.shop_iv)
    Button shopIv;
    @Bind(R.id.buy_iv)
    Button buyIv;
    @Bind(R.id.bottom_radioGroup)
    RadioGroup bottomRadioGroup;
    @Bind(R.id.hha)
    RelativeLayout hha;
    @Bind(R.id.bottom_tv)
    TextView bottomTv;
    @Bind(R.id.dibu)
    RelativeLayout dibu;
    @Bind(R.id.goods_tv)
    TextView goodsTv;
    @Bind(R.id.goods_tv1)
    TextView goodsTv1;
    @Bind(R.id.goods_tv2)
    TextView goodsTv2;
    @Bind(R.id.btn)
    ImageView btn;
    @Bind(R.id.xingqing_finishd)
    ImageView xingqingFinishd;
    @Bind(R.id.view_xians)
    View viewXians;
    @Bind(R.id.product_pinglun)
    RelativeLayout productPinglun;
    @Bind(R.id.xingqing_finishd1)
    ImageView xingqingFinishd1;
    @Bind(R.id.kefu_xiangqing)
    ImageView kefuXiangqing;
    @Bind(R.id.shop_xiangqing)
    ImageView shopXiangqing;
    @Bind(R.id.text_chart_num)
    TextView textChartNum;
    @Bind(R.id.share_iv1)
    ImageView shareIv1;
    @Bind(R.id.toubu_ry)
    RelativeLayout toubuRy;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.ceshi)
    ImageView ceshi;
    @Bind(R.id.topscroller)
    MyScrollView topscroller;
    @Bind(R.id.web_load)
    PullToRefreshWebView webLoad;
    @Bind(R.id.list_pull)
    PullToRefreshListView listPull;
    private ArrayList<String> content1 = new ArrayList<>();
    private ArrayList<String> time1 = new ArrayList<>();
    private ArrayList<String> smalltitle = new ArrayList<>();
    private WebView webs;
    private WebView webs1;
    private ArrayList<String> logins7 = new ArrayList<>();
    private JSONObject resposeobject;
    private List<gouwu2> gou23 = new ArrayList<>();
    private List<gouwu3> gouwu24 = new ArrayList<>();
    private RequestQueue queue;
    private String jieguo = "0";
    private String jieguo1 = "0";
    private ArrayList<String> details = new ArrayList<>();
    private ArrayList<String> details1 = new ArrayList<>();
    private ArrayList<String> detail2 = new ArrayList<>();
    private ArrayList<String> detail3 = new ArrayList<>();
    private ArrayList<xiangqingbean.ResponseJsonBean.ListProductSkuBean> skudetails = new ArrayList<>();
    private ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean> xqbean = new ArrayList<>();
    private ArrayList<String> wuyu = new ArrayList<>();
    private Context context;
    private int type;
    String url;
    private ViewGroup anim_mask_layout;//动画层
    private Handler handle = new Handler();
    String result13;
    public h5popwindow popWindow;
    private PopupWindow pop;
    ImageLoader loader;
    private String picurl;
    ArrayList<String> skuid=new ArrayList<>();
    ArrayList<String> nowprice=new ArrayList<>();
    ArrayList<String> previousprice=new ArrayList<>();
    ArrayList<String> skuvaluename=new ArrayList<>();
    ArrayList<String> servicepriceyes=new ArrayList<>();
    ArrayList<String> servicepriceno=new ArrayList<>();
    ArrayList<String> stock=new ArrayList<>();
    ArrayList<String> serviceyes=new ArrayList<>();
    ArrayList<String> serviceno=new ArrayList<>();
    ArrayList<String> classsku=new ArrayList<>();
    ArrayList<String> installservice=new ArrayList<>();
    ArrayList<String> productid=new ArrayList<>();
    ArrayList<String> skufreight=new ArrayList<>();
    h5detailsActivity activity1;
    public AMapLocationClientOption mLocationOption=null;   //声明mLocationOption对象，定位参数
    private AMapLocationClient mLocationClient = null;    //声明AMapLocationClient类对象，定位发起端
    private String message;
    private View v_city;
    private List<showtime> showtimes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5details);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        loader = ((Myapp) getApplication()).getLoader();
        final PullUpToLoadMore ptlm = (PullUpToLoadMore) findViewById(R.id.ptlm);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptlm.scrollToTop();
            }
        });
        stat();
        popWindow = new h5popwindow(h5detailsActivity.this);
        postdata();
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        FileUtils29 file = new FileUtils29();
        String infomation = file.readDataFromFile(this);
        Intent intent = getIntent();
        String result = intent.getStringExtra("uid");
        try {
            URLEncoder.encode(infomation,"utf-8"); //"UTF-8"
            url = "https://api.aijiaijia.com/api_products_info?"+"pid="+result+"&"+"cityname="+ URLEncoder.encode(infomation,"utf-8"); //"UTF-8";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("asand", "onCreate: "+infomation);
        if (result!= null) {
            FileUtils ful = new FileUtils();
            ful.saveDataToFile(this, url);
            initdata();
        }
        Log.i("aiya", "onCreate: " + url);
        settab();
        postnum();  //购物车数量
        location();
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("aa", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putInt("age", 22);         //根据键值对添加数据
        edit.putString("name", "LJie");
        edit.commit();  //保存数据信息
        //指定操作的文件名称
        SharedPreferences share1 = getSharedPreferences("aa", MODE_PRIVATE);
        Log.i("buxiannia", "onCreate: "+share1.getString("name", "信息为空..."));
        Log.i("buxiannia1", "onCreate: "+share1.getInt("age", 0));
        StatusBarCompat.setStatusBarColor(h5detailsActivity.this, Color.parseColor("#222222"), true);
    }

    private void showpopwindow(String discount) {
        Log.i("heheis", "showpopwindow: "+"heihei");
        v_city = LayoutInflater.from(h5detailsActivity.this).inflate(R.layout.activity_detailpic, null);
        pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT , true);
        ImageView pop_picture= (ImageView) v_city.findViewById(R.id.pop_picture);
        TextView prict_yuan= (TextView) v_city.findViewById(R.id.prict_yuan);
        TextView price_discount= (TextView) v_city.findViewById(R.id.price_discount);
        TextView price_tv= (TextView) v_city.findViewById(R.id.price_tv);
        price_tv.setText(discount);
        prict_yuan.setVisibility(View.INVISIBLE);
        price_discount.setVisibility(View.VISIBLE);
        setBackgroundAlpha(0.5f);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(v_city, Gravity.CENTER, 0, 0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
        pop_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(h5detailsActivity.this, "领取成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                pop.dismiss();
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                String  result1 = format.format(date);
                Log.i("ganpao", "onClick: "+result1);
                DBHelper7 helper7 = new DBHelper7(h5detailsActivity.this);
                helper7.insert(new showtime(result1+"pid="+xqbean.get(0).getId(),result1));

            }
        });
    }
    private void showpopwindow1(int showsavemoney) {
        Log.i("heheis1", "showpopwindow: "+"heihei");
        v_city = LayoutInflater.from(h5detailsActivity.this).inflate(R.layout.activity_detailpic, null);
        pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT , true);
        ImageView pop_picture= (ImageView) v_city.findViewById(R.id.pop_picture);
        TextView prict_yuan= (TextView) v_city.findViewById(R.id.prict_yuan);
        TextView price_discount= (TextView) v_city.findViewById(R.id.price_discount);
        TextView price_tv= (TextView) v_city.findViewById(R.id.price_tv);
        price_tv.setText(String.valueOf(showsavemoney));
        prict_yuan.setVisibility(View.VISIBLE);
        price_discount.setVisibility(View.INVISIBLE);
        setBackgroundAlpha(0.5f);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(v_city, Gravity.CENTER, 0, 0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
        pop_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(h5detailsActivity.this, "领取成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                pop.dismiss();
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                String  result1 = format.format(date);
                Log.i("ganpao", "onClick: "+result1);
                DBHelper7 helper7 = new DBHelper7(h5detailsActivity.this);
                helper7.insert(new showtime(result1+"pid="+xqbean.get(0).getId(),result1));
               /* FileUtils43 file1=new FileUtils43();
                file1.saveDataToFile(h5detailsActivity.this,);*/

            }
        });
    }

    private void postnum() {
        queue = Volley.newRequestQueue(h5detailsActivity.this);
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
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                textChartNum.setVisibility(View.VISIBLE);
                                newtextChartNum.setVisibility(View.VISIBLE);
                                String result4 = resposeobject.getString("shopcard_num");
                                if (!result4.equals("0")) {
                                    textChartNum.setBackgroundResource(R.drawable.bubblet);
                                    newtextChartNum.setBackgroundResource(R.drawable.bubblet);
                                    newtextChartNum.setText(result4);
                                    textChartNum.setText(result4);
                                } else {
                                    textChartNum.setVisibility(View.INVISIBLE);
                                    newtextChartNum.setVisibility(View.INVISIBLE);
                                }

                            } else {
                                textChartNum.setVisibility(View.INVISIBLE);
                                newtextChartNum.setVisibility(View.INVISIBLE);


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
    private void setlistener() {

        shareIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
                //showShare1(h5detailsActivity.this," ",true);
            }
        });
        shopXiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(h5detailsActivity.this);
                String url = "https://api.aijiaijia.com/api_userinfo";
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
                                    if (result3.equals("6")) {
                                        Toast.makeText(h5detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(h5detailsActivity.this, LoginActivity.class);
                                        startActivityForResult(intent, 5);
                                    } else {
                                        Intent intent = new Intent(h5detailsActivity.this, GouwuActivity1.class);
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
        kefuXiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = String.valueOf(xqbean.get(0).getId());
                queue = Volley.newRequestQueue(h5detailsActivity.this);
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
                                    if (result3.equals("0")) {
                                        Toast.makeText(h5detailsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    } else if (result3.equals("1")) {
                                        Toast.makeText(h5detailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                                    } else if (result3.equals("6")) {

                                        Intent intent = new Intent(h5detailsActivity.this, LoginActivity.class);
                                        startActivityForResult(intent, 5);
                                        Toast.makeText(h5detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
        xingqingFinishd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Log.i("jihde", "setlistener: " + status);
        if (status!=null) {
            if (status.equals("1")) {
                buyIv.setClickable(true);
                buyIv.setBackgroundColor(Color.parseColor("#fbd23a"));
                buyIv.setText("立即购买");
                buyIv.setVisibility(View.VISIBLE);
                buyIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nowprice.clear();
                        skuid.clear();
                        previousprice.clear();
                        skuvaluename.clear();
                        servicepriceyes.clear();
                        servicepriceno.clear();
                        serviceyes.clear();
                        serviceno.clear();
                        stock.clear();
                        classsku.clear();
                        skufreight.clear();
                        installservice.clear();
                        String message="购买";//区分是购物车的Popwindow
                        Log.i("pianpian", "onClick: "+skudetails.size());
                        for (int i = 0; i <skudetails.size() ; i++) {
                            skuid.add(String.valueOf(skudetails.get(i).getSku_id()));
                            nowprice.add(String.valueOf(skudetails.get(i).getSku_sell_price()));
                            previousprice.add( String.valueOf(skudetails.get(i).getSku_price()));
                            skuvaluename.add(String.valueOf(skudetails.get(i).getSku_value_name()));
                            servicepriceyes.add(String.valueOf(skudetails.get(i).getSku_service_price_yes()));
                            servicepriceno.add(String.valueOf(skudetails.get(i).getSku_service_price_no()));
                            serviceyes.add(String.valueOf(skudetails.get(i).getSku_service_value_yes()));
                            serviceno.add(String.valueOf(skudetails.get(i).getSku_service_value_no()));
                            stock.add( String.valueOf(skudetails.get(i).getSku_stock()));
                            classsku.add(String.valueOf(skudetails.get(i).getSku_name()));
                            installservice.add(String.valueOf(skudetails.get(i).getSku_service()));
                            skufreight.add(String.valueOf(skudetails.get(i).getSku_freight()));

                        }
                        productid.add( String.valueOf(xqbean.get(0).getId()));
                        String pictureurl = String.valueOf(wuyu.get(0));
                       /* String skuid=String.valueOf(skudetails.get(0).getSku_id());
                        String nowprice = String.valueOf(skudetails.get(0).getSku_sell_price());
                        String previousprice = String.valueOf(skudetails.get(0).getSku_price());
                        String skuvaluename = String.valueOf(skudetails.get(0).getSku_value_name());
                        String servicepriceyes = String.valueOf(skudetails.get(0).getSku_service_price_yes());
                        String servicepriceno = String.valueOf(skudetails.get(0).getSku_service_price_no());
                        String serviceyes = String.valueOf(skudetails.get(0).getSku_service_value_yes());
                        String serviceno = String.valueOf(skudetails.get(0).getSku_service_value_no());
                        String stock = String.valueOf(skudetails.get(0).getSku_stock());
                        String pictureurl = String.valueOf(wuyu.get(0));
                        String classsku = String.valueOf(skudetails.get(0).getSku_name());
                        String installservice = String.valueOf(skudetails.get(0).getSku_service());
                        String productid = String.valueOf(xqbean.get(0).getId());
                        Log.i("cainiao", "onClick: " + nowprice);
                        Log.i("cainiao1", "onClick: " + previousprice);
                        Log.i("cainiao2", "onClick: " + skuvaluename);
                        Log.i("cainiao3", "onClick: " + servicepriceyes);*/
                        popWindow.showAsDropDown(v,message, loader,skuid,skufreight, productid,nowprice, previousprice, skuvaluename, servicepriceyes, servicepriceno, serviceyes, serviceno, stock, pictureurl, classsku, installservice);
                        setBackgroundAlpha(0.5f);
                      /*  if (xqbean != null) {
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
            }else {
                buyIv.setBackgroundColor(Color.parseColor("#DADADA"));
                buyIv.setText("已下架");
                buyIv.setClickable(false);

            }


        }
      /*  Log.i("status", "setlistener: "+status);
        buyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.showAsDropDown(v);
                setBackgroundAlpha(0.5f);


            }
        });*/
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
        newgouwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(h5detailsActivity.this);
                String url = "https://api.aijiaijia.com/api_userinfo";
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
                                    if (result3.equals("6")) {
                                        Toast.makeText(h5detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(h5detailsActivity.this, LoginActivity.class);
                                        startActivityForResult(intent, 5);

                                    } else {
                                        Intent intent = new Intent(h5detailsActivity.this, GouwuActivity1.class);
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
        xqIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = String.valueOf(xqbean.get(0).getId());
                queue = Volley.newRequestQueue(h5detailsActivity.this);
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
                                    if (result3.equals("0")) {
                                        Toast.makeText(h5detailsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    } else if (result3.equals("1")) {
                                        Toast.makeText(h5detailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                                    } else if (result3.equals("6")) {

                                        Intent intent = new Intent(h5detailsActivity.this, LoginActivity.class);
                                        startActivityForResult(intent, 5);
                                        Toast.makeText(h5detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
        commodityLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xqbean.size() != 0) {
                    final String id = String.valueOf(xqbean.get(0).getId());
                    FileUtils fu = new FileUtils();
                    fu.saveDataToFile(h5detailsActivity.this, id);
                    Intent intent1 = new Intent(h5detailsActivity.this, evaluateActivity.class);
                    startActivity(intent1);
                }
            }
        });

        xingqingFinishd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nowKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUtils ful = new FileUtils();
                String dd = ful.readDataFromFile(h5detailsActivity.this);
                if (xqbean.size() != 0) {
                    String ww = xqbean.get(0).getProduct_name();
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(dd, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(h5detailsActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                } else {
                    Toast toast = Toast.makeText(h5detailsActivity.this, "请检查网络!!!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        shopIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message="购物车";//区分是购物车的Popwindow
                nowprice.clear();
                skuid.clear();
                previousprice.clear();
                skuvaluename.clear();
                servicepriceyes.clear();
                servicepriceno.clear();
                serviceyes.clear();
                serviceno.clear();
                stock.clear();
                classsku.clear();
                skufreight.clear();
                installservice.clear();
                for (int i = 0; i <skudetails.size() ; i++) {
                    skuid.add(String.valueOf(skudetails.get(i).getSku_id()));
                    nowprice.add(String.valueOf(skudetails.get(i).getSku_sell_price()));
                    previousprice.add( String.valueOf(skudetails.get(i).getSku_price()));
                    skuvaluename.add(String.valueOf(skudetails.get(i).getSku_value_name()));
                    servicepriceyes.add(String.valueOf(skudetails.get(i).getSku_service_price_yes()));
                    servicepriceno.add(String.valueOf(skudetails.get(i).getSku_service_price_no()));
                    serviceyes.add(String.valueOf(skudetails.get(i).getSku_service_value_yes()));
                    serviceno.add(String.valueOf(skudetails.get(i).getSku_service_value_no()));
                    stock.add( String.valueOf(skudetails.get(i).getSku_stock()));
                    classsku.add(String.valueOf(skudetails.get(i).getSku_name()));
                    installservice.add(String.valueOf(skudetails.get(i).getSku_service()));
                    skufreight.add(String.valueOf(skudetails.get(i).getSku_freight()));
                }
                productid.add( String.valueOf(xqbean.get(0).getId()));
                String pictureurl = String.valueOf(wuyu.get(0));
               /* String skuid=String.valueOf(skudetails.get(0).getSku_id());
                String nowprice = String.valueOf(skudetails.get(0).getSku_sell_price());
                String previousprice = String.valueOf(skudetails.get(0).getSku_price());
                String skuvaluename = String.valueOf(skudetails.get(0).getSku_value_name());
                String servicepriceyes = String.valueOf(skudetails.get(0).getSku_service_price_yes());
                String servicepriceno = String.valueOf(skudetails.get(0).getSku_service_price_no());
                String serviceyes = String.valueOf(skudetails.get(0).getSku_service_value_yes());
                String serviceno = String.valueOf(skudetails.get(0).getSku_service_value_no());
                String stock = String.valueOf(skudetails.get(0).getSku_stock());
                String pictureurl = String.valueOf(wuyu.get(0));
                String classsku = String.valueOf(skudetails.get(0).getSku_name());
                String installservice = String.valueOf(skudetails.get(0).getSku_service());
                String productid = String.valueOf(xqbean.get(0).getId());*/
                Log.i("hahas", "onClick: "+skuid);
                popWindow.showAsDropDown(view,message, loader,skuid,skufreight, productid,nowprice, previousprice, skuvaluename, servicepriceyes, servicepriceno, serviceyes, serviceno, stock, pictureurl, classsku, installservice);
                setBackgroundAlpha(0.5f);
                // postdata1();
            }
        });
    }


    public static void showShare1(Context context, String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();

        oks.setUrl("http://www.mob.com "); //微信不绕过审核分享链接
        //  oks.setComment(h5detailsActivity.getString(R.string.app_share_comment)); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite("ShareSDK");  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setSiteUrl("http://mob.com");//QZone
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("This is a beautiful place!");
        oks.setLatitude(23.169f);
        oks.setLongitude(112.908f);
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.i("ssss","onComplete");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i("ssss","onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.i("ssss","onCancel");
            }
        });
        // 去自定义不同平台的字段内容
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (platform.getName().equals(Wechat.NAME)){


                }
            }
        });
        // 在九宫格设置自定义的图标
        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_back_white);
        String label = "ShareSDK";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

            }
        };
        oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }


    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
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

    private void postcomment() {
        queue = Volley.newRequestQueue(this);
        String url = "https://api.aijiaijia.com/api_comments";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu565", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        Log.i("dasfdd", "onResponse: " + str);
                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject responseobject = jsonObject.getJSONObject("responseJson");
                            JSONArray jsonarry = responseobject.getJSONArray("list");
                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject object = jsonarry.getJSONObject(i);
                                String data = object.getString("content");
                                content1.add(data);
                                String data1 = object.getString("showname");
                                time1.add(data1);
                                String data2 = object.getString("time");
                                smalltitle.add(data2);
                            }
                            Log.i("heihie", "onResponse: "+content1.toString());
                            Log.i("lala", "onResponse: "+time1.toString());
                            Log.i("lala1", "onResponse: "+smalltitle.toString());
                            if (content1.size() == 0) {
                                goodsTv.setVisibility(View.GONE);
                                goodsTv1.setVisibility(View.GONE);
                                goodsTv2.setVisibility(View.GONE);

                            } else if (content1.size() == 1) {
                                goodsTv.setVisibility(View.VISIBLE);
                                goodsTv1.setVisibility(View.VISIBLE);
                                goodsTv2.setVisibility(View.VISIBLE);
                                goodsTv.setText(time1.get(0));
                                goodsTv1.setText(smalltitle.get(0));
                                goodsTv2.setText(content1.get(0));

                            } else {
                                goodsTv.setVisibility(View.VISIBLE);
                                goodsTv1.setVisibility(View.VISIBLE);
                                goodsTv2.setVisibility(View.VISIBLE);
                                goodsTv.setText(time1.get(0));
                                goodsTv1.setText(smalltitle.get(0));
                                goodsTv2.setText(content1.get(0));

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
                if (xqbean.size() != 0) {
                    String id2 = String.valueOf(xqbean.get(0).getId());
                    if (id2 != null) {
                        map.put("page", "1");
                        map.put("readpid", id2);
                    }
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
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.i("hcuass", "onError: "+hashMap);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.i("hcuass", "onError: "+throwable);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.i("hcuass", "onError: "+i);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        String aa = aMapLocation.toString();
        String data = new Gson().toJson(aa);
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                LatitudeConstant.latitudecookie=String.valueOf(aMapLocation.getLatitude());//获取纬度
                Longtitudecookie.longtitudecookie=String.valueOf(aMapLocation.getLongitude());//获取经度
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
                    FileUtils29 file=new FileUtils29();
                    file.saveDataToFile(this,message);

                }

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
            FileUtils29 file=new FileUtils29();
            file.saveDataToFile(this,"all");
            Toast.makeText(h5detailsActivity.this, "定位失败", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    class webViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
          /*  view.loadUrl(url);
            return true;*/
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.i("caodan", "onReceivedError: " + errorCode);
            if (errorCode == -2) {

            }
        }
    }

    private void settab() {
        String[] tabArr = {"商品详情", "规格参数", "售后服务"};
        layout9.setSelectedTabIndicatorColor(Color.parseColor("#fbd23a"));
        layout9.setTabTextColors(Color.BLACK, Color.BLACK);
        layout9.setTabMode(layout9.MODE_FIXED);
        for (int i = 0; i < tabArr.length; i++) {
            TabLayout.Tab tab = layout9.newTab()
                    .setText(tabArr[i])
                    .setTag(i);
            layout9.addTab(tab);
        }


    }

    private void addshuju() {
        xqTv.setText(xqbean.get(0).getProduct_name());
    /*    String hh2 = String.valueOf(xqbean.get(0).getProduct_nowprice());*/
        String hh2 = String.valueOf(skudetails.get(0).getSku_sell_price());
        String hh4 = String.valueOf(skudetails.get(0).getSku_price());
        if (hh2.equals("0") || hh2.equals("null")) {
            xqTv1.setText("￥" + hh4);
            yuanjiaTv.setVisibility(View.GONE);
        } else if (hh4.equals("0") || hh4.equals("null")) {
            xqTv1.setText("￥" + hh2);
            yuanjiaTv.setVisibility(View.GONE);
        } else if (hh2.equals(hh4)) {
            xqTv1.setText("￥" + hh2);
            yuanjiaTv.setVisibility(View.GONE);
        } else {
            xqTv1.setText("￥" + hh2);
            yuanjiaTv.setText("原价:" + hh4);
            yuanjiaTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    private void addlunbo() {
        searchmoregaoview2.updatePager1(wuyu, loader);
    }

    private void postdata() {
        queue = Volley.newRequestQueue(h5detailsActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_statistics";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("heihyas", "onResponse: "+str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            result13 = resposeobject.getString("op");


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
        WindowManager.LayoutParams lp = h5detailsActivity.this.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        h5detailsActivity.this.getWindow().setAttributes(lp);
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


    private void postdata1() {
        queue = Volley.newRequestQueue(h5detailsActivity.this);
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
                                        Toast toast = Toast.makeText(h5detailsActivity.this, "购物车已有该商品", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                                if (!jieguo.equals("1")) {
                                    if (xqbean.size() != 0) {
                                        final String id = String.valueOf(xqbean.get(0).getId());
                                        queue = Volley.newRequestQueue(h5detailsActivity.this);
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
                                                                Toast.makeText(h5detailsActivity.this, "加入失败", Toast.LENGTH_SHORT).show();
                                                            } else if (result3.equals("1")) {
                                                                // setAnim(v);
                                                                Toast.makeText(h5detailsActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
                                                                postdata();
                                                                postnum();

                                                            } else if (result3.equals("6")) {
                                                                Log.i("jiaba", "onResponse: " + result3);
                                                                Toast toast = Toast.makeText(h5detailsActivity.this, "未登录", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                                Intent intent = new Intent(h5detailsActivity.this, LoginActivity.class);
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
                                        Toast toast = Toast.makeText(h5detailsActivity.this, "请检查网络!!!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }

                                }
                            } else {
                                Log.i("zhananp", "onResponse: " + "heihei");
                                Toast toast = Toast.makeText(h5detailsActivity.this, "未登录", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent intent = new Intent(h5detailsActivity.this, LoginActivity.class);
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
        FileUtils6 ff = new FileUtils6();
        String sharid=ff.readDataFromFile(h5detailsActivity.this);   //用户的uid
        Log.i("husd", "showShare: " + xqbean.get(0).getId());
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(xqbean.get(0).getProduct_name());
        if(sharid!=null&&!sharid.equals("nologin")){
            Date date = new Date();
            long sharetime=date.getTime()/1000;
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId()+"&shareduid="+sharid+"&sharedtime="+String.valueOf(sharetime));
        }else {
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId());
        }

        // text是分享文本，所有平台都需要这个字段
        oks.setText("【有人@我】质量这么好的商品,快戳我看看吧!");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        Log.i("honglong", "showShare: "+wuyu.get(0));
        oks.setImageUrl(wuyu.get(0));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        if(sharid!=null&&!sharid.equals("nologin")){
            Date date = new Date();
            long sharetime=date.getTime()/1000;
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId()+"&shareduid="+sharid+"&sharedtime="+String.valueOf(sharetime));
        }else {
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId());
        }

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(xqbean.get(0).getProduct_name());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(xqbean.get(0).getProduct_name());
        if(sharid!=null&&!sharid.equals("nologin")){
            Date date = new Date();
            long sharetime=date.getTime()/1000;
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId()+"&shareduid="+sharid+"&sharedtime="+String.valueOf(sharetime));
        }else {
             // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("https://api.aijiaijia.com/m/index.html?pid=" + xqbean.get(0).getId());
        }

        // 启动分享GUI
        oks.show(this);


    }

    private void initdata() {
        getxqlist();
    }

    private void getxqlist() {
        FileUtils ful = new FileUtils();
        String dd = ful.readDataFromFile(this);
        wuyu.clear();
        xqbean.clear();
        skudetails.clear();
        BiZhiRequest<xiangqingbean> request = new BiZhiRequest<xiangqingbean>(xiangqingbean.class, url, new Response.Listener<xiangqingbean>() {
            @Override
            public void onResponse(xiangqingbean response) {
                if (response != null && response.getResponseJson().getList_product() != null && response.getResponseJson().getList_product_sku() != null) {
                    xqbean.addAll(response.getResponseJson().getList_product());
                    skudetails.addAll(response.getResponseJson().getList_product_sku());
                    dialog.dismiss();
                    postcomment();
                    topscroller.setBackgroundColor(Color.parseColor("#ffffff"));
                    status = String.valueOf(xqbean.get(0).getProduct_status());
                    lable = String.valueOf(xqbean.get(0).getPromotion_label());
                    picurl = String.valueOf(xqbean.get(0).getProduct_tag_pic());
                    if (picurl != null) {
                        loader.loadImage(picurl, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                ceshi.setImageBitmap(loadedImage);
                            }
                        });
                    }
                    if (lable.equals("null")) {
                        detailTv.setVisibility(View.GONE);
                    } else {
                        detailTv.setVisibility(View.VISIBLE);
                        detailTv.setText(lable);
                    }
                    addshuju();
                    setlistener();
                    String showflag=String.valueOf(xqbean.get(0).getProduct_tipshow_flag());//如果flag为2显示折扣，3显示节省money
                    DBHelper7 helper7 = new DBHelper7(h5detailsActivity.this);
                    showtimes = helper7.queryAll();
                    if(showflag!=null&&showflag.equals("2")){
                        String showdisconunt=String.valueOf(xqbean.get(0).getProduct_tipshow_discount());
                        Log.i("aiyasyws", "onResponse: "+showdisconunt);
                        Log.i("aiyasyws1", "onResponse: "+xqbean.get(0).getProduct_tipshow_discount());
                        if(showdisconunt!=null){
                            Date date = new Date();
                            DateFormat format = new SimpleDateFormat("yyyyMMdd");
                            String   result= format.format(date);
                            if(showtimes!=null&&!showtimes.toString().contains(result)){
                                if (showtimes.size() != 0) {
                                    for (int j = 0; j < showtimes.size(); j++) {
                                        helper7.delete(showtimes.get(j).get_id());
                                    }
                                }
                                showpopwindow(showdisconunt);
                            }else if(showtimes!=null&&showtimes.toString().contains(result)&&!showtimes.toString().contains(result+"pid="+xqbean.get(0).getId())){
                                showpopwindow(showdisconunt);
                            }else if (showtimes.size()==0){
                                showpopwindow(showdisconunt);
                            }

                        }
                    }else if(showflag!=null&&showflag.equals("3")){
                        double d=Double.parseDouble(String.valueOf(xqbean.get(0).getProduct_tipshow_saved()) );
                        int showsavemoney=(int)d;
                        if(String.valueOf(showsavemoney)!=null){
                            Date date = new Date();
                            DateFormat format = new SimpleDateFormat("yyyyMMdd");
                            String   result= format.format(date);
                            if(showtimes!=null&&!showtimes.toString().contains(result)){
                                if (showtimes.size() != 0) {
                                    for (int j = 0; j < showtimes.size(); j++) {
                                        helper7.delete(showtimes.get(j).get_id());
                                    }
                                }
                                showpopwindow1(showsavemoney);
                            }else if(showtimes!=null&&showtimes.toString().contains(result)&&!showtimes.toString().contains(result+"pid="+xqbean.get(0).getId())){
                                showpopwindow1(showsavemoney);
                            }else if (showtimes.size()==0){
                                showpopwindow1(showsavemoney);
                            }
                        }
                    }
                }
                if (response != null && response.getResponseJson().getList_product_urlpics() != null) {
                    wuyu.addAll(response.getResponseJson().getList_product_urlpics());
                    addlunbo();
                }
                if (response != null && response.getResponseJson().getList_product_detail() != null && response.getResponseJson().getList_product_aftersaleservice() != null && response.getResponseJson().getList_product_attribute_type() != null && response.getResponseJson().getList_product_attribute_parm() != null) {
                    details.addAll(response.getResponseJson().getList_product_detail());
                    details1.addAll(response.getResponseJson().getList_product_aftersaleservice());
                    detail2.addAll(response.getResponseJson().getList_product_attribute_type());
                    detail3.addAll(response.getResponseJson().getList_product_attribute_parm());
                    enableloadmore();
                    webLoad.setVisibility(View.VISIBLE);
                    listPull.setVisibility(View.GONE);
                    webs = webLoad.getRefreshableView();
                    webLoad.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<WebView>() {
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {
                            setendPullToRefreshStyle();
                            ptlm.scrollToTop();
                            webLoad.onRefreshComplete();
                        }
                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {

                            setUptenloadmore();
                            webLoad.onRefreshComplete();
                        }
                    });
                    listPull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                            setendPullToRefreshStyle();
                            ptlm.scrollToTop();
                            listPull.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listPull.onRefreshComplete();
                                }
                            }, 500);
                        }
                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                            setUptenloadmore();
                            listPull.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listPull.onRefreshComplete();
                                }
                            },1000);
                        }
                    });
                    @SuppressLint("SetJavaScriptEnabled")
                    WebSettings webSettings = webs.getSettings();
                    //设置WebView属性，能够执行Javascript脚本
                    webSettings.setJavaScriptEnabled(true);
                    //设置可以访问文件
                    webSettings.setAllowFileAccess(true);
                    //设置支持缩放
                    webSettings.setBuiltInZoomControls(true);
                    //加载需要显示的网页
                    webs.loadUrl(details.get(0).toString());
                    //设置Web视图
                    webs.setWebViewClient(new webViewClient());
                    //Web视图*/
                    layout9.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {

                            if (tab.getText().equals("商品详情")) {
                                webLoad.setVisibility(View.VISIBLE);
                                listPull.setVisibility(View.GONE);
                                @SuppressLint("SetJavaScriptEnabled") final
                                WebSettings webSettings = webs.getSettings();
                                //设置WebView属性，能够执行Javascript脚本
                                webSettings.setJavaScriptEnabled(true);
                                //设置可以访问文件
                                webSettings.setAllowFileAccess(true);
                                //设置支持缩放
                                webSettings.setBuiltInZoomControls(true);
                                //加载需要显示的网页
                                webs.loadUrl(details.get(0).toString());
                                //设置Web视图
                                webs.setWebViewClient(new webViewClient());
                                //Web视图

                            } else if (tab.getText().equals("售后服务")) {
                                webLoad.setVisibility(View.VISIBLE);
                                listPull.setVisibility(View.GONE);
                                @SuppressLint("SetJavaScriptEnabled") final
                                WebSettings webSettings = webs.getSettings();
                                //设置WebView属性，能够执行Javascript脚本
                                webSettings.setJavaScriptEnabled(true);
                                //设置可以访问文件
                                webSettings.setAllowFileAccess(true);
                                //设置支持缩放
                                webSettings.setBuiltInZoomControls(true);
                                //加载需要显示的网页
                                webs.loadUrl(details1.get(0).toString());
                                //设置Web视图
                                webs.setWebViewClient(new webViewClient());
                                //Web视图
                            } else {
                                listPull.setVisibility(View.VISIBLE);
                                webLoad.setVisibility(View.GONE);
                                h5detailActivityadapter adapter = new h5detailActivityadapter(h5detailsActivity.this, detail2, detail3);
                                listPull.setAdapter(adapter);
                            }
                        }
                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {
                        }
                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                        }
                    });
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(h5detailsActivity.this, "请检查网络!!!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
        queue.add(request);
    }
    private void setUptenloadmore() {
        ILoadingLayout il=webLoad.getLoadingLayoutProxy(false,true);  //false true 意思是上拉时的布局
        ILoadingLayout i2=listPull.getLoadingLayoutProxy(false,true);  //false true 意思是上拉时的布局
      /*  //获取用于设置下拉刷新中显示内容的设置对象
        ILoadingLayout il = webLoad.getLoadingLayoutProxy();
        ILoadingLayout i2 = listPull.getLoadingLayoutProxy();*/
        //设置下拉状态时的提示文字
        il.setPullLabel("已经到底部了");
        i2.setPullLabel("已经到底部了");
        //设置下拉刷新中的图片
        il.setLoadingDrawable(getResources().getDrawable(R.drawable.yy_jt_down));
        i2.setLoadingDrawable(getResources().getDrawable(R.drawable.yy_jt_down));
        //设置正在刷新过程中的提示文字
        il.setRefreshingLabel("已经到底部了");
        i2.setRefreshingLabel("已经到底部了");
        //设置松手提示文字i1
        il.setReleaseLabel("已经到底部了");
        i2.setReleaseLabel("已经到底部了");
    }
    private void enableloadmore() {
        webLoad.setMode(PullToRefreshBase.Mode.BOTH);
        listPull.setMode(PullToRefreshBase.Mode.BOTH);
    }
    private void setendPullToRefreshStyle() {
        //获取下啦布局
        ILoadingLayout il=webLoad.getLoadingLayoutProxy(true,false);//true,false  意思是下拉时的布局
        ILoadingLayout i2=listPull.getLoadingLayoutProxy(true,false);//true,false  意思是下拉时的布局
      /*  //获取用于设置下拉刷新中显示内容的设置对象
        ILoadingLayout il = webLoad.getLoadingLayoutProxy();
        ILoadingLayout i2 = listPull.getLoadingLayoutProxy();*/
        //设置下拉状态时的提示文字
        il.setPullLabel("继续下拉回到顶部");
        i2.setPullLabel("继续下拉回到顶部");
        //设置正在刷新过程中的提示文字
        il.setRefreshingLabel("正在回到顶部");
        i2.setRefreshingLabel("正在回到顶部");
        //设置下拉刷新的图标
        il.setLoadingDrawable(getResources().getDrawable(R.drawable.yy_jt_down));
        i2.setLoadingDrawable(getResources().getDrawable(R.drawable.yy_jt_down));
        //设置松手提示文字i1
        il.setReleaseLabel("松开回到顶部");
        i2.setReleaseLabel("松开回到顶部");
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(h5detailsActivity.this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(h5detailsActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5) {
            if (data != null) {
                String result2 = data.getStringExtra("result7");
                Log.i("aiyue", "onActivityResult: " + data);
                postnum();
                postdata();
            }
        }
    }
    @Override
    public void onClick(View view) {

    }
    @Override
    public void onDismiss() {

    }
}
