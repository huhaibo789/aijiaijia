package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.mylistview;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.FileUtils;
import view.newSearchPagerGaoview;

public class newsousuoaActivity extends AppCompatActivity {
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.xq_iv)
    ImageView xqIv;
    @Bind(R.id.xq_iv1)
    ImageView xqIv1;
    @Bind(R.id.fenxiang_iv)
    ImageView fenxiangIv;
    @Bind(R.id.xq_tv)
    TextView xqTv;
    @Bind(R.id.xq_tv1)
    TextView xqTv1;
    @Bind(R.id.yuanjia)
    TextView yuanjia;
    @Bind(R.id.kucun)
    TextView kucun;
    @Bind(R.id.yaoqiu_tv1)
    TextView yaoqiuTv1;
    @Bind(R.id.yaoqiu_tv2)
    TextView yaoqiuTv2;
    @Bind(R.id.yaoqiu_tv3)
    TextView yaoqiuTv3;
    @Bind(R.id.ll_content)
    RelativeLayout llContent;

    @Bind(R.id.miaoxu)
    TextView miaoxu;
    @Bind(R.id.zhiliang_sure)
    LinearLayout zhiliangSure;
    @Bind(R.id.layout)
    PullPushLayout layout;
    @Bind(R.id.radio_shouye)
    RadioButton radioShouye;
    @Bind(R.id.radio_category)
    RadioButton radioCategory;
    @Bind(R.id.radio_feel)
    RadioButton radioFeel;
    @Bind(R.id.radio_mine)
    Button radioMine;
    @Bind(R.id.bottom_radioGroup)
    RadioGroup bottomRadioGroup;
    @Bind(R.id.searchmoregaoview2)
    newSearchPagerGaoview searchmoregaoview2;
    @Bind(R.id.shangpin_tv)
    TextView shangpinTv;
    @Bind(R.id.xiangqing_more)
    TextView xiangqingMore;
    @Bind(R.id.commodity_ly)
    RelativeLayout commodityLy;
    @Bind(R.id.xianc)
    View xianc;
    @Bind(R.id.goods_tv)
    TextView goodsTv;
    @Bind(R.id.goods_tv1)
    TextView goodsTv1;
    @Bind(R.id.goods_tv2)
    TextView goodsTv2;
    @Bind(R.id.goods_tv3)
    TextView goodsTv3;
    @Bind(R.id.goods_tv4)
    TextView goodsTv4;
    @Bind(R.id.goods_tv5)
    TextView goodsTv5;
    @Bind(R.id.newxingqing_finish)
    ImageView newxingqingFinish;
    @Bind(R.id.newkefu_xiangqing)
    ImageView newkefuXiangqing;
    @Bind(R.id.newshop_xiangqing)
    ImageView newshopXiangqing;
    @Bind(R.id.text_chart_num)
    TextView textChartNum;
    @Bind(R.id.newshare_iv1)
    ImageView newshareIv1;
    @Bind(R.id.xinry)
    RelativeLayout xinry;
    @Bind(R.id.tab_design)
    TabLayout tabDesign;
    @Bind(R.id.web_newload)
    LinearLayout webNewload;
    @Bind(R.id.newguige_lv2)
    mylistview newguigeLv2;
    @Bind(R.id.newguige_lv1)
    mylistview newguigeLv1;
    private ArrayList<String> content1 = new ArrayList<>();
    private ArrayList<String> time1 = new ArrayList<>();
    private ArrayList<String> smalltitle = new ArrayList<>();
    private  ArrayList<String> detaidl=new ArrayList<>();
    private ArrayList<String> shouhou=new ArrayList<>();
    private ArrayList<String> attributetype=new ArrayList<>();
    private ArrayList<String> attributeparm=new ArrayList<>();
    private ImageView tupian_new;
    private PullPushLayout mLayout;
    private Drawable bgBackDrawable;
    private Drawable bgShareDrawable;
    private Drawable bgNavBarDrawable;
    private Drawable bglineNavBarDrawable;
    private RequestQueue queue;
    private int alphaMax = 180;
    private ArrayList<String> tu = new ArrayList<>();
    private Handler handle = new Handler();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newsousuoa);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        String result = intent.getStringExtra("uid");
        url = "https://api.aijiaijia.com/api_products_info?" + "pid=" + result;
        if (result != null) {
            FileUtils ful = new FileUtils();
            ful.saveDataToFile(this, url);
            Log.i("fghrs", "onCreate: " + url);
        }

        postshuju();
        postdata();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                initView();
                initdata();
            }
        }, 1000);

    }

    private void postdata() {
        content1.clear();
        time1.clear();
        smalltitle.clear();
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
                //String id2 = String.valueOf(xqbean.get(0).getId());
             /*   if (id2 != null) {
                    map.put("page", "1");
                    map.put("readpid", id2);
                }*/

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

    private void initdata() {
        ImageLoader loader = ((Myapp) newsousuoaActivity.this.getApplication()).getLoader();
        Log.i("guyd", "initdata: " + tu.toString());
        searchmoregaoview2.updatePager2(tu, loader);
        if (content1.size() == 0) {
            goodsTv.setVisibility(View.GONE);
            goodsTv1.setVisibility(View.GONE);
            goodsTv2.setVisibility(View.GONE);
            goodsTv3.setVisibility(View.GONE);
            goodsTv4.setVisibility(View.GONE);
            goodsTv5.setVisibility(View.GONE);
        } else if (content1.size() == 1) {
            goodsTv.setText(time1.get(0));
            goodsTv1.setText(smalltitle.get(0));
            goodsTv2.setText(content1.get(0));
            goodsTv3.setVisibility(View.GONE);
            goodsTv4.setVisibility(View.GONE);
            goodsTv5.setVisibility(View.GONE);
        } else {
            goodsTv.setText(time1.get(0));
            goodsTv1.setText(smalltitle.get(0));
            goodsTv2.setText(content1.get(0));
            goodsTv3.setText(time1.get(1));
            goodsTv4.setText(smalltitle.get(1));
            goodsTv5.setText(content1.get(1));

        }
        String[] tabArr = {"商品详情", "规格参数", "售后服务"};
        tabDesign.setSelectedTabIndicatorColor(Color.parseColor("#149885"));
        tabDesign.setTabTextColors(Color.BLACK, Color.BLACK);
        tabDesign.setTabMode(tabDesign.MODE_FIXED);
        for (int i = 0; i < tabArr.length; i++) {
            TabLayout.Tab tab = tabDesign.newTab()
                    .setText(tabArr[i])
                    .setTag(i);
            tabDesign.addTab(tab);

        }
        View   v_city2 = LayoutInflater.from(this).inflate(R.layout.acticity_web, null);
        View  v_city3=LayoutInflater.from(this).inflate(R.layout.activity_web1,null);
        final WebView webview2= (WebView) v_city3.findViewById(R.id.webview2);
        webNewload.addView(v_city3);
        webNewload.addView(v_city2);
        final WebView  webview3= (WebView) v_city2.findViewById(R.id.webview3);
        webview3.setVisibility(View.VISIBLE);
        webview2.setVisibility(View.GONE);
        newguigeLv1.setVisibility(View.GONE);
        newguigeLv2.setVisibility(View.GONE);
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings =webview3.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webview3.loadUrl(detaidl.get(0).toString());
        //设置Web视图
        webview3.setWebViewClient(new webViewClient ());
        //Web视图
        tabDesign.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getText().equals("商品详情")){
                    webview3.setVisibility(View.VISIBLE);
                    webview2.setVisibility(View.GONE);
                  newguigeLv1.setVisibility(View.GONE);
                   newguigeLv2.setVisibility(View.GONE);
                    @SuppressLint("SetJavaScriptEnabled")
                    WebSettings webSettings =webview3.getSettings();
                    //设置WebView属性，能够执行Javascript脚本
                    webSettings.setJavaScriptEnabled(true);
                    //设置可以访问文件
                    webSettings.setAllowFileAccess(true);
                    //设置支持缩放
                    webSettings.setBuiltInZoomControls(true);
                    //加载需要显示的网页
                    webview3.loadUrl(detaidl.get(0).toString());
                    //设置Web视图
                    webview3.setWebViewClient(new webViewClient ());
                    //Web视图
                }else if(tab.getText().equals("售后服务")){
                    webview2.setVisibility(View.VISIBLE);
                    webview3.setVisibility(View.GONE);
                    newguigeLv1.setVisibility(View.GONE);
                   newguigeLv2.setVisibility(View.GONE);
                    @SuppressLint("SetJavaScriptEnabled")
                    WebSettings webSettings =webview2.getSettings();
                    //设置WebView属性，能够执行Javascript脚本
                    webSettings.setJavaScriptEnabled(true);
                    //设置可以访问文件
                    webSettings.setAllowFileAccess(true);
                    //设置支持缩放
                    webSettings.setBuiltInZoomControls(true);
                    //加载需要显示的网页
                    webview2.loadUrl(shouhou.get(0).toString());
                    //设置Web视图
                    webview2.setWebViewClient(new webViewClient ());
                }else {
                  newguigeLv1.setVisibility(View.VISIBLE);
                   newguigeLv2.setVisibility(View.VISIBLE);
                    webview2.setVisibility(View.GONE);
                    webview3.setVisibility(View.GONE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(newsousuoaActivity.this, android.R.layout.simple_spinner_dropdown_item, attributetype);
                  newguigeLv2.setAdapter(adapter);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(newsousuoaActivity.this, android.R.layout.simple_spinner_dropdown_item, attributeparm);
               newguigeLv1.setAdapter(adapter1);


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

    private void postshuju() {
        attributeparm.clear();
        attributetype.clear();
        shouhou.clear();
        detaidl.clear();
        tu.clear();
        queue = Volley.newRequestQueue(this);
        String urls = url;
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu56fsd", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            JSONArray jsonarry = resposeobject.getJSONArray("list_product_urlpics");
                            JSONArray jsond=resposeobject.getJSONArray("list_product_detail");
                            JSONArray newjson=resposeobject.getJSONArray("list_product_attribute_type");
                            JSONArray newjson1=resposeobject.getJSONArray("list_product_attribute_parm");
                            for (int j = 0; j <newjson.length() ; j++) {
                                String wi= (String) newjson.get(j);
                                attributetype.add(wi);
                            }
                            for (int k = 0; k <newjson1.length() ; k++) {
                                String wi= (String) newjson1.get(k);
                              attributeparm.add(wi);
                            }
                            for (int i = 0; i <jsond.length() ; i++) {

                                String wi= (String) jsond.get(i);
                                detaidl.add(wi);
                            }
                            JSONArray jsond1=resposeobject.getJSONArray("list_product_aftersaleservice");
                            for (int i = 0; i <jsond1.length() ; i++) {
                                String sale= (String) jsond1.get(i);
                                shouhou.add(sale);
                            }
                            JSONArray jsd = resposeobject.getJSONArray("list_product");
                            for (int i = 0; i < jsd.length(); i++) {
                                JSONObject json = jsd.getJSONObject(i);
                                String title = json.getString("product_name");
                                String nowprice = json.getString("product_nowprice");
                                String agoprice = json.getString("product_price");
                                String uid = json.getString("id");
                                String stock = json.getString("product_stock");
                                kucun.setText("库存:" + stock + "件");
                                xqTv.setText(title);
                                xqTv1.setText("￥" + nowprice);
                                yuanjia.setText("￥" + agoprice);
                                if (nowprice.equals("0") || nowprice.equals("null")) {
                                    xqTv1.setText("￥" + agoprice);
                                    yuanjia.setVisibility(View.INVISIBLE);
                                } else if (yuanjia.equals("0") || yuanjia.equals("null")) {
                                    xqTv1.setText("￥" + nowprice);
                                    yuanjia.setVisibility(View.INVISIBLE);
                                } else if (nowprice.equals(agoprice)) {
                                    xqTv1.setText("￥" + nowprice);
                                    yuanjia.setVisibility(View.INVISIBLE);

                                } else {
                                    xqTv1.setText("￥" + nowprice);
                                    yuanjia.setText("￥" + agoprice);
                                    yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                }


                            }
                            for (int i = 0; i < jsonarry.length(); i++) {
                                String picture = (String) jsonarry.get(i);
                                Log.i("fere", "onResponse: " + picture);
                                tu.add(picture);
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

        };
        queue.add(post);
    }

    private void initView() {
        mLayout = (PullPushLayout) this.findViewById(R.id.layout);

        mLayout.setOnTouchEventMoveListenre(new PullPushLayout.OnTouchEventMoveListenre() {

            @Override
            public void onSlideUp(int mOriginalHeaderHeight, int mHeaderHeight) {
                Log.i("gidjd", "onSlideUp: " + "hahha");
                Log.i("gyfe", "onSlideUp: " + mOriginalHeaderHeight);
                Log.i("ufnie", "onSlideUp: " + mHeaderHeight);
                if (mHeaderHeight < 12) {
                    xinry.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlideDwon(int mOriginalHeaderHeight, int mHeaderHeight) {
                Log.i("hgdd", "onSlideDwon: " + mHeaderHeight);
                if (mHeaderHeight > 4) {
                    xinry.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(int alpha) {
                Log.i("djhdb", "onSlide: " + alpha);
              /*  int alphaReverse = alphaMax - alpha;
                if (alphaReverse < 0) {
                    alphaReverse = 0;
                }
                bgBackDrawable.setAlpha(alphaReverse);
                bgShareDrawable.setAlpha(alphaReverse);
                bgNavBarDrawable.setAlpha(alpha);
                bglineNavBarDrawable.setAlpha(alpha);*/

            }
        });
//        navBar = this.findViewById(R.id.nav_bar);
//        lineNavBar = this.findViewById(R.id.line_nav_bar);
//        btnBack = this.findViewById(R.id.iv_back);
//        btnShare = this.findViewById(R.id.iv_share);
      /*  bgBackDrawable = btnBack.getBackground();
        bgBackDrawable.setAlpha(alphaMax);
        bgShareDrawable = btnShare.getBackground();
        bgShareDrawable.setAlpha(alphaMax);
        bgNavBarDrawable = navBar.getBackground();
        bglineNavBarDrawable = lineNavBar.getBackground();
        bgNavBarDrawable.setAlpha(0);
        bglineNavBarDrawable.setAlpha(0);*/

    }
    class webViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}


