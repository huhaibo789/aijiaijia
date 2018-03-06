package com.example.administrator.myyushi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConnectionActivity extends AppCompatActivity {

    @Bind(R.id.advice)
    RelativeLayout advice;
    @Bind(R.id.advice_view)
    View adviceView;
    @Bind(R.id.advice_kefu)
    RelativeLayout adviceKefu;
    @Bind(R.id.advice_view1)
    View adviceView1;
    @Bind(R.id.advice_shop)
    RelativeLayout adviceShop;
    @Bind(R.id.service_view)
    View serviceView;
    @Bind(R.id.service_rl)
    RelativeLayout serviceRl;
    @Bind(R.id.return_ivs)
    ImageView return_ivs;
    public static final String ACTION_CALL = "android.intent.action.CALL";
    @Bind(R.id.connect_kefu)
    ImageView connectKefu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);
        setlistener();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#fbd23a"), true);
    }

    private void setlistener() {
        return_ivs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adviceKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "025-86771486"));
                if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent1);
            }
        });
        adviceShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://h5.aijiaijia.com/7/activity.html";
                Intent intent = new Intent(ConnectionActivity.this, WebviewActivity.class);
                intent.putExtra("name3", url);
                intent.putExtra("xingming", "购物说明");
                startActivity(intent);
            }
        });
        serviceRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = "https://url.aijiaijia.com/service.html";
                Intent intent = new Intent(ConnectionActivity.this, WebviewActivity.class);
                intent.putExtra("name3", url1);
                intent.putExtra("xingming", "售后说明");
                startActivity(intent);
            }
        });
        connectKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dd = "https://api.aijiaijia.com/api_sellorder_list?page=1";
                String ww = "我的";
                String title = "在线客服";
                // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                ConsultSource source = new ConsultSource(dd, ww, "custom information string");
                // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                Unicorn.openServiceActivity(ConnectionActivity.this, // 上下文
                        title, // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(this);
    }
}
