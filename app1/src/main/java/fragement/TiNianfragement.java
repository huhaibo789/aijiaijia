package fragement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.example.administrator.myyushi.AllzhangtingActivity;
import com.example.administrator.myyushi.Calendar1Activity;
import com.example.administrator.myyushi.FenggeActivity;
import com.example.administrator.myyushi.HuasaActivity;
import com.example.administrator.myyushi.InformationActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.ScottmapActivity;
import com.example.administrator.myyushi.ZuijinActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import util.Myapp;
import utils.FileUtils23;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class TiNianfragement extends Fragment implements LocationSource, AMapLocationListener {

    @Bind(R.id.hua)
    TextView hua;
    @Bind(R.id.category_tv)
    RelativeLayout category_tv;
    @Bind(R.id.tiyan_zhangting)
    TextView tiyanZhangting;
    int currentPage = 0;
    int maxPage = 0;
    @Bind(R.id.next_but)
    Button nextbut;
    @Bind(R.id.xiala)
    ImageView xiala;
    @Bind(R.id.calendar)
    RelativeLayout calendar;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.fengge_ly)
    RelativeLayout fenggely;
    @Bind(R.id.xiandai)
    TextView xiandai;
    @Bind(R.id.zuiji_ly)
    RelativeLayout zuiji_ly;
    @Bind(R.id.zuijin_tv)
    TextView zuijin_tv;
    @Bind(R.id.yuyue_ly)
    RelativeLayout yuyueLy;
    @Bind(R.id.liulan)
    RelativeLayout liulan;
    @Bind(R.id.interst)
    TextView interst;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.ff)
    TextView ff;
    @Bind(R.id.arrive)
    TextView arrive;
    @Bind(R.id.view3)
    View view3;
    @Bind(R.id.arrive1)
    LinearLayout arrive1;
    @Bind(R.id.view4)
    View view4;
    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.ditu)
    RelativeLayout ditu;
    @Bind(R.id.xiala_ly)
    RelativeLayout xialaLy;
    @Bind(R.id.city_tv)
    TextView cityTv;
    PopupWindow pop;
    @Bind(R.id.picture_super)
    ImageView pictureSuper;
    @Bind(R.id.juli_tv)
    TextView juliTv;
    @Bind(R.id.yuyue_iv)
    ImageView yuyueIv;
    @Bind(R.id.yuyue_iv1)
    ImageView yuyueIv1;
    @Bind(R.id.yuyue_iv2)
    ImageView yuyueIv2;
    @Bind(R.id.yuyue_iv3)
    ImageView yuyueIv3;
    @Bind(R.id.sum_tv)
    TextView sumTv;
    @Bind(R.id.jiantou)
    ImageView jiantou;
    @Bind(R.id.yuyue_product)
    LinearLayout yuyueProduct;
    @Bind(R.id.tiyan)
    TextView tiyan;
    @Bind(R.id.se_button)
    View seButton;
    @Bind(R.id.ganxing)
    RelativeLayout ganxing;
    @Bind(R.id.leixing)
    TextView leixing;
    @Bind(R.id.se_button1)
    View seButton1;
    @Bind(R.id.yuyue_dao)
    RelativeLayout yuyueDao;
    @Bind(R.id.changecolor)
    LinearLayout changecolor;
    @Bind(R.id.scro)
    ScrollView scro;
    @Bind(R.id.imv_tv)
    TextView imvTv;
    @Bind(R.id.tiyan_iv)
    ImageView tiyan_iv;
    private View v_city;
    String address;
    String result4;
    String newresult;
    String weizhi;
    String mendian_latitude;
    String mendian_longitude;
    LoadingDialog dialog;
    private RequestQueue queue;
    private ImageLoader loader;
    private ArrayList<String> array = new ArrayList<>();
    private ArrayList<String> picture = new ArrayList<>();
    private String numone1;
    private CameraUpdate mUpdata;
    private double mdata;
    private double mdata1;
    private UiSettings mUiSettings;
    public static final int REQUSET = 1;
    private String message;
    private String result3;
    private String mendian_id;
    private String result2 = "花洒";
    private String result6 = "现代";
    private String result1;
    //AMap是地图对象
    private AMap aMap;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    public void update1() {
        addview();
        setlistener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new LoadingDialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tiyan, container, false);
        ButterKnife.bind(this, view);
        loader = ((Myapp) getActivity().getApplication()).getLoader();
        yuyueProduct.setVisibility(View.GONE);
        pictureSuper.setVisibility(View.GONE);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(getContext(), ScottmapActivity.class);
                    intent.putExtra("message", message);
                    intent.putExtra("biaoti",result4);
                    intent.putExtra("address",newresult);
                    intent.putExtra("mendian_latitude",mendian_latitude);
                    intent.putExtra("mendian_longitude",mendian_longitude);
                    Log.i("dddee", "onMapClick: "+message);
                    startActivity(intent);
                }
            });
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        }
        //开始定位
        location();

        return view;
    }

    private void init() {
        // mUiSettings.setMyLocationButtonEnabled(true);
        aMap = mapView.getMap();
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        mUiSettings = aMap.getUiSettings();
        Log.i("ierkm", "init: " + mUiSettings);
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(true);
        if (message.equals("南京市")) {
            ditu.setVisibility(View.VISIBLE);
            arrive1.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            yuyueDao.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            mdata = 31.988361;
            mdata1 = 118.79923;
            LatLng marker1 = new LatLng(mdata, mdata1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            drawMarkers();
        } else if (message.equals("济南市")) {
            ditu.setVisibility(View.VISIBLE);
            arrive1.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            yuyueDao.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            mdata = 36.68492;
            mdata1 = 117.01452;
            LatLng marker1 = new LatLng(mdata, mdata1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            drawMarkers();
        } else if (message.equals("武汉市")) {
            ditu.setVisibility(View.VISIBLE);
            arrive1.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            yuyueDao.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            mdata = 30.57472;
            mdata1 = 114.36025;
            LatLng marker1 = new LatLng(mdata, mdata1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            drawMarkers();
        } else if (message.equals("青岛市")) {
            ditu.setVisibility(View.VISIBLE);
            arrive1.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            yuyueDao.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            mdata = 36.117393;
            mdata1 = 120.40079;
            LatLng marker1 = new LatLng(mdata, mdata1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            drawMarkers();
        } else if (message.equals("无锡市")) {
            ditu.setVisibility(View.VISIBLE);
            arrive1.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            yuyueDao.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            mdata = 31.562445;
            mdata1 = 120.353546;
            LatLng marker1 = new LatLng(mdata, mdata1);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            drawMarkers();
        }else {
            ditu.setVisibility(View.GONE);
           // mapView.setVisibility(View.GONE);
            pictureSuper.setVisibility(View.VISIBLE);
            cityTv.setText(message);
            juliTv.setText("抱歉，您所在的城市体验店暂未开业。");
            tiyanZhangting.setText(" ");
            zuijin_tv.setText("请提交预约，您的专属客服会在24小时内与您联系!");
            tiyanZhangting.setClickable(false);
            zuiji_ly.setClickable(false);
            arrive1.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            yuyueDao.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
        }



    }

    private void drawMarkers() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(mdata, mdata1))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));

        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        result1 = format.format(date);
        time.setText(result1);
        addview();
        setlistener();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        StatService.onPause(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        StatService.onResume(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void addview() {
        v_city = LayoutInflater.from(getContext()).inflate(R.layout.activity_city, null);
        final Button button1 = (Button) v_city.findViewById(R.id.button1);
        final Button button2 = (Button) v_city.findViewById(R.id.button2);
        final Button button3 = (Button) v_city.findViewById(R.id.button3);
        final Button button4 = (Button) v_city.findViewById(R.id.button4);
        final Button button5 = (Button) v_city.findViewById(R.id.button5);
        final Button button9= (Button) v_city.findViewById(R.id.button9);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityTv.setText("南京");
                button1.setTextColor(Color.parseColor("#149985"));
                button1.setBackgroundResource(R.drawable.green_border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#666666"));
                button9.setBackgroundResource(R.drawable.border);
                message = "南京市";
                addinformation();
                init();
                pop.dismiss();
                postdata();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityTv.setText("无锡");
                button2.setTextColor(Color.parseColor("#149985"));
                button2.setBackgroundResource(R.drawable.green_border);
                button1.setTextColor(Color.parseColor("#666666"));
                button1.setBackgroundResource(R.drawable.border);
                button4.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                button9.setBackgroundResource(R.drawable.border);
                message = "无锡市";
                addinformation();
                init();
                pop.dismiss();
                postdata();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityTv.setText("武汉");
                message = "武汉市";
                button3.setTextColor(Color.parseColor("#149985"));
                button1.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#666666"));
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
                button9.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.green_border);
                addinformation();
                init();
                pop.dismiss();
                postdata();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityTv.setText("青岛");
                message = "青岛市";
                button4.setTextColor(Color.parseColor("#149985"));
                button1.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#666666"));
                button5.setBackgroundResource(R.drawable.border);
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
                button9.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.green_border);
                addinformation();
                init();
                pop.dismiss();
                postdata();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityTv.setText("济南");
                message = "济南市";
                button1.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#149985"));
                button4.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#666666"));
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button9.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.green_border);
                addinformation();
                init();
                pop.dismiss();
                postdata();

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cityTv.setText("其它城市");
                message="其它";
                button1.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#149985"));
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                button9.setBackgroundResource(R.drawable.green_border);
                addinformation();
                init();
                pop.dismiss();

            }
        });

    }

    private void postdata() {
        queue = Volley.newRequestQueue(getContext());
        String urls = "https://api.aijiaijia.com/api_displayhall";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu56sder", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            Log.i("heng", "onResponse: " + result3);
                            if (result3.equals("0")) {
                                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                if (jsonarry.length() != 0) {
                                    JSONObject object = jsonarry.getJSONObject(0);
                                    mendian_id = object.getString("mendian_id");
                                    String mendian_name = object.getString("mendian_name");
                                    String mendian_address=object.getString("mendian_address");
                                    String mendian_latitude1=object.getString("mendian_latitude");
                                    String mendian_longitude1=object.getString("mendian_longitude");
                                    zuijin_tv.setText(mendian_name);
                                    weizhi="0";
                                    result4=mendian_name;
                                    newresult=mendian_address;
                                    mendian_latitude=mendian_latitude1;
                                    mendian_longitude=mendian_longitude1;

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", "1");
                try {
                    String str=new String(message.getBytes("utf-8"),"ISO-8859-1");
                    map.put("cityname", str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }

        };
        queue.add(post);
    }

    private void setlistener() {
       /* pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });*/
        nextbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = cityTv.getText().toString();
                if (data.equals("切换")) {
                    Toast.makeText(getContext(), "请选择城市", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), InformationActivity.class);
                    if (result3 != null) {
                        intent.putExtra("zu_id", result3);
                    }
                    if (result2 != null) {
                        intent.putExtra("style", result2);
                    }
                    if (result6 != null) {
                        intent.putExtra("manner", result6);
                    }
                    if (mendian_id != null) {
                        intent.putExtra("appointshop", mendian_id);
                    }
                    if (result1 != null) {
                        intent.putExtra("appointtime", result1);
                    }
                    startActivity(intent);
                }

            }
        });

        cityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);*/
                //  scro.setBackgroundColor(Color.parseColor("#e0000000"));
                pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);
               // pop.showAsDropDown(imvTv);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, 180);
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1.0f);
                    }
                });
                setBackgroundAlpha(0.5f);//设置屏幕透明度
                //pop.showAsDropDown(v);
                //  pop.showAtLocation(v, Gravity.RIGHT, 50, -370);
            }
        });
        xiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);*/
                //  scro.setBackgroundColor(Color.parseColor("#e0000000"));
                pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);

                // pop.showAsDropDown(imvTv);
                pop.setBackgroundDrawable(new BitmapDrawable());
                setBackgroundAlpha(0.5f);
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, 180);
                //pop.showAsDropDown(v);
                //  pop.showAtLocation(v, Gravity.RIGHT, 50, -370);
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1.0f);
                    }
                });
                 setBackgroundAlpha(0.5f);//设置屏幕透明度
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), Calendar1Activity.class);
                startActivityForResult(intent1, REQUSET);

            }
        });
        tiyanZhangting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getContext(), AllzhangtingActivity.class);
                Log.i("idmdew", "onClick: " + message);
                intent2.putExtra("message", message);
                startActivity(intent2);
            }
        });
        category_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getContext(), HuasaActivity.class);
                startActivityForResult(intent3, 4);

            }
        });
        fenggely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getContext(), FenggeActivity.class);
                startActivityForResult(intent4, 5);
            }
        });
        zuiji_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), ZuijinActivity.class);
                intent5.putExtra("infor",message);
                intent5.putExtra("weizhi1",weizhi);
                startActivityForResult(intent5, 6);
            }
        });

        Intent intent1 = getActivity().getIntent();
        Bundle bundle = intent1.getExtras();
        if (bundle != null) {
            numone1 = bundle.getString("numone1");
            time.setText(numone1);
        }
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) getContext()).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) getContext()).getWindow().setAttributes(lp);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3) {
            if (data != null) {
                result1 = data.getStringExtra("result5");
                time.setText(result1);
            }

        } else if (requestCode == 4) {
            if (data != null) {
                result2 = data.getStringExtra("result6");
                Log.i("ggsmi1", "onActivityResult: " + result2);
                result3 = data.getStringExtra("newresult");
                Log.i("suiram", "onActivityResult: " + result3);
                hua.setText(result2);
            }


        } else if (requestCode == 5) {
            if (data != null) {
                result6 = data.getStringExtra("result7");
                xiandai.setText(result6);
            }

        } else if (requestCode == 6) {
            if (data != null) {
                newresult=data.getStringExtra("mendian_address");
                result4 = data.getStringExtra("result8");
                weizhi=data.getStringExtra("weizhi");
                Log.i("huhu", "onActivityResult: "+weizhi);
                mendian_latitude = data.getStringExtra("mendian_latitude");
                 mendian_longitude = data.getStringExtra("mendian_longitude");
                mendian_id = data.getStringExtra("mendian_id");
                zuijin_tv.setText(result4);
                mdata=Double.parseDouble(mendian_latitude);
                mdata1=Double.parseDouble(mendian_longitude);
                // mUiSettings.setMyLocationButtonEnabled(true);
                aMap = mapView.getMap();
                //设置显示定位按钮 并且可以点击
                UiSettings settings = aMap.getUiSettings();
                // 是否显示定位按钮
                settings.setMyLocationButtonEnabled(true);
                aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
                mUiSettings = aMap.getUiSettings();
                Log.i("ierkm", "init: " + mUiSettings);
                mUiSettings.setZoomControlsEnabled(false);
                mUiSettings.setCompassEnabled(true);
                ditu.setVisibility(View.VISIBLE);
                arrive1.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                yuyueDao.setVisibility(View.VISIBLE);
                view4.setVisibility(View.VISIBLE);
           /*     aMap = mapView.getMap();
                //设置显示定位按钮 并且可以点击
                UiSettings settings = aMap.getUiSettings();
                // 是否显示定位按钮
                settings.setMyLocationButtonEnabled(true);
                aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
                mUiSettings = aMap.getUiSettings();
                mUiSettings.setZoomControlsEnabled(false);
                mUiSettings.setCompassEnabled(true);*/
                LatLng marker1 = new LatLng(mdata, mdata1);
                //设置中心点和缩放比例
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                drawMarkers();
            }
        }


    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        String aa = aMapLocation.toString();
        String data = new Gson().toJson(aa);

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                tiyan_iv.setVisibility(View.VISIBLE);
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                message = aMapLocation.getCity();//城市信息
                if(message!=null){
                    try {
                        Thread.sleep(1000);
                        dialog.dismiss();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                addinformation();
                FileUtils23 file = new FileUtils23();
                file.saveDataToFile(getContext(), message);
                if (message.equals("南京市")) {
                    cityTv.setText("南京");
                    init();
                    postdata();
                } else if (message.equals("无锡市")) {
                    cityTv.setText("无锡");
                    init();
                    postdata();
                } else if (message.equals("武汉市")) {
                    cityTv.setText("武汉");
                    init();
                    postdata();
                } else if (message.equals("青岛市")) {
                    cityTv.setText("青岛");
                    init();
                    postdata();

                } else if (message.equals("济南市")) {
                    cityTv.setText("济南");
                    init();
                    postdata();

                }
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    mListener.onLocationChanged(aMapLocation);
                   /* //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(31.562445,120.353546)));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);*/
                    //添加图钉
                    //  aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    isFirstLoc = false;
                    init();
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getContext(), "定位失败", Toast.LENGTH_SHORT).show();
                mapView.setVisibility(View.GONE);
                pictureSuper.setVisibility(View.VISIBLE);
                cityTv.setText("切换");
                juliTv.setText("抱歉，您所在的城市体验店暂未开业。");
                tiyanZhangting.setText(" ");
                zuijin_tv.setText("请提交预约，您的专属客服会在24小时内与您联系!");
                tiyanZhangting.setClickable(false);
                zuiji_ly.setClickable(false);
                tiyan_iv.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        }
    }

    private void addinformation() {
        if (!(message.equals("南京市") || message.equals("无锡市") || message.equals("武汉市") || message.equals("青岛市") || message.equals("济南市"))) {
            mapView.setVisibility(View.GONE);
            pictureSuper.setVisibility(View.VISIBLE);
            cityTv.setText(message);
            juliTv.setText("抱歉，您所在的城市体验店暂未开业。");
            tiyanZhangting.setText(" ");
            zuijin_tv.setText("请提交预约，您的专属客服会在24小时内与您联系!");
            tiyanZhangting.setClickable(false);
            tiyan_iv.setVisibility(View.INVISIBLE);
            zuiji_ly.setClickable(false);
        } else {
            tiyan_iv.setVisibility(View.VISIBLE);
            mapView.setVisibility(View.VISIBLE);
            pictureSuper.setVisibility(View.GONE);
            juliTv.setText("离你最近的展厅:");
            tiyanZhangting.setText("(浏览所有展厅)");
            zuijin_tv.setText("");
            tiyanZhangting.setClickable(true);
            zuiji_ly.setClickable(true);
        }
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

}

