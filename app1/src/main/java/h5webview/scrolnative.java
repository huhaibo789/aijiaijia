package h5webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by misshu on 2017/5/8/008.
 */
public class scrolnative extends WebView {

    public scrolnative(Context context) {
        super(context);
    }

    public scrolnative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public scrolnative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public interface ScrollViewListener {
        void onScrollChanged(WebView scrolnative, int x, int y, int oldx, int oldy);

    }
    private ScrollViewListener scrollViewListener = null;


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        Log.i("haha", "onScrollChanged: "+y);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
