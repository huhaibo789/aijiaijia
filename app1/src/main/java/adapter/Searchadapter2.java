package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import bean.Sousobean1;

/**
 * Created by 胡海波 on 2016/8/17.
 */
public class Searchadapter2  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<Sousobean1.ResponseJsonBean.ListBrandBean> brandbean = new ArrayList<>();
    private ArrayList<Sousobean1.ResponseJsonBean.ListBean> listbean1 = new ArrayList<>();
    private ArrayList<Sousobean1.ResponseJsonBean.ListStyleBean> stylebean = new ArrayList<>();
    public  Searchadapter2(Context context,ImageLoader loader){
        inflater=LayoutInflater.from(context);
        this.loader=loader;
    }
    public  void updatebrand(ArrayList<Sousobean1.ResponseJsonBean.ListBrandBean> brandbean){
        this.brandbean=brandbean;
    }
    public  void updatelistbean1(ArrayList<Sousobean1.ResponseJsonBean.ListBean> listbean1){
        this.listbean1=listbean1;
    }
    public  void updatestylebean(ArrayList<Sousobean1.ResponseJsonBean.ListStyleBean> stylebean ){
        this.stylebean=stylebean;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new mtupian(inflater.inflate(R.layout.activity_sousi,parent,false));
                break;
            case 1:
                holder=new mtupian1(inflater.inflate(R.layout.activity_sousi,parent,false));
                break;
            case 2:
                holder=new mtupian2(inflater.inflate(R.layout.activity_sousi,parent,false));
                break;
            case 3:
                holder=new mtupian3(inflater.inflate(R.layout.activity_sousi,parent,false));
                break;
            case 4:
                holder=new mtupian4(inflater.inflate(R.layout.activity_sousi,parent,false));
                break;
            case 5:
                holder=new mtupian5(inflater.inflate(R.layout.activity_sousi,parent,false));
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
        }else if(position==3){
            return 3;
        }else  {
            return 4;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                switch (getItemViewType(position)){
                    case 0:
                        final mtupian mhod= (mtupian) holder;
                        if(listbean1!=null){


                        for (int i = 0; i <listbean1.size() ; i++) {
                            String ii = String.valueOf(listbean1.get(0).getProduct_name());
                            mhod.jiage_tv.setText(ii);
                            Log.i("jjstart", "onBindViewHolder: " + listbean1.get(0).getProduct_name());

                            String jia = String.valueOf(listbean1.get(0).getProduct_nowprice());
                            String jia1 = String.valueOf(listbean1.get(0).getProduct_price());
                            String jia2 = String.valueOf(listbean1.get(1).getProduct_nowprice());
                            String jia3 = String.valueOf(listbean1.get(1).getProduct_price());
                            mhod.jiage_tv1.setText(jia);
                            mhod.jiage_tv2.setText(jia1);
                            mhod.jiage_tv3.setText(listbean1.get(1).getProduct_name());
                            mhod.jiage_tv4.setText(jia2);
                            mhod.jiage_tv5.setText(jia3);

                            loader.loadImage(listbean1.get(0).getProduct_pic(), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    mhod.jiage_iv.setImageBitmap(loadedImage);
                                }
                            });
                            loader.loadImage(listbean1.get(1).getProduct_pic(), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    mhod.jiage_iv1.setImageBitmap(loadedImage);
                                }
                            });
                        }
                        }


                        break;
                    case 1:
                        final mtupian1 mhod1= (mtupian1) holder;
                        if(listbean1!=null) {


                            String ii1 = String.valueOf(listbean1.get(2).getProduct_name());
                            mhod1.jiage_tv.setText(ii1);
                            String jj = String.valueOf(listbean1.get(2).getProduct_nowprice());
                            String jj1 = String.valueOf(listbean1.get(2).getProduct_price());
                            String jj2 = String.valueOf(listbean1.get(3).getProduct_nowprice());
                            String jj3 = String.valueOf(listbean1.get(3).getProduct_price());
                            mhod1.jiage_tv1.setText(jj);
                            mhod1.jiage_tv2.setText(jj1);
                            mhod1.jiage_tv3.setText(listbean1.get(3).getProduct_name());
                            mhod1.jiage_tv4.setText(jj2);
                            mhod1.jiage_tv5.setText(jj3);
                            loader.loadImage(listbean1.get(2).getProduct_pic(), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    mhod1.jiage_iv.setImageBitmap(loadedImage);
                                }
                            });
                            loader.loadImage(listbean1.get(3).getProduct_pic(), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    mhod1.jiage_iv1.setImageBitmap(loadedImage);
                                }
                            });
                        }
                        break;
                    case 2:
                        if(listbean1!=null) {


                            final mtupian2 mhod2 = (mtupian2) holder;
                            String ii2 = String.valueOf(listbean1.get(4).getProduct_name());
                            mhod2.jiage_tv.setText(ii2);
                            mhod2.jiage_tv1.setText(String.valueOf(listbean1.get(4).getProduct_nowprice()));
                            mhod2.jiage_tv2.setText(String.valueOf(listbean1.get(4).getProduct_price()));
                        mhod2.jiage_tv3.setText(listbean1.get(5).getProduct_name());
                        mhod2.jiage_tv4.setText(String.valueOf(listbean1.get(5).getProduct_nowprice()) );
                        mhod2.jiage_tv5.setText(String.valueOf(listbean1.get(5).getProduct_price() ));
                            loader.loadImage(listbean1.get(4).getProduct_pic(), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    mhod2.jiage_iv.setImageBitmap(loadedImage);
                                }
                            });
                       loader.loadImage(listbean1.get(5).getProduct_pic(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mhod2.jiage_iv1.setImageBitmap(loadedImage);
                            }
                        });
                        }
                        break;
                    case 3:
                        final mtupian3  mhod3= (mtupian3) holder;
                        String ii3=String.valueOf(listbean1.get(6).getProduct_name());
                        mhod3.jiage_tv.setText(ii3);
                        mhod3.jiage_tv1.setText(String.valueOf(listbean1.get(6).getProduct_nowprice()));
                        mhod3.jiage_tv2.setText(String.valueOf(listbean1.get(6).getProduct_price()));
                        mhod3.jiage_tv3.setText(listbean1.get(7).getProduct_name());
                        mhod3.jiage_tv4.setText(String.valueOf(listbean1.get(7).getProduct_nowprice()) );
                        mhod3.jiage_tv5.setText(String.valueOf(listbean1.get(7).getProduct_price()));
                        loader.loadImage(listbean1.get(6).getProduct_pic(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mhod3.jiage_iv.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(listbean1.get(7).getProduct_pic(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mhod3.jiage_iv1.setImageBitmap(loadedImage);
                            }
                        });
                        break;
                    case 4:
                        final mtupian4  mhod4= (mtupian4) holder;
                        String ii4=String.valueOf(listbean1.get(8).getProduct_name());
                        mhod4.jiage_tv.setText(ii4);
                        mhod4.jiage_tv1.setText(String.valueOf(listbean1.get(8).getProduct_nowprice()));
                        mhod4.jiage_tv2.setText(String.valueOf(listbean1.get(8).getProduct_price()));
                        mhod4.jiage_tv3.setText(listbean1.get(9).getProduct_name());
                        mhod4.jiage_tv4.setText(String.valueOf(listbean1.get(9).getProduct_nowprice()) );
                        mhod4.jiage_tv5.setText(String.valueOf(listbean1.get(9).getProduct_price()));
                        loader.loadImage(listbean1.get(8).getProduct_pic(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mhod4.jiage_iv.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(listbean1.get(9).getProduct_pic(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mhod4.jiage_iv1.setImageBitmap(loadedImage);
                            }
                        });
                      break;

                }
    }

    @Override
    public int getItemCount() {
        int count2=listbean1==null?0:listbean1.size()-5

                ;
        return count2;
    }
    class  mtupian extends RecyclerView.ViewHolder{
     private TextView jiage_tv,jiage_tv1,jiage_tv2,jiage_tv3,jiage_tv4,jiage_tv5;
        private ImageView jiage_iv,jiage_iv1;
        public mtupian(View itemView) {
            super(itemView);
            jiage_tv= (TextView) itemView.findViewById(R.id.jiage_tv);
            jiage_tv1= (TextView) itemView.findViewById(R.id.jiage_tv1);
            jiage_tv2= (TextView) itemView.findViewById(R.id.jiage_tv2);
            jiage_tv3= (TextView) itemView.findViewById(R.id.jiage_tv3);
            jiage_tv4= (TextView) itemView.findViewById(R.id.jiage_tv4);
            jiage_tv5= (TextView) itemView.findViewById(R.id.jiage_tv5);
            jiage_iv= (ImageView) itemView.findViewById(R.id.jiage_iv);
            jiage_iv1= (ImageView) itemView.findViewById(R.id.jiage_iv1);

        }
    }
    class mtupian1 extends RecyclerView.ViewHolder{
        private TextView jiage_tv,jiage_tv1,jiage_tv2,jiage_tv3,jiage_tv4,jiage_tv5;
        private ImageView jiage_iv,jiage_iv1;
        public mtupian1(View itemView) {
            super(itemView);
            jiage_tv= (TextView) itemView.findViewById(R.id.jiage_tv);
            jiage_tv1= (TextView) itemView.findViewById(R.id.jiage_tv1);
            jiage_tv2= (TextView) itemView.findViewById(R.id.jiage_tv2);
            jiage_tv3= (TextView) itemView.findViewById(R.id.jiage_tv3);
            jiage_tv4= (TextView) itemView.findViewById(R.id.jiage_tv4);
            jiage_tv5= (TextView) itemView.findViewById(R.id.jiage_tv5);
            jiage_iv= (ImageView) itemView.findViewById(R.id.jiage_iv);
            jiage_iv1= (ImageView) itemView.findViewById(R.id.jiage_iv1);
        }

    }
    class mtupian2 extends RecyclerView.ViewHolder{
        private TextView jiage_tv,jiage_tv1,jiage_tv2,jiage_tv3,jiage_tv4,jiage_tv5;
        private ImageView jiage_iv,jiage_iv1;
        public mtupian2(View itemView) {
            super(itemView);
            jiage_tv= (TextView) itemView.findViewById(R.id.jiage_tv);
            jiage_tv1= (TextView) itemView.findViewById(R.id.jiage_tv1);
            jiage_tv2= (TextView) itemView.findViewById(R.id.jiage_tv2);
            jiage_tv3= (TextView) itemView.findViewById(R.id.jiage_tv3);
            jiage_tv4= (TextView) itemView.findViewById(R.id.jiage_tv4);
            jiage_tv5= (TextView) itemView.findViewById(R.id.jiage_tv5);
            jiage_iv= (ImageView) itemView.findViewById(R.id.jiage_iv);
            jiage_iv1= (ImageView) itemView.findViewById(R.id.jiage_iv1);
        }

    }
    class mtupian3 extends RecyclerView.ViewHolder{
        private TextView jiage_tv,jiage_tv1,jiage_tv2,jiage_tv3,jiage_tv4,jiage_tv5;
        private ImageView jiage_iv,jiage_iv1;
        public mtupian3(View itemView) {
            super(itemView);
            jiage_tv= (TextView) itemView.findViewById(R.id.jiage_tv);
            jiage_tv1= (TextView) itemView.findViewById(R.id.jiage_tv1);
            jiage_tv2= (TextView) itemView.findViewById(R.id.jiage_tv2);
            jiage_tv3= (TextView) itemView.findViewById(R.id.jiage_tv3);
            jiage_tv4= (TextView) itemView.findViewById(R.id.jiage_tv4);
            jiage_tv5= (TextView) itemView.findViewById(R.id.jiage_tv5);
            jiage_iv= (ImageView) itemView.findViewById(R.id.jiage_iv);
            jiage_iv1= (ImageView) itemView.findViewById(R.id.jiage_iv1);
        }

    }
    class mtupian4 extends RecyclerView.ViewHolder{
        private TextView jiage_tv,jiage_tv1,jiage_tv2,jiage_tv3,jiage_tv4,jiage_tv5;
        private ImageView jiage_iv,jiage_iv1;
        public mtupian4(View itemView) {
            super(itemView);
            jiage_tv= (TextView) itemView.findViewById(R.id.jiage_tv);
            jiage_tv1= (TextView) itemView.findViewById(R.id.jiage_tv1);
            jiage_tv2= (TextView) itemView.findViewById(R.id.jiage_tv2);
            jiage_tv3= (TextView) itemView.findViewById(R.id.jiage_tv3);
            jiage_tv4= (TextView) itemView.findViewById(R.id.jiage_tv4);
            jiage_tv5= (TextView) itemView.findViewById(R.id.jiage_tv5);
            jiage_iv= (ImageView) itemView.findViewById(R.id.jiage_iv);
            jiage_iv1= (ImageView) itemView.findViewById(R.id.jiage_iv1);
        }

    }
    class mtupian5 extends RecyclerView.ViewHolder{
        private TextView jiage_tv,jiage_tv1,jiage_tv2,jiage_tv3,jiage_tv4,jiage_tv5;
        private ImageView jiage_iv,jiage_iv1;
        public mtupian5(View itemView) {
            super(itemView);
            jiage_tv= (TextView) itemView.findViewById(R.id.jiage_tv);
            jiage_tv1= (TextView) itemView.findViewById(R.id.jiage_tv1);
            jiage_tv2= (TextView) itemView.findViewById(R.id.jiage_tv2);
            jiage_tv3= (TextView) itemView.findViewById(R.id.jiage_tv3);
            jiage_tv4= (TextView) itemView.findViewById(R.id.jiage_tv4);
            jiage_tv5= (TextView) itemView.findViewById(R.id.jiage_tv5);
            jiage_iv= (ImageView) itemView.findViewById(R.id.jiage_iv);
            jiage_iv1= (ImageView) itemView.findViewById(R.id.jiage_iv1);
        }

    }
}
