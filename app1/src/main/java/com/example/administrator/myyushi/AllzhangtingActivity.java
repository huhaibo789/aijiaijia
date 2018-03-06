package com.example.administrator.myyushi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Urlutil.Utils;
import adapter.zhantingadapter;
import bean.zhantingbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import util.Myapp;

public class AllzhangtingActivity extends AppCompatActivity {
    @Bind(R.id.all_iv)
    ImageView allIv;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.zt_iv)
    ImageView zt_iv;
    @Bind(R.id.cityname_tv)
    TextView citynameTv;
    private zhantingadapter ztadapter;
    private String message;
    private RequestQueue queue;
    private View v_city;
    private ArrayList<zhantingbean.ResponseJsonBean.ListBean> data = new ArrayList<>();
    private ArrayList<String> mendian_address=new ArrayList<>();
    private ArrayList<String> mendian_image=new ArrayList<>();
    private ArrayList<String> mendian_name=new ArrayList<>();
    private ArrayList<String> mendian_phone=new ArrayList<>();
    private Context context;
    private Handler handle=new Handler();
    PopupWindow pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allzhangting);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        citynameTv.setText(message);
        postdata();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setadapter();
                setlistener();
                addview();
            }
        },500);

    }
    private void addview() {
        v_city = LayoutInflater.from(AllzhangtingActivity.this).inflate(R.layout.activity_city1, null);
        final Button button1= (Button) v_city.findViewById(R.id.button_nanjin);
        final Button button2= (Button) v_city.findViewById(R.id.button_wuxi);
        final Button button3= (Button) v_city.findViewById(R.id.button_wuhan);
        final Button button4= (Button) v_city.findViewById(R.id.button_qingdao);
        final Button button5= (Button) v_city.findViewById(R.id.button_jinan);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citynameTv.setText("南京");
                button1.setTextColor(Color.parseColor("#149985"));
                button1.setBackgroundResource(R.drawable.green_border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                 message="南京市";
                 postdata();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ztadapter.notifyDataSetChanged();
                    }
                },1000);
                 pop.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citynameTv.setText("无锡");
                message="无锡市";
                button2.setTextColor(Color.parseColor("#149985"));
                button2.setBackgroundResource(R.drawable.green_border);
                button1.setTextColor(Color.parseColor("#666666"));
                button1.setBackgroundResource(R.drawable.border);
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                postdata();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ztadapter.notifyDataSetChanged();
                    }
                },1000);
                pop.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citynameTv.setText("武汉");
                message="武汉市";
                button3.setTextColor(Color.parseColor("#149985"));
                button1.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.green_border);
                postdata();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ztadapter.notifyDataSetChanged();
                    }
                },1000);
                Log.i("udndeerf1", "getParams: "+message);
                pop.dismiss();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citynameTv.setText("青岛");
                message="青岛市";
                button4.setTextColor(Color.parseColor("#149985"));
                button1.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button5.setBackgroundResource(R.drawable.border);
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.green_border);
                postdata();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ztadapter.notifyDataSetChanged();
                    }
                },1000);
                pop.dismiss();
            }
        });
     button5.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             citynameTv.setText("济南");
             message="济南市";
             button1.setTextColor(Color.parseColor("#666666"));
             button2.setTextColor(Color.parseColor("#666666"));
             button3.setTextColor(Color.parseColor("#666666"));
             button5.setTextColor(Color.parseColor("#149985"));
             button4.setTextColor(Color.parseColor("#666666"));
             button1.setBackgroundResource(R.drawable.border);
             button2.setBackgroundResource(R.drawable.border);
             button3.setBackgroundResource(R.drawable.border);
             button4.setBackgroundResource(R.drawable.border);
             button5.setBackgroundResource(R.drawable.green_border);
             postdata();
             handle.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     ztadapter.notifyDataSetChanged();
                 }
             },1000);
             pop.dismiss();
         }
     });
    }

    private void postdata() {
        mendian_name.clear();
        mendian_image.clear();
        mendian_address.clear();
        mendian_phone.clear();
        queue = Volley.newRequestQueue(this);
        String urls="https://api.aijiaijia.com/api_displayhall";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str=response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("0")) {
                                Toast.makeText(AllzhangtingActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry=resposeobject.getJSONArray("list");
                                for (int i = 0; i <jsonarry.length() ; i++) {
                                     JSONObject  object=jsonarry.getJSONObject(i);
                                    String address=object.getString("mendian_address");
                                    mendian_address.add(address);
                                    String name=object.getString("mendian_name");
                                    mendian_name.add(name);
                                    String phone=object.getString("mendian_phone");
                                    mendian_phone.add(phone);
                                    String image=object.getString("mendian_image");
                                    mendian_image.add(image);
                                }

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
                map.put("page","1");
                try {
                    Log.i("udndeerf", "getParams: "+message);
                    String str=new String(message.getBytes("utf-8"),"ISO-8859-1");
                    map.put("cityname",str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }

        };
        queue.add(post);
    }

    private void setlistener() {
        zt_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        allIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(v, Gravity.NO_GRAVITY, 10, 140);
            }
        });
        citynameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(v, Gravity.NO_GRAVITY, 10, 180);
            }
        });
    }

    private void setadapter() {
        ImageLoader loader = ((Myapp) AllzhangtingActivity.this.getApplication()).getLoader();
        ztadapter = new zhantingadapter(AllzhangtingActivity.this, loader,mendian_name,mendian_phone,mendian_address,mendian_image);
        listview.setAdapter(ztadapter);
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
