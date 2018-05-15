package com.example.administrator.jiayan_project.adapter.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.administrator.jiayan_project.adapter.holder.NewsViewHolder;
import com.example.administrator.jiayan_project.enity.news.NewsBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Administrator on 2018/5/7/007.
 */

public class NewsAdapter extends RecyclerArrayAdapter<NewsBean> {
public NewsAdapter(Context context) {
        super(context);
        }

@Override
public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
        }
}