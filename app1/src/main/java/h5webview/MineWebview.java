package h5webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by misshu on 2017/4/20/020.
 */
public class MineWebview extends WebView {
    private int oldX;
    public OnScrollChangeListener listener;
    private boolean overScrollLeft = false;
    ScrollInterface web;
    private boolean overScrollRight = false;

    private boolean isScrolling = false;

    public MineWebview(Context context) {
        super(context);

    }

    public MineWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MineWebview(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);
//Log.e("hhah",""+l+" "+t+" "+oldl+" "+oldt);
        web.onSChanged(l, t, oldl, oldt);

    }

    public void setOnCustomScroolChangeListener(ScrollInterface t){        this.web=t;
    }

    public interface ScrollInterface {

        public void onSChanged(int l, int t, int oldl, int oldt) ;
    }}



