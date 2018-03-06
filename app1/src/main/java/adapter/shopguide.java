package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.AllsaleActivity;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.YuyueActivity;
import com.example.administrator.myyushi.appointevaluteActivity;
import com.example.administrator.myyushi.sellordewuliuActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.FileUtils19;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.Fileutils18;

/**
 * Created by 胡海波 on 2016/10/29.
 */
public class shopguide extends BaseAdapter {
    private ImageLoader loader;
    private RequestQueue queue;
    private AllsaleActivity activity;
    private int lastPosition;
    private LayoutInflater inflater;
    private String id1;
    private ArrayList<String> shopguide=new ArrayList<>();
  private  ArrayList<String> pic=new ArrayList<>();
    private  ArrayList<String> mendian=new ArrayList<>();
    private  ArrayList<String> time=new ArrayList<>();
    private  ArrayList<String> zhaungtai=new ArrayList<>();
    private  ArrayList<String> nopic=new ArrayList<>();
    private ArrayList<String> orderid=new ArrayList<>();
    private ArrayList<String> orderstatus1=new ArrayList<>();
    private Context context;
    private   String order_appointshop;
    private   String order_sn;
    private   String order_statustext;
    private   String order_appointtime;
    private   String order_zuchecked;
    private   JSONArray jsonarry;
    private  JSONArray jsonarry1;
    private    String ss;
    private     JSONObject jsonObject;
    private  JSONObject resposeobject;
    private   String op;
    private   JSONObject object;
   private Handler hadle=new Handler();
    public   shopguide(AllsaleActivity context,ImageLoader loader,String ss,ArrayList<String> shopguide1,ArrayList<String> ordershop,ArrayList<String> ordertime,ArrayList<String> orderzuchecked,
                       ArrayList<String> ordersatates,ArrayList<String> orderid,ArrayList<String> orderstatus1){
        super();
        this.ss=ss;
        this.context=context;
        this.loader=loader;
        this.activity=context;
        this.shopguide=shopguide1;
        this.mendian=ordershop;
        this.time=ordertime;
        this.zhaungtai=ordersatates;
        this.nopic=orderzuchecked;
        this.orderid=orderid;
        this.orderstatus1=orderstatus1;
        Log.i("iemdcedde", "shopguide: "+orderstatus1);
    }

    @Override
    public int getCount() {
        return shopguide.size();
    }

    @Override
    public Object getItem(int position) {
        return  null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_guide, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.guide_tv = (TextView) convertView.findViewById(R.id.guide_tv);
            vh.guide_success = (TextView) convertView.findViewById(R.id.guide_success);
            vh.guide_yuyue = (TextView) convertView.findViewById(R.id.guide_yuyue);
            vh.guide_num = (TextView) convertView.findViewById(R.id.guide_num);
            vh.guide_place = (TextView) convertView.findViewById(R.id.guide_place);
            vh.guide_time = (TextView) convertView.findViewById(R.id.guide_time);
            vh.guide_iv = (ImageView) convertView.findViewById(R.id.guide_iv);
            vh.guide_iv1 = (ImageView) convertView.findViewById(R.id.guide_iv1);
            vh.guide_iv2 = (ImageView) convertView.findViewById(R.id.guide_iv2);
            vh.guide_iv3 = (ImageView) convertView.findViewById(R.id.guide_iv3);
            vh.guide_point= (ImageView) convertView.findViewById(R.id.guide_point);
            vh. guide_miss = (Button) convertView.findViewById(R.id. guide_miss);
            vh.left_button= (Button) convertView.findViewById(R.id.left_button);
            vh.middle_button= (Button) convertView.findViewById(R.id.middle_button);


        } else {
            vh = (ViewHold) convertView.getTag();
        }
      //  final ViewHold finalVh1 = information(vh);

        if(mendian.size()!=0){
            vh.guide_place.setText("预约展厅:"+mendian.get(position).toString() );
            Log.i("ifkrr", "getView: "+mendian.size());
        }
        if(time.size()!=0){
           vh.guide_time.setText("时间："+time.get(position));
        }

