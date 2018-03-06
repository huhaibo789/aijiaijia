package com.example.administrator.myyushi;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class payincashActivity extends AppCompatActivity {

    @Bind(R.id.paycash_rl)
    RelativeLayout paycashRl;
    @Bind(R.id.payin_rl)
    RelativeLayout payinRl;
    @Bind(R.id.creditcard_tv)
    RelativeLayout creditcardTv;
    @Bind(R.id.pickgods_tv)
    RelativeLayout pickgodsTv;
    @Bind(R.id.logistics_tv)
    RelativeLayout logisticsTv;
    @Bind(R.id.Goldsymbols)
    TextView Goldsymbols;
    @Bind(R.id.money_tv)
    EditText moneyTv;
    @Bind(R.id.payfor_tv)
    RelativeLayout payforTv;
    @Bind(R.id.mobilephone_tv)
    EditText mobilephoneTv;
    @Bind(R.id.mobilephone_rl)
    RelativeLayout mobilephoneRl;
    @Bind(R.id.timeout)
    TextView timeout;
    @Bind(R.id.timeall_tv)
    RelativeLayout timeallTv;
    @Bind(R.id.Verificationcode_tv)
    EditText VerificationcodeTv;
    @Bind(R.id.Verificationcode_rl)
    RelativeLayout VerificationcodeRl;
    @Bind(R.id.post_order)
    RelativeLayout postOrder;
    @Bind(R.id.printer_rl)
    RelativeLayout printerRl;
    @Bind(R.id.credit_tv)
    TextView creditTv;
    @Bind(R.id.paymoney_tv)
    TextView paymoneyTv;
    @Bind(R.id.xianchang_tv)
    TextView xianchangTv;
    @Bind(R.id.sonlogistic_tv)
    TextView sonlogisticTv;
    private RequestQueue queue;
    public String result1;
    private TimeCount time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payincash);
        ButterKnife.bind(this);
        initdata();  //初始化数据
        queue = Volley.newRequestQueue(this);
        setlistener();
        time = new TimeCount(30000, 1000); //倒计时
        StatusBarCompat.setStatusBarColor(payincashActivity.this, Color.parseColor("#fbd23a"), true); //改变状态栏颜色
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发

            timeout.setText("重新验证");
            timeout.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            timeout.setClickable(false);
            timeout.setText(millisUntilFinished /1000+"秒后重新获取");
        }
    }
    private void setlistener() {
        paycashRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        payinRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payinRl.setBackgroundResource(R.drawable.shoppers);
                creditcardTv.setBackgroundResource(R.drawable.nocheckshoppers);
            }
        });
        creditcardTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creditcardTv.setBackgroundResource(R.drawable.shoppers);
                payinRl.setBackgroundResource(R.drawable.nocheckshoppers);
            }
        });
       pickgodsTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               pickgodsTv.setBackgroundResource(R.drawable.shoppers);
               logisticsTv.setBackgroundResource(R.drawable.nocheckshoppers);
           }
       });
        logisticsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickgodsTv.setBackgroundResource(R.drawable.nocheckshoppers);
                logisticsTv.setBackgroundResource(R.drawable.shoppers);
            }
        });
        timeallTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aa=mobilephoneTv.getText().toString().trim();
                if(TextUtils.isEmpty(aa)||aa.length()!=11){
                    Toast toast = Toast.makeText(payincashActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }else {
                    postByStringRequest();
                }
            }
        });

    }

    private void postByStringRequest() {
        final String user=mobilephoneTv.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDataUrl = "https://api.aijiaijia.com/api_postphonenum_getsmscode";
        //POST方式更加安全
        StringRequest stringrequest = new StringRequest(Request.Method.POST, JSONDataUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response1) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response1);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            Log.i("ifdmf", "onResponse: "+response1);
                            result1=resposeobject.getString("op");
                            if(result1.equals("2")){
                                Toast  toast = Toast.makeText(payincashActivity.this,
                                        "此号码已注册存在", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                // Toast.makeText(zhuceActivity.this, "此号码已注册存在", Toast.LENGTH_SHORT).show();
                            }else if(result1.equals("1")){
                                time.start();
                            }else if(result1.equals(">1")){
                                Toast  toast = Toast.makeText(payincashActivity.this,
                                        "获取验证码失败", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                Toast toast = Toast.makeText(payincashActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        }) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", user);
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                for(String response1 : response.headers.keySet()){
                    if(response1.contains("Set-Cookie")){
                        //拿到session
                        String   cookies = response.headers.get(response1);
                        Constant.localCookie = cookies.substring(0, cookies
                                .indexOf(";"));
                        Log.i("info",cookies);
                        break;
                    }
                }
                return super.parseNetworkResponse(response);

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

        requestQueue.add(stringrequest);

    }

    private void initdata() {
    }
}
