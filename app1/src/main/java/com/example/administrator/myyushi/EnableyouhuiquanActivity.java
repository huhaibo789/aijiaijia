package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

import adapter.Enableyouhuiadapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import util.Myapp;
import utils.Fileutils18;

public class EnableyouhuiquanActivity extends AppCompatActivity {
    @Bind(R.id.return_youhui)
    ImageView return_youhui;
    @Bind(R.id.youhui_ry)
    RelativeLayout youhuiRy;
    @Bind(R.id.listview_youhui)
    PullToRefreshListView listviewYouhui;
    private Enableyouhuiadapter enableyouadapter;
    private ArrayList<String> shuji = new ArrayList<>();  //优惠券图片
    private ArrayList<String> shuji1 = new ArrayList<>(); //优惠券标题
    private ArrayList<String> shuji2 = new ArrayList<>(); //优惠券开始时间
    private ArrayList<String> shuji3 = new ArrayList<>(); //优惠券截止时间
    private ArrayList<String> shuji4 = new ArrayList<>(); //优惠券内容
    private ArrayList<String> shuji5 = new ArrayList<>();//优惠券次数
    private ArrayList<String> shuji6 = new ArrayList<>();//优惠券id
    private RequestQueue queue;
    int currentpage = 1;
    JSONArray jsonarry;
    private Handler handler = new Handler();
    public static volatile String localCookie = null;
    private String url3 = "https://api.aijiaijia.com/api_enabledcoupons?page="+currentpage;
    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enableyouhuiquan);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        getVolley(url3);
        enableLoadMore();
        setlistener();
        setPulltoRefreshStyle();
        StatusBarCompat.setStatusBarColor(EnableyouhuiquanActivity.this, Color.parseColor("#fbd23a"), true);
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
    private void enableLoadMore() {
        listviewYouhui.setMode(PullToRefreshBase.Mode.BOTH);
    }
    private void setadapter1() {
        ImageLoader loader = ((Myapp) EnableyouhuiquanActivity.this.getApplication()).getLoader();
        enableyouadapter = new Enableyouhuiadapter(EnableyouhuiquanActivity.this, loader);
        listviewYouhui.setAdapter(enableyouadapter);
    }
    private void getVolley(final String isUrl) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl = isUrl;
        JsonObjectRequest objectRequest = new JsonObjectRequest(JSONDataUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject1) {
                        //取到的jsonObject数据在这里处理操作
                        Log.i("Exujimeng", "onResponse: " + jsonObject1);
                        JSONObject resposeobject = null;
                        try {
                            resposeobject = jsonObject1.getJSONObject("responseJson");
                            Log.i("Exujimeng1", "onResponse: " + resposeobject);
                            String result = resposeobject.getString("op");
                            if (result!=null&&result.equals("1")) {
                                jsonarry = resposeobject.getJSONArray("list");
                                if (jsonarry!=null&&jsonarry.length() == 0) {
                                    youhuiRy.setVisibility(View.VISIBLE);
                                    listviewYouhui.setVisibility(View.GONE);
                                    dialog.dismiss();
                                } else {
                                    youhuiRy.setVisibility(View.GONE);
                                    listviewYouhui.setVisibility(View.VISIBLE);
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
                                    dialog.dismiss();
                                    enableyouadapter.update(shuji);
                                    enableyouadapter.update1(shuji1);
                                    enableyouadapter.update2(shuji2);
                                    enableyouadapter.update3(shuji3);
                                    enableyouadapter.update4(shuji4);
                                    enableyouadapter.notifyDataSetChanged();
                                }
                            } else if (result!=null&&result.equals("6")) {
                                Toast.makeText(EnableyouhuiquanActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                            }
                            listviewYouhui.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Fileutils18 gg = new Fileutils18();
                                    gg.saveDataToFile(EnableyouhuiquanActivity.this, shuji5.get(position-1));
                                    Intent intent = new Intent();
                                    intent.putExtra("result10", shuji6.get(position-1));
                                    Log.i("ggsmidf", "onItemClick: " + shuji6.get(position-1));
                                    setResult(456, intent);
                                    finish();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAGArray", volleyError.getMessage(), volleyError);
                dialog.dismiss();
            }
        }) {
            //重写getHeaders 默认的key为cookie，value则为localCookie
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };
        requestQueue.add(objectRequest);
    }
    private void setlistener() {
        return_youhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                            Toast.makeText(EnableyouhuiquanActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                            listviewYouhui.onRefreshComplete();
                        }else {
                            currentpage=currentpage-1;
                            if(jsonarry!=null&&jsonarry.length()!=0){
                                getVolley(url3);
                                listviewYouhui.onRefreshComplete();
                            }else {
                                Toast.makeText(EnableyouhuiquanActivity.this, "到底啦", Toast.LENGTH_SHORT).show();
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
                        if(jsonarry!=null&&jsonarry.length()<10){
                            Toast.makeText(EnableyouhuiquanActivity.this, "到底啦", Toast.LENGTH_SHORT).show();
                            listviewYouhui.onRefreshComplete();
                        }else {
                            currentpage=currentpage+1;
                            getVolley(url3);
                            listviewYouhui.onRefreshComplete();
                        }
                    }
                },1000);
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
