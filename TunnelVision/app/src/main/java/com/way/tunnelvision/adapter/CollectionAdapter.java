package com.way.tunnelvision.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.flyco.labelview.LabelView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.entity.Post;
import com.way.tunnelvision.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/20.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {
    private static final String TAG = CollectionAdapter.class.getName();

    public ArrayList<Post> datas = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    private Context ctx;

    public CollectionAdapter(ArrayList<Post> datas, Context context){
        this.datas = datas;
        this.ctx = context;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_collection_item,parent,false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CollectionViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder debug, start");
        try {
            Post post = datas.get(position);
            holder.position = position;
            holder.mlabelView_label.setText("New");
            holder.mTextView_displayname.setText(post.getDisplayName());
            holder.mTextView_createddate.setText(post.getCreatedDate());
            holder.mTextView_content.setText(post.getContent());
            if(0 == post.getGender()){
                Drawable drawable = ctx.getResources().getDrawable(R.drawable.ic_gender_female_accent);
                holder.mTextView_displayname.setCompoundDrawables(drawable,null,null,null);
            }else {
                Drawable drawable = ctx.getResources().getDrawable(R.drawable.ic_gender_male_accent);
                holder.mTextView_displayname.setCompoundDrawables(drawable,null,null,null);
            }
//            viewHolder.mTextView_repeat.setTag(position);
//            viewHolder.mTextView_comment.setTag(position);
//            viewHolder.mTextView_like.setTag(position);
            holder.mTextView_repeat.setText(Integer.toString(post.getRepeatsCount()));
            holder.mTextView_comment.setText(Integer.toString(post.getCommentsCount()));
            holder.mTextView_like.setText(Integer.toString(post.getLikesCount()));

            Glide.with(ctx)
                    .load(post.getAvatarResourceId())
                    .centerCrop()
                    //.animate(ViewPropertyAnimation.Animator)
                    .dontAnimate()
                    .placeholder(R.drawable.ic_user_avatar_0)
                    .error(R.drawable.ic_user_avatar_0)
                    .into(new ImageViewTarget<GlideDrawable>(holder.mImageView_avatar) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            holder.mImageView_avatar.setImageDrawable(resource);
                        }

                        @Override
                        public void setRequest(Request request) {
                            holder.mImageView_avatar.setTag(position);
                            holder.mImageView_avatar.setTag(R.id.glide_tag_id, request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) holder.mImageView_avatar.getTag(R.id.glide_tag_id);
                        }
                    });

            holder.mImageView_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, datas.get(position).getDisplayName());
                }
            });
            Glide.with(ctx)
                    .load(post.getIconResourceId())
                    .centerCrop()
                    //.animate(ViewPropertyAnimation.Animator)
                    .dontAnimate()
                    .placeholder(R.drawable.bg_icon_default_accent)
                    .error(R.drawable.bg_icon_default_accent)
                    .into(new ImageViewTarget<GlideDrawable>(holder.mImageView_icon) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            holder.mImageView_icon.setImageDrawable(resource);
                        }

                        @Override
                        public void setRequest(Request request) {
                            holder.mImageView_icon.setTag(position);
                            holder.mImageView_icon.setTag(R.id.glide_tag_id, request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) holder.mImageView_icon.getTag(R.id.glide_tag_id);
                        }
                    });

            holder.mImageView_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, datas.get(position).getIconResourceId());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder error", e);
        }
        Log.d(TAG, "onBindViewHolder debug, end");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        //public View rootView;
        public ImageView mImageView_icon;
        public LabelView mlabelView_label;
        public CircularImageView mImageView_avatar;
        public TextView mTextView_displayname;
        public TextView mTextView_createddate;
        public TextView mTextView_content;
        public TextView mTextView_repeat;
        public TextView mTextView_comment;
        public TextView mTextView_like;
        public int position;

        public CollectionViewHolder(View view){
            super(view);
            mImageView_icon = (ImageView) view.findViewById(R.id.iv_collection_item_icon);
            mlabelView_label = (LabelView) view.findViewById(R.id.lv_collection_item_label);
            mImageView_avatar = (CircularImageView) view.findViewById(R.id.civ_collection_item_avatar);
            mTextView_displayname = (TextView) view.findViewById(R.id.tv_collection_item_displayname);
            mTextView_createddate = (TextView) view.findViewById(R.id.tv_collection_item_createddate);
            mTextView_content = (TextView) view.findViewById(R.id.tv_collection_item_content);
            mTextView_repeat = (TextView) view.findViewById(R.id.tv_collection_item_repeat);
            mTextView_comment = (TextView) view.findViewById(R.id.tv_collection_item_comment);
            mTextView_like = (TextView) view.findViewById(R.id.tv_collection_item_like);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
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
}
