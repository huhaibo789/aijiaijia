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
 * Created by misshu on 2017/4/26/026.
 */
public class Asbrandhorgridviewadapter extends BaseAdapter {
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    private HorizontalScrollViewAdapter mAdapter1;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.lunbo, R.drawable.lunbo1, R.drawable.lunbo2, R.drawable.lunbo3,R.drawable.lunbo4
    ));
    public Asbrandhorgridviewadapter(ImageLoader loader,Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_horasbrandgridview, null);
            holder.imagiv= (ImageView) convertView.findViewById(R.id.id_contentiv);
            holder.mHorizontalScrollView= (MyHorizontalScrollView) convertView.findViewById(R.id.id_horizontalScrollView);
            holder.keyhome_tv= (TextView) convertView.findViewById(R.id.Selectkeyhome_tv);
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
        mAdapter1 = new HorizontalScrollViewAdapter(context, mDatas);
        final GridHolder finalHolder1 = holder;
        holder.mHorizontalScrollView.setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
                finalHolder1.imagiv.setImageResource(mDatas.get(position));
            }
        });
        final GridHolder finalHolder2 = holder;
        holder.mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                finalHolder2.imagiv.setImageResource(mDatas.get(position));
            }
        });
        //设置适配器
        holder.mHorizontalScrollView.initDatas(mAdapter1);
        return convertView;
    }
    public static class GridHolder {
        ImageView imagiv;
        MyHorizontalScrollView mHorizontalScrollView;
        TextView keyhome_tv;

    }
}
