package h5Fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class h5colorfullife extends Fragment {
    @Bind(R.id.remove_ly)
    LinearLayout removeLy;
    @Bind(R.id.h5colorful)
    WebView h5colorful;
    LoadingDialog dialog;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.reload_button)
    Button reloadButton;
    @Bind(R.id.color_rl)
    RelativeLayout colorRl;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public h5colorfullife() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_h5colorfullife, container, false);
        ButterKnife.bind(this, view);
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = h5colorful.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webSettings.setDomStorageEnabled(true);
        h5colorful.loadUrl(h5Utils.TYPE_clor_URL);
        h5colorful.addJavascriptInterface(new JSMethod(context), "android");
        //设置Web视图
        h5colorful.setWebViewClient(new webViewClient());
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
            h5colorful.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (text.equals("none")) {
                        h5colorful.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    } else {
                        h5colorful.getParent().requestDisallowInterceptTouchEvent(true);
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
        if (h5colorful != null) {
            try {
                removeLy.removeView(h5colorful);
                h5colorful.removeAllViews();
                h5colorful.destroy();
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
            Log.i("huhus", "shouldOverrideUrlLoading: " + url);
            wevb wev = new wevb();
            wev.web(context, url);
            return true;
        }

        @Override
        public void onReceivedError(final WebView view1, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view1, errorCode, description, failingUrl);
            if (errorCode == -2) {
               colorRl.setVisibility(View.VISIBLE);
                h5colorful.setVisibility(View.GONE);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        h5colorful.setVisibility(View.VISIBLE);
                       colorRl.setVisibility(View.GONE);
                        view1.loadUrl("https://h5.aijiaijia.com/activity.html");
                    }
                });
            }
        }
    }
}
