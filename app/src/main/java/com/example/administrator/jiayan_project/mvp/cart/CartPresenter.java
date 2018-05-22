package com.example.administrator.jiayan_project.mvp.cart;

import android.os.Handler;
import android.util.Log;

import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.enity.cart.CartBean;
import com.example.administrator.jiayan_project.enity.login.UserBean;
import com.example.administrator.jiayan_project.mvp.base.AbstractMvpPersenter;
import com.example.administrator.jiayan_project.mvp.base.IBaseRequestCallBack;
import com.example.administrator.jiayan_project.mvp.login.LoginView;

/**
 * Created by Administrator on 2018/5/16/016.
 */

public class CartPresenter   extends AbstractMvpPersenter<CartView> {
    private CartModel cartModel;
    public CartPresenter(){
        this.cartModel=new CartModel(MyApplication.getContext());
    }
    public void clickRequestCart(){
        if (getmMvpView() != null) {
            getmMvpView().requestLoading();
        }
        //模拟网络延迟，可以显示出加载中
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cartModel.CartAll(new IBaseRequestCallBack<CartBean>() {
                    @Override
                    public void requestError(Throwable throwable) {
                        if (getmMvpView() != null) {
                            getmMvpView().resultFailure(Log.getStackTraceString(throwable));
                        }
                    }

                    @Override
                    public void requestSuccess(CartBean cartBean) {
                        if (getmMvpView() != null) {
                            getmMvpView().resultUserSuccess(cartBean);
                        }
                    }
                });

            }
        }, 10);
    }
}