package com.example.administrator.jiayan_project;

import android.Manifest;

import android.graphics.PixelFormat;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.example.administrator.jiayan_project.ui.base.BaseActivity;
import com.example.administrator.jiayan_project.ui.base.BaseFragment;
import com.example.administrator.jiayan_project.ui.fragment.main.MainFragment;
import com.example.administrator.jiayan_project.ui.fragment.mine.AboutFragment;
import com.example.administrator.jiayan_project.ui.fragment.yan_news.YanNewsMainFragment;
import com.example.administrator.jiayan_project.utils.eventbus.StartNewsEvent;
import com.tencent.bugly.beta.Beta;
import com.vondear.rxtools.RxPermissionsTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {


    @Override
    protected int getContextViewId() {
        return R.id.qmuidemo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 7-11修复fragment加载为空。测试。暂未知道结果
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();//重新创建Manager，防止此对象为空
            manager.popBackStackImmediate(null, 1);//弹出所有fragment
        }


        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）,腾讯X5官网
        Beta.checkUpgrade(false,true);  // 自动检测更新，详情操作可以参考bugly官网的升级高级设置
        if (savedInstanceState == null) {
        BaseFragment fragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        EventBus.getDefault().register(this);
    }
           RxPermissionsTool.
                   with(this).
                    addPermission(Manifest.permission.CAMERA).
                    addPermission(Manifest.permission.CALL_PHONE).
                    addPermission(Manifest.permission.READ_PHONE_STATE).
                    addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
                    initPermission();
        }

//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）,腾讯X5官网
//        if (savedInstanceState == null) {
//            BaseFragment fragment = new MainFragment();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
//                    .addToBackStack(fragment.getClass().getSimpleName())
//                    .commit();
//            EventBus.getDefault().register(this);
//            }
//           RxPermissionsTool.
//                with(this).
//                addPermission(Manifest.permission.CAMERA).
//                addPermission(Manifest.permission.CALL_PHONE).
//                addPermission(Manifest.permission.READ_PHONE_STATE).
//                addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
//                initPermission();
//    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void onMoonEvent(StartNewsEvent startNewsEvent){
        if (startNewsEvent.getMessage()==null){
            startFragment(new YanNewsMainFragment());
        }
    }
}
