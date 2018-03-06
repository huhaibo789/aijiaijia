package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.unionpay.UPPayAssistEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Alipay.AuthResult;
import Alipay.PayResult;
import Alipay.SignUtils;
import adapter.yuyedetailsadapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.FileUtils19;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.Fileutils18;

public class detailsActivity extends AppCompatActivity {

    @Bind(R.id.appointdetail_tv)
    TextView appointdetailTv;
    @Bind(R.id.appointdetail_tv1)
    TextView appointdetailTv1;
    @Bind(R.id.Dingdanshutv2)
    TextView Dingdanshutv2;
    @Bind(R.id.yuyue_number)
    TextView yuyueNumber;
    @Bind(R.id.listview_yuyue)
    ListView listviewYuyue;
    @Bind(R.id.shouhuo_return)
    ScrollView shouhuoReturn;
    @Bind(R.id.detailyuyue_iv)
    ImageView detailyuyueIv;
    @Bind(R.id.noproduct)
    TextView noproduct;
    @Bind(R.id.detailleft_button)
    Button detailleftButton;
    @Bind(R.id.detailsmiddle_button)
    Button detailsmiddleButton;
    @Bind(R.id.detailright_button)
    Button detailrightButton;
    @Bind(R.id.total_ly)
    LinearLayout totalLy;
    @Bind(R.id.satte_tv)
    TextView satteTv;
    @Bind(R.id.arrive_tv)
    TextView arriveTv;
    @Bind(R.id.arrive_tv1)
    TextView arriveTv1;
    @Bind(R.id.kefu_ivs)
    ImageView kefuIvs;
    private yuyedetailsadapter adapter;
    private RequestQueue queue;
    private String order_notifyURL;
    private  String RSA_PRIVATE;
    private String sell;
    private String partner;
    private String  order_sn;
    private String  order_subject;
    private String order_body;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> phone = new ArrayList<>();
    private ArrayList<String> address = new ArrayList<>();
    private ArrayList<String> ordersn = new ArrayList<>();
    private ArrayList<String> state = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> mendian = new ArrayList<>();
    private ArrayList<String> product = new ArrayList<>();
    private ArrayList<String> picture = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> smalltitle = new ArrayList<>();
    private ArrayList<String> nowprice = new ArrayList<>();
    private ArrayList<String> beforeprice = new ArrayList<>();
    private ArrayList<String> number = new ArrayList<>();
    private String ss;
    private  String tn;
    private String order_id;
    private String ordersn1;
    private ImageView sellorder_iv;
    private ImageView sellorderkefu_ivs;
    private  TextView sellorderdetail_tv;
    private TextView sellorderdetail_tv1;
    private  LinearLayout sellordertotal_ly;
    private TextView sellorderaddress;
    private TextView sellorder_number;
    private  TextView sellorderstate_tv;
    private ListView listview_sellorder;
    private Handler handle=new Handler();
    private Button detailleft_button1,detailsmiddle_button2,detailright_button3;
    private TextView sellorder_zhifu,sellorder_total,sellorder_youhui,sellorder_jifen,sellorder_yunfei,sellorder_shiji;
    private final String mMode = "00";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    public static final String APPID = "2016080901725251";
    private  String order_payprice;
    @SuppressLint("HandlerLeak")
    private Handler  mHandler1 = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(detailsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(detailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(detailsActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(detailsActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(detailsActivity.this, Color.parseColor("#fbd23a"), true);
        Intent intent = getIntent();
        order_id = intent.getStringExtra("orderyu");
        ordersn1 = intent.getStringExtra("orederyu1");
        Log.i("bianhao1", "onCreate: "+order_id);
        Log.i("bianhao", "onCreate: "+ordersn1);
        if(ordersn1!=null){
            Log.i("haha", "onCreate: "+ordersn1);
        }else {
            Log.i("haue", "onCreate: "+order_id);
        }

        String aa=intent.getStringExtra("tt");
        if(aa.equals("1")){
            setContentView(R.layout.activity_sellorderdetail);
            sellorder_iv= (ImageView) findViewById(R.id.sellorder_iv);
            sellorderkefu_ivs= (ImageView) findViewById(R.id.sellorderkefu_ivs);
            sellorderdetail_tv= (TextView) findViewById(R.id.sellorderdetail_tv);
            sellorderdetail_tv1= (TextView) findViewById(R.id.sellorderdetail_tv1);
            sellordertotal_ly= (LinearLayout) findViewById(R.id.sellordertotal_ly);
            sellorderaddress= (TextView) findViewById(R.id.sellorderaddress);
            sellorder_number= (TextView) findViewById(R.id.sellorder_number);
            sellorderstate_tv= (TextView) findViewById(R.id.sellorderstate_tv);
            listview_sellorder= (ListView) findViewById(R.id.listview_sellorder);
            detailleft_button1= (Button) findViewById(R.id.detailleft_button1);
            detailsmiddle_button2= (Button) findViewById(R.id.detailsmiddle_button2);
            detailright_button3= (Button) findViewById(R.id.detailright_button3);
            sellorder_zhifu= (TextView) findViewById(R.id.sellorder_zhifu);
            sellorder_total= (TextView) findViewById(R.id.sellorder_total);
            sellorder_youhui= (TextView) findViewById(R.id.sellorder_youhui);
            sellorder_jifen= (TextView) findViewById(R.id.sellorder_jifen);
            sellorder_yunfei= (TextView) findViewById(R.id.sellorder_yunfei);
            sellorder_shiji= (TextView) findViewById(R.id.sellorder_shiji);
            postshuju1();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setadapter1();
                    setlistener1();
                }
            },1000);

        }else {
            postshuju();
            setlistener();
        }

    }

