package h5Fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
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
public class h5KeyhomeFragement extends Fragment {
    int lastX;
    int lastY;

    @Bind(R.id.remove_ly)
    LinearLayout removeLy;
    @Bind(R.id.h5keyhome)
    WebView h5keyhome;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.reload_button)
    Button reloadButton;
    @Bind(R.id.key_rl)
    RelativeLayout keyRl;

    //记录手指按下时的横坐标。
    private float xDown;
    private GestureDetector gestureDetector;
    //记录手指移动时的横坐标。
    private float xMove;
    private Context context;
    private String mParam1;
    private String mParam2;
    LoadingDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public h5KeyhomeFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_h5keyhome, container, false);
        ButterKnife.bind(this, view);
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = h5keyhome.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
       /* //加载需要显示的网页
        h5keyhome.loadUrl("https://h5.aijiaijia.com/goujia2.html");*/
        h5keyhome.loadUrl(h5Utils.TYPE_create_URL);
        h5keyhome.addJavascriptInterface(new JSMethod(context), "android");
        //设置Web视图
        h5keyhome.setWebViewClient(new webViewClient());
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
            Log.i("jkeid", "startFunction: " + text);
            h5keyhome.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (text.equals("none")) {
                        h5keyhome.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    } else {
                        h5keyhome.getParent().requestDisallowInterceptTouchEvent(true);
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
        if (h5keyhome != null) {
            try {
                removeLy.removeView(h5keyhome);
                h5keyhome.removeAllViews();
                h5keyhome.destroy();
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
            Log.i("hudshdsu", "shouldOverrideUrlLoading: " + url);
            wevb wev = new wevb();
            wev.web(context, url);
            return true;
        }

        @Override
        public void onReceivedError(final WebView view1, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view1, errorCode, description, failingUrl);
            if (errorCode == -2) {
               keyRl.setVisibility(View.VISIBLE);
                h5keyhome.setVisibility(View.GONE);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        h5keyhome.setVisibility(View.VISIBLE);
                        keyRl.setVisibility(View.GONE);
                        view1.loadUrl("https://h5.aijiaijia.com/fastCreate.html");
                    }
                });
            }
        }
    }
}
