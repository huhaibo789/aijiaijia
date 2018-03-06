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

public class ReceivegoodsActivity extends AppCompatActivity {

    @Bind(R.id.receivegoods_rl)
    RelativeLayout receivegoodsRl;
    @Bind(R.id.receive_name)
    TextView receiveName;
    @Bind(R.id.receive_icon)
    ImageView receiveIcon;
    @Bind(R.id.user_information)
    RelativeLayout userInformation;
    @Bind(R.id.user_view)
    View userView;
    @Bind(R.id.receive_phone)
    TextView receivePhone;
    @Bind(R.id.receivephone_icon)
    ImageView receivephoneIcon;
    @Bind(R.id.userphone_information)
    RelativeLayout userphoneInformation;
    @Bind(R.id.user_view1)
    View userView1;
    @Bind(R.id.receive_address)
    TextView receiveAddress;
    @Bind(R.id.receivecity_icon)
    ImageView receivecityIcon;
    @Bind(R.id.usercity_information)
    RelativeLayout usercityInformation;
    @Bind(R.id.user_view2)
    View userView2;
    @Bind(R.id.receive_cityaddress)
    TextView receiveCityaddress;
    @Bind(R.id.receiveadress_icon)
    ImageView receiveadressIcon;
    @Bind(R.id.useraddress_information)
    RelativeLayout useraddressInformation;
    @Bind(R.id.user_view3)
    View userView3;
    @Bind(R.id.receive_zhifu)
    TextView receiveZhifu;
    @Bind(R.id.receivepay_icon)
    ImageView receivepayIcon;
    @Bind(R.id.userpay_information)
    RelativeLayout userpayInformation;
    @Bind(R.id.user_view4)
    View userView4;
    @Bind(R.id.paycode)
    Button paycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivegoods);
        ButterKnife.bind(this);
        setlistener();
        StatusBarCompat.setStatusBarColor(ReceivegoodsActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void setlistener() {
        receivegoodsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
