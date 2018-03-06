package adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.PermissionsActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.YouhuiquanActivity;
import com.example.administrator.myyushi.h5exciseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Urlutil.LatitudeConstant;
import Urlutil.Longtitudecookie;
import Urlutil.PermissionsChecker;
import paomadeng.UPMarqueeView;
import utils.FileUtils29;

/**
 * Created by misshu on 2017/6/20/020.
 */
public class h5exercise extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private int mTextCount;
    private ImageLoader loader;
    private h5exciseActivity activity;
    Runnable runnable;
    List<View> views = new ArrayList<>();
    boolean flag=false;
    private Handler mHandler = new Handler();
    ArrayList<String> picture=new ArrayList<>(); //活动页的图片
    ArrayList<String> dynamic=new ArrayList<>();//领取动态
    ArrayList<String> jihe=new ArrayList<>();//领取动态
    ArrayList<String> exhibitionpic=new ArrayList<>();//展厅图片
    ArrayList<String> exhibitionaddress=new ArrayList<>();//展厅地址
    ArrayList<String> exhibitionname=new ArrayList<>();//展厅名字
    ArrayList<String> exhibitiondistance=new ArrayList<>();//展厅距离
    ArrayList<String> exhibitionphone = new ArrayList<>();//展厅号码
    ArrayList<String> exhibitiolatitude = new ArrayList<>();//展厅维度
    ArrayList<String> exhibitionlongitude = new ArrayList<>();//展厅经度
    ImageView daohang_iv;
    PopupWindow pop;
    PopupWindow pop1;
    private RequestQueue queue;
    private View v_city;
    private View bottomshow;
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_PERMISSION = 4;  //权限请求
    static final String[] PERMISSIONS = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.CALL_PHONE,
    };
    private int screenWidth;
    private int screenHeight;
    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public h5exercise(h5exciseActivity context, ImageLoader loader,ArrayList<String> picture,ArrayList<String> dynamic,ArrayList<String> jihe, ArrayList<String> exhibitionpic
            ,ArrayList<String> exhibitionaddress,ArrayList<String> exhibitionname,ArrayList<String> exhibitiondistance,ArrayList<String> exhibitionphone,ArrayList<String> exhibitiolatitude,ArrayList<String> exhibitionlongitude){
        this.activity=context;
        this.loader=loader;
        inflater=LayoutInflater.from(context);
        this.picture=picture;
        this.dynamic=dynamic;
        this.jihe=jihe;
        this.exhibitionpic=exhibitionpic;
        this.exhibitionaddress=exhibitionaddress;
        this.exhibitionname=exhibitionname;
        this.exhibitiondistance=exhibitiondistance;
        this.exhibitionphone=exhibitionphone;
        this.exhibitiolatitude=exhibitiolatitude;
        this.exhibitionlongitude=exhibitionlongitude;
        Log.i("meirew", "h5exercise: "+dynamic.toString());
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new MyHolderType0(inflater.inflate(R.layout.layout_pic_view,parent,false)); //活动页图片
                break;
            case 1:
                holder=new MyHolderType1(inflater.inflate(R.layout.layout_paomadeng_view,parent,false)); //领取动态
                break;
            case 2:
                holder=new MyHolderType2(inflater.inflate(R.layout.layout_distance,parent,false)); //文字离你最近的展厅
                break;
            case 3:
                if(exhibitionname.get(0).equals("null")){
                    holder=new MyHolderType4(inflater.inflate(R.layout.activity_nolocation,parent,false));
                }else {
                    holder=new MyHolderType3(inflater.inflate(R.layout.layout_huodong_view,parent,false));
                }

                break;
        }
        return holder;
    }
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else if(position==2) {
            return 2;
        }else {
            return 3;
        }

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                final   MyHolderType0   holder0= (MyHolderType0) holder;
                //holder0.h5exercise_iv.setImageResource(R.drawable.zhutu);
                loader.loadImage(picture.get(1),new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        holder0.h5exercise_iv.setImageBitmap(loadedImage);
                    }
                });

                v_city = LayoutInflater.from(activity).inflate(R.layout.activity_img, null);
                pop = new PopupWindow(v_city, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT , true);
                ImageView image= (ImageView) v_city.findViewById(R.id.dismiss_iv);
                final ImageView image1= (ImageView) v_city.findViewById(R.id.pop_imag);
                loader.loadImage(picture.get(2),new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        image1.setImageBitmap(loadedImage);
                    }
                });
                image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(activity, YouhuiquanActivity.class);
                        activity.startActivity(intent);
                    }
                });
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pop.dismiss();
                    }
                });
                holder0.h5exercise_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postdata1(holder0);

                    }
                });
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1.0f);
                    }
                });
                break;
            case 1:
                final MyHolderType1 holder1= (MyHolderType1) holder;
                Handler handle=new Handler();
                initview();
                holder1.upview1.setViews(views);
                break;
            case 2:
                final MyHolderType2 holder2= (MyHolderType2) holder;
                holder2.h5wenben_tv.setText("离您最近的展厅:");
                break;
            case 3:
                if(exhibitionname.get(0).equals("null")){
                    final MyHolderType4 holder4= (MyHolderType4) holder;
                    holder4.pic_imag.setImageResource(R.drawable.mendian);
                }else {
                    final MyHolderType3 holder3= (MyHolderType3) holder;
                    //设置滚动的单个布局
                    if(flag==false){
                        for (int i = 0; i <exhibitionname.size() ; i++) {
                            View moreView =  LayoutInflater.from(activity).inflate(R.layout.activity_huodong, null);
                            final ImageView gouwuda_iv = (ImageView) moreView.findViewById(R.id.activity_pic);
                            TextView address_tv = (TextView) moreView.findViewById(R.id.address_tv);
                            daohang_iv = (ImageView) moreView.findViewById(R.id.daohang_iv);
                            TextView address_tv1= (TextView) moreView.findViewById(R.id.address_tv1);
                            final TextView daohang_tv= (TextView) moreView.findViewById(R.id.daohang_tv);
                            TextView distance_tv = (TextView) moreView.findViewById(R.id.distance_tv);
                            ImageView phone_iv = (ImageView) moreView.findViewById(R.id.phone_iv);
                            TextView phones_tv= (TextView) moreView.findViewById(R.id.phones_tv);
                            Log.i("huahua", "onBindViewHolder: "+exhibitionpic);
                            loader.loadImage(exhibitionpic.get(i),new SimpleImageLoadingListener(){
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    gouwuda_iv.setImageBitmap(loadedImage);
                                }
                            });
                            address_tv.setText(exhibitionname.get(i));
                            address_tv1.setText(exhibitionaddress.get(i));
                            distance_tv.setText(exhibitiondistance.get(i)+"km");
                            holder3.allsource_ly.addView(moreView);
                            final int finalI10 = i;
                            final int finalI11 = i;
                            final int finalI12 = i;
                            final int finalI13 = i;
                            final int finalI15 = i;
                            daohang_iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bottomshow = LayoutInflater.from(activity).inflate(R.layout.activity_dibbu, null);
                                    final TextView takebaidu= (TextView) bottomshow.findViewById(R.id.takebaidu);
                                    final TextView takegaode= (TextView) bottomshow.findViewById(R.id.takegaode);
                                    final TextView takewangye= (TextView) bottomshow.findViewById(R.id.takewangye);
                                    final TextView takemiss= (TextView) bottomshow.findViewById(R.id.takemiss);
                                    initScreenWidth();
                                    pop1 = new PopupWindow(bottomshow, screenWidth,screenHeight/2 , true);
                                    setBackgroundAlpha(0.5f);
                                    pop1.setBackgroundDrawable(new BitmapDrawable());
                                    pop1.setOutsideTouchable(true);
                                    pop1.setFocusable(true);
                                    pop1.showAtLocation(bottomshow, Gravity.BOTTOM, 0, 0);
                                    pop1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                        @Override
                                        public void onDismiss() {
                                            setBackgroundAlpha(1f);
                                        }
                                    });
                                    takebaidu.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent2 =null;
                                            double bd_lat = 0.0;
                                            double bd_lon = 0.0;
                                            double location[] = new double[2];   //高德是火星坐标，将高德坐标转换成百度坐标
                                            double x = Double.parseDouble(exhibitionlongitude.get(finalI11)), y = Double.parseDouble(exhibitiolatitude.get(finalI10));
                                            double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
                                            double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
                                            bd_lon = z * Math.cos(theta) + 0.0065;
                                            bd_lat = z * Math.sin(theta) + 0.006;
                                            location[0] = bd_lat;
                                            location[1] = bd_lon;
                                            try {
                                                intent2 = Intent.getIntent("intent://map/direction?" +
                                                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                                                        "destination=latlng:"+location[1]+","+location[0]+"|name:"+ exhibitionaddress.get(finalI15)+    //终点
                                                        "&mode=driving&" +          //导航路线方式
                                                        "&src=爱即爱家#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                                            } catch (URISyntaxException e) {
                                                e.printStackTrace();
                                            }
                                            if(isInstallByread("com.baidu.BaiduMap")){
                                                activity.startActivity(intent2);
                                            }else {
                                                Toast.makeText(activity, "您没安装百度地图", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    takegaode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent1 = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=爱即爱家&lat="+exhibitionlongitude.get(finalI13)+"&lon="+exhibitiolatitude.get(finalI12)+"&dev=0&style=2"));
                                            intent1.setPackage("com.autonavi.minimap");
                                            if(isInstallByread("com.autonavi.minimap")){
                                                activity.startActivity(intent1); //启动调用
                                                Log.i("GasStation", "高德地图客户端已经安装") ;
                                            }else {
                                                Toast.makeText(activity, "您没安装高德地图", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    takewangye.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(activity, "即将前往网页地图", Toast.LENGTH_SHORT).show();
                                            Intent intent3= new Intent();
                                            intent3.setData(Uri.parse("http://ditu.amap.com/"));//Url 就是你要打开的网址
                                            intent3.setAction(Intent.ACTION_VIEW);
                                            activity.startActivity(intent3); //启动浏览器
                                        }
                                    });
                                    takemiss.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            pop1.dismiss();
                                        }
                                    });

                                }
                            });
                            final int finalI14 = i;
                            daohang_tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bottomshow = LayoutInflater.from(activity).inflate(R.layout.activity_dibbu, null);
                                    final TextView takebaidu= (TextView) bottomshow.findViewById(R.id.takebaidu);
                                    final TextView takegaode= (TextView) bottomshow.findViewById(R.id.takegaode);
                                    final TextView takewangye= (TextView) bottomshow.findViewById(R.id.takewangye);
                                    final TextView takemiss= (TextView) bottomshow.findViewById(R.id.takemiss);
                                    initScreenWidth();
                                    pop1 = new PopupWindow(bottomshow, screenWidth,screenHeight/2 , true);
                                    setBackgroundAlpha(0.5f);
                                    pop1.setBackgroundDrawable(new BitmapDrawable());
                                    pop1.setOutsideTouchable(true);
                                    pop1.setFocusable(true);
                                    pop1.showAtLocation(bottomshow, Gravity.BOTTOM, 0, 0);
                                    pop1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                        @Override
                                        public void onDismiss() {
                                            setBackgroundAlpha(1f);
                                        }
                                    });
                                    takebaidu.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent2 =null;
                                            double bd_lat = 0.0;
                                            double bd_lon = 0.0;
                                            double location[] = new double[2];   //高德是火星坐标，将高德坐标转换成百度坐标
                                            double x = Double.parseDouble(exhibitionlongitude.get(finalI11)), y = Double.parseDouble(exhibitiolatitude.get(finalI10));
                                            double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
                                            double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
                                            bd_lon = z * Math.cos(theta) + 0.0065;
                                            bd_lat = z * Math.sin(theta) + 0.006;
                                            location[0] = bd_lat;
                                            location[1] = bd_lon;
                                            try {
                                                intent2 = Intent.getIntent("intent://map/direction?" +
                                                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                                                        "destination=latlng:"+location[1]+","+location[0]+"|name:"+ exhibitionaddress.get(finalI15)+    //终点
                                                        "&mode=driving&" +          //导航路线方式
                                                        "&src=爱即爱家#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                                            } catch (URISyntaxException e) {
                                                e.printStackTrace();
                                            }
                                            if(isInstallByread("com.baidu.BaiduMap")){
                                                activity.startActivity(intent2);
                                            }else {
                                                Toast.makeText(activity, "您没安装百度地图", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    takegaode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent1 = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=爱即爱家&lat="+exhibitionlongitude.get(finalI13)+"&lon="+exhibitiolatitude.get(finalI12)+"&dev=0&style=2"));
                                            intent1.setPackage("com.autonavi.minimap");
                                            if(isInstallByread("com.autonavi.minimap")){
                                                activity.startActivity(intent1); //启动调用
                                                Log.i("GasStation", "高德地图客户端已经安装") ;
                                            }else {
                                                Toast.makeText(activity, "您没安装高德地图", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    takewangye.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(activity, "即将前往网页地图", Toast.LENGTH_SHORT).show();
                                            Intent intent3= new Intent();
                                            intent3.setData(Uri.parse("http://ditu.amap.com/"));//Url 就是你要打开的网址
                                            intent3.setAction(Intent.ACTION_VIEW);
                                            activity.startActivity(intent3); //启动浏览器
                                        }
                                    });
                                    takemiss.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            pop1.dismiss();
                                        }
                                    });
                                }
                            });
                            final int finalI = i;
                            phone_iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +exhibitionphone.get(finalI)));
                                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    activity.startActivity(intent1);
                                }
                            });
                            phones_tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +exhibitionphone.get(finalI)));
                                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    activity.startActivity(intent1);
                                  /*  mPermissionsChecker = new PermissionsChecker(activity);
                                    //检查权限(6.0以上做权限判断)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                                            startPermissionsActivity();
                                        }else {

                                        }
                                    }*/
                                }
                            });
                        }
                        flag=true;
                    }
                }



                break;
        }
    }

    private void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = activity.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(activity, REQUEST_PERMISSION,
                PERMISSIONS);
    }
    private void postdata1(final MyHolderType0 holder0) {
        queue = Volley.newRequestQueue(activity);
        String url = "https://api.aijiaijia.com/api_coupons_add?coupontypeid=6";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("hahasa", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                setBackgroundAlpha(0.5f);
                                pop.setBackgroundDrawable(new BitmapDrawable());
                                pop.setOutsideTouchable(true);
                                pop.setFocusable(true);
                                pop.showAtLocation(v_city, Gravity.CENTER, 0, 0);
                                loader.loadImage(picture.get(0),new SimpleImageLoadingListener(){
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        super.onLoadingComplete(imageUri, view, loadedImage);
                                        holder0.h5exercise_iv.setImageBitmap(loadedImage);
                                    }
                                });
                            }else if(result3.equals("11")){
                                Toast.makeText(activity, "暂时不能领取", Toast.LENGTH_SHORT).show();
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



    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }
    private boolean isInstallByread(String packageName) {

        return new File("/data/data/" + packageName).exists();

    }
    private void initview() {
        for (int i = 0; i < dynamic.size(); i = i + 6) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView phone_number = (TextView) moreView.findViewById(R.id.phone_number);
            TextView phone2_number = (TextView) moreView.findViewById(R.id.phone2_number);
            TextView phone3_number = (TextView) moreView.findViewById(R.id.phone3_number);
            TextView phone_number1= (TextView) moreView.findViewById(R.id.phone_number1);
            TextView phone2_number1 = (TextView) moreView.findViewById(R.id.phone2_number1);
            TextView phone3_number1 = (TextView) moreView.findViewById(R.id.phone3_number1);
            //进行对控件赋值
            phone_number.setText(dynamic.get(i).toString()+"  "+jihe.get(i).toString());
            if (dynamic.size() > i + 1) {
                phone_number1.setText(dynamic.get(i+1).toString()+"  "+jihe.get(i+1).toString());
                if(dynamic.size()>i+2){
                    phone2_number.setText(dynamic.get(i+2).toString()+"  "+jihe.get(i+2).toString());
                    if(dynamic.size()>i+3){
                        phone2_number1.setText(dynamic.get(i+3).toString()+"  "+jihe.get(i+3).toString());
                        if(dynamic.size()>i+4){
                            phone3_number.setText(dynamic.get(i+4).toString()+"  "+jihe.get(i+4).toString());
                            if(dynamic.size()>i+5){
                                phone3_number1.setText(dynamic.get(i+5).toString()+"  "+jihe.get(i+5).toString());
                            }else {
                                phone3_number1.setVisibility(View.GONE);
                            }
                        }else {
                            phone3_number.setVisibility(View.GONE);
                            phone3_number1.setVisibility(View.GONE);
                        }
                    }else {
                        phone2_number1.setVisibility(View.GONE);
                        phone3_number.setVisibility(View.GONE);
                        phone3_number1.setVisibility(View.GONE);
                    }
                }else {
                    phone3_number.setVisibility(View.GONE);
                    phone2_number1.setVisibility(View.GONE);
                    phone3_number1.setVisibility(View.GONE);
                    phone2_number.setVisibility(View.GONE);
                }
            } else {
                phone2_number.setVisibility(View.GONE);
                phone3_number.setVisibility(View.GONE);
                phone2_number1.setVisibility(View.GONE);
                phone3_number1.setVisibility(View.GONE);
                phone_number1.setVisibility(View.GONE);
            }
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    @Override
    public int getItemCount() {
        int count=exhibitionname==null?0:exhibitionname.size()+3;
        return count;
    }
    class MyHolderType0 extends  RecyclerView.ViewHolder{
        ImageView h5exercise_iv;
        public MyHolderType0(View itemView) {
            super(itemView);
            h5exercise_iv= (ImageView) itemView.findViewById(R.id.h5exercise_iv);
        }
    }
    class MyHolderType1 extends RecyclerView.ViewHolder{

        private TextView tbtv;
        private UPMarqueeView upview1;
        public MyHolderType1(View itemView) {
            super(itemView);
            tbtv= (TextView) itemView.findViewById(R.id.tbtv);
            upview1= (UPMarqueeView) itemView.findViewById(R.id.upview1);
        }
    }
    class  MyHolderType2 extends RecyclerView.ViewHolder{
        private TextView h5wenben_tv;
        public MyHolderType2(View itemView) {
            super(itemView);
            h5wenben_tv= (TextView) itemView.findViewById(R.id.h5wenben_tv);

        }
    }
    class  MyHolderType3 extends RecyclerView.ViewHolder{
        private LinearLayout allsource_ly;
        public MyHolderType3(View itemView) {
            super(itemView);
            allsource_ly= (LinearLayout) itemView.findViewById(R.id.allsource_ly);

        }
    }
    class  MyHolderType4 extends RecyclerView.ViewHolder{
        private TextView wen_ben,wen_bentv;
        private ImageView pic_imag;
        public MyHolderType4(View itemView) {
            super(itemView);
            wen_ben= (TextView) itemView.findViewById(R.id.wenben);
            wen_bentv= (TextView) itemView.findViewById(R.id.wen_bentv);
            pic_imag= (ImageView) itemView.findViewById(R.id.pic_imag);
        }
    }
}
