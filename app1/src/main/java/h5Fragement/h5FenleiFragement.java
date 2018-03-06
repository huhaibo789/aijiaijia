package h5Fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by misshu on 2017/5/4/004.
 */
public class h5FenleiFragement extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.h5fenleiwebview)
    WebView h5fenleiwebview;
    @Bind(R.id.h5fen)
    LinearLayout h5fen;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.reload_button)
    Button reloadButton;
    @Bind(R.id.classfy_rl)
    RelativeLayout classfyRl;
    private String mParam1;
    private String mParam2;
    private Context context;
    LoadingDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public h5FenleiFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_h5fenlei, container, false);
        ButterKnife.bind(this, view);
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = h5fenleiwebview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webSettings.setDomStorageEnabled(true);
        h5fenleiwebview.loadUrl(h5Utils.TYPE_fenlei_URL);
        // h5fenleiwebview.loadUrl("https://h5.aijiaijia.com/blue.html");
        //设置Web视图
        h5fenleiwebview.setWebViewClient(new webViewClient());
        //Web视图
        setlistener();
        return view;
    }

    private void setlistener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (h5fenleiwebview != null) {
            try {
                h5fen.removeView(h5fenleiwebview);
                h5fenleiwebview.removeAllViews();
                h5fenleiwebview.destroy();
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
        public void onReceivedError(final WebView view1, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view1, errorCode, description, failingUrl);
            if (errorCode == -2) {
                classfyRl.setVisibility(View.VISIBLE);
                h5fenleiwebview.setVisibility(View.GONE);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        h5fenleiwebview.setVisibility(View.VISIBLE);
                        classfyRl.setVisibility(View.GONE);
                        view1.loadUrl("https://h5.aijiaijia.com/classify.html");
                    }
                });
            }
        }
    }
}
