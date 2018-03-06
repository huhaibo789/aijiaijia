package h5Fragement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.LoginActivity;
import com.example.administrator.myyushi.NewsousuoActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.WebviewActivity;
import com.example.administrator.myyushi.h5detailsActivity;
import com.example.administrator.myyushi.updatepopwindow;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import adapter.FragmentAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.FileUtils24;
import utils.FileUtils26;
import utils.FileUtils30;
import utils.FileUtils31;
import utils.FileUtils32;
import utils.FileUtils33;
import utils.FileUtils37;
import utils.FileUtils6;
import utils.FileUtis36;

/**
 * Created by misshu on 2017/4/17/017.
 */
public class h5homepageFragement extends Fragment implements PopupWindow.OnDismissListener{
    @Bind(R.id.h5tabs)
    TabLayout h5tabs;
    @Bind(R.id.h5viewpager)
    ViewPager h5viewpager;
    @Bind(R.id.backed)
    LinearLayout backed;
    private RequestQueue queue;
    private String message;
    private Context context;
    String phone;
    String password;
    ImageLoader loader;
    private String result1;
    public updatepopwindow popWindow;
    PopupWindow pop;
    public AMapLocationClientOption mLocationOption = null;   //声明mLocationOption对象，定位参数
    private AMapLocationClient mLocationClient = null;    //声明AMapLocationClient类对象，定位发起端
    private View v_city;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = ((Myapp) getActivity().getApplication()).getLoader();
        popWindow = new updatepopwindow(getActivity());
        FileUtils33 files=new FileUtils33();
        String username=files.readDataFromFile(context);
        Log.i("huhsad", "onViewCreated: "+username);
        if(username!=null&&!username.equals("null")){
            Log.i("ganpaos", "onViewCreated: "+username);
            thirdlogin();
        }else {
            Log.i("ganpaos1", "onViewCreated: "+username);
            FileUtis36 fil = new FileUtis36();
            fil.saveDataToFile(context,"nologin");
            FileUtils37 file=new FileUtils37();
            file.saveDataToFile(context,"nologin");
            qingqiu();
        }
        postdata();
        initViewPager();


    }

    private void thirdlogin() {

        FileUtils33 file=new FileUtils33();
        String cookie=file.readDataFromFile(context);
        FileUtils37 file1=new FileUtils37();
        file1.saveDataToFile(context,"nologin");
        if(cookie!=null&&!cookie.equals("null")){
            Constant.localCookie = cookie;
            Log.i("heile", "thirdlogin: "+cookie);
        }

    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }
    private void postdata() {
        v_city = LayoutInflater.from(context).inflate(R.layout.activity_picimg, null);
        pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT , true);
        final ImageView image= (ImageView) v_city.findViewById(R.id.dismiss_iv);
        final ImageView image1= (ImageView) v_city.findViewById(R.id.pop_imag);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });

        queue = Volley.newRequestQueue(context);
        String url = "https://url.aijiaijia.com/v_android.php";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("heihei", "onResponse: " + str.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            String result = jsonObject.getString("version");
                            String title = jsonObject.getString("vimg");
                            final String content = jsonObject.getString("vlink");
                            String btn = jsonObject.getString("activity");
                            final String btnlink = jsonObject.getString("aimg");
                            final String btnend = jsonObject.getString("alink");
                            PackageManager manager;
                            PackageInfo info = null;
                            manager = context.getPackageManager();
                            try {
                                info = manager.getPackageInfo(context.getPackageName(), 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String ss = info.versionName;
                            Log.i("wuhu", "onResponse: "+ss);
                            Log.i("hues", "onResponse: "+result);
                            if (!result.equals(ss)) {
                                Date date = new Date();
                                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                                result1 = format.format(date);
                                FileUtils30 file=new FileUtils30();
                                String dateinfo=file.readDataFromFile(context);
                                FileUtils31 files=new FileUtils31();
                                String infofile=files.readDataFromFile(context);
                                if(infofile!=null&&infofile.equals("1")&&dateinfo!=null&&dateinfo.equals(result1)){
                                    Log.i("wuhu", "onResponse: "+result1);
                                    Log.i("hues", "onResponse: "+infofile);
                                }else {
                                    Log.i("hues1", "onResponse: "+result);
                                    setBackgroundAlpha(0.5f);
                                    pop.setBackgroundDrawable(new BitmapDrawable());
                                    pop.setOutsideTouchable(true);
                                    pop.setFocusable(true);
                                    pop.showAtLocation(v_city, Gravity.CENTER, 0, 0);
                                    loader.loadImage(title,new SimpleImageLoadingListener(){
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                              image1.setImageBitmap(loadedImage);
                                        }
                                    });
                                    image.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dissmiss();
                                            Date date = new Date();
                                            DateFormat format = new SimpleDateFormat("yyyyMMdd");
                                            String  result1 = format.format(date);
                                            FileUtils30 file1=new FileUtils30();
                                            file1.saveDataToFile(context,result1);
                                            FileUtils31 file=new FileUtils31();
                                            file.saveDataToFile(context,"1");
                                        }
                                    });
                                    image1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(content.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?pid")){
                                                Intent intent=new Intent(context,h5detailsActivity.class);
                                                String[] strArray = content.split("=");
                                                String uid = strArray[strArray.length - 1];
                                                intent.putExtra("uid", uid);
                                                context.startActivity(intent);
                                            }else if(content.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?keyword")){
                                                Intent intent = new Intent(context, NewsousuoActivity.class);
                                                String[] strArray = content.split("=");
                                                String uid = strArray[strArray.length - 1];
                                                intent.putExtra("useid", uid);
                                                context.startActivity(intent);
                                            }else if(content.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?weblink")){
                                                String[] strArray = content.split("weblink=");
                                                String name=strArray[strArray.length-1];  //分割的网址
                                                String[] strname=name.split("webTitle="); //分割标题
                                                String title1=strname[strname.length-1]; //标题
                                                try {
                                                    String strUTF8 = URLDecoder.decode(name, "UTF-8");
                                                    String Title = URLDecoder.decode(title1, "UTF-8");
                                                    Intent intent = new Intent(context, WebviewActivity.class);
                                                    intent.putExtra("shoping", strUTF8);
                                                    intent.putExtra("xingming", Title);
                                                    context.startActivity(intent);
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }else if(content.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?api_login")){
                                                Intent intent = new Intent(context, LoginActivity.class);
                                                context.startActivity(intent);
                                            }else if(content.contains("https://down.aijiaijia.com/ajaj.apk")||content.contains("https://down.aijiaijia.com/")){
                                                Uri uri = Uri.parse(content);
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                context.startActivity(intent);
                                            }
                                        }
                                    });

                                  /*  popWindow.showAsDropDown(loader,title,content);
                                    setBackgroundAlpha(0.5f);*/
                                }
                            } else {
                                if(btn.equals("1")){
                                    Date date = new Date();
                                    DateFormat format = new SimpleDateFormat("yyyyMMdd");
                                    result1 = format.format(date);
                                    FileUtils30 file=new FileUtils30();
                                    String dateinfo=file.readDataFromFile(context);
                                    FileUtils31 files=new FileUtils31();
                                    String infofile=files.readDataFromFile(context);
                                     if(infofile!=null&&infofile.equals("1")&&dateinfo!=null&&dateinfo.equals(result1)){
                                         Log.i("wuhu", "onResponse: "+result1);
                                         Log.i("hues", "onResponse: "+infofile);
                                     }else {
                                        /* popWindow.showAsDropDown(loader, btnlink, btnend);
                                         setBackgroundAlpha(0.5f);*/
                                         setBackgroundAlpha(0.5f);
                                         pop.setBackgroundDrawable(new BitmapDrawable());
                                         pop.setOutsideTouchable(true);
                                         pop.setFocusable(true);
                                         pop.showAtLocation(v_city, Gravity.CENTER, 0, 0);
                                         loader.loadImage(btnlink,new SimpleImageLoadingListener(){
                                             @Override
                                             public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                 super.onLoadingComplete(imageUri, view, loadedImage);
                                                 image1.setImageBitmap(loadedImage);
                                             }
                                         });
                                         image.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 dissmiss();
                                                 Date date = new Date();
                                                 DateFormat format = new SimpleDateFormat("yyyyMMdd");
                                                 String  result1 = format.format(date);
                                                 FileUtils30 file1=new FileUtils30();
                                                 file1.saveDataToFile(context,result1);
                                                 FileUtils31 file=new FileUtils31();
                                                 file.saveDataToFile(context,"1");
                                             }
                                         });
                                         image1.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 if(btnend.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?pid")){
                                                     Intent intent=new Intent(context,h5detailsActivity.class);
                                                     String[] strArray = content.split("=");
                                                     String uid = strArray[strArray.length - 1];
                                                     intent.putExtra("uid", uid);
                                                     context.startActivity(intent);
                                                 }else if(btnend.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?keyword")){
                                                     Intent intent = new Intent(context, NewsousuoActivity.class);
                                                     String[] strArray = content.split("=");
                                                     String uid = strArray[strArray.length - 1];
                                                     intent.putExtra("useid", uid);
                                                     context.startActivity(intent);
                                                 }else if(btnend.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?weblink")){
                                                     String[] strArray = content.split("weblink=");
                                                     String name=strArray[strArray.length-1];  //分割的网址
                                                     String[] strname=name.split("webTitle="); //分割标题
                                                     String title1=strname[strname.length-1]; //标题
                                                     try {
                                                         String strUTF8 = URLDecoder.decode(name, "UTF-8");
                                                         String Title = URLDecoder.decode(title1, "UTF-8");
                                                         Intent intent = new Intent(context, WebviewActivity.class);
                                                         intent.putExtra("shoping", strUTF8);
                                                         intent.putExtra("xingming", Title);
                                                         context.startActivity(intent);
                                                     } catch (UnsupportedEncodingException e) {
                                                         e.printStackTrace();
                                                     }
                                                 }else if(btnend.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?api_login")){
                                                     Intent intent = new Intent(context, LoginActivity.class);
                                                     context.startActivity(intent);
                                                 }else if(btnend.contains("https://down.aijiaijia.com/ajaj.apk")||btnend.contains("https://down.aijiaijia.com/")){
                                                     Uri uri = Uri.parse(btnend);
                                                     Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                     context.startActivity(intent);
                                                 }
                                             }
                                         });
                                     }
                                }
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

        };

        queue.add(post);
    }

    private void dissmiss() {
        pop.dismiss();
    }

    /*  private void setBackgroundAlpha(float bgAlpha) {
          WindowManager.LayoutParams lp = MainActivity.getWindow()
                  .getAttributes();
          lp.alpha = bgAlpha;
          getWindow().setAttributes(lp);
      }*/
    private void qingqiu() {
        FileUtils24 file = new FileUtils24();
        phone = file.readDataFromFile(context);
        FileUtils26 file1 = new FileUtils26();
        password = file1.readDataFromFile(context);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String JSONDataUrl = "https://api.aijiaijia.com/api_login";
        //POST方式更加安全
        StringRequest stringrequest = new StringRequest(Request.Method.POST, JSONDataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        //就是在主线程上操作,弹出结果
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String ss = resposeobject.getString("op");
                            String ss1 = resposeobject.getString("trsuid");
                            Log.i("fsddsd", "onResponse: "+ss);
                                if(ss.equals("1")){
                                    FileUtils6 ff = new FileUtils6();
                                    ff.saveDataToFile(context, ss1);   //用户的uid
                                    FileUtils37 files=new FileUtils37();
                                    files.saveDataToFile(context,phone);
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
            }
        }) {

            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("dish", "getParams: " + "daolema");
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", password);
                return map;
            }

            //重写parseNetworkResponse方法，返回的数据中 Set-Cookie:***************;
            //其中**************为session id
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                Response<String> superResponse = super
                        .parseNetworkResponse(response);
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                //Constant是一个自建的类，存储常用的全局变量
                Constant.localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                Log.d("sessionid", "sessionid----------------" + Constant.localCookie);
                return superResponse;
            }
        };
        requestQueue.add(stringrequest);
    }


    private void initViewPager() {
        Fragment tabFrames[] = new Fragment[]{new h5SelectbrandFragement(), new h5KeyhomeFragement(), new h5colorfullife(), new h5bluesirFragement()};
        String tabTitles[] = new String[]{"精选品牌", "一键购家", "缤纷活动", "蓝领先生"};
        FragmentPagerAdapter mFragmentAdapteradapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), tabTitles, tabFrames);
        for (int i = 0; i < tabTitles.length; i++) {
            h5tabs.addTab(h5tabs.newTab().setText(tabTitles[i]));
            //给ViewPager设置适配器
            h5viewpager.setAdapter(mFragmentAdapteradapter);
            //将TabLayout和ViewPager关联起来。
            h5tabs.setupWithViewPager(h5viewpager);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_h5homepage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onDismiss() {

    }
}
