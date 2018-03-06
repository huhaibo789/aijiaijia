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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.NewsousuoActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Sousuo2Activity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import bean.Listbean;
import newfrage.GridViewData;
import util.Myapp;
import utils.FileUtils;

/**
 * Created by 胡海波 on 2016/12/15.
 */

public class newgridfenlei extends BaseAdapter {
   private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    public newgridfenlei( ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist,ImageLoader loader,Context context) {
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
        // TODO Auto-generated method stub
        return productlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        GridHolder holder = null;

        if (null == convertView) {
            holder = new GridHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_newsearch, null);
            holder.newtv1 = (TextView) convertView.findViewById(R.id.newtv1);
            holder.newiv4 = (TextView) convertView.findViewById(R.id.newiv4);
            holder.newiv3 = (TextView) convertView.findViewById(R.id.newiv3);
            holder.ly_pic = (LinearLayout) convertView.findViewById(R.id.ly_pic);
            holder. newid_left = (ImageView) convertView.findViewById(R.id. newid_left);
            convertView.setTag(holder);
        } else {
            holder = (GridHolder) convertView.getTag();
        }
        holder.newtv1.setText(productlist.get(position).getProduct_name());
        String hh = String.valueOf((int) productlist.get(position).getProduct_nowprice());
        String hh1 = String.valueOf((int) productlist.get(position).getProduct_price());
        if(hh.equals("0")||hh.equals("null")){
            holder.newiv3.setText("¥"+hh1);
            holder.newiv4.setVisibility(View.GONE);
        }else if(hh1.equals("0")||hh1.equals("null")){
            holder.newiv3.setText("¥"+hh);
            holder.newiv4.setVisibility(View.GONE);
        }else if(hh.equals(hh1)){
            holder.newiv3.setText("¥"+hh);
            holder.newiv4.setVisibility(View.GONE);
        }else{
            holder.newiv4.setText("¥"+hh1);
            holder.newiv3.setText("¥"+hh);
            holder.newiv4.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
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
                finalHolder.newid_left.setImageBitmap(loadedImage);
            }
        });
      /*  loader.loadImage(productlist.get(position).getCategoriePic(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalHolder.newid_left.setImageBitmap(loadedImage);
            }
        });*/
        holder.ly_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  id=productlist.get(position).getMc_link();
                FileUtils fu=new FileUtils();
                fu.saveDataToFile(context,id);
                Intent intent3=new Intent(context,Sousuo2Activity.class);
               context.startActivity(intent3);
            }
        });
      /*  holder.child_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String categroy = list.get(position).getIds();
                ;
                Intent intent = new Intent(context, NewsousuoActivity.class);
                intent.putExtra("useid", categroy);
                context.startActivity(intent);

            }
        });*/

        return convertView;
    }
    public static class GridHolder {
        TextView newiv4,newiv3,newtv1;
        LinearLayout ly_pic;
        ImageView newid_left;
    }
}