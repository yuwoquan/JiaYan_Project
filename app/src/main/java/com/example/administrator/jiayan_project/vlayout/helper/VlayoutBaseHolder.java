package com.example.administrator.jiayan_project.vlayout.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.jiayan_project.vlayout.homepage.ItemListener;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/19.
 */

public class VlayoutBaseHolder<T> extends RecyclerView.ViewHolder {
    public ItemListener mListener;
    public Context mContext;
    public View mView;
    public T mData;
    public int position;
    public String mText;

    public VlayoutBaseHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, itemView);
        init();
    }

    public void init() {

    }

    public void setContext(Context context) {
        mContext = context;
    }
    public void setTitle(String text){
        mText=text;
    }
    public void setListener(ItemListener listener) {
        mListener = listener;
    }

    public void setData(int ps, T mData) {
        this.mData = mData;
        position = ps;
    }

}
