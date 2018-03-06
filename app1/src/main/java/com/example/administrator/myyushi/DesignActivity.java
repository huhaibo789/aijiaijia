package com.example.administrator.myyushi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.h5design;
import adapter.h5exercise;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import util.Myapp;

public class DesignActivity extends AppCompatActivity {

    @Bind(R.id.returnfanhui_rl)
    RelativeLayout returnfanhuiRl;
    @Bind(R.id.status_rl)
    RelativeLayout statusRl;
    @Bind(R.id.design_rcv)
    RecyclerView designRcv;
    ImageLoader loader;
    private h5design adapter;
    LoadingDialog dialog;
    private RequestQueue queue;
    ArrayList<String>  designordersn=new ArrayList<>(); //订单号
    ArrayList<String>  designorderpic=new ArrayList<>();//商品图片
    ArrayList<String>  designordertitle=new ArrayList<>();//商品标题
    ArrayList<String>  designorderprcie=new ArrayList<>();//商品价格
    ArrayList<String>  designorderpreviousprice=new ArrayList<>();//商品原价
    ArrayList<String>  designorderstyle=new ArrayList<>();//商品产品型号
    ArrayList<String>  designordernum=new ArrayList<>();//商品数量
    ArrayList<String>  designorderstatus=new ArrayList<>();//商品状态
    ArrayList<String>  designname=new ArrayList<>();//收货人姓名
    ArrayList<String>  designphone=new ArrayList<>();//收货人号码
    ArrayList<String>  designaddress=new ArrayList<>();//安装地址
    ArrayList<String>  designfreght=new ArrayList<>();//安装运费
    ArrayList<String>  designinstall=new ArrayList<>();//安装价格
    ArrayList<String>  designallsale=new ArrayList<>();//价格总计
    String information1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design2);
        ButterKnife.bind(this);
        dialog = new LoadingDialog(DesignActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        postdata();
        dialog.dismiss();
        setlistener();
        StatusBarCompat.setStatusBarColor(DesignActivity.this, Color.parseColor("#fbd23a"), true);
    }



    private void setlistener() {
        returnfanhuiRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void postdata() {
        designordersn.clear();
        designorderpic.clear();
        designordertitle.clear();
        designorderprcie.clear();
        designorderpreviousprice.clear();
        designorderstyle.clear();
        designordernum.clear();
        designorderstatus.clear();
        designname.clear();
        designphone.clear();
        designaddress.clear();
        designfreght.clear();
        designinstall.clear();
        designallsale.clear();
        queue = Volley.newRequestQueue(DesignActivity.this);
        String url = "https://api.aijiaijia.com/api_sellorder_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        information1 = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(information1);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                JSONArray   jsonarry = resposeobject.getJSONArray("sellorders");
                                //防止刷新过程中网络不佳出现无订单页面
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String order_sn = object.getString("order_sn");
                                    designordersn.add(order_sn);
                                    designname.add("张三");
                                    designphone.add("15150659189");
                                    designaddress.add("浙江省杭州市余杭区九华东路189号");
                                    designinstall.add("100");
                                    String order_statustext = object.getString("order_statustext");
                                    designorderstatus.add(order_statustext);
                                    String order_payprice = object.getString("order_payprice");
                                    designallsale.add(order_payprice);
                                    String order_shipfee = object.getString("order_shipfee");
                                    designfreght.add(order_shipfee);
                                    JSONArray objects = object.getJSONArray("products");
                                    for (int j = 0; j < objects.length(); j++) {
                                        JSONObject Object1 = objects.getJSONObject(j);
                                        String product_pic = Object1.getString("product_pic");
                                        designorderpic.add(product_pic);
                                        String product_name = Object1.getString("product_name");
                                        designordertitle.add(product_name);
                                        String product_bn1 = Object1.getString("product_bn");
                                        designorderstyle.add(product_bn1);
                                        String product_nowprice = Object1.getString("product_nowprice");
                                        designorderprcie.add(product_nowprice);
                                        String product_price = Object1.getString("product_price");
                                        designorderpreviousprice.add(product_price);
                                        String num = Object1.getString("num");
                                        designordernum.add(num);
                                    }
                                }
                                if(information1!=null){
                                    setadapter();
                                }



                            } else if (op.equals("6")) {
                                Toast.makeText(DesignActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                map.put("page", "1");
                map.put("s", "2");
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
        loader = ((Myapp) getApplication()).getLoader();
        adapter = new h5design(DesignActivity.this, loader,information1,designordersn, designorderpic, designordertitle, designorderprcie, designorderpreviousprice, designorderstyle, designordernum,designorderstatus
                ,designname,designphone,designaddress,designfreght,designinstall,designallsale);
        designRcv.setLayoutManager(new LinearLayoutManager(DesignActivity.this));
        designRcv.setAdapter(adapter);
    }
}
