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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import adapter.Searchpageradapter;
import adapter.Searchpageradapter1;
import adapter.Searchpageradapter2;
import bean.Listbean;
import bean.xiangqingbean;

/**
 * Created by 胡海波 on 2016/8/20.
 */
public class SearchPagerGaoview2 extends LinearLayout  {
    private Searchpageradapter1 adapter;
    private ViewPager pager;
    private RadioGroup group;
    private Context context;
    private WebView webview2;
    private Runnable r;
    private int currentIndex=0;
    private Handler handle = new Handler();
    private boolean flag;
    private ImageView dots;
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist;
    private ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean> xqbean;
    private ArrayList<String> wuyu=new ArrayList<>();
    private ArrayList<View> pagerList1 = new ArrayList<>();
    private ImageLoader loader;
    private ImageView[] mBottomImages;
    private List<ImageView> mIndicator;
    private LinearLayout ll_indicator;
    public SearchPagerGaoview2(Context context) {
        super(context);
        init(context);
    }

    public SearchPagerGaoview2(Context context, AttributeSet attrs) {
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
    private void setlistener() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                    setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initRadio() {
       /* mIndicator =new ArrayList<ImageView>();
        for (int i = 0; i < wuyu.size(); i++) {
            dots = new ImageView(context);
            dots.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT));
            dots.setPadding(0, 0, 20, 0);
            if (currentIndex == i) {
                dots.setBackgroundResource(R.mipmap.lunbohigh);
            } else {
                dots.setBackgroundResource(R.mipmap.lunbonom);
            }
           ll_indicator.addView(dots);
            mIndicator.add(dots);
        }*/
        mIndicator =new ArrayList<ImageView>();
        for (int i = 0; i < wuyu.size(); i++) {
            ImageView imag=new ImageView(context);
            //RadioButton rb = new RadioButton(context);
            imag.setId(i);
       imag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bei = pager.getCurrentItem() / wuyu.size();
                    pager.setCurrentItem(bei * wuyu.size()+ v.getId());
                }
            });

            mIndicator.add(imag);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 20, 0);
            imag.setLayoutParams(lp);

            ll_indicator.addView(imag);


        }
      //  group.check(0);
    }

    private void setIndicator(int position) {
          position%=wuyu.size();
        //遍历mIndicator重置src为normal
        Log.i("dffe", "setIndicator: "+position);
        for (ImageView indicator : mIndicator){
            indicator.setImageResource(R.drawable.graydots);
        }
        if(position<wuyu.size()){
            mIndicator.get(position).setImageResource(R.drawable.yellowdots);
        }

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
                    } else {
                        pager.setCurrentItem(position);
                    }
//                if (pagerList != null && position == pagerList.size()) {
//                    position = 0;
//                }


                }
            };

        }
    }

    private void setadapter() {
        adapter = new Searchpageradapter1(context);
        pager.setAdapter(adapter);
        startSelect();
    }

    private void initView(View view) {
        pager = (ViewPager) view.findViewById(R.id.viewpager1);
        ll_indicator=(LinearLayout)view.findViewById(R.id.ll_indicator1);
        //initRadio();
    }

    public void updatePager(ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist, ImageLoader loader) {
        this.courlist = courlist;
        Log.i("wan", "updatePager: " + courlist.toString());
        for (int i = 0; i < courlist.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.search_page_item, null);
            ImageView iv1 = (ImageView) view.findViewById(R.id.pager_iv1);

            iv1.setTag(courlist.get(i).getId());
            iv1.setScaleType(ImageView.ScaleType.FIT_XY);

            pagerList1.add(view);
            adapter.setPagerList(pagerList1, loader);
            adapter.notifyDataSetChanged();


        }

    }

    public void updatePager1(ArrayList<String> wuyu, ImageLoader loader) {

        this.wuyu= wuyu;
        if(wuyu.size()!=0){
            Log.i("daytew", "updatePager1: "+"hsde");
            initRadio();

            if(wuyu.size()>1){
                for (ImageView indicator : mIndicator){
                    indicator.setImageResource(R.drawable.graydots);
                }
                mIndicator.get(0).setImageResource(R.drawable.yellowdots);
            }


        }
        for (int i = 0; i < wuyu.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.search_page_item1, null);
            ImageView iv2 = (ImageView) view.findViewById(R.id.pager_iv2);
            iv2.setTag(wuyu.get(i));
            //iv2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            pagerList1.add(view);
            adapter.setPagerList(pagerList1, loader);
            adapter.notifyDataSetChanged();

        }
    }


}
