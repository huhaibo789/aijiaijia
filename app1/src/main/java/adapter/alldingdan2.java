package adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.administrator.myyushi.AllsaleActivity;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.appointevaluteActivity;
import com.example.administrator.myyushi.sellordewuliuActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
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
import java.util.List;
import java.util.Map;

import Alipay.AuthResult;
import Alipay.PayResult;
import Alipay.SignUtils;

/**
 * Created by 胡海波 on 2016/11/2.
 */
public class alldingdan2 extends BaseAdapter {
    private ImageLoader loader;
    private String order_notifyURL;
    private Handler handle=new Handler();
    private  String RSA_PRIVATE;
    private String sell;
    private String partner;
    private String  order_sn;
    private String  order_subject;
    private String order_body;
    private RequestQueue queue;
    private AllsaleActivity activity;
    private int lastPosition;
    private LayoutInflater inflater;
    private String id1;
    private Context context;
    private  View v_city;
    private View v_city1;
    private View v_city2;
    private String data;
    private String tn;
    private  String order_payprice;
    private int i;
    private  boolean flag=true;
    private  ArrayList<String> postiton1=new ArrayList<>();
    public static final String APPID = "2016080901725251";
    private int s=0;
    //定义hashMap 用来存放之前创建的每一项item
    HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private  ArrayList<String> tupian=new ArrayList<>();
    private ArrayList<String> ordersn;
    private ArrayList<String> state;
    private ArrayList<String> price;
    private ArrayList<String> yunfei;
    private ArrayList<String> zhuangtai;
    private ArrayList<String> arry2 = new ArrayList<>();
    private ArrayList<String> array3 = new ArrayList<>();
    private ArrayList<String> array4 = new ArrayList<>();
    private ArrayList<String> array5 = new ArrayList<>();
    private ArrayList<String> array6 = new ArrayList<>();
    private ArrayList<String> array7 = new ArrayList<>();
    private ArrayList<String> orderid1;
    private ArrayList<String> payway;
    private  int aa=0;
    private final String mMode = "00";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
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
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    public   alldingdan2(AllsaleActivity context,ImageLoader loader,String data, ArrayList<String> ordersn,ArrayList<String> state,ArrayList<String> price,
                         ArrayList<String> yunfei, ArrayList<String> zhuangtai, ArrayList<String> orderid1,ArrayList<String> payway  ){
        super();
        this.context=context;
        this.loader=loader;
        this.activity=context;
        this.ordersn=ordersn;
        this.data=data;
        this.state=state;
        this.price=price;
        this.yunfei=yunfei;
        this.zhuangtai=zhuangtai;
        this.orderid1=orderid1;
        this.payway=payway;
    }
    public void setDatainfo(String details){
        this.data = details;
        Log.i("fenli", "setDatainfo: "+data);
    }

