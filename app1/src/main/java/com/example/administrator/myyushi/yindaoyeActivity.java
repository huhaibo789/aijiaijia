package com.example.administrator.myyushi;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Set;




public class yindaoyeActivity extends AppCompatActivity {
    private Handler handler=new Handler();
    private  Runnable r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_yindaoye);
        r=new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent();
                intent.setClass(yindaoyeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        };
        handler.postDelayed(r,2000);
    }
}
