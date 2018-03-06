package h5Fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

import Urlutil.h5Utils;
import Urlutil.wevb;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import utils.FileUtils28;

/**
 * Created by misshu on 2017/4/20/020.
 */
public class h5bluesirFragement extends Fragment {
    @Bind(R.id.ly_pull)
    LinearLayout lyPull;
    @Bind(R.id.h5bluesir)
    WebView h5bluesir;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.reload_button)
    Button reloadButton;
    @Bind(R.id.blue_rl)
    RelativeLayout blueRl;
    private Handler handle = new Handler();
    private Context context;
    LoadingDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public h5bluesirFragement() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_h5bluesir, container, false);
        ButterKnife.bind(this, view);
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = h5bluesir.getSettings();
        // 启用javascript
        h5bluesir.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //android与Js交互
        h5bluesir.addJavascriptInterface(new JSMethod(context), "android");
        h5bluesir.setVerticalScrollBarEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//s
        webSettings.setUseWideViewPort(true);//s
        webSettings.setLoadWithOverviewMode(true);//s
        webSettings.setSavePassword(true);//s
        webSettings.setSaveFormData(true);//s
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);//s
        h5bluesir.requestFocus();//s
        h5bluesir.getSettings().setDomStorageEnabled(true);
        h5bluesir.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        h5bluesir.loadUrl(h5Utils.TYPE_blue_URL);
        h5bluesir.setWebViewClient(new webViewClient());
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
            h5bluesir.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (text.equals("none")) {
                        h5bluesir.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    } else {
                        h5bluesir.getParent().requestDisallowInterceptTouchEvent(true);
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

        if (h5bluesir != null) {
            try {
                lyPull.removeView(h5bluesir);
                h5bluesir.removeAllViews();
                h5bluesir.destroy();

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

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.dismiss();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wevb wev = new wevb();
            wev.web(context, url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (errorCode == -2) {
             blueRl.setVisibility(View.VISIBLE);
                h5bluesir.setVisibility(View.GONE);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        h5bluesir.setVisibility(View.VISIBLE);
                       blueRl.setVisibility(View.GONE);
                        h5bluesir.loadUrl("https://h5.aijiaijia.com/blueSir.html");
                    }
                });
            }
        }
    }
}
