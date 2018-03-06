package com.example.administrator.myyushi;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Urlutil.LatitudeConstant;
import Urlutil.Longtitudecookie;
import Urlutil.PermissionsChecker;
import adapter.h5exercise;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import request.LoadingDialog;
import util.Myapp;
import utils.FileUtils29;
import utils.FileUtils37;
import utils.FileUtis36;


public class h5exciseActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {
    @Bind(R.id.activity_rl)
    RelativeLayout activityRl;
    @Bind(R.id.h5exercise_rcv)
    RecyclerView h5exerciseRcv;
    @Bind(R.id.h5return_rl)
    RelativeLayout h5returnRl;
    @Bind(R.id.fenxiang_shuju)
    ImageView fenxiangShuju;
    @Bind(R.id.fujin)
    TextView fujin;
    private RequestQueue queue;
    LoadingDialog dialog;
    ImageLoader loader;
    private h5exercise adapter;
    ArrayList<String> picture = new ArrayList<>(); //活动页的图片
    ArrayList<String> dynamic = new ArrayList<>();//领取动态
    ArrayList<String> jihe = new ArrayList<>();//领取动态
    ArrayList<String> exhibitionpic = new ArrayList<>();//展厅图片
    ArrayList<String> exhibitionaddress = new ArrayList<>();//展厅地址
    ArrayList<String> exhibitionname = new ArrayList<>();//展厅名字
    ArrayList<String> exhibitiondistance = new ArrayList<>();//展厅距离
    ArrayList<String> exhibitionphone = new ArrayList<>();//展厅号码
    ArrayList<String> exhibitiolatitude = new ArrayList<>();//展厅维度
    ArrayList<String> exhibitionlongitude = new ArrayList<>();//展厅经度
    private View v_city;
    String nearshop;
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_PERMISSION = 4;  //权限请求
    static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
    };
    private String message;
    public AMapLocationClientOption mLocationOption = null;   //声明mLocationOption对象，定位参数
    private AMapLocationClient mLocationClient = null;    //声明AMapLocationClient类对象，定位发起端

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5excise);
        ButterKnife.bind(this);
        // mPermissionsChecker = new PermissionsChecker(this);
       /* //检查权限(6.0以上做权限判断)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                startPermissionsActivity();
            }
        }*/
        Intent intent = getIntent();
        FileUtis36 fil = new FileUtis36();
        String thirdliginid = fil.readDataFromFile(h5exciseActivity.this);
        FileUtils37 file = new FileUtils37();
        String telphone = file.readDataFromFile(h5exciseActivity.this);
        Log.i("ganfans", "onCreateView: " + thirdliginid);
        Log.i("ganfans1", "onCreateView: " + telphone);
        if (telphone != null && telphone.equals("nologin") && thirdliginid != null && !thirdliginid.equals("nologin")) {
            Log.i("fsdsdds", "onCreateView: " + telphone);
        } else if (telphone != null && !telphone.equals("nologin") && thirdliginid != null && thirdliginid.equals("nologin")) {
            //加载需要显示的网页
            Log.i("fsdsdds1", "onCreateView: " + telphone);
        } else if (telphone != null && !telphone.equals("nologin") && thirdliginid != null && !thirdliginid.equals("nologin")) {
            Log.i("fsdsdds2", "onCreateView: " + telphone);
        } else {
            Intent intent1 = new Intent(h5exciseActivity.this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }
        nearshop = intent.getStringExtra("nearshop");
        v_city = LayoutInflater.from(h5exciseActivity.this).inflate(R.layout.h5base_login, null);
        queue = Volley.newRequestQueue(h5exciseActivity.this);
        dialog = new LoadingDialog(h5exciseActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        postdata();
       /* dynamic.add("158*****893");
        dynamic.add("147*****689");
        dynamic.add("179*****233");
        dynamic.add("147*****645");
        dynamic.add("138*****897");
        dynamic.add("128*****788");
        dynamic.add("168*****788");
        dynamic.add("118*****598");
        dynamic.add("118*****748");
        dynamic.add("188*****788");
        dynamic.add("138*****498");
        dynamic.add("138*****498");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");
        jihe.add("获取200元现金券");*/
        dialog.dismiss();
        location();
        setlistener();
        StatusBarCompat.setStatusBarColor(h5exciseActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSION,
                PERMISSIONS);
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

    private void setlistener() {
        h5returnRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fenxiangShuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareSDK.initSDK(h5exciseActivity.this);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                oks.setTitle("德国百年卫浴-门店抵用券");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("https://api.aijiaijia.com/m/coupon.html?");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("此券适用于实体门店任何产品，你来我就送！");
                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                oks.setImageUrl("https://imgs.aijiaijia.com/client/custom/2017-06-23/de22eadc5d5f.png");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("https://api.aijiaijia.com/m/coupon.html");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("德国百年卫浴-门店抵用券");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite("德国百年卫浴-门店抵用券");
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("https://api.aijiaijia.com/m/coupon.html?");
                // 启动分享GUI
                oks.show(h5exciseActivity.this);
            }
        });
    }

    private void setadapter() {

        loader = ((Myapp) getApplication()).getLoader();
        adapter = new h5exercise(h5exciseActivity.this, loader, picture, dynamic, jihe, exhibitionpic, exhibitionaddress, exhibitionname, exhibitiondistance, exhibitionphone, exhibitionlongitude, exhibitiolatitude);
        h5exerciseRcv.setLayoutManager(new LinearLayoutManager(h5exciseActivity.this));
        h5exerciseRcv.setAdapter(adapter);
    }

    private void postdata() {
        Log.i("yamade", "postdata: " + "aiya");
        exhibitionname.clear();
        exhibitionpic.clear();
        exhibitiondistance.clear();
        exhibitionaddress.clear();
        exhibitionphone.clear();
        exhibitiolatitude.clear();
        exhibitionlongitude.clear();
        exhibitiondistance.clear();
        jihe.clear();
        dynamic.clear();
        picture.clear();
        queue = Volley.newRequestQueue(h5exciseActivity.this);
        String url = "https://api.aijiaijia.com/api_coupons_hot";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("hahasasda", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list_dispalyhall");
                                JSONArray jsonary1 = resposeobject.getJSONArray("list_coupons");
                                if (jsonarry.length() == 0) {
                                    exhibitionname.add("null");
                                } else {
                                    exhibitionname.clear();
                                }
                                //JSONArray coupon=resposeobject.getJSONArray("list_coupons");
                                JSONArray coupon = resposeobject.getJSONArray("list_coupons_pics");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object1 = jsonarry.getJSONObject(i);
                                    exhibitionpic.add(object1.getString("mendian_image"));
                                    exhibitionname.add(object1.getString("mendian_name"));
                                    exhibitionaddress.add(object1.getString("mendian_address"));
                                    exhibitionphone.add(object1.getString("mendian_phone"));
                                    exhibitionlongitude.add(object1.getString("mendian_longitude"));
                                    exhibitiolatitude.add(object1.getString("mendian_latitude"));
                                    double num = Double.parseDouble(object1.getString("distance"));
                                    Log.i("hahs", "onResponse: " + num);
                                    DecimalFormat df = new DecimalFormat("0.0");
                                    exhibitiondistance.add(String.valueOf(df.format(num / 1000)));
                                }
                                if (coupon.length() == 0) {
                                    Log.i("heihei", "onResponse: " + "aa");
                                } else {
                                    for (int j = 0; j < coupon.length(); j++) {
                                        JSONObject object = coupon.getJSONObject(j);
                                        picture.add(object.getString("trs_coupontype_pic_activity_no"));
                                        picture.add(object.getString("trs_coupontype_pic_activity_yes"));
                                        picture.add(object.getString("trs_coupontype_pic_activity_dialog"));
                                    }
                                }
                                if (jsonary1.length() == 0) {
                                    Log.i("heihei", "onResponse: " + "bb");
                                } else {
                                    for (int k = 0; k < jsonary1.length(); k++) {
                                        JSONObject object1 = jsonary1.getJSONObject(k);
                                        dynamic.add(object1.getString("phone"));
                                        jihe.add(object1.getString("msg"));
                                    }
                                }
                                setadapter();
                               /* if(coupon.length()==0){

                                }else {
                                  for(int j = 0; j <coupon.length() ; j++){
                                      JSONObject object = coupon.getJSONObject(j);
                                      jihe.add(object.getString("msg"));
                                      dynamic.add(object.getString("phone"));
                                  }

                                }*/
                               /* for (int j = 0; j <coupon.length() ; j++) {
                                    JSONObject couponobject = jsonarry.getJSONObject(j);
                                    picture.add(couponobject.getString("trs_coupontype_pic_activity_yes"));
                                }*/

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
                FileUtils29 file = new FileUtils29();
                String message = file.readDataFromFile(h5exciseActivity.this);
                try {
                    String str = new String(message.getBytes("utf-8"), "ISO-8859-1");
                    if (message.equals("all")) {
                        map.put("cityname", "no");
                        map.put("coupontypeid", "6");
                    } else {
                        map.put("cityname", str);
                        map.put("latitude", LatitudeConstant.latitudecookie);
                        map.put("longitude", Longtitudecookie.longtitudecookie);
                        map.put("coupontypeid", "6");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return map;
            }

            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };
        queue.add(post);

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        String aa = aMapLocation.toString();
        String data = new Gson().toJson(aa);
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                LatitudeConstant.latitudecookie = String.valueOf(aMapLocation.getLatitude());//获取纬度
                Longtitudecookie.longtitudecookie = String.valueOf(aMapLocation.getLongitude());//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                message = aMapLocation.getCity();//城市信息
                Log.i("xianshi", "onLocationChanged: " + message);
                if (message != null) {
                    FileUtils29 file = new FileUtils29();
                    file.saveDataToFile(this, message);

                }

            }
            aMapLocation.getDistrict();//城区信息
            aMapLocation.getStreet();//街道信息
            aMapLocation.getStreetNum();//街道门牌号信息
            aMapLocation.getCityCode();//城市编码
            aMapLocation.getAdCode();//地区编码
            // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置

        } else {
            Log.i("weinihao", "onLocationChanged: " + "laile3");
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
            FileUtils29 file = new FileUtils29();
            file.saveDataToFile(this, "all");
            Toast.makeText(h5exciseActivity.this, "定位失败", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
