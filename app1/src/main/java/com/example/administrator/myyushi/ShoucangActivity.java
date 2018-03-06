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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.shoucangadapter;
import bean.gouwu1;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import util.Myapp;
import utils.DBHelper3;

public class ShoucangActivity extends AppCompatActivity {

    @Bind(R.id.shouhuo_iv2)
    ImageView shouhuoIv2;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    @Bind(R.id.bianji1)
    TextView bianji1;
    @Bind(R.id.shouhuo1)
    RelativeLayout shouhuo1;
    @Bind(R.id.list_shoucang)
    ListView listShoucang;
    LoadingDialog dialog1;
    @Bind(R.id.shoucang_rl)
    RelativeLayout shoucangRl;
    private shoucangadapter cangadapter;
    private TextView nowsee1;
    private RequestQueue queue;
    private DBHelper3 helper3;
    private List<gouwu1> gou1 = new ArrayList<>();
    private ArrayList<String> loginsn = new ArrayList<>();
    private ArrayList<String> shoucang = new ArrayList<>();
    private ArrayList<String> shoucang1 = new ArrayList<>();
    private ArrayList<String> shoucang2 = new ArrayList<>();
    private ArrayList<String> shoucang3 = new ArrayList<>();
    private ArrayList<String> shoucang4 = new ArrayList<>();
    private ArrayList<String> shoucang5 = new ArrayList<>();
    private Handler handle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shoucang);
        ButterKnife.bind(this);
        dialog1 = new LoadingDialog(ShoucangActivity.this);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.show();
        postdata();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setadapter();
                setlistener();

            }
        }, 1000);
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.dismiss();
            }
        }, 1000);
        StatusBarCompat.setStatusBarColor(ShoucangActivity.this, Color.parseColor("#fbd23a"), true);
    }

    public void postdata() {
        shoucang.clear();
        shoucang1.clear();
        shoucang2.clear();
        shoucang3.clear();
        shoucang4.clear();
        loginsn.clear();
        helper3 = new DBHelper3(this);
        gou1 = helper3.queryAll();
        if (gou1.size() != 0) {
            for (int i = 0; i < gou1.size(); i++) {
                helper3.delete(gou1.get(i).get_id());
            }

        }
        gou1.clear();
        queue = Volley.newRequestQueue(this);
        String url = "https://api.aijiaijia.com/api_collection";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("shoucang566", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        JSONObject jsonobject = null;
                        try {
                            jsonobject = new JSONObject(str);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            JSONArray jsonarry = resposeobject.getJSONArray("list");
                            if (jsonarry.length() == 0) {
                                setContentView(R.layout.activity_wushoucang);
                                ImageView shouhuo_iv2 = (ImageView) findViewById(R.id.shouhuo_iv2);
                                TextView go_it = (TextView) findViewById(R.id.go_it);
                                shouhuo_iv2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });
                                go_it.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ShoucangActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }
                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject object = jsonarry.getJSONObject(i);
                                String dizhi = object.getString("product_pic");
                                shoucang.add(dizhi);
                                Log.i("yuy1", "onResponse: " + dizhi);
                                String title = object.getString("product_name");
                                shoucang1.add(title);
                                String producation = object.getString("product_bn");
                                shoucang2.add(producation);
                                String nowprice = object.getString("product_nowprice");
                                shoucang3.add(nowprice);
                                String price = object.getString("product_price");
                                shoucang4.add(price);
                                String zhuangtai = object.getString("product_status");
                                loginsn.add(zhuangtai);
                                String productid = object.getString("pid");
                                shoucang5.add(productid);
                                DBHelper3 helper9 = new DBHelper3(ShoucangActivity.this);
                                helper9.insert(new gouwu1(productid, dizhi, producation, title, nowprice, price));
                                gou1 = helper9.queryAll();
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

    private void setadapter() {
        ImageLoader loader = ((Myapp) ShoucangActivity.this.getApplication()).getLoader();
        cangadapter = new shoucangadapter(ShoucangActivity.this, loader, gou1, shoucang, shoucang1, shoucang2, shoucang3, shoucang4, shoucang5, loginsn);
        listShoucang.setAdapter(cangadapter);

    }


    private void setlistener() {
        listShoucang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("dssd", "onItemClick: " + position);

                String url2 = shoucang5.get(position).toString();
                Intent intent3 = new Intent(ShoucangActivity.this, h5detailsActivity.class);
                intent3.putExtra("uid", url2);
                startActivity(intent3);
            }
        });
        shoucangRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shouhuoIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bianji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ShoucangActivity.this, Shoucang1Activity.class);
                startActivity(intent1);
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
