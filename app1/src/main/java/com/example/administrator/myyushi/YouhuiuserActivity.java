package com.example.administrator.myyushi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YouhuiuserActivity extends AppCompatActivity {

    @Bind(R.id.youhuiuser_tv)
    TextView youhuiuserTv;
  private String aa;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiuser);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        Intent intent=getIntent();
        aa=intent.getStringExtra("youhui");
        getByStringRequest1(aa);


    }
    private void getByStringRequest1(String bb) {
        String url5=bb;
        Log.i("enen", "postByStringRequest: "+aa);
        //2. 封装请求对象
        /*
        * 在封装请求对象时，由于Volley中的请求对象的父类Request是一个抽象类
        * 因此，在封装请求对象时，通常使用现成的实现子类
        * 常用子类：
        * 1）StringRequest
        * 2）JsonObjectRequest
        * 3）自定义Request子类
        * 以上3者的区别：
        * 网络请求返回的数据类型不同
        * */
        StringRequest getString = new StringRequest(
                StringRequest.Method.GET,  //设置请求方式（如果是get请求，那么此参数可省略）
                url5, //设置连接网址
                new Response.Listener<String>() {
                    //一旦成功读取此数据后，调用此方法
                    //参数：网络连接读取结果
                    @Override
                    public void onResponse(String response) {
                        Log.i("wunaide ", "onResponse: "+response);
                        youhuiuserTv.setText(" 此优惠卷可全场通用");
                    }
                },
                new Response.ErrorListener() {
                    //一旦连接出错，调用此方法，
                    //参数：出错原因
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("caole", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        //3. 将请求对象添加到请求队列中执行加载操作
        queue.add(getString);
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
