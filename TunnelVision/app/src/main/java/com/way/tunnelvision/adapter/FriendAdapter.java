package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.entity.Friend;
import com.way.tunnelvision.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/13.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private static final String TAG = FriendAdapter.class.getName();

    private static final int TYPE_REFRESH_HEADER = -5;
    private static final int TYPE_HEADER = -4;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public ArrayList<Friend> datas = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    private Context ctx;

    public FriendAdapter(ArrayList<Friend> datas, Context context) {
        this.datas = datas;
        this.ctx = context;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_friend_item, viewGroup, false);
        FriendViewHolder vh = new FriendViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final FriendViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder debug, start");
        try {
            Friend friend = datas.get(position);
            viewHolder.position = position;
            //viewHolder.mImageView_avatar.setTag(position);
            //viewHolder.mTextView_displayname.setTag(position);
            //viewHolder.mImageView_gender.setTag(position);

            viewHolder.mTextView_displayname.setText(friend.getDisplayName());
            if(0 == friend.getGender()) {
                viewHolder.mImageView_gender.setBackgroundResource(R.drawable.ic_gender_female_primary);
            } else if(1 == friend.getGender()) {
                viewHolder.mImageView_gender.setBackgroundResource(R.drawable.ic_gender_male_accent);
            } else {
                viewHolder.mImageView_gender.setBackgroundResource(R.drawable.ic_gender_female_primary);
            }

            Glide.with(ctx)
                    .load(friend.getAvatarResourceId())
                    .centerCrop()
                    //.animate(ViewPropertyAnimation.Animator)
                    .dontAnimate()
                    .placeholder(R.drawable.ic_android_black_18dp)
                    .error(R.drawable.ic_android_black_18dp)
                    .into(new ImageViewTarget<GlideDrawable>(viewHolder.mImageView_avatar) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            viewHolder.mImageView_avatar.setImageDrawable(resource);
                        }

                        @Override
                        public void setRequest(Request request) {
                            viewHolder.mImageView_avatar.setTag(position);
                            viewHolder.mImageView_avatar.setTag(R.id.glide_tag_id, request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) viewHolder.mImageView_avatar.getTag(R.id.glide_tag_id);
                        }
                    });

            viewHolder.mImageView_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, datas.get(position).getAvatarResourceId());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder error", e);
        }
        Log.d(TAG, "onBindViewHolder debug, end");
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public View rootView;
        public CircularImageView mImageView_avatar;
        public TextView mTextView_displayname;
        public ImageView mImageView_gender;
        public int position;

        public FriendViewHolder(View view) {
            super(view);
            rootView = view.findViewById(R.id.cv_friend_item_container);
            mImageView_avatar = (CircularImageView) view.findViewById(R.id.civ_friend_item_avatar);
            mTextView_displayname = (TextView) view.findViewById(R.id.tv_friend_item_displayname);
            mImageView_gender = (ImageView) view.findViewById(R.id.iv_friend_item_gender);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }

//    interface OnRecyclerViewListener {
//        void onItemClick(int position);
//
//        boolean onItemLongClick(int position);
//    }
}
