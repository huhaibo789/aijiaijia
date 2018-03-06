package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import adapter.youhuiadapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import util.Myapp;
import utils.FileUtils24;
import utils.Fileutils18;

public class YouhuiquanActivity extends AppCompatActivity {
    @Bind(R.id.return_youhui)
    ImageView return_youhui;
    @Bind(R.id.listview_youhui)
    PullToRefreshListView listviewYouhui;
    @Bind(R.id.noyouhui_tv)
    TextView noyouhui_tv;
    @Bind(R.id.daijinquan)
    TextView daijinquan;
    private youhuiadapter youadapter;
    private Handler handler = new Handler();
    private ArrayList<String> shuji = new ArrayList<>();
    private ArrayList<String> shuji1 = new ArrayList<>();
    private ArrayList<String> shuji2 = new ArrayList<>();
    private ArrayList<String> shuji3 = new ArrayList<>();
    private ArrayList<String> shuji4 = new ArrayList<>();
    private ArrayList<String> shuji5 = new ArrayList<>();
    private ArrayList<String> shuji6 = new ArrayList<>();
    private RequestQueue queue;
    private   JSONArray jsonarry;
    public static volatile String localCookie = null;
    int currentpage = 1;
    String ss;
    private String url3 = "https://api.aijiaijia.com/api_coupons_all";
    LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiquan);
        ButterKnife.bind(this);
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        queue = Volley.newRequestQueue(this);
        getVolley();
        enableLoadMore();
        setListener2();
        setPulltoRefreshStyle();
        setlistener();
        StatusBarCompat.setStatusBarColor(YouhuiquanActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void setPulltoRefreshStyle() {
        ILoadingLayout il = listviewYouhui.getLoadingLayoutProxy();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = format1.format(curDate);
        il.setLastUpdatedLabel("最近更新：" + str);
        il.setLoadingDrawable(getResources().getDrawable(R.mipmap.ic_pulltorefresh_arrow));
        //设置下拉状态时的提示文字
        il.setPullLabel("下拉刷新");
        //设置正在刷新过程中的提示文字
        il.setRefreshingLabel("正在刷新");
        //设置松手提示文字
        il.setReleaseLabel("松开刷新");
    }

    private void setListener2() {
        daijinquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://url.aijiaijia.com/coupon-note.html";
                Intent intent = new Intent(YouhuiquanActivity.this, WebviewActivity.class);
                intent.putExtra("name3", url);
                startActivity(intent);
            }
        });
        listviewYouhui.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("dnmdjdjd", "run: "+currentpage);
                        if(currentpage==1){
                            Toast.makeText(YouhuiquanActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                            listviewYouhui.onRefreshComplete();
                        }else {
                            currentpage=currentpage-1;
                            getVolley();
                            if(jsonarry.length()!=0){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setadapter1();
                                        listviewYouhui.onRefreshComplete();
                                    }
                                },1000);
                            }else {
                                Toast.makeText(YouhuiquanActivity.this, "再刷新试试", Toast.LENGTH_SHORT).show();
                                listviewYouhui.onRefreshComplete();
                            }
                        }
                    }
                },1000);



            }

            //上拉
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(jsonarry.length()<10){
                            Toast.makeText(YouhuiquanActivity.this, "到底啦", Toast.LENGTH_SHORT).show();
                            listviewYouhui.onRefreshComplete();
                        }else {
                            currentpage=currentpage+1;
                            getVolley();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setadapter1();
                                    listviewYouhui.onRefreshComplete();
                                }
                            },1000);

                        }


                    }
                },1000);
            }
        });
    }

    private void enableLoadMore() {
        listviewYouhui.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void setadapter1() {
        ImageLoader loader = ((Myapp) YouhuiquanActivity.this.getApplication()).getLoader();
        youadapter = new youhuiadapter(YouhuiquanActivity.this, loader, shuji, shuji1, shuji2, shuji3, shuji4);
        listviewYouhui.setAdapter(youadapter);
    }

    private void postByStringRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl = "https://api.aijiaijia.com/api_login?phone=15150659154&password=a123456";
        //POST方式更加安全
        StringRequest stringrequest = new StringRequest(Request.Method.POST, JSONDataUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub

                        Log.e("woshiwode", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
            }
        }) {
            //重写parseNetworkResponse方法，返回的数据中 Set-Cookie:***************;
            //其中**************为session id
         /*   @Override
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
            }*/
        };
        requestQueue.add(stringrequest);
    /*    final String shuju="1";
        FileUtils6  ff1=new FileUtils6();
        final String douzi1=ff1.readDataFromFile(this);
        Log.i("woqucao", "postByStringRequest: "+douzi1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                final String state= NetUtil2.LoginOfPost(shuju,douzi1);
                //执行在主线程上
                runOnUiThread(new Runnable() {
                    public void run() {
                        //就是在主线程上操作,弹出结果
                        try {
                            JSONObject jsonObject = new JSONObject(state);
                            Log.i("chengxiang", "run: "+state);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                });

            }

        }).start();*/

    }

    private void getVolley() {
        queue = Volley.newRequestQueue(YouhuiquanActivity.this);
        String url = "https://api.aijiaijia.com/api_coupons_all";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfsw", "onResponse: post  success " + response);

                        ss = response.toString().trim();
                        Log.i("kdidkd", "onResponse: "+ss.toString());

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                },500);

                                jsonarry = resposeobject.getJSONArray("list");
                                if(jsonarry.length()==0){
                                    noyouhui_tv.setVisibility(View.VISIBLE);
                                    listviewYouhui.setVisibility(View.GONE);

                                }else {
                                    listviewYouhui.setVisibility(View.VISIBLE);
                                    noyouhui_tv.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String ss6 = object.getString("cp_state");
                                    String ss = object.getString("cp_showpic");
                                    shuji.add(ss);
                                    String ss1 = object.getString("cp_title");
                                    shuji1.add(ss1);
                                    String ss2 = object.getString("cp_btime");
                                    shuji2.add(ss2);
                                    String ss3 = object.getString("cp_etime");
                                    shuji3.add(ss3);
                                    String ss4 = object.getString("trs_instructions");
                                    shuji4.add(ss4);
                                    String ss5 = object.getString("cp_amount");
                                    shuji5.add(ss5);
                                    String ss7 = object.getString("cp_id");
                                    shuji6.add(ss7);

                                }
                                setadapter1();
                            } else if (op.equals("6")) {
                                Toast.makeText(YouhuiquanActivity.this, "未登录", Toast.LENGTH_SHORT).show();

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


    private void setlistener() {
        return_youhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
