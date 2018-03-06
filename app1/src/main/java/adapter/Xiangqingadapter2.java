package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import bean.xiangqingbean1;
import bean.xiangqingbean2;
import view.SearchPagerGaoview3;
import view.SearchPagerGaoview4;

/**
 * Created by 胡海波 on 2016/8/22.
 */
public class Xiangqingadapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<xiangqingbean2.ResponseJsonBean.ListProductBean> xqbean2=new ArrayList<>();
    public Xiangqingadapter2(Context context, ImageLoader loader){
        inflater=LayoutInflater.from(context);
        this.loader=loader;
    }
    public void updatexqbean (ArrayList<xiangqingbean2.ResponseJsonBean.ListProductBean> xqbean2){
        this.xqbean2=xqbean2;
        Log.i("msng", "updatexqbean: "+xqbean2);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new mXiangqing(inflater.inflate(R.layout.search_more3,parent,false));
                break;
            case 1:
                holder=new mXiangqing1(inflater.inflate(R.layout.activity_xiangqing,parent,false));
                break;
            case 2:
                holder=new mXiangqing2(inflater.inflate(R.layout.activity_xiangqing1,parent,false));
                break;
            case 3:
                holder=new mXiangqing3(inflater.inflate(R.layout.activity_xiangqing2,parent,false));
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else if(position==1){
            return 1;
        }else if(position==2){
            return 2;
        }else {
            return 3;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                mXiangqing mxholder= (mXiangqing) holder;
                if(xqbean2!=null){
                    mxholder.gaoview4.updatePager3(xqbean2,loader);
                }
                break;
            case 1:
                mXiangqing1 mxholder1= (mXiangqing1) holder;

                for (int i = 0; i <xqbean2.size() ; i++) {
                    String ss=xqbean2.get(0).getProduct_name();
                    String hh2=String.valueOf( xqbean2.get(0).getProduct_nowprice());
                    String hh4=String.valueOf(xqbean2.get(0).getProduct_price());

                    Log.i("msng1", "onBindViewHolder: "+hh2);
                    mxholder1.xq_tv.setText(ss);
                    mxholder1.xq_tv1.setText(hh2);
                    mxholder1.yuanjia_tv.setText("原价:"+hh4);
                }

              /*  mxholder1.xq_iv.setImageResource(R.mipmap.ic_launch);
                mxholder1.xq_iv1.setImageResource(R.mipmap.ic_launcher);*/
                break;
            case 2:
                mXiangqing2 mxholder2= (mXiangqing2) holder;
                for (int i = 0; i < xqbean2.size(); i++) {
                    String hh3=String.valueOf(xqbean2.get(0).getProduct_stock());
                    mxholder2.yaoqiu_tv.setText("库存:"+hh3+"件");
                }
                mxholder2.yaoqiu_tv1.setText("正品保证");
                mxholder2.yaoqiu_tv2.setText("免运费");
                mxholder2.yaoqiu_tv3.setText("假一赔五");
                break;
            case 3:
                mXiangqing3 mxholder3= (mXiangqing3) holder;
                mxholder3.shangpin_tv.setText("商品评价");


                break;
        }
    }

    @Override
    public int getItemCount() {
        int count=xqbean2==null?0:xqbean2.size()+3;
        return count;
    }
    class mXiangqing  extends   RecyclerView.ViewHolder{
        SearchPagerGaoview4 gaoview4;
        public mXiangqing(View itemView) {
            super(itemView);
            gaoview4= (SearchPagerGaoview4) itemView.findViewById(R.id.searchmoregaoview3);
        }
    }
    class mXiangqing1 extends  RecyclerView.ViewHolder{
        private TextView xq_tv,xq_tv1,yuanjia_tv;
        private ImageView xq_iv,xq_iv1;
        public mXiangqing1(View itemView) {
            super(itemView);
            yuanjia_tv = (TextView) itemView.findViewById(R.id.yuanjia_tv);
            xq_tv= (TextView) itemView.findViewById(R.id.xq_tv);
            xq_tv1= (TextView) itemView.findViewById(R.id.xq_tv1);
            xq_iv= (ImageView) itemView.findViewById(R.id.xq_iv);
            xq_iv1= (ImageView) itemView.findViewById(R.id.xq_iv1);

        }
    }
    class mXiangqing2 extends  RecyclerView.ViewHolder{
        private TextView yaoqiu_tv,yaoqiu_tv1,yaoqiu_tv2,yaoqiu_tv3;

        public mXiangqing2(View itemView) {
            super(itemView);
            yaoqiu_tv= (TextView) itemView.findViewById(R.id.yaoqiu_tv);
            yaoqiu_tv1= (TextView) itemView.findViewById(R.id.yaoqiu_tv1);
            yaoqiu_tv2= (TextView) itemView.findViewById(R.id.yaoqiu_tv2);
            yaoqiu_tv3= (TextView) itemView.findViewById(R.id.yaoqiu_tv3);


        }
    }
    class mXiangqing3 extends  RecyclerView.ViewHolder{
        private TextView shangpin_tv;
        private TextView arrive_iv,shop_iv,buy_iv;
        private  ImageView shangpin_iv;
        public mXiangqing3(View itemView) {
            super(itemView);
            shangpin_tv= (TextView) itemView.findViewById(R.id.shangpin_tv);
            arrive_iv= (TextView) itemView.findViewById(R.id.arrive_iv);
            shop_iv= (TextView) itemView.findViewById(R.id.shop_iv);
            buy_iv= (TextView) itemView.findViewById(R.id.buy_iv);


        }
    }
}
