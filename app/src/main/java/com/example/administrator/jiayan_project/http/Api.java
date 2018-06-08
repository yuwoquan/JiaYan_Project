package com.example.administrator.jiayan_project.http;

import com.example.administrator.jiayan_project.enity.banquet.BanquetBean;
import com.example.administrator.jiayan_project.enity.banquet.CheckFavoriteBean;
import com.example.administrator.jiayan_project.enity.banquet.FavoritrResultBean;
import com.example.administrator.jiayan_project.enity.banquet.KeepFavoriteBean;
import com.example.administrator.jiayan_project.enity.big.BigYanBean;
import com.example.administrator.jiayan_project.enity.cart.CartBean;
import com.example.administrator.jiayan_project.enity.favourite.FavouriteBean;
import com.example.administrator.jiayan_project.enity.homepage.BannerBean;
import com.example.administrator.jiayan_project.enity.homepage.FestivalBean;
import com.example.administrator.jiayan_project.enity.homepage.FirstChooseBean;
import com.example.administrator.jiayan_project.enity.homepage.HotBean;
import com.example.administrator.jiayan_project.enity.homepage.NewsBean;
import com.example.administrator.jiayan_project.enity.homepage.RecommendBean;
import com.example.administrator.jiayan_project.enity.homepage.StarBean;
import com.example.administrator.jiayan_project.enity.login.LoginBean;
import com.example.administrator.jiayan_project.enity.login.UserBean;
import com.example.administrator.jiayan_project.enity.news.NewsDetailBean;
import com.example.administrator.jiayan_project.enity.news.NewsListBean;
import com.example.administrator.jiayan_project.enity.news.NewsVideoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 鱼握拳 on 2018/3/31.
 */

public interface Api {
    /**
     * 主页
     */
    /**首页轮播图*/
    @GET("index/ad")
    Observable<BannerBean> getBanner();

    /**
     * 快报
     */
    @GET("index/news")
    Observable<NewsBean> getNews();

    /**超值首选*/
    @GET("index/huasuan")
    Observable<FirstChooseBean> getFirst();

    /**新品推荐*/
    @GET("index/isnew")
    Observable<RecommendBean> getRecommend();

    /*** 明星推荐*/
    @GET("index/feature")
    Observable<StarBean>   getStar();

    /**热门产品*/
    @GET("index/ishot")
    Observable<HotBean> getHot();

    /***节日盛宴*/
    @GET("index/isfeast")
    Observable<FestivalBean>    getFestival();

    /**
     * 登陆短信接口
     * @return
     */
//    http://v.juhe.cn/sms/send?mobile=手机号码&tpl_id=短信模板ID&tpl_value=%23code%23%3D654654&key=
    @POST("send")
    Observable<UserBean>  postUser(@Query("mobile") String mobile,
                                   @Query("tpl_value") String tpl_value,
                                    @Query("key") String key,
                                    @Query("tpl_id") String tpl_id);

    /**
     * post到服务器
     * @param username
     * @return
     */
    @GET("Login/login/username/{username}")
    Observable<List<LoginBean>>  postMessage(@Path("username") String username);
    /**
    * 购物车
    */
    @GET("index.php/api/index/huasuan")
    Observable<CartBean>   getCart();

    /**
     * 快报详情信息
     */
    @GET("News/details/id/{id}")
    Observable<NewsDetailBean>   getNeswDetail(@Path("id") String id);

    /**
     * 新闻列表首页
     */
    @GET("News/news")
    Observable<NewsListBean>   getNeswHome();

    /**
     * 新闻列表视频
     */
    @GET("News/video")
    Observable<NewsVideoBean>   getNeswVideo();

    /**
     * 新闻列表活动
     */
    @GET("News/activity")
    Observable<NewsListBean>   getNeswActivity();

    /**
     * 新闻列表商学院
     */
    @GET("News/business")
    Observable<NewsListBean>   getNeswXue();

    /**
     * 新闻列表宴万家
     */
    @GET("News/yanwanjia")
    Observable<NewsListBean>   getNeswYan();

    /**
     *  宴会详情第一个页面
     */
    @GET("Details/message/id/{id}")
    Observable<BanquetBean>   getBanquet(@Path("id") String id);

    /**
     * 大型宴席
     */
    @GET("Large/large")
    Observable<BigYanBean>   getBigYan();

    /**
     * 我的收藏
     */
    @GET("Collection/mycollection/userid/{userid}")
    Observable<FavouriteBean>   getMyFavorite(@Path("userid") String userid);

    /**
     * 添加（删除）到我的收藏
     */
    @POST("Collection/editcollection")
    Observable<FavoritrResultBean>  postMyFavorite(@Body KeepFavoriteBean keepFavoriteBean);

    /**
     * 添加（删除）到我的收藏
     */
    @POST("Collection/delete")
    Observable<FavoritrResultBean>  deleteMyFavorite(@Body KeepFavoriteBean keepFavoriteBean);

    /**
     * 检查是否收藏该数据
     */
    @GET("Collection/collection/userid/{userid}/dinnerid/{dinnerid}")
    Observable<CheckFavoriteBean>  getCheckFavorite(@Path("userid") int userid,@Path("dinnerid") int dinnerid);
}
