package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.entity.LatestMessage;
import com.way.tunnelvision.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class LatestMessageAdapter extends RecyclerView.Adapter<LatestMessageAdapter.ViewHolder> {
    private static final String TAG = LatestMessageAdapter.class.getName();

    private static final int TYPE_REFRESH_HEADER = -5;
    private static final int TYPE_HEADER = -4;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public ArrayList<LatestMessage> datas = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    private Context ctx;

    public LatestMessageAdapter(ArrayList<LatestMessage> datas, Context context) {
        this.datas = datas;
        this.ctx = context;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder debug, start");
        try {
            LatestMessage latestMessage = datas.get(position);
            viewHolder.position = position;
            viewHolder.mTextView_displayname.setTag(position);
            viewHolder.mTextView_latestcontent.setTag(position);
            viewHolder.mTextView_latestdatetime.setTag(position);
            viewHolder.mTextView_displayname.setText(latestMessage.getDisplayName());
            viewHolder.mTextView_latestcontent.setText(latestMessage.getLatestContent());
            viewHolder.mTextView_latestdatetime.setText(latestMessage.getLatestDateTime());

            Glide.with(ctx)
                    .load(latestMessage.getIconResourceId())
                    .centerCrop()
                    //.animate(ViewPropertyAnimation.Animator)
                    .dontAnimate()
                    .placeholder(R.drawable.ic_android_black_18dp)
                    .error(R.drawable.ic_android_black_18dp)
                    .into(new ImageViewTarget<GlideDrawable>(viewHolder.mImageView_icon) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            viewHolder.mImageView_icon.setImageDrawable(resource);
                        }

                        @Override
                        public void setRequest(Request request) {
                            viewHolder.mImageView_icon.setTag(position);
                            viewHolder.mImageView_icon.setTag(R.id.glide_tag_id, request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) viewHolder.mImageView_icon.getTag(R.id.glide_tag_id);
                        }
                    });

            viewHolder.mImageView_icon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, datas.get(position).getUserName());
                }
            });
            viewHolder.mTextView_displayname.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, "mTextView_displayname" + position);
                }
            });
            viewHolder.mTextView_latestcontent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, "mTextView_latestcontent" + position);
                }
            });
            viewHolder.mTextView_latestdatetime.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, "mTextView_latestdatetime" + position);
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
    class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {
        public View rootView;
        public ImageView mImageView_icon;
        public TextView mTextView_displayname;
        public TextView mTextView_latestcontent;
        public TextView mTextView_latestdatetime;
        public int position;

        public ViewHolder(View view) {
            super(view);
            rootView = view.findViewById(R.id.ll_message_item_container);
            mImageView_icon = (ImageView) view.findViewById(R.id.iv_message_item_icon);
            mTextView_displayname = (TextView) view.findViewById(R.id.tv_message_item_displayname);
            mTextView_latestcontent = (TextView) view.findViewById(R.id.tv_message_item_latestcontent);
            mTextView_latestdatetime = (TextView) view.findViewById(R.id.tv_message_item_latestdatetime);
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
