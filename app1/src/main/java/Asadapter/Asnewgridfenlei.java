package Asadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Sousuo2Activity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import bean.Listbean;
import utils.FileUtils;

/**
 * Created by misshu on 2017/4/26/026.
 */
public class Asnewgridfenlei extends BaseAdapter {
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    public Asnewgridfenlei( ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist,ImageLoader loader,Context context) {
        this.productlist = productlist;
        this.context=context;
        this.loader=loader;
    }
    @Override
    public int getCount() {
        return productlist.size();
    }

    @Override
    public Object getItem(int position) {
        return productlist.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GridHolder holder = null;
        if (null == convertView) {
            holder = new GridHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_asnewgridfenlei, null);
            holder.imag_name= (ImageView) convertView.findViewById(R.id.imag_name);
            convertView.setTag(holder);
        } else {
            holder = (GridHolder) convertView.getTag();
        }
        final GridHolder finalHolder = holder;
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.yugang)
                .showImageOnFail(R.drawable.yugang)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ALPHA_8)
                .build();
        ImageSize mImageSize = new ImageSize(100, 100);
        loader.loadImage(productlist.get(position).getCategoriePic(),mImageSize,options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalHolder.imag_name.setImageBitmap(loadedImage);
            }
        });
        holder.imag_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  id=productlist.get(position).getMc_link();
                FileUtils fu=new FileUtils();
                fu.saveDataToFile(context,id);
                Intent intent3=new Intent(context,Sousuo2Activity.class);
                context.startActivity(intent3);
            }
        });
        return convertView;
    }
    public static class GridHolder {
        ImageView imag_name;
    }
}
