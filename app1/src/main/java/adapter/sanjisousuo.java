package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.YouhuiuserActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 胡海波 on 2016/11/7.
 */
public class sanjisousuo extends BaseAdapter {

    //定义hashMap 用来存放之前创建的每一项item
    HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<String> picture;
    private ArrayList<String> title;
    private ArrayList<String> nowprice;
    private ArrayList<String> beforeprice;
    private Context context;
    public sanjisousuo (Context context, ImageLoader loader,ArrayList<String> picture,ArrayList<String> title,ArrayList<String> nowprice,ArrayList<String> beforeprice){
        super();
        this.context=context;
        this.loader=loader;
        this.picture=picture;
        this.title=title;
        this.nowprice=nowprice;
        this.beforeprice=beforeprice;

    }


    @Override
    public int getCount() {

        return picture.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(lmap.get(position)==null){
            convertView=View.inflate(context, R.layout.activity_sanjisousuo,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            lmap.put(position,convertView);
            vh.sanjipic_iv= (ImageView) convertView.findViewById(R.id.sanjipic_iv);
            vh.sanjititle= (TextView) convertView.findViewById(R.id.sanjititle);
            vh.sanji_nowprice= (TextView) convertView.findViewById(R.id.sanji_nowprice);
            vh.sanji_beforeprice= (TextView) convertView.findViewById(R.id.sanji_beforeprice);

        }else {
            convertView = lmap.get(position);
            vh= (ViewHold) convertView.getTag();
        }
        vh.sanjititle.setText(title.get(position));
        if(String.valueOf(nowprice.get(position)).equals("0")||String.valueOf(nowprice.get(position)).equals("null")){
            vh.sanji_nowprice.setText("¥"+String.valueOf(beforeprice.get(position)));
            vh.sanji_beforeprice.setVisibility(View.GONE);
        }else if(String.valueOf(beforeprice.get(position)).equals("0")||String.valueOf(beforeprice.get(position)).equals("null")){
            vh.sanji_nowprice.setText("¥"+String.valueOf(nowprice.get(position)));
            vh.sanji_beforeprice.setVisibility(View.GONE);
        }else if(String.valueOf(beforeprice.get(position)).equals(String.valueOf(nowprice.get(position)))){
            vh.sanji_nowprice.setText("¥"+String.valueOf(beforeprice.get(position)));
            vh.sanji_beforeprice.setVisibility(View.GONE);
        }else {
            vh.sanji_nowprice.setText("¥"+String.valueOf(nowprice.get(position)));
            vh.sanji_beforeprice.setText("¥"+String.valueOf(beforeprice.get(position)));
            vh.sanji_beforeprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        Log.i("laile", "getView: "+"sdasfa");

       /* vh.sanji_nowprice.setText("¥"+String.valueOf(nowprice.get(position)));
        vh.sanji_beforeprice.setText("¥"+String.valueOf(beforeprice.get(position)));
        vh.sanji_beforeprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);*/
        final ViewHold finalVh = vh;
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.yugang)
                .showImageOnFail(R.drawable.yugang)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        ImageSize mImageSize = new ImageSize(210, 210);
        ImageLoader.getInstance().loadImage(picture.get(position),mImageSize,options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh.sanjipic_iv.setImageBitmap(loadedImage);
            }
        });
     /*   loader.loadImage(picture.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh.sanjipic_iv.setImageBitmap(loadedImage);
            }
        });*/
        return convertView;
    }
    public class ViewHold {
        ImageView sanjipic_iv;
        TextView sanjititle;
        TextView sanji_nowprice;
        TextView sanji_beforeprice;
    }

}
