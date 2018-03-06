package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Searchpageradapter extends PagerAdapter {
    private ImageLoader loader;
    LayoutInflater inflater;

    ArrayList<View> pagerList ;
    public void setPagerList(ArrayList<View> pagerList, ImageLoader loader) {
        this.pagerList = pagerList;
        this.loader = loader;
    }
    public Searchpageradapter (Context context) {
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return pagerList == null?0:pagerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=pagerList.get(position);
        container.addView(view);
        ImageView iv1= (ImageView) view.findViewById(R.id.pager_iv1);
        loader.displayImage(iv1.getTag().toString(),iv1);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagerList.get(position));
        Log.i("","===== remove remove!!!!  "+"  "+position%pagerList.size());
    }
}
