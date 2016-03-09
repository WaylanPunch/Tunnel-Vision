package com.way.tunnelvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.util.ImageLoaderUtil;
import com.way.tunnelvision.util.TimeUtil;

import java.util.List;

/**
 * Created by pc on 2016/1/6.
 */
public class NewsCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = NewsCollectionAdapter.class.getName();

    private List<NewsModel> contents;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public NewsCollectionAdapter(Context context, List<NewsModel> contents) {
        this.mContext = context;
        this.contents = contents;
    }

    public List<NewsModel> getContents() {
        return contents;
    }

    public void setContents(List<NewsModel> contents) {
        this.contents = contents;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return TYPE_NORMAL;
//    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_card_normal, parent, false);
        return new ViewHolderNormal(view);
    }

    public NewsModel getItem(int position) {
        return contents == null ? null : contents.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsModel newsItem = contents.get(position);
        if (newsItem == null) {
            return;
        }
        ViewHolderNormal viewHolderNormal = (ViewHolderNormal) holder;
        viewHolderNormal.itemPosition = position;
        viewHolderNormal.tv_title.setText(newsItem.getTitle());
        viewHolderNormal.tv_source.setText(newsItem.getSource());
        viewHolderNormal.tv_digest.setText(newsItem.getDigest());
        String dateStrNormal = TimeUtil.prettyTimeForDate(newsItem.getPtime());
        viewHolderNormal.tv_ptime.setText(dateStrNormal);
        ImageLoaderUtil.display(mContext, viewHolderNormal.iv_img, newsItem.getImgsrc());
    }


    class ViewHolderNormal extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView iv_img;
        TextView tv_title;
        TextView tv_digest;
        TextView tv_source;
        TextView tv_ptime;
        int itemPosition;

        public ViewHolderNormal(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_news_item_normal_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_news_item_normal_title);
            tv_digest = (TextView) itemView.findViewById(R.id.tv_news_item_normal_digest);
            tv_source = (TextView) itemView.findViewById(R.id.tv_news_item_normal_source);
            tv_ptime = (TextView) itemView.findViewById(R.id.tv_news_item_normal_ptime);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, itemPosition);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                return mOnItemLongClickListener.onItemLongClick(v, itemPosition);
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}
