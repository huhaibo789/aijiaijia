package h5Fragement;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Urlutil.wevb;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import utils.FileUtils24;
import utils.FileUtils37;
import utils.FileUtis36;


/**
 * Created by misshu on 2017/4/17/017.
 */
public class h5TiNianfragement extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.discover)
    WebView discover;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.reload_button)
    Button reloadButton;
    @Bind(R.id.find_rl)
    RelativeLayout findRl;
    private String mParam1;
    private String mParam2;
    private Context context;
    LoadingDialog dialog;
    String thirdliginid;
    String telphone;
    private Uri imageUri;
    boolean istrue=true;
    String isfirst;
    private ValueCallback<Uri> mUploadFile;
    /**
     * 拍照/选择文件请求码
     */
    private static final int REQUEST_UPLOAD_FILE_CODE = 12343;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public h5TiNianfragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_discover, container, false);
        ButterKnife.bind(this, view);
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = discover.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        FileUtis36 fil = new FileUtis36();
        thirdliginid = fil.readDataFromFile(context);
        FileUtils37 files=new FileUtils37();
        telphone=files.readDataFromFile(context);
        setlogin();
        if (telphone!=null&&telphone.equals("nologin")&&thirdliginid  != null&&!thirdliginid.equals("nologin")) {
            //加载需要显示的网页
            discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid=" +"&userid="+thirdliginid);
            istrue=false;
            isfirst="0";
            Log.i("fsdsdds", "onCreateView: "+telphone);
            // discover.loadUrl("https://h5.aijiaijia.com/32/article.html?uid=%E5%B0%8F%E4%B8%A4%E5%8F%A3&webTitle=%E8%AF%A6%E6%83%85");
        } else if(telphone!=null&&!telphone.equals("nologin")&&thirdliginid!=null&&thirdliginid.equals("nologin")){
            //加载需要显示的网页
            discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+telphone+"&userid=");
            istrue=false;
            isfirst="0";
            Log.i("fsdsdds1", "onCreateView: "+telphone);
        }else if(telphone!=null&&!telphone.equals("nologin")&&thirdliginid!=null&&!thirdliginid.equals("nologin")) {
            discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+telphone+"&userid="+thirdliginid);
            istrue=false;
            isfirst="0";
            Log.i("fsdsdds2", "onCreateView: "+telphone);
        }else {
            Log.i("fsdsdds3", "onCreateView: "+telphone);
            discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+"&userid=");
            istrue=false;
            isfirst="1";
        }
        //设置Web视图
        discover.setWebViewClient(new webViewClient());
        discover.setWebChromeClient(new WebChromeClient() {
            // Andorid 4.1+
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                openFileChooser(uploadFile);
            }

            // Andorid 3.0 +
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
                openFileChooser(uploadFile);
            }

            // Android 3.0
            public void openFileChooser(ValueCallback<Uri> uploadFile) {
                // Toast.makeText(WebviewActivity.this, "上传文件/图片",Toast.LENGTH_SHORT).show();
                mUploadFile = uploadFile;
                startActivityForResult(Intent.createChooser(createCameraIntent(), "Image Browser"), REQUEST_UPLOAD_FILE_CODE);
            }
        });
        //Web视图
        return view;
    }

    public void setlogin() {
        if(istrue==false){
            FileUtis36 fil = new FileUtis36();
            thirdliginid = fil.readDataFromFile(context);
            FileUtils37 files=new FileUtils37();
            telphone=files.readDataFromFile(context);
            if (telphone!=null&&telphone.equals("nologin")&&thirdliginid  != null&&!thirdliginid.equals("nologin")) {
                //加载需要显示的网页
                if(!isfirst.equals("0")){
                    discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid=" +"&userid="+thirdliginid);
                    isfirst="0";
                }else {
                    Log.i("aiya", "setlogin: "+isfirst);
                }

            } else if(telphone!=null&&!telphone.equals("nologin")&&thirdliginid!=null&&thirdliginid.equals("nologin")){
                //加载需要显示的网页
                if(!isfirst.equals("0")){
                    discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+telphone+"&userid=");
                    isfirst="0";
                }else {
                    Log.i("aiya", "setlogin: "+isfirst);
                }

            }else if(telphone!=null&&!telphone.equals("nologin")&&thirdliginid!=null&&!thirdliginid.equals("nologin")) {
                if(!isfirst.equals("0")){
                    discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+telphone+"&userid="+thirdliginid);
                    isfirst="0";
                }else {
                    Log.i("aiya", "setlogin: "+isfirst);
                }

            }else {
                if(!isfirst.equals("1")){
                    discover.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+"&userid=");
                    isfirst="1";
                }else {
                    Log.i("hahsdas", "setlogin: "+isfirst);
                }
            }
        }

    }

    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//拍照
        //=======================================================
        Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);//选择图片文件
        imageIntent.setType("image/*");
        //=======================================================
        return cameraIntent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (discover != null) {
            try {
                discover.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ButterKnife.unbind(this);
    }

    class webViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.dismiss();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wevb wev = new wevb();
            wev.web(context, url);
            return true;
        }

        @Override
        public void onReceivedError(final WebView view1, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view1, errorCode, description, failingUrl);
            if (errorCode == -2) {
                findRl.setVisibility(View.VISIBLE);
                discover.setVisibility(View.GONE);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        discover.setVisibility(View.VISIBLE);
                        findRl.setVisibility(View.GONE);
                        if (thirdliginid  != null&&!thirdliginid.equals("null")) {
                            //加载需要显示的网页
                            view1.loadUrl("https://h5.aijiaijia.com/discovery.html?uid=" +"&userid="+thirdliginid);
                            // discover.loadUrl("https://h5.aijiaijia.com/32/article.html?uid=%E5%B0%8F%E4%B8%A4%E5%8F%A3&webTitle=%E8%AF%A6%E6%83%85");
                        } else if(telphone!=null&&thirdliginid.equals("null")){
                            //加载需要显示的网页
                            view1.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+telphone+"&userid=");
                        }else {
                            view1.loadUrl("https://h5.aijiaijia.com/discovery.html?uid="+"&userid=");
                        }

                    }
                });
            }
        }

        //最后在OnActivityResult中接受返回的结果
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_UPLOAD_FILE_CODE) {
                if (null == mUploadFile) {
                    return;
                }
                Uri result = (null == data) ? null : data.getData();
                if (null != result) {
                    ContentResolver resolver = context.getContentResolver();
                    String[] columns = {MediaStore.Images.Media.DATA};
                    Cursor cursor = resolver.query(result, columns, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(columns[0]);
                    String imgPath = cursor.getString(columnIndex);
                    System.out.println("imgPath = " + imgPath);
                    if (null == imgPath) {
                        return;
                    }
                    File file = new File(imgPath);
                    //将图片处理成大小符合要求的文件
                    result = Uri.fromFile(handleFile(file));
                    mUploadFile.onReceiveValue(result);
                    mUploadFile = null;
                }
            }

        }

        /**
         * 处理拍照/选择的文件
         */
        private File handleFile(File file) {
            DisplayMetrics dMetrics = getResources().getDisplayMetrics();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;
            System.out.println("  imageWidth = " + imageWidth + " imageHeight = " + imageHeight);
            int widthSample = (int) (imageWidth / (dMetrics.density * 90));
            int heightSample = (int) (imageHeight / (dMetrics.density * 90));
            System.out.println("widthSample = " + widthSample + " heightSample = " + heightSample);
            options.inSampleSize = widthSample < heightSample ? heightSample : widthSample;
            options.inJustDecodeBounds = false;
            Bitmap newBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            System.out.println("newBitmap.size = " + newBitmap.getRowBytes() * newBitmap.getHeight());
            File handleFile = new File(file.getParentFile(), "upload.png");
            try {
                if (newBitmap.compress(Bitmap.CompressFormat.PNG, 50, new FileOutputStream(handleFile))) {
                    System.out.println("保存图片成功");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return handleFile;

        }
    }
}
