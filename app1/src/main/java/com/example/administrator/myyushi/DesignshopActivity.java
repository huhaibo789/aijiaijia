package com.example.administrator.myyushi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ShoppingCartAdapter;
import bean.ShoppingCartBean;
import bean.gouwu;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import utils.DBHelper2;

public class DesignshopActivity extends AppCompatActivity implements View.OnClickListener, ShoppingCartAdapter.CheckInterface,ShoppingCartAdapter.ModifyCountInterface{

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.gouwu_rl)
    RelativeLayout gouwuRl;
    @Bind(R.id.top_bar)
    LinearLayout topBar;
    @Bind(R.id.check_box_single)
    CheckBox checkBoxSingle;
    @Bind(R.id.all_select)
    TextView allSelect;
    @Bind(R.id.subtitle)
    TextView subtitle;

    @Bind(R.id.designreceive)
    RelativeLayout designreceive;
    @Bind(R.id.designshop_view)
    View designshopView;
    @Bind(R.id.designshop_install)
    TextView designshopInstall;
    @Bind(R.id.designshop_freight)
    TextView designshopFreight;
    @Bind(R.id.designshop_all)
    TextView designshopAll;
    @Bind(R.id.designshop_share)
    Button designshopShare;
    @Bind(R.id.designshop_code)
    Button designshopCode;
    @Bind(R.id.bottom_bar)
    RelativeLayout bottomBar;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    public TextView allclear;
    private ShoppingCartAdapter shoppingCartAdapter;
    private RequestQueue queue;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private JSONArray jsonArray;
    LoadingDialog dialog;
    private TextView go_guang;
    private RelativeLayout wushouhuo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designshop);
        ButterKnife.bind(this);
        allclear= (TextView) findViewById(R.id.allclear);
        postdata();
        initdata();
    }

    private void postdata() {
        shoppingCartBeanList.clear();
        queue = Volley.newRequestQueue(DesignshopActivity.this);
        String url = "https://api.aijiaijia.com/api_shopcart";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("zhabisa", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("6")) {
                                Toast.makeText(DesignshopActivity.this, "请检查是否登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DesignshopActivity.this, LoginActivity.class);
                                startActivity(intent);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            jsonArray = resposeobject.getJSONArray("list");
                            if (jsonArray.length() == 0) {
                                dialog.dismiss();
                                setContentView(R.layout.activvity_wugouwu);
                                go_guang = (TextView) findViewById(R.id.go_guang);
                                wushouhuo= (RelativeLayout) findViewById(R.id.wushouhuo);
                                go_guang.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(DesignshopActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                wushouhuo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                ShoppingCartBean shoppingCartBean = new ShoppingCartBean();//数据集合
                                JSONObject object = jsonArray.getJSONObject(i);
                                String s1 = object.getString("product_pic");
                                shoppingCartBean.setImageUrl(s1); //网址
                                String s2 = object.getString("num");
                                shoppingCartBean.setCount(Integer.parseInt(s2));
                                String s3 = object.getString("product_name");
                                shoppingCartBean.setShoppingName(s3);
                                String s4 = object.getString("skuprice");
                                shoppingCartBean.setPrice(Double.parseDouble(s4));
                                String s5 = object.getString("skusellprice");

                                String serviceti = object.getString("stringtips");

                              /*  String s4 = object.getString("product_price");
                                logins4.add(s4);
                                String s5 = object.getString("product_nowprice");
                                logins5.add(s5);*/
                                String s6 = object.getString("product_bn");
                                shoppingCartBean.setShopnumber(s6);
                                String s7 = object.getString("pid");


                                dialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(DesignshopActivity.this, "网络不给力了", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        dialog.dismiss();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
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

    private void initdata() {
        for (int i = 0; i < 6; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setShoppingName("文章标题");
            shoppingCartBean.setShopnumber("纯棉");
            shoppingCartBean.setSkuname("标准");
            shoppingCartBean.setImageUrl("http:");
            shoppingCartBean.setPrice(60);
            shoppingCartBean.setCount(2);
            shoppingCartBeanList.add(shoppingCartBean);
            initview();
        }
    }

    private void initview() {
        shoppingCartAdapter = new ShoppingCartAdapter(DesignshopActivity.this);
        shoppingCartAdapter.setCheckInterface(DesignshopActivity.this);
        shoppingCartAdapter.setModifyCountInterface(DesignshopActivity.this);
        listview.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void checkGroup(int position, boolean isChecked) {

    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        currentCount++;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {

    }

    @Override
    public void childDelete(int position) {

    }
    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getCount();
            }
        }
        designshopAll.setText("合计:" + totalPrice);
        // tv_settlement.setText("结算(" + totalCount + ")");
    }
}
