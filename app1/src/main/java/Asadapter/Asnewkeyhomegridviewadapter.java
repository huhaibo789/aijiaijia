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
public class Asnewkeyhomegridviewadapter extends BaseAdapter {
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean> productlist;;
    private ImageLoader loader;
    private Context context;
    private HorizontalScrollViewAdapter mAdapter;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.androidlogo, R.drawable.androidlogo, R.drawable.androidlogo, R.drawable.androidlogo,
            R.drawable.androidlogo));
    public Asnewkeyhomegridviewadapter(ImageLoader loader,Context context) {

        this.context=context;
        this.loader=loader;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return 2;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_keyhomegrid, null);
            holder.mImg= (ImageView) convertView.findViewById(R.id.id_content);
            holder.keyhome_tv= (TextView) convertView.findViewById(R.id.keyhome_tv);
            holder.mHorizontalScrollView= (MyHorizontalScrollView) convertView.findViewById(R.id.id_horizontalScrollView);
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
        mAdapter = new HorizontalScrollViewAdapter(context, mDatas);
        //添加滚动回调
        final GridHolder finalHolder1 = holder;
        holder.mHorizontalScrollView
                .setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener()
                {
                    @Override
                    public void onCurrentImgChanged(int position,
                                                    View viewIndicator)
                    {
                        finalHolder1. mImg.setImageResource(mDatas.get(position));

                    }
                });
        //添加点击回调
        final GridHolder finalHolder2 = holder;
        holder.mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener()
        {

            @Override
            public void onClick(View view, int position)
            {
                finalHolder2.mImg.setImageResource(mDatas.get(position));

            }
        });
        //设置适配器
        holder.mHorizontalScrollView.initDatas(mAdapter);
        return convertView;
    }
    public static class GridHolder {
        ImageView mImg;
        TextView keyhome_tv;
        MyHorizontalScrollView mHorizontalScrollView;
    }
}
