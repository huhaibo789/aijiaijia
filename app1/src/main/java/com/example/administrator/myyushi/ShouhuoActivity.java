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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.shouhuoadapter;
import bean.Food;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import utils.DBHelper1;

public class ShouhuoActivity extends AppCompatActivity {

    @Bind(R.id.shouhuo_iv)
    ImageView shouhuoIv;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    @Bind(R.id.add_tv)
    TextView addTv;
    @Bind(R.id.shouhuo)
    RelativeLayout shouhuo;
    @Bind(R.id.yanse)
    TextView yanse;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.tubiao)
    ImageView tubiao;
    @Bind(R.id.wenben)
    TextView wenben;
    @Bind(R.id.no_ry)
    RelativeLayout noRy;
    @Bind(R.id.shouhuoaddress_return)
    RelativeLayout shouhuoaddressReturn;

    private List<Food> food = new ArrayList<>();
    private DBHelper1 helper;
    private shouhuoadapter adapter;
    LoadingDialog dialog1;
    private ArrayList<String> lsit1 = new ArrayList<>();
    private RequestQueue queue;
    private ArrayList<String> us_name1 = new ArrayList<>();
    private ArrayList<String> us_phone1 = new ArrayList<>();
    private ArrayList<String> us_pscsds1 = new ArrayList<>();
    private ArrayList<String> us_address1 = new ArrayList<>();
    private ArrayList<String> us_id = new ArrayList<>();
    private Handler handle = new Handler();
    private ArrayList<String> us_is_default1 = new ArrayList<>();
    private String s;
    String jieguo;
    String results = "1";
    String shouhuod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouhuo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        jieguo = intent.getStringExtra("sure");
        if (jieguo != null) {
            Log.i("fgrr", "onCreate: " + jieguo);
        }
        dialog1 = new LoadingDialog(ShouhuoActivity.this);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.show();
        postshuju();
        setlistener();
      /*  handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setlistener();
                setadapter();
            }
        }, 1000);*/
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.dismiss();
            }
        }, 1000);
        StatusBarCompat.setStatusBarColor(ShouhuoActivity.this, Color.parseColor("#fbd23a"), true);
        //showAlertDialog() ;
      /*  Intent intent=getIntent();

        Bundle bundle=intent.getBundleExtra("tag");
        if(bundle!=null){

            String user=bundle.getString("xx");
            String num=bundle.getString("yy");
            String address=bundle.getString("zz");
            Log.i("zhu", "onCreate: "+user);
            Log.i("zhu1", "onCreate: "+num);
            Log.i("zhu2", "onCreate: "+address);*/

           /* lsit1.add(user);
            lsit1.add(num);
            lsit1.add(address);
            adapter.updateziliao(lsit1);
            adapter.updateziliao1(user,num,address);*/
     /*   }*/

    }

    private void postshuju() {
        us_name1.clear();
        us_phone1.clear();
        us_address1.clear();
        us_pscsds1.clear();
        us_id.clear();
        us_is_default1.clear();
        queue = Volley.newRequestQueue(ShouhuoActivity.this);
        String url = "https://api.aijiaijia.com/api_usershipping_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjian", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {

                            try {
                                s = new String(response.getBytes("ISO-8859-1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            jsonObject = new JSONObject(str);
                            Log.i("sdfdeer", "onResponse: " + s);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {

                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                if (jsonarry.length() == 0) {
                                    noRy.setVisibility(View.VISIBLE);
                                    list.setVisibility(View.GONE);
                                    return;
                                } else {
                                    noRy.setVisibility(View.GONE);
                                    list.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < jsonarry.length(); i++) {
                                        JSONObject object = jsonarry.getJSONObject(i);
                                        String us_name = object.getString("us_name");
                                        us_name1.add(us_name);
                                        String us_phone = object.getString("us_phone");
                                        us_phone1.add(us_phone);
                                        String us_pscsds = object.getString("us_pscsds");
                                        us_pscsds1.add(us_pscsds);
                                        String us_address = object.getString("us_address");
                                        us_address1.add(us_address);
                                        String id = object.getString("id");
                                        us_id.add(id);
                                        String us_is_default = object.getString("us_is_default");
                                        us_is_default1.add(us_is_default);
                                    }
                                    setadapter();

                                }

                            } else if (result3.equals("6")) {
                                Toast.makeText(ShouhuoActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ShouhuoActivity.this, LoginActivity.class);
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

    private void setadapter() {
        adapter = new shouhuoadapter(ShouhuoActivity.this, us_name1, us_phone1, us_pscsds1, us_address1, us_id, us_is_default1);
        list.setAdapter(adapter);
       /* adapter=new shouhuoadapter(this,food);
        list.setAdapter(adapter);*/

    }


    private void setlistener() {
        shouhuoaddressReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("heloe", "onClick: " + jieguo);
                Intent intent6 = new Intent(ShouhuoActivity.this, NewaddActivity.class);
                if (jieguo != null && jieguo.equals("2")) {
                    Log.i("heloe", "onClick: " + "1");
                    intent6.putExtra("msure", jieguo);
                    startActivity(intent6);
                    finish();
                } else if (jieguo != null && jieguo.equals("1")) {
                    Log.i("heloe", "onClick: " + "2");
                    intent6.putExtra("msure", jieguo);
                    startActivityForResult(intent6, 5);
                } else {
                    Log.i("heloe", "onClick: " + "3");
                    startActivityForResult(intent6, 5);
                }

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent5 = new Intent();
                intent5.putExtra("usname", us_name1.get(position));
                intent5.putExtra("usphone", us_phone1.get(position));
                intent5.putExtra("usaddress", us_address1.get(position));
                intent5.putExtra("uspscsds", us_pscsds1.get(position));
                setResult(7, intent5);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            if (data != null) {
                Log.i("fefd", "onActivityResult: " + requestCode);
                // postshuju();
                postshuju();

                jieguo = "1";
                // adapter.notifyDataSetChanged();
               /* handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setadapter();
                    }
                }, 2000);*/

            }
        }
    }
}
