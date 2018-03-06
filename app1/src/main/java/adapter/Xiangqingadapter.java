package adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.GouwuActivity1;
import com.example.administrator.myyushi.LoginActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Sousuo2Activity;
import com.example.administrator.myyushi.evaluateActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu;
import bean.gouwu2;
import bean.gouwu3;
import bean.xiangqingbean;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import utils.FileUtils;
import view.SearchPagerGaoview2;

/**
 * Created by 胡海波 on 2016/8/18.
 */
public class Xiangqingadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String platName="QQ";//极光推送
   private Sousuo2Activity act;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private Context context;
    private RequestQueue queue;
    private ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean>  xqbean=new ArrayList<>();
    private ArrayList<String> wuyu=new ArrayList<>();
    private ArrayList<String> detail=new ArrayList<>();
    private ArrayList<String> details1=new ArrayList<>();
    private ArrayList<String> detail2=new ArrayList<>();
    private ArrayList<String> detail3=new ArrayList<>();
    private  List<gouwu>  gou34=new ArrayList<>();
    private List<gouwu2> gou23=new ArrayList<>();
    private List<gouwu3> gouwu24=new ArrayList<>();
    private ArrayList<String> content1=new ArrayList<>();
    private ArrayList<String> time1=new ArrayList<>();
    private ArrayList<String> smalltitle=new ArrayList<>();
    private Handler handle=new Handler();
    private ListView myview;
    String aa="1";
    String hh="1";
    public Xiangqingadapter(Sousuo2Activity context, ImageLoader loader,ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean>  xqbean,ArrayList<String> wuyu, ArrayList<String> detail,ArrayList<String> details1,ArrayList<String> detail2,ArrayList<String> detail3){
        inflater=LayoutInflater.from(context);
        this.loader=loader;
        this.act=context;
        this.context=context;
        this.xqbean=xqbean;
        this.wuyu=wuyu;
        this.detail=detail;
        this.details1=details1;
        this.detail2=detail2;
        this.detail3=detail3;
    }


  /*  public void updatexqbean (ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean> xqbean){
        this.xqbean=xqbean;
    }
    public  void updateurl(ArrayList<String> wuyu){
        this.wuyu=wuyu;
    }
    public  void updatedetail(ArrayList<String> detail){
        this.detail=detail;
    }
    public  void updatedetail1(ArrayList<String> details1){
        this.details1=details1;
    }
    public  void updatedetail2(ArrayList<String> detail2){
        this.detail2=detail2;
    }
    public  void updetedetail3(ArrayList<String> detail3){
        this.detail3=detail3;
    }*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new mXiangqing(inflater.inflate(R.layout.search_more2,parent,false));
                break;
            case 1:
                holder=new mXiangqing1(inflater.inflate(R.layout.activity_xiangqing,parent,false));
                break;
            case 2:
                holder=new mXiangqing2(inflater.inflate(R.layout.activity_xiangqing1,parent,false));
                break;
            case 3:
                holder=new mXiangqing3(inflater.inflate(R.layout.activity_xiangqing2,parent,false));
                break;
            case 4:
                holder=new mXiangqing4(inflater.inflate(R.layout.activity_xiangqing3,parent,false));
                break;
          /*  case 5:
                holder=new mXiangqing5(inflater.inflate(R.layout.activity_xiangqing4,parent,false));
                break;*/

        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else if(position==1){
            return 1;
        }else if(position==2){
            return 2;
        }else if(position==3){
            return 3;
        }else {
            return 4;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         switch (getItemViewType(position)){
             case 0:
                    if(!hh.equals("2")){
                        final mXiangqing mxholder= (mXiangqing) holder;
                        if(xqbean!=null){
                            mxholder.gaoview2.updatePager1(wuyu,loader);
                        }
                    }
                 break;
             case 1:
                 final mXiangqing1 mxholder1= (mXiangqing1) holder;
                 postde(mxholder1);
                 for (int i = 0; i <xqbean.size() ; i++) {
                     String ss=xqbean.get(0).getProduct_name();
                     String hh2=String.valueOf( xqbean.get(0).getProduct_nowprice());
                     String hh4=String.valueOf(xqbean.get(0).getProduct_price());
                     mxholder1.xq_tv.setText(ss);
                     if(hh2.equals("0")||hh2.equals("null")){
                         mxholder1.xq_tv1.setText("￥"+hh4);
                         mxholder1.yuanjia_tv.setVisibility(View.GONE);
                     }else if(hh4.equals("0")||hh4.equals("null")){
                         mxholder1.xq_tv1.setText("￥"+hh2);
                         mxholder1.yuanjia_tv.setVisibility(View.GONE);
                     }else if(hh2.equals(hh4)){
                         mxholder1.xq_tv1.setText("￥"+hh2);
                         mxholder1.yuanjia_tv.setVisibility(View.GONE);
                     }else {
                         mxholder1.xq_tv1.setText("￥"+hh2);
                         mxholder1.yuanjia_tv.setText("原价:"+hh4);
                         mxholder1.yuanjia_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                     }
                      mxholder1.newgouwu.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              queue = Volley.newRequestQueue(context);
                              String url = "https://api.aijiaijia.com/api_userinfo";
                              StringRequest post = new StringRequest(
                                      StringRequest.Method.POST,
                                      url,
                                      new Response.Listener<String>() {
                                          @Override
                                          public void onResponse(String response) {
                                              String str = response.toString().trim();
                                              JSONObject jsonObject = null;
                                              try {
                                                  jsonObject = new JSONObject(str);
                                                  JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                                  String result3 = resposeobject.getString("op");
                                                  if (result3.equals("6")) {
                                                      Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
                                                     Intent intent = new Intent(context, LoginActivity.class);
                                                      ((Sousuo2Activity)context).startActivityForResult(intent,5);

                                                  } else {
                                                      Intent intent = new Intent(context, GouwuActivity1.class);
                                                      context.startActivity(intent);
                                                  }
                                              } catch (JSONException e) {
                                                  e.printStackTrace();
                                              }
                                          }
                                      },
                                      new Response.ErrorListener() {
                                          @Override
                                          public void onErrorResponse(VolleyError error) {
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
                      });

                      mxholder1.fenxiang.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                              showShare();
                          }
                      });
                     mxholder1.xq_iv1.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             final String id=String.valueOf(xqbean.get(0).getId());
                             queue = Volley.newRequestQueue(context);
                             String urls="https://api.aijiaijia.com/api_collection_add";
                             StringRequest post = new StringRequest(
                                     StringRequest.Method.POST,
                                     urls,
                                     new Response.Listener<String>() {
                                         @Override
                                         public void onResponse(String response) {
                                             String str=response.toString().trim();
                                             JSONObject jsonObject = null;
                                             try {
                                                 jsonObject = new JSONObject(str);
                                                 JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                                                 String result3 = resposeobject.getString("op");
                                                 if (result3.equals("0")) {
                                                     Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
                                                 } else if (result3.equals("1")) {
                                                     Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();

                                                 } else if (result3.equals("6")) {

                                                     Intent intent=new Intent(context,LoginActivity.class);
                                                     ((Sousuo2Activity)context).startActivityForResult(intent,5);
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
                                         }
                                     }
                             ) {
                                 //通过重写此对象的getParams方法设置请求条件
                                 //将所有的请求条件存入返回值的map对象中
                                 @Override
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String, String> map = new HashMap<>();
                                     map.put("pids",id);
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
                 }
                 break;
             case 2:
                 mXiangqing2 mxholder2= (mXiangqing2) holder;
                 for (int i = 0; i < xqbean.size(); i++) {
                     String hh3=String.valueOf(xqbean.get(0).getProduct_stock());
                     mxholder2.yaoqiu_tv.setText("库存:"+hh3+"件");
                 }
                 mxholder2.yaoqiu_tv1.setText("正品保证");
                 mxholder2.yaoqiu_tv2.setText("质保15年");
                 mxholder2.yaoqiu_tv3.setText("假一赔五");
                 break;
             case 3:
                 final mXiangqing3 mxholder3= (mXiangqing3) holder;
                 mxholder3.shangpin_tv.setText("商品评价");
                 postdata();
                 Log.i("yeheuie", "onBindViewHolder: "+content1.size());
                 handle.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         if(content1.size()==0){
                             mxholder3.goods_tv.setVisibility(View.GONE);
                             mxholder3.goods_tv1.setVisibility(View.GONE);
                             mxholder3.goods_tv2.setVisibility(View.GONE);
                             mxholder3.goods_tv3.setVisibility(View.GONE);
                             mxholder3.goods_tv4.setVisibility(View.GONE);
                             mxholder3.goods_tv5.setVisibility(View.GONE);
                         }else if(content1.size()==1){
                             mxholder3.goods_tv.setText(time1.get(0));
                             mxholder3.goods_tv1.setText(smalltitle.get(0));
                             mxholder3.goods_tv2.setText(content1.get(0));
                             mxholder3.goods_tv3.setVisibility(View.GONE);
                             mxholder3.goods_tv4.setVisibility(View.GONE);
                             mxholder3.goods_tv5.setVisibility(View.GONE);
                         }else{
                             mxholder3.goods_tv.setText(time1.get(0));
                             mxholder3.goods_tv1.setText(smalltitle.get(0));
                             mxholder3.goods_tv2.setText(content1.get(0));
                             mxholder3.goods_tv3.setText(time1.get(1));
                             mxholder3.goods_tv4.setText(smalltitle.get(1));
                             mxholder3.goods_tv5.setText(content1.get(1));
                         }


                     }
                 },500);
              /*   mxholder3.shop_iv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         final String id=String.valueOf(xqbean.get(0).getId());
                         queue = Volley.newRequestQueue(context);
                         Log.i("xiapeng7", "onClick: "+id);
                         String url="http://api.aijiaijia.com/api_shopcart_add";
                         StringRequest post = new StringRequest(
                                 StringRequest.Method.POST,
                                 url,
                                 new Response.Listener<String>() {
                                     @Override
                                     public void onResponse(String response) {
                                         Log.i("gouwu565", "onResponse: post  success " + response);
                                         String str=response.toString().trim();
                                         Log.i("dasf", "onResponse: "+str);
                                         JSONObject jsonObject = null;
                                         try {
                                             jsonObject = new JSONObject(str);
                                             JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                                             String result3 = resposeobject.getString("op");
                                             if( result3.equals("0")){
                                                 Toast.makeText(context, "加入失败", Toast.LENGTH_SHORT).show();
                                             }else if(result3.equals("1")){
                                                 Toast.makeText(context, "加入成功", Toast.LENGTH_SHORT).show();
                                                JSONArray jsonarry=resposeobject.getJSONArray("list");
                                             }else if(result3.equals("6")){
                                                 Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
                                                 Intent intent=new Intent(context,LoginActivity.class);
                                                 context.startActivity(intent);
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
                                 map.put("pids",id);
                                 map.put("nums", "1");

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
                 });*/
               /*  mxholder3.arrive_iv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         String ss6=String.valueOf(xqbean.get(0).getId());
                          Intent intent=new Intent(context, HasproductActivity.class);
                          intent.putExtra("id",ss6);
                          context.startActivity(intent);
                         if(context instanceof Activity){
                             Activity activity= (Activity) context;
                             activity.finish();
                         }

                     }
                 });*/
                 mxholder3.commodity_ly.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if(xqbean.size()!=0){
                             final String id=String.valueOf(xqbean.get(0).getId());
                             FileUtils fu=new FileUtils();
                             fu.saveDataToFile(context,id);
                             Intent intent1=new Intent(context,evaluateActivity.class);
                             context.startActivity(intent1);
                         }




                     }
                 });
              /*   mxholder3.buy_iv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         final String id=String.valueOf(xqbean.get(0).getId());
                         queue = Volley.newRequestQueue(context);
                         Log.i("xiapeng7", "onClick: "+id);
                         String url="http://api.aijiaijia.com/api_point";
                         StringRequest post = new StringRequest(
                                 StringRequest.Method.POST,
                                 url,
                                 new Response.Listener<String>() {
                                     @Override
                                     public void onResponse(String response) {
                                         Log.i("gouwu565", "onResponse: post  success " + response);
                                         String str=response.toString().trim();
                                         Log.i("dasf", "onResponse: "+str);
                                         JSONObject jsonObject = null;
                                         try {
                                             jsonObject = new JSONObject(str);
                                             JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                                             String result3 = resposeobject.getString("op");
                                             if( result3.equals("1")){
                                                 String dizhi1=wuyu.get(0).toString();
                                                 String ss3=xqbean.get(0).getProduct_name();
                                                 String ss4=String.valueOf( xqbean.get(0).getProduct_nowprice());
                                                 String ss5=String.valueOf(xqbean.get(0).getProduct_price());
                                                 String ss6=String.valueOf(xqbean.get(0).getId());
                                                 Log.i("eduee", "onClick: "+ss6);
                                                 DBHelper4 helper5=new DBHelper4(context);
                                                 gou23=helper5.queryAll();
                                                 for (int i = 0; i <gou23.size() ; i++) {
                                                     helper5.delete(gou23.get(i).get_id());

                                                 }
                                                 helper5.insert(new gouwu2(dizhi1,ss3,ss4,ss5,ss6));
                                                 DBHelper5 gelper6=new DBHelper5(context);
                                                 gouwu24=gelper6.queryAll();
                                                 if(gouwu24.size()!=0){
                                                     for (int i = 0; i <gouwu24.size() ; i++) {
                                                         gelper6.delete(gouwu24.get(i).get_id());
                                                     }
                                                 }
                                                 String ss="请选择";
                                                 String ss1="Null";
                                                 String ss2="Null";
                                                 String aa="没有使用积分";
                                                 FileUtils19 qq=new FileUtils19();
                                                 qq.saveDataToFile(context,aa);
                                                 FileUtils21 kk1=new FileUtils21();
                                                 kk1. saveDataToFile(context,ss2);
                                                 FileUtils20 kk=new FileUtils20();
                                                 kk. saveDataToFile(context,ss1)  ;
                                                 Fileutils18 gg=new  Fileutils18();
                                                 gg.saveDataToFile(context,ss);
                                                 Intent intent=new Intent(context, BaseActivity.class);
                                                 context.startActivity(intent);
                                             } else if(result3.equals("6")){
                                                 Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
                                                 Intent intent=new Intent(context,LoginActivity.class);
                                                 context.startActivity(intent);
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
                 });*/
                 break;
             case 4:
                  if(!aa.equals("2")){
                      final mXiangqing4  mxholder4= (mXiangqing4) holder;
                      final String[] tabArr = {"商品详情", "规格参数", "售后服务"};
                      // mxholder4.itemView.setFocusable(true);
                      mxholder4.layout.setSelectedTabIndicatorColor(Color.parseColor("#149885"));
                      mxholder4.layout.setTabTextColors(Color.BLACK, Color.BLACK);
                      mxholder4.layout.setTabMode(mxholder4.layout.MODE_FIXED);
                      for (int i = 0; i < tabArr.length; i++) {
                          TabLayout.Tab tab = mxholder4.layout.newTab()
                                  .setText(tabArr[i])
                                  .setTag(i);
                          mxholder4.layout.addTab(tab);
                      }
                      View   v_city2 = LayoutInflater.from(context).inflate(R.layout.acticity_web, null);
                      View  v_city3=LayoutInflater.from(context).inflate(R.layout.activity_web1,null);
                      final WebView  webview2= (WebView) v_city3.findViewById(R.id.webview2);
                      mxholder4.web_load.addView(v_city3);
                      mxholder4.web_load.addView(v_city2);
                      final WebView  webview3= (WebView) v_city2.findViewById(R.id.webview3);
                      webview3.setVisibility(View.VISIBLE);
                      webview2.setVisibility(View.GONE);
                      mxholder4.guige_lv1.setVisibility(View.GONE);
                      mxholder4.guige_lv2.setVisibility(View.GONE);
                      @SuppressLint("SetJavaScriptEnabled")
                      WebSettings webSettings =webview3.getSettings();
                      //设置WebView属性，能够执行Javascript脚本
                      webSettings.setJavaScriptEnabled(true);
                      //设置可以访问文件
                      webSettings.setAllowFileAccess(true);
                      //设置支持缩放
                      webSettings.setBuiltInZoomControls(true);
                      //加载需要显示的网页
                      webview3.loadUrl(detail.get(0).toString());
                      //设置Web视图
                      webview3.setWebViewClient(new webViewClient ());
                      //Web视图


                 /*mxholder4.webview3.setVisibility(View.VISIBLE);
                 @SuppressLint("SetJavaScriptEnabled")
                 WebSettings webSettings = mxholder4.webview3.getSettings();
                 //设置WebView属性，能够执行Javascript脚本
                 webSettings.setJavaScriptEnabled(true);
                 //设置可以访问文件
                 webSettings.setAllowFileAccess(true);
                 //设置支持缩放
                 webSettings.setBuiltInZoomControls(true);
                 //加载需要显示的网页
                 mxholder4.webview3.loadUrl(detail.get(0).toString());
                 Log.i("linglingwan", "onBindViewHolder: "+detail.get(0));
                 //设置Web视图
                 mxholder4.webview3.setWebViewClient(new webViewClient ());
                 //Web视图*/
                      mxholder4.layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                          @Override
                          public void onTabSelected(TabLayout.Tab tab) {

                              if(tab.getText().equals("商品详情")){
                           /*  View   v_city2 = LayoutInflater.from(context).inflate(R.layout.acticity_web, null);
                             mxholder4.web_load.addView(v_city2);
                             WebView  webview3= (WebView) v_city2.findViewById(R.id.webview3);*/
                                  webview3.setVisibility(View.VISIBLE);
                                  webview2.setVisibility(View.GONE);
                                  mxholder4.guige_lv1.setVisibility(View.GONE);
                                  mxholder4.guige_lv2.setVisibility(View.GONE);
                                  @SuppressLint("SetJavaScriptEnabled")
                                  WebSettings webSettings =webview3.getSettings();
                                  //设置WebView属性，能够执行Javascript脚本
                                  webSettings.setJavaScriptEnabled(true);
                                  //设置可以访问文件
                                  webSettings.setAllowFileAccess(true);
                                  //设置支持缩放
                                  webSettings.setBuiltInZoomControls(true);
                                  //加载需要显示的网页
                                  webview3.loadUrl(detail.get(0).toString());
                                  //设置Web视图
                                  webview3.setWebViewClient(new webViewClient ());
                                  //Web视图


                          /*   mxholder4.webview3.setVisibility(View.VISIBLE);
                             mxholder4.guige_lv1.setVisibility(View.GONE);
                             mxholder4.guige_lv2.setVisibility(View.GONE);
                             @SuppressLint("SetJavaScriptEnabled")
                             WebSettings webSettings = mxholder4.webview3.getSettings();
                             //设置WebView属性，能够执行Javascript脚本
                             webSettings.setJavaScriptEnabled(true);
                             //设置可以访问文件
                             webSettings.setAllowFileAccess(true);
                             //设置支持缩放
                             webSettings.setBuiltInZoomControls(true);
                             //加载需要显示的网页
                             mxholder4.webview3.loadUrl(detail.get(0).toString());
                             Log.i("linglingwan", "onBindViewHolder: "+detail.get(0));
                             //设置Web视图
                             mxholder4.webview3.setWebViewClient(new webViewClient ());
                             //Web视图*/

                              }else if(tab.getText().equals("售后服务")){



                             /*View   v_city2 = LayoutInflater.from(context).inflate(R.layout.acticity_web, null);
                             mxholder4.web_load.addView(v_city2);
                             WebView  webview3= (WebView) v_city2.findViewById(R.id.webview3);*/
                                  webview2.setVisibility(View.VISIBLE);
                                  webview3.setVisibility(View.GONE);
                                  mxholder4.guige_lv1.setVisibility(View.GONE);
                                  mxholder4.guige_lv2.setVisibility(View.GONE);
                                  @SuppressLint("SetJavaScriptEnabled")
                                  WebSettings webSettings =webview2.getSettings();
                                  //设置WebView属性，能够执行Javascript脚本
                                  webSettings.setJavaScriptEnabled(true);
                                  //设置可以访问文件
                                  webSettings.setAllowFileAccess(true);
                                  //设置支持缩放
                                  webSettings.setBuiltInZoomControls(true);
                                  //加载需要显示的网页
                                  webview2.loadUrl(details1.get(0).toString());
                                  Log.i("linglingwan", "onBindViewHolder: "+detail.get(0));
                                  //设置Web视图
                                  webview2.setWebViewClient(new webViewClient ());

                                  //Web视图
                            /* mxholder4.webview3.setVisibility(View.VISIBLE);
                             mxholder4.guige_lv1.setVisibility(View.GONE);
                             mxholder4.guige_lv2.setVisibility(View.GONE);
                             @SuppressLint("SetJavaScriptEnabled")
                             WebSettings webSettings = mxholder4.webview3.getSettings();
                             //设置WebView属性，能够执行Javascript脚本
                             webSettings.setJavaScriptEnabled(true);
                             //设置可以访问文件
                             webSettings.setAllowFileAccess(true);
                             //设置支持缩放
                             webSettings.setBuiltInZoomControls(true);
                             //加载需要显示的网页
                             mxholder4.webview3.loadUrl(details1.get(0).toString());
                             //设置Web视图
                             mxholder4.webview3.setWebViewClient(new webViewClient ());
                             //Web视图*/
                              }else {
                                  mxholder4.guige_lv1.setVisibility(View.VISIBLE);
                                  mxholder4.guige_lv2.setVisibility(View.VISIBLE);
                                  //  mxholder4.webview3.setVisibility(View.GONE);
                                  webview2.setVisibility(View.GONE);
                                  webview3.setVisibility(View.GONE);
                           /*  mylistview adapter=new mylistview(context);
                             mxholder4.guige_lv2.setAdapter(adapter);*/
                                  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, detail2);
                                  mxholder4.guige_lv2.setAdapter(adapter);
                                  ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, detail3);
                                  mxholder4.guige_lv1.setAdapter(adapter1);


                              }
                          }

                          @Override
                          public void onTabUnselected(TabLayout.Tab tab) {

                          }

                          @Override
                          public void onTabReselected(TabLayout.Tab tab) {

                          }
                      });
                  }


                   hh="2";
                  aa="2";
                 break;

         }
    }

    private void postde(final mXiangqing1 mxholder1) {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_sellorder_statistics";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                mxholder1.newtext_chart_num.setVisibility(View.VISIBLE);
                                String result4 = resposeobject.getString("shopcard_num");
                                if(!result4.equals("0")){
                                    mxholder1.newtext_chart_num.setBackgroundResource(R.drawable.bubblet);
                                    mxholder1.newtext_chart_num.setText(result4);
                                }else {
                                    mxholder1.newtext_chart_num.setVisibility(View.INVISIBLE);
                                }

                            } else {
                                mxholder1.newtext_chart_num.setVisibility(View.INVISIBLE);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
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


    private void showShare() {
            ShareSDK.initSDK(context);
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle(xqbean.get(0).getProduct_name());
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("https://api.aijiaijia.com/m/index.html?pid=" +xqbean.get(0).getId());
            // text是分享文本，所有平台都需要这个字段
            oks.setText("【有人@我】质量这么好的商品,快戳我看看吧!");
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
            oks.setImageUrl(wuyu.get(0));
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("https://api.aijiaijia.com/m/index.html?pid=" +xqbean.get(0).getId());
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment(xqbean.get(0).getProduct_name());
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(xqbean.get(0).getProduct_name());
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("https://api.aijiaijia.com/m/index.html?pid=" +xqbean.get(0).getId());
            // 启动分享GUI
            oks.show(context);
        }


    private void postdata() {

        queue = Volley.newRequestQueue(context);

        String url="https://api.aijiaijia.com/api_comments";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu565", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        Log.i("dasfdd", "onResponse: "+str);
                        try {
                            JSONObject jsonObject =new JSONObject(str);
                            JSONObject  responseobject=jsonObject.getJSONObject("responseJson");
                            JSONArray jsonarry=responseobject.getJSONArray("list");
                            for (int i = 0; i <jsonarry.length() ; i++) {
                                JSONObject  object=jsonarry.getJSONObject(i);
                                String  data=object.getString("content");
                                content1.add(data);
                                String data1=object.getString("showname");
                                time1.add(data1);
                                String data2=object.getString("time");
                               smalltitle.add(data2);
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
                if(xqbean.size()!=0){
                    String id2=String.valueOf(xqbean.get(0).getId());
                    if(id2!=null){
                        map.put("page", "1");
                        map.put("readpid",id2);
                    }
                }


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

    @Override
    public int getItemCount() {
        int count=xqbean==null?0:xqbean.size()+4;
        return count;
    }
    class mXiangqing  extends   RecyclerView.ViewHolder{
        SearchPagerGaoview2 gaoview2;
        private ImageView xingqing_finish,kefu_xiangqing,shop_xiangqing,share_iv1;

        public mXiangqing(View itemView) {
            super(itemView);
            gaoview2= (SearchPagerGaoview2) itemView.findViewById(R.id.searchmoregaoview2);
            xingqing_finish= (ImageView) itemView.findViewById(R.id.xingqing_finish);
            kefu_xiangqing= (ImageView) itemView.findViewById(R.id.kefu_xiangqing);
            shop_xiangqing= (ImageView) itemView.findViewById(R.id.shop_xiangqing);
            share_iv1= (ImageView) itemView.findViewById(R.id.share_iv1);
        }
    }
    class webViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (errorCode == -2) {
               act.setContentView(R.layout.activity_noconnect);
                WebView we = (WebView) act.findViewById(R.id.wv_gif);
                String gifPath = "file:///android_asset/gidf.gif";
                we.loadUrl(gifPath);
                Toast.makeText(context, "请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        }
    }

 class mXiangqing1 extends  RecyclerView.ViewHolder{
  private TextView xq_tv,xq_tv1,yuanjia_tv,newtext_chart_num;
     private ImageView xq_iv,xq_iv1,newgouwu,fenxiang;
     public mXiangqing1(View itemView) {
         super(itemView);
         yuanjia_tv = (TextView) itemView.findViewById(R.id.yuanjia_tv);
         xq_tv= (TextView) itemView.findViewById(R.id.xq_tv);
         xq_tv1= (TextView) itemView.findViewById(R.id.xq_tv1);
         xq_iv= (ImageView) itemView.findViewById(R.id.xq_iv);
         xq_iv1= (ImageView) itemView.findViewById(R.id.xq_iv1);
          fenxiang= (ImageView) itemView.findViewById(R.id.fenxiang);
         newgouwu= (ImageView) itemView.findViewById(R.id.newgouwu);
         newtext_chart_num= (TextView) itemView.findViewById(R.id.newtext_chart_num);
     }
 }
    class mXiangqing2 extends  RecyclerView.ViewHolder{
        private TextView yaoqiu_tv,yaoqiu_tv1,yaoqiu_tv2,yaoqiu_tv3;

        public mXiangqing2(View itemView) {
            super(itemView);
           yaoqiu_tv= (TextView) itemView.findViewById(R.id.yaoqiu_tv);
            yaoqiu_tv1= (TextView) itemView.findViewById(R.id.yaoqiu_tv1);
            yaoqiu_tv2= (TextView) itemView.findViewById(R.id.yaoqiu_tv2);
            yaoqiu_tv3= (TextView) itemView.findViewById(R.id.yaoqiu_tv3);


        }
    }
    class mXiangqing3 extends  RecyclerView.ViewHolder{
        private TextView shangpin_tv;
        private RadioButton arrive_iv,shop_iv;
        private Button buy_iv;
        private TextView goods_tv,goods_tv1,goods_tv2,goods_tv3,goods_tv4,goods_tv5;
        private RelativeLayout commodity_ly;

        private  ImageView shangpin_iv;
        public mXiangqing3(View itemView) {
            super(itemView);
            shangpin_tv= (TextView) itemView.findViewById(R.id.shangpin_tv);
            commodity_ly= (RelativeLayout) itemView.findViewById(R.id.commodity_ly);
            arrive_iv= (RadioButton) itemView.findViewById(R.id.arrive_iv);
            shop_iv= (RadioButton) itemView.findViewById(R.id.shop_iv);
            buy_iv= (Button) itemView.findViewById(R.id.buy_iv);
            goods_tv= (TextView) itemView.findViewById(R.id.goods_tv);
            goods_tv1= (TextView) itemView.findViewById(R.id.goods_tv1);
            goods_tv2= (TextView) itemView.findViewById(R.id.goods_tv2);
            goods_tv3= (TextView) itemView.findViewById(R.id.goods_tv3);
            goods_tv4= (TextView) itemView.findViewById(R.id.goods_tv4);
            goods_tv5= (TextView) itemView.findViewById(R.id.goods_tv5);



        }
    }
    class mXiangqing4 extends  RecyclerView.ViewHolder{
         private TabLayout layout;
     //  private WebView webview3;
        private LinearLayout web_load;
        private ListView guige_lv2,guige_lv1;
        public mXiangqing4(View itemView) {
            super(itemView);
            layout= (TabLayout) itemView.findViewById(R.id.layout9);
        //   webview3= (WebView) itemView.findViewById(R.id.webview3);
            guige_lv2= (ListView) itemView.findViewById(R.id.guige_lv2);
            guige_lv1= (ListView) itemView.findViewById(R.id.guige_lv1);
            web_load= (LinearLayout) itemView.findViewById(R.id.web_load);
           /* recommend_viewPager= (ViewPager) itemView.findViewById(R.id.recommend_viewPager);*/

        }
    }
  /*  class   mXiangqing5 extends  RecyclerView.ViewHolder{

        public mXiangqing5(View itemView) {
            super(itemView);

        }
    }*/
}
