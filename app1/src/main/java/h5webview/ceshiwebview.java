package h5webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

import utils.FileUtils;
import utils.FileUtils28;

/**
 * Created by misshu on 2017/4/27/027.
 */
public class ceshiwebview extends WebView {
    private float xDown;
    private float yDown;
    private float xUp;
    private float yUp;
    public ceshiwebview(Context context) {
        super(context);
    }
    public ceshiwebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        FileUtils28 fileUtils28=new FileUtils28();
        String aa=fileUtils28.readDataFromFile(getContext());
        if(aa!=null&&aa.equals("none")){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }
}
