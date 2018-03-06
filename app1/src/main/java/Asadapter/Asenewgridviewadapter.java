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

import adapter.HorizontalScrollViewAdapter;
import adapter.MyHorizontalScrollView;
import bean.Listbean;

/**
 * Created by misshu on 2017/4/25/025.
 */
public class Asenewgridviewadapter extends BaseAdapter {
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> price=new ArrayList<>();
    private ArrayList<String> previous=new ArrayList<>();
    private newHorizontalScrollViewAdapter madpater;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.lunbo, R.drawable.lunbo1, R.drawable.lunbo2, R.drawable.lunbo3,R.drawable.lunbo4
    ));
    public Asenewgridviewadapter(ArrayList<String> title,ArrayList<String> price,ArrayList<String> previous,ImageLoader loader,Context context) {
        this.title=title;
        this.price=price;
        this.previous=previous;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_newhorasbrand, null);
            holder.id_newhorizontalScrollView= (newMyHorizontallyScrolley) convertView.findViewById(R.id.id_newhorizontalScrollView);
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
        madpater = new newHorizontalScrollViewAdapter(title,price,previous,context, mDatas);
        final GridHolder finalHolder1 = holder;

        //设置适配器
        holder.id_newhorizontalScrollView.initDatas(madpater);
        return convertView;
    }
    public static class GridHolder {

        newMyHorizontallyScrolley id_newhorizontalScrollView;


    }
}
