package com.example.administrator.jiayan_project.vlayout.homepage;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.R;
import com.example.administrator.jiayan_project.enity.homepage.FestivalBean;
import com.example.administrator.jiayan_project.http.Constants;
import com.example.administrator.jiayan_project.vlayout.helper.VlayoutBaseHolder;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鱼握拳 on 2018/4/12.
 */

public class FestivalHolder extends VlayoutBaseHolder<FestivalBean> {
    @BindView(R.id.image)
    QMUIRadiusImageView imageView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.liangdian)
    TextView liangdian;
    @BindView(R.id.money)
    TextView money;
    private static final String TAG = "FestivalHolder";
    public FestivalHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(int ps, FestivalBean fData) {
        super.setData(ps, fData);

        List<FestivalBean.DataBean> list=fData.getData();
        for (int i = 0; i <fData.getData().size() ; i++) {
            Glide.with(MyApplication.getContext()).load(Constants.JiaYan+fData.getData().get(i).getOriginalimg()).into(imageView);
            name.setText(fData.getData().get(i).getPrice());
            liangdian.setText("88");
            money.setText("¥"+fData.getData().get(i).getPrice());
        }
    }
}
