package com.example.administrator.myyushi;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import utils.FileUtils24;

/**
 * Created by misshu on 2017/6/19/019.
 */
public class JSMethod {
    private WebviewActivity activity;
   private String wangzhi;
    public JSMethod(WebviewActivity context,String wangzhi) {
        this.activity = context;
        this.wangzhi=wangzhi;
    }
    @JavascriptInterface
    public void refreshFunction(final String text) {
        Log.i("jkei", "startFunction: " + text);
        if(text!=null){
            Log.i("jkei1", "startFunction: " + "huse");
            FileUtils24 file = new FileUtils24();
            String phone = file.readDataFromFile(activity);
            Log.i("hcsujsude", "refreshFunction: "+phone);
            Log.i("hcsujsude1", "refreshFunction: "+wangzhi);
            if(phone!=null){
                Log.i("hcsujsude2", "refreshFunction: "+wangzhi);
                activity.webview2.loadUrl(wangzhi);
                //activity.webview2.reload();
                activity.webview2.setWebViewClient(new webViewClient());
            }else {
                activity.webview2.loadUrl(wangzhi);
                activity.webview2.setWebViewClient(new webViewClient());
            }




        }
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

        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

               return true;
        }

        @Override
        public void onReceivedError(final WebView view1, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view1, errorCode, description, failingUrl);

        }


    }
}
