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

import bean.Listbean;
import bean.fenleibean;
import utils.FileUtils;

/**
 * Created by 胡海波 on 2016/12/20.
 */
public class newgridfenlei1 extends BaseAdapter {

    private  ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean> productbiglist4=new ArrayList<>();
    private ImageLoader loader;
    private Context context;
    public newgridfenlei1(  ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean> productbiglist4,ImageLoader loader,Context context) {
        this.productbiglist4 = productbiglist4;
        this.context=context;
        this.loader=loader;
    }

    @Override
    public int getCount() {
        return productbiglist4.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return productbiglist4.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_xingridview, null);
            holder.htextView1 = (TextView) convertView.findViewById(R.id.htextView1);
            holder.himageView1 = (ImageView) convertView.findViewById(R.id.himageView1);
            holder. hchild_item = (RelativeLayout) convertView.findViewById(R.id. hchild_item);
            convertView.setTag(holder);
        } else {
            holder = (GridHolder) convertView.getTag();
        }
        if(productbiglist4!=null){
            holder.htextView1.setText(productbiglist4.get(position).getCategorieName());
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
        //loader.displayImage(productbiglist4.get(position).getCategoriePic(),holder.himageView1, options);
         loader.loadImage(productbiglist4.get(position).getCategoriePic(),mImageSize,options,new SimpleImageLoadingListener(){
             @Override
             public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                 super.onLoadingComplete(imageUri, view, loadedImage);
                 finalHolder.himageView1.setImageBitmap(loadedImage);
             }
         });
     /* loader.loadImage(productbiglist4.get(position).getCategoriePic(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalHolder.himageView1.setImageBitmap(loadedImage);
            }
        });*/
        holder.himageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categroy = productbiglist4.get(position).getId();
                Intent intent = new Intent(context, NewsousuoActivity.class);
                intent.putExtra("useid", categroy);
                context.startActivity(intent);
              /*  String  id=productbiglist4.get(position+1).getMc_link();
                Log.i("gdds", "onClick: "+id);
                FileUtils fu=new FileUtils();
                fu.saveDataToFile(context,id);
                Intent intent3=new Intent(context,Sousuo2Activity.class);
                context.startActivity(intent3);*/
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
        TextView htextView1;
        RelativeLayout hchild_item;
        ImageView himageView1;
    }
}
