package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu2;
import bean.gouwu3;
import bean.h5gouwu;
import butterknife.Bind;
import h5Fragement.h5bluesirFragement;
import util.Myapp;
import utils.DBHelper4;
import utils.DBHelper5;
import utils.DBHelper6;
import utils.FileUtils1;
import utils.FileUtils19;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.FileUtils35;
import utils.Fileutils18;

/**
 * Created by misshu on 2017/5/11/011.
 */
@SuppressLint("CommitPrefEdits")
public class h5popwindow implements PopupWindow.OnDismissListener, View.OnClickListener {
    private ImageView ivAdapterGridPic;
    private  TextView popNum;
    private  TextView popReduce;
    private   TextView popAdd;;
    private TextView nowPrice;
    private TextView previousPrice;
    private  TextView stocks;
    private  TextView selectTv;
    private  TextView installTv;
    private   TextView noinstall;
    private TextView allnum;
    private PopupWindow popupWindow;
    private OnItemClickListener listener;
    private h5detailsActivity activtiy;
    private final int ADDORREDUCE = 1;
    private Context context;
    private String str_color = "";
    private String str_type = "";
    private RelativeLayout selecttwo;
    private RelativeLayout selectthree;
    private TextView select_tv1;
    private TextView select_tv2;
    private TextView select_tv4;
    private TextView select_tv5;
    private TextView select_tv6;
    String isok="不安装";
    /* String productid;
     String skuid;*/
   /* String nowprice;
    String previousprice;
    String skuvaluename;
    String servicepriceyes;
    String servicepriceno;
    String serviceyes;
    String serviceno;
    String stock;*/
    String pictureurl;
    /* String classsku;
     String installservice;*/
    private boolean flag;
    private ImageLoader loader;
    private RelativeLayout noinstall_rl;
    private RelativeLayout selectfour;
    private RelativeLayout selectfive;
    private RelativeLayout selectsex;
    private RelativeLayout install_rl;
    private RelativeLayout allselect_rl;
    private RelativeLayout sure_result;
    private TextView freight;
    private TextView install_service;
    private TextView class_sku;
    private TextView chose_tv;
    private TextView jilu;
    private ImageView describe_iv;
    private RequestQueue queue;
    private String  result13="0";
    private JSONObject resposeobject;
    String messaged;
    private String jieguo = "0";
    ArrayList<String> h5id=new ArrayList<>();   //商品的id
    ArrayList<String> h5idnum=new ArrayList<>();//商品的数量
    ArrayList<String> h5skuid=new ArrayList<>();//sku的id
    ArrayList<String> h5isok=new ArrayList<>(); //是否安装服务
    List<h5gouwu> h5shop= new ArrayList<>();
    List<gouwu3> goud = new ArrayList<>();
    List<gouwu2> gou2 = new ArrayList<>();
    String issku;
    String isname;
    int content1;
    ArrayList<String> skuid=new ArrayList<>();
    ArrayList<String> nowprice=new ArrayList<>();
    ArrayList<String> previousprice=new ArrayList<>();
    ArrayList<String> skuvaluename=new ArrayList<>();
    ArrayList<String> servicepriceyes=new ArrayList<>();
    ArrayList<String> servicepriceno=new ArrayList<>();
    ArrayList<String> stock=new ArrayList<>();
    ArrayList<String> serviceyes=new ArrayList<>();
    ArrayList<String> serviceno=new ArrayList<>();
    ArrayList<String> classsku=new ArrayList<>();
    ArrayList<String> installservice=new ArrayList<>();
    ArrayList<String> productid=new ArrayList<>();
    ArrayList<String> skufright=new ArrayList<>();
    public h5popwindow(h5detailsActivity context) {
        this.activtiy = context;
        queue = Volley.newRequestQueue(activtiy);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_popwindow, null);
        sure_result= (RelativeLayout) view.findViewById(R.id.sure_result);
        jilu= (TextView) view.findViewById(R.id.jilu);
        freight= (TextView) view.findViewById(R.id.freight);
        describe_iv= (ImageView) view.findViewById(R.id.describe_iv);
        install_service= (TextView) view.findViewById(R.id.install_service);
        class_sku= (TextView) view.findViewById(R.id.class_sku);
        chose_tv= (TextView) view.findViewById(R.id.chose_tv);
        install_rl= (RelativeLayout) view.findViewById(R.id.install_rl);
        noinstall_rl= (RelativeLayout) view.findViewById(R.id.noinstall_rl);
        select_tv1= (TextView) view.findViewById(R.id.select_tv1);
        select_tv2= (TextView) view.findViewById(R.id.select_tv2);
        select_tv4= (TextView) view.findViewById(R.id.select_tv4);
        select_tv5= (TextView) view.findViewById(R.id.select_tv5);
        select_tv6= (TextView) view.findViewById(R.id.select_tv6);
        selecttwo= (RelativeLayout) view.findViewById(R.id.selecttwo);
        selectthree= (RelativeLayout) view.findViewById(R.id.selectthree);
        selectfour= (RelativeLayout) view.findViewById(R.id.selectfour);
        selectfive= (RelativeLayout) view.findViewById(R.id.selectfive);
        selectsex= (RelativeLayout) view.findViewById(R.id.selectsex);
        class_sku= (TextView) view.findViewById(R.id.class_sku);
        nowPrice= (TextView) view.findViewById(R.id.now_price);   //现价
        previousPrice= (TextView) view.findViewById(R.id.previous_price);  //原价
        stocks= (TextView) view.findViewById(R.id.stock);   //库存
        selectTv= (TextView) view.findViewById(R.id.select_tv);  //skuValuename
        installTv= (TextView) view.findViewById(R.id.install_tv); //安装
        noinstall= (TextView) view.findViewById(R.id.noinstall); //不安装
        allnum= (TextView) view.findViewById(R.id.allnum);
        popAdd = (TextView) view.findViewById(R.id.pop_add);
        allselect_rl= (RelativeLayout) view.findViewById(R.id.allselect_rl);
        popReduce = (TextView) view.findViewById(R.id.pop_reduce);
        popNum = (TextView) view.findViewById(R.id.pop_num);
        ivAdapterGridPic= (ImageView) view.findViewById(R.id.iv_adapter_grid_pic);
        popAdd.setOnClickListener(this);
        popReduce.setOnClickListener(this);
        install_rl.setOnClickListener(this);
        noinstall_rl.setOnClickListener(this);
        allselect_rl.setOnClickListener(this);
        describe_iv.setOnClickListener(this);
        sure_result.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      /*  popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();*/
        popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });

    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activtiy.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activtiy.getWindow().setAttributes(lp);
    }

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

    public void showAsDropDown(View parent,String messaged, ImageLoader loader,ArrayList<String> skuid,ArrayList<String> skufright, ArrayList<String> productid,ArrayList<String> nowprice, ArrayList<String> previousprice, ArrayList<String> skuvaluename, ArrayList<String> servicepriceyes, ArrayList<String> servicepriceno,ArrayList<String> serviceyes, ArrayList<String> serviceno,
                               ArrayList<String> stock, String pictureurl,ArrayList<String> classsku,ArrayList<String> installservice) {
        flag=true;
        noinstall_rl.setBackgroundResource(R.drawable.h5detailborder);
        install_rl.setBackgroundDrawable(activtiy.getResources().getDrawable(R.drawable.h5detailsnochose));
        popNum.setText("1");
        this.messaged=messaged;
        this.productid=productid;
        this.skuid=skuid;
        this.loader = loader;
        this.nowprice = nowprice;
        this.previousprice = previousprice;
        this.skufright=skufright;
        this.skuvaluename = skuvaluename;
        this.servicepriceyes = servicepriceyes;
        this.servicepriceno = servicepriceno;
        this.serviceyes = serviceyes;
        this.serviceno = serviceno;
        this.stock = stock;
        this.pictureurl = pictureurl;
        this.classsku=classsku;
        this.installservice=installservice;
        Log.i("weilai", "showAsDropDown: "+messaged);
        Log.i("chues", "showAsDropDown: "+nowprice);
        Log.i("feichule", "showAsDropDown: "+stock.size());
        chose_tv.setText("已选:"+"\""+skuvaluename.get(0)+"\"");
        if(stock.size()==1){
            selecttwo.setVisibility(View.GONE);
            selectthree.setVisibility(View.GONE);
            selectfour.setVisibility(View.GONE);
            selectfive.setVisibility(View.GONE);
            selectsex.setVisibility(View.GONE);
            nowPrice.setText("￥"+nowprice.get(0));
            previousPrice.setText("原价:"+previousprice.get(0));
            freight.setText("运费:"+skufright.get(0));
            previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            selectTv.setText(skuvaluename.get(0));
            stocks.setText("库存:"+stock.get(0)+"件");
            class_sku.setText(classsku.get(0));
            issku=skuid.get(0);
            isname=skuvaluename.get(0);
            install_service.setText(installservice.get(0));
            noinstall.setText(serviceno.get(0));
            jilu.setText(servicepriceyes.get(0));
            if(serviceyes.get(0).equals("null")||servicepriceyes.get(0).equals("null")){
                install_rl.setVisibility(View.INVISIBLE);
            }else {
                install_rl.setVisibility(View.VISIBLE);
                installTv.setText(serviceyes.get(0)+ "" + "￥" + "" + servicepriceyes.get(0));
            }
            allnum.setText(nowprice.get(0));
        }else if(stock.size()==2){
            selecttwo.setVisibility(View.VISIBLE);
            selectthree.setVisibility(View.GONE);
            selectfour.setVisibility(View.GONE);
            selectfive.setVisibility(View.GONE);
            selectsex.setVisibility(View.GONE);
            nowPrice.setText("￥"+nowprice.get(0));
            previousPrice.setText("原价:"+previousprice.get(0));
            previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            selectTv.setText(skuvaluename.get(0));
            freight.setText("运费:"+skufright.get(0));
            isname=skuvaluename.get(0);
            select_tv1.setText(skuvaluename.get(1));
            issku=skuid.get(0);
            jilu.setText(servicepriceyes.get(0));
            stocks.setText("库存:"+stock.get(0)+"件");
            class_sku.setText(classsku.get(0));
            install_service.setText(installservice.get(0));
            noinstall.setText(serviceno.get(0));
            if(serviceyes.get(0).equals("null")||servicepriceyes.get(0).equals("null")){
                install_rl.setVisibility(View.INVISIBLE);
            }else {
                install_rl.setVisibility(View.VISIBLE);
                installTv.setText(serviceyes.get(0)+ "" + "￥" + "" + servicepriceyes.get(0));
            }
            allnum.setText(nowprice.get(0));
            setlistener();
        }else if(stock.size()==3){
            selecttwo.setVisibility(View.VISIBLE);
            selectthree.setVisibility(View.VISIBLE);
            selectfour.setVisibility(View.GONE);
            selectfive.setVisibility(View.GONE);
            selectsex.setVisibility(View.GONE);
            nowPrice.setText("￥"+nowprice.get(0));
            previousPrice.setText("原价:"+previousprice.get(0));
            isname=skuvaluename.get(0);
            previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            selectTv.setText(skuvaluename.get(0));
            freight.setText("运费:"+skufright.get(0));
            issku=skuid.get(0);
            select_tv1.setText(skuvaluename.get(1));
            select_tv2.setText(skuvaluename.get(2));
            jilu.setText(servicepriceyes.get(0));
            stocks.setText("库存:"+stock.get(0)+"件");
            class_sku.setText(classsku.get(0));
            install_service.setText(installservice.get(0));
            noinstall.setText(serviceno.get(0));
            if(serviceyes.get(0).equals("null")||servicepriceyes.get(0).equals("null")){
                install_rl.setVisibility(View.INVISIBLE);
            }else {
                install_rl.setVisibility(View.VISIBLE);
                installTv.setText(serviceyes.get(0)+ "" + "￥" + "" + servicepriceyes.get(0));
            }
            allnum.setText(nowprice.get(0));
            setlistener();
        }else if(stock.size()==4){
            selecttwo.setVisibility(View.VISIBLE);
            selectthree.setVisibility(View.VISIBLE);
            selectfour.setVisibility(View.VISIBLE);
            selectfive.setVisibility(View.GONE);
            selectsex.setVisibility(View.GONE);
            nowPrice.setText("￥"+nowprice.get(0));
            isname=skuvaluename.get(0);
            freight.setText("运费:"+skufright.get(0));
            previousPrice.setText("原价:"+previousprice.get(0));
            jilu.setText(servicepriceyes.get(0));
            previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            selectTv.setText(skuvaluename.get(0));
            issku=skuid.get(0);
            select_tv1.setText(skuvaluename.get(1));
            select_tv2.setText(skuvaluename.get(2));
            select_tv4.setText(skuvaluename.get(3));
            stocks.setText("库存:"+stock.get(0)+"件");
            class_sku.setText(classsku.get(0));
            install_service.setText(installservice.get(0));
            noinstall.setText(serviceno.get(0));
            if(serviceyes.get(0).equals("null")||servicepriceyes.get(0).equals("null")){
                install_rl.setVisibility(View.INVISIBLE);
            }else {
                install_rl.setVisibility(View.VISIBLE);
                installTv.setText(serviceyes.get(0)+ "" + "￥" + "" + servicepriceyes.get(0));
            }
            allnum.setText(nowprice.get(0));
            setlistener();
        }else if(stock.size()==5){
            selecttwo.setVisibility(View.VISIBLE);
            selectthree.setVisibility(View.VISIBLE);
            selectfour.setVisibility(View.VISIBLE);
            selectfive.setVisibility(View.VISIBLE);
            selectsex.setVisibility(View.GONE);
            nowPrice.setText("￥"+nowprice.get(0));
            issku=skuid.get(0);
            isname=skuvaluename.get(0);
            freight.setText("运费:"+skufright.get(0));
            previousPrice.setText("原价:"+previousprice.get(0));
            previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            jilu.setText(servicepriceyes.get(0));
            selectTv.setText(skuvaluename.get(0));
            select_tv1.setText(skuvaluename.get(1));
            select_tv2.setText(skuvaluename.get(2));
            select_tv4.setText(skuvaluename.get(3));
            select_tv5.setText(skuvaluename.get(4));
            stocks.setText("库存:"+stock.get(0)+"件");
            class_sku.setText(classsku.get(0));
            install_service.setText(installservice.get(0));
            noinstall.setText(serviceno.get(0));
            if(serviceyes.get(0).equals("null")||servicepriceyes.get(0).equals("null")){
                install_rl.setVisibility(View.INVISIBLE);
            }else {
                install_rl.setVisibility(View.VISIBLE);
                installTv.setText(serviceyes.get(0)+ "" + "￥" + "" + servicepriceyes.get(0));
            }
            allnum.setText(nowprice.get(0));
            setlistener();
        }
        loader.loadImage(pictureurl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                ivAdapterGridPic.setImageBitmap(loadedImage);

            }
        });

        setBackgroundAlpha(0.5f);
        islogin();
        if(Build.VERSION.SDK_INT < 24){
            popupWindow.showAtLocation(parent,Gravity.BOTTOM, 0, 0);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.update();
        }else {
            //适配Android7.0
            popupWindow.showAtLocation(parent,Gravity.NO_GRAVITY, 0, parent.getHeight()+getStatusBarHeight());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.update();

        }

    }

    private void setlistener() {
        selectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content1=0;
                allselect_rl.setBackgroundResource(R.drawable.h5detailborder);
                selecttwo.setBackgroundResource(R.drawable.h5detailsnochose);
                selectthree.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfour.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfive.setBackgroundResource(R.drawable.h5detailsnochose);
                selectsex.setBackgroundResource(R.drawable.h5detailsnochose);
                chose_tv.setText("已选:"+"\""+skuvaluename.get(0)+"\"");
                nowPrice.setText("￥"+nowprice.get(0));
                previousPrice.setText("原价:"+previousprice.get(0));
                previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                stocks.setText("库存:"+stock.get(0)+"件");
                freight.setText("运费:"+skufright.get(0));
                class_sku.setText(classsku.get(0));
                jilu.setText(servicepriceyes.get(0));
                issku=skuid.get(0);
                isname=skuvaluename.get(0);
                install_service.setText(installservice.get(0));
                noinstall.setText(serviceno.get(0));
                installTv.setText(serviceyes.get(0)+ "" + "￥" + "" + servicepriceyes.get(0));
                allnum.setText(nowprice.get(0));

            }
        });
        select_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content1=1;
                allselect_rl.setBackgroundResource(R.drawable.h5detailsnochose);
                selecttwo.setBackgroundResource(R.drawable.h5detailborder);
                selectthree.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfour.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfive.setBackgroundResource(R.drawable.h5detailsnochose);
                selectsex.setBackgroundResource(R.drawable.h5detailsnochose);
                nowPrice.setText("￥"+nowprice.get(1));
                issku=skuid.get(1);
                isname=skuvaluename.get(1);
                previousPrice.setText("原价:"+previousprice.get(1));
                chose_tv.setText("已选:"+"\""+skuvaluename.get(1)+"\"");
                previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                stocks.setText("库存:"+stock.get(1)+"件");
                freight.setText("运费:"+skufright.get(1));
                class_sku.setText(classsku.get(1));
                jilu.setText(servicepriceyes.get(1));
                install_service.setText(installservice.get(1));
                noinstall.setText(serviceno.get(1));
                installTv.setText(serviceyes.get(1)+ "" + "￥" + "" + servicepriceyes.get(1));
                allnum.setText(nowprice.get(1));
            }
        });
        select_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content1=2;
                allselect_rl.setBackgroundResource(R.drawable.h5detailsnochose);
                selecttwo.setBackgroundResource(R.drawable.h5detailsnochose);
                selectthree.setBackgroundResource(R.drawable.h5detailborder);
                selectfour.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfive.setBackgroundResource(R.drawable.h5detailsnochose);
                selectsex.setBackgroundResource(R.drawable.h5detailsnochose);
                nowPrice.setText("￥"+nowprice.get(2));
                jilu.setText(servicepriceyes.get(2));
                chose_tv.setText("已选:"+"\""+skuvaluename.get(2)+"\"");
                issku=skuid.get(2);
                isname=skuvaluename.get(2);
                previousPrice.setText("原价:"+previousprice.get(2));
                previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                stocks.setText("库存:"+stock.get(2)+"件");
                freight.setText("运费:"+skufright.get(2));
                class_sku.setText(classsku.get(2));
                install_service.setText(installservice.get(2));
                noinstall.setText(serviceno.get(2));
                installTv.setText(serviceyes.get(2)+ "" + "￥" + "" + servicepriceyes.get(2));
                allnum.setText(nowprice.get(2));
            }
        });
        select_tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content1=3;
                allselect_rl.setBackgroundResource(R.drawable.h5detailsnochose);
                selecttwo.setBackgroundResource(R.drawable.h5detailsnochose);
                selectthree.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfour.setBackgroundResource(R.drawable.h5detailborder);
                selectfive.setBackgroundResource(R.drawable.h5detailsnochose);
                selectsex.setBackgroundResource(R.drawable.h5detailsnochose);
                nowPrice.setText("￥"+nowprice.get(3));
                previousPrice.setText("原价:"+previousprice.get(3));
                previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                stocks.setText("库存:"+stock.get(3)+"件");
                freight.setText("运费:"+skufright.get(3));
                class_sku.setText(classsku.get(3));
                issku=skuid.get(3);
                chose_tv.setText("已选:"+"\""+skuvaluename.get(3)+"\"");
                isname=skuvaluename.get(3);
                jilu.setText(servicepriceyes.get(3));
                install_service.setText(installservice.get(3));
                noinstall.setText(serviceno.get(3));
                installTv.setText(serviceyes.get(3)+ "" + "￥" + "" + servicepriceyes.get(3));
                allnum.setText(nowprice.get(3));
            }
        });
        select_tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content1=4;
                allselect_rl.setBackgroundResource(R.drawable.h5detailsnochose);
                selecttwo.setBackgroundResource(R.drawable.h5detailsnochose);
                selectthree.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfour.setBackgroundResource(R.drawable.h5detailsnochose);
                selectfive.setBackgroundResource(R.drawable.h5detailborder);
                selectsex.setBackgroundResource(R.drawable.h5detailsnochose);
                nowPrice.setText("￥"+nowprice.get(4));
                issku=skuid.get(4);
                isname=skuvaluename.get(4);
                freight.setText("运费:"+skufright.get(4));
                chose_tv.setText("已选:"+"\""+skuvaluename.get(4)+"\"");
                jilu.setText(servicepriceyes.get(4));
                previousPrice.setText("原价:"+previousprice.get(4));
                previousPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                stocks.setText("库存:"+stock.get(4)+"件");
                class_sku.setText(classsku.get(4));
                install_service.setText(installservice.get(4));
                noinstall.setText(serviceno.get(4));
                installTv.setText(serviceyes.get(4)+ "" + "￥" + "" + servicepriceyes.get(4));
                allnum.setText(nowprice.get(4));
            }
        });
    }

    private void islogin() {
        queue = Volley.newRequestQueue(activtiy);
        String urls = "https://api.aijiaijia.com/api_sellorder_statistics";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            result13 = resposeobject.getString("op");
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

    private int getStatusBarHeight()
    {
        try
        {

            Resources resource = activtiy.getApplication().getResources();
            int resourceId = resource.getIdentifier("status_bar_height", "dimen", "Android");
            if (resourceId != 0)
            {
                return resource.getDimensionPixelSize(resourceId);
            }
        } catch (Exception e)
        {
        }
        return 0;
    }

    public void dissmiss() {
        popupWindow.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_add:
                if (!popNum.getText().toString().equals("10000")) {

                    String num_add = Integer.valueOf(popNum.getText().toString()) + ADDORREDUCE + "";
                    popNum.setText(num_add);
                    try {
                        Number price= NumberFormat.getInstance().parse(nowprice.get(content1));
                        Number num= NumberFormat.getInstance().parse(num_add);
                        Number numjilu= NumberFormat.getInstance().parse(jilu.getText().toString());//服务费
                        float changeprice= price.floatValue();
                        float changenum=num.floatValue();
                        float changejilu=numjilu.floatValue();
                        float changeallsale=changeprice*changenum;
                        float changeallsale1=changeprice*changenum+changejilu*changenum;
                        DecimalFormat df = new DecimalFormat("0.00");
                        String all=df.format(changeallsale);
                        String allinstall=df.format(changeallsale1);
                        if(flag==false){
                            allnum.setText(allinstall);
                        }else {
                            allnum.setText(all);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                } else {
                    Toast.makeText(context, "超过上线", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.pop_reduce:
                if (!popNum.getText().toString().equals("1")) {
                    String num_reduce = Integer.valueOf(popNum.getText().toString()) - ADDORREDUCE + "";
                    popNum.setText(num_reduce);
                    try {
                        Number price= NumberFormat.getInstance().parse(nowprice.get(content1));
                        Number num= NumberFormat.getInstance().parse(num_reduce);
                        Number numjilu= NumberFormat.getInstance().parse(jilu.getText().toString());//服务费
                        float changeprice= price.floatValue();
                        float changenum=num.floatValue();
                        float changenum1=numjilu.floatValue();
                        float changeallsale=changeprice*changenum;
                        float changeallsale1=changeprice*changenum+changenum1*changenum;
                        DecimalFormat df = new DecimalFormat("0.00");
                        String all=df.format(changeallsale);
                        String allinstall=df.format(changeallsale1);
                        if(flag==false){
                            Log.i("hashs1", "onClick: "+flag);
                            allnum.setText(allinstall);
                        }else {
                            Log.i("hashs2", "onClick: "+flag);
                            allnum.setText(all);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Toast.makeText(context, "最少为1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.install_rl:
                Log.i("zhuj", "onClick: "+flag);
                if(flag==true){
                    flag=false;
                    install_rl.setBackgroundResource(R.drawable.h5detailborder);
                    noinstall_rl.setBackgroundDrawable(activtiy.getResources().getDrawable(R.drawable.h5detailsnochose));
                    Log.i("caiji", "onClick: "+allnum.getText());
                    String installallname=allnum.getText().toString();
                    String num_add = popNum.getText().toString();
                    try {
                        Number price= NumberFormat.getInstance().parse(installallname);
                        Number num= NumberFormat.getInstance().parse(jilu.getText().toString());
                        Number numadd= NumberFormat.getInstance().parse(num_add);
                        float changeprice= price.floatValue();
                        float changenum=num.floatValue();
                        float numadd1=numadd.floatValue();
                        float changeallsale=changeprice+changenum*numadd1;
                        Log.i("shabie", "onClick: "+changeallsale);
                        DecimalFormat df = new DecimalFormat("0.00");
                        String all=df.format(changeallsale);
                        Log.i("xitian", "onClick: "+all);
                        allnum.setText(all);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.noinstall_rl:
                Log.i("zhuj1", "onClick: "+flag);
                if(flag==false){
                    flag=true;
                    noinstall_rl.setBackgroundResource(R.drawable.h5detailborder);
                    install_rl.setBackgroundDrawable(activtiy.getResources().getDrawable(R.drawable.h5detailsnochose));
                    String num_add = popNum.getText().toString();
                    Log.i("hsulou", "onClick: "+popNum.getText());
                    Log.i("hsulou1", "onClick: "+num_add);
                    String installallname=allnum.getText().toString();
                    Log.i("shabi", "onClick: "+installallname);
                    try {
                        Number price= NumberFormat.getInstance().parse(installallname);
                        Number num= NumberFormat.getInstance().parse(jilu.getText().toString());
                        Number numadd= NumberFormat.getInstance().parse(num_add);
                        float changeprice= price.floatValue();
                        float changenum=num.floatValue();
                        float numadd1=numadd.floatValue();
                        Log.i("hasalou", "onClick: "+changenum);
                        Log.i("hasalou1", "onClick: "+changeprice);
                        Log.i("hasalou2", "onClick: "+numadd1);
                        float changeallsale=changeprice-changenum*numadd1;
                        DecimalFormat df = new DecimalFormat("0.00");
                        String all=df.format(changeallsale);
                        allnum.setText(all);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.sure_result:
                dissmiss();
                if(messaged.equals("购买")){
                    logindenglu();
                }else if(messaged.equals("购物车")){
                    postshop();
                }

                break;
            case R.id.allselect_rl:
                allnum.setText(nowPrice.getText().toString());
                popNum.setText("1");
                break;
            case R.id.describe_iv:
                Intent intent = new Intent(activtiy, h5webviewActivity.class);
                String url="https://h5.aijiaijia.com/blueSir.html";
                intent.putExtra("shoping",url);
                intent.putExtra("xingming","蓝领先生");
                activtiy.startActivity(intent);
                activtiy.finish();
                break;

            default:
                break;
        }
    }

    private void postshop() {
        h5id.clear();
        h5idnum.clear();
        h5id.add(productid.get(0));
        String shuliang=popNum.getText().toString();
        h5idnum.add(shuliang);
        queue = Volley.newRequestQueue(activtiy);
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
                                    if (productid.equals(s7)) {
                                        jieguo = "1";
                                        Toast toast = Toast.makeText(activtiy, "购物车已有该商品", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                                if (!jieguo.equals("1")) {
                                    if (productid!= null) {
                                        queue = Volley.newRequestQueue(activtiy);
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
                                                                Toast.makeText(activtiy, "加入失败", Toast.LENGTH_SHORT).show();
                                                            } else if (result3.equals("1")) {
                                                                // setAnim(v);
                                                                Toast.makeText(activtiy, "加入成功", Toast.LENGTH_SHORT).show();
                                                                postnum();
                                                              /*  postdata();
                                                                postnum();*/

                                                            } else if (result3.equals("6")) {
                                                                Log.i("jiaba", "onResponse: " + result3);
                                                                Toast toast = Toast.makeText(activtiy, "未登录", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                                Intent intent = new Intent(activtiy, LoginActivity.class);
                                                                activtiy.startActivityForResult(intent, 5);
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
                                                map.put("pids", productid.get(0));
                                                String shuliang=popNum.getText().toString();
                                                map.put("nums", shuliang);
                                                map.put("skuids",issku);
                                                try {
                                                    String str4=new String(isname.getBytes("utf-8"),"ISO-8859-1");
                                                    Log.i("feiwu", "getParams: "+str4);
                                                    Log.i("feiwu1", "getParams: "+isname);
                                                    map.put("skutexts",str4);
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                                if(flag==false){
                                                    Log.i("heisae", "getParams: "+flag);
                                                    map.put("isoks","2");
                                                }else {
                                                    map.put("isoks","1");
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
                                    } else {
                                        Toast toast = Toast.makeText(activtiy, "请检查网络!!!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }

                                }
                            } else {
                                Log.i("zhananp", "onResponse: " + "heihei");
                                Toast toast = Toast.makeText(activtiy, "未登录", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent intent = new Intent(activtiy, LoginActivity.class);
                                activtiy.startActivityForResult(intent, 5);
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

    private void postnum() {
        queue = Volley.newRequestQueue(activtiy);
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
                                activtiy.textChartNum.setVisibility(View.VISIBLE);
                                activtiy.newtextChartNum.setVisibility(View.VISIBLE);
                                String result4 = resposeobject.getString("shopcard_num");
                                if (!result4.equals("0")) {
                                    activtiy.textChartNum.setBackgroundResource(R.drawable.bubblet);
                                    activtiy.newtextChartNum.setBackgroundResource(R.drawable.bubblet);
                                    activtiy.newtextChartNum.setText(result4);
                                    activtiy.textChartNum.setText(result4);
                                } else {
                                    activtiy.textChartNum.setVisibility(View.INVISIBLE);
                                    activtiy.newtextChartNum.setVisibility(View.INVISIBLE);
                                }

                            } else {
                                activtiy.textChartNum.setVisibility(View.INVISIBLE);
                                activtiy.newtextChartNum.setVisibility(View.INVISIBLE);


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

    private void logindenglu() {
        if(result13.equals("1")){
            Log.i("tamade", "logindenglu: "+result13);
            h5isok.clear();
            h5skuid.clear();
            h5id.clear();
            h5idnum.clear();
            h5id.add(productid.get(0));
            String shuliang=popNum.getText().toString();
            h5idnum.add(shuliang);
            h5skuid.add(issku);
            if(flag==false){
                h5isok.add("2");
            }else {
                h5isok.add("1");
            }
            DBHelper6 hell = new DBHelper6(activtiy);
            h5shop = hell.queryAll();
            if (h5shop!=null&&h5shop.size() != 0) {
                for (int j = 0; j < h5shop.size(); j++) {
                    hell.delete(h5shop.get(j).get_id());
                }
            }
            if (h5id!= null &&h5id.size() > 0) {
                for (int i = 0; i < h5id.size(); i++) {
                    Log.i("zhanan", "logindenglu: "+h5id);
                    DBHelper6 helper5 = new DBHelper6(activtiy);
                    helper5.insert(new h5gouwu(h5id.get(i),String.valueOf(h5idnum.get(i)),String.valueOf(h5skuid.get(i)),String.valueOf(h5isok.get(i))));
                    Log.i("zhanan1", "logindenglu: "+h5idnum);
                    Log.i("zhanan2", "logindenglu: "+h5skuid);
                    Log.i("zhanan3", "logindenglu: "+h5isok);
                }
            }
            DBHelper6 hell1 = new DBHelper6(activtiy);
            h5shop = hell1.queryAll();
            Log.i("mabisa", "logindenglu: "+h5shop);
            DBHelper5 hel5=new DBHelper5(activtiy);
            goud=hel5.queryAll();
            if(goud.size()!=0){
                for (int i = 0; i <goud.size() ; i++) {
                    hel5.delete(goud.get(i).get_id());
                }
            }
            DBHelper4 hel4=new DBHelper4(activtiy);
            gou2=hel4.queryAll();
            if(gou2.size()!=0){
                for (int j = 0; j <gou2.size() ; j++) {
                    hel4.delete(gou2.get(j).get_id());
                }
            }
            FileUtils1 ggsa = new FileUtils1();
            ggsa.saveDataToFile(activtiy, String.valueOf(h5idnum.get(0)));
            String ss = "请选择";
            String ss1 = "Null";
            String ss2 = "Null";
            String aa = "没有使用积分";
            FileUtils19 qq = new FileUtils19();
            qq.saveDataToFile(activtiy, aa);
            FileUtils21 kk1 = new FileUtils21();
            kk1.saveDataToFile(activtiy, ss2);
            FileUtils20 kk = new FileUtils20();
            kk.saveDataToFile(activtiy, ss1);
            Fileutils18 gg = new Fileutils18();
            gg.saveDataToFile(activtiy, ss);
            FileUtils35 fil=new FileUtils35();
            fil.saveDataToFile(activtiy, "1");
            Intent intent = new Intent(activtiy, h5BaseActivity.class);
            activtiy.startActivity(intent);
            activtiy.finish();
        }else {
            Log.i("tamade1", "logindenglu: "+result13);
            Intent intent = new Intent(activtiy, LoginActivity.class);
            activtiy.startActivityForResult(intent,5);
        }

    }


}
