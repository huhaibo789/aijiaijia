package h5webview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by misshu on 2017/5/8/008.
 */
public class CustomSwipe extends SwipeRefreshLayout {
    private CanChildScrollUpCallback mCanChildScrollUpCallback;
    public interface CanChildScrollUpCallback {
        boolean canSwipeRefreshChildScrollUp();
    }
    public CustomSwipe(Context context) {
        super(context,null);
    }
    public  CustomSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {
        mCanChildScrollUpCallback = canChildScrollUpCallback;    }
    @Override
    public boolean canChildScrollUp() {
        if (mCanChildScrollUpCallback != null) {
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }
}
