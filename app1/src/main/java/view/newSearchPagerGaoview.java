package view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import adapter.Searchpageradapter1;
import adapter.Searchpageradapter2;
import bean.Listbean;
import bean.xiangqingbean;

/**
 * Created by 胡海波 on 2016/12/17.
 */
public class newSearchPagerGaoview extends LinearLayout {
    private Searchpageradapter2 adapter2;
    private ViewPager pager;
    private RadioGroup group;
    private Context context;
    private WebView webview2;
    private Runnable r;
    private Handler handle = new Handler();
    private boolean flag;
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist;
    private ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean> xqbean;
    private ArrayList<String> wuyu;
    private ArrayList<View> pagerList1 = new ArrayList<>();
    private ImageLoader loader;
    private ImageView[] mBottomImages;
    private List<ImageView> mIndicator;
    private LinearLayout ll_indicator;
    public newSearchPagerGaoview(Context context) {
        super(context);
        init(context);
    }

    public newSearchPagerGaoview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.search_more_itemdetail, this, true);
        initView(view);
        setadapter();
        // setadapter1();
        setlistener();
    }

  /*  private void setadapter1() {
        adapter2=new Searchpageradapter2(context);
        pager.setAdapter(adapter2);
        startSelect();
    }*/

    private void setlistener() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                //group.check(position % 5);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initRadio() {
        mIndicator =new ArrayList<ImageView>();

        for (int i = 0; i < 5; i++) {
            ImageView imag=new ImageView(context);
            //RadioButton rb = new RadioButton(context);
            imag.setId(i);
            imag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bei = pager.getCurrentItem() / 5;
                    pager.setCurrentItem(bei * 5 + v.getId());

                }
            });

            mIndicator.add(imag);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 10, 0);
            imag.setLayoutParams(lp);
            ll_indicator.addView(imag);

        }
        //  group.check(0);
    }
    private void setIndicator(int position) {
        position %= 5;
        //遍历mIndicator重置src为normal
        for (ImageView indicator : mIndicator){
            indicator.setImageResource(R.mipmap.lunbonom);
        }
        mIndicator.get(position).setImageResource(R.mipmap.lunbohigh);
    }
    private void startSelect() {
        if (pagerList1 == null) {
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        } else {
            r = new Runnable() {
                @Override
                public void run() {

                    int position = pager.getCurrentItem() + 1;

                    if (position == pagerList1.size()) {
                        pager.setCurrentItem(0);
                        Log.i("gangan", "run: " + pagerList1.size());
                    } else {
                        pager.setCurrentItem(position);
                    }

                }
            };

        }
    }

    private void setadapter() {
        adapter2=new Searchpageradapter2(context);
        pager.setAdapter(adapter2);
        startSelect();
    }

    private void initView(View view) {
        pager = (ViewPager) view.findViewById(R.id.viewpager1);
        ll_indicator=(LinearLayout)view.findViewById(R.id.ll_indicator1);
        initRadio();
    }
    public void updatePager2(ArrayList<String> wuyu, ImageLoader loader) {

        this.wuyu= wuyu;
        for (int i = 0; i < wuyu.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.newsearch_page_item, null);
            ImageView iv3 = (ImageView) view.findViewById(R.id.pager_iv3);
            iv3.setTag(wuyu.get(i));
            //iv2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            pagerList1.add(view);
            adapter2.setPagerList(pagerList1, loader);
            adapter2.notifyDataSetChanged();


        }
    }

}
