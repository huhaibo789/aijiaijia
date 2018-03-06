package nativetwoFragement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.R;

import adapter.FragmentAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by misshu on 2017/4/20/020.
 */
public class homepageFragement extends Fragment {
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private RequestQueue queue;
    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
        /*//透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
    }
    private void initViewPager() {
        Fragment tabFrames[] = new Fragment[]{new AsselectbrandFragment(), new AskeyhomeFragment(), new AsColorfullifeFragment(), new AsbulesirFragement()};
        String tabTitles[] = new String[]{"精选品牌", "一键购家", "缤纷活动", "蓝领先生"};
        FragmentPagerAdapter mFragmentAdapteradapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), tabTitles, tabFrames);
        for (int i = 0; i < tabTitles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles[i]));
            //给ViewPager设置适配器
            viewpager.setAdapter(mFragmentAdapteradapter);
            //将TabLayout和ViewPager关联起来。
            mTabLayout.setupWithViewPager(viewpager);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homepage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
