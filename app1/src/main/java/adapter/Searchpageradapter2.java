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
 * Created by 胡海波 on 2016/12/17.
 */
public class Searchpageradapter2 extends PagerAdapter {
    private ImageLoader loader;
    LayoutInflater inflater;

    ArrayList<View> pagerList ;
    public void setPagerList(ArrayList<View> pagerList, ImageLoader loader) {
        this.pagerList = pagerList;
        this.loader = loader;
    }
    public Searchpageradapter2 (Context context) {
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
        final ImageView iv1= (ImageView) view.findViewById(R.id.pager_iv3);
        loader.displayImage(iv1.getTag().toString(),iv1);
       /* loader.loadImage(iv1.getTag().toString(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                iv1.setImageBitmap(loadedImage);
            }
        });*/
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagerList.get(position));
        Log.i("","===== remove remove!!!!  "+"  "+position%pagerList.size());
    }
}
