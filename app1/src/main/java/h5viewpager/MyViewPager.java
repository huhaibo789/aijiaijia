package h5viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.example.administrator.myyushi.R;

import h5webview.customwebview;

/**
 * Created by misshu on 2017/4/20/020.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
   /* private boolean mNoScroll = false;//标记不允许左右滚动
    public MyViewPager(Context context) {
        super(context);

    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
  *//*  @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        Log.i("meiguo", "canScroll: "+"1");
      customwebview wev= (customwebview) v.findViewById(R.id.h5webs); //res ID
        if (v != this && (v instanceof customwebview)) {
            Log.i("chaoxian", "canScroll: "+"2");
            requestDisallowInterceptTouchEvent(false);
            return true;
        }else {
            requestDisallowInterceptTouchEvent(true);
            Log.i("chaoxian", "canScroll: "+"3");
        }
        return super.canScroll(v, checkV, dx, x, y);
    }*//*
   @Override
   protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
       Log.i("duzitong", "canScroll: "+v);
       Log.i("duzitong", "canScroll: "+checkV);
       Log.i("duzitong", "canScroll: "+dx);
       Log.i("duzitong", "canScroll: "+x);
       Log.i("duzitong", "canScroll: "+y);


       //customwebview wev= (customwebview) v.findViewById(R.id.h5webs); //res ID
       if ( wev != null ) {
           Log.i("duzitong", "canScroll: "+"d");
           return wev.canScrollHorizontally ( -dx ) ;
       } else {
           Log.i("duzitong", "canScroll: "+"dde");
           return super.canScroll ( v, checkV, dx, x, y ) ;
       }
   }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    //设置不允许左右滚动
    public void setNoScroll(boolean noScroll) {
        this.mNoScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (mNoScroll) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mNoScroll) {
            return false;
        }

        return super.onInterceptTouchEvent(arg0);
    }
*//*
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        customwebview view = (customwebview) v.findViewById ( R.id.h5webs ); //res ID
        if ( view != null ) {
            Log.i("huahau", "canScroll: "+-dx);
            return view.canScrollHorizontally ( -dx ) ;
        } else {
            Log.i("huahau1", "canScroll: "+-dx);
            return super.canScroll ( v, checkV, dx, x, y ) ;
        }

    }*/
}
