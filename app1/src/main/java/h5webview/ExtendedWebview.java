package h5webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by misshu on 2017/4/20/020.
 */
public class ExtendedWebview extends WebView {
    // 滑动距离及坐标 归还父控件焦点
    private float xDistance, yDistance, xLast, yLast,xDown, mLeft;
    public ExtendedWebview(Context context) {
        super(context);
    }
    public ExtendedWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public  ExtendedWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("shizi", "dispatchTouchEvent: "+ev.getX());
        Log.i("shizi1", "dispatchTouchEvent: "+ev.getY());
        int num= (int) ev.getX();
        if(num>580){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("touch", "ACTION_DOWN");
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                xDown = ev.getX();
                mLeft = ev.getX();// 解决与侧边栏滑动冲突
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
               /* if (mLeft<100||xDistance < yDistance) {
                    Log.i("haibo", "dispatchTouchEvent: "+"meizi");
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    Log.i("feiwu", "dispatchTouchEvent: "+curX);
                    getParent().requestDisallowInterceptTouchEvent(true);
                }*/
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("touch", "ACTION_DOWN");
                xDistance = yDistance = 0f;
                xLast = event.getX();
                yLast = event.getY();
                xDown = event.getX();
                mLeft = event.getX();// 解决与侧边栏滑动冲突
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = event.getX();
                final float curY = event.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (mLeft < 100 || xDistance < yDistance) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (curX < xDown) {
                        Log.i("ggyu", "onTouchEvent: "+curX);
                        Log.i("ggyu1", "onTouchEvent: "+xDown);
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        Log.i("ggyu2", "onTouchEvent: "+curX);
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    if (curX > xDown) {
                        Log.i("ggyu3", "onTouchEvent: "+curX);
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        Log.i("ggyu4", "onTouchEvent: "+curX);
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                 *//*   if (getCurrentItem() == 0) {

                    } else if (getCurrentItem() == (getAdapter().getCount()-1)) {

                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }*//*
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }*/
    /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getY()>8&&event.getY()<310){
            getParent().requestDisallowInterceptTouchEvent(true);
        }else {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    num1=(int)event.getX();
                    Log.i("weare", "onTouchEvent: "+event.getX());
                    Log.i("wearey", "onTouchEvent: "+event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("yuanwei1", "onTouchEvent: "+"1");



                    if(num>300){
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                    Log.i("weare1", "onTouchEvent: "+event.getX());
                    break;
                case MotionEvent.ACTION_UP:
                    movex2=(int)event.getX();
                    num=Math.abs(movex2-num1);
                    Log.i("suiran", "onTouchEvent: "+num);
                    getParent().requestDisallowInterceptTouchEvent(false);
                    Log.i("weare2", "onTouchEvent: "+event.getX());
            *//*    Log.i("yuanwei2", "onTouchEvent: "+"2");
                if (event.getAction() == MotionEvent.ACTION_UP){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(true);
                }*//*



                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }*/
}
