package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.util.ImageLoaderUtil;
import com.way.tunnelvision.util.StringUtil;
import com.way.tunnelvision.util.SystemUtil;

import java.util.List;

/**
 * Created by pc on 2016/3/11.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ImageViewHolder> {
    private final static String TAG = PhotoAdapter.class.getName();

    private List<ImageModel> mData;
    private Context mContext;
    private int mMaxWidth;
    private int mMaxHeight;

    private OnItemClickListener mOnItemClickListener;

    public PhotoAdapter(Context context) {
        this.mContext = context;
        mMaxWidth = SystemUtil.getWidthInPx(mContext) - 20;
        mMaxHeight = SystemUtil.getHeightInPx(mContext) - SystemUtil.getStatusHeight(mContext) -
                SystemUtil.dip2px(mContext, 96);
    }

    public void setContent(List<ImageModel> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item_card, parent, false);
        ImageViewHolder vh = new ImageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageModel imageModel = mData.get(position);
        if (imageModel == null) {
            return;
        }
        if (!StringUtil.isEmpty(imageModel.getTitle()) && !StringUtil.isBlank(imageModel.getTitle())) {
            Log.d(TAG, "onBindViewHolder debug, ImageModel Title = " + imageModel.getTitle());
            holder.mTitle.setText(imageModel.getTitle());
        }
        float scale = (float) imageModel.getWidth() / (float) mMaxWidth;
        int height = (int) (imageModel.getHeight() / scale);
        if (height > mMaxHeight) {
            height = mMaxHeight;
        }
        holder.mImage.setMinimumHeight(height);
        //holder.mImage.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth, height));
        ImageLoaderUtil.display(mContext, holder.mImage, imageModel.getThumburl());
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public ImageModel getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public ImageView mImage;

        public ImageViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tv_photo_item_title);
            mImage = (ImageView) v.findViewById(R.id.iv_photo_item_image);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }

}
