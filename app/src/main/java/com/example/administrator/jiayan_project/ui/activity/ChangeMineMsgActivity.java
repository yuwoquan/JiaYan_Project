package com.example.administrator.jiayan_project.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.jiayan_project.MainActivity;
import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.R;
import com.example.administrator.jiayan_project.db.bean.KeepUserBean;
import com.example.administrator.jiayan_project.db.bean.KeepUserBeanDao;
import com.example.administrator.jiayan_project.db.greendao.GreenDaoManager;
import com.example.administrator.jiayan_project.enity.banquet.FavoritrResultBean;
import com.example.administrator.jiayan_project.mvp.base.AbstractMvpActivity;
import com.example.administrator.jiayan_project.mvp.base.ChangeMsgMvpActivity;
import com.example.administrator.jiayan_project.mvp.changeMsg.ChangeMsgPresenter;
import com.example.administrator.jiayan_project.mvp.changeMsg.ChangeMsgView;
import com.example.administrator.jiayan_project.mvp.login.LoginPresenter;
import com.example.administrator.jiayan_project.mvp.login.LoginView;
import com.example.administrator.jiayan_project.ui.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class ChangeMineMsgActivity extends ChangeMsgMvpActivity<ChangeMsgView,ChangeMsgPresenter> implements ChangeMsgView {

    @BindView(R.id.mtopbar)
    QMUITopBar mTopBar;
    @BindView(R.id.image)
    QMUIRadiusImageView image;
    private Uri resultUri;
    private static final String TAG = "ChangeMineMsgActivity";
    private List<KeepUserBean> list;
    private int UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mine_msg);
        ButterKnife.bind(this);
        initTopBar();
        list = GreenDaoManager.getInstance().getSession().getKeepUserBeanDao().queryBuilder()
                .offset(0)//偏移量，相当于 SQL 语句中的 skip
                .limit(1)//只获取结果集的前 3 个数据
                .orderDesc(KeepUserBeanDao.Properties.Id)//通过 StudentNum 这个属性进行正序排序  Desc倒序
                .build()
                .list();
        UserId=list.get(0).getUserId();
        Resources r = mContext.getResources();
        resultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.bg_people) + "/"
                + r.getResourceTypeName(R.drawable.bg_people) + "/"
                + r.getResourceEntryName(R.drawable.bg_people));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialogChooseImage();
            }
        });
        Glide.with(this).load(R.drawable.bg_people).into(image);
    }

    @Override
    protected ChangeMsgPresenter createPresenter() {
        return new ChangeMsgPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data.getData());
                }
                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
//                RequestOptions options = new RequestOptions()
//                        .placeholder(R.drawable.circle_elves_ball)
//                        //异常占位图(当加载异常的时候出现的图片)
//                        .error(R.drawable.circle_elves_ball)
//                        //禁止Glide硬盘缓存缓存
//                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                Glide.with(mContext).
                        load(RxPhotoTool.cropImageUri).
                        thumbnail(0.5f).
                        into(image);
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {


//                    if (data != null) {
//                        Bitmap bitmap = data.getParcelableExtra("data");
//                        //将bitmap转换为Uri
//                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
//                        //对非正确的Uri处理，这类Uri存在手机的external.db中，可以查询_data字段查出对应文件的uri
//                        if (uri.getPath().contains("external")) {
//                            Log.e(TAG, "是否存在" );
//                            uri = external(uri.getPath());
//                            File file = null;
//                            try {
//                                file = new File(new URI(uri.toString()));
//                                Log.e(TAG, "onActivityResul水水水水水水水水水水水t: "+file );
//                            } catch (URISyntaxException e) {
//                                e.printStackTrace();
//                            }
//                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                final MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//                            getPresenter().postMineMsg(UserId,part);
//                        }
//                    }



                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, image);
                    RxSPTool.putContent(mContext, "AVATAR", resultUri.toString());
                    Log.e(TAG, "onActivityResult:11111111111111111"+resultUri );
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//网上
    private Uri external(String external) {
        String myImageUrl = "content://media" + external;
        Uri uri = Uri.parse(myImageUrl);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        File file = new File(img_path);
        Uri fileUri = Uri.fromFile(file);
        return fileUri;
    }
    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        Glide.with(mContext).
                load(uri).
                into(imageView);

//        if (data != null) {
//            Bitmap bitmap = data.getParcelableExtra("data");
            //将bitmap转换为Uri
//            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            //对非正确的Uri处理，这类Uri存在手机的external.db中，可以查询_data字段查出对应文件的uri
//            if (uri.getPath().contains("external")) {
//                Log.e(TAG, "是否存在" );
//                uri = external(uri.getPath());
                File file = null;
                try {
                    file = new File(new URI(uri.toString()));
                    Log.e(TAG, "onActivityResul水水水水水水水水水水水t: "+file );
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                final MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                getPresenter().postMineMsg(UserId,part);
//            }
//        }





        Log.e(TAG, "roadImageView: "+uri );


//        File file = new File(uri.toString());
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        final MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

//
//        Observable.timer(0, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                      getPresenter().postMineMsg(UserId,part);
//                    }
//                });


        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    private void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.cam));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.camtwo));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(500, 500)
                .withOptions(options)
                .start(this);
    }
    private void initDialogChooseImage() {
        RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mContext, TITLE);
        dialogChooseImage.show();
    }
    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle("修改信息");
        mTopBar.addRightTextButton("保存", R.id.topbar_right_about_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyApplication.getContext(), "55", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void requestLoading() {

    }

    @Override
    public void resultFailure(String result) {
        Log.e(TAG, "resultFailure: "+result );
    }

    @Override
    public void resultPostSuccess(FavoritrResultBean favoritrResultBean) {
        Log.e(TAG, "resultPostSuccess: "+favoritrResultBean.getCode()+"----"+favoritrResultBean.getMsg() );
    }
}