    private void setlistener1() {
        sellorderkefu_ivs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=ordersn1;
                String ww="订单详情";
                String title = "在线客服";
                // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                ConsultSource source = new ConsultSource(url, ww, "custom information string");
                // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                Unicorn.openServiceActivity(detailsActivity.this, // 上下文
                        title, // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
            }
        });
        sellorder_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        detailleft_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss=detailleft_button1.getText().toString();
                if(ss.equals("售后")){
                    String url=ordersn1;
                    Log.i("urejd", "onClick: "+url);
                    String ww="导购订单";
                    String title = "                              在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(url, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(detailsActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                }else if(ss.equals("退换货")){

                    String url=ordersn1;
                    String ww="导购订单";
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(url, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(detailsActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                }
            }
        });
        String ss1=detailsmiddle_button2.getText().toString();
        String ss2=detailright_button3.getText().toString();
        if(ss1.equals("安装")){
            detailsmiddle_button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url=ordersn1;
                    String ww="导购订单";
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(url, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(detailsActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );

                }
            });
        }else if(ss1.equals("取消订单")){
            detailsmiddle_button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postshujus();
                }
            });

        }else if(ss1.equals("查看物流")){
            detailsmiddle_button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(detailsActivity.this, sellordewuliuActivity.class);
                    intent.putExtra("sn1", ordersn1);
                    startActivity(intent);
                }
            });

          /*  queue = Volley.newRequestQueue(detailsActivity.this);
            String url = "https://api.aijiaijia.com/api_sellordertracedetail";
            final StringRequest post = new StringRequest(
                    StringRequest.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i("gouwudasfsw", "onResponse: post  success " + response);
                            String  ss = response.toString().trim();
                            JSONObject jsonObject = null;

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
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("ordersn",ordersn1);
                    return map;
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

            queue.add(post);*/

        }
        if(ss2.equals("付款")&& sellorder_zhifu.getText().equals("支付宝")){
            detailright_button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postdetail();
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setlistener3();
                        }
                    },500);

                }
            });


        }else if(ss2.equals("付款")&&sellorder_zhifu.getText().equals("银联支付")){
            detailright_button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postshuju2();
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UPPayAssistEx.startPay(detailsActivity.this, null, null, tn, mMode);
                        }
                    },500);

                }
            });

        }else if(ss2.equals("评价")){
            detailright_button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(detailsActivity.this,appointevaluteActivity.class);
                    intent.putExtra("orderid1", order_id );
                    startActivity(intent);
                }
            });

        }else if(ss2.equals("确认收货")){

            detailright_button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    queue = Volley.newRequestQueue(detailsActivity.this);
                    String url = "https://api.aijiaijia.com/api_sellorder_update";
                    final StringRequest post = new StringRequest(
                            StringRequest.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.i("gouwudasfsw", "onResponse: post  success " + response);

                                    String  ss = response.toString().trim();
                                    JSONObject jsonobject = null;
                                    try {
                                        jsonobject = new JSONObject(ss);
                                        JSONObject responsejson = jsonobject.getJSONObject("responseJson");
                                        String result=responsejson.getString("op");
                                        if(result.equals("1")){
                                            Toast toast = Toast.makeText(detailsActivity.this, "收货成功", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();

                                        }else {
                                            Toast toast = Toast.makeText(detailsActivity.this, "收货失败", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    JSONObject jsonObject = null;

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
                        protected Map<String,String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("orderid",order_id);
                            map.put("s","5");
                            return map;
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

                    queue.add(post);
                }
            });

        }else if(ss2.equals("申请退款")){
            detailright_button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url=ordersn1;
                    String ww="导购订单";
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(url, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(detailsActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );

                }
            });
        }



    }

    private void setlistener3() {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(detailsActivity.this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                          finish();
                        }
                    }).show();
            return;
        }
