package com.way.tunnelvision.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.flyco.labelview.LabelView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.entity.Post;
import com.way.tunnelvision.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private static final String TAG = PostAdapter.class.getName();

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
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_post_item, viewGroup, false);
        PostViewHolder vh = new PostViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final PostViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder debug, start");
        try {
            Post post = datas.get(position);
            viewHolder.position = position;
            viewHolder.mlabelView_label.setText("New");
            viewHolder.mTextView_displayname.setText(post.getDisplayName());
            viewHolder.mTextView_createddate.setText(post.getCreatedDate());
            viewHolder.mTextView_content.setText(post.getContent());
            if(0 == post.getGender()){
                Drawable drawable = ctx.getResources().getDrawable(R.drawable.ic_gender_female_accent);
                viewHolder.mTextView_displayname.setCompoundDrawables(drawable,null,null,null);
            }else {
                Drawable drawable = ctx.getResources().getDrawable(R.drawable.ic_gender_male_accent);
                viewHolder.mTextView_displayname.setCompoundDrawables(drawable,null,null,null);
            }
//            viewHolder.mTextView_repeat.setTag(position);
//            viewHolder.mTextView_comment.setTag(position);
//            viewHolder.mTextView_like.setTag(position);
            viewHolder.mTextView_repeat.setText(Integer.toString(post.getRepeatsCount()));
            viewHolder.mTextView_comment.setText(Integer.toString(post.getCommentsCount()));
            viewHolder.mTextView_like.setText(Integer.toString(post.getLikesCount()));

            Glide.with(ctx)
                    .load(post.getAvatarResourceId())
                    .centerCrop()
                    //.animate(ViewPropertyAnimation.Animator)
                    .dontAnimate()
                    .placeholder(R.drawable.ic_user_avatar_0)
                    .error(R.drawable.ic_user_avatar_0)
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

            viewHolder.mImageView_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, datas.get(position).getIconResourceId());
                }
            });
            /*
            viewHolder.mTextView_repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, "Repeats" + position);
                }
            });
            viewHolder.mTextView_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, "Comments" + position);
                }
            });
            viewHolder.mTextView_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToastUtil.show(ctx, "Likes" + position);
                }
            });
            */
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
    class PostViewHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {
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

        public PostViewHolder(View view) {
            super(view);
            //rootView = view.findViewById(R.id.ll_post_item_container);
            mImageView_icon = (ImageView) view.findViewById(R.id.iv_post_item_icon);
            mlabelView_label = (LabelView) view.findViewById(R.id.lv_post_item_label);
            mImageView_avatar = (CircularImageView) view.findViewById(R.id.civ_post_item_avatar);
            mTextView_displayname = (TextView) view.findViewById(R.id.tv_post_item_displayname);
            mTextView_createddate = (TextView) view.findViewById(R.id.tv_post_item_createddate);
            mTextView_content = (TextView) view.findViewById(R.id.tv_post_item_content);
            mTextView_repeat = (TextView) view.findViewById(R.id.tv_post_item_repeat);
            mTextView_comment = (TextView) view.findViewById(R.id.tv_post_item_comment);
            mTextView_like = (TextView) view.findViewById(R.id.tv_post_item_like);
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

//    interface OnRecyclerViewListener {
//        void onItemClick(int position);
//
//        boolean onItemLongClick(int position);
//    }
}
