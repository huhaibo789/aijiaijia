package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myyushi.MainActivity;
import com.example.administrator.myyushi.R;

import java.util.List;

/**
 * Created by 胡海波 on 2016/12/24.
 */
public class ViewPagerAdp extends PagerAdapter {

    // �����б�
    private List<View> views;
    private static final String SHAREDPREFERENCES_NAME = "FirstEntrance";
    private ImageView iv_start;
    private Context context;

    public ViewPagerAdp(List<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    // ���arg1λ�õĽ���
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public void finishUpdate(ViewGroup container) {
    }

    // ��õ�ǰ������
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    // ��ʼ��arg1λ�õĽ���
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        if (position == views.size() - 1) {
            iv_start = (ImageView) container.findViewById(R.id.iv_start);
            iv_start.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // �����Ѿ���
                    setGuided();
                    goHomePage();

                }

            });
        }
        return views.get(position);
    }

    private void goHomePage() {
        // ��ת
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    /**
     *
     * �����Ѿ�����ˣ��´����������ٴ���
     */
    private void setGuided() {
        SharedPreferences preferences = context.getSharedPreferences(
                SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // �������
        editor.putBoolean(SHAREDPREFERENCES_NAME, true);
        // �ύ�޸�
        editor.commit();
    }

    // �ж��Ƿ��ɶ�����ɽ���
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return (view == obj);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View view) {
    }
}
