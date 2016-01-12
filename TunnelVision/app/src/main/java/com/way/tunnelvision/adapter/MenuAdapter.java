package com.way.tunnelvision.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.way.tunnelvision.R;
import com.way.tunnelvision.model.MenuModel;

import java.util.List;

/**
 * Created by pc on 2016/1/6.
 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
 */
public class MenuAdapter extends BaseAdapter {
    private final static String TAG = MenuAdapter.class.getName();

    private List<MenuModel> mMenuList;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/
    private Context mContext;

    public MenuAdapter(Context context, List<MenuModel> menuList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMenuList = menuList;
    }

    public List<MenuModel> getmMenuList() {
        return mMenuList;
    }

    public void setmMenuList(List<MenuModel> mMenuList) {
        this.mMenuList = mMenuList;
    }

    @Override
    public int getCount() {
        return mMenuList.size();//返回数组的长度
    }

    @Override
    public MenuModel getItem(int position) {
        return mMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*书中详细解释该方法*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderMenu holder;
        MenuModel menuM = mMenuList.get(position);
        //观察convertView随ListView滚动情况
        //Log.d(TAG, "getView debug, position = " + position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.menu_item_card, null);
            holder = new ViewHolderMenu();
                    /*得到各个控件的对象*/
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_menu_item_icon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_menu_item_title);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolderMenu) convertView.getTag();//取出ViewHolder对象
        }
        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/

        int getcolor = mContext.getResources().getColor(R.color.colorAccent);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(40)  // width in px
                .height(40) // height in px
                .endConfig()
                .buildRound(menuM.getMenuInitial(), getcolor);
        holder.iv_icon.setImageDrawable(drawable);
        holder.tv_title.setText(menuM.getMenuTitle());
        return convertView;
    }

    /*存放控件*/
    class ViewHolderMenu {
        public ImageView iv_icon;
        public TextView tv_title;
    }
}
