package com.zxing;

import com.google.zxing.WriterException;
import com.zxing.qrcode.CaptureActivity;
import com.zxing.qrcode.EncodingHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//二维码扫描
	private Button bt_scan;
	//二维码url
	private TextView tv_qrcode_string;
	//编辑框
	private EditText et_qr_string;
	//二维码生成
	private Button bt_add_qrcode;
	//二维码图片
	private ImageView iv_qr_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bt_scan = (Button) findViewById(R.id.bt_scan);
		tv_qrcode_string = (TextView) findViewById(R.id.tv_qrcode_string);
		
		et_qr_string = (EditText) findViewById(R.id.et_qr_string);
		bt_add_qrcode = (Button) findViewById(R.id.bt_add_qrcode);
		iv_qr_image = (ImageView) findViewById(R.id.iv_qr_image);
		
		//二维码扫描
		bt_scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		
		//二维码生成
		bt_add_qrcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String contentString = et_qr_string.getText().toString().trim();
					
					if (!contentString.equals("")) {
						//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
						Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 350);
						iv_qr_image.setImageBitmap(qrCodeBitmap);
					}else {
						Toast.makeText(getApplicationContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
					}
					
				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			//扫描得到的url
			tv_qrcode_string.setText(scanResult);
			
			//用默认浏览器打开扫描得到的地址
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(scanResult);
			intent.setData(content_url);
			startActivity(intent);
		}
	}


}
