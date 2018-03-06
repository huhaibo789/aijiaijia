package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu;
import bean.gouwu1;
import bean.zhantingbean;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class shoucangadapter extends BaseAdapter {
    private  ArrayList<String> loginc1 =new ArrayList<>();
    private  ArrayList<String> loginc2 =new ArrayList<>();
    private  ArrayList<String> loginc3 =new ArrayList<>();
    private  ArrayList<String> loginc4 =new ArrayList<>();
    private  ArrayList<String> loginc5 =new ArrayList<>();
    private ArrayList<String> loginc6=new ArrayList<>();
    private ArrayList<String> loginsn=new ArrayList<>();
    private RequestQueue queue;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private JSONArray jsonArray;
    private List<gouwu1> data=new ArrayList<>();
    private Context context;
    public   shoucangadapter (Context context, ImageLoader loader,List<gouwu1> data,ArrayList<String> loginc1,ArrayList<String> loginc2,
    ArrayList<String> loginc3,ArrayList<String> loginc4,ArrayList<String> loginc5,ArrayList<String> loginc6, ArrayList<String> loginsn){
        super();
        this.context=context;
        this.loader=loader;
        this.loginc1=loginc1;
        this.loginc2=loginc2;
        this.loginc3=loginc3;
        this.loginc4=loginc4;
        this.loginc5=loginc5;
        this.loginc6=loginc6;
        this.loginsn=loginsn;
        this.data=data;
    }
    @Override
    public int getCount() {
        return loginc1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_shoucangye,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.sh_iv= (ImageView) convertView.findViewById(R.id.sh_iv);
            vh.sh_tv= (TextView) convertView.findViewById(R.id.sh_tv);
            vh.sh_tv1= (TextView) convertView.findViewById(R.id.sh_tv1);
            vh.sh_tv2= (TextView) convertView.findViewById(R.id.sh_tv2);
            vh.tv_jieshao= (TextView) convertView.findViewById(R.id.tv_jieshao);
            vh.sss= (TextView) convertView.findViewById(R.id.sss);
            vh.sss1= (TextView) convertView.findViewById(R.id.sss1);
            vh.sh_tv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            vh= (ViewHold) convertView.getTag();
        }
        vh.sh_tv.setText(loginc2.get(position));
        vh.sh_tv1.setText(loginc4.get(position));
        vh.sh_tv2.setText(loginc5.get(position));
        vh.tv_jieshao.setText("产品编号:"+loginc3.get(position));
        final ViewHold finalVh1 = vh;
        loader.loadImage(loginc1.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh1.sh_iv.setImageBitmap(loadedImage);
            }
        });
        if(loginc4.get(position).equals("0")||loginc4.get(position).equals("null")){
            vh.sh_tv1.setText("¥"+loginc5.get(position));
            vh.sh_tv2.setVisibility(View.INVISIBLE);
        }else if(loginc5.get(position).equals("0")||loginc5.get(position).equals("null")){
            vh.sh_tv1.setText("¥"+loginc4.get(position));
            vh.sh_tv2.setVisibility(View.INVISIBLE);
        }else {
            vh.sh_tv2.setVisibility(View.VISIBLE);
            vh.sh_tv1.setText("¥"+loginc4.get(position));
            vh.sh_tv2.setText("¥"+loginc5.get(position));
        }
        if(loginsn.get(position).equals("2")){
            vh.sh_tv.setTextColor(Color.parseColor("#ACACAC"));
            vh.tv_jieshao.setTextColor(Color.parseColor("#ACACAC"));
            vh.sh_tv1.setTextColor(Color.parseColor("#ACACAC"));
            vh.sss.setVisibility(View.VISIBLE);
            vh.sss1.setBackgroundResource(R.drawable.toumingyuan);
        }else {
            vh.sss.setVisibility(View.INVISIBLE);
            vh.sss1.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    public class ViewHold {
        ImageView sh_iv;
        TextView  sh_tv;
        TextView sh_tv1;
        TextView sh_tv2;
        TextView sss;
        TextView sss1;
        TextView tv_jieshao;
    }

}