    @Override
    public int getCount() {
        Log.i("uejsdjis", "getCount: "+ordersn.size());

        return ordersn.size();




    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;

          /* if(convertView==null){
               convertView=View.inflate(context, R.layout.activity_normalalldingdan,null);
               vh=new ViewHold();
               convertView.setTag(vh);
               vh.linear_buju= (LinearLayout) convertView.findViewById(R.id.linear_buju);
           }else {
               vh= (ViewHold) convertView.getTag();
           }*/
        if (lmap.get(position) == null) {
            // convertView=View.inflate(context, R.layout.activity_normalalldingdan,null);
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_normalalldingdan, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            lmap.put(position, convertView);
            vh.linear_buju = (LinearLayout) convertView.findViewById(R.id.linear_buju);
            flag = true;
        } else {
            convertView = lmap.get(position);
            vh = (ViewHold) convertView.getTag();
            flag = false;
        }
      /*  for (int i = 0; i <postiton1.size() ; i++) {
           if(String.valueOf(position).equals(postiton1.get(i))) {
               flag=false;
           }else {
               flag=true;
           }
        }*/

        if(data!=null){
            if (flag) {
                JSONObject jsonobject = null;
                try {
                    Log.i("halousa", "getView: "+data);
                    jsonobject = new JSONObject(data);
                    JSONObject responseobject = jsonobject.getJSONObject("responseJson");
                    JSONArray jsonarry = responseobject.getJSONArray("sellorders");
                    for (int i = 0; i < jsonarry.length(); i++) {
                        JSONObject object = jsonarry.getJSONObject(i);
                        String result = object.getString("order_sn");

                        if (ordersn.get(position).equals(result)) {
                            v_city2 = LayoutInflater.from(context).inflate(R.layout.activity_appointyuyue3, null);
                            TextView appointyuyue_bianhao = (TextView) v_city2.findViewById(R.id.appointyuyue_bianhao);
                            TextView appoint_zhuangtai = (TextView) v_city2.findViewById(R.id.appoint_zhuangtai);
                            appointyuyue_bianhao.setText("订单编号:" + ordersn.get(position));
                            appoint_zhuangtai.setText(state.get(position));
                            vh.linear_buju.addView(v_city2);
                            JSONArray jsonarry1 = object.getJSONArray("products");
                            for (int h = 0; h < jsonarry1.length(); h++) {
                                v_city = LayoutInflater.from(context).inflate(R.layout.activity_normal1, null);
                                final ImageView gouwu_iv = (ImageView) v_city.findViewById(R.id.gouwu_iv);
                                TextView gouwu_tv = (TextView) v_city.findViewById(R.id.gouwu_tv);
                                TextView skutext= (TextView) v_city.findViewById(R.id.skutext);
                                TextView product_bn = (TextView) v_city.findViewById(R.id.product_bn);
                                TextView gouwu_tv1 = (TextView) v_city.findViewById(R.id.gouwu_tv1);
                                TextView gouwu_tv2 = (TextView) v_city.findViewById(R.id.gouwu_tv2);
                                TextView gouwu_shuliang = (TextView) v_city.findViewById(R.id.gouwu_shuliang);
                                JSONObject Object1 = jsonarry1.getJSONObject(h);
                                String product_pic = Object1.getString("product_pic");
                                imageLoader.loadImage(product_pic, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        super.onLoadingComplete(imageUri, view, loadedImage);
                                        gouwu_iv.setImageBitmap(loadedImage);
                                    }
                                });
                                String product_name = Object1.getString("product_name");
                                gouwu_tv.setText(product_name);
                                String skutext1=Object1.getString("skutext");
                                skutext.setText(skutext1);
                                String product_bn1 = Object1.getString("product_bn");
                                product_bn.setText(product_bn1);
                                String product_nowprice = Object1.getString("product_nowprice");
                                String product_price = Object1.getString("product_price");
                                if(product_nowprice.equals("0")||product_nowprice.equals("null")){
                                    gouwu_tv1.setText("¥"+product_price);
                                    gouwu_tv2.setVisibility(View.GONE);
                                }else if(product_price.equals("0")||product_price.equals("null")){
                                    gouwu_tv1.setText("¥"+product_nowprice);
                                    gouwu_tv2.setVisibility(View.GONE);
                                }else if(product_nowprice.equals(product_price)){
                                    gouwu_tv1.setText("¥"+product_nowprice);
                                    gouwu_tv2.setVisibility(View.GONE);
                                }else {
                                    gouwu_tv1.setText("¥"+product_nowprice);
                                    gouwu_tv2.setText("¥"+product_price);
                                    gouwu_tv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                }
                                String num = Object1.getString("num");
                                gouwu_shuliang.setText(num);
                                vh.linear_buju.addView(v_city);
                            }
                            v_city1 = LayoutInflater.from(context).inflate(R.layout.activitty_appointyuyue2, null);
                            TextView xinxi = (TextView) v_city1.findViewById(R.id.xinxi);
                            final Button addviewleft_button = (Button) v_city1.findViewById(R.id.addviewleft_button);
                            final Button addviewmiddle_button = (Button) v_city1.findViewById(R.id.addviewmiddle_button);
                            Button addviewright_button = (Button) v_city1.findViewById(R.id.addviewright_button);
                            xinxi.setText("共" + jsonarry1.length() + "件商品合计¥" + price.get(position) + "(含运费¥" + yunfei.get(position) + ")");
                            if (zhuangtai.get(position).equals("21")) {
                                addviewleft_button.setVisibility(View.INVISIBLE);
                                addviewmiddle_button.setVisibility(View.INVISIBLE);
                                addviewright_button.setText("取消预约");
                            } else if (zhuangtai.get(position).equals("2")) {
                                addviewleft_button.setVisibility(View.INVISIBLE);
                                addviewmiddle_button.setVisibility(View.VISIBLE);
                                addviewright_button.setText("付款");
                                addviewmiddle_button.setText("取消订单");
                            } else if (zhuangtai.get(position).equals("4")) {
                                addviewleft_button.setVisibility(View.VISIBLE);
                                addviewmiddle_button.setVisibility(View.VISIBLE);
                                addviewleft_button.setText("退换货");
                                addviewmiddle_button.setText("查看物流");
                                addviewright_button.setText("确认收货");

                            } else if (zhuangtai.get(position).equals("5")) {
                                addviewleft_button.setVisibility(View.VISIBLE);
                                addviewmiddle_button.setVisibility(View.VISIBLE);
                                addviewleft_button.setText("售后");
                                addviewmiddle_button.setText("安装");
                                addviewright_button.setText("评价");
                            } else if (zhuangtai.get(position).equals("7")) {
                                addviewleft_button.setVisibility(View.INVISIBLE);
                                addviewmiddle_button.setVisibility(View.INVISIBLE);
                                addviewright_button.setText("已取消");
                            } else if (zhuangtai.get(position).equals("3")) {
                                addviewleft_button.setVisibility(View.INVISIBLE);
                                addviewmiddle_button.setVisibility(View.INVISIBLE);
                                addviewright_button.setText("申请退款");
                            } else if (zhuangtai.get(position).equals("6")) {
                                addviewleft_button.setVisibility(View.VISIBLE);
                                addviewmiddle_button.setVisibility(View.VISIBLE);
                                addviewleft_button.setText("退换货");
                                addviewmiddle_button.setText("查看物流");
                                addviewright_button.setText("确认收货");

                            } else if (zhuangtai.get(position).equals("1")) {
                                addviewleft_button.setVisibility(View.VISIBLE);
                                addviewmiddle_button.setVisibility(View.VISIBLE);
                                addviewleft_button.setText("售后");
                                addviewmiddle_button.setText("安装");
                                addviewright_button.setText("已评价");
                            }
                            vh.linear_buju.addView(v_city1);
                            addviewleft_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String ss = addviewleft_button.getText().toString();
                                    if (ss.equals("售后")) {
                                        String url = ordersn.get(position);
                                        Log.i("urejd", "onClick: " + url);
                                        String ww = "导购订单";
                                        String title = "在线客服";
                                        // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                                        // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                                        ConsultSource source = new ConsultSource(url, ww, "custom information string");
                                        // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                                        Unicorn.openServiceActivity(context, // 上下文
                                                title, // 聊天窗口的标题
                                                source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                                        );
                                    } else if (ss.equals("退换货")) {

                                        String url = ordersn.get(position);
                                        String ww = "导购订单";
                                        String title = "在线客服";
                                        // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                                        // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                                        ConsultSource source = new ConsultSource(url, ww, "custom information string");
                                        // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                                        Unicorn.openServiceActivity(context, // 上下文
                                                title, // 聊天窗口的标题
                                                source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                                        );
                                    }
                                }
                            });
                            String ss1 = addviewmiddle_button.getText().toString();
                            String ss2 = addviewright_button.getText().toString();
                            if (ss1.equals("安装")) {
                                addviewmiddle_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = ordersn.get(position);
                                        String ww = "导购订单";
                                        String title = "在线客服";
                                        // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                                        // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                                        ConsultSource source = new ConsultSource(url, ww, "custom information string");
                                        // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                                        Unicorn.openServiceActivity(context, // 上下文
                                                title, // 聊天窗口的标题
                                                source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                                        );

                                    }
                                });
                            } else if (ss1.equals("取消订单")) {
                                addviewmiddle_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        postshuju(position);
                                    }
                                });

                            } else if (ss1.equals("查看物流")) {
                                addviewmiddle_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, sellordewuliuActivity.class);
                                        intent.putExtra("sn1", ordersn.get(position));
                                        context.startActivity(intent);
                                    }
                                });


                            }
                            Log.i("isdkss", "getView: " + ss2);
                            if (ss2.equals("付款") && payway.get(position).equals("支付宝")) {
                                addviewright_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        postshuju1(position);
                                        handle.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                setlistener();
                                            }
                                        }, 500);

                                    }
                                });


                            } else if (ss2.equals("付款") && payway.get(position).equals("银联支付")) {
                                Log.i("sffdf", "getView: " + position);
                                addviewright_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        postshuju2(position);
                                        handle.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i("dsff", "run: " + tn);
                                                UPPayAssistEx.startPay(context, null, null, tn, mMode);
                                            }
                                        }, 500);

                                    }
                                });

                            } else if (ss2.equals("评价")) {
                                addviewright_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, appointevaluteActivity.class);
                                        intent.putExtra("orderid1", orderid1.get(position));
                                        activity.startActivityForResult(intent,9);
                                    }
                                });

                            } else if (ss2.equals("确认收货")) {
                                final int finalPosition5 = position;
                                addviewright_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        // 链式编程
                                        builder.setTitle("提示")
                                                .setMessage("是否确认收货")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        queue = Volley.newRequestQueue(context);
                                                        String url = "https://api.aijiaijia.com/api_sellorder_update";
                                                        final StringRequest post = new StringRequest(
                                                                StringRequest.Method.POST,
                                                                url,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {

                                                                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                                                                        String ss = response.toString().trim();
                                                                        try {
                                                                            JSONObject jsonobject = new JSONObject(ss);
                                                                            JSONObject responsejson = jsonobject.getJSONObject("responseJson");
                                                                            String result = responsejson.getString("op");
                                                                            if (result.equals("1")) {
                                                                                Toast.makeText(context, "收货成功", Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                Toast.makeText(context, "收货失败", Toast.LENGTH_SHORT).show();
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
                                                                map.put("orderid", orderid1.get(finalPosition5));
                                                                map.put("s", "5");
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
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Toast.makeText(context, "你已取消", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .setCancelable(false); // 能否通过点击对话框以外的区域（包括返回键）关闭对话框
                                        // 通过建造者创建Dialog对象
                                        // AlertDialog dialog = builder.create();
                                        // dialog.show();
                                        // 以上两行代码可以简化成以下这一行代码
                                        builder.show();

                                    }
                                });

                            }else if(ss2.equals("申请退款")){
                                addviewright_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = ordersn.get(position);
                                        String ww = "导购订单";
                                        String title = "在线客服";
                                        // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                                        // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                                        ConsultSource source = new ConsultSource(url, ww, "custom information string");
                                        // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                                        Unicorn.openServiceActivity(context, // 上下文
                                                title, // 聊天窗口的标题
                                                source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                                        );

                                    }
                                });
                            }
                        }
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
                if (position == ordersn.size() - 1) {
                    flag = false;
                }

            }
        }


        return convertView;
    }




    private void postshuju(final int position) {
        queue = Volley.newRequestQueue(context);
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
                                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("21")) {
                                Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                            }else if(op.equals("6")){
                                Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
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
                Log.i("shenme", "getParams: "+"laile");
                map.put("orderid",orderid1.get(position));
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

    private void postshuju2(final int position) {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_sellorder_topay";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfswsdesq", "onResponse: post  success " + response);
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
                map.put("ordersn",ordersn.get(position));
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

    private void postshuju1(final int position) {
        queue = Volley.newRequestQueue(context);
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
                map.put("ordersn",ordersn.get(position));
                Log.i("iemda", "getParams: "+position+ordersn.get(position));
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

    private void setlistener() {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            if(context instanceof Activity){
                                Activity activity= (Activity) context;
                                activity.finish();
                            }
                       /*     finish();*/
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
                PayTask alipay = new PayTask(activity);
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
                PayTask alipay = new PayTask(activity);
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





    public class ViewHold {
        LinearLayout linear_buju;
        TextView sdd;
    }

}

