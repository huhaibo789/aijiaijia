package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.HasproductActivity;
import com.example.administrator.myyushi.JifenActivity;
import com.example.administrator.myyushi.LoginActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Sousuo2Activity;
import com.example.administrator.myyushi.WebviewActivity;
import com.example.administrator.myyushi.newsousuoaActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.Listbean;
import bean.fenleibean;
import newfrage.InnerGridView;
import utils.FileUtils24;
import utils.FileUtils6;
import view.SearchPagerGaoview;

/**
 * Created by 胡海波 on 2016/12/20.
 */
public class new1searchadapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private ImageLoader loader;
    private Context mContext;
    Handler handle=new Handler();
    private RequestQueue queue;
    private boolean flag=false;
    private    String result3;
    private ArrayList<String> list=new ArrayList<>();
    private  ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist3=new ArrayList<>();
    private  ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist4=new ArrayList<>();
    private ArrayList<String> picture=new ArrayList<>();
    float width;
    public new1searchadapter(Context context, ImageLoader loader, ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist3,ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist4,float width){
        this.mContext=context;
        inflater=LayoutInflater.from(context);
        this.loader=loader;
        this.biglist3=biglist3;
        this.biglist4=biglist4;
        this.width=width;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new MyHolderType0(inflater.inflate(R.layout.activity_fenlei1,parent,false));
                break;
            case 1:
                holder=new MyHolderType1(inflater.inflate(R.layout.activity_news1,parent,false));
                break;
        }
        return holder;
    }
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else{
            return  1;
        }


    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case 0:
                final   MyHolderType0     mholer0= (MyHolderType0) holder;
                if(biglist3.size()!=0) {
                    mholer0.tui_tv.setText(biglist3.get(0).getCategorieName());
                    if (biglist3.get(0).getSub().size() == 0) {
                        mholer0.tui_pic.setVisibility(View.GONE);
                        mholer0.tui_pic1.setVisibility(View.GONE);
                        mholer0.tui_pic2.setVisibility(View.GONE);
                        mholer0.tui_pic3.setVisibility(View.GONE);
                        mholer0.tui_pic4.setVisibility(View.GONE);
                        mholer0.tui_pic5.setVisibility(View.GONE);
                    } else if (biglist3.get(0).getSub().size() == 1) {
                        mholer0.tui_pic.setVisibility(View.VISIBLE);
                        loader.loadImage(biglist3.get(0).getSub().get(0).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic.setImageBitmap(loadedImage);
                            }
                        });
                        mholer0.tui_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(0).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic1.setVisibility(View.GONE);
                        mholer0.tui_pic2.setVisibility(View.GONE);
                        mholer0.tui_pic3.setVisibility(View.GONE);
                        mholer0.tui_pic4.setVisibility(View.GONE);
                        mholer0.tui_pic5.setVisibility(View.GONE);
                    } else if (biglist3.get(0).getSub().size() == 2) {
                        mholer0.tui_pic.setVisibility(View.VISIBLE);
                        mholer0.tui_pic1.setVisibility(View.VISIBLE);
                        loader.loadImage(biglist3.get(0).getSub().get(0).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(1).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic1.setImageBitmap(loadedImage);
                            }
                        });
                        mholer0.tui_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(0).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(1).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic2.setVisibility(View.GONE);
                        mholer0.tui_pic3.setVisibility(View.GONE);
                        mholer0.tui_pic4.setVisibility(View.GONE);
                        mholer0.tui_pic5.setVisibility(View.GONE);
                    } else if (biglist3.get(0).getSub().size() == 3) {
                        mholer0.tui_pic.setVisibility(View.VISIBLE);
                        mholer0.tui_pic1.setVisibility(View.VISIBLE);
                        mholer0.tui_pic2.setVisibility(View.VISIBLE);
                        loader.loadImage(biglist3.get(0).getSub().get(0).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(1).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(2).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic2.setImageBitmap(loadedImage);
                            }
                        });
                        mholer0.tui_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(0).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(1).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(2).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic3.setVisibility(View.GONE);
                        mholer0.tui_pic4.setVisibility(View.GONE);
                        mholer0.tui_pic5.setVisibility(View.GONE);
                    } else if (biglist3.get(0).getSub().size() == 4) {
                        mholer0.tui_pic.setVisibility(View.VISIBLE);
                        mholer0.tui_pic1.setVisibility(View.VISIBLE);
                        mholer0.tui_pic2.setVisibility(View.VISIBLE);
                        mholer0.tui_pic3.setVisibility(View.VISIBLE);
                        loader.loadImage(biglist3.get(0).getSub().get(0).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(1).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(2).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic2.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(3).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic3.setImageBitmap(loadedImage);
                            }
                        });
                        mholer0.tui_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(0).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(1).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(2).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(3).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });

                        mholer0.tui_pic4.setVisibility(View.GONE);
                        mholer0.tui_pic5.setVisibility(View.GONE);
                    } else if (biglist3.get(0).getSub().size() == 5) {
                        mholer0.tui_pic.setVisibility(View.VISIBLE);
                        mholer0.tui_pic1.setVisibility(View.VISIBLE);
                        mholer0.tui_pic2.setVisibility(View.VISIBLE);
                        mholer0.tui_pic3.setVisibility(View.VISIBLE);
                        mholer0.tui_pic4.setVisibility(View.VISIBLE);
                        loader.loadImage(biglist3.get(0).getSub().get(0).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(1).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(2).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic2.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(3).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic3.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(4).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic4.setImageBitmap(loadedImage);
                            }
                        });
                        mholer0.tui_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(0).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(1).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(2).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(3).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(4).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });

                        mholer0.tui_pic5.setVisibility(View.GONE);
                    } else if (biglist3.get(0).getSub().size() == 6) {
                        mholer0.tui_pic.setVisibility(View.VISIBLE);
                        mholer0.tui_pic1.setVisibility(View.VISIBLE);
                        mholer0.tui_pic2.setVisibility(View.VISIBLE);
                        mholer0.tui_pic3.setVisibility(View.VISIBLE);
                        mholer0.tui_pic4.setVisibility(View.VISIBLE);
                        mholer0.tui_pic5.setVisibility(View.VISIBLE);
                        loader.loadImage(biglist3.get(0).getSub().get(0).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(1).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(2).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic2.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(3).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic3.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(4).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic4.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(biglist3.get(0).getSub().get(5).getMc_pic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mholer0.tui_pic5.setImageBitmap(loadedImage);
                            }
                        });
                        mholer0.tui_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(0).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(1).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(2).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(3).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(4).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });
                        mholer0.tui_pic5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = biglist3.get(0).getSub().get(5).getMc_link();
                                String[] arry = result.split("=");
                                String data = arry[arry.length - 1];
                                Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                intent3.putExtra("uid", data);
                                mContext.startActivity(intent3);
                            }
                        });


                    }
                }
               /* if(!flag){
                    final   MyHolderType0   holder0= (MyHolderType0) holder;
                    if(biglist3.size()!=0){
                        holder0.textView1.setText(biglist3.get(0).getCategorieName());
                        for (int i = 0; i <biglist3.get(0).getSub().size() ; i++) {
                            final ImageView img = new ImageView(mContext);
                            loader.displayImage(biglist3.get(0).getSub().get(i).getMc_pic(),img);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.height=250;
                            lp.setMargins(0, 10, 0, 0);
                            img.setLayoutParams(lp);
                            img.setScaleType(ImageView.ScaleType.FIT_XY);
                            holder0.ssde.addView(img);
                            final int finalI = i;
                            img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String result = biglist3.get(0).getSub().get(finalI).getMc_link();
                                    String[] arry = result.split("=");
                                    String data = arry[arry.length - 1];
                                    Intent intent3 = new Intent(mContext, Sousuo2Activity.class);
                                    intent3.putExtra("uid", data);
                                    mContext.startActivity(intent3);
                                }
                            });
                        }
                        flag=true;
                    }

                }*/


                break;
            case 1:
                final MyHolderType1 holder1= (MyHolderType1) holder;
                if(biglist3.size()!=0){
                    holder1.mgridview.setAdapter(new newgridfenlei1((ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean>) biglist4.get(position-1).getSub(),loader,mContext));
                    holder1.newtextView1.setText(biglist4.get(position-1).getCategorieName());
                }

                break;





        }
    }


    public int getItemCount() {
        int count=biglist4==null?0:biglist4.size()+1;
        return count;
    }



    class MyHolderType0 extends  RecyclerView.ViewHolder{
        private  TextView  tui_tv;
        private ImageView tui_pic,tui_pic1,tui_pic2,tui_pic3,tui_pic4,tui_pic5;
      /*  private TextView textView1;
        private RelativeLayout head_item;
        private LinearLayout ssde;*/
        public MyHolderType0(View itemView) {
            super(itemView);
            tui_tv= (TextView) itemView.findViewById(R.id.tui_tv);
            tui_pic= (ImageView) itemView.findViewById(R.id.tui_pic);
            tui_pic1= (ImageView) itemView.findViewById(R.id.tui_pic1);
            tui_pic2= (ImageView) itemView.findViewById(R.id.tui_pic2);
            tui_pic3= (ImageView) itemView.findViewById(R.id.tui_pic3);
            tui_pic4= (ImageView) itemView.findViewById(R.id.tui_pic4);
            tui_pic5= (ImageView) itemView.findViewById(R.id.tui_pic5);
            /*textView1= (TextView) itemView.findViewById(R.id.textView1);
            head_item= (RelativeLayout) itemView.findViewById(R.id.head_item);
            ssde= (LinearLayout) itemView.findViewById(R.id.ssde);*/

        }
    }
    class MyHolderType1 extends RecyclerView.ViewHolder{
        private InnerGridView mgridview;
         private  TextView newtextView1;
        private  RelativeLayout newhead_item;
        public MyHolderType1(View itemView) {
            super(itemView);
            newtextView1= (TextView) itemView.findViewById(R.id.newtextView1);
            newhead_item= (RelativeLayout) itemView.findViewById(R.id.newhead_item);
            mgridview= (InnerGridView) itemView.findViewById(R.id.mgridview);
        }
    }




}
