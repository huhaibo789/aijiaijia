package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;


import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import bean.Sousobean1;
import bean.huasabean;
import bean.zhantingbean;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class Newadapter extends BaseAdapter {

    private ArrayList<Sousobean1.ResponseJsonBean.ListBean> listbean1 = new ArrayList<>();
    private LayoutInflater inflater;
    private ImageLoader loader;
    public List<View> viewlist=new ArrayList<View>();
    public View view;
    private Context context;
    public  Newadapter(Context context, ImageLoader loader){
        super();
        this.context=context;
        this.loader=loader;
        viewlist=new ArrayList<View>();
    }
    @Override
    public int getCount() {
        return listbean1.size()/2;

    }

    @Override
    public Object getItem(int position) {
        return listbean1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_sanji,null);
            viewlist.add(convertView);
            convertView=viewlist.get(position);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh. newsanji_tv= (TextView) convertView.findViewById(R.id. newsanji_tv);
            vh. newsanji_tv1= (TextView) convertView.findViewById(R.id. newsanji_tv1);
            vh. newsanji_tv2= (TextView) convertView.findViewById(R.id. newsanji_tv2);
            vh. newsanji_tv3= (TextView) convertView.findViewById(R.id. newsanji_tv3);
            vh. newsanji_tv4= (TextView) convertView.findViewById(R.id. newsanji_tv4);
            vh. newsanji_tv5= (TextView) convertView.findViewById(R.id. newsanji_tv5);
            vh. newsanji_iv= (ImageView) convertView.findViewById(R.id.newsanji_iv);
            vh. newsanji_iv1= (ImageView) convertView.findViewById(R.id.newsanji_iv1);
        }else {
            vh= (ViewHold) convertView.getTag();
        }
        if(position==0){
            vh.newsanji_tv.setText(listbean1.get(0).getProduct_name());
            vh.newsanji_tv2.setText(String.valueOf(listbean1.get(0).getProduct_nowprice()));
            vh.newsanji_tv3.setText(String.valueOf(listbean1.get(0).getProduct_price()));
            vh.newsanji_tv1.setText(listbean1.get(1).getProduct_name());
            vh.newsanji_tv4.setText(String.valueOf(listbean1.get(1).getProduct_nowprice()));
            vh.newsanji_tv5.setText(String.valueOf(listbean1.get(1).getProduct_price()));
            final ViewHold finalVh2 = vh;
            loader.loadImage(listbean1.get(0).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh2.newsanji_iv.setImageBitmap(loadedImage);
                }
            });
            final ViewHold finalVh = vh;
            loader.loadImage(listbean1.get(1).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh.newsanji_iv1.setImageBitmap(loadedImage);
                }
            });
        }else if(position==1){
            vh.newsanji_tv.setText(listbean1.get(2).getProduct_name());
            vh.newsanji_tv2.setText(String.valueOf(listbean1.get(2).getProduct_nowprice()));
            vh.newsanji_tv3.setText(String.valueOf(listbean1.get(2).getProduct_price()));
            vh.newsanji_tv1.setText(listbean1.get(3).getProduct_name());
            vh.newsanji_tv4.setText(String.valueOf(listbean1.get(3).getProduct_nowprice()));
            vh.newsanji_tv5.setText(String.valueOf(listbean1.get(3).getProduct_price()));
            final ViewHold finalVh2 = vh;
            loader.loadImage(listbean1.get(2).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh2.newsanji_iv.setImageBitmap(loadedImage);
                }
            });
            final ViewHold finalVh = vh;
            loader.loadImage(listbean1.get(3).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh.newsanji_iv1.setImageBitmap(loadedImage);
                }
            });
        }else if(position==2){
            vh.newsanji_tv.setText(listbean1.get(4).getProduct_name());
            vh.newsanji_tv2.setText(String.valueOf(listbean1.get(4).getProduct_nowprice()));
            vh.newsanji_tv3.setText(String.valueOf(listbean1.get(4).getProduct_price()));
            vh.newsanji_tv1.setText(listbean1.get(5).getProduct_name());
            vh.newsanji_tv4.setText(String.valueOf(listbean1.get(5).getProduct_nowprice()));
            vh.newsanji_tv5.setText(String.valueOf(listbean1.get(5).getProduct_price()));
            final ViewHold finalVh2 = vh;
            loader.loadImage(listbean1.get(4).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh2.newsanji_iv.setImageBitmap(loadedImage);
                }
            });
            final ViewHold finalVh = vh;
            loader.loadImage(listbean1.get(5).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh.newsanji_iv1.setImageBitmap(loadedImage);
                }
            });
        }else if(position==3){
            vh.newsanji_tv.setText(listbean1.get(6).getProduct_name());
            vh.newsanji_tv2.setText(String.valueOf(listbean1.get(6).getProduct_nowprice()));
            vh.newsanji_tv3.setText(String.valueOf(listbean1.get(6).getProduct_price()));
            vh.newsanji_tv1.setText(listbean1.get(7).getProduct_name());
            vh.newsanji_tv4.setText(String.valueOf(listbean1.get(7).getProduct_nowprice()));
            vh.newsanji_tv5.setText(String.valueOf(listbean1.get(7).getProduct_price()));
            final ViewHold finalVh2 = vh;
            loader.loadImage(listbean1.get(6).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh2.newsanji_iv.setImageBitmap(loadedImage);
                }
            });
            final ViewHold finalVh = vh;
            loader.loadImage(listbean1.get(7).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh.newsanji_iv1.setImageBitmap(loadedImage);
                }
            });
        }else if(position==4){
            vh.newsanji_tv.setText(listbean1.get(8).getProduct_name());
            vh.newsanji_tv2.setText(String.valueOf(listbean1.get(8).getProduct_nowprice()));
            vh.newsanji_tv3.setText(String.valueOf(listbean1.get(8).getProduct_price()));
            vh.newsanji_tv1.setText(listbean1.get(9).getProduct_name());
            vh.newsanji_tv4.setText(String.valueOf(listbean1.get(9).getProduct_nowprice()));
            vh.newsanji_tv5.setText(String.valueOf(listbean1.get(9).getProduct_price()));
            final ViewHold finalVh2 = vh;
            loader.loadImage(listbean1.get(8).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh2.newsanji_iv.setImageBitmap(loadedImage);
                }
            });
            final ViewHold finalVh = vh;
            loader.loadImage(listbean1.get(9).getProduct_pic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh.newsanji_iv1.setImageBitmap(loadedImage);
                }
            });
        }
        final ViewHold finalVh1 = vh;
        return convertView;
    }
    public class ViewHold {
        TextView  newsanji_tv,newsanji_tv1,newsanji_tv2,newsanji_tv3,newsanji_tv4,newsanji_tv5;
       ImageView newsanji_iv,newsanji_iv1;
    }

}
