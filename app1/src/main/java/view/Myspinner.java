package view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

/**
 * Created by 胡海波 on 2016/8/12.
 */
public class Myspinner extends TextView {
    private Context mContext;
    private ArrayAdapter adapter;
    private ListView popContentView;
    private AdapterView.OnItemClickListener onItemClickListener;
    private PopupWindow mDropView;
    public Myspinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater  inflater=LayoutInflater.from(mContext);
        LinearLayout container= (LinearLayout) inflater.inflate(R.layout.spinner_content,null);
        popContentView= (ListView) container.findViewById(R.id.spinner_content);
        mDropView = new PopupWindow(container, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mDropView.setBackgroundDrawable(new BitmapDrawable());
        mDropView.setFocusable(true);
        mDropView.setOutsideTouchable(true);
        mDropView.setOutsideTouchable(true);
        mDropView.setTouchable(true);

      /* this.setOnClickListener(new onclickListener);*/
    }
}
