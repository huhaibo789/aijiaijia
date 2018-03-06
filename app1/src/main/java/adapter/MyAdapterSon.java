package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myyushi.R;

import java.util.ArrayList;
import java.util.List;

import bean.newsanji1;

/**
 * Created by 胡海波 on 2016/9/22.
 */
public class MyAdapterSon extends BaseAdapter {
    private List<newsanji1> l;
    private Context context;
    private int selectItem = -1;
    private ArrayList<String> shuju=new ArrayList<>();
    public MyAdapterSon(Context context, ArrayList<String> shuju) {
        this.context = context;
        this.shuju=shuju;
       // setData(l);
    }

    @Override
    public int getCount() {
        return shuju.size();
    }

    @Override
    public Object getItem(int position) {
        return shuju.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    // 更新adapter
    public void updataAdapter( List<newsanji1> l) {
        setData(l);
        this.notifyDataSetChanged();
    }

    public void setData( List<newsanji1> l) {
        if (l != null)
            this.l = l;
        else
            this.l = new ArrayList<newsanji1>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_newsanjiitem1,
                    null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (shuju.size()>0) {
            holder.name.setText(shuju.get(position));

          /*  if (position == selectItem) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.click));

            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.defult));
            }*/
        }
        return convertView;
    }

  /*  public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }*/

    class Holder {
        TextView name;
    }
    private void show(String str) {

    }
}
