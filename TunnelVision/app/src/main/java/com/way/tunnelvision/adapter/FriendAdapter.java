package com.way.tunnelvision.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.section.SectionedAdapter;
import com.way.tunnelvision.entity.Friend;

import java.util.List;

/**
 * Created by pc on 2015/12/13.
 */
public class FriendAdapter extends SectionedAdapter<Friend> {


    private OnRecyclerViewListener onRecyclerViewListener;
    public FriendAdapter(List<Friend> friends) {
        this.setItemList(friends);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, Friend item, @ViewType int viewType) {
        FriendViewHolder friendViewHolder = (FriendViewHolder)holder;
        friendViewHolder.userid = item.getUserId();
        friendViewHolder.tv_displayname.setText(item.getDisplayName());
        friendViewHolder.iv_icon.setBackgroundResource(R.drawable.ic_android_black_18dp);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, @ViewType int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_friend_item, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        String userid;
        ImageView iv_icon;
        TextView tv_displayname;

        public FriendViewHolder(View itemView) {
            super(itemView);
            this.iv_icon = (ImageView) itemView.findViewById(R.id.iv_friend_item_icon);
            this.tv_displayname = (TextView) itemView.findViewById(R.id.tv_friend_item_displayname);
        }

        @Override
        public void onClick(View v) {
            if(null !=onRecyclerViewListener){
                onRecyclerViewListener.OnItemClick(userid);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(null !=onRecyclerViewListener){
                onRecyclerViewListener.OnItemLongClick(userid);
            }
            return false;
        }
    }

    public interface OnRecyclerViewListener{
        void OnItemClick(String userid);
        boolean OnItemLongClick(String userid);
    }
}
