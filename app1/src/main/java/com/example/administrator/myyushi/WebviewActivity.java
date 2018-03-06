package com.example.administrator.myyushi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;

import java.io.File;
import java.io.IOException;

import Urlutil.ImageFilePath;
import Urlutil.h5Utils;
import Urlutil.wevb;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;

public class WebviewActivity extends AppCompatActivity {
    @Bind(R.id.end_activity)
    ImageView endActivity;
    @Bind(R.id.text_wenben)
    TextView textWenben;
    @Bind(R.id.weizhi)
    RelativeLayout weizhi;
    @Bind(R.id.webview2)
    WebView webview2;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.reload_button)
    Button reloadButton;
    @Bind(R.id.webs_rl)
    RelativeLayout websRl;
    LoadingDialog dialog;
    private String information = "2";
    private String result = "1";
    String shoping;
    String servicetitle;
    String service;
    String data2;
    String data1;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview2);
        ButterKnife.bind(this);
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        setlistener();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#fbd23a"), true);
        StatService.bindJSInterface(this, webview2);
        Intent intent = getIntent();
        servicetitle = intent.getStringExtra("servicetitle");
        service = intent.getStringExtra("service");
        shoping = intent.getStringExtra("shoping");
        Finalurl.finalurl = shoping;
        data2 = intent.getStringExtra("name3");
        data1 = intent.getStringExtra("name1");
        String xingming = intent.getStringExtra("xingming");
        String color = intent.getStringExtra("color");
        if (servicetitle != null && service != null) {
            textWenben.setText(servicetitle);
            textWenben.setTextColor(Color.parseColor("#ffffff"));
            weizhi.setBackgroundColor(Color.parseColor("#fbd23a"));
        }
        if (xingming != null && xingming.equals("业内话题") || color != null) {
            textWenben.setText(xingming);
            textWenben.setTextColor(Color.parseColor("#ffffff"));
            endActivity.setImageResource(R.drawable.arrowreturn);
            weizhi.setBackgroundColor(Color.parseColor("#fbd23a"));
        } else {
            textWenben.setText(xingming);
            textWenben.setTextColor(Color.parseColor("#222222"));
            endActivity.setImageResource(R.drawable.yy_jt_left);
            weizhi.setBackgroundColor(Color.parseColor("#fbd23a"));
        }
        if (service != null) {
            webview2.loadUrl(service);
        }else if(shoping != null){
            webview2.loadUrl(shoping);
        }else if (!information.equals("1")) {
            if (data2 != null) {
                webview2.loadUrl(data2);
            } else if (data1 != null) {
                webview2.loadUrl(data1);
            }
        }
        WebSettings mWebSettings = webview2.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setDomStorageEnabled(true);
        webview2.setWebChromeClient(mWebChromeClient);
        webview2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.dismiss();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                   if(shoping.contains("article")){
                       view.loadUrl(url);
                       return true;
                   }else {
                       wevb wev = new wevb();
                       wev.web(WebviewActivity.this, url);
                       return true;
                   }
            }
            @Override
            public void onReceivedError(final WebView view1, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view1, errorCode, description, failingUrl);
                if (errorCode == -2) {
                    websRl.setVisibility(View.VISIBLE);
                    webview2.setVisibility(View.GONE);
                    reloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           webview2.setVisibility(View.VISIBLE);
                            websRl.setVisibility(View.GONE);
                            if (service != null) {
                                view1.loadUrl(service);
                            }else if(shoping != null){
                                view1.loadUrl(shoping);
                            }else if (!information.equals("1")) {
                                if (data2 != null) {
                                    view1.loadUrl(data2);
                                } else if (data1 != null) {
                                    view1.loadUrl(data1);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    private void setlistener() {
        endActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 2;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    @SuppressLint("SdCardPath")
    private File createImageFile() {
        //mCameraPhotoPath="/mnt/sdcard/tmp.png";
        File file = new File(Environment.getExternalStorageDirectory() + "/", "tmp.png");
        mCameraPhotoPath = file.getAbsolutePath();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        // android 5.0
        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                FileChooserParams fileChooserParams) {
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePathCallback;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    Log.e("WebViewSetting", "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    System.out.println(mCameraPhotoPath);
                } else {
                    takePictureIntent = null;
                }
            }
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");
            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
                System.out.println(takePictureIntent);
            } else {
                intentArray = new Intent[0];
            }
            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
            return true;
        }
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebviewActivity.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILECHOOSER_RESULTCODE);
        }
        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebviewActivity.this.startActivityForResult(
                    Intent.createChooser(i, "Image Chooser"),
                    FILECHOOSER_RESULTCODE);
        }
        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebviewActivity.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), WebviewActivity.FILECHOOSER_RESULTCODE);
        }

    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (result != null) {
                String imagePath = ImageFilePath.getPath(this, result);
                if (!TextUtils.isEmpty(imagePath)) {
                    result = Uri.parse("file:///" + imagePath);
                }
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
            // 5.0
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

}
