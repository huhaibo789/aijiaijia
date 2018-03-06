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
import bean.Listbean;
import bean.xiangqingbean;

/**
 * Created by 胡海波 on 2016/8/20.
 */
public class SearchPagerGaoview6 extends LinearLayout {
    private Searchpageradapter adapter;
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
    public SearchPagerGaoview6(Context context) {
        super(context);
        init(context);
    }

    public SearchPagerGaoview6(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.search_more_itemdetail, this, true);
        initView(view);
        setadapter();
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
            ll_indicator.addView(imag);
            mIndicator.add(imag);
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
//                if (pagerList != null && position == pagerList.size()) {
//                    position = 0;
//                }

                    handle.postDelayed(this, 2000);
                }
            };
            handle.postDelayed(r, 2000);
        }
    }

    private void setadapter() {
        adapter = new Searchpageradapter(context);
        pager.setAdapter(adapter);
        startSelect();
    }

    private void initView(View view) {
        pager = (ViewPager) view.findViewById(R.id.viewpager);
        // group = (RadioGroup) view.findViewById(R.id.radiogroup);
        ll_indicator=(LinearLayout)view.findViewById(R.id.ll_indicator);
    /*    webview2= (WebView) view.findViewById(R.id.webview2);*/
        initRadio();
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

        for (int i = 0; i < wuyu.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.search_page_item, null);
            ImageView iv1 = (ImageView) view.findViewById(R.id.pager_iv1);
            iv1.setTag(wuyu.get(i));
            iv1.setScaleType(ImageView.ScaleType.FIT_XY);
            pagerList1.add(view);
            adapter.setPagerList(pagerList1, loader);
            adapter.notifyDataSetChanged();
            Log.i("gouwuche8", "updatePager1: "+wuyu.toString());

        }
    }
    public void updatePager10(ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean> xqbean, ImageLoader loader) {

        ArrayList<String> cc = new ArrayList<String>();
        cc.add("http://imgs.aijiaijia.com/product/2016-08-12/pro_c04832.jpg");
        cc.add("http://imgs.aijiaijia.com/product/2016-08-12/pro_a0ac0f.jpg");
        cc.add("http://imgs.aijiaijia.com/product/2016-08-12/pro_2ec699.jpg");
        cc.add("http://imgs.aijiaijia.com/product/2016-08-12/pro_9cc0c2.jpg");
        cc.add("http://imgs.aijiaijia.com/product/2016-08-23/pro_9ee99c.jpg");
        this.xqbean = xqbean;
        Log.i("wan", "updatePager: " + xqbean.toString());
        for (int i = 0; i < 5; i++) {
            Log.i("gangan1", "updatePager1: " + xqbean.size());
            View view = LayoutInflater.from(context).inflate(R.layout.search_page_item, null);
            ImageView iv1 = (ImageView) view.findViewById(R.id.pager_iv1);


            iv1.setTag(cc.get(i));
            iv1.setScaleType(ImageView.ScaleType.FIT_XY);

            pagerList1.add(view);
            adapter.setPagerList(pagerList1, loader);
            adapter.notifyDataSetChanged();


        }

    }
}
