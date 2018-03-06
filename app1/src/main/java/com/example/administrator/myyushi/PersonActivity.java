package com.example.administrator.myyushi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import Urlutil.PermissionsChecker;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import h5powendow.SelectPicPopupWindow;
import uploadpicture.CircleImg;
import uploadpicture.UploadUtil;
import uploadpicture.fileut;
import uploadpicture.uploadfile;
import util.Myapp;
import utils.FileUtils24;
import utils.FileUtils26;
import utils.FileUtils34;
import utils.FileUtils38;
import utils.FileUtils39;
import utils.FileUtils40;
import utils.FileUtils41;
import utils.FileUtils42;

public class PersonActivity extends AppCompatActivity implements UploadUtil.OnUploadProcessListener, PlatformActionListener {
    @Bind(R.id.user)
    TextView user;
    @Bind(R.id.nicheng)
    RelativeLayout nicheng;
    @Bind(R.id.information)
    TextView information;
    @Bind(R.id.xingbie)
    RelativeLayout xingbie;
    @Bind(R.id.return_iv)
    ImageView returniv;
    @Bind(R.id.chaoyou)
    ImageView chaoyou;
    @Bind(R.id.chaoyou1)
    ImageView chaoyou1;
    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.chaoyou2)
    ImageView chaoyou2;
    @Bind(R.id.changepassword)
    RelativeLayout changepassword;
    @Bind(R.id.pic_login)
    CircleImg picLogin;
    @Bind(R.id.pic_ry)
    RelativeLayout picRy;
    @Bind(R.id.pop_show)
    LinearLayout popShow;
    @Bind(R.id.phone_binding)
    TextView phoneBinding;
    @Bind(R.id.qq_binding)
    TextView qqBinding;
    @Bind(R.id.wechat_binding)
    TextView wechatBinding;
    @Bind(R.id.sina_binding)
    TextView sinaBinding;
    @Bind(R.id.phone_rl)
    RelativeLayout phoneRl;
    @Bind(R.id.qq_rl)
    RelativeLayout qqRl;
    @Bind(R.id.weixin_rl)
    RelativeLayout weixinRl;
    @Bind(R.id.sina_rl)
    RelativeLayout sinaRl;
    @Bind(R.id.return_rl)
    RelativeLayout returnRl;
    @Bind(R.id.sinaweibo_iv)
    ImageView sinaweiboIv;
    @Bind(R.id.qq_iv)
    ImageView qqIv;
    @Bind(R.id.weixin_iv)
    ImageView weixinIv;
    private String numone;
    String nickname;
    String isphone;
    String gender;
    String sessionid;
    private RequestQueue queue;
    private SelectPicPopupWindow menuWindow;
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private static final int UPLOAD_INIT_PROCESS = 4;//上传初始化
    protected static final int UPLOAD_FILE_DONE = 2;//上传中
    private static final int UPLOAD_IN_PROCESS = 5;//上传文件响应
    private File filepath;//返回的文件地址
    private ProgressDialog pd;
    private String urlpath="1";            // 图片本地路径
    private String resultStr = "";    // 服务端返回结果集
    ImageLoader loader;
    String userId; //用户Id
    String name = null;
    Platform wechat;
    String unionid;
    String qquniode;
    Platform qq;
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_PERMISSION = 4;  //权限请求
    static final String[] PERMISSIONS = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
         };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        ShareSDK.initSDK(PersonActivity.this);
        mPermissionsChecker = new PermissionsChecker(this);
        //检查权限(6.0以上做权限判断)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                startPermissionsActivity();
            }
        }
        loader = ((Myapp) PersonActivity.this.getApplication()).getLoader();
        postshuju();
        setlistener();
        StatusBarCompat.setStatusBarColor(PersonActivity.this, Color.parseColor("#fbd23a"), true);
    }
    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSION,
                PERMISSIONS);
    }
    private void postshuju() {
        queue = Volley.newRequestQueue(PersonActivity.this);
        String url = "https://api.aijiaijia.com/api_userinfo";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjianef", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                JSONObject object = resposeobject.getJSONObject("list_user");
                                nickname = object.getString("nickname");
                                user.setText(nickname);
                                isphone = object.getString("phone");
                                if (!isphone.equals("null")) {
                                    changepassword.setVisibility(View.VISIBLE);
                                    phoneBinding.setText("已绑定");
                                    phoneBinding.setTextColor(Color.parseColor("#eaeaea"));
                                    phoneBinding.setClickable(false);
                                } else {
                                    changepassword.setVisibility(View.GONE);
                                    phoneBinding.setText("绑定");
                                    phoneBinding.setTextColor(Color.parseColor("#fbd23a"));
                                    phoneBinding.setClickable(true);
                                }
                                gender = object.getString("gender");
                                information.setText(gender);
                                String qqbind = object.getString("qq");
                                if (!qqbind.equals("null")) {
                                    qqBinding.setText("已绑定");
                                    qqBinding.setTextColor(Color.parseColor("#eaeaea"));
                                    qqBinding.setClickable(false);
                                } else {
                                    qqBinding.setText("绑定");
                                    qqBinding.setTextColor(Color.parseColor("#fbd23a"));
                                    qqBinding.setClickable(true);
                                }
                                String wexin = object.getString("weixin");
                                Log.i("cainiaods", "onResponse: " + wexin);
                                if (!wexin.equals("null")) {
                                    wechatBinding.setText("已绑定");
                                    wechatBinding.setTextColor(Color.parseColor("#eaeaea"));
                                    wechatBinding.setClickable(false);
                                } else {
                                    wechatBinding.setText("绑定");
                                    wechatBinding.setTextColor(Color.parseColor("#fbd23a"));
                                    wechatBinding.setClickable(true);
                                }
                                String sina = object.getString("sina");
                                Log.i("cainiao1", "onResponse: " + sina);
                                if (!sina.equals("null")) {
                                    sinaBinding.setText("已绑定");
                                    sinaBinding.setTextColor(Color.parseColor("#eaeaea"));
                                    sinaBinding.setClickable(false);
                                } else {
                                    sinaBinding.setText("绑定");
                                    sinaBinding.setTextColor(Color.parseColor("#fbd23a"));
                                    sinaBinding.setClickable(true);
                                }
                                final String userheadpics = object.getString("user_head_pics");
                                loader.loadImage(userheadpics, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        super.onLoadingComplete(imageUri, view, loadedImage);
                                        picLogin.setImageBitmap(loadedImage);
                                    }
                                });

                            } else if (result3.equals("6")) {
                                Toast.makeText(PersonActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PersonActivity.this, LoginActivity.class);
                                startActivity(intent);

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

    private void setlistener() {
        phoneBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonActivity.this, bindingActivity.class);
                startActivityForResult(intent, 20);
            }
        });
        qqBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qq = ShareSDK.getPlatform(PersonActivity.this,QQ.NAME);
                //qq.SSOSetting(true);
                if(qq.isAuthValid()){
                    qq.removeAccount(true);
                }
                authorize(qq);


            }
        });
        wechatBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechat = ShareSDK.getPlatform(PersonActivity.this, Wechat.NAME);
                if(wechat.isAuthValid()){
                    wechat.removeAccount(true);
                }
               /* wechat.SSOSetting(true);

                if (!wechat.isClientValid()) {

                    Toast.makeText(
                            PersonActivity.this,

                            "微信未安装,请先安装微信",

                            Toast.LENGTH_LONG).show();

                }
*/
                authorize(wechat);

            }
        });
        sinaBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform sinweibo = ShareSDK.getPlatform(PersonActivity.this, SinaWeibo.NAME);
                if(sinweibo.isAuthValid()){
                    sinweibo.removeAccount(true);
                }
               /* sinweibo.SSOSetting(true);

                if (!sinweibo.isClientValid()) {

                    Toast.makeText(PersonActivity.this,
                            "微博未安装,请先安装微博",
                            Toast.LENGTH_LONG).show();
                }*/

                authorize(sinweibo);

            }
        });
        picLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuWindow = new SelectPicPopupWindow(PersonActivity.this, itemsOnClick);
                menuWindow.showAtLocation(popShow,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonActivity.this, XiugaiActivity.class);
                startActivity(intent);
            }
        });
        nicheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this, ChenhuActivity.class);
                intent.putExtra("chenhu", nickname);
                startActivityForResult(intent, 6);
            }
        });
        returnRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent();
                setResult(30, intent5);
                finish();
            }
        });
        xingbie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this, SexActivity.class);
                intent.putExtra("sex", gender);
                startActivityForResult(intent, 7);

            }
        });


    }

    private void getQQUnionid(Platform qq) {
        StringRequest getString = new StringRequest(
                StringRequest.Method.GET,  //设置请求方式（如果是get请求，那么此参数可省略）
                "https://graph.qq.com/oauth2.0/me?access_token=" + qq.getDb().getToken()+"&unionid=1", //设置连接网址
                new Response.Listener<String>() {
                    //一旦成功读取此数据后，调用此方法
                    //参数：网络连接读取结果
                    @Override
                    public void onResponse(String response) {
                        Log.i("chusnegbs", "onResponse: success!!  " + response);
                        String[] strArray = response.split("unionid\":\"");
                        String name=strArray[strArray.length-1];
                        String[] zifu=name.split("\"");
                        qquniode=zifu[zifu.length-2];
                        postqqbind();
                        Log.i("huhuh", "onResponse: "+qquniode);
                      /*  String[] strAry1=name.split("}");
                        String uid=strAry1[strAry1.length-2];*/
                  /*      Log.i("huhess", "onResponse: "+uid);*/

                    }
                },
                new Response.ErrorListener() {
                    //一旦连接出错，调用此方法，
                    //参数：出错原因
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("chuesee", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        //3. 将请求对象添加到请求队列中执行加载操作
        queue.add(getString);
    }

    //第三方登录要数据不要功能
    private void authorize(Platform plat) {
        if (plat == null) {

            return;

        }
        plat.setPlatformActionListener(PersonActivity.this);

//开启SSO授权
        // plat.SSOSetting(false);
        plat.SSOSetting(false);
        // plat.authorize();
        plat.showUser(null);
    }

    private void postqqbind() {
        queue = Volley.newRequestQueue(PersonActivity.this);
        String url = "https://api.aijiaijia.com/api_thirdbingtoway";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfs", "onResponse: post  success " + response);
                        String ss = response.toString().trim();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            Log.i("oidsmds", "onResponse: " + response.toString());
                            String result = resposeobject.getString("op");
                            Log.i("kdffd", "onResponse: " + result);
                            if (result.equals("0")) {
                                Toast toast = Toast.makeText(PersonActivity.this, "绑定失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            } else if (result.equals("1")) {
                                Toast.makeText(PersonActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                                if (name.equals("qq")) {
                                    postshuju();
                                    qqBinding.setClickable(false);
                                } else if (name.equals("weixin")) {
                                    postshuju();
                                    wechatBinding.setClickable(false);
                                } else if (name.equals("sina")) {
                                    postshuju();
                                    sinaBinding.setClickable(false);
                                }

                            } else if (result.equals("2")) {
                                Toast toast = Toast.makeText(PersonActivity.this, "用户名已绑定", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (result.equals("3")) {
                                Toast toast = Toast.makeText(PersonActivity.this, "验证码错误", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            } else if (result.equals("4")) {
                                Toast.makeText(PersonActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
                            }else {

                                Toast toast = Toast.makeText(PersonActivity.this, "绑定失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
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
                        Toast toast = Toast.makeText(PersonActivity.this, "网络错误", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("toway", name);
                if(name.equals("weixin")&&unionid!=null){
                    map.put("touid",unionid);
                }else if(name.equals("qq")&&qquniode!=null){
                    map.put("touid", qquniode);
                }else {
                    map.put("touid",userId);
                }

                Log.i("zhuyiyang", "getParams: " + map.toString());
                return map;
            }
            //重写parseNetworkResponse方法，返回的数据中 Set-Cookie:***************;
            //其中**************为session id

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

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {

                case R.id.takePhotoBtn:   //拍照
                    getCamera();
                    break;

                case R.id.pickPhotoBtn:   //从相册选择
                    getPhoto();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 从相册选择图片来源
     */
    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    /**
     * 从系统相机选择图片来源
     */
    private void getCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "hand.jpg")));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /****
     * 调用系统自带切图工具对图片进行裁剪
     * 微信也是
     *
     * @param uri
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    /**
     * 上传图片到服务器
     */
    private void toUploadFile() {
        /*pd = ProgressDialog.show(PersonActivity.this, "", "正在上传文件...");
        pd.show();*/
        String fileKey = "upload";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(PersonActivity.this); //设置监听器监听上传状态
        Map<String, String> params = new HashMap<String, String>();//上传map对象
        params.put("userId", "");
        //uploadUtil.uploadFile(fileKey, "https://api.aijiaijia.com/api_upload");
        uploadUtil.uploadFile(filepath, fileKey, "https://api.aijiaijia.com/api_upload");
        //  Toast.makeText(mcontext, "上传成功", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 7) {
            if (data != null) {
                String result2 = data.getStringExtra("result3");
                postshuju();

            }
        } else if (resultCode == 6) {
            if (data != null) {
                String result2 = data.getStringExtra("result4");
                postshuju();
            }
        } else if (resultCode == 20) {
            if (data != null) {
                postshuju();
            }

        }
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory()
                                + "/hand.jpg");//保存图片
                        if (file.exists()) {
                            //对相机拍照照片进行裁剪
                            photoClip(Uri.fromFile(file));
                        }
                }
                break;

            case PHOTO_REQUEST://从相册取
                if (data != null) {
                    Uri uri = data.getData();
                    //对相册取出照片进行裁剪
                    photoClip(uri);

                }
                break;
            case PHOTO_CLIP:
                Log.i("heihies2", "onActivityResult: "+PHOTO_CLIP);
                Log.i("heihies3", "onActivityResult: "+data.toString());
                //完成
                if (data != null) {

                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");

                            Log.i("zhuyiyang", "onActivityResult: "+urlpath);
                          //  urlpath = fileut.saveFile(PersonActivity.this, "temphead.jpg", photo);
                            try {
                                //获得图片路径
                                filepath = UploadUtil.saveFile(photo, Environment.getExternalStorageDirectory().toString(), "hand.jpg");
                                Log.i("hasake", "onActivityResult: " + filepath);
                                // 开启子线程
                                new Thread() {
                                    public void run() {
                                        FileUtils24 file = new FileUtils24();
                                        String files=file.readDataFromFile(PersonActivity.this); //用户登录电话
                                        FileUtils26 fileps = new FileUtils26();
                                        String filepas= fileps.readDataFromFile(PersonActivity.this);
                                        if(files!=null&&filepas!=null){
                                            loginByPost(files, filepas); // 调用loginByPost方法
                                        }else {
                                            FileUtils38 thirdfile=new FileUtils38();
                                            String thirdstyle=thirdfile.readDataFromFile(PersonActivity.this); //第三方登录的方式(如qq)
                                            FileUtils39 fileuniode=new FileUtils39();
                                            String uniode=fileuniode.readDataFromFile(PersonActivity.this); //第三方登录uniode
                                            FileUtils40 filegender=new FileUtils40();
                                            String gender=filegender.readDataFromFile(PersonActivity.this); //第三方登录的性别
                                            FileUtils41 filenick=new FileUtils41();
                                            String name=filenick.readDataFromFile(PersonActivity.this);//第三方登录的姓名
                                            FileUtils42 fileimg=new FileUtils42();
                                            String imgurl=fileimg.readDataFromFile(PersonActivity.this);//第三方登录的图片url
                                            if(thirdstyle!=null&&uniode!=null&&gender!=null&&name!=null&&imgurl!=null){
                                                loginByPost1(thirdstyle, uniode,gender,name,imgurl);
                                            }
                                        }
                                    }
                                }.start();
                                picLogin.setImageBitmap(photo); //上传完成将照片写入imageview与用户进行交互
                                //上传照片
                                //toUploadFile();
                           /*   UploadImage uplod=new UploadImage();
                              uplod.uploadFile(filepath,"https://api.aijiaijia.com/api_upload");*/
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                       /* indenglu.setImageBitmap(photo);  //自己的交互*/

                    }
                }
                break;
        }

    }
    public void loginByPost1(String thirdstyle, String uniode,String gender,String name,String imgurl ) {
        Log.i("aiwoai", "loginByPost1: "+thirdstyle);
        Log.i("aiwoai1", "loginByPost1: "+uniode);
        Log.i("aiwoai2", "loginByPost1: "+gender);
        Log.i("aiwoai3", "loginByPost1: "+name);
        Log.i("aiwoai4", "loginByPost1: "+imgurl);
        try {

            // 请求的地址
            String spec = "https://api.aijiaijia.com/api_thirdlogin";
            // 根据地址创建URL对象
            URL url = new URL(spec);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
            String data = "type=" + URLEncoder.encode(thirdstyle, "UTF-8")
                    + "&uid=" + URLEncoder.encode(uniode, "UTF-8")+"&fromdevice="+URLEncoder.encode("2", "UTF-8")+"&gender="+ URLEncoder.encode(gender, "UTF-8")
                    +"&nickname="+URLEncoder.encode(name, "UTF-8")+"&avatar="+URLEncoder.encode(imgurl, "UTF-8");
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                Log.i("heihies", "loginByPost: " + result);

// 取得sessionid.
                String cookieval = urlConnection.getHeaderField("set-cookie");
                Log.i("chaosheng", "loginByPost: " + cookieval);
                if (cookieval != null) {
                    Log.i("chaosheng1", "loginByPost: " + cookieval);
                    sessionid = cookieval.substring(0, cookieval.indexOf(";"));
                    Log.i("chaosheng2", "loginByPost: " + sessionid);
                    FileUtils34 fil = new FileUtils34();
                    fil.saveDataToFile(PersonActivity.this, sessionid);
                }
                File file = new File(String.valueOf(filepath)); //这里的path就是那个地址的全局变量
                /*uploadfile file1=new uploadfile();
                file1.uploadFile(file, "https://api.aijiaijia.com/api_upload");*/
               uploadfile.uploadFile(PersonActivity.this, file, "https://api.aijiaijia.com/api_upload");
            /*    String fileKey = "upload";
                Log.i("tongkus", "loginByPost: " + sessionid);
                UploadImage uplod = new UploadImage();
                uplod.uploadFile(filepath, "https://api.aijiaijia.com/api_upload",fileKey);*/
                //toUploadFile();
               /* String fileKey = "upload";
                UploadImage uplod = new UploadImage();
                uplod.uploadFile(filepath, "https://api.aijiaijia.com/api_upload",fileKey);*/
            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loginByPost(String userName, String userPass) {

        try {

            // 请求的地址
            String spec = "https://api.aijiaijia.com/api_login";
            // 根据地址创建URL对象
            URL url = new URL(spec);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
            String data = "phone=" + URLEncoder.encode(userName, "UTF-8")
                    + "&password=" + URLEncoder.encode(userPass, "UTF-8");
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                Log.i("heihies", "loginByPost: " + result);

// 取得sessionid.
                String cookieval = urlConnection.getHeaderField("set-cookie");
                Log.i("chaosheng", "loginByPost: " + cookieval);
                if (cookieval != null) {
                    Log.i("chaosheng1", "loginByPost: " + cookieval);
                    sessionid = cookieval.substring(0, cookieval.indexOf(";"));
                    Log.i("chaosheng2", "loginByPost: " + sessionid);
                    FileUtils34 fil = new FileUtils34();
                    fil.saveDataToFile(PersonActivity.this, sessionid);
                }
                File file = new File(String.valueOf(filepath)); //这里的path就是那个地址的全局变量
                /*uploadfile file1=new uploadfile();
                file1.uploadFile(file, "https://api.aijiaijia.com/api_upload");*/
                String resultds = uploadfile.uploadFile(PersonActivity.this, file, "https://api.aijiaijia.com/api_upload");
            /*    String fileKey = "upload";
                Log.i("tongkus", "loginByPost: " + sessionid);
                UploadImage uplod = new UploadImage();
                uplod.uploadFile(filepath, "https://api.aijiaijia.com/api_upload",fileKey);*/
                //toUploadFile();
               /* String fileKey = "upload";
                UploadImage uplod = new UploadImage();
                uplod.uploadFile(filepath, "https://api.aijiaijia.com/api_upload",fileKey);*/
            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUploadDone(int responseCode, String message) {
        //上传完成响应
        pd.dismiss();
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        //上传中
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
    }

    @Override
    public void initUpload(int fileSize) {
        //准备上传
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (i == Platform.ACTION_USER_INFOR) {
            PlatformDb platDb = platform.getDb();
            if (platform.getName().equals(Wechat.NAME)) {
                userId = platDb.getUserId();
                name = "weixin";
                unionid = wechat.getDb().get("unionid");
                postqqbind();

            } else if (platform.getName().equals(SinaWeibo.NAME)) {
                //微博登录
                userId = platDb.getUserId();
                name = "sina";
                postqqbind();

            } else if (platform.getName().equals(QQ.NAME)) {
                // QQ登录
                userId = platDb.getUserId();
                name = "qq";
                getQQUnionid(qq);


            }
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
