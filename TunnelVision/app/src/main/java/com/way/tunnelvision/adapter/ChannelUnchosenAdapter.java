package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.way.tunnelvision.R;
import com.way.tunnelvision.model.ChannelModel;

import java.util.List;

/**
 * Created by pc on 2016/2/27.
 */
public class ChannelUnchosenAdapter extends RecyclerView.Adapter<ChannelUnchosenAdapter.ViewHolderChannelUnchosen> {

    private List<ChannelModel> channelModels;
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_CHOSEN = 1;
    private static final int TYPE_UNCHOSEN = 2;

    private Context mContext;

    public ChannelUnchosenAdapter(Context mContext, List<ChannelModel> channelModels) {
        super();
        this.mContext = mContext;
        this.channelModels = channelModels;
    }

    public ChannelUnchosenAdapter(List<ChannelModel> channelModels) {
        super();
        this.channelModels = channelModels;
    }

    public List<ChannelModel> getContents() {
        return this.channelModels;
    }

    public void setContents(List<ChannelModel> channelModels) {
        this.channelModels = channelModels;
    }

    @Override
    public ViewHolderChannelUnchosen onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_chosen_item_card, parent, false);
        return new ViewHolderChannelUnchosen(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderChannelUnchosen viewHolder, int position) {
        //viewHolder.mTextView.setText(channelModels.get(position).getChannelTitle());
        ChannelModel channelItem = channelModels.get(position);
        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        viewHolder.tv_title.setText(channelItem.getChannelTitle());
        switch (getItemViewType(position)) {
            case TYPE_DEFAULT:
                int defaultcolor = mContext.getResources().getColor(R.color.colorPrimary);
                TextDrawable defaultDrawable = TextDrawable.builder()
                        .beginConfig()
                        .width(40)  // width in px
                        .height(40) // height in px
                        .endConfig()
                        .buildRound(channelItem.getChannelTitleInitial(), defaultcolor);
                viewHolder.iv_icon.setImageDrawable(defaultDrawable);
                viewHolder.iv_press.setBackgroundResource(R.drawable.ic_bookmark_primary);
                break;
            case TYPE_CHOSEN:
                int chosencolor = mContext.getResources().getColor(R.color.colorPrimary);
                TextDrawable chosenDrawable = TextDrawable.builder()
                        .beginConfig()
                        .width(40)  // width in px
                        .height(40) // height in px
                        .endConfig()
                        .buildRound(channelItem.getChannelTitleInitial(), chosencolor);
                viewHolder.iv_icon.setImageDrawable(chosenDrawable);
                viewHolder.iv_press.setBackgroundResource(R.drawable.ic_bookmark_primary);
                break;
            case TYPE_UNCHOSEN:
                int unchosenColor = mContext.getResources().getColor(R.color.gray);
                TextDrawable unchosenDrawable = TextDrawable.builder()
                        .beginConfig()
                        .width(40)  // width in px
                        .height(40) // height in px
                        .endConfig()
                        .buildRound(channelItem.getChannelTitleInitial(), unchosenColor);
                viewHolder.iv_icon.setImageDrawable(unchosenDrawable);
                viewHolder.iv_press.setBackgroundResource(R.drawable.ic_bookmark_plus_gray);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChannelModel channelItem = channelModels.get(position);
        switch (channelItem.getChannelChosen()) {
            case 0:
                return TYPE_DEFAULT;
            case 1:
                return TYPE_CHOSEN;
            default:
                return TYPE_UNCHOSEN;
        }
    }

    @Override
    public int getItemCount() {
        return channelModels.size();
    }

    public class ViewHolderChannelUnchosen extends RecyclerView.ViewHolder {
        public ImageView iv_icon;
        public TextView tv_title;
        public ImageView iv_press;

        public ViewHolderChannelUnchosen(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_channel_chosen_item_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_channel_chosen_item_title);
            iv_press = (ImageView) itemView.findViewById(R.id.iv_channel_chosen_item_press);
        }
    }
}