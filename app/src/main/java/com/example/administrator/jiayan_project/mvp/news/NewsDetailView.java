package com.example.administrator.jiayan_project.mvp.news;

import com.example.administrator.jiayan_project.enity.login.UserBean;
import com.example.administrator.jiayan_project.enity.news.NewsDetailBean;
import com.example.administrator.jiayan_project.mvp.base.IMvpBaseView;

/**
 * Created by Administrator on 2018/5/30/030.
 */

public interface NewsDetailView  extends IMvpBaseView {
    /**
     * 加载数据前
     */
    void requestLoading();

    /**
     * 加载出错
     * @param result
     */
    void resultFailure(String result);

    /**
     * 新闻详情接口
     */
    void  resultUserSuccess(NewsDetailBean newsDetailBean);
}
