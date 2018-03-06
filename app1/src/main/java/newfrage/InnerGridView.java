package newfrage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 胡海波 on 2016/12/14.
 */
public class InnerGridView extends GridView {
    public InnerGridView(Context context) {
        super(context);

    }

    public InnerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
