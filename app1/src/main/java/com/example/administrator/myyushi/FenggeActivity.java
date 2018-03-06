package com.example.administrator.myyushi;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;

import java.util.ArrayList;

import Urlutil.Utils;
import bean.huasabean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import utils.FileUtils;

public class FenggeActivity extends AppCompatActivity {

    @Bind(R.id.huasa_iv)
    ImageView huasaIv;
    @Bind(R.id.fengge_tv2)
    TextView fenggeTv2;
    @Bind(R.id.fengge_lv2)
    ListView fenggeLv2;
    private ArrayList<huasabean.ResponseJsonBean.ZuListBean> zulistbean1=new ArrayList<>();
    private ArrayAdapter<String> adapter1;
    private RequestQueue queue;
    private ArrayList<String> huan1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fengge);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        initData();
        setlistener();
    }

    private void setlistener() {
        huasaIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        BiZhiRequest<huasabean>  request=new BiZhiRequest<huasabean>(huasabean.class, Utils.TYPE_Zhangting1_URL, new Response.Listener<huasabean>() {
            @Override
            public void onResponse(huasabean response) {
                if(response!=null&&response.getResponseJson().getZu_list()!=null){
                    zulistbean1.addAll(response.getResponseJson().getZu_list());

                   // String bb=zulistbean1.get(1).getAttr_name();
                    String aa=zulistbean1.get(1).getAttr_value();
                    //fenggeTv2.setText(bb);
                    String[] sourcrAttr=aa.split(",");
                    for (int i = 0; i <sourcrAttr.length ; i++) {
                        Log.i("tyt", "getView: "+sourcrAttr[i]);
                        huan1.add(sourcrAttr[i]);
                    }
                    adapter1 = new ArrayAdapter<String>(FenggeActivity.this, android.R.layout.simple_spinner_dropdown_item, huan1);
                    fenggeLv2.setAdapter(adapter1);
                     fenggeLv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                         @Override
                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                             Intent intent5=new Intent();
                             intent5.putExtra("result7", huan1.get(position));
                             setResult(5,intent5);
                             finish();

                         }
                     });


                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FenggeActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
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
