package Asadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
public class Asbrandgridlastadapter extends BaseAdapter {
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    public Asbrandgridlastadapter( ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist,ImageLoader loader,Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_asbrandgridview1, null);
            holder.image_product= (ImageView) convertView.findViewById(R.id.image_product);
            holder.brand_tv= (TextView) convertView.findViewById(R.id.brand_tv);
            holder.brand_price= (TextView) convertView.findViewById(R.id.brand_price);
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
                finalHolder.image_product.setImageBitmap(loadedImage);
            }
        });
      /*  loader.loadImage(productlist.get(position).getCategoriePic(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalHolder.newid_left.setImageBitmap(loadedImage);
            }
        });*/
        holder.image_product.setOnClickListener(new View.OnClickListener() {
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
        ImageView image_product;
        TextView brand_tv,brand_price;

    }
}
