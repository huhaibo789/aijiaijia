package adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 胡海波 on 2016/11/21.
 */
 public  class mylistview extends ListView {
    public mylistview(Context context) {
        super(context);
    }

    public mylistview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mylistview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