        if(orderstatus1.get(position).equals("21")){
            Log.i("sifdffd", "getView: "+orderstatus1.get(position));
            vh.left_button.setVisibility(View.INVISIBLE);
            vh.middle_button.setVisibility(View.INVISIBLE);
            vh.guide_miss.setText("取消预约");
        }else if(orderstatus1.get(position).equals("2")){
            vh.left_button.setVisibility(View.INVISIBLE);
            vh.middle_button.setVisibility(View.VISIBLE);
            vh.guide_miss.setText("付款");
            vh.middle_button.setText("取消预约");
        }else if(orderstatus1.get(position).equals("4")){
            vh.left_button.setVisibility(View.VISIBLE);
            vh.middle_button.setVisibility(View.VISIBLE);
            vh.left_button.setText("退换货");
            vh.middle_button.setText("查看物流");
            vh.guide_miss.setText("确认收货");

        }else if(orderstatus1.get(position).equals("5")){
            vh.left_button.setVisibility(View.VISIBLE);
            vh.middle_button.setVisibility(View.VISIBLE);
            vh.left_button.setText("售后");
            vh.middle_button.setText("安装");
            vh.guide_miss.setText("评价");
        }else if(orderstatus1.get(position).equals("7")){
            vh.left_button.setVisibility(View.INVISIBLE);
            vh.middle_button.setVisibility(View.INVISIBLE);
            vh.guide_miss.setText("已取消");
        }else if(orderstatus1.get(position).equals("3")){
            vh.left_button.setVisibility(View.INVISIBLE);
            vh.middle_button.setVisibility(View.INVISIBLE);
            vh.guide_miss.setText("取消订单");
        }else if(orderstatus1.get(position).equals("6")){
            vh.left_button.setVisibility(View.VISIBLE);
            vh.middle_button.setVisibility(View.VISIBLE);
            vh.left_button.setText("退换货");
            vh.middle_button.setText("查看物流");
            vh.guide_miss.setText("确认收货");

        }else if(orderstatus1.get(position).equals("1")){
            vh.left_button.setVisibility(View.VISIBLE);
            vh.middle_button.setVisibility(View.VISIBLE);
            vh.left_button.setText("售后");
            vh.middle_button.setText("安装");
            vh.guide_miss.setText("已评价");
        }
       /* String ss=vh.left_button.getText().toString();
        if(ss.equals("售后")){
            vh.left_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url="http://api.aijiaijia.com/api_appointorder_list?page=1";
                    String ww="导购订单";
                    String title = "                              在线客服";
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

        }*/
        if(zhaungtai.size()!=0){
           vh.guide_success.setText(zhaungtai.get(position));
            final ViewHold finalVh10 = vh;
            final ViewHold finalVh11 = vh;
            vh.guide_miss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ss= finalVh10.guide_miss.getText().toString();
                    if(ss.equals("取消预约")){
                        postshuju(position);
                    }else if(ss.equals("付款")){
                        String ss3 = "请选择";
                        String ss1 = "Null";
                        String ss2 = "Null";
                        String aa = "没有使用积分";
                        FileUtils19 qq = new FileUtils19();
                        qq.saveDataToFile(context, aa);
                        FileUtils21 kk1 = new FileUtils21();
                        kk1.saveDataToFile(context, ss2);
                        FileUtils20 kk = new FileUtils20();
                        kk.saveDataToFile(context, ss1);
                        Fileutils18 gg = new Fileutils18();
                        gg.saveDataToFile(context, ss3);
                        Intent intent=new Intent(context,YuyueActivity.class);
                        String data=shopguide.get(position);
                        intent.putExtra("ordersn",data);
                        Log.i("iefcmed", "onClick: "+data.toString());
                        context.startActivity(intent);
                      //  postshuju1(position);
                    }else if(ss.equals("确认收货")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        // 链式编程
                        builder.setTitle("提示")
                                .setMessage("是否确认收货")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        postdata(position);
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


                    }else if(ss.equals("取消订单")){
                         postquxiao(position);
                    }else if(ss.equals("评价")){

                        Intent intent=new Intent(context,appointevaluteActivity.class);
                        intent.putExtra("orderid",orderid.get(position));
                        context.startActivity(intent);
                    }else if(ss.equals("申请退款")){
                        finalVh11.guide_miss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url="https://api.aijiaijia.com/api_appointorder_list?page=1";
                                String ww="导购订单";
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
            });
           /* vh.left_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data=finalVh10.left_button.getText().toString();
                    if(data.equals("退换货")){
                        postdata1(position);
                    }
                }
            });*/
          /*  vh.middle_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data1=finalVh10.middle_button.getText().toString();
                    if(data1.equals("取消预约")){

                    }else if(data1.equals("查看物流")){

                    }
                }
            });*/

        }
        if(shopguide.size()!=0){
          vh.guide_tv.setText("订单编号:"+shopguide.get(position));
        }

        if(nopic.size()!=0&&!nopic.get(position).toString().equals("null")){
            Log.i("oierf", "getView: "+nopic.get(position));
            vh.guide_yuyue.setVisibility(View.VISIBLE);
            if(nopic.size()!=0){
               vh.guide_yuyue.setText("预约产品:"+nopic.get(position));
                vh.guide_iv.setVisibility(View.INVISIBLE);
               vh.guide_iv1.setVisibility(View.INVISIBLE);
              vh.guide_iv2.setVisibility(View.INVISIBLE);
               vh.guide_iv3.setVisibility(View.INVISIBLE);
             vh.guide_num.setVisibility(View.INVISIBLE);
             vh.guide_point.setVisibility(View.INVISIBLE);
            }
        }else {
            if (ss != null) {
                try {
                    JSONObject jsonobject1 = new JSONObject(ss);
                    JSONObject responseobject1 = jsonobject1.getJSONObject("responseJson");
                    String op=responseobject1.getString("op");
                    if (op.equals("1")) {
                        JSONArray jsonarry1 = responseobject1.getJSONArray("sellorders");
                        JSONObject jsonproduct1 = jsonarry1.getJSONObject(position);
                        JSONArray jsonarry3 = jsonproduct1.getJSONArray("products");
                        Log.i("dfffff", "getView: "+jsonarry3.length());
                        Log.i("hgfhf", "getView: "+jsonarry3.toString());
                        Log.i("ienfkw", "getView: "+"来了");
                        if (jsonarry3.length() == 0) {
                            vh.guide_iv.setVisibility(View.INVISIBLE);
                            vh.guide_iv1.setVisibility(View.INVISIBLE);
                            vh.guide_iv2.setVisibility(View.INVISIBLE);
                            vh.guide_iv3.setVisibility(View.INVISIBLE);
                            vh.guide_num.setVisibility(View.INVISIBLE);
                            vh.guide_point.setVisibility(View.INVISIBLE);
                            vh.guide_yuyue.setText(" ");
                        } else if (jsonarry3.length() == 1) {
                            pic.clear();
                            for (int i = 0; i < jsonarry3.length(); i++) {
                                JSONObject productobject = jsonarry3.getJSONObject(i);
                                String wangzhi = productobject.getString("product_pic");
                                pic.add(wangzhi);
                            }
                            vh.guide_iv.setVisibility(View.VISIBLE);
                            vh.guide_iv1.setVisibility(View.INVISIBLE);
                            vh.guide_iv2.setVisibility(View.INVISIBLE);
                            vh.guide_iv3.setVisibility(View.INVISIBLE);
                            vh.guide_num.setVisibility(View.VISIBLE);
                            vh.guide_num.setText("共" + jsonarry3.length() + "件");
                            vh.guide_yuyue.setText("预约产品:");
                            final ViewHold finalVh = vh;
                            loader.loadImage(pic.get(0), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh.guide_iv.setImageBitmap(loadedImage);
                                }
                            });

                        } else if (jsonarry3.length() == 2) {
                            pic.clear();
                            for (int i = 0; i < jsonarry3.length(); i++) {
                                JSONObject productobject = jsonarry3.getJSONObject(i);
                                String wangzhi = productobject.getString("product_pic");
                                pic.add(wangzhi);
                            }
                            vh.guide_iv.setVisibility(View.VISIBLE);
                            vh.guide_iv1.setVisibility(View.VISIBLE);
                            vh.guide_iv2.setVisibility(View.INVISIBLE);
                            vh.guide_iv3.setVisibility(View.INVISIBLE);
                            vh.guide_num.setVisibility(View.VISIBLE);
                            vh.guide_num.setText("共" + jsonarry3.length() + "件");
                            vh.guide_yuyue.setText("预约产品:");
                            final ViewHold finalVh1 = vh;
                            loader.loadImage(pic.get(0), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh1.guide_iv.setImageBitmap(loadedImage);
                                }
                            });
                            final ViewHold finalVh2 = vh;
                            loader.loadImage(pic.get(1), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh2.guide_iv1.setImageBitmap(loadedImage);
                                }
                            });
                        } else if (jsonarry3.length() == 3) {
                            pic.clear();
                            for (int i = 0; i < jsonarry3.length(); i++) {
                                JSONObject productobject = jsonarry3.getJSONObject(i);
                                String wangzhi = productobject.getString("product_pic");
                                pic.add(wangzhi);
                            }
                            vh.guide_iv.setVisibility(View.VISIBLE);
                            vh.guide_iv1.setVisibility(View.VISIBLE);
                            vh.guide_iv2.setVisibility(View.VISIBLE);
                            vh.guide_iv3.setVisibility(View.INVISIBLE);
                            vh.guide_num.setVisibility(View.VISIBLE);
                            vh.guide_num.setText("共" + jsonarry3 + "件");
                            final ViewHold finalVh3 = vh;
                            loader.loadImage(pic.get(0), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh3.guide_iv.setImageBitmap(loadedImage);
                                }
                            });
                            final ViewHold finalVh4 = vh;
                            loader.loadImage(pic.get(1), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh4.guide_iv1.setImageBitmap(loadedImage);
                                }
                            });
                            final ViewHold finalVh5 = vh;
                            loader.loadImage(pic.get(2), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh5.guide_iv2.setImageBitmap(loadedImage);
                                }
                            });
                        } else if (jsonarry3.length() >= 4) {
                            pic.clear();
                            vh.guide_iv.setVisibility(View.VISIBLE);
                            vh.guide_iv1.setVisibility(View.VISIBLE);
                            vh.guide_iv2.setVisibility(View.VISIBLE);
                            vh.guide_iv3.setVisibility(View.VISIBLE);
                            vh.guide_num.setVisibility(View.VISIBLE);
                            vh.guide_num.setText("共" + jsonarry3.length() + "件");
                            final ViewHold finalVh6 = vh;
                            for (int i = 0; i < jsonarry3.length(); i++) {
                                JSONObject productobject = jsonarry3.getJSONObject(i);
                                String wangzhi = productobject.getString("product_pic");
                                pic.add(wangzhi);
                            }
                            loader.loadImage(pic.get(0), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh6.guide_iv.setImageBitmap(loadedImage);
                                }
                            });
                            final ViewHold finalVh7 = vh;
                            loader.loadImage(pic.get(1), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh7.guide_iv1.setImageBitmap(loadedImage);
                                }
                            });
                            final ViewHold finalVh8 = vh;
                            loader.loadImage(pic.get(2), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh8.guide_iv2.setImageBitmap(loadedImage);
                                }
                            });
                            final ViewHold finalVh9 = vh;
                            loader.loadImage(pic.get(3), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalVh9.guide_iv3.setImageBitmap(loadedImage);
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
          String data=vh.left_button.getText().toString();
          String data1=vh.middle_button.getText().toString();
        Log.i("idcddeee", "getView: "+data1);
        if(data.equals("售后")){
            vh.left_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url="https://api.aijiaijia.com/api_appointorder_list?page=1";
                    String ww="导购订单";
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

        }else if(data.equals("退换货")){
            vh.left_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url="https://api.aijiaijia.com/api_appointorder_list?page=1";
                    String ww="导购订单";
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
        if(data1.equals("安装")){
            vh.middle_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url="http://api.aijiaijia.com/api_appointorder_list?page=1";
                    String ww="导购订单";
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
        }else if(data1.equals("取消预约")){
            vh.middle_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postdata2(position);
                }
            });
        }else if(data1.equals("查看物流")){
            vh.middle_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,sellordewuliuActivity.class);
                    intent.putExtra("sn",shopguide.get(position));
                    context.startActivity(intent);
                    postdata3(position);
                }
            });

        }

        return convertView;

    }

    private void postdata3(final int position) {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_appointordertracedetail";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shopguide.clear();
                        Log.i("skfdvcor", "onResponse: post  success " + response);
                        ss = response.toString().trim();
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
              map.put("ordersn",shopguide.get(position));
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

    private void postquxiao(final int position) {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_appointorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shopguide.clear();
                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                        ss = response.toString().trim();
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
                map.put("orderid",orderid.get(position));
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
    private void postdata2(final int position) {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_appointorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shopguide.clear();
                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                        ss = response.toString().trim();
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
                map.put("orderid",orderid.get(position));
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

    private void postdata(final int position) {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_appointorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shopguide.clear();
                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                        ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(context, "评价成功", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("21")) {
                                Toast.makeText(context, "评价失败", Toast.LENGTH_SHORT).show();
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
                map.put("orderid",orderid.get(position));
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


    private void postshuju(final int position) {
        Log.i("ofdmedo", "postshuju: "+position);
        Log.i("isndfe", "postshuju: "+orderid.get(position));
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_appointorder_update";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shopguide.clear();
                        Log.i("gouwudasfsw", "onResponse: post  success " + response);
                        ss = response.toString().trim();
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
                map.put("orderid",orderid.get(position));
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


    public class ViewHold {

        TextView guide_tv,guide_success,guide_yuyue,guide_num,guide_place,guide_time;
        ImageView guide_iv,guide_iv1,guide_iv2,guide_iv3,guide_point;
        Button guide_miss,left_button,middle_button;
    }

}
