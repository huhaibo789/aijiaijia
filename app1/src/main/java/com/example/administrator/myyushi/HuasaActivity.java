package com.example.administrator.myyushi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Urlutil.Utils;
import adapter.zhantingadapter;
import adapter.zhantingadapter1;
import bean.huasabean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import util.Myapp;
import utils.FileUtils;

public class HuasaActivity extends AppCompatActivity {

    @Bind(R.id.fengge_lv1)
    ListView fenggeLv1;
    @Bind(R.id.huasa_iv)
    ImageView huasa_iv;
    @Bind(R.id.fengge_tv1)
    TextView  fengetv1;

    private ArrayAdapter<String> adapter;
    private zhantingadapter1 ztadapter1;
    private RequestQueue queue;
    private Context context;
    private ArrayList<huasabean.ResponseJsonBean.ZuListBean> zulistbean=new ArrayList<>();
    private ArrayList<String> huan=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huasa);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        initData();
       // setadapter();
        setlistener();
        
    }

    private void setlistener() {
        huasa_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

   /* private void setadapter() {
        ImageLoader loader=((Myapp)HuasaActivity.this.getApplication()).getLoader();
        ztadapter1=new zhantingadapter1(HuasaActivity.this,loader);
        fenggeLv1.setAdapter(ztadapter1);
    }*/

    private void initData() {
       /* StringRequest post=new StringRequest(StringRequest.Method.POST, Utils.TYPE_Zhangting1_URLPost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("yongpost", "onResponse: "+response);



            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", "1");
                map.put("cityname", "all");
                return map;
            }
        };
        queue.add(post);*/

       BiZhiRequest<huasabean>  request=new BiZhiRequest<huasabean>(huasabean.class, Utils.TYPE_Zhangting1_URL, new Response.Listener<huasabean>() {
            @Override
            public void onResponse(huasabean response) {
                 final String zu_id=String.valueOf(response.getResponseJson().getZu_id());
                if(response!=null&&response.getResponseJson().getZu_list()!=null){
                    zulistbean.addAll(response.getResponseJson().getZu_list());
                    String aa=zulistbean.get(0).getAttr_value();
                    Log.i("tyt1", "onResponse: "+aa);
                    final String[] sourcrAttr=aa.split(",");
                    for (int i = 0; i <sourcrAttr.length ; i++) {
                        Log.i("tyt", "getView: "+sourcrAttr[i]);
                        huan.add(sourcrAttr[i]);
                    }
                    adapter = new ArrayAdapter<String>(HuasaActivity.this, android.R.layout.simple_spinner_dropdown_item, huan);
                    fenggeLv1.setAdapter(adapter);
                   fenggeLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                               Intent intent4=new Intent();
                               intent4.putExtra("result6", huan.get(position));
                                intent4.putExtra("newresult",zu_id);
                              Log.i("ggsmi", "onItemClick: "+huan.get(position));
                               setResult(4,intent4);
                               finish();

                       }
                   });

                   // ztadapter1.notifyDataSetChanged();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HuasaActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
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
