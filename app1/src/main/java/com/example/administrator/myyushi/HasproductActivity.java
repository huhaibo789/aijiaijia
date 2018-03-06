package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
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
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu3;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.DBHelper5;
import utils.FileUtils23;

public class HasproductActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    @Bind(R.id.city_tv)
    TextView cityTv;
    @Bind(R.id.xiala1)
    ImageView xiala1;
    @Bind(R.id.xiala_ly1)
    RelativeLayout xialaLy1;
    @Bind(R.id.yuyue_ly)
    RelativeLayout yuyueLy;
    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.picture_super1)
    ImageView pictureSuper1;
    @Bind(R.id.juli_tv1)
    TextView juliTv1;
    @Bind(R.id.tiyan_zhangting1)
    TextView tiyanZhangting1;
    @Bind(R.id.liulan1)
    RelativeLayout liulan1;
    @Bind(R.id.zuijin_tv)
    TextView zuijinTv;
    @Bind(R.id.zuiji_ly1)
    RelativeLayout zuijiLy1;

    @Bind(R.id.interst1)
    TextView interst1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.yuyue_iv4)
    ImageView yuyueIv4;
    @Bind(R.id.yuyue_iv5)
    ImageView yuyueIv5;
    @Bind(R.id.yuyue_iv6)
    ImageView yuyueIv6;
    @Bind(R.id.yuyue_iv7)
    ImageView yuyueIv7;
    @Bind(R.id.sum_tv1)
    TextView sumTv1;
    @Bind(R.id.jiantou1)
    ImageView jiantou1;
    @Bind(R.id.yuyue_product1)
    LinearLayout yuyueProduct1;

    @Bind(R.id.arrive)
    TextView arrive;
    @Bind(R.id.view3)
    View view3;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.calendar)
    RelativeLayout calendar;
    @Bind(R.id.arrive1)
    LinearLayout arrive1;
    @Bind(R.id.view4)
    View view4;
    @Bind(R.id.next_but)
    Button nextBut;
    @Bind(R.id.finish_iv1)
    ImageView finishIv1;
    @Bind(R.id.leixing)
    TextView leixing;
    @Bind(R.id.hua)
    TextView hua;
    @Bind(R.id.category_tv)
    RelativeLayout categoryTv;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.ff)
    TextView ff;
    @Bind(R.id.xiandai)
    TextView xiandai;
    @Bind(R.id.fengge_ly)
    RelativeLayout fenggeLy;
    @Bind(R.id.se_button1)
    View seButton1;
    @Bind(R.id.se_button)
   View seButton;
    @Bind(R.id.you_iv)
    ImageView you_iv;
    String address;
    String result4;
    String newresult;
    String weizhi="0";
    private String result2 = "花洒";
    private String result6 = "现代";
    private List<gouwu3> goud = new ArrayList<>();
    private double data;
    private double data1;
    public static final int REQUSET = 1;
    PopupWindow pop;
    private RequestQueue queue;
    private ImageLoader loader;
    private UiSettings mUiSettings;
    private View v_city;
    private String mendian_id;
    private ArrayList<String> picture = new ArrayList<>();
    //AMap是地图对象
    private AMap aMap;
    private String result1;
    private String id;
    private String result3;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private String message = "南京市";
    private String shop;
    private ArrayList<String> ide = new ArrayList<>();
    private ArrayList<String> num = new ArrayList<>();
    private ArrayList<String> pide=new ArrayList<>();
    private ArrayList<String> number=new ArrayList<>();
    private String ids2;
    private String nums = "1";
    private String ww;
    String mendian_latitude;
    String mendian_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasproduct);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ww = intent.getStringExtra("ww");
        if (ww!=null&&ww.equals("1")) {
            yuyueProduct1.setVisibility(View.GONE);
            categoryTv.setVisibility(View.VISIBLE);
            fenggeLy.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        } else {
            yuyueProduct1.setVisibility(View.VISIBLE);
            categoryTv.setVisibility(View.GONE);
            fenggeLy.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);

        }
     /*   pide=(ArrayList<String>) getIntent().getSerializableExtra("shop");
        number=(ArrayList<String>) getIntent().getSerializableExtra("shop1");*/
       shop = intent.getStringExtra("shop");
        id = intent.getStringExtra("id");
        loader = ((Myapp) HasproductActivity.this.getApplication()).getLoader();
        pictureSuper1.setVisibility(View.GONE);
        inittime();
        addview();
        setlistener();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(HasproductActivity.this, ScottmapActivity.class);
                     if(message!=null){
                         intent.putExtra("message", message);
                     }
                   if(result4!=null){
                       intent.putExtra("biaoti",result4);
                   }
                if(newresult!=null){
                    intent.putExtra("address",newresult);
                }
                 if(mendian_latitude!=null){
                     intent.putExtra("mendian_latitude",mendian_latitude);
                 }
                 if(mendian_longitude!=null){
                     intent.putExtra("mendian_longitude",mendian_longitude);
                 }

                    startActivity(intent);
                }
            });
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        }
        //开始定位
        location();
        postproduct();
        if (shop != null && shop.equals("1")) {
            postproduct1();
        }

    }

    private void postproduct1() {
        picture.clear();
        queue = Volley.newRequestQueue(HasproductActivity.this);
        String urls = "https://api.aijiaijia.com/api_appointorder_hasproducts";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu56sdee", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("0")) {
                                Toast.makeText(HasproductActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("product_list");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String product_pic = object.getString("product_pic");
                                    picture.add(product_pic);
                                }
                                if (jsonarry.length() == 1) {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");
                                } else if (jsonarry.length() == 2) {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    yuyueIv5.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(1), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv5.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");
                                    yuyueIv6.setVisibility(View.INVISIBLE);
                                    yuyueIv7.setVisibility(View.INVISIBLE);
                                } else if (jsonarry.length() == 3) {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    yuyueIv5.setVisibility(View.VISIBLE);
                                    yuyueIv6.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(1), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv5.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(2), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv6.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");

                                } else {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    yuyueIv5.setVisibility(View.VISIBLE);
                                    yuyueIv6.setVisibility(View.VISIBLE);
                                    yuyueIv7.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(1), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv5.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(2), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv6.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(3), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv7.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");
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
                        Log.i("dsda", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                DBHelper5 helper5 = new DBHelper5(HasproductActivity.this);
                goud = helper5.queryAll();
                Log.i("heee", "getParams: "+goud.toString());
                for (int i = 0; i < goud.size(); i++) {
                    ide.add(goud.get(i).getProductid());
                    num.add(goud.get(i).getNum());
                }
                String str = ide.toString();
                int len = str.length() - 1;
                String regex = "\\s*,\\s*";
                id = str.substring(1, len).replaceAll(regex, ",");
                map.put("page", "1");
                try {
                    String str1=new String(message.getBytes("utf-8"),"ISO-8859-1");
                    map.put("cityname",  str1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                map.put("pids", id);
                String number = num.toString();
                int len1 = number.length() - 1;
                String regex1 = "\\s*,\\s*";
                nums = number.substring(1, len1).replaceAll(regex1, ",");
                map.put("nums", nums);
                Log.i("huee", "getParams: "+map.toString());
                return map;
            }

        };
        queue.add(post);
    }

    private void inittime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        result1 = format.format(date);
        time.setText(result1);
    }

    private void postproduct() {
        picture.clear();
        queue = Volley.newRequestQueue(HasproductActivity.this);
        String urls = "https://api.aijiaijia.com/api_appointorder_hasproducts";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu56sdee", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            Log.i("heng", "onResponse: " + result3);
                            if (result3.equals("0")) {
                                Toast.makeText(HasproductActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("product_list");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String product_pic = object.getString("product_pic");
                                    picture.add(product_pic);
                                }
                                if (jsonarry.length() == 1) {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");
                                } else if (jsonarry.length() == 2) {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    yuyueIv5.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(1), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv5.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");
                                    yuyueIv6.setVisibility(View.INVISIBLE);
                                    yuyueIv7.setVisibility(View.INVISIBLE);
                                } else if (jsonarry.length() == 3) {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    yuyueIv5.setVisibility(View.VISIBLE);
                                    yuyueIv6.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(1), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv5.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(2), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv6.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");

                                } else {
                                    yuyueIv4.setVisibility(View.VISIBLE);
                                    yuyueIv5.setVisibility(View.VISIBLE);
                                    yuyueIv6.setVisibility(View.VISIBLE);
                                    yuyueIv7.setVisibility(View.VISIBLE);
                                    loader.loadImage(picture.get(0), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv4.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(1), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv5.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(2), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv6.setImageBitmap(loadedImage);
                                        }
                                    });
                                    loader.loadImage(picture.get(3), new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            yuyueIv7.setImageBitmap(loadedImage);
                                        }
                                    });
                                    sumTv1.setText("共" + jsonarry.length() + "件");
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
                        Log.i("dsda", "onErrorResponse: " + error.getMessage());
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

                if (id != null) {
                    map.put("pids", id);
                }
                map.put("nums", "1");
                return map;
            }

        };
        queue.add(post);
    }

    private void setlistener() {
        categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(HasproductActivity.this, HuasaActivity.class);
                startActivityForResult(intent3, 4);

            }
        });
        fenggeLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(HasproductActivity.this, FenggeActivity.class);
                startActivityForResult(intent4, 5);
            }
        });
        yuyueProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shop != null && shop.equals("1")) {
                    Intent intent = new Intent(HasproductActivity.this, AppointmentActivity.class);
                    intent.putExtra("gouwu", "2");
                    intent.putExtra("message", message);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HasproductActivity.this, AppointmentActivity.class);
                    intent.putExtra("gouwu", "3");
                    intent.putExtra("message", message);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });
        finishIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = cityTv.getText().toString();
                if (data.equals("切换")) {
                    Toast.makeText(HasproductActivity.this, "请选择一个城市", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(HasproductActivity.this, InformationActivity.class);
                    intent.putExtra("pids", id);
                    intent.putExtra("num", nums);
                    intent.putExtra("appointshop", mendian_id);
                    intent.putExtra("appointtime", result1);
                    startActivity(intent);
                }
            }
        });
        zuijiLy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(HasproductActivity.this, ZuijinActivity.class);
                intent5.putExtra("infor",message);
                intent5.putExtra("weizhi1",weizhi);
                startActivityForResult(intent5, 6);
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HasproductActivity.this, Calendar1Activity.class);
                startActivityForResult(intent1, REQUSET);
            }
        });
        xialaLy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);
                // pop.showAsDropDown(imvTv);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, 180);

            }
        });
        tiyanZhangting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HasproductActivity.this, AllzhangtingActivity.class);
                Log.i("idmdew", "onClick: " + message);
                intent2.putExtra("message", message);
                startActivity(intent2);
            }
        });

    }

    private void addview() {
        v_city = LayoutInflater.from(this).inflate(R.layout.activity_city, null);
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
                button9.setBackgroundResource(R.drawable.border);
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button2.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#666666"));
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
                button9.setBackgroundResource(R.drawable.border);
                button4.setTextColor(Color.parseColor("#666666"));
                button5.setTextColor(Color.parseColor("#666666"));
                button3.setTextColor(Color.parseColor("#666666"));
                button9.setTextColor(Color.parseColor("#666666"));
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
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
                button9.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
                button5.setBackgroundResource(R.drawable.border);
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
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
                button9.setBackgroundResource(R.drawable.border);
                button1.setBackgroundResource(R.drawable.border);
                button2.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
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
                button9.setBackgroundResource(R.drawable.border);
                button3.setBackgroundResource(R.drawable.border);
                button4.setBackgroundResource(R.drawable.border);
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
        queue = Volley.newRequestQueue(this);
        String urls = "https://api.aijiaijia.com/api_displayhall";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu56sd", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            Log.i("heng", "onResponse: " + result3);
                            if (result3.equals("0")) {
                                Toast.makeText(HasproductActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                if (jsonarry.length() != 0) {
                                    JSONObject object = jsonarry.getJSONObject(0);
                                    mendian_id = object.getString("mendian_id");
                                    String mendian_name = object.getString("mendian_name");
                                    String mendian_address=object.getString("mendian_address");
                                    String mendian_latitude1=object.getString("mendian_latitude");
                                    String mendian_longitude1=object.getString("mendian_longitude");
                                    zuijinTv.setText(mendian_name);
                                    result4=mendian_name;
                                    newresult=mendian_address;
                                    mendian_latitude=mendian_latitude1;
                                    Log.i("fddfff", "onResponse: "+mendian_latitude);
                                    mendian_longitude=mendian_longitude1;
                                    Log.i("ddfdf", "onResponse: "+mendian_longitude);
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
                        Log.i("dsda", "onErrorResponse: " + error.getMessage());
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

                Log.i("yrndf", "getParams: " + message);
                return map;
            }

        };
        queue.add(post);
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

        }

        drawMarkers();

    }

    private void drawMarkers() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(data, data1))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    private void addinformation() {
        if (!(message.equals("南京市") || message.equals("无锡市") || message.equals("武汉市") || message.equals("青岛市") || message.equals("济南市"))) {
            mapView.setVisibility(View.GONE);
            pictureSuper1.setVisibility(View.VISIBLE);
            cityTv.setText(message);
            juliTv1.setText("抱歉，您所在的城市体验店暂未开业。");
            tiyanZhangting1.setText(" ");
            you_iv.setVisibility(View.INVISIBLE);
            zuijinTv.setText("请提交预约，您的专属客服会在24小时内与您联系!");
            tiyanZhangting1.setClickable(false);
            zuijiLy1.setClickable(false);
        } else {
            mapView.setVisibility(View.VISIBLE);
            pictureSuper1.setVisibility(View.GONE);
            juliTv1.setText("离你最近的展厅:");
            tiyanZhangting1.setText("(浏览所有展厅)");
            zuijinTv.setText("");
            tiyanZhangting1.setClickable(true);
            zuijiLy1.setClickable(true);
        }
    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(HasproductActivity.this.getApplicationContext());
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
    public void onResume() {
        super.onResume();
        mapView.onResume();
        StatService.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        StatService.onPause(this);
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        String aa = aMapLocation.toString();
        String data = new Gson().toJson(aa);

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
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
                Log.i("uednc", "onLocationChanged: " + message);
                addinformation();
                FileUtils23 file = new FileUtils23();
                file.saveDataToFile(HasproductActivity.this, message);
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
                Toast.makeText(HasproductActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                message="切换";
                addinformation();

            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3) {
            if (data != null) {
                result1 = data.getStringExtra("result5");
                time.setText(result1);
            }

        } else if (requestCode == 6) {
            if (data != null) {
                newresult=data.getStringExtra("mendian_address");
                weizhi=data.getStringExtra("weizhi");
                result4 = data.getStringExtra("result8");
                mendian_latitude = data.getStringExtra("mendian_latitude");
                mendian_longitude = data.getStringExtra("mendian_longitude");
                mendian_id = data.getStringExtra("mendian_id");
                zuijinTv.setText(result4);
                Double d = Double.parseDouble(mendian_latitude);
                Double d1 = Double.parseDouble(mendian_longitude);
                LatLng marker1 = new LatLng(d, d1);
                //设置中心点和缩放比例
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                drawMarkers();
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

        }

    }

}
