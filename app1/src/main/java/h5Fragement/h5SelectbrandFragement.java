package h5Fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

import Urlutil.h5Utils;
import Urlutil.wevb;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import utils.FileUtils28;
import utils.FileUtils29;

/**
 * Created by misshu on 2017/4/17/017.
 */
public class h5SelectbrandFragement extends Fragment {
    int lastX;
    int lastY;
    @Bind(R.id.h5pulltorefresh)
    WebView h5pulltorefresh;
    LoadingDialog dialog;
    @Bind(R.id.remove_ly)
    RelativeLayout removeLy;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.reload_button)
    Button reloadButton;
    @Bind(R.id.select_rl)
    RelativeLayout selectRl;
    //  private cbWebView selectweb;
    //记录手指按下时的横坐标。
    private float xDown;
    private GestureDetector gestureDetector;
    //记录手指移动时的横坐标。
    private float xMove;
    private Context context;
    private String mParam1;
    private String mParam2;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public h5SelectbrandFragement() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_h5selectbrand, container, false);
        ButterKnife.bind(this, view);
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = h5pulltorefresh.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
     /*   if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            h5pulltorefresh.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }*/


      //  webSettings.setLoadsImagesAutomatically(true);
      // webSettings.setBlockNetworkImage(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        FileUtils29 file=new FileUtils29();
        String message=file.readDataFromFile(context);
        h5pulltorefresh.loadUrl(h5Utils.TYPE_select_URL+"?locationcity="+message);
        h5pulltorefresh.addJavascriptInterface(new JSMethod(context), "android");
        h5pulltorefresh.setWebViewClient(new webViewClient());
       // h5pulltorefresh.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        return view;
    }

    //与js交互
    public class JSMethod {
        private Context context;

        public JSMethod(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void startFunction(final String text) {
            Log.i("jkei", "startFunction: " + text);
            h5pulltorefresh.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (text.equals("none")) {
                        h5pulltorefresh.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    } else {
                        h5pulltorefresh.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                }
            });

            FileUtils28 files = new FileUtils28();
            files.saveDataToFile(context, text);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (h5pulltorefresh != null) {
            try {
                removeLy.removeView(h5pulltorefresh);
                h5pulltorefresh.removeAllViews();
                h5pulltorefresh.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ButterKnife.unbind(this);
    }

    class webViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /*h5webs.getContentHeight();//获取html高度
            h5webs.getScale();//手机上网页的缩放比例
            h5webs.getHeight();//webview控件的高度
            float num = h5webs.getContentHeight() * h5webs.getScale(); //得到的是网页在手机上真实的高度
            float mWebViewTotalHeight = h5webs.getContentHeight() * h5webs.getScale() - h5webs.getHeight();//减去webview控件的高度得到的是网页上下可滚动的范围
            Log.i("hu1", "onCreateView: " + h5webs.getContentHeight());
            Log.i("hu2", "onCreateView: " + h5webs.getHeight());
            Log.i("hu3", "onCreateView: " + num);
            Log.i("hu4", "onCreateView: " + mWebViewTotalHeight);*/
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.dismiss();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("haye", "shouldOverrideUrlLoading: " + url);
            wevb wev = new wevb();
            wev.web(context, url);
            return true;
        }

        @Override
        public void onReceivedError(final WebView view1, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view1, errorCode, description, failingUrl);
            if (errorCode == -2) {
                selectRl.setVisibility(View.VISIBLE);
                h5pulltorefresh.setVisibility(View.GONE);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        h5pulltorefresh.setVisibility(View.VISIBLE);
                       selectRl.setVisibility(View.GONE);
                        view1.loadUrl(h5Utils.TYPE_select_URL);
                    }
                });
            }
        }
    }


}
