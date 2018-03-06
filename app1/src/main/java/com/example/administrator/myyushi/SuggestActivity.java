package com.example.administrator.myyushi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SuggestActivity extends AppCompatActivity {

    @Bind(R.id.advice)
    RelativeLayout advice;
    @Bind(R.id.advice_et)
    EditText adviceEt;
    @Bind(R.id.post)
    Button post;
    @Bind(R.id.return_jianyi)
    ImageView returnJianyi;

    private RequestQueue queue;
    private String edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setTranslucentStatus();
        //setStatusBar(this.getWindow());
        setContentView(R.layout.activity_suggest);
        ButterKnife.bind(this);
        setlistener();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#fbd23a"), true);



    }


    private void setlistener() {
        returnJianyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adviceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adviceEt.setCursorVisible(true);
                edit = adviceEt.getText().toString().trim();
                if (edit == null) {
                    Toast.makeText(SuggestActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
            }
        });
    }

    private void postdata() {
        queue = Volley.newRequestQueue(SuggestActivity.this);
        String urls = "https://api.aijiaijia.com/api_feedback_commit";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu5se", "onResponse: post  success " + response);
                        String data = response.toString().trim();
                        try {
                            JSONObject object = new JSONObject(data);
                            JSONObject responce = object.getJSONObject("responseJson");
                            String result = responce.getString("op");
                            if (result.equals("1")) {
                                Toast.makeText(SuggestActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SuggestActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
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
                try {
                    String str = new String(edit.getBytes("utf-8"), "ISO-8859-1");
                    map.put("content", str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }


        };
        queue.add(post);
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
