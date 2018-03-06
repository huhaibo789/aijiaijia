package com.example.administrator.myyushi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
  private TextView  tv;
    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initview();
        Intent intent=getIntent();
        String data1=intent.getStringExtra("name");
        if(data1!=null){
            if(data1.contains("http")){
                mWebview.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                });
                //初始化网页设置对象
                WebSettings settings = mWebview.getSettings();
                //设置webview支持JavaScript语言
                settings.setJavaScriptEnabled(true);
                mWebview.setWebChromeClient(new WebChromeClient(){

                    @Override
                    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                        Log.i("", "onJsAlert: ");

                        return true;
                    }

                    @Override
                    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                        Log.i("", "onJsConfirm: ");

                        return true;
                    }
                    @Override
                    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                        Log.i("", "onJsPrompt: ");
                        return true;
                    }
                });
                mWebview.loadUrl(data1);
            }else {
                tv.setText(data1);
            }
        }

    }
    private void initview() {
        tv= (TextView) findViewById(R.id.tvSecond);
        mWebview= (WebView) findViewById(R.id.webview);
    }
}
