package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu2;
import bean.gouwu3;
import bean.h5gouwu;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import utils.DBHelper4;
import utils.DBHelper5;
import utils.DBHelper6;
import utils.FileUtils1;
import utils.FileUtils19;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.Fileutils18;

public class h5webviewActivity extends AppCompatActivity {

    @Bind(R.id.end_activity)
    ImageView endActivity;
    @Bind(R.id.text_wenben)
    TextView textWenben;
    @Bind(R.id.weizhi)
    RelativeLayout weizhi;
    @Bind(R.id.h5webview)
    WebView h5webview;
    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_h5webview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String shoping=intent.getStringExtra("shoping");
        Log.i("aiyas", "onCreate: "+shoping);
        String nametitle=intent.getStringExtra("xingming");
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        StatService.bindJSInterface(this, h5webview);
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings =h5webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
       /* webSettings.setDomStorageEnabled(true);//开启DOM stronge 功能
        webSettings.setDatabaseEnabled(true);//开启database stronge api功能
        String catchDirPath=this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();//缓存数据的存储地址
        webSettings.setAppCachePath(catchDirPath);
        webSettings.setAppCacheEnabled(true);//设置开启缓存功能
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//缓存模式
        webSettings.setAllowFileAccess(true);
        Log.i("chulai", "onCreate: "+catchDirPath);*/
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);

        h5webview.loadUrl(shoping);
        textWenben.setText(nametitle);
       h5webview.setWebViewClient(new webViewClient());
        setlistener();
    }

    private void setlistener() {
        endActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);



        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.dismiss();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("haye", "shouldOverrideUrlLoading: " + url);
            //  return super.shouldOverrideUrlLoading(view, url);
           /* view.loadUrl(url);
            return true;*/
            String id = url;
            String[] strArray = id.split("=");
            Log.i("hets", "shouldOverrideUrlLoading: " + url);
            if (url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?weblink")) {
                Log.i("hets1", "shouldOverrideUrlLoading: " + url);
                String title = strArray[strArray.length - 1];
                String name = strArray[strArray.length - 2];
                try {
                    String strUTF8 = URLDecoder.decode(name, "UTF-8");
                    String Title = URLDecoder.decode(title, "UTF-8");
                    Log.i("hfbe", "shouldOverrideUrlLoading: " + strUTF8);
                    Log.i("hfbe1", "shouldOverrideUrlLoading: " + Title);
                    Intent intent = new Intent(h5webviewActivity.this, WebviewActivity.class);
                    Log.i("lailed", "shouldOverrideUrlLoading: " + "ld");
                    intent.putExtra("shoping", strUTF8);
                    intent.putExtra("xingming", Title);
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            } else if (!url.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?weblink")) {
                Log.i("hets2", "shouldOverrideUrlLoading: " + url);
                Intent intent = new Intent(h5webviewActivity.this, h5detailsActivity.class);
                String uid = strArray[strArray.length - 1];
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (errorCode == -2) {
                setContentView(R.layout.activity_noconnect);
                WebView we = (WebView) findViewById(R.id.wv_gif);
                String gifPath = "file:///android_asset/gidf.gif";
                we.loadUrl(gifPath);

            }
        }
    }
}
