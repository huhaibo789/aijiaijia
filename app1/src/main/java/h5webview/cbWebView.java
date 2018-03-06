package h5webview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by misshu on 2017/4/20/020.
 */
public class cbWebView extends WebView {
    private OnScrollChangedCallback mOnScrollChangedCallback;
    private boolean mDispachOverScrollEvent = true;
    private boolean mClampedX = false;
    private  SwipeRefreshLayout swipeRefreshLayout;
    public cbWebView(Context context) {
        super(context);

    }

    public cbWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private void init() {

    }

    public OnScrollChangedCallback getmOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }
    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }
    public interface OnScrollChangedCallback {
        void onScroll(int l, int t);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //普通webview用这个
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t);
    }
}
