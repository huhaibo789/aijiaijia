package com.example.administrator.myyushi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class withdrawalActivity extends AppCompatActivity {

    @Bind(R.id.zhifu_rl)
    RelativeLayout zhifuRl;
    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.ali_zhifubao)
    RelativeLayout aliZhifubao;
    @Bind(R.id.ali_view)
    View aliView;
    @Bind(R.id.icon_money)
    ImageView iconMoney;
    @Bind(R.id.ali_money)
    RelativeLayout aliMoney;
    @Bind(R.id.apply_money)
    Button applyMoney;
    @Bind(R.id.apply_success)
    TextView applySuccess;
    @Bind(R.id.sucess_apply)
    RelativeLayout sucessApply;
    @Bind(R.id.ali_view1)
    View aliView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal2);
        ButterKnife.bind(this);
        setlistener();
        StatusBarCompat.setStatusBarColor(withdrawalActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void setlistener() {
        zhifuRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        aliZhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sucessApply.setVisibility(View.VISIBLE);
                aliZhifubao.setVisibility(View.VISIBLE);
                aliMoney.setVisibility(View.VISIBLE);
                aliView.setVisibility(View.VISIBLE);
                applyMoney.setVisibility(View.VISIBLE);
                sucessApply.setVisibility(View.GONE);
            }
        });
        aliMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sucessApply.setVisibility(View.VISIBLE);
                aliZhifubao.setVisibility(View.VISIBLE);
                aliMoney.setVisibility(View.VISIBLE);
                aliView.setVisibility(View.VISIBLE);
                applyMoney.setVisibility(View.VISIBLE);
                sucessApply.setVisibility(View.GONE);
            }
        });
        applyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sucessApply.setVisibility(View.GONE);
                aliZhifubao.setVisibility(View.GONE);
                aliMoney.setVisibility(View.GONE);
                aliView.setVisibility(View.GONE);
                applyMoney.setVisibility(View.GONE);
                aliView1.setVisibility(View.GONE);
                sucessApply.setVisibility(View.VISIBLE);
            }
        });
    }


}
