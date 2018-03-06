package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import utils.FileUtils;
import utils.FileUtils30;
import utils.FileUtils31;

/**
 * Created by misshu on 2017/5/17/017.
 */
@SuppressLint("CommitPrefEdits")
public class updatepopwindow implements PopupWindow.OnDismissListener, View.OnClickListener {
    private ImageView update_iv;
    private ImageView missupdate;
    private PopupWindow popupWindow;
    private OnItemClickListener listener;
    private final int ADDORREDUCE = 1;
    private Context context;
     private FragmentActivity getactivity;
    private boolean flag;
    private RelativeLayout ss;
    private String link;
    private String btnlink;
    private ImageLoader loader;
    private RequestQueue queue;
    private JSONObject resposeobject;
    private String result13;
    private String jieguo = "0";
    String uids;
    public updatepopwindow(FragmentActivity getactivity) {
        this.getactivity = getactivity;
        View view = LayoutInflater.from(getactivity).inflate(R.layout.activity_updatepopwindow, null);
        ss= (RelativeLayout) view.findViewById(R.id.ss);
        update_iv= (ImageView) view.findViewById(R.id.update_iv);
        missupdate= (ImageView) view.findViewById(R.id.missupdate);
        update_iv.setOnClickListener(this);
        missupdate.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
       // popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
              setBackgroundAlpha(1f);
            }
        });// ��popWindow��ʧʱ�ļ���

    }
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getactivity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getactivity.getWindow().setAttributes(lp);
    }
   /* private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activtiy.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activtiy.getWindow().setAttributes(lp);
    }*/

    public interface OnItemClickListener {
        public void onClickOKPop();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss() {

    }

    public void jianting(View parent) {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    public void showAsDropDown(ImageLoader loader,String link,String btnlink) {
          this.link=link;
        this.btnlink=btnlink;
        this.loader=loader;
        Log.i("haas", "showAsDropDown: "+btnlink);
        Log.i("haas1", "showAsDropDown: "+link);
        loader.loadImage(link,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                update_iv.setImageBitmap(loadedImage);
            }
        });
        popupWindow.showAtLocation(ss,Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    public void dissmiss() {
        popupWindow.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_iv:
                String id = btnlink;
                Log.i("huhsa", "onClick: "+btnlink);
                 if(btnlink.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?pid")){
                     Intent intent=new Intent(getactivity,h5detailsActivity.class);
                     String[] strArray = id.split("=");
                     String uid = strArray[strArray.length - 1];
                     intent.putExtra("uid", uid);
                     getactivity.startActivity(intent);
                 }else if(btnlink.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?keyword")){
                     Intent intent = new Intent(getactivity, NewsousuoActivity.class);
                     String[] strArray = id.split("=");
                     String uid = strArray[strArray.length - 1];
                     intent.putExtra("useid", uid);

                     getactivity.startActivity(intent);
                 }else if(btnlink.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?weblink")){
                     String[] strArray = id.split("weblink=");
                     String name=strArray[strArray.length-1];  //分割的网址
                     String[] strname=name.split("webTitle="); //分割标题
                     String title=strname[strname.length-1]; //标题
                     try {
                         String strUTF8 = URLDecoder.decode(name, "UTF-8");
                         String Title = URLDecoder.decode(title, "UTF-8");
                         Intent intent = new Intent(getactivity, WebviewActivity.class);
                         intent.putExtra("shoping", strUTF8);
                         intent.putExtra("xingming", Title);
                         getactivity.startActivity(intent);
                     } catch (UnsupportedEncodingException e) {
                         e.printStackTrace();
                     }
                 }else if(btnlink.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?api_login")){
                     Intent intent = new Intent(getactivity, LoginActivity.class);
                     getactivity.startActivity(intent);
                 }else if(btnlink.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?addcart")){
                     String[] strArray = id.split("=");
                     String uid = strArray[strArray.length - 1];
                     String[] strArray1 = uid.split("PN");
                     uids= strArray1[strArray1.length - 1];
                     postdata();
                 }else if (btnlink.contains("https://down.aijiaijia.com/ajaj.apk")||btnlink.contains("https://down.aijiaijia.com/")){
                     Log.i("huhsa", "onClick: "+"heihei");
                     Uri uri = Uri.parse(btnlink);
                     Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                     getactivity.startActivity(intent);
                 }



                break;
            case R.id.missupdate:
                 dissmiss();
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                String  result1 = format.format(date);
                FileUtils30 file1=new FileUtils30();
                file1.saveDataToFile(getactivity,result1);
                FileUtils31 file=new FileUtils31();
                file.saveDataToFile(getactivity,"1");
                Log.i("heihies", "onClick: "+result1);
                break;
            default:
                break;
        }
    }

    private void postdata() {

        queue = Volley.newRequestQueue(getactivity);
        String url = "https://api.aijiaijia.com/api_shopcart";
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
                            resposeobject = jsonObject.getJSONObject("responseJson");
                            if (result13.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String s7 = object.getString("pid");
                                    if (uids.equals(s7)) {
                                        jieguo = "1";
                                        Toast toast = Toast.makeText(getactivity, "购物车已有该商品", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                                if (!jieguo.equals("1")) {
                                    if (uids != null) {

                                        queue = Volley.newRequestQueue(getactivity);
                                        String url = "https://api.aijiaijia.com/api_shopcart_add";
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
                                                            if (result3.equals("0")) {
                                                                Toast.makeText(getactivity, "加入失败", Toast.LENGTH_SHORT).show();
                                                            } else if (result3.equals("1")) {
                                                                // setAnim(v);
                                                                Toast.makeText(getactivity, "加入成功", Toast.LENGTH_SHORT).show();

                                                            } else if (result3.equals("6")) {
                                                                Toast toast = Toast.makeText(getactivity, "未登录", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                                Intent intent = new Intent(getactivity, LoginActivity.class);
                                                                getactivity.startActivity(intent);
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
                                                map.put("pids", uids);
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
                                    } else {
                                        Toast toast = Toast.makeText(getactivity, "请检查网络!!!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }

                                }
                            } else {
                                Toast toast = Toast.makeText(getactivity, "未登录", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent intent = new Intent(getactivity, LoginActivity.class);
                                getactivity.startActivity(intent);
                                return;
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


}
