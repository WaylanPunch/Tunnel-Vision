package com.way.tunnelvision.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.model.NewsModel;

import java.util.List;

/**
 * Created by pc on 2016/1/6.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<NewsModel> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public NewsAdapter(List<NewsModel> contents) {
        this.contents = contents;
    }

    public List<NewsModel> getContents() {
        return contents;
    }

    public void setContents(List<NewsModel> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        //NewsModel newsItem = contents.get(position);
//        switch (newsItem.getNewsType()) {
//            case 0:
//                return TYPE_HEADER;
//            default:
                return TYPE_CELL;
        //}
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item_card_big, parent, false);
                return new ViewHolderBig(view);
            }
            case TYPE_CELL: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item_card_small, parent, false);
                return new ViewHolderSmall(view);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsModel newsItem = contents.get(position);
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ViewHolderBig viewHolderBig = (ViewHolderBig) holder;
//                viewHolderBig.iv_icon.setBackgroundResource(Integer.parseInt(newsItem.getNewsIcon()));
//                String dateStrBig = TimeUtil.prettyTimeForDate(newsItem.getNewsTime());
//                viewHolderBig.tv_time.setText(dateStrBig);
                break;
            case TYPE_CELL:
                ViewHolderSmall viewHolderSmall = (ViewHolderSmall) holder;
//                viewHolderSmall.tv_name.setText(newsItem.getNewsTitle());
//                String dateStrSmall = TimeUtil.prettyTimeForDate(newsItem.getNewsTime());
//                viewHolderSmall.tv_time.setText(dateStrSmall);
                break;
        }
    }

    class ViewHolderBig extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_time;
        public ViewHolderBig(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_news_item_big_icon);
            tv_time = (TextView) itemView.findViewById(R.id.tv_news_item_big_time);
        }
    }

    class ViewHolderSmall extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_time;
        public ViewHolderSmall(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_news_item_small_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_news_item_small_time);
        }
    }
}
