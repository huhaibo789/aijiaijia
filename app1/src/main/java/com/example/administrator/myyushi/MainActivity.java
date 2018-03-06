package com.example.administrator.myyushi;

import android.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxing.qrcode.CaptureActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import Urlutil.ExampleUtil;
import Urlutil.LatitudeConstant;
import Urlutil.Longtitudecookie;
import Urlutil.PermissionsChecker;
import butterknife.Bind;
import butterknife.ButterKnife;

import cn.jpush.android.api.JPushInterface;
import fragement.Wodefragement;
import h5Fragement.h5FenleiFragement;
import h5Fragement.h5TiNianfragement;
import h5Fragement.h5homepageFragement;
import util.Myapp;
import utils.FileUtils;
import utils.FileUtils29;



public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {
    FragmentManager manager;
    List<Fragment> list = new ArrayList<>();
    Fragment lastfragement;
    @Bind(R.id.it)
    ImageButton it;
    /* @Bind(R.id.et_find)
     EditText etFind;*/
    @Bind(R.id.searh_ib)
    ImageButton searh_ib;
    @Bind(R.id.it1)
    ImageButton it1;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.framelayout)
    FrameLayout framelayout;
    @Bind(R.id.radio_shouye)
    RadioButton radioShouye;
    @Bind(R.id.radio_category)
    RadioButton radioCategory;
    @Bind(R.id.radio_feel)
    RadioButton radioFeel;
    @Bind(R.id.radio_mine)
    RadioButton radioMine;
    @Bind(R.id.bottom_radioGroup)
    RadioGroup bottomRadioGroup;
    private RequestQueue queue;
    private String id;
    private long[] mHits = new long[2];
    private TextView tv;
    private String message;
    public updatepopwindow popWindow;
    ImageLoader loader;
    public AMapLocationClientOption  mLocationOption=null;   //声明mLocationOption对象，定位参数
    private AMapLocationClient  mLocationClient = null;    //声明AMapLocationClient类对象，定位发起端
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_PERMISSION = 4;  //权限请求
  /*  static final String[] PERMISSIONS = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};*/
    static final String[] PERMISSIONSLocation = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA,
            android.Manifest.permission.CALL_PHONE
    };
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.administrator.myyushi.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private MessageReceiver mMessageReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtils29 file=new FileUtils29();
        file.saveDataToFile(this,"all"); //城市定位
        Longtitudecookie.longtitudecookie="0"; //经度
        LatitudeConstant.latitudecookie="0"; //维度
        setContentView(R.layout.activity_main);
        JPushInterface.init(getApplicationContext());
        registerMessageReceiver();
        mPermissionsChecker = new PermissionsChecker(this);
        //检查权限(6.0以上做权限判断)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONSLocation)) {
                startPermissionsActivity1();
                location();//定位
            }else {
                location();//定位
            }
        }
        location();//定位
        /*StatusBarCompat.setStatusBarColor(MainActivity.this, Color.parseColor("#fbd23a"), true);*/
        StatService.setDebugOn(true);
        //我改的setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        initview();
        initFrags();
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.framelayout, list.get(0)).commit();
        setListener();
        /*Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();
        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = i_getvalue.getData();
            if(uri != null){
                Log.i("heiheisa", "onCreate: "+uri);
            }
              *//*  String name = uri.getQueryParameter("name");
                String age= uri.getQueryParameter("age");*//*
            }*/
        // 获取网页传递过来的数据
        Intent intent = getIntent();
        String scheme = intent.getScheme();
        Uri uri = intent.getData();
        Log.i("huhu", "onCreate: "+scheme);
        Log.i("huhsa", "onCreate: "+uri);
        if(uri!=null){
            String host = uri.getHost();
            String dataString = intent.getDataString();
            if(dataString!=null&&dataString.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?pid")){
                String id = uri.getQueryParameter("pid");
                Intent intent2=new Intent(MainActivity.this,h5detailsActivity.class);
                intent2.putExtra("uid",id);
                startActivity(intent2);
            }else if(dataString!=null&&dataString.contains("aijiaijiashop://api.aijiaijia.com/m/index.html?nearshop")){
                String id = uri.getQueryParameter("nearshop");
                Intent intent2=new Intent(MainActivity.this,h5exciseActivity.class);
                intent2.putExtra("nearshop",1);
                startActivity(intent2);
            }
        }

        StatusBarCompat.setStatusBarColor(MainActivity.this, Color.parseColor("#fbd23a"), true);
    }
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }

                }
            } catch (Exception e){
            }
        }
    }
    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void setListener() {
        bottomRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) bottomRadioGroup.findViewById(checkedId);
                int checkedIndex = Integer.parseInt(rb.getTag().toString());
                if (checkedIndex == 0 || checkedIndex == 1) {
                    searchlayout.setVisibility(View.VISIBLE);
                } else {
                    searchlayout.setVisibility(View.GONE);
                }
                if(checkedIndex==0||checkedIndex==1||checkedIndex==2){
                    StatusBarCompat.setStatusBarColor(MainActivity.this, Color.parseColor("#fbd23a"), true);
                }else {
                    StatusBarCompat.setStatusBarColor(MainActivity.this, Color.parseColor("#222222"), true);
                }
                Fragment checkedFragement = list.get(checkedIndex);
                if (!checkedFragement.isAdded()) {
                    manager.beginTransaction().add(R.id.framelayout, checkedFragement).commit();
                } else {
                    if (checkedIndex == 3) {
                        ((Wodefragement) checkedFragement).update5();
                        ((Wodefragement) checkedFragement).update2();
                    }else if(checkedIndex==2){
                        ((h5TiNianfragement) checkedFragement).setlogin();
                    }
                   /* if(checkedIndex==2){
                        ((TiNianfragement)checkedFragement).update1();
                    }else if(checkedIndex==3){
                        ((Wodefragement)checkedFragement).update5();
                        ((Wodefragement)checkedFragement).update2();
                    }*/
                }
                manager.beginTransaction().show(checkedFragement).commit();
                manager.beginTransaction().hide(lastfragement).commit();
                lastfragement = checkedFragement;
            }
        });
        it1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent3 = new Intent(MainActivity.this, h5exciseActivity.class);
                startActivity(intent3);*/
                Intent intent3 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent3);
            }
        });
        searh_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent3 = new Intent(MainActivity.this, h5exciseActivity.class);
                startActivity(intent3);*/
                Intent intent2 = new Intent(MainActivity.this, NewsousuoActivity.class);
                startActivity(intent2);
            }
        });
    }

    private void initFrags() {
        list.add(new h5homepageFragement());
        list.add(new h5FenleiFragement());
        list.add(new h5TiNianfragement());
        list.add(new Wodefragement());
        lastfragement = list.get(0);
    }

    private void initview() {
        bottomRadioGroup = (RadioGroup) findViewById(R.id.bottom_radioGroup);
    }
    /*private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSION,
                PERMISSIONS);
    }*/
    private void startPermissionsActivity1() {
        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSION,
                PERMISSIONSLocation);

    }
    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (SystemClock.uptimeMillis() - mHits[0] <= 500) {
            finish();
        } else {
            Toast.makeText(MainActivity.this, "双击返回按钮退出程序", Toast.LENGTH_SHORT).show();
        }
    }

    public void click(View view) {
        //打开相机
        startActivityForResult(new Intent(this, CaptureActivity.class), 0);
     /*   //检查权限(6.0以上做权限判断)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                startPermissionsActivity();
            } else {

            }
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 8) {
            String result = data.getExtras().getString("result");
            if (result.contains("pid") && result != null) {
                String[] strArray = result.split("=");
                String name = strArray[strArray.length - 1];
                Intent intent = new Intent(this, h5detailsActivity.class);
                intent.putExtra("uid", name);
                startActivity(intent);
            }else {
                Intent inent = new Intent(this, SecondActivity.class);
                inent.putExtra("name", result);
                startActivity(inent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(this);
        StatService.start(this);

    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();

        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(this);

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        String aa = aMapLocation.toString();
        String data = new Gson().toJson(aa);
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                LatitudeConstant.latitudecookie=String.valueOf(aMapLocation.getLatitude());//获取纬度
                Longtitudecookie.longtitudecookie=String.valueOf(aMapLocation.getLongitude());//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                message = aMapLocation.getCity();//城市信息
                Log.i("xianshi", "onLocationChanged: "+message);
                if(message!=null){
                    FileUtils29 file=new FileUtils29();
                    file.saveDataToFile(this,message);

                }

            }
            aMapLocation.getDistrict();//城区信息
            aMapLocation.getStreet();//街道信息
            aMapLocation.getStreetNum();//街道门牌号信息
            aMapLocation.getCityCode();//城市编码
            aMapLocation.getAdCode();//地区编码
            // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置

        }else {
            Log.i("weinihao", "onLocationChanged: "+"laile3");
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
            FileUtils29 file=new FileUtils29();
            file.saveDataToFile(this,"all");
            Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }
}
