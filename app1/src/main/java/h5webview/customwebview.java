package h5webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.webkit.WebView;

/**
 * Created by misshu on 2017/4/17/017.
 */
public class customwebview extends WebView {
    //手指向右滑动时的最小速度
    private static final int XSPEED_MIN = 200;
    //手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 200;
    //记录手指按下时的横坐标。
    private float xDown;

    //记录手指移动时的横坐标。
    private float xMove;

    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;
  private float xUp;
    private float yUp;
    Context mContext;
    public customwebview(Context context) {

        super(context);
        this.mContext=context;
        init();
    }

    private void init() {

    }

    public customwebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init();

    }
    public customwebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                //活动的距离
                int distanceX = (int) (xMove - xDown);
                //获取顺时速度
                int xSpeed = getScrollVelocity();
                //当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
                // if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
                if(distanceX>0){
                    requestDisallowInterceptTouchEvent(true);
                }else {
                    if(Math.abs(distanceX) > XDISTANCE_MIN) {
                        getParent().requestDisallowInterceptTouchEvent(false);

                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.i("zb3", "onTouchEvent: "+"zb3");
                xUp=event.getX();
                yUp=event.getY();
                if(xUp-xDown<-30){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else if(xUp-xDown>30){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        Log.i("kule4", "getScrollVelocity: "+velocity);
        return Math.abs(velocity);
    }

    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }
    /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int offset = computeHorizontalScrollOffset();
        final int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        Log.i("jianren", "onTouchEvent: "+offset);
        Log.i("jianren1", "onTouchEvent: "+range);
        Log.i("jiji", "onTouchEvent: "+event.getX());
        Log.i("zaixiangyu", "onTouchEvent: "+event.getY());
        Log.i("jiuzheyang", "onTouchEvent: "+  event.getRawX());

        return super.onTouchEvent(event);
    }*/

  /*  // 重载滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        Log.i("suihe", "onTouchEvent: "+evt.getY());
        if(evt.getY()>8&&evt.getY()<320){
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.onTouchEvent(evt);
    }*/
}
