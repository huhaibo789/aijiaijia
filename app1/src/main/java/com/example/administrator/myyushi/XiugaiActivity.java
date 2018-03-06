package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class XiugaiActivity extends AppCompatActivity {

    @Bind(R.id.shouhuo_iv2)
    ImageView shouhuoIv2;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    @Bind(R.id.shouhuo)
    RelativeLayout shouhuo;
    @Bind(R.id.againNew_password)
    EditText againNew_password;
    @Bind(R.id.new_password)
    EditText new_password;
    @Bind(R.id.before_password)
    EditText before_password;
    @Bind(R.id.xiugai_button)
    Button xiugai_button;
    private RequestQueue queue;
    private Handler handler = new Handler();
    private String url = "https://api.aijiaijia.com/api_updatepassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_xiugai);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        setlistener();
        StatusBarCompat.setStatusBarColor(XiugaiActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void setlistener() {
        before_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String beforepassword=before_password.getText().toString().trim();
                String newpassword=new_password.getText().toString().trim();
                String againNewpassword=againNew_password.getText().toString().trim();
                if(!TextUtils.isEmpty(beforepassword)&&!TextUtils.isEmpty(newpassword)&&!TextUtils.isEmpty(againNewpassword)){
                    if(beforepassword.length()>=6&&newpassword.length()>=6&&againNewpassword.length()>=6){
                        xiugai_button.setClickable(true);
                        xiugai_button.setBackgroundColor(Color.parseColor("#fbd23a"));
                    }else {
                        xiugai_button.setClickable(false);
                        xiugai_button.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                }else {
                    xiugai_button.setClickable(false);
                    xiugai_button.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String beforepassword=before_password.getText().toString().trim();
                String newpassword=new_password.getText().toString().trim();
                String againNewpassword=againNew_password.getText().toString().trim();
                if(!TextUtils.isEmpty(beforepassword)&&!TextUtils.isEmpty(newpassword)&&!TextUtils.isEmpty(againNewpassword)){
                    if(beforepassword.length()>=6&&newpassword.length()>=6&&againNewpassword.length()>=6){
                        xiugai_button.setClickable(true);
                        xiugai_button.setBackgroundColor(Color.parseColor("#fbd23a"));
                    }else {
                        xiugai_button.setClickable(false);
                        xiugai_button.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                }else {
                    xiugai_button.setClickable(false);
                    xiugai_button.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        againNew_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String beforepassword=before_password.getText().toString().trim();
                String newpassword=new_password.getText().toString().trim();
                String againNewpassword=againNew_password.getText().toString().trim();
                if(!TextUtils.isEmpty(beforepassword)&&!TextUtils.isEmpty(newpassword)&&!TextUtils.isEmpty(againNewpassword)){
                    if(beforepassword.length()>=6&&newpassword.length()>=6&&againNewpassword.length()>=6){
                        xiugai_button.setClickable(true);
                        xiugai_button.setBackgroundColor(Color.parseColor("#fbd23a"));
                    }else {
                        xiugai_button.setClickable(false);
                        xiugai_button.setBackgroundColor(Color.parseColor("#B4B4B4"));
                    }
                }else {
                    xiugai_button.setClickable(false);
                    xiugai_button.setBackgroundColor(Color.parseColor("#B4B4B4"));
                }
            }
        });
        shouhuoIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xiugai_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String beforepassword=before_password.getText().toString().trim();
                String newpassword=new_password.getText().toString().trim();
                String againNewpassword=againNew_password.getText().toString().trim();
                if(beforepassword.equals(newpassword)){
                    Toast toast = Toast.makeText(XiugaiActivity.this, "新密码不能与原密码相同", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                   return;
                }else {
                    if(!newpassword.equals(againNewpassword)){
                        Toast toast = Toast.makeText(XiugaiActivity.this, "两次输入的新密码不同", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else {
                        postvolley1(url);
                    }
                }

               // final String url4=url+"?"+"oldpassword="+bb+"&&"+"newpassword="+bb1;
               // postByStringRequest();
              /*  handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // getVolley(url4);
                        //postvolley(url);

                    }
                }, 2000);*/

            }
        });
    }

    private void postvolley1(String url) {
        final String bb=before_password.getText().toString();
        final String bb1=new_password.getText().toString();
        String bb2=againNew_password.getText().toString();
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str=response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                Toast toast = Toast.makeText(XiugaiActivity.this, "修改成功", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent  intent=new Intent(XiugaiActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (result3.equals("5")) {
                                Toast toast = Toast.makeText(XiugaiActivity.this,"用户名不存在", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (result3.equals("6")) {
                                Toast toast = Toast.makeText(XiugaiActivity.this,"未登录", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (result3.equals("7")) {
                                Toast toast = Toast.makeText(XiugaiActivity.this,"原密码不正确", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }else {
                                Toast toast = Toast.makeText(XiugaiActivity.this, "修改失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                        }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(XiugaiActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("oldpassword",bb);
                map.put("newpassword", bb1);
                map.put("fromdevice","2");
                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试8", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
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