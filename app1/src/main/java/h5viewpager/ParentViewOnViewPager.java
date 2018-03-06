package h5viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by misshu on 2017/4/20/020.
 */
public class ParentViewOnViewPager extends ViewPager {

    private boolean isSmallToBig = true;
    private boolean isBigToSmall = false;
    //记录上一次滑动的positionOffsetPixels值
    private int lastValue = -1;

    public ParentViewOnViewPager(Context context) {
        super(context);
    }


    public ParentViewOnViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);

    }

    @Override
    protected void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset != 0) {
            boolean isLeft = true;
            if (lastValue >= positionOffsetPixels) {
                //右滑
                isLeft = false;
            } else if (lastValue < positionOffsetPixels) {
                //左滑
                isLeft = true;
            }
            setIndiactorView(positionOffset,isLeft);
        }
        lastValue = positionOffsetPixels;

        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    private void setIndiactorView(float positionOffset, boolean isLeft) {

        //设定滑动的边界，这里设置的滑动到一半
        float radiusOffsetHead = 0.5f;
        //如果滑动小于一半，通过isSmallToBig这个变量来避免多次调用
        // initIndicatorView 方法
        if (positionOffset <= radiusOffsetHead && !isSmallToBig) {

            Log.i("huht", "setIndiactorView: "+"44");
            isSmallToBig = true;
            isBigToSmall = false;

        } else if (positionOffset > radiusOffsetHead && !isBigToSmall) {
            //超过一半。如果是左滑，就-1，如果是右滑，就+1
            Log.i("jkol", "setIndiactorView: "+"55");
          /*  int currnt  = isLeft ? mCurrentPage + 1 : mCurrentPage - 1;
            initIndicatorView(currnt);*/
            isBigToSmall = true;
            isSmallToBig = false;
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }
/*  @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        Log.i("huhd", "canScroll: "+dx);
        Log.i("jje", "canScroll: "+y);
        Log.i("jje1", "canScroll: "+x);
        if(y<330){
            int currentItem = ((ViewPager) v).getCurrentItem();
            int countItem = ((ViewPager) v).getAdapter().getCount();
            if((currentItem==(countItem-1) && dx<0) || (currentItem==0 && dx>0)){
                return false;
            }
            return true;
        }


        return super.canScroll(v, checkV, dx, x, y);
    }*/
}
