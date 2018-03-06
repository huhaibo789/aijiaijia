package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.fenleibean;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 胡海波 on 2016/12/12.
 */
public class newfenleiadapter extends BaseAdapter {
     boolean flag=true;
    int i=0;
    private RequestQueue queue;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<String> databean = new ArrayList<>();
    private ArrayList<String> shuzi=new ArrayList<>();
    private ArrayList<String> wed=new ArrayList<>();
    private ArrayList<Integer> databean1 = new ArrayList<>();
    private ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist4;
    private ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean> biglist5;
    private ArrayList<fenleibean.ResponseJsonBean.ListBigBean.SubBean> biglist6 = new ArrayList<>();
    ArrayList<String> tuijian = new ArrayList<>();
    ArrayList<String> tupian = new ArrayList<>();
    private ArrayList<ImageView> ivs = new ArrayList<>();
    private Context context;
    private ArrayList<String> xiaobiaoti=new ArrayList<>();
    private View v_city;
    private View v_city1;
    private View v_city2;

    public newfenleiadapter(Context context, ImageLoader loader) {
        super();
        this.context = context;
        this.loader = loader;
    }

    public  void upshujud(ArrayList<String> databean){
        this.databean=databean;
        Log.i("dfgff", "upshujud: "+databean.size());

    }

    public  void upwensa(ArrayList<String> wed){
        this.wed=wed;
        Log.i("dfgffsd", "upshujud: "+wed.size());

    }
    public  void   upshujud1(ArrayList<String> shuzi){
       this.shuzi=shuzi;
        Log.i("dfgffdsw", "upshujud: "+shuzi.size());

    }
    @Override
    public int getCount() {
        return databean.size();
    }

    @Override
    public Object getItem(int position) {
        return databean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_newfenlei, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.newfen_ly = (LinearLayout) convertView.findViewById(R.id.newfen_ly);
            vh.sanjipic_iv= (ImageView) convertView.findViewById(R.id.sanjipic_iv);
            vh.shezhi= (TextView) convertView.findViewById(R.id.shezhi);
           /*  vh.toubu_tv= (TextView) convertView.findViewById(R.id.toubu_tv);
            vh.matong= (ImageView) convertView.findViewById(R.id.matong);
            vh.matong1= (ImageView) convertView.findViewById(R.id.matong1);
            vh.matong_tv= (TextView) convertView.findViewById(R.id.matong_tv);
            vh.matong1_tv= (TextView) convertView.findViewById(R.id.matong1_tv);*/
        } else {
            vh = (ViewHold) convertView.getTag();
        }
  /*     vh.toubu_tv.setText(shuzi.get(position));*/
        final ViewHold finalVh8 = vh;
        loader.loadImage(databean.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh8.sanjipic_iv.setImageBitmap(loadedImage);
            }
        });
        vh.shezhi.setText(wed.get(position));
          /*  queue = Volley.newRequestQueue(context);
            String url = "http://api.aijiaijia.com/api_categories";
            final ViewHold finalVh2 = vh;
            final ViewHold finalVh3 = vh;
            final ViewHold finalVh1 = vh;
            final ViewHold finalVh = vh;
            final ViewHold finalVh4 = vh;
            final ViewHold finalVh5 = vh;
        final ViewHold finalVh6 = vh;
        final ViewHold finalVh7 = vh;
        StringRequest post = new StringRequest(
                    StringRequest.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str = response.toString().trim();
                            Log.i("djskuidn", "onResponse: " + str);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(str);
                                JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                JSONArray jsonary = resposeobject.getJSONArray("list_big");

                                    JSONObject jsonobject = jsonary.getJSONObject(i);

                                    Log.i("wwe", "onResponse: "+i);
                                    String wenben = jsonobject.getString("categorieName");
                                    tuijian.add(wenben);
                                    v_city = LayoutInflater.from(context).inflate(R.layout.activity_wenben, null);
                                    TextView text = (TextView) v_city.findViewById(R.id.newfenlei_tv);
                                    text.setText(wenben);

                                    //finalVh2.newfen_ly.addView(v_city);
                                    JSONArray jsonarry1 = jsonobject.getJSONArray("sub");
                                    Log.i("hyusesd", "onResponse: " + jsonarry1.toString());
                                    for (int j = 0; j < jsonarry1.length(); j++) {
                                    v_city1 = LayoutInflater.from(context).inflate(R.layout.activity_tupian, null);
                                    final ImageView tuiv = (ImageView) v_city1.findViewById(R.id.newfenlei_iv);
                                    TextView texttv= (TextView) v_city1.findViewById(R.id.newfenlei_tv1);
                                        texttv.setId(0);
                                        JSONObject jsonob = jsonarry1.getJSONObject(j);
                                        String picture = jsonob.getString("categoriePic");
                                        loader.displayImage(picture,tuiv);
                                        if(i!=0){

                                            String title = jsonob.getString("categorieName");
                                            texttv.setText(title);
                                            loader.loadImage(picture,new SimpleImageLoadingListener(){
                                                @Override
                                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                                    tuiv.setImageBitmap(loadedImage);
                                                }
                                            });
                                            finalVh7.newfen_ly.addView(v_city1);

                                        }



                                    }
                                i++;
                                    Log.i("shusijs", "onResponse: " + tupian.toString());




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("dsda", "onErrorResponse: " + error.getMessage());
                        }
                    }
            ) {


            };
            queue.add(post);*/




        return convertView;
    }

    private void postdata() {

    }

    public class ViewHold {
     LinearLayout newfen_ly;
      /* ImageView sanjipic_iv;
        TextView shezhi;*/
      /*  TextView toubu_tv;
        TextView matong_tv;
        TextView matong1_tv;
        ImageView matong;
        ImageView matong1;*/
      ImageView sanjipic_iv;
        TextView shezhi;
    }


}
