package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.Post;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private static final int TYPE_REFRESH_HEADER = -5;
    private static final int TYPE_HEADER = -4;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public ArrayList<Post> datas = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    private Context ctx;

    public PostAdapter(ArrayList<Post> datas, Context context) {
        this.datas = datas;
        this.ctx = context;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_post_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Post post = datas.get(position);
        viewHolder.position = position;
        viewHolder.mTextView.setText(post.getTitle());
        //viewHolder.mImageView.setImageResource(R.drawable.ic_android_black_18dp);
        ///*
        Picasso.with(ctx)
                .load(post.getIconResourceId())
                .resize(200, 200)
                .centerCrop()
                .placeholder(R.drawable.ic_android_black_18dp)
                .error(R.drawable.ic_android_black_18dp)
                .into(viewHolder.mImageView);
        //*/
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {
        public View rootView;
        public ImageView mImageView;
        public TextView mTextView;
        public int position;

        public ViewHolder(View view) {
            super(view);
            rootView = view.findViewById(R.id.ll_post_item_container);
            mImageView = (ImageView) view.findViewById(R.id.iv_post_item_icon);
            mTextView = (TextView) view.findViewById(R.id.tv_post_item_title);
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

    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }
}
