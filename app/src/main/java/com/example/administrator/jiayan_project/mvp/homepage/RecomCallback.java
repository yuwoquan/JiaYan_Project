package com.example.administrator.jiayan_project.mvp.homepage;

import com.example.administrator.jiayan_project.enity.homepage.RecommendBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/9/009.
 */

public interface RecomCallback<T> {
      /**
     * @descriptoin	请求异常
     */
    void requestError(Throwable throwable);
    void requestRecomSuccess(List<RecommendBean> callBack);
}
