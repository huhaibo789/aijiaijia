package h5Fragement;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by misshu on 2017/5/8/008.
 */
public class scroll extends SwipeRefreshLayout {
    private ViewGroup viewGroup;
    public scroll(Context context) {
        super(context);
    }

    public scroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ViewGroup getViewGroup() {
        return viewGroup;
    }
    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if(null!=viewGroup){
            if(viewGroup.getScrollY()> 1){

                Log.i("keu", "onTouchEvent: "+"1");
                //直接截断时间传播
                return false;
            }else{
                Log.i("keu", "onTouchEvent: "+"2");
                return super.onTouchEvent(arg0);
            }
        }
        return super.onTouchEvent(arg0);
    }

}
