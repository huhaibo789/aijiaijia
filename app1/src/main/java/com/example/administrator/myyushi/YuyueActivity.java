package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.unionpay.UPPayAssistEx;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Alipay.AuthResult;
import Alipay.PayResult;
import Alipay.SignUtils;
import adapter.yuyueadapter;
import bean.Food;
import bean.gouwu2;
import bean.gouwu3;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.FileUtils10;
import utils.FileUtils11;
import utils.FileUtils12;
import utils.FileUtils13;
import utils.FileUtils14;
import utils.FileUtils15;
import utils.FileUtils16;
import utils.FileUtils17;
import utils.FileUtils19;
import utils.FileUtils2;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.FileUtils22;

public class YuyueActivity extends Activity implements Callback,Runnable {
    PercentRelativeLayout layout;
    public static final String LOG_TAG = "PayDemo";
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016080901725251";
    public TextView zhifuJiage;
    @Bind(R.id.return_iv)
    ImageView returnIv;
    @Bind(R.id.row_one_item_one)
    TextView rowOneItemOne;
    @Bind(R.id.dingdanly)
    LinearLayout dingdanly;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.zhifu_now)
    Button zhifuNow;
    private String order_notifyURL;
    private RequestQueue queue;
    private yuyueadapter yuadapter;
    private Handler handle = new Handler();
    private String tn1;
    public String id;
    public String result1;
    public String jj2;
    private List<gouwu2> gou2 = new ArrayList<>();
    private List<Food> food = new ArrayList<>();
    private List<gouwu3> goud = new ArrayList<>();
    private ArrayList<String> s1 = new ArrayList<>();
    private ArrayList<String> s2 = new ArrayList<>();
    private ArrayList<String> s3 = new ArrayList<>();
    private ArrayList<String> s4 = new ArrayList<>();
    private ArrayList<String> s5 = new ArrayList<>();
    private ArrayList<String> s6=new ArrayList<>();
    private ArrayList<String> s7=new ArrayList<>();
    private Context mContext = null;
    private int mGoodsIdx = 0;
    private Handler mHandler = null;
    private ProgressDialog mLoadingDialog = null;
    private  String PID;
    private  String TARGET_ID;   //收款号
    private  String RSA_PRIVATE;
    private String order_sn;
    private String order_body;
    private String order_subject;
    private  String order_payprice;
    private  String sn;
    public static final int PLUGIN_VALID = 0;
    public static final int PLUGIN_NOT_INSTALLED = -1;
    public static final int PLUGIN_NEED_UPGRADE = 2;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "00";
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";
    @SuppressLint("HandlerLeak")
    private Handler  mHandler1 = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(YuyueActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(YuyueActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(YuyueActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(YuyueActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            queue = Volley.newRequestQueue(YuyueActivity.this);
            String url = "https://api.aijiaijia.com/api_appointorder_updateandtopay";
            StringRequest post = new StringRequest(
                    StringRequest.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("hulala", "onResponse: " + response.toString());
                            String aa = response.toString().trim();
                            try {
                                JSONObject object = new JSONObject(aa);
                                JSONObject resposeobject = object.getJSONObject("responseJson");
                                String result = resposeobject.getString("op");
                                FileUtils22 file=new FileUtils22();
                                String pay1=file.readDataFromFile(mContext);
                                if(pay1.equals("1")){
                                    TARGET_ID=resposeobject.getString("seller");
                                    Log.i("uwdf", "onResponse: "+TARGET_ID);
                                    RSA_PRIVATE=resposeobject.getString("privatekey");
                                    Log.i("isne", "onResponse: "+RSA_PRIVATE);
                                    PID=resposeobject.getString("partner");
                                    Log.i("icsmf", "onResponse: "+PID);
                                    order_sn=resposeobject.getString("order_sn");
                                    order_payprice=resposeobject.getString("order_payprice");
                                    order_body=resposeobject.getString("order_body");
                                    order_subject=resposeobject.getString("order_subject");
                                    order_notifyURL=resposeobject.getString("order_notifyURL");
                                    // handleMessage1();
                                   pay();
                                }else {
                                    String tn1=resposeobject.getString("tn");
                                    UPPayAssistEx.startPay(YuyueActivity.this, null, null, tn1, mMode);
                                }
                                if (result.equals("11")) {
                                    Toast.makeText(YuyueActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("12")) {
                                    Toast.makeText(YuyueActivity.this, "下单错误", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("13")) {
                                    Toast.makeText(YuyueActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("14")) {
                                    Toast.makeText(YuyueActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("6")) {
                                    Toast.makeText(YuyueActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                                }
                                Log.i("xujimeng1", "onResponse: " + resposeobject);
                                JSONArray jsonarry = resposeobject.getJSONArray("list");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("shoucang2", "onErrorResponse: " + error.getMessage());
                        }
                    }
            ) {
                //通过重写此对象的getParams方法设置请求条件
                //将所有的请求条件存入返回值的map对象中
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("ordersn",sn);
                    FileUtils19 qq = new FileUtils19();
                    String shuju = qq.readDataFromFile(YuyueActivity.this);
                    Log.i("fdsfty", "getParams: " + shuju.toString());
                    if (shuju.equals("积分")) {
                        FileUtils15 gg = new FileUtils15();
                        String ss = gg.readDataFromFile(YuyueActivity.this);
                        map.put("postpoint", ss);
                    }
                    if (result1 != null) {
                        map.put("couponid", result1);
                    }
                    FileUtils22 file=new FileUtils22();
                    String pay=file.readDataFromFile(mContext);
                    if(pay.equals("1")){
                        map.put("payway", "1");
                    }else{
                        map.put("payway", "3");
                    }

                    FileUtils20 fi = new FileUtils20();
                    String fr = fi.readDataFromFile(YuyueActivity.this);
                    FileUtils21 fr1 = new FileUtils21();
                    String fr2 = fr1.readDataFromFile(YuyueActivity.this);
                    if (fr != "Null") {
                        map.put("invocie", fr);
                    }
                    if (fr2 != "Null") {

                        try {
                            String aa = URLEncoder.encode(fr2, "UTF-8");
                            map.put("memo", aa);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    map.put("frondevice", "2");
                    map.put("addressid", id);
                    Log.i("fsfggr", "getParams: " + map);
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
            Log.e(LOG_TAG, " " + v.getTag());
            mGoodsIdx = (Integer) v.getTag();

            mLoadingDialog = ProgressDialog.show(mContext, // context
                    "", // title
                    "正在努力加载中,请稍候...", // message
                    true); // 进度是否是不确定的，这只和创建进度条有关

            /*************************************************
             * 步骤1：从网络开始,获取交易流水号即TN
             ************************************************/
            new Thread(YuyueActivity.this).start();

        }
    };

    private void postdata(View v) {
        queue = Volley.newRequestQueue(YuyueActivity.this);
        String url = "https://api.aijiaijia.com/api_appointorder_updateandtopay";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("hulala", "onResponse: " + response.toString());
                        String aa = response.toString().trim();
                        try {
                            JSONObject object = new JSONObject(aa);
                            JSONObject resposeobject = object.getJSONObject("responseJson");
                            String result = resposeobject.getString("op");
                            FileUtils22 file=new FileUtils22();
                            String pay1=file.readDataFromFile(mContext);
                            if(pay1.equals("1")){
                                TARGET_ID=resposeobject.getString("seller");
                                Log.i("uwdf", "onResponse: "+TARGET_ID);
                                RSA_PRIVATE=resposeobject.getString("privatekey");
                                Log.i("isne", "onResponse: "+RSA_PRIVATE);
                                PID=resposeobject.getString("partner");
                                Log.i("icsmf", "onResponse: "+PID);
                                order_sn=resposeobject.getString("order_sn");
                                order_payprice=resposeobject.getString("order_payprice");
                                order_body=resposeobject.getString("order_body");
                                order_subject=resposeobject.getString("order_subject");
                                order_notifyURL=resposeobject.getString("order_notifyURL");
                                // handleMessage1();
                                pay();
                            }else {
                                String tn1=resposeobject.getString("tn");
                                UPPayAssistEx.startPay(YuyueActivity.this, null, null, tn1, mMode);
                            }
                            if (result.equals("11")) {
                                Toast.makeText(YuyueActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                            } else if (result.equals("12")) {
                                Toast.makeText(YuyueActivity.this, "下单错误", Toast.LENGTH_SHORT).show();
                            } else if (result.equals("13")) {
                                Toast.makeText(YuyueActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            } else if (result.equals("14")) {
                                Toast.makeText(YuyueActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            } else if (result.equals("6")) {
                                Toast.makeText(YuyueActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                            }
                            Log.i("xujimeng1", "onResponse: " + resposeobject);
                            JSONArray jsonarry = resposeobject.getJSONArray("list");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("shoucang2", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ordersn",sn);
                Log.i("ikdemd", "getParams: "+sn);
                FileUtils19 qq = new FileUtils19();
                String shuju = qq.readDataFromFile(YuyueActivity.this);
                if (shuju.equals("积分")) {
                    FileUtils15 gg = new FileUtils15();
                    String ss = gg.readDataFromFile(YuyueActivity.this);
                    map.put("postpoint", ss);
                }
                if (result1 != null) {
                    map.put("couponid", result1);
                }
                FileUtils22 file=new FileUtils22();
                String pay=file.readDataFromFile(mContext);
                if(pay.equals("1")){
                    map.put("payway", "1");
                }else{
                    map.put("payway", "3");
                }
                FileUtils20 fi = new FileUtils20();
                String fr = fi.readDataFromFile(YuyueActivity.this);
                FileUtils21 fr1 = new FileUtils21();
                String fr2 = fr1.readDataFromFile(YuyueActivity.this);
                if (fr != "Null") {
                    map.put("invocie", fr);
                }
                if (fr2 != "Null") {

                    try {
                        String aa = URLEncoder.encode(fr2, "UTF-8");
                        map.put("memo", aa);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                map.put("frondevice", "2");
                map.put("addressid", id);
                Log.i("fsfggr", "getParams: " + map);
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
        Log.e(LOG_TAG, " " + v.getTag());
        mGoodsIdx = (Integer) v.getTag();

        mLoadingDialog = ProgressDialog.show(mContext, // context
                "", // title
                "正在努力加载中,请稍候...", // message
                true); // 进度是否是不确定的，这只和创建进度条有关

        /*************************************************
         * 步骤1：从网络开始,获取交易流水号即TN
         ************************************************/
        new Thread(YuyueActivity.this).start();
    }


    private void pay() {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
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
                PayTask alipay = new PayTask(YuyueActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
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
        String orderInfo = "partner=" + "\"" + PID+ "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + TARGET_ID + "\"";

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


    public void doStartUnionPayPlugin(Activity activity, String tn,
                                      String mode) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mHandler = new Handler(this);
        setContentView(R.layout.activity_yuyue2);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        sn=intent.getStringExtra("ordersn");
        Log.i("idmeewew", "onCreate: "+sn);
        returnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zhifuJiage = (TextView) findViewById(R.id.zhifu_jiage);
        postshuju();
        zhifuNow.setTag(0);
        zhifuNow.setOnClickListener(mClickListener);
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setadapter();
                //setdata();
                //setlistener();
            }
        }, 1000);
    }
    private void setadapter() {
        ImageLoader loader = ((Myapp) YuyueActivity.this.getApplication()).getLoader();
       yuadapter = new yuyueadapter(YuyueActivity.this, loader);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        yuadapter.updatexin3(s4);
        yuadapter.updatexin4(s3);
        yuadapter.updatexin5(s5);
        yuadapter.updatexin6(s6);
        recycle.setAdapter(yuadapter);
    }
    private void postshuju() {
        Log.i("dfsjfe", "postshuju: " + goud.toString());
        queue = Volley.newRequestQueue(YuyueActivity.this);
        String url = "https://api.aijiaijia.com/api_comfirm_appointorder";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("shoucang3r", "onResponse: " + response);
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            String shipping_fee = resposeobject.getString("shipping_fee");
                            FileUtils14 yy = new FileUtils14();
                            yy.saveDataToFile(YuyueActivity.this, shipping_fee);
                            String pointset_cashratio = resposeobject.getString("pointset_cashratio");
                            FileUtils17 rr = new FileUtils17();
                            rr.saveDataToFile(YuyueActivity.this, pointset_cashratio);
                            String sum_point = resposeobject.getString("sum_point");
                            FileUtils15 gg = new FileUtils15();
                            gg.saveDataToFile(YuyueActivity.this, sum_point);
                            JSONObject object = resposeobject.getJSONObject("user_address");
                            id = object.getString("id");
                            String us_address = object.getString("us_address");
                            FileUtils10 ff = new FileUtils10();
                            ff.saveDataToFile(YuyueActivity.this, us_address);
                            String us_name = object.getString("us_name");
                            FileUtils11 ff1 = new FileUtils11();
                            ff1.saveDataToFile(YuyueActivity.this, us_name);
                            String us_phone = object.getString("us_phone");
                            FileUtils12 ff2 = new FileUtils12();
                            ff2.saveDataToFile(YuyueActivity.this, us_phone);
                            String us_pscsds = object.getString("us_pscsds");
                            FileUtils13 ff3 = new FileUtils13();
                            ff3.saveDataToFile(YuyueActivity.this, us_pscsds);
                            String us_zipcode = object.getString("us_zipcode");
                            String user_point = resposeobject.getString("user_point");
                            FileUtils16 gg1 = new FileUtils16();
                            gg1.saveDataToFile(YuyueActivity.this, user_point);
                            JSONArray jsonArry = resposeobject.getJSONArray("product_list");
                            s3.clear();
                            s4.clear();
                            s5.clear();
                            s6.clear();
                            for (int i = 0; i < jsonArry.length(); i++) {
                                JSONObject object1 = jsonArry.getJSONObject(i);
                                s3.add(object1.getString("product_max_point"));
                                s4.add(object1.getString("product_nowprice"));
                                s5.add(object1.getString("num"));
                                s6.add(object1.getString("product_pic"));
                                s7.add(object1.getString("product_price"));
                            }
                            float  num8= 0;
                            for (int i = 0; i <s6.size() ; i++) {
                                if(s4.get(i).equals("null")){
                                    Number num5= NumberFormat.getInstance().parse(s7.get(i));
                                    Number num4= NumberFormat.getInstance().parse(s5.get(i));
                                    float num1=num4.floatValue();
                                    float num7=num5.floatValue();
                                    float num9=num7*num1;
                                    num8=num8+num9;
                                }else {
                                    Number num5= NumberFormat.getInstance().parse(s4.get(i));
                                    Number num4= NumberFormat.getInstance().parse(s5.get(i));
                                    float num1=num4.floatValue();
                                    float num7=num5.floatValue();
                                    float num9=num7*num1;
                                    num8=num8+num9;
                                }
                            }
                            FileUtils2 yy1=new FileUtils2();
                            yy1.saveDataToFile(YuyueActivity.this,String.valueOf(num8));
                            Log.i("iddgg", "onResponse: "+num8);
                            if (result3.equals("1")) {
                                Toast.makeText(YuyueActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("6")) {
                                Toast.makeText(YuyueActivity.this, "未登录", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("shoucang2", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("ordersn",sn);
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
    public void updateTextView(TextView tv) {

    }
    @Override
    public boolean handleMessage(Message msg) {
        Log.e(LOG_TAG, " " + "" + msg.obj);
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            tn = (String) msg.obj;
            /*************************************************
             * 步骤2：通过银联工具类启动支付插件
             ************************************************/

            doStartUnionPayPlugin(this,tn
                    , mMode);
        }

        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 456) {
            if (data != null) {
                result1 = data.getStringExtra("result10");
                Log.i("fgsdgy", "onActivityResult: "+result1.toString());
                yuadapter.notifyDataSetChanged();

            }

        }
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        Log.i("sdsdw", "onActivityResult: "+str);
        if (str!=null&&str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, mMode);
                    if (ret) {
                        // 验证通过后，显示支付结果
                        msg = "支付成功！";
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "支付成功！";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            // builder.setCustomTitle();
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else if (str!=null&&str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            // builder.setCustomTitle();
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else if (str!=null&&str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            // builder.setCustomTitle();
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }


    }
    @Override
    public void run() {
        String tn = null;
        InputStream is;
        try {

            String url = TN_URL_01;

            URL myURL = new URL(url);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            tn =  baos.toString();;
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }
    int startpay(Activity act, String tn, int serverIdentifier) {
        return 0;
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

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
