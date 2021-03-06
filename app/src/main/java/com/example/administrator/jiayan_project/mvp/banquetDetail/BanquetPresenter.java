package com.example.administrator.jiayan_project.mvp.banquetDetail;

import android.os.Handler;
import android.util.Log;

import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.enity.banquet.BanquetBean;
import com.example.administrator.jiayan_project.enity.banquet.CheckFavoriteBean;
import com.example.administrator.jiayan_project.enity.banquet.FavoritrResultBean;
import com.example.administrator.jiayan_project.enity.news.NewsDetailBean;
import com.example.administrator.jiayan_project.mvp.base.AbstractMvpPersenter;
import com.example.administrator.jiayan_project.mvp.base.IBaseRequestCallBack;
import com.example.administrator.jiayan_project.mvp.news.NewsDetailModel;
import com.example.administrator.jiayan_project.mvp.news.NewsDetailView;

/**
 * Created by Administrator on 2018/6/1/001.
 */

public class BanquetPresenter extends AbstractMvpPersenter<BanquetView> {
    private BanquetModel banquetModel;
    public BanquetPresenter(){
        this.banquetModel=new BanquetModel(MyApplication.getContext());
    }
    public void clickRequestBanquet(final String id) {
        //获取View
        if (getmMvpView() != null) {
            getmMvpView().requestLoading();
        }
        //模拟网络延迟，可以显示出加载中
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                banquetModel.getBanquet(id,new IBaseRequestCallBack<BanquetBean>() {
                    @Override
                    public void requestError(Throwable throwable) {
                        if (getmMvpView() != null) {
                            getmMvpView().resultFailure(Log.getStackTraceString(throwable));
                        }
                    }
                    @Override
                    public void requestSuccess(BanquetBean banquetBean) {
                        if (getmMvpView() != null) {
                            getmMvpView().resultBanquetSuccess(banquetBean);
                        }
                    }
                });
            }
        }, 300);
    }
    public void clickPostLove(final int userid,final int dinnerid) {
        banquetModel.postKeepFavorite(userid,dinnerid,new IBaseRequestCallBack<FavoritrResultBean>() {
            @Override
            public void requestError(Throwable throwable) {
                if (getmMvpView() != null) {
                    getmMvpView().resultFailure(Log.getStackTraceString(throwable));
                }
            }
            @Override
            public void requestSuccess(FavoritrResultBean favoritrResultBean) {
                if (getmMvpView() != null) {
                    getmMvpView().resultKeepFavoriteSuccess(favoritrResultBean);
                }
            }
        });
    }
    public void checkGetSaveLove(final int userid,final int dinnerid) {
        banquetModel.getChecjFavorite(userid,dinnerid,new IBaseRequestCallBack<CheckFavoriteBean>() {
            @Override
            public void requestError(Throwable throwable) {
                if (getmMvpView() != null) {
                    getmMvpView().resultFailure(Log.getStackTraceString(throwable));
                }
            }
            @Override
            public void requestSuccess(CheckFavoriteBean checkFavoriteBean) {
                if (getmMvpView() != null) {
                    getmMvpView().resultCheckFavoriteSuccess(checkFavoriteBean);
                }
            }
        });
    }

    public void AddToCart(int userid,int detail,int num,int dinnerid,int ren) {
        banquetModel.postAddCart(userid,detail,num,dinnerid,ren,new IBaseRequestCallBack<FavoritrResultBean>() {
            @Override
            public void requestError(Throwable throwable) {
                if (getmMvpView() != null) {
                    getmMvpView().resultFailure(Log.getStackTraceString(throwable));
                }
            }
            @Override
            public void requestSuccess(FavoritrResultBean favoritrResultBean) {
                if (getmMvpView() != null) {
                    getmMvpView().resultAddCartSuccess(favoritrResultBean);
                }
            }
        });
    }
    public void interruptHttp(){
        banquetModel.interruptHttp();
    }
}
