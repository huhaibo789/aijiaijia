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
import java.util.Arrays;
import java.util.List;

import bean.Listbean;

/**
 * Created by misshu on 2017/4/26/026.
 */
public class Aslocationselectbrand extends BaseAdapter {
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.location,R.drawable.location1
    ));
    private ArrayList<String> title=new ArrayList<>();

    public Aslocationselectbrand(ImageLoader loader,Context context) {
        title.add("金陵展厅");
        title.add("金盛展厅");
        this.context=context;
        this.loader=loader;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return 2;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_locationbrand, null);
            holder.location_iv= (ImageView) convertView.findViewById(R.id.location_iv);
            holder.product_name= (TextView) convertView.findViewById(R.id.product_name);
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
        holder.location_iv.setImageResource(mDatas.get(position));
        holder.product_name.setText(title.get(position));
        return convertView;
    }
    public static class GridHolder {
        ImageView location_iv;
        TextView product_name;

    }
}
