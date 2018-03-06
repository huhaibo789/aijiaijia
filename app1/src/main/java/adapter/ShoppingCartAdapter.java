package adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.DesignshopActivity;
import com.example.administrator.myyushi.R;

import java.util.HashMap;
import java.util.List;

import bean.ShoppingCartBean;

/**
 * Created by misshu on 2017/6/28/028.
 */
public class ShoppingCartAdapter extends BaseAdapter {
    DesignshopActivity activity;
    //定义hashMap 用来存放之前创建的每一项item
    HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    private boolean isShow = true;//是否显示编辑/完成
    private List<ShoppingCartBean> shoppingCartBeanList;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;


    public ShoppingCartAdapter( DesignshopActivity activity) {
        this.activity = activity;
    }

    public void setShoppingCartBeanList(List<ShoppingCartBean> shoppingCartBeanList) {
        this.shoppingCartBeanList = shoppingCartBeanList;
        notifyDataSetChanged();
    }

    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getCount() {
        return shoppingCartBeanList == null ? 0 : shoppingCartBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCartBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 是否显示可编辑
     *
     * @param flag
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }
    ViewHolder holder = null;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (lmap.get(position) == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(activity).inflate(
                    R.layout.cart_list_item, null);
            holder.checkBox = (CheckBox) view.findViewById(R.id.check_box);
            holder.image = (ImageView) view
                    .findViewById(R.id.iv_adapter_list_pic);
            holder.shopName = (TextView) view.findViewById(R.id.gouwu_tv);
            holder.xx = (TextView) view.findViewById(R.id.xx);
            holder.content = (TextView) view.findViewById(R.id.tv_intro);
            holder.carNum = (TextView) view.findViewById(R.id.tv_num);
            holder.nowprice = (TextView) view.findViewById(R.id.tv_price);
            holder.previousprice = (TextView) view.findViewById(R.id.tv_price1);
            holder.sss = (TextView) view.findViewById(R.id.sss);
            holder.standard = (TextView) view.findViewById(R.id.standard);
            holder.sss1 = (TextView) view.findViewById(R.id.sss1);
            holder.servicetip = (TextView) view.findViewById(R.id.servicetip);
            view.setTag(holder);
        } else {
            view = lmap.get(position);
            holder = (ViewHolder) view.getTag();
        }
        final ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        holder.shopName.setText(shoppingCartBean.getShoppingName());  //商品名称
        holder.content.setText(shoppingCartBean.getShopnumber());  //商品编号
        holder.standard.setText(shoppingCartBean.getSkuname());  //商品sku名字
        holder.nowprice.setText("￥:" + shoppingCartBean.getPrice()); //商品价格
        holder.checkBox.setChecked(shoppingCartBean.isChoosed()); //商品是否选中
        holder.carNum.setText(shoppingCartBean.getCount()); //商品数量

        //单选框按钮
        holder.checkBox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shoppingCartBean.setChoosed(((CheckBox) v).isChecked());
                        checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                    }
                }
        );

      /*  //增加按钮
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(position, holder.tv_show_num, holder.ck_chose.isChecked());//暴露增加接口
            }
        });

        //删减按钮
        holder.iv_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(position, holder.tv_show_num, holder.ck_chose.isChecked());//暴露删减接口
            }
        });*/

        //删除弹窗
        activity.allclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = new AlertDialog.Builder(activity).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                modifyCountInterface.childDelete(position);//删除 目前只是从item中移除

                            }
                        });
                alert.show();
            }
        });
     /*   holder.actv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        return convertView;
    }


    //初始化控件
    class ViewHolder {
        CheckBox checkBox;
        TextView sss;
        TextView sss1;
        ImageView image;
        TextView shopName;
        TextView content;
        TextView carNum;
        TextView nowprice;
        TextView xx;
        TextView previousprice;
        TextView standard;
        TextView servicetip;



    }


    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);
    }
}
