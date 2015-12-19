package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.entity.Post;
import com.way.tunnelvision.util.TimeUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/17.
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private final static  String TAG = DiaryAdapter.class.getName();

    public ArrayList<Post> datas = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    private Context ctx;

    public DiaryAdapter(ArrayList<Post> list, Context context){
        this.datas = list;
        this.ctx = context;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener1){
        this.onRecyclerViewListener = onRecyclerViewListener1;
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_diary_timeline_item, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DiaryViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder debug, start");
        try{
            Post post = datas.get(position);
            holder.position = position;
            holder.textView_content.setText(post.getContent());
            String createdatestr = post.getCreatedDate();
            int dayofweek = TimeUtil.dayForWeek(createdatestr);
            holder.textView_date.setText(createdatestr);
            if(1==dayofweek){
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_1);
            }else if(2==dayofweek){
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_2);
            }else if(3==dayofweek){
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_3);
            }else if(4==dayofweek){
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_4);
            }else if(5==dayofweek){
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_5);
            }else if(6==dayofweek){
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_6);
            }else if(7==dayofweek){
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_7);
            }else {
                holder.imageView_dateofweek.setBackgroundResource(R.drawable.ic_dateofweek_1);
            }
            Glide.with(ctx)
                    .load(post.getIconResourceId())
                    .centerCrop()
                    //.animate(ViewPropertyAnimation.Animator)
                    .dontAnimate()
                    .placeholder(R.drawable.ic_android_black_18dp)
                    .error(R.drawable.ic_android_black_18dp)
                    .into(new ImageViewTarget<GlideDrawable>(holder.imageView_icon) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            holder.imageView_icon.setImageDrawable(resource);
                        }

                        @Override
                        public void setRequest(Request request) {
                            holder.imageView_icon.setTag(position);
                            holder.imageView_icon.setTag(R.id.glide_tag_id, request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) holder.imageView_icon.getTag(R.id.glide_tag_id);
                        }
                    });

        }catch (Exception e){
            Log.e(TAG, "onBindViewHolder error", e);
        }
        Log.d(TAG, "onBindViewHolder debug, end");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class  DiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        int position;
        ImageView imageView_dateofweek;
        CardView cardView_container;
        ImageView imageView_icon;
        TextView textView_content;
        TextView textView_date;

        DiaryViewHolder(View itemView){
            super(itemView);
            imageView_dateofweek = (ImageView) itemView.findViewById(R.id.iv_diary_timeline_dateofweek_day);
            cardView_container = (CardView) itemView.findViewById(R.id.cv_diary_timeline_item_content_container);
            imageView_icon = (ImageView) itemView.findViewById(R.id.iv_diary_timeline_item_icon);
            textView_content = (TextView) itemView.findViewById(R.id.tv_diary_timeline_item_content);
            textView_date = (TextView) itemView.findViewById(R.id.tv_diary_timeline_item_date);
            cardView_container.setOnClickListener(this);
            cardView_container.setOnLongClickListener(this);
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
//        boolean onItemLongClick(int posiyion);
//    }
}
