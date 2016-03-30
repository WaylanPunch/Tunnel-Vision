package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by pc on 2016/1/6.
 */
public class BackgroundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = BackgroundAdapter.class.getName();

    private List<HeaderImageModel> contents;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private OnItemCheckedChangeListener mOnItemCheckedChangeListener;

    public BackgroundAdapter(Context context, List<HeaderImageModel> contents) {
        this.mContext = context;
        this.contents = contents;
    }

    public List<HeaderImageModel> getContents() {
        return contents;
    }

    public void setContents(List<HeaderImageModel> contents) {
        this.contents = contents;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener onItemCheckedChangeListener) {
        this.mOnItemCheckedChangeListener = onItemCheckedChangeListener;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.background_item_card, parent, false);
        return new ViewHolderBackground(view);
    }

    public HeaderImageModel getItem(int position) {
        return contents == null ? null : contents.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderImageModel imageItem = contents.get(position);
        if (imageItem == null) {
            return;
        }
        ViewHolderBackground viewHolderBackground = (ViewHolderBackground) holder;
        viewHolderBackground.itemPosition = position;
        viewHolderBackground.tv_title.setText(imageItem.getTitle());
        //viewHolderBackground.tv_setting.setText(imageItem.getSource());
        ImageLoaderUtil.display(mContext, viewHolderBackground.iv_img, imageItem.getUrl());
    }


    class ViewHolderBackground extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_title;
        CheckBox cb_setting;
        int itemPosition;

        public ViewHolderBackground(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_background_item_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_background_item_title);
            cb_setting = (CheckBox) itemView.findViewById(R.id.cb_background_item_setting);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, itemPosition);
                    }
                }
            });

            cb_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (mOnItemCheckedChangeListener != null) {
                        mOnItemCheckedChangeListener.onItemCheckedChange(buttonView, isChecked, itemPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChange(CompoundButton view, boolean isChecked, int position);
    }
}
