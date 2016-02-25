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
import com.way.tunnelvision.model.ChannelModel;

import java.util.List;

/**
 * Created by pc on 2016/1/6.
 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
 */
public class ChannelChosenAdapter extends BaseAdapter {
    private final static String TAG = ChannelChosenAdapter.class.getName();

    private List<ChannelModel> channelModels;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/
    private Context mContext;

    public ChannelChosenAdapter(Context context, List<ChannelModel> channelModels) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.channelModels = channelModels;
    }

    public List<ChannelModel> getChannelModels() {
        return this.channelModels;
    }

    public void setChannelModels(List<ChannelModel> channelModels) {
        this.channelModels = channelModels;
    }

    @Override
    public int getCount() {
        return channelModels.size();//返回数组的长度
    }

    @Override
    public ChannelModel getItem(int position) {
        return channelModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*书中详细解释该方法*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderChannel holder;
        ChannelModel channelModel = channelModels.get(position);
        //观察convertView随ListView滚动情况
        //Log.d(TAG, "getView debug, position = " + position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.channel_chosen_item_card, null);
            holder = new ViewHolderChannel();
                    /*得到各个控件的对象*/
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_channel_chosen_item_icon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_channel_chosen_item_title);
            holder.iv_press = (ImageView) convertView.findViewById(R.id.iv_channel_chosen_item_press);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolderChannel) convertView.getTag();//取出ViewHolder对象
        }
        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/

        int getcolor = mContext.getResources().getColor(R.color.colorPrimary);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(40)  // width in px
                .height(40) // height in px
                .endConfig()
                .buildRound(channelModel.getChannelTitleInitial(), getcolor);
        holder.iv_icon.setImageDrawable(drawable);
        holder.tv_title.setText(channelModel.getChannelTitle());
        int chosen = channelModel.getChannelChosen();
        if (0 == chosen || 1 == chosen) {
            holder.iv_press.setBackgroundResource(R.drawable.ic_bookmark_primary);
        } else {
            holder.iv_press.setBackgroundResource(R.drawable.ic_bookmark_plus_gray);
        }
        return convertView;
    }

    /*存放控件*/
    class ViewHolderChannel {
        public ImageView iv_icon;
        public TextView tv_title;
        public ImageView iv_press;
    }
}
