package com.example.administrator.myyushi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

import java.io.File;
import java.net.URISyntaxException;

import Urlutil.AmapUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ScottmapActivity extends AppCompatActivity {

    @Bind(R.id.map_view1)
    MapView mapView1;
    @Bind(R.id.scott_iv)
    ImageView scottIv;
    @Bind(R.id.place_tv)
    TextView placeTv;
    @Bind(R.id.buton_gaode)
    Button butonGaode;
    //AMap是地图对象
    private AMap aMap;

    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private LocationSource.OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private String message;
    private UiSettings mUiSettings;
    private double mdata;
    private double mdata1;
    String address;
    String result4;
    String newresult;
    String mendian_latitude;
    String mendian_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scottmap);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        result4 = intent.getStringExtra("biaoti");
        newresult = intent.getStringExtra("address");
        mendian_latitude = intent.getStringExtra("mendian_latitude");
        mendian_longitude = intent.getStringExtra("mendian_longitude");
        setlistener();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView1.onCreate(savedInstanceState);
        if (aMap == null) {
            //显示定位层并且可以触发定位,默认是flase
        }
        //开始定位
        init();
    }

    private void init() {
        // mUiSettings.setMyLocationButtonEnabled(true);
        aMap = mapView1.getMap();
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        mUiSettings = aMap.getUiSettings();
        Log.i("ierkm", "init: " + mUiSettings);
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(true);
        if (mendian_longitude != null && mendian_latitude != null) {
            mdata1 = Double.parseDouble(mendian_longitude);
            mdata = Double.parseDouble(mendian_latitude);
        } else {
            Toast.makeText(ScottmapActivity.this, "不能获取位置", Toast.LENGTH_SHORT).show();
            return;
        }


        LatLng marker1 = new LatLng(mdata, mdata1);
        //设置中心点和缩放比例
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
     /*   if (message.equals("南京市")) {
            data = 31.988361;
            data1 = 118.79923;
            LatLng marker1 = new LatLng(data, data1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        } else if (message.equals("济南市")) {
            data = 36.68492;
            data1 = 117.01452;
            LatLng marker1 = new LatLng(data, data1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        } else if (message.equals("武汉市")) {
            data = 30.57472;
            data1 = 114.36025;
            LatLng marker1 = new LatLng(data, data1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        } else if (message.equals("青岛市")) {
            data = 36.117393;
            data1 = 120.40079;
            LatLng marker1 = new LatLng(data, data1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        } else if (message.equals("无锡市")) {
            data = 31.562445;
            data1 = 120.353546;
            LatLng marker1 = new LatLng(data, data1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        }*/

        drawMarkers();
    }

    private void drawMarkers() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(mdata, mdata1))
                .title(result4 + "\n" + newresult)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    private void setlistener() {
        butonGaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent1 = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=爱即爱家&lat="+ mendian_latitude+ "&lon="+ mendian_longitude+"&dev=0"));
                intent1.setPackage("com.autonavi.minimap");
                if(isInstallByread("com.autonavi.minimap")){
                    startActivity(intent1); //启动调用
                    Log.i("GasStation", "高德地图客户端已经安装") ;

                }else{
                    Intent intent=null;
                    try {
                       intent = Intent.getIntent("intent://map/direction?origin=latlng:"+mendian_latitude+mendian_longitude+"|name:我家&destination="+result4+"&mode=driving®ion="+message+"&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                    if(isInstallByread("com.baidu.BaiduMap")){
                        startActivity(intent); //启动调用
                    }else{
                        Toast.makeText(ScottmapActivity.this, "您手机没有安装任何地图软件", Toast.LENGTH_SHORT).show();
                        Intent intent2= new Intent();
                        intent2.setData(Uri.parse("http://ditu.amap.com/"));//Url 就是你要打开的网址
                        intent2.setAction(Intent.ACTION_VIEW);
                        startActivity(intent2); //启动浏览器
                }

            }
                }





        });
        scottIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isInstallByread(String packageName) {

        return new File("/data/data/" + packageName).exists();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView1.onSaveInstanceState(outState);
    }


}
