package com.example.administrator.jiayan_project.adapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.R;
import com.example.administrator.jiayan_project.adapter.news.DescHolder;
import com.example.administrator.jiayan_project.adapter.news.HeaderHolder;
import com.example.administrator.jiayan_project.adapter.news.HotelEntityAdapter;
import com.example.administrator.jiayan_project.adapter.news.HotelUtils;
import com.example.administrator.jiayan_project.adapter.news.SectionedRecyclerViewAdapter;
import com.example.administrator.jiayan_project.enity.classify.MainChefClassifyBean;
import com.example.administrator.jiayan_project.http.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/7/9/009.
 */

public class MainClassifChefAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHolder, RecyclerView.ViewHolder> {


    public List<MainChefClassifyBean.TypedataBean> allTagList;
    public List<MainChefClassifyBean.TypedataBean.DetailBean> cAllTagList;
    private Context mContext;
    private LayoutInflater mInflater;
    private  int l;
    private SparseBooleanArray mBooleanMap;

    public MainClassifChefAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(List<MainChefClassifyBean.TypedataBean> allTagList) {
        this.allTagList = allTagList;
        notifyDataSetChanged();
    }

    public void setMsg(List<MainChefClassifyBean.TypedataBean.DetailBean> cAllTagList) {
        this.cAllTagList = cAllTagList;
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return HotelUtils.isEmpty(allTagList) ? 0 : allTagList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = allTagList.get(section).getDetail().size();
        if (count >= 80 && !mBooleanMap.get(section)) {
            count = 80;
        }
        l= allTagList.get(section).getDetail().size();
        return HotelUtils.isEmpty(allTagList.get(section).getDetail()) ? 0 : count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.classify_head, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.classify_desc, parent, false));
    }


    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = mBooleanMap.get(section);
                String text = isOpen ? "展开" : "关闭";
                mBooleanMap.put(section, !isOpen);
                holder.openView.setText(text);
                notifyDataSetChanged();
            }
        });
//        if (l==0){
//            holder.titleView.setVisibility(View.GONE);
//        }
        holder.titleView.setText(allTagList.get(section).getTitlename());
        holder.openView.setText(mBooleanMap.get(section) ? "关闭" : "展开");

    }


    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(final DescHolder holder, final int section, final int position) {
        Glide.with(MyApplication.getContext())
                .load(Constants.BaseUrl+allTagList.get(section).getDetail().get(position).getCookimg())
                .crossFade()
                .into(holder.imageView);
        holder.descView.setText(allTagList.get(section).getDetail().get(position).getCookname());
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("-----------------", "onClick: "+section );
//            }
//        });
        if(mOnLeftItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnLeftItemClickListener.onLeftItemClick(section,position);
                }
            });
        }
    }
    public interface OnTypeItemClickChefListener{
        void onLeftItemClick(int section,int position);
    }
    private OnTypeItemClickChefListener mOnLeftItemClickListener;

    public void setOnLeftItemClickChefListener(OnTypeItemClickChefListener onLeftItemClickListener) {
        this.mOnLeftItemClickListener = onLeftItemClickListener;
    }
}
