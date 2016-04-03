package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.MainApp;
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
        viewHolderBackground.cb_setting.setTag(imageItem);
        int chosen = imageItem.getChosen();
        if (chosen == 0) {
            //viewHolderBackground.cb_setting.setChecked(true);
            //viewHolderBackground.cb_setting.setEnabled(false);
            //viewHolderBackground.cb_setting.setClickable(false);
            viewHolderBackground.cb_setting.setVisibility(View.GONE);
            //viewHolderBackground.fl_bottom.setBackgroundColor(MainApp.getContext().getResources().getColor(R.color.gray));
            viewHolderBackground.tv_title.setText("默认");
            viewHolderBackground.tv_title.setTextColor(MainApp.getContext().getResources().getColor(R.color.white));
        } else if (chosen == 1) {
            viewHolderBackground.cb_setting.setChecked(true);
        } else {
            viewHolderBackground.cb_setting.setChecked(false);
        }
        ImageLoaderUtil.display(mContext, viewHolderBackground.iv_img, imageItem.getUrl());
    }


    class ViewHolderBackground extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_title;
        CheckBox cb_setting;
        FrameLayout fl_bottom;
        int itemPosition;

        public ViewHolderBackground(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_background_item_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_background_item_title);
            cb_setting = (CheckBox) itemView.findViewById(R.id.cb_background_item_setting);
            fl_bottom = (FrameLayout) itemView.findViewById(R.id.fl_background_bottom_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, itemPosition);
                    }
                }
            });

            cb_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemCheckedChangeListener != null) {
                        mOnItemCheckedChangeListener.onItemCheckedChange(v, itemPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChange(View view, int position);
    }
}
