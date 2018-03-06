package com.example.administrator.myyushi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.MyAdapterLeft;
import adapter.MyAdapterRight;
import adapter.MyAdapterSon;
import adapter.Newadapter;
import adapter.sanjisousuo;
import bean.Sousobean1;
import bean.newsanji;
import bean.newsanji1;
import request.LoadingDialog;
import util.Myapp;
import utils.FileUtils29;

public class NewsousuoActivity extends Activity implements View.OnClickListener,
        PopupWindow.OnDismissListener {
    private RequestQueue queue;
    private ArrayList<Sousobean1.ResponseJsonBean.ListBrandBean> brandbean = new ArrayList<>();
    private ArrayList<Sousobean1.ResponseJsonBean.ListBean> listbean1 = new ArrayList<>();
    private ArrayList<Sousobean1.ResponseJsonBean.ListStyleBean> stylebean = new ArrayList<>();
    private Newadapter adapter2;
    private ImageButton return_fanhui;
    private ImageButton informate;
    private TextView tishi_newsou;
    private ImageView icon1, icon2, icon3;
    private TextView fenlei, diqu, paixu;
    private LinearLayout ll_top;
    private RelativeLayout ll_fenlei, ll_diqu, ll_paixu;
    private ListView lv1, lv2,lv3, lv4;
    private LinearLayout ll2;
    private PopupWindow popLeft,popMid,popRight;
    private List<newsanji> fatlist;
    private List<newsanji1> sonlist,sonlist1,sonlist2,sonlist3,sonlist4;
    private List<newsanji1> right;
    private List<newsanji1> mid;
    private ImageView swith_on,swith_on1,swith_on2;
    private int screenWidth;
    private int screenHeight;
    private MyAdapterLeft adapterleft;
    private MyAdapterSon adapterleftson;
    private MyAdapterRight adapterRight;
    private int imaPosition;//选中的
    private PullToRefreshGridView gridview2;
    private JSONArray jsonarry;
    private ArrayList<String> name=new ArrayList<>();
    private ArrayList<String> brand=new ArrayList<>();
    private ArrayList<String> dsid=new ArrayList<>();
    private ArrayList<String> id1=new ArrayList<>();
    private Handler handle=new Handler();
    private ArrayList<String> picture=new ArrayList<>();
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> nowprice=new ArrayList<>();
    private ArrayList<String> beforeprice=new ArrayList<>();
    int  currentpage=1;
    private   String data;
    private  String aa="0";
    private  String bb="0";
    private String cc="0";
    String xinjieguo="2";
    private sanjisousuo adapter5;
    private  String result="品牌";
    private   String ss=null;
    private String ss1=null;
    private EditText searchsou;
    private  String etfind;
    private  String jieguo;
    String isflag=null;
    ImageView shoptu_iv;
    LoadingDialog dialog;
    String filename;
    private ArrayList<String> jihename=new ArrayList<>();
    private ArrayList<String> fenggename=new ArrayList<>();
    private ArrayList<String> userid=new ArrayList<>();
    private boolean falg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newsanji);
        FileUtils29 file=new FileUtils29();
        filename=file.readDataFromFile(NewsousuoActivity.this);
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        queue = Volley.newRequestQueue(this);
        searchsou= (EditText) findViewById(R.id.searchsou);
        intiView();
        brand.clear();
        jihename.clear();
        fenggename.clear();
        name.clear();
        dsid.clear();
        id1.clear();
        userid.clear();
        picture.clear();
        title.clear();
        nowprice.clear();
        beforeprice.clear();
        Intent intent=getIntent();
        ss=intent.getStringExtra("brandid");
        jieguo=intent.getStringExtra("useid");
        etfind=intent.getStringExtra("etfind");  //分类单品类型进入
        isflag=intent.getStringExtra("isflag");
        Log.i("halou", "onCreate: "+ss);
        Log.i("halou1", "onCreate: "+jieguo);
        Log.i("hula", "onCreate: "+etfind);
        Log.i("shamo", "onCreate: "+isflag);
        if(etfind!=null){
            searchsou.setText(etfind);
            postshuju1();
            postshuju();
        }else {
            if(jieguo!=null){
                searchsou.setText(jieguo);
                postshuju1();
                postshuju();
            }else {
                postshuju1();
                postshuju();
            }
        }

        initScreenWidth();
        intifatherlist();
        enableLoadMore();
        setListeners();
        setPullToRefreshStyle();
    }
    private void setPullToRefreshStyle() {
        //获取用于设置下拉刷新中显示内容的设置对象
        ILoadingLayout il = gridview2.getLoadingLayoutProxy();
        SimpleDateFormat format1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str=format1.format(curDate);
        //设置最底部的加载时间TextView上显示的文字
        il.setLastUpdatedLabel("最近更新："+str);
        //设置右侧显示的提示图片
        il.setLoadingDrawable(getResources().getDrawable(R.mipmap.ic_pulltorefresh_arrow));
        ;
        //设置下拉状态时的提示文字
        il.setPullLabel("下拉刷新");
        //设置正在刷新过程中的提示文字
        il.setRefreshingLabel("正在刷新");
        //设置松手提示文字
        il.setReleaseLabel("松开刷新");
    }
    private void setListeners() {
        searchsou.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) searchsou.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(NewsousuoActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索，进行自己要的操作...
                    etfind=searchsou.getText().toString();
                    userid.clear();
                    picture.clear();
                    title.clear();
                    nowprice.clear();
                    beforeprice.clear();
                   if(etfind!=null){
                       currentpage=1;
                       postshu();
                   }else {
                       //输入为空的状态
                       postshu();
                   }
                    return true;
                }
                return false;
            }
        });
        informate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(NewsousuoActivity.this,SearchActivity.class);
                startActivity(intent3);
            }
        });
        return_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uid=userid.get(position);
               // Intent intent=new Intent(NewsousuoActivity.this,Sousuo2Activity.class);
                Intent intent=new Intent(NewsousuoActivity.this,h5detailsActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        gridview2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                 currentpage=1;
                 postshuju1Down();

            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                currentpage = currentpage + 1;
                postshuju1Up();
             /*   handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // adapter5.notifyDataSetChanged();
                        if(falg==true){
                            Toast.makeText(NewsousuoActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                        }

                        gridview2.onRefreshComplete();
                    }
                },2000);*/
            }
        });
    }
    private void postshuju1Down() {
        gridview2.setVisibility(View.VISIBLE);
        tishi_newsou.setVisibility(View.GONE);
        shoptu_iv.setVisibility(View.GONE);
        queue = Volley.newRequestQueue(NewsousuoActivity.this);
        String url = "https://api.aijiaijia.com/api_products_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userid.clear();
                        picture.clear();
                        title.clear();
                        nowprice.clear();
                        beforeprice.clear();
                        String ss = response.toString().trim();
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            jsonarry = resposeobject.getJSONArray("list");
                            if(jsonarry.length()!=0){
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject jsonob = jsonarry.getJSONObject(i);
                                    String usid=jsonob.getString("id");
                                    userid.add(usid);
                                    String pic = jsonob.getString("product_pic");
                                    picture.add(pic);
                                    String name=jsonob.getString("product_name");
                                    title.add(name);
                                    String nowjiage=jsonob.getString("product_nowprice");
                                    nowprice.add(nowjiage);
                                    String beforejiage=jsonob.getString("product_price");
                                    beforeprice.add(beforejiage);
                                }
                                setadapter();
                                gridview2.onRefreshComplete();
                            }
                         /*   if(etfind!=null){
                                if(jsonarry.length()==0&&xinjieguo.equals("2")){
                                    gridview2.setVisibility(View.GONE);
                                    tishi_newsou.setVisibility(View.VISIBLE);
                                    shoptu_iv.setVisibility(View.VISIBLE);
                                    tishi_newsou.setText("暂无此商品信息");
                                }
                            }else if(etfind==null&&jsonarry.length()==0&&currentpage==1){
                                gridview2.setVisibility(View.GONE);
                                tishi_newsou.setVisibility(View.VISIBLE);
                                shoptu_iv.setVisibility(View.VISIBLE);
                                tishi_newsou.setText("暂无此商品信息");
                            }else {
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject jsonob = jsonarry.getJSONObject(i);
                                    String usid=jsonob.getString("id");
                                    userid.add(usid);
                                    String pic = jsonob.getString("product_pic");
                                    picture.add(pic);
                                    String name=jsonob.getString("product_name");
                                    title.add(name);
                                    String nowjiage=jsonob.getString("product_nowprice");
                                    nowprice.add(nowjiage);
                                    String beforejiage=jsonob.getString("product_price");
                                    beforeprice.add(beforejiage);
                                }
                                setadapter();
                                if(jsonarry.length()==0){
                                    falg=true;
                                }else {
                                    falg=false;
                                }
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gridview2.setVisibility(View.GONE);
                        tishi_newsou.setVisibility(View.VISIBLE);
                        tishi_newsou.setGravity(Gravity.CENTER);
                        tishi_newsou.setText("网络连接失败");
                        dialog.dismiss();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", String.valueOf(currentpage));
                if(etfind!=null){
                    try {
                        String str=new String(etfind.getBytes("utf-8"),"ISO-8859-1");
                        map.put("keyword",str);
                        aa="0";
                        bb="0";
                        ss=null;
                        ss1=null;
                        jieguo=null;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }else {
                    map.put("keyword","");
                }
                if(aa!=null&&!aa.equals("0")){
                    if(aa.equals("1")){
                        map.put("so","desc");
                    }else if(aa.equals("2")){
                        map.put("so","asc");
                    }
                    map.put("po","");

                }else {
                    map.put("so","");
                    if(bb!=null&&!bb.equals("0")){
                        if(bb.equals("1")){
                            map.put("po","desc");
                        }else if(bb.equals("2")){
                            map.put("po","asc");
                        }
                    }else {
                        map.put("po","");
                    }
                }
                if(ss!=null){
                    map.put("brandid",ss);
                    jieguo=null;
                }else {
                    map.put("brandid","");
                }
                if(ss1!=null){
                    map.put("styleid",ss1);
                    jieguo=null;
                }else {
                    map.put("styleid","");
                }
                try {
                    map.put("cityname",   URLEncoder.encode(filename,"utf-8")); //"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
              /*  if(jieguo!=null){
                    map.put("categoryid",jieguo);
                }else {
                    map.put("categoryid","");
                }*/
                Log.i("halou", "getParams: "+map.toString());
                return map;
            }
        };
        queue.add(post);
    }
    private void postshuju1Up() {
        gridview2.setVisibility(View.VISIBLE);
        tishi_newsou.setVisibility(View.GONE);
        shoptu_iv.setVisibility(View.GONE);
        queue = Volley.newRequestQueue(NewsousuoActivity.this);
        String url = "https://api.aijiaijia.com/api_products_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String ss = response.toString().trim();
                        Log.i("hasake", "onResponse: "+ss);
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            jsonarry = resposeobject.getJSONArray("list");
                            Log.i("zhuanyan1e", "onResponse: "+jsonarry.length());
                            if(jsonarry.length()!=0){
                                Log.i("hffde", "onResponse: "+etfind);
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject jsonob = jsonarry.getJSONObject(i);
                                    String usid=jsonob.getString("id");
                                    userid.add(usid);
                                    String pic = jsonob.getString("product_pic");
                                    picture.add(pic);
                                    String name=jsonob.getString("product_name");
                                    title.add(name);
                                    String nowjiage=jsonob.getString("product_nowprice");
                                    nowprice.add(nowjiage);
                                    String beforejiage=jsonob.getString("product_price");
                                    beforeprice.add(beforejiage);
                                }
                                adapter5.notifyDataSetChanged();
                                gridview2.onRefreshComplete();
                            }else{
                                Log.i("hffde1", "onResponse: "+etfind);
                                Toast.makeText(NewsousuoActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                                gridview2.onRefreshComplete();
                            }
                           /* Log.i("zhuanyan1e", "onResponse: "+jsonarry.length());
                            Log.i("hffde", "onResponse: "+etfind);
                            Log.i("hffde1", "onResponse: "+isflag);
                            if(etfind!=null&&isflag==null){
                                Log.i("hffde5", "onResponse: "+isflag);
                                if(jsonarry.length()==0&&xinjieguo.equals("2")){
                                    Log.i("hffde2", "onResponse: "+isflag);
                                    gridview2.setVisibility(View.GONE);
                                    tishi_newsou.setVisibility(View.VISIBLE);
                                    shoptu_iv.setVisibility(View.VISIBLE);
                                    tishi_newsou.setText("暂无此商品信息");
                                }
                            }else if(etfind==null&&jsonarry.length()==0&&currentpage==1){
                                Log.i("hffde3", "onResponse: "+isflag);
                                gridview2.setVisibility(View.GONE);
                                tishi_newsou.setVisibility(View.VISIBLE);
                                shoptu_iv.setVisibility(View.VISIBLE);
                                tishi_newsou.setText("暂无此商品信息");
                            }else {
                                Log.i("hffde4", "onResponse: "+isflag);
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject jsonob = jsonarry.getJSONObject(i);
                                    String usid=jsonob.getString("id");
                                    userid.add(usid);
                                    String pic = jsonob.getString("product_pic");
                                    picture.add(pic);
                                    String name=jsonob.getString("product_name");
                                    title.add(name);
                                    String nowjiage=jsonob.getString("product_nowprice");
                                    nowprice.add(nowjiage);
                                    String beforejiage=jsonob.getString("product_price");
                                    beforeprice.add(beforejiage);
                                }
                                Log.i("bhshs", "onResponse: "+"fdsse");
                                setadapter();
                                //adapter5.notifyDataSetChanged();
                                if(jsonarry.length()==0){
                                    falg=true;
                                }else {
                                    falg=false;
                                }
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gridview2.setVisibility(View.GONE);
                        tishi_newsou.setVisibility(View.VISIBLE);
                        tishi_newsou.setGravity(Gravity.CENTER);
                        tishi_newsou.setText("网络连接失败");
                        dialog.dismiss();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", String.valueOf(currentpage));
                if(etfind!=null){
                    try {
                        String str=new String(etfind.getBytes("utf-8"),"ISO-8859-1");
                        map.put("keyword",str);
                        aa="0";
                        bb="0";
                        ss=null;
                        ss1=null;
                        jieguo=null;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else {
                    map.put("keyword","");
                }
                if(aa!=null&&!aa.equals("0")){
                    if(aa.equals("1")){
                        map.put("so","desc");
                    }else if(aa.equals("2")){
                        map.put("so","asc");
                    }
                    map.put("po","");

                }else {
                    map.put("so","");
                    if(bb!=null&&!bb.equals("0")){
                        if(bb.equals("1")){
                            map.put("po","desc");
                        }else if(bb.equals("2")){
                            map.put("po","asc");
                        }
                    }else {
                        map.put("po","");
                    }
                }
                if(ss!=null){
                    map.put("brandid",ss);
                    jieguo=null;
                }else {
                    map.put("brandid","");
                }
                if(ss1!=null){
                    map.put("styleid",ss1);
                    jieguo=null;
                }else {
                    map.put("styleid","");
                }
                try {
                    map.put("cityname",   URLEncoder.encode(filename,"utf-8")); //"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
               /* if(jieguo!=null){
                    map.put("categoryid",jieguo);
                }else {
                    map.put("categoryid","");
                }*/
                Log.i("jaiyousa", "getParams: "+map);
                return map;
            }
        };
        queue.add(post);
    }
    private void postshu() {
        gridview2.setVisibility(View.VISIBLE);
        tishi_newsou.setVisibility(View.GONE);
        shoptu_iv.setVisibility(View.GONE);
        queue = Volley.newRequestQueue(NewsousuoActivity.this);
        String url = "https://api.aijiaijia.com/api_products_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String ss = response.toString().trim();
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            jsonarry = resposeobject.getJSONArray("list");
                            Log.i("fdddff4", "onResponse: "+jsonarry.length());
                            if(etfind!=null&&jsonarry.length()==0){
                                if(jsonarry.length()==0&&xinjieguo.equals("2")){
                                    gridview2.setVisibility(View.GONE);
                                    tishi_newsou.setVisibility(View.VISIBLE);
                                    shoptu_iv.setVisibility(View.VISIBLE);
                                    tishi_newsou.setText("换个关键词再试试");
                                }
                            }else {
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject jsonob = jsonarry.getJSONObject(i);
                                    String usid=jsonob.getString("id");
                                    userid.add(usid);
                                    String pic = jsonob.getString("product_pic");
                                    picture.add(pic);
                                    String name=jsonob.getString("product_name");
                                    title.add(name);
                                    String nowjiage=jsonob.getString("product_nowprice");
                                    nowprice.add(nowjiage);
                                    String beforejiage=jsonob.getString("product_price");
                                    beforeprice.add(beforejiage);
                                }
                                adapter5.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gridview2.setVisibility(View.GONE);
                        tishi_newsou.setVisibility(View.VISIBLE);
                        tishi_newsou.setGravity(Gravity.CENTER);
                        tishi_newsou.setText("网络连接失败");
                        dialog.dismiss();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", String.valueOf(currentpage));
                if(etfind!=null){
                    try {
                        String str=new String(etfind.getBytes("utf-8"),"ISO-8859-1");
                        map.put("keyword",str);
                        aa="0";
                        bb="0";
                        ss=null;
                        ss1=null;
                        jieguo=null;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else {
                    map.put("keyword","");
                }
                if(aa!=null&&!aa.equals("0")){
                    if(aa.equals("1")){
                        map.put("so","desc");
                    }else if(aa.equals("2")){
                        map.put("so","asc");
                    }
                    map.put("po","");
                }else {
                    map.put("so","");
                    if(bb!=null&&!bb.equals("0")){
                        if(bb.equals("1")){
                            map.put("po","desc");
                        }else if(bb.equals("2")){
                            map.put("po","asc");
                        }
                    }else {
                        map.put("po","");
                    }
                }
                if(ss!=null){
                    map.put("brandid",ss);
                    jieguo=null;
                }else {
                    map.put("brandid","");
                }
                if(ss1!=null){
                    map.put("styleid",ss1);
                    jieguo=null;
                }else {
                    map.put("styleid","");
                }
                if(jieguo!=null){
                    map.put("categoryid",jieguo);
                }else {
                    map.put("categoryid","");
                }
                try {
                    map.put("cityname",   URLEncoder.encode(filename,"utf-8")); //"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("fdddff3", "onEditorAction: "+map);
                return map;
            }
        };
        queue.add(post);
    }
    private void enableLoadMore() {
        gridview2.setMode(PullToRefreshBase.Mode.BOTH);
    }
    private void postshuju2(){
        gridview2.setVisibility(View.VISIBLE);
        tishi_newsou.setVisibility(View.GONE);
        shoptu_iv.setVisibility(View.GONE);
        queue = Volley.newRequestQueue(NewsousuoActivity.this);
        String url = "https://api.aijiaijia.com/api_products_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String ss = response.toString().trim();
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            jsonarry = resposeobject.getJSONArray("list");
                            if(etfind!=null){
                                if(jsonarry.length()==0&&xinjieguo.equals("2")){
                                    gridview2.setVisibility(View.GONE);
                                    tishi_newsou.setVisibility(View.VISIBLE);
                                    shoptu_iv.setVisibility(View.VISIBLE);
                                    tishi_newsou.setText("换个关键词再试试");
                                }
                            }
                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject jsonob = jsonarry.getJSONObject(i);
                                String usid=jsonob.getString("id");
                                userid.add(usid);
                                String pic = jsonob.getString("product_pic");
                                picture.add(pic);
                                String name=jsonob.getString("product_name");
                                title.add(name);
                                String nowjiage=jsonob.getString("product_nowprice");
                                nowprice.add(nowjiage);
                                String beforejiage=jsonob.getString("product_price");
                                beforeprice.add(beforejiage);
                            }
                            adapter5.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gridview2.setVisibility(View.GONE);
                        tishi_newsou.setVisibility(View.VISIBLE);
                        tishi_newsou.setGravity(Gravity.CENTER);
                        tishi_newsou.setText("网络连接失败");
                        dialog.dismiss();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", String.valueOf(currentpage));
                if(etfind!=null){
                    try {
                        String str=new String(etfind.getBytes("utf-8"),"ISO-8859-1");
                        map.put("keyword",str);
                        aa="0";
                        bb="0";
                        ss=null;
                        ss1=null;
                        jieguo=null;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else {
                    map.put("keyword","");
                }
                if(aa!=null&&!aa.equals("0")){
                    if(aa.equals("1")){
                        map.put("so","desc");
                    }else if(aa.equals("2")){
                        map.put("so","asc");
                    }
                    map.put("po","");
                }else {
                    map.put("so","");
                    if(bb!=null&&!bb.equals("0")){
                        if(bb.equals("1")){
                            map.put("po","desc");
                        }else if(bb.equals("2")){
                            map.put("po","asc");
                        }
                    }else {
                        map.put("po","");
                    }
                }

                if(ss!=null){
                    map.put("brandid",ss);
                    jieguo=null;
                }else {
                    map.put("brandid","");
                }
                if(ss1!=null){
                    map.put("styleid",ss1);
                    jieguo=null;
                }else {
                    map.put("styleid","");
                }
                if(jieguo!=null){
                    map.put("categoryid",jieguo);
                }else {
                    map.put("categoryid","");
                }
                try {
                    map.put("cityname",   URLEncoder.encode(filename,"utf-8")); //"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return map;
            }
        };
        queue.add(post);
    }
    private void postshuju1() {
        gridview2.setVisibility(View.VISIBLE);
        tishi_newsou.setVisibility(View.GONE);
        shoptu_iv.setVisibility(View.GONE);
        queue = Volley.newRequestQueue(NewsousuoActivity.this);
        String url = "https://api.aijiaijia.com/api_products_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String ss = response.toString().trim();
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            jsonarry = resposeobject.getJSONArray("list");
                            if(etfind!=null){
                                if(jsonarry.length()==0&&xinjieguo.equals("2")){
                                    gridview2.setVisibility(View.GONE);
                                    tishi_newsou.setVisibility(View.VISIBLE);
                                    shoptu_iv.setVisibility(View.VISIBLE);
                                    tishi_newsou.setText("换个关键词再试试");
                                }
                            }else if(etfind==null&&jsonarry.length()==0&&currentpage==1){
                                gridview2.setVisibility(View.GONE);
                                tishi_newsou.setVisibility(View.VISIBLE);
                                shoptu_iv.setVisibility(View.VISIBLE);
                                tishi_newsou.setText("换个关键词再试试");
                            }
                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject jsonob = jsonarry.getJSONObject(i);
                                String usid=jsonob.getString("id");
                                userid.add(usid);
                                String pic = jsonob.getString("product_pic");
                                picture.add(pic);
                                String name=jsonob.getString("product_name");
                                title.add(name);
                                String nowjiage=jsonob.getString("product_nowprice");
                                nowprice.add(nowjiage);
                                String beforejiage=jsonob.getString("product_price");
                                beforeprice.add(beforejiage);
                            }
                            if(jsonarry.length()!=0){
                                Log.i("ffe", "onResponse: "+"1");
                                setadapter();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gridview2.setVisibility(View.GONE);
                        tishi_newsou.setVisibility(View.VISIBLE);
                        tishi_newsou.setGravity(Gravity.CENTER);
                        tishi_newsou.setText("网络连接失败");
                        dialog.dismiss();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", String.valueOf(currentpage));
                if(etfind!=null){
                  /*  Pattern p = Pattern.compile("[0-9]*");
                    Matcher m = p.matcher(etfind);
                    if(m.matches() ){
                        Toast.makeText(NewsousuoActivity.this,"输入的是数字", Toast.LENGTH_SHORT).show();
                    }
                    p=Pattern.compile("[a-zA-Z]");
                    m=p.matcher(etfind);
                    if(m.matches()){
                        Toast.makeText(NewsousuoActivity.this,"输入的是字母", Toast.LENGTH_SHORT).show();
                    }
                    p=Pattern.compile("[\u4e00-\u9fa5]");
                    m=p.matcher(etfind);
                    if(m.matches()){
                        Toast.makeText(NewsousuoActivity.this,"输入的是汉字", Toast.LENGTH_SHORT).show();
                    }
                    p=Pattern.compile("[+]");
                    m=p.matcher(etfind);
                  if(m.matches()){
                     Toast.makeText(NewsousuoActivity.this,"输入的是加号", Toast.LENGTH_SHORT).show();
                  }
                    p=Pattern.compile("[-]");
                    m=p.matcher(etfind);
                    if(m.matches()){
                        Toast.makeText(NewsousuoActivity.this,"输入的是减号", Toast.LENGTH_SHORT).show();
                    }
                    p=Pattern.compile("[/]");
                    m=p.matcher(etfind);
                    if(m.matches()){
                        Toast.makeText(NewsousuoActivity.this,"输入的是/", Toast.LENGTH_SHORT).show();
                    }*/
                    try {
                        String str=new String(etfind.getBytes("utf-8"),"ISO-8859-1");
                        map.put("keyword",str);
                        aa="0";
                        bb="0";
                        ss=null;
                        ss1=null;
                        jieguo=null;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }else {
                    map.put("keyword","");
                }
                if(aa!=null&&!aa.equals("0")){
                    if(aa.equals("1")){
                        map.put("so","desc");
                    }else if(aa.equals("2")){
                        map.put("so","asc");
                    }
                    map.put("po","");

                }else {
                    map.put("so","");
                    if(bb!=null&&!bb.equals("0")){
                        if(bb.equals("1")){
                            map.put("po","desc");
                        }else if(bb.equals("2")){
                            map.put("po","asc");
                        }
                    }else {
                        map.put("po","");
                    }
                }
                if(ss!=null){
                    map.put("brandid",ss);
                    jieguo=null;
                }else {
                    map.put("brandid","");
                }
                if(ss1!=null){
                    map.put("styleid",ss1);
                    jieguo=null;
                }else {
                    map.put("styleid","");
                }
                try {
                    map.put("cityname",   URLEncoder.encode(filename,"utf-8")); //"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("heiyuo", "getParams: "+map);
                return map;
            }
        };
        queue.add(post);
    }
    private void postshuju() {
        gridview2.setVisibility(View.VISIBLE);
        tishi_newsou.setVisibility(View.GONE);
        shoptu_iv.setVisibility(View.GONE);
        name.clear();
        fenggename.clear();
        dsid.clear();
        jihename.clear();
        id1.clear();
        brand.clear();
        String url = "https://api.aijiaijia.com/api_products_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String ss = response.toString().trim();
                        if(response!=null){
                            dialog.dismiss();

                        }
                        try {
                            JSONObject jsonobject = new JSONObject(ss);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            jsonarry = resposeobject.getJSONArray("list_brand");
                            if(etfind!=null){
                                if(jsonarry.length()==0&&xinjieguo.equals("2")){
                                    gridview2.setVisibility(View.GONE);
                                    tishi_newsou.setVisibility(View.VISIBLE);
                                    shoptu_iv.setVisibility(View.VISIBLE);
                                    tishi_newsou.setText("换个关键词再试试");
                                }
                            }
                            JSONArray jsonarray1=resposeobject.getJSONArray("list_style");
                            Log.i("huhua", "onResponse: "+jsonarry.toString());
                            Log.i("huhua", "onResponse: "+jsonarry.length());
                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject jsonob = jsonarry.getJSONObject(i);
                                String pic = jsonob.getString("brand_name");
                                name.add(pic);
                                String styleid=jsonob.getString("brand_id");
                                dsid.add(styleid);
                                String styname=jsonob.getString("brand_name");
                                jihename.add(styname);
                            }
                            Log.i("suanlba", "onResponse: "+name.toString());
                            for (int j = 0; j <jsonarray1.length() ; j++) {
                                JSONObject jsonob = jsonarray1.getJSONObject(j);
                                String text=jsonob.getString("style_name");
                                brand.add(text);
                                String brandid=jsonob.getString("style_id");
                                id1.add(brandid);
                                String branname=jsonob.getString("style_name");
                                fenggename.add(branname);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gridview2.setVisibility(View.GONE);
                        tishi_newsou.setVisibility(View.VISIBLE);
                        tishi_newsou.setText("网络连接失败");
                        tishi_newsou.setGravity(Gravity.CENTER);
                        dialog.dismiss();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", "1");
                try {
                    map.put("cityname",   URLEncoder.encode(filename,"utf-8")); //"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return map;
            }
        };
        queue.add(post);
    }
    private void setadapter() {
        ImageLoader loader = ((Myapp) NewsousuoActivity.this.getApplication()).getLoader();
        adapter5 = new sanjisousuo(NewsousuoActivity.this, loader, picture,title,nowprice,beforeprice);
        gridview2.setAdapter(adapter5);
    }
    private void intiView() {
        informate= (ImageButton) findViewById(R.id.informate);
        return_fanhui= (ImageButton) findViewById(R.id.return_fanhui);
        gridview2= (PullToRefreshGridView) findViewById(R.id.gridview2);
        ll_top =(LinearLayout) findViewById(R.id.ll_layout);
        ll_fenlei = (RelativeLayout) findViewById(R.id.ll_quyu);
        ll_diqu = (RelativeLayout) findViewById(R.id.ll_jiage);
        ll_paixu = (RelativeLayout) findViewById(R.id.ll_huxing);
        tishi_newsou= (TextView) findViewById(R.id.tishi_newsou);
        shoptu_iv= (ImageView) findViewById(R.id.shoptu_iv);
        fenlei = (TextView) findViewById(R.id.fenlei);
        diqu  = (TextView) findViewById(R.id.diqu);
        paixu = (TextView) findViewById(R.id.paixu);
        ll_fenlei.setOnClickListener(this);
        ll_diqu.setOnClickListener(this);
        ll_paixu.setOnClickListener(this);
        swith_on= (ImageView) findViewById(R.id.swith_on);
        swith_on1= (ImageView) findViewById(R.id.swith_on1);
        swith_on2= (ImageView) findViewById(R.id.swith_on2);
    }

    /**初始化父类和子类*/
    private void intifatherlist() {
        fatlist = new ArrayList<newsanji>();
        sonlist = new ArrayList<newsanji1>();
        sonlist1 = new ArrayList<newsanji1>();
        sonlist2 = new ArrayList<newsanji1>();
        sonlist3 = new ArrayList<newsanji1>();
        sonlist4 = new ArrayList<newsanji1>();
        for (int i = 0; i <name.size() ; i++) {
            Log.i("huhse", "intifatherlist: "+name.size());
            newsanji1 s = new newsanji1();
            s.setName(name.get(i));
            sonlist.add(s);
        }
        for (int j = 0; j <brand.size() ; j++) {
            newsanji1 f = new newsanji1();
            f.setName(brand.get(j));
            sonlist1.add(f);
        }
        /**初始化父类*/
        newsanji sanji3=new newsanji();
        sanji3.setName("综合");
        newsanji sanji1 = new newsanji();
        sanji1.setName("品牌");
        newsanji sanji2 = new newsanji();
        sanji2.setName("风格");
        for (int i = 0; i <3; i++) {
            if (i==1) {

                sanji1.setSonList(sonlist);
            }
            if (i==2) {
                sanji2.setSonList(sonlist1);
            }
        }
        fatlist.add(sanji3);
        fatlist.add(sanji1);
        fatlist.add(sanji2);
    }
    /**
     * @Title: initScreenWidth
     * @Description: 查看自身的宽高
     * @author yimei
     * @return void 返回类型
     */
    private void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_quyu:
                Log.i("chaoyou", "onClick: "+"左");
                setBackgroundAlpha(0.5f);//设置屏幕透明度
                swith_on.setImageResource(R.drawable.upsory);
                getPopLeft();
                popLeft.showAsDropDown(ll_top);
                break;
            case R.id.ll_jiage:
                Log.i("chaoyou1", "onClick: "+"中间");
                setBackgroundAlpha(0.5f);//设置屏幕透明度
                swith_on1.setImageResource(R.drawable.upsory);
                getPopMid();
                popMid.showAsDropDown(ll_top);
                break;
            case R.id.ll_huxing:
                setBackgroundAlpha(0.5f);//设置屏幕透明度
                Log.i("chaoyou2", "onClick: "+"右");
                swith_on2.setImageResource(R.drawable.upsory);
                getPopRight();
                break;
        }
    }
    private void getPopMid() {
// 获取自定义布局文件pop.xml的视图
        View left_right = getLayoutInflater().inflate
                (R.layout.popmid, null, false);
        left_right.setFocusable(true); // 这个很重要
        left_right.setFocusableInTouchMode(true);

        // PopupWindow(布局，宽度，高度)
        popMid = new PopupWindow(left_right,screenWidth, screenHeight/4, true);
        popMid.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        popMid.setFocusable(true);
        // 重写onKeyListener,按返回键消失
        left_right.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popMid.dismiss();
                    swith_on1.setImageResource(R.drawable.downsory);
                    //为空的话就会重新构建不会保留
//					popLeft = null;
                    return true;
                }
                return false;
            }
        });
        // 设置动画效果
        //点击其他地方消失
        left_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popMid != null && popMid.isShowing()) {
                    popMid.dismiss();
                    swith_on1.setImageResource(R.drawable.downsory);
                }
                return false;
            }});

        // pop.xml视图里面的控件
        lv4 = (ListView) left_right.findViewById(R.id.lv4);
        mid = new ArrayList<newsanji1>();
        for (int i = 0; i < 4; i++) {
            newsanji1 s = new newsanji1();
            s.setId(i+"");
            if (i==0) {
                s.setName("综合销量");
            }
            if (i==1) {
                s.setName("销量从高到低");
            }
            if (i==2) {
                s.setName("销量从低到高");
            }
            mid.add(s);
        }
        adapterRight = new MyAdapterRight(NewsousuoActivity.this, mid);
        lv4.setAdapter(adapterRight);
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if(position==1){
                    cc="1";
                    aa="1";
                    etfind=null;
                    userid.clear();
                    picture.clear();
                    title.clear();
                    nowprice.clear();
                    beforeprice.clear();
                    postshuju2();
                }else if(position==2){
                    cc="2";
                    aa="2";
                    etfind=null;
                    userid.clear();
                    picture.clear();
                    title.clear();
                    nowprice.clear();
                    beforeprice.clear();
                    postshuju2();
                }else if(position==0){
                    cc="0";
                    aa="0";
                    etfind=null;
                    postshuju2();
                }
                adapterRight.setSelectItem(position);
                // 当没有下级时直接将信息设置textview中
                String name = mid.get(position).getName();
                setHeadText(2, name,0);
                setHeadText(3, "价格",0);
                popMid.dismiss();
                swith_on1.setImageResource(R.drawable.downsory);
            }
        });
    }
    @Override
    public void onDismiss() {

    }
    /***获取PopupWindow实例 .分类*/
    private void getPopLeft() {
        if (null != popLeft) {
            popLeft.dismiss();

            return ;
        } else {
            swith_on.setImageResource(R.drawable.upsory);
            //初始化分类弹窗
            initPopLeft();
        }
    }
    /***获取PopupWindow实例 .分类*/
    private void getPopRight() {
        swith_on2.setImageResource(R.drawable.upsory);
        //初始化分类弹窗
        initPopRight();

    }
    /**
     * 创建分类弹出PopupWindow
     */
    protected void initPopLeft() {
        // 获取自定义布局文件pop.xml的视图
        View left_view = getLayoutInflater().inflate
                (R.layout.popleft, null, false);
        left_view.setFocusable(true); // 这个很重要
        left_view.setFocusableInTouchMode(true);
        popLeft = new PopupWindow(left_view,screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popLeft.setFocusable(true);
        popLeft.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        // 重写onKeyListener,按返回键消失
        left_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("huyisod", "onKey: "+keyCode);
                    popLeft.dismiss();
                    swith_on.setImageResource(R.drawable.downsory);
                    //为空的话就会重新构建不会保留
                    return true;
                }
                return false;
            }
        });
        // 设置动画效果
        //点击其他地方消失
        left_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popLeft != null && popLeft.isShowing()) {
                    popLeft.dismiss();
                    swith_on.setImageResource(R.drawable.downsory);
                }
                return false;
            }});
        // pop.xml视图里面的控件
        lv1 = (ListView) left_view.findViewById(R.id.lv1);
        lv2 = (ListView) left_view.findViewById(R.id.lv2);
        Log.i("zhezhende", "initPopLeft: "+fatlist);
        adapterleft = new MyAdapterLeft(NewsousuoActivity.this, fatlist);
        lv1.setAdapter(adapterleft);
        //setBackgroundAlpha(0.5f);//设置屏幕透明度
        //listview的监听
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if(position==0) {
                    ss1=null;
                    ss=null;
                    aa="0";
                    bb="0";
                    etfind=null;
                    userid.clear();
                    picture.clear();
                    title.clear();
                    nowprice.clear();
                    beforeprice.clear();
                    postshuju2();
                    fenlei.setText("综合");
                    diqu.setText("销量");
                    paixu.setText("价格");
                    popLeft.dismiss();
                    swith_on.setImageResource(R.drawable.downsory);
                }else if(position==1){
                    result="品牌";
                    etfind=null;
                    adapterleft.setSelectItem(position);
                    imaPosition = position;
                    adapterleft.notifyDataSetChanged();
                    adapterleftson =new MyAdapterSon(NewsousuoActivity.this,
                            name);
                    lv2.setAdapter(adapterleftson);
                }else if(position==2){
                    result="风格";
                    etfind=null;
                    adapterleft.setSelectItem(position);
                    imaPosition = position;
                    adapterleft.notifyDataSetChanged();
                    adapterleftson =new MyAdapterSon(NewsousuoActivity.this,
                            brand);
                    lv2.setAdapter(adapterleftson);
                }

                //二维数组里面有元素
                Log.i("ytege1", "onItemClick: "+fatlist.get(position).getSonList());
                Log.i("okee", "onItemClick: "+name.toString());
                if (name!=null) {
                    Log.i("ytege", "onItemClick: "+fatlist.get(position).getSonList());
                    //不为空才显示
                    lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            if(result.equals("品牌")){
                                ss=dsid.get(position).toString();
                                etfind=null;
                                fenlei.setText(jihename.get(position));
                                Log.i("idkdd", "onItemClick: "+ss);
                                Log.i("ikwmd", "onItemClick: "+dsid);
                                ss1=null;
                                userid.clear();
                                picture.clear();
                                title.clear();
                                nowprice.clear();
                                beforeprice.clear();
                                postshuju2();
                            }else if(result.equals("风格")){
                                ss=null;
                                etfind=null;
                                ss1=id1.get(position);
                                fenlei.setText(fenggename.get(position));
                                userid.clear();
                                picture.clear();
                                title.clear();
                                nowprice.clear();
                                beforeprice.clear();
                                postshuju2();
                            }
                            popLeft.dismiss();
                            swith_on.setImageResource(R.drawable.downsory);
                        }
                    });
                    //没元素
                } else {
                    // 当没有下级时直接将信息设置textview中
                    String name = fatlist.get(position).getName();
                    //第一个都是1
                    popLeft.dismiss();
                    swith_on.setImageResource(R.drawable.downsory);
                }

            }});

    }
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
    /**最右边的弹窗，排序*/
    protected void initPopRight() {
        // 获取自定义布局文件pop.xml的视图
        View left_right = getLayoutInflater().inflate
                (R.layout.popright, null, false);
        left_right.setFocusable(true); // 这个很重要
        left_right.setFocusableInTouchMode(true);


        // 重写onKeyListener,按返回键消失
        left_right.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popRight.dismiss();
                    swith_on2.setImageResource(R.drawable.downsory);
                    //为空的话就会重新构建不会保留
                    return true;
                }
                return false;
            }
        });
        // 设置动画效果
        //点击其他地方消失
        left_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popRight != null && popRight.isShowing()) {
                    popRight.dismiss();
                    swith_on2.setImageResource(R.drawable.downsory);
                }
                return false;
            }});
        // pop.xml视图里面的控件
        lv3 = (ListView) left_right.findViewById(R.id.lv3);
        right = new ArrayList<newsanji1>();
        for (int i = 0; i < 4; i++) {
            newsanji1 s = new newsanji1();
            s.setId(i+"");
            if (i==0) {
                s.setName("默认价格");
            }
            if (i==1) {
                s.setName("价格从高到低");
            }
            if (i==2) {
                s.setName("价格从低到高");
            }
            right.add(s);
        }
        adapterRight = new MyAdapterRight(NewsousuoActivity.this, right);
        lv3.setAdapter(adapterRight);
        // PopupWindow(布局，宽度，高度)
        popRight = new PopupWindow(left_right,screenWidth, screenHeight/4, true);
        popRight.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        popRight.setFocusable(true);
        popRight.showAsDropDown(ll_top);
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if(position==1){
                    cc="1";
                    bb="1";
                    aa="0";
                    etfind=null;
                    userid.clear();
                    picture.clear();
                    title.clear();
                    nowprice.clear();
                    beforeprice.clear();
                    postshuju2();
                }else if(position==2){
                    cc="2";
                    bb="2";
                    aa="0";
                    etfind=null;
                    userid.clear();
                    picture.clear();
                    title.clear();
                    nowprice.clear();
                    beforeprice.clear();
                    postshuju2();

                }else if(position==0){
                    cc="0";
                    bb="0";
                    aa="0";
                    etfind=null;
                    userid.clear();
                    picture.clear();
                    title.clear();
                    nowprice.clear();
                    beforeprice.clear();
                    postshuju2();
                }
                adapterRight.setSelectItem(position);
                // 当没有下级时直接将信息设置textview中
                String name = right.get(position).getName();
                setHeadText(3, name,0);
                setHeadText(2, "销量",0);
                popRight.dismiss();
                swith_on2.setImageResource(R.drawable.downsory);
            }
        });
    }
    private void setHeadText(int idx, String text,int image) {
        switch (idx) {
            case 1:
                fenlei.setText(text);
                icon1.setImageResource(image);
                break;
            case 2:
                diqu.setText(text);
                break;
            case 3:
                paixu.setText(text);
                break;
        }
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