package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final static String TAG = ImageAdapter.class.getName();

    private List<ImageModel> mData;
    private Context mContext;
    private int mMaxWidth;
    private int mMaxHeight;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnDownloadClickListener mOnDownloadClickListener;

    public ImageAdapter(Context context) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_card, parent, false);
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
            //LogUtil.d(TAG, "onBindViewHolder debug, ImageModel Title = " + imageModel.getTitle());
            holder.mTitle.setText(imageModel.getTitle());
        }
        float scale = (float) imageModel.getWidth() / (float) mMaxWidth;
        int height = (int) (imageModel.getHeight() / scale);
        if (height > mMaxHeight) {
            height = mMaxHeight;
        }
        holder.position = position;
        holder.mImage.setLayoutParams(new FrameLayout.LayoutParams(mMaxWidth, height));
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
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
    public void setOnDownloadClickListener(OnDownloadClickListener onDownloadClickListener) {
        this.mOnDownloadClickListener = onDownloadClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public interface OnDownloadClickListener {
        void onDownloadClick(View view, int position);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        public int position;
        public TextView mTitle;
        public ImageView mImage;
        public ImageView mImage_download;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_image_item_title);
            mImage = (ImageView) itemView.findViewById(R.id.iv_image_item_image);
            mImage_download = (ImageView) itemView.findViewById(R.id.iv_image_item_download);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        return mOnItemLongClickListener.onItemLongClick(v, position);
                    }
                    return false;
                }
            });
            mImage_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnDownloadClickListener != null){
                        mOnDownloadClickListener.onDownloadClick(v, position);
                    }
                }
            });
        }
    }

}
