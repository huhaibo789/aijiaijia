package com.example.administrator.myyushi;

import android.app.Activity;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdp;

public class WelcomeAty extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ViewPagerAdp viewPagerAdp;
    private List<View> views;
    private ImageView dots;
    private int currentIndex=0;
    private List<ImageView> imgArrayList = new ArrayList<ImageView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        initViews();
        initDots();

    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.welcomepager1, null));
        views.add(inflater.inflate(R.layout.welcomepager2, null));
        views.add(inflater.inflate(R.layout.welcomepager3, null));
        views.add(inflater.inflate(R.layout.welcomepager5, null));
        viewPagerAdp = new ViewPagerAdp(views, this);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        viewPager.setAdapter(viewPagerAdp);
        viewPager.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout welcome_dot = (LinearLayout) findViewById(R.id.welcome_dot);
        for (int i = 0; i < views.size(); i++) {
            dots = new ImageView(this);
            dots.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
            dots.setPadding(20, 0, 20, 0);
            if (currentIndex == i) {
                dots.setBackgroundResource(R.mipmap.lunbohigh);
            } else {
                dots.setBackgroundResource(R.mipmap.lunbonom);
            }
            welcome_dot.addView(dots);
            imgArrayList.add(dots);
        }
    }
    public void onPageScrollStateChanged(int arg0) {
    }
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }
    public void onPageSelected(int position) {
        int size = imgArrayList.size();
        for (int i = 0; i < size; i++) {
            imgArrayList.get(position).setBackgroundResource(R.mipmap.lunbohigh);
            if (position != i) {
                imgArrayList.get(i).setBackgroundResource(
                        R.mipmap.lunbonom);
            }
        }
    }
}
