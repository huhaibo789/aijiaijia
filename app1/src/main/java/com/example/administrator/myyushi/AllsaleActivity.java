package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
import java.util.Map;

import adapter.alldingdan2;
import adapter.shopguide;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;

public class AllsaleActivity extends AppCompatActivity {
    @Bind(R.id.custom_service)
    ImageView customService;
    @Bind(R.id.layout10)
    TabLayout layout10;
    @Bind(R.id.list_shangpin)
    PullToRefreshListView listShangpin;
    @Bind(R.id.finish_xiaoshou)
    ImageView finishXiaoshou;
    @Bind(R.id.lyy)
    LinearLayout lyy;
    @Bind(R.id.rly)
    RelativeLayout rly;
    @Bind(R.id.show_wuping)
    RelativeLayout showWuping;
    @Bind(R.id.goto_shop)
    TextView gotoShop;
    int currentpage = 1;
    private JSONArray jsonarry;
    private alldingdan2 alladapter;
    private shopguide guideadapter;
    private Handler handle = new Handler();
    private String ss;
    private RequestQueue queue;
    private JSONArray jsonarry10;
    private ArrayList<String> array = new ArrayList<>();
    private ArrayList<String> array1 = new ArrayList<>();
    private ArrayList<String> arry2 = new ArrayList<>();
    private ArrayList<String> array3 = new ArrayList<>();
    private ArrayList<String> array5 = new ArrayList<>();
    private ArrayList<String> array6 = new ArrayList<>();
    private ArrayList<String> array7 = new ArrayList<>();
    private ArrayList<String> array8 = new ArrayList<>();
    private ArrayList<String> array9 = new ArrayList<>();
    private ArrayList<String> array10 = new ArrayList<>();
    private ArrayList<String> array4 = new ArrayList<>();
    private ArrayList<String> shopguide = new ArrayList<>();
    private ArrayList<String> ordershop = new ArrayList<>();
    private ArrayList<String> ordertime = new ArrayList<>();
    private ArrayList<String> ordersatates = new ArrayList<>();
    private ArrayList<String> orderzuchecked = new ArrayList<>();
    private ArrayList<String> orderid = new ArrayList<>();
    private ArrayList<String> orderstatus1 = new ArrayList<>();
    int ad = 1;
    private String ssw;
    private ArrayList<String> xiaoshouorderid = new ArrayList<>();
    private ArrayList<String> payway = new ArrayList<>();
    private String result;
    private View v_city;
    private boolean falg;
    private  boolean firstfalg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsale);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        result = intent.getStringExtra("status");
        init();
        if (result != null && result.equals("2")) {
            firstfalg=true;
            postwaitpay();
            enableLoadMore();
            paysetlistener();
            setPullToRefreshStyle();
            setlistener();
        } else if (result != null && result.equals("10")) {
            firstfalg=true;
            postwaitpay();
            enableLoadMore();
            paysetlistener();
            setPullToRefreshStyle();
            setlistener();
        } else if (result != null && result.equals("5")) {
            firstfalg=true;
            postwaitpay();
            enableLoadMore();
            paysetlistener();
            setPullToRefreshStyle();
            setlistener();
        }else if(result!=null&&result.equals("3")){
            firstfalg=true;
            postwaitpay();
            enableLoadMore();
            paysetlistener();
            setPullToRefreshStyle();
            setlistener();
        } else {
            posd();
            enableLoadMore();
            setlistener1();
            setPullToRefreshStyle();
            setlistener();
        }
        StatusBarCompat.setStatusBarColor(AllsaleActivity.this, Color.parseColor("#fbd23a"), true);
    }
    private void posd() {
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ssw = response.toString().trim();
                        Log.i("heious", "onResponse: "+ssw);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ssw);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("sellorders");
                                //防止刷新过程中网络不佳出现无订单页面
                                if(firstfalg=true){
                                    if (jsonarry != null && jsonarry.length() == 0) {
                                        showWuping.setVisibility(View.VISIBLE);
                                        firstfalg=false;
                                    } else {
                                        showWuping.setVisibility(View.GONE);
                                        firstfalg=false;
                                    }
                                }
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    array.add(order_sn);
                                    String order_statustext = object.getString("order_statustext");
                                    array1.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    arry2.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    array3.add(order_shipfee);
                                    String order_status = object.getString("order_status");
                                    array4.add(order_status);
                                    String xiaoshou = object.getString("order_id");
                                    xiaoshouorderid.add(xiaoshou);
                                    String zhifu = object.getString("order_payway");
                                    payway.add(zhifu);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        array5.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        array6.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        array7.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        array8.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        array9.add(product_price);
                                        String num = Object1.getString("num");
                                        array10.add(num);
                                    }
                                }
                                if(ssw!=null){
                                    setadapter();
                                }

                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", String.valueOf(currentpage));
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
    private void paysetlistener() {
        listShangpin.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage=1;
                postwaitpayDown();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setadapter();
                        listShangpin.onRefreshComplete();
                    }
                },1000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage = currentpage + 1;
                postwaitpayUp();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alladapter.setDatainfo(ssw);
                        alladapter.notifyDataSetChanged();
                        if(falg==true){
                            Toast.makeText(AllsaleActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                        }
                        listShangpin.onRefreshComplete();
                    }
                },500);
            }
        });
        listShangpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllsaleActivity.this, detailsActivity.class);
                intent.putExtra("tt", "1");
                intent.putExtra("orderyu", xiaoshouorderid.get(position - 1));
                intent.putExtra("orederyu1", array.get(position - 1));
                startActivity(intent);
            }
        });
    }
    private void postwaitpayDown() {
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        array.clear();
                        array1.clear();
                        arry2.clear();
                        array3.clear();
                        array4.clear();
                        array5.clear();
                        array6.clear();
                        array7.clear();
                        array8.clear();
                        array9.clear();
                        array10.clear();
                        xiaoshouorderid.clear();
                        payway.clear();
                        ssw = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ssw);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("sellorders");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    array.add(order_sn);
                                    String order_statustext = object.getString("order_statustext");
                                    array1.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    arry2.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    array3.add(order_shipfee);
                                    String order_status = object.getString("order_status");
                                    array4.add(order_status);
                                    String xiaoshou = object.getString("order_id");
                                    xiaoshouorderid.add(xiaoshou);
                                    String zhifu = object.getString("order_payway");
                                    payway.add(zhifu);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        array5.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        array6.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        array7.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        array8.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        array9.add(product_price);
                                        String num = Object1.getString("num");
                                        array10.add(num);
                                    }
                                }
                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", String.valueOf(currentpage));
                if (result.equals("2")) {
                    map.put("s", "2");
                } else if (result.equals("10")) {
                    map.put("s", "10");
                } else if (result.equals("5")) {
                    map.put("s", "5");
                }else if(result.equals("3")){
                    map.put("s","3");
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

    private void postwaitpayUp() {
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      /*  array.clear();
                        array1.clear();
                        arry2.clear();
                        array3.clear();
                        array4.clear();
                        array5.clear();
                        array6.clear();
                        array7.clear();
                        array8.clear();
                        array9.clear();
                        array10.clear();
                        xiaoshouorderid.clear();
                        payway.clear();*/
                        ssw = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ssw);
                            Log.i("shangdalu", "onResponse: "+ssw);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("sellorders");
                                if(jsonarry.length()==0){
                                    falg=true;
                                }else {
                                    falg=false;
                                }
                                Log.i("huyed", "onResponse: "+jsonarry.length());
                                Log.i("huyed1", "onResponse: "+jsonarry.toString());
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    array.add(order_sn);
                                    String order_statustext = object.getString("order_statustext");
                                    array1.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    arry2.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    array3.add(order_shipfee);
                                    String order_status = object.getString("order_status");
                                    array4.add(order_status);
                                    String xiaoshou = object.getString("order_id");
                                    xiaoshouorderid.add(xiaoshou);
                                    String zhifu = object.getString("order_payway");
                                    payway.add(zhifu);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        array5.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        array6.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        array7.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        array8.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        array9.add(product_price);
                                        String num = Object1.getString("num");
                                        array10.add(num);

                                    }
                                }
                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", String.valueOf(currentpage));
                if (result.equals("2")) {
                    map.put("s", "2");
                } else if (result.equals("10")) {
                    map.put("s", "10");
                } else if (result.equals("5")) {
                    map.put("s", "5");
                }else if(result.equals("3")){
                    map.put("s","3");
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

    private void setPullToRefreshStyle() {
        //获取用于设置下拉刷新中显示内容的设置对象
        ILoadingLayout il = listShangpin.getLoadingLayoutProxy();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = format1.format(curDate);
        //设置最底部的加载时间TextView上显示的文字
        il.setLastUpdatedLabel("最近更新：" + str);
        //设置右侧显示的提示图片
        il.setLoadingDrawable(getResources().getDrawable(R.mipmap.ic_pulltorefresh_arrow));
        //设置下拉状态时的提示文字
        il.setPullLabel("下拉刷新");
        //设置正在刷新过程中的提示文字
        il.setRefreshingLabel("正在刷新");
        //设置松手提示文字
        il.setReleaseLabel("松开刷新");

    }

    private void enableLoadMore() {
       // listShangpin.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listShangpin.setMode(PullToRefreshBase.Mode.BOTH);
    }
    private void postwaitpay() {
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ssw = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ssw);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("sellorders");
                                //防止刷新过程中网络不佳出现无订单页面
                                if(firstfalg=true){
                                    if (jsonarry != null && jsonarry.length() == 0) {
                                        showWuping.setVisibility(View.VISIBLE);
                                        firstfalg=false;
                                    } else {
                                        showWuping.setVisibility(View.GONE);
                                        firstfalg=false;
                                    }
                                }
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    array.add(order_sn);
                                    String order_statustext = object.getString("order_statustext");
                                    array1.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    arry2.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    array3.add(order_shipfee);
                                    String order_status = object.getString("order_status");
                                    array4.add(order_status);
                                    String xiaoshou = object.getString("order_id");
                                    xiaoshouorderid.add(xiaoshou);
                                    String zhifu = object.getString("order_payway");
                                    payway.add(zhifu);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        array5.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        array6.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        array7.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        array8.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        array9.add(product_price);
                                        String num = Object1.getString("num");
                                        array10.add(num);
                                    }
                                }
                             if(ssw!=null){
                                 setadapter();
                             }



                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", String.valueOf(currentpage));
                if (result.equals("2")) {
                    map.put("s", "2");
                } else if (result.equals("10")) {
                    map.put("s", "10");
                } else if (result.equals("5")) {
                    map.put("s", "5");
                }else if(result.equals("3")){
                    map.put("s","3");
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

    private void setlistener1() {
        listShangpin.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage=1;
                postDown();
                  handle.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          setadapter();
                          listShangpin.onRefreshComplete();
                      }
                  },1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage = currentpage + 1;
                posd1();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alladapter.setDatainfo(ssw);
                        Log.i("ganghao", "onRefresh: "+ssw);
                        alladapter.notifyDataSetChanged();
                        if(falg==true){
                            Toast.makeText(AllsaleActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                        }
                        listShangpin.onRefreshComplete();
                    }
                },500);
            }
        });
        listShangpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllsaleActivity.this, detailsActivity.class);
                intent.putExtra("tt", "1");
                intent.putExtra("orderyu", xiaoshouorderid.get(position-1));
                intent.putExtra("orederyu1", array.get(position-1));
                startActivity(intent);
            }
        });

    }

    private void postDown() {
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        array.clear();
                        array1.clear();
                        arry2.clear();
                        array3.clear();
                        array4.clear();
                        array5.clear();
                        array6.clear();
                        array7.clear();
                        array8.clear();
                        array9.clear();
                        array10.clear();
                        xiaoshouorderid.clear();
                        payway.clear();
                        ssw = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ssw);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("sellorders");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    array.add(order_sn);
                                    String order_statustext = object.getString("order_statustext");
                                    array1.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    arry2.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    array3.add(order_shipfee);
                                    String order_status = object.getString("order_status");
                                    array4.add(order_status);
                                    String xiaoshou = object.getString("order_id");
                                    xiaoshouorderid.add(xiaoshou);
                                    String zhifu = object.getString("order_payway");
                                    payway.add(zhifu);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        array5.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        array6.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        array7.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        array8.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        array9.add(product_price);
                                        String num = Object1.getString("num");
                                        array10.add(num);
                                    }
                                }
                                Log.i("mdk", "onResponse: " + array10);
                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", String.valueOf(currentpage));
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

    private void posd1() {
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      /*  array.clear();
                        array1.clear();
                        arry2.clear();
                        array3.clear();
                        array4.clear();
                        array5.clear();
                        array6.clear();
                        array7.clear();
                        array8.clear();
                        array9.clear();
                        array10.clear();
                        xiaoshouorderid.clear();
                        payway.clear();*/
                        ssw = response.toString().trim();
                        Log.i("xiaohua1", "onResponse: "+ssw);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ssw);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("sellorders");
                                if(jsonarry.length()==0){
                                    falg=true;
                                }else {
                                    falg=false;
                                }
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    array.add(order_sn);
                                    String order_statustext = object.getString("order_statustext");
                                    array1.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    arry2.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    array3.add(order_shipfee);
                                    String order_status = object.getString("order_status");
                                    array4.add(order_status);
                                    String xiaoshou = object.getString("order_id");
                                    xiaoshouorderid.add(xiaoshou);
                                    String zhifu = object.getString("order_payway");
                                    payway.add(zhifu);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        array5.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        array6.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        array7.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        array8.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        array9.add(product_price);
                                        String num = Object1.getString("num");
                                        array10.add(num);
                                    }
                                }
                                Log.i("mdk", "onResponse: " + array10);
                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", String.valueOf(currentpage));
                Log.i("xiaohua", "getParams: "+currentpage);
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

    private void setadapter() {
        ImageLoader loader = ((Myapp) AllsaleActivity.this.getApplication()).getLoader();
        alladapter = new alldingdan2(AllsaleActivity.this, loader, ssw, array, array1, arry2, array3, array4, xiaoshouorderid, payway);
        listShangpin.setAdapter(alladapter);
    }

    private void init() {
        final String[] tabArr = {"商品订单"};
       layout10.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        layout10.setTabTextColors(Color.BLACK, Color.parseColor("#222222"));
        layout10.setTabMode(layout10.MODE_FIXED);
        for (int i = 0; i < tabArr.length; i++) {
            TabLayout.Tab tab = layout10.newTab()
                    .setText(tabArr[i])
                    .setTag(i);
            layout10.addTab(tab);
        }
    }

    private void setlistener() {
        gotoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllsaleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        finishXiaoshou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent();
                setResult(14, intent5);
                finish();
            }
        });
        customService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dd = "https://api.aijiaijia.com/api_sellorder_list?page=1";
                String ww = "全部订单";
                String title = "在线客服";
                // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                ConsultSource source = new ConsultSource(dd, ww, "custom information string");
                // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                Unicorn.openServiceActivity(AllsaleActivity.this, // 上下文
                        title, // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
            }
        });
        layout10.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("商品订单")) {
                    //
                    Log.i("ioekee", "onTabSelected: " + result);
                    if (result != null && result.equals("2")) {
                        postwaitpay();
                        handle.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonarry != null && jsonarry.length() == 0) {
                                    showWuping.setVisibility(View.VISIBLE);
                                } else {
                                    showWuping.setVisibility(View.GONE);
                                }
                                setadapter();
                                setlistener1();
                            }
                        }, 1000);
                    } else if (result != null && result.equals("10")) {
                        postwaitpay();
                        handle.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonarry != null && jsonarry.length() == 0) {
                                    showWuping.setVisibility(View.VISIBLE);
                                } else {
                                    showWuping.setVisibility(View.GONE);
                                }
                                setadapter();
                                setlistener1();
                            }
                        }, 1000);
                    } else if (result != null && result.equals("5")) {
                        postwaitpay();
                        handle.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonarry != null && jsonarry.length() == 0) {
                                    showWuping.setVisibility(View.VISIBLE);
                                } else {
                                    showWuping.setVisibility(View.GONE);
                                }
                                setadapter();
                                setlistener1();
                            }
                        }, 1000);
                    } else {
                        postshuju();
                        handle.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setadapter();
                                enableLoadMore();
                                setlistener1();
                                setPullToRefreshStyle();
                            }
                        }, 1000);
                    }

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

    private void setlistener2() {
        listShangpin.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentpage == 1) {
                            Log.i("hahas", "run: " + currentpage);
                            Toast.makeText(AllsaleActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                            postshuju1();
                            handle.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setguideadapter();
                                }
                            }, 1000);
                            listShangpin.onRefreshComplete();
                        } else {
                            Log.i("gde", "run: " + currentpage);
                            currentpage = currentpage - 1;
                            postshuju1();
                            if (ss != null) {
                                handle.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setguideadapter();
                                        listShangpin.onRefreshComplete();
                                    }
                                }, 1000);
                            } else {
                                return;
                            }


                        }
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (jsonarry10.length() < 10) {
                            Toast.makeText(AllsaleActivity.this, "到底啦", Toast.LENGTH_SHORT).show();
                            listShangpin.onRefreshComplete();
                        } else {
                            currentpage = currentpage + 1;
                            postshuju1();
                            handle.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setguideadapter();
                                    listShangpin.onRefreshComplete();
                                }
                            }, 1000);

                        }

                    }
                }, 1000);
            }
        });
        listShangpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllsaleActivity.this, detailsActivity.class);
                intent.putExtra("tt", "2");
                intent.putExtra("orderyu", orderid.get(position - 1));
                intent.putExtra("orederyu1", shopguide.get(position - 1));
                startActivity(intent);
            }
        });
    }

    private void setguideadapter() {

        ImageLoader loader = ((Myapp) AllsaleActivity.this.getApplication()).getLoader();
        guideadapter = new shopguide(AllsaleActivity.this, loader, ss, shopguide, ordershop, ordertime, orderzuchecked, ordersatates, orderid, orderstatus1);
        listShangpin.setAdapter(guideadapter);


    }

    private void postshuju1() {
        shopguide.clear();
        ordershop.clear();
        ordertime.clear();
        ordersatates.clear();
        orderzuchecked.clear();
        orderid.clear();
        orderstatus1.clear();
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_appointorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shopguide.clear();
                        Log.i("gouwudasfs", "onResponse: post  success " + response);
                        ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry10 = resposeobject.getJSONArray("sellorders");
                                for (int i = 0; i < jsonarry10.length(); i++) {
                                    JSONObject object = jsonarry10.getJSONObject(i);
                                    shopguide.add(object.getString("order_sn"));
                                    ordershop.add(object.getString("order_appointshop"));
                                    ordertime.add(object.getString("order_appointtime"));
                                    ordersatates.add(object.getString("order_statustext"));
                                    orderzuchecked.add(object.getString("order_zuchecked"));
                                    orderid.add(object.getString("order_id"));
                                    orderstatus1.add(object.getString("order_status"));
                                }
                                Log.i("odmdfkd", "onResponse: " + orderstatus1);
                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                if (result == null) {
                    map.put("page", String.valueOf(currentpage));
                    Log.i("huse", "getParams: " + currentpage);
                } else {
                    if (result.equals("2")) {
                        map.put("page", String.valueOf(currentpage));
                        map.put("s", "2");
                    } else if (result.equals("10")) {
                        map.put("page", String.valueOf(currentpage));
                        map.put("s", "10");
                    } else if (result.equals("5")) {
                        map.put("page", String.valueOf(currentpage));
                        map.put("s", "5");
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

    private void postshuju() {
        queue = Volley.newRequestQueue(AllsaleActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        array.clear();
                        array1.clear();
                        arry2.clear();
                        array3.clear();
                        array4.clear();
                        array5.clear();
                        array6.clear();
                        array7.clear();
                        array8.clear();
                        array9.clear();
                        array10.clear();
                        xiaoshouorderid.clear();
                        payway.clear();
                        ssw = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ssw);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("sellorders");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    array.add(order_sn);
                                    String order_statustext = object.getString("order_statustext");
                                    array1.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    arry2.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    array3.add(order_shipfee);
                                    String order_status = object.getString("order_status");
                                    array4.add(order_status);
                                    String xiaoshou = object.getString("order_id");
                                    xiaoshouorderid.add(xiaoshou);
                                    String zhifu = object.getString("order_payway");
                                    payway.add(zhifu);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        array5.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        array6.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        array7.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        array8.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        array9.add(product_price);
                                        String num = Object1.getString("num");
                                        array10.add(num);
                                    }
                                }
                                Log.i("mdk", "onResponse: " + array10);
                            } else if (op.equals("6")) {
                                Toast.makeText(AllsaleActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", String.valueOf(1));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==9){
            setadapter();
        }
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
