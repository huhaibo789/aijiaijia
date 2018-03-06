package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myyushi.R;

import java.util.ArrayList;
import java.util.List;

import bean.newsanji1;

/**
 * Created by 胡海波 on 2016/9/22.
 */
public class MyAdapterRight extends BaseAdapter {
    private List<newsanji1> l;
    private Context context;
    private int selectItem = -1;

    public MyAdapterRight(Context context, List<newsanji1> l) {
        this.context = context;
        setData(l);
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public newsanji1 getItem(int position) {
        return l.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_newsanjiitem2,
                    null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_paixu);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (l.size()>0) {
            holder.name.setText(l.get(position).getName());

          /*  if (position == selectItem) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.click));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.defult));
            }*/
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    class Holder {
        TextView name;
    }
    private void show(String str) {
        Toast.makeText(context, str, 0).show();
    }
}
