package Asadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import java.util.ArrayList;
import bean.Listbean;
/**
 * Created by misshu on 2017/4/26/026.
 */
public class Asbrandgridviewadapter extends BaseAdapter {
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    public Asbrandgridviewadapter(ImageLoader loader,Context context) {
        this.context=context;
        this.loader=loader;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return 1;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_asbrandgridview, null);
            holder.styleproduct= (ImageView) convertView.findViewById(R.id.styleproduct);
            holder.galleyproduct= (TextView) convertView.findViewById(R.id.galleyproduct);
            holder.production= (TextView) convertView.findViewById(R.id.production);
            holder.productionprice= (TextView) convertView.findViewById(R.id.productionprice);
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
       /* loader.loadImage(productlist.get(position).getCategoriePic(),mImageSize,options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalHolder.styleproduct.setImageBitmap(loadedImage);
            }
        });*/
      /*  loader.loadImage(productlist.get(position).getCategoriePic(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalHolder.newid_left.setImageBitmap(loadedImage);
            }
        });*/
     /*   holder.zhanshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  id=productlist.get(position).getMc_link();
                FileUtils fu=new FileUtils();
                fu.saveDataToFile(context,id);
                Intent intent3=new Intent(context,Sousuo2Activity.class);
                context.startActivity(intent3);
            }
        });*/
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
        ImageView styleproduct;
        TextView galleyproduct,production,productionprice;

    }
}