/**
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 *
 * orderInfo的获取必须来自服务端；
 */
        String orderInfo = getOrderInfo(order_subject, order_body, order_payprice, order_sn, order_notifyURL);
        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(detailsActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler1.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price,String sn,String url) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + partner+ "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + sell + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + sn+ "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url="+ "\"" + url + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
       /* *//**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         *//*
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID,order_payprice,order_sn,order_body,order_subject,order_notifyURL);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(detailsActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler1.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();*/

    private void postshuju2() {
        String url = "https://api.aijiaijia.com/api_sellorder_topay";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String  ss = response.toString().trim();
                        try {
                            JSONObject object=new JSONObject(ss);
                            JSONObject responseobject=object.getJSONObject("responseJson");
                            tn=responseobject.getString("tn");
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ordersn",ordersn1);
                return map;
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

        queue.add(post);
    }

    private void postdetail() {
        queue = Volley.newRequestQueue(detailsActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_topay";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                        String  ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            sell=resposeobject.getString("seller");
                            RSA_PRIVATE=resposeobject.getString("privatekey");
                            partner=resposeobject.getString("partner");
                            order_sn=resposeobject.getString("order_sn");
                            order_subject=resposeobject.getString("order_subject");
                            order_payprice=resposeobject.getString("order_payprice");
                            order_body=resposeobject.getString("order_body");
                            order_notifyURL=resposeobject.getString("order_notifyURL");
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ordersn",ordersn1);
                return map;
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

        queue.add(post);
    }

    private void postshujus() {
        queue = Volley.newRequestQueue(detailsActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                        String  ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(detailsActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("21")) {
                                Toast.makeText(detailsActivity.this, "取消失败", Toast.LENGTH_SHORT).show();
                            }else if(op.equals("6")){
                                Toast.makeText(detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderid",  order_id );
                map.put("s","7");
                return map;
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

        queue.add(post);

    }

    private void postshuju1() {
        picture.clear();
        title.clear();
        smalltitle.clear();
        nowprice.clear();
        beforeprice.clear();
        number.clear();
        queue = Volley.newRequestQueue(detailsActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_info";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ss = response.toString().trim();
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject responseobject = jsonobject.getJSONObject("responseJson");
                            String op = responseobject.getString("op");
                            if (op.equals("1")) {
                              JSONObject object=responseobject.getJSONObject("shipping");
                                String ordershipping_name=object.getString("ordershipping_name");
                                String ordershipping_address=object.getString("ordershipping_address");
                                String ordershipping_phone=object.getString("ordershipping_phone");
                                String ordershipping_pscsds=object.getString("ordershipping_pscsds");
                                sellorder_number.setText("订单编号:"+ordersn1);

                                if(ordershipping_name!=null){
                                    sellorderdetail_tv.setVisibility(View.VISIBLE);
                                    sellorderdetail_tv.setText(ordershipping_name);
                                }else {
                                    sellorderdetail_tv.setVisibility(View.INVISIBLE);
                                }
                                if(ordershipping_phone!=null){
                                    sellorderdetail_tv1.setVisibility(View.VISIBLE);
                                    sellorderdetail_tv1.setText(ordershipping_phone);
                                }else {
                                    sellorderdetail_tv1.setVisibility(View.INVISIBLE);
                                }
                                if(ordershipping_address!=null&&ordershipping_address!=null){
                                    sellordertotal_ly.setVisibility(View.VISIBLE);
                                    sellorderaddress.setText(ordershipping_pscsds+ordershipping_address);
                                }else {
                                    sellordertotal_ly.setVisibility(View.INVISIBLE);
                                }
                                JSONArray jsonarry = responseobject.getJSONArray("sellorders");
                                for (int i = 0; i <jsonarry.length() ; i++) {
                                    JSONObject jsonobject1=jsonarry.getJSONObject(i);
                                    String order_payway=jsonobject1.getString("order_payway");
                                    Log.i("frfr", "onResponse: "+order_payway);
                                    sellorder_zhifu.setText(order_payway);
                                    String order_shipfee=jsonobject1.getString("order_shipfee");
                                    sellorder_yunfei.setText(order_shipfee);
                                    String order_coupon_price=jsonobject1.getString("order_coupon_price");
                                    sellorder_youhui.setText(order_coupon_price);
                                    String order_point_price=jsonobject1.getString("order_point_price");
                                    sellorder_jifen.setText(order_point_price);
                                    String order_payprice=jsonobject1.getString("order_payprice");
                                    sellorder_shiji.setText(order_payprice);
                                    String order_price=jsonobject1.getString("order_price");
                                    sellorder_total.setText(order_price);
                                    String  sellorderstate_tv1=jsonobject1.getString("order_statustext");
                                    sellorderstate_tv.setText(sellorderstate_tv1);
                                     String zhuangtai=jsonobject1.getString("order_status");
                                    if (zhuangtai.equals("21")) {
                                        detailleft_button1.setVisibility(View.INVISIBLE);
                                        detailsmiddle_button2.setVisibility(View.INVISIBLE);
                                        detailright_button3.setText("取消预约");
                                    } else if (zhuangtai.equals("2")) {
                                        detailleft_button1.setVisibility(View.INVISIBLE);
                                        detailsmiddle_button2.setVisibility(View.VISIBLE);
                                        detailright_button3.setText("付款");
                                        detailsmiddle_button2.setText("取消订单");
                                    } else if (zhuangtai.equals("4")) {
                                        detailleft_button1.setVisibility(View.VISIBLE);
                                        detailsmiddle_button2.setVisibility(View.VISIBLE);
                                        detailleft_button1.setText("退换货");
                                        detailsmiddle_button2.setText("查看物流");
                                        detailright_button3.setText("确认收货");

                                    } else if (zhuangtai.equals("5")) {
                                        detailleft_button1.setVisibility(View.VISIBLE);
                                        detailsmiddle_button2.setVisibility(View.VISIBLE);
                                        detailleft_button1.setText("售后");
                                        detailsmiddle_button2.setText("安装");
                                        detailright_button3.setText("评价");
                                    } else if (zhuangtai.equals("7")) {
                                        detailleft_button1.setVisibility(View.INVISIBLE);
                                        detailsmiddle_button2.setVisibility(View.INVISIBLE);
                                        detailright_button3.setText("已取消");
                                    } else if (zhuangtai.equals("3")) {
                                        detailleft_button1.setVisibility(View.INVISIBLE);
                                        detailsmiddle_button2.setVisibility(View.INVISIBLE);
                                        detailright_button3.setText("申请退款");
                                    } else if (zhuangtai.equals("6")) {
                                        detailleft_button1.setVisibility(View.VISIBLE);
                                        detailsmiddle_button2.setVisibility(View.VISIBLE);
                                        detailleft_button1.setText("退换货");
                                        detailsmiddle_button2.setText("查看物流");
                                        detailright_button3.setText("确认收货");

                                    } else if (zhuangtai.equals("1")) {
                                        detailleft_button1.setVisibility(View.VISIBLE);
                                        detailsmiddle_button2.setVisibility(View.VISIBLE);
                                        detailleft_button1.setText("售后");
                                        detailsmiddle_button2.setText("安装");
                                        detailright_button3.setText("已评价");
                                    }
                                    JSONArray jsonarry2= jsonobject1.getJSONArray("products");
                                    Log.i("odkdd", "onResponse: "+jsonarry2.toString());
                                    for (int j= 0; j <jsonarry2.length() ; j++) {
                                        JSONObject jdonobject = jsonarry2.getJSONObject(j);
                                        picture.add(jdonobject.getString("product_pic"));
                                        title.add(jdonobject.getString("product_name"));
                                        smalltitle.add(jdonobject.getString("product_bn"));
                                        nowprice.add(jdonobject.getString("product_nowprice"));
                                        beforeprice.add(jdonobject.getString("product_price"));
                                        number.add(jdonobject.getString("num"));
                                    }
                                }


                                Log.i("dddfer", "onResponse: "+ordersn);

                            } else if (op.equals("6")) {
                                Toast.makeText(detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("orderid", order_id);
                Log.i("sdfeee", "getParams: "+order_id);
                return map;
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
        queue.add(post);
    }

    private void setadapter1() {
        ImageLoader loader = ((Myapp) detailsActivity.this.getApplication()).getLoader();
        adapter = new yuyedetailsadapter(detailsActivity.this, loader, picture, title, smalltitle, nowprice, beforeprice, number);
        listview_sellorder.setAdapter(adapter);
        setListViewHeightBasedOnChildren1(listview_sellorder);
    }
    private void setListViewHeightBasedOnChildren1(ListView listview_sellorder) {
        ListAdapter listAdapter = listview_sellorder.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview_sellorder);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview_sellorder.getLayoutParams();
        params.height = totalHeight
                + (listview_sellorder.getDividerHeight() * (listAdapter.getCount() - 1));
        listview_sellorder.setLayoutParams(params);

    }
    private void setlistener() {
        kefuIvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=ordersn1;
                String ww="订单详情";
                String title = "在线客服";
                // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                ConsultSource source = new ConsultSource(url, ww, "custom information string");
                // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                Unicorn.openServiceActivity(detailsActivity.this, // 上下文
                        title, // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
            }
        });
        detailyuyueIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        detailrightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss = detailrightButton.getText().toString();
                if (ss.equals("取消预约")) {
                    postdata();
                } else if (ss.equals("付款")) {
                    String ss3 = "请选择";
                    String ss1 = "Null";
                    String ss2 = "Null";
                    String aa = "没有使用积分";
                    FileUtils19 qq = new FileUtils19();
                    qq.saveDataToFile(detailsActivity.this, aa);
                    FileUtils21 kk1 = new FileUtils21();
                    kk1.saveDataToFile(detailsActivity.this, ss2);
                    FileUtils20 kk = new FileUtils20();
                    kk.saveDataToFile(detailsActivity.this, ss1);
                    Fileutils18 gg = new Fileutils18();
                    gg.saveDataToFile(detailsActivity.this, ss3);
                    Intent intent = new Intent(detailsActivity.this, YuyueActivity.class);
                    String data = ordersn1;
                    intent.putExtra("ordersn", data);
                    Log.i("iefcmed", "onClick: " + data.toString());
                    startActivity(intent);

                }else if(ss.equals("申请退款")){
                    String url="https://api.aijiaijia.com/api_appointorder_list?page=1";
                    String ww="导购订单";
                    String title = "在线客服";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    ConsultSource source = new ConsultSource(url, ww, "custom information string");
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(detailsActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                }else if(ss.equals("确认收货")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(detailsActivity.this);
                    // 链式编程
                    builder.setTitle("提示")
                            .setMessage("是否确认收货")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    postdata1();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(detailsActivity.this, "你已取消", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setCancelable(false); // 能否通过点击对话框以外的区域（包括返回键）关闭对话框
                    // 通过建造者创建Dialog对象
                    // AlertDialog dialog = builder.create();
                    // dialog.show();
                    // 以上两行代码可以简化成以下这一行代码
                    builder.show();


                }else if(ss.equals("取消订单")){
                    postquxiao();
                }else if(ss.equals("评价")){

                    Intent intent=new Intent(detailsActivity.this,appointevaluteActivity.class);
                    intent.putExtra("orderid",order_id);
                    startActivity(intent);
                }
            }
        });
        detailsmiddleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = detailsmiddleButton.getText().toString();
                if (data.equals("取消预约")) {
                    postdata();
                }else if(data.equals("查看物流")){
                    Intent intent = new Intent(detailsActivity.this, sellordewuliuActivity.class);
                    intent.putExtra("sn1", ordersn);
                    startActivity(intent);
                }
            }
        });
    }

    private void postquxiao() {
        queue = Volley.newRequestQueue(detailsActivity.this);
        String url = "https://api.aijiaijia.com/api_appointorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                        ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(detailsActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("21")) {
                                Toast.makeText(detailsActivity.this, "取消失败", Toast.LENGTH_SHORT).show();
                            }else if(op.equals("6")){
                                Toast.makeText(detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderid",order_id);
                map.put("s","3");
                return map;
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

        queue.add(post);
    }

    private void postdata1() {
        queue = Volley.newRequestQueue(detailsActivity.this);
        String url = "http://api.aijiaijia.com/api_appointorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(detailsActivity.this, "收货成功", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("21")) {
                                Toast.makeText(detailsActivity.this, "收货失败", Toast.LENGTH_SHORT).show();
                            }else if(op.equals("6")){
                                Toast.makeText(detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderid",order_id);
                map.put("s","5");
                return map;
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

        queue.add(post);
    }
    private void postdata() {
        queue = Volley.newRequestQueue(detailsActivity.this);
        String url = "https://api.aijiaijia.com/api_appointorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(detailsActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("21")) {
                                Toast.makeText(detailsActivity.this, "失败", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("6")) {
                                Toast.makeText(detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("orderid", order_id);
                map.put("s", "7");
                return map;
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

        queue.add(post);
    }

    private void postshuju() {
        name.clear();
        phone.clear();
        address.clear();
        ordersn.clear();
        state.clear();
        time.clear();
        mendian.clear();
        product.clear();
        picture.clear();
        title.clear();
        smalltitle.clear();
        nowprice.clear();
        beforeprice.clear();
        number.clear();
        queue = Volley.newRequestQueue(detailsActivity.this);
        String url = "https://api.aijiaijia.com/api_appointorder_info";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ss = response.toString().trim();
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject responseobject = jsonobject.getJSONObject("responseJson");
                            String op = responseobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(detailsActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                                JSONArray jsonarry = responseobject.getJSONArray("sellorders");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    name.add(object.getString("order_appointname"));
                                    phone.add(object.getString("order_appointphone"));
                                    address.add(object.getString("order_appointaddress"));
                                    Log.i("suizhe", "onResponse: "+address);
                                    ordersn.add(object.getString("order_sn"));
                                    state.add(object.getString("order_statustext"));
                                    time.add(object.getString("order_appointtime"));
                                    mendian.add(object.getString("order_appointshop"));
                                    product.add(object.getString("order_zuchecked"));
                                    String orderstate = object.getString("order_status");
                                    if (orderstate.equals("21")) {
                                        detailleftButton.setVisibility(View.INVISIBLE);
                                        detailsmiddleButton.setVisibility(View.INVISIBLE);
                                        detailrightButton.setText("取消预约");
                                    } else if (orderstate.equals("2")) {
                                        detailleftButton.setVisibility(View.INVISIBLE);
                                        detailsmiddleButton.setVisibility(View.VISIBLE);
                                        detailsmiddleButton.setText("取消预约");
                                        detailrightButton.setText("付款");
                                    } else if (orderstate.equals("4")) {
                                        detailleftButton.setVisibility(View.VISIBLE);
                                        detailsmiddleButton.setVisibility(View.VISIBLE);
                                        detailleftButton.setText("退换货");
                                        detailsmiddleButton.setText("查看物流");
                                        detailrightButton.setText("确认收货");
                                    } else if (orderstate.equals("5")) {
                                        detailleftButton.setVisibility(View.VISIBLE);
                                        detailsmiddleButton.setVisibility(View.VISIBLE);
                                        detailleftButton.setText("售后");
                                        detailsmiddleButton.setText("安装");
                                        detailrightButton.setText("评价");
                                    } else if (orderstate.equals("7")) {
                                        detailleftButton.setVisibility(View.INVISIBLE);
                                        detailsmiddleButton.setVisibility(View.INVISIBLE);
                                        detailrightButton.setText("已取消");
                                    } else if (orderstate.equals("3")) {
                                        detailleftButton.setVisibility(View.INVISIBLE);
                                        detailsmiddleButton.setVisibility(View.INVISIBLE);
                                        detailrightButton.setText("取消订单");
                                    } else if (orderstate.equals("1")) {
                                        detailleftButton.setVisibility(View.VISIBLE);
                                        detailsmiddleButton.setVisibility(View.VISIBLE);
                                        detailleftButton.setText("售后");
                                        detailsmiddleButton.setText("安装");
                                        detailrightButton.setText("已评价");
                                    }
                                    JSONArray jsonarry1 = object.getJSONArray("products");
                                    if (jsonarry1.length() >= 1) {
                                        for (int k = 0; k < jsonarry1.length(); k++) {
                                            JSONObject jdonobject = jsonarry1.getJSONObject(k);
                                            picture.add(jdonobject.getString("product_pic"));
                                            title.add(jdonobject.getString("product_name"));
                                            smalltitle.add(jdonobject.getString("product_bn"));
                                            nowprice.add(jdonobject.getString("product_nowprice"));
                                            beforeprice.add(jdonobject.getString("product_price"));
                                            number.add(jdonobject.getString("num"));
                                        }
                                        listviewYuyue.setVisibility(View.VISIBLE);
                                        setadapter();
                                    } else {
                                        listviewYuyue.setVisibility(View.INVISIBLE);
                                        if (product.get(0).equals("null")) {
                                            noproduct.setText("您预约的产品:");

                                        } else {
                                            noproduct.setText("您预约的产品：" + product.get(0).toString());
                                        }
                                    }

                                }

                                appointdetailTv.setText(name.get(0).toString());
                                appointdetailTv1.setText(phone.get(0).toString());
                                if (address.get(0).equals("null")) {
                                    totalLy.setVisibility(View.INVISIBLE);
                                } else {
                                    totalLy.setVisibility(View.VISIBLE);
                                    Dingdanshutv2.setText(address.get(0).toString());
                                }
                                yuyueNumber.setText("订单编号:" + ordersn.get(0));
                                satteTv.setText(state.get(0).toString());
                                arriveTv.setText("您预约到店时间:" + time.get(0));
                                arriveTv1.setText("您预约的门店:" + mendian.get(0));
                            } else if (op.equals("6")) {
                                Toast.makeText(detailsActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("orderid", order_id);
                return map;
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

        queue.add(post);
    }

    private void setadapter() {
        ImageLoader loader = ((Myapp) detailsActivity.this.getApplication()).getLoader();
        adapter = new yuyedetailsadapter(detailsActivity.this, loader, picture, title, smalltitle, nowprice, beforeprice, number);
        listviewYuyue.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listviewYuyue);
    }

    private void setListViewHeightBasedOnChildren(ListView listviewYuyue) {
        ListAdapter listAdapter = listviewYuyue.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listviewYuyue);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listviewYuyue.getLayoutParams();
        params.height = totalHeight
                + (listviewYuyue.getDividerHeight() * (listAdapter.getCount() - 1));
        listviewYuyue.setLayoutParams(params);

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
