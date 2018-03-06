package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import bean.fenleibean;

/**
 * Created by 胡海波 on 2016/8/30.
 */
public class fenleiadapter extends BaseAdapter{


    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<String> databean = new ArrayList<>();
    private ArrayList<Integer> databean1=new ArrayList<>();
    private ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist4;
    private ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean> biglist5;
    private ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean> biglist6=new ArrayList<>();

    private ArrayList<ImageView> ivs = new ArrayList<>();
    private Context context;

    public fenleiadapter(Context context, ImageLoader loader) {
        super();
        this.context = context;
        this.loader = loader;
    }

    public void updateData1(ArrayList<String> databean) {
        this.databean = databean;

    }

    public void updateData2(ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist4) {
        this.biglist4 = biglist4;

    }

    public void updateData3(ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean> biglist5) {
        this.biglist5 = biglist5;

    }

    @Override
    public int getCount() {
        return databean.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_fenadapter, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.liner= (LinearLayout) convertView.findViewById(R.id.liner);
            vh.da_tv = (TextView) convertView.findViewById(R.id.da_tv);
           /* vh.da_tv1 = (TextView) convertView.findViewById(R.id.da_tv1);
            vh.da_tv2 = (TextView) convertView.findViewById(R.id.da_tv2);
            vh.da_tv3 = (TextView) convertView.findViewById(R.id.da_tv3);
            vh.da_tv4 = (TextView) convertView.findViewById(R.id.da_tv4);
            vh.da_tv5 = (TextView) convertView.findViewById(R.id.da_tv5);
            vh.da_tv6 = (TextView) convertView.findViewById(R.id.da_tv6);
            vh.da_iv = (ImageView) convertView.findViewById(R.id.da_iv);
            vh.da_iv1 = (ImageView) convertView.findViewById(R.id.da_iv1);
            vh.da_iv2 = (ImageView) convertView.findViewById(R.id.da_iv2);
            vh.da_iv3 = (ImageView) convertView.findViewById(R.id.da_iv3);
            vh.da_iv4 = (ImageView) convertView.findViewById(R.id.da_iv4);
            vh.da_iv5 = (ImageView) convertView.findViewById(R.id.da_iv5);*/
            /*vh.ivb= (ImageView) convertView.findViewById(R.id.ivb);*/
           /* vh.da_tv1= (TextView) convertView.findViewById(R.id.da_tv1);
            vh.da_tv2= (TextView) convertView.findViewById(R.id.da_tv2);
            vh.da_iv= (ImageView) convertView.findViewById(R.id.da_iv);
            vh.da_iv1= (ImageView) convertView.findViewById(R.id.da_iv1);*/

        } else {
            vh = (ViewHold) convertView.getTag();
        }
        for (int i = 0; i <biglist4.size() ; i++) {
                 biglist6.addAll(biglist4.get(0).getSub()) ;
            Log.i("chishi1", "getView: "+biglist6);

        }
        vh.da_tv.setText(databean.get(position));
        final ViewHold finalVh10 = vh;
        Log.i("chishi", "getView: "+position);
       /* loader.loadImage(biglist5.get(position).getCategoriePic(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh10.da_iv.setImageBitmap(loadedImage);

            }
        });*/
        final ViewHold finalVh = vh;
        final ViewHold finalVh1 = vh;
        for (int i = 0; i <biglist6.size() ; i++) {
            loader.loadImage(biglist6.get(i).getCategoriePic(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);

                    ImageView ivs=new ImageView(context);
                    ivs.setImageBitmap(loadedImage);
                    finalVh1.liner.addView(ivs);


                }
            });
        }

               /*   loader.loadImage(biglist5.get(1).getCategoriePic(), new SimpleImageLoadingListener() {
                      @Override
                      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                          super.onLoadingComplete(imageUri, view, loadedImage);

                          ImageView ivs=new ImageView(context);
                          ivs.setImageBitmap(loadedImage);
                          finalVh1.liner.addView(ivs);


                      }
                  });*/






       /* for (int i = 0; i <biglist4.size() ; i++) {

           if( biglist4.get(i).getSub().size()==1){
               final ViewHold finalVh = vh;
               final ViewHold finalVh1 = vh;
               final ViewHold finalVh2 = vh;
               final ViewHold finalVh3 = vh;
               final ViewHold finalVh4 = vh;
               final ViewHold finalVh5 = vh;
               loader.loadImage( biglist4.get(i).getSub().get(0).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh.da_iv.setImageBitmap(loadedImage);

                   }

               });
               finalVh1.da_iv1.setVisibility(View.GONE);
               finalVh2.da_iv2.setVisibility(View.GONE);
               finalVh3.da_iv3.setVisibility(View.GONE);
               finalVh4.da_iv4.setVisibility(View.GONE);
               finalVh5.da_iv5.setVisibility(View.GONE);
           }else if( biglist4.get(i).getSub().size()==2) {
               final ViewHold finalVh = vh;
               final ViewHold finalVh1 = vh;
               final ViewHold finalVh2 = vh;
               final ViewHold finalVh3 = vh;
               final ViewHold finalVh4 = vh;
               final ViewHold finalVh5 = vh;
               loader.loadImage( biglist4.get(i).getSub().get(0).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh.da_iv.setImageBitmap(loadedImage);

                   }
               });

               loader.loadImage(biglist4.get(i).getSub().get(1).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh1.da_iv1.setImageBitmap(loadedImage);

                   }
               });
               finalVh2.da_iv2.setVisibility(View.GONE);
               finalVh3.da_iv3.setVisibility(View.GONE);
               finalVh4.da_iv4.setVisibility(View.GONE);
               finalVh5.da_iv5.setVisibility(View.GONE);
           }else if(biglist4.get(i).getSub().size()==3){
               final ViewHold finalVh = vh;
               final ViewHold finalVh1 = vh;
               final ViewHold finalVh2 = vh;
               final ViewHold finalVh3 = vh;
               final ViewHold finalVh4 = vh;
               final ViewHold finalVh5 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(0).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh.da_iv.setImageBitmap(loadedImage);

                   }
               });

               loader.loadImage(biglist4.get(i).getSub().get(1).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh1.da_iv1.setImageBitmap(loadedImage);

                   }
               });
               loader.loadImage(biglist4.get(i).getSub().get(2).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh2.da_iv2.setImageBitmap(loadedImage);

                   }
               });
               finalVh3.da_iv3.setVisibility(View.GONE);
               finalVh4.da_iv4.setVisibility(View.GONE);
               finalVh5.da_iv5.setVisibility(View.GONE);
           }else if(biglist4.get(i).getSub().size()==4){
               final ViewHold finalVh = vh;
               final ViewHold finalVh1 = vh;
               final ViewHold finalVh2 = vh;
               final ViewHold finalVh3 = vh;
               final ViewHold finalVh4 = vh;
               final ViewHold finalVh5 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(0).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh.da_iv.setImageBitmap(loadedImage);

                   }
               });

               loader.loadImage(biglist4.get(i).getSub().get(1).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh1.da_iv1.setImageBitmap(loadedImage);

                   }
               });
               loader.loadImage(biglist4.get(i).getSub().get(2).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh2.da_iv2.setImageBitmap(loadedImage);

                   }
               });
               final ViewHold finalVh6 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(3).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh6.da_iv3.setImageBitmap(loadedImage);

                   }
               });

               finalVh4.da_iv4.setVisibility(View.GONE);
               finalVh5.da_iv5.setVisibility(View.GONE);
           }else if(biglist4.get(i).getSub().size()==5){
               final ViewHold finalVh = vh;
               final ViewHold finalVh1 = vh;
               final ViewHold finalVh2 = vh;
               final ViewHold finalVh3 = vh;
               final ViewHold finalVh4 = vh;
               final ViewHold finalVh5 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(0).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh.da_iv.setImageBitmap(loadedImage);

                   }
               });

               loader.loadImage(biglist4.get(i).getSub().get(1).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh1.da_iv1.setImageBitmap(loadedImage);

                   }
               });
               loader.loadImage(biglist4.get(i).getSub().get(2).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh2.da_iv2.setImageBitmap(loadedImage);

                   }
               });
               final ViewHold finalVh7 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(3).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh7.da_iv3.setImageBitmap(loadedImage);

                   }
               });
               final ViewHold finalVh8 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(4).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh8.da_iv4.setImageBitmap(loadedImage);

                   }
               });

               finalVh5.da_iv5.setVisibility(View.GONE);
           }else if(biglist4.get(i).getSub().size()==6){
               final ViewHold finalVh = vh;
               final ViewHold finalVh1 = vh;
               final ViewHold finalVh2 = vh;
               final ViewHold finalVh3 = vh;
               final ViewHold finalVh4 = vh;
               final ViewHold finalVh5 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(0).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalVh.da_iv.setImageBitmap(loadedImage);

                   }
               });

               loader.loadImage(biglist4.get(i).getSub().get(1).getCategoriePic(), new SimpleImageLoadingListener() {
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh1.da_iv1.setImageBitmap(loadedImage);

                   }
               });
               loader.loadImage(biglist4.get(i).getSub().get(2).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh2.da_iv2.setImageBitmap(loadedImage);

                   }
               });
               final ViewHold finalVh7 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(3).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh7.da_iv3.setImageBitmap(loadedImage);

                   }
               });
               final ViewHold finalVh8 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(4).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh8.da_iv4.setImageBitmap(loadedImage);

                   }
               });
               final ViewHold finalVh9 = vh;
               loader.loadImage(biglist4.get(i).getSub().get(5).getCategoriePic(),new SimpleImageLoadingListener(){
                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);

                       finalVh9.da_iv5.setImageBitmap(loadedImage);

                   }
               });

           }


    }*/

       /* for (int i = 0; i <biglist4.size() ; i++) {
            for (int j = 0; j <biglist4.get(i).getSub() ; j++) {
                ImageView iv=new ImageView(context);
                iv.setImageResource(R.drawable.ic_launcher);
                vh.liner.addView(iv);
            }
        }*/
      /*  vh.da_tv1.setText(biglist4.get(position).getCategorieName());
        vh.da_tv2.setText(biglist4.get(position).getCategorieName());
*/
       /* Log.i("ykz", "getView: "+biglist4.size());
        if(biglist4!=null) {
            for (int i = 0; i < biglist4.size(); i++) {
                loader.displayImage(biglist4.get(i).getCategoriePic(), vh.ivb);
                vh.liner.addView(vh.ivb);
            }
        }*/

     /*   final ViewHold finalVh1 = vh;
        final ViewHold finalVh = vh;
        final ViewHold finalVh2 = vh;*/
     /*   loader.loadImage(biglist4.get(position).getCategoriePic(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);

                finalVh2.liner.addView(loadedImage);


            }
        });
        loader.loadImage(biglist4.get(position+1).getCategoriePic(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh1.da_iv1.setImageBitmap(loadedImage);
            }
        });*/
        /*vh.da_tv1.setText(databean.get(0).getSub().get(0).getCategorieName());
      /*  vh.biao_tv2.setText(data.get(position).getMendian_address());*//*
        final ViewHold finalVh = vh;
        for (int i = 0; i <databean.size() ; i++) {
            loader.loadImage(databean.get(i).getSub().get(position).getCategoriePic(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh.da_iv.setImageBitmap(loadedImage);
                }
            });
        }
*/

        return convertView;
    }
    public class ViewHold {
        LinearLayout liner;
      /*  ImageView ivb;*/
        TextView da_tv,da_tv1,da_tv2,da_tv3,da_tv4,da_tv5,da_tv6;
       ImageView da_iv,da_iv1,da_iv2,da_iv3,da_iv4,da_iv5;
        /*ImageView da_iv1;*/
     /*   TextView da_tv;
        TextView da_tv1;
        TextView da_tv2;*/
    }

}
