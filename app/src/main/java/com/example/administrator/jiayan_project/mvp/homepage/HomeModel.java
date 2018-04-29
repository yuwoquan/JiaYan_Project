package com.example.administrator.jiayan_project.mvp.homepage;

import android.content.Context;

import com.example.administrator.jiayan_project.enity.homepage.BannerBean;
import com.example.administrator.jiayan_project.enity.homepage.FestivalBean;
import com.example.administrator.jiayan_project.enity.homepage.FirstChooseBean;
import com.example.administrator.jiayan_project.enity.homepage.HotBean;
import com.example.administrator.jiayan_project.enity.homepage.RecommendBean;
import com.example.administrator.jiayan_project.enity.homepage.StarBean;
import com.example.administrator.jiayan_project.http.Api;
import com.example.administrator.jiayan_project.http.BaseModel;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;

/**
 * Created by 鱼握拳 on 2018/4/11.
 */

public class HomeModel  extends BaseModel{
    private Call<BannerBean> bannerBeanCall;
    private Call<FestivalBean> festivalBeanCall;
    private Call<FirstChooseBean> firstChooseBeanCall;
    private Call<HotBean> hotBeanCall;
    private Call<RecommendBean> recommendBeanCall;
    private Call<StarBean> starBeanCall;
    private CompositeDisposable mcompositeDisposable;
    private Context context;
    private Api api;
    public  HomeModel(Context mContext) {
        super();
        context = mContext;
        api = retrofitManager.getService();
        mcompositeDisposable = new CompositeDisposable();
    }
    public void interruptHttp(){
        if(bannerBeanCall != null && !bannerBeanCall.isCanceled()){
            bannerBeanCall.cancel();
        }
        if (festivalBeanCall!=null&&!festivalBeanCall.isCanceled()){
            festivalBeanCall.cancel();
        }
        if (firstChooseBeanCall!=null&&!firstChooseBeanCall.isCanceled()){
            firstChooseBeanCall.cancel();
        }
        if (hotBeanCall!=null&&!hotBeanCall.isCanceled()){
            festivalBeanCall.cancel();
        }
        if (recommendBeanCall!=null&&!recommendBeanCall.isCanceled()){
            recommendBeanCall.cancel();
        }
        if (starBeanCall!=null&&!starBeanCall.isCanceled()){
            starBeanCall.cancel();
        }
    }
}
