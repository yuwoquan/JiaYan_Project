package com.example.administrator.jiayan_project.ui.fragment.yan_news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.R;
import com.example.administrator.jiayan_project.enity.news.NewsDetailBean;
import com.example.administrator.jiayan_project.mvp.base.AbstractMvpFragment;
import com.example.administrator.jiayan_project.mvp.news.NewsDetailPresenter;
import com.example.administrator.jiayan_project.mvp.news.NewsDetailView;
import com.example.administrator.jiayan_project.utils.helper.RudenessScreenHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsDetailFragment extends AbstractMvpFragment<NewsDetailView, NewsDetailPresenter> implements NewsDetailView {

    private static final String TAG = "NewsDetailFragment";
    @BindView(R.id.mtopbar)
    QMUITopBar mTopBar;
    @BindView(R.id.title)
    TextView title;
    //    @BindView(R.id.image)
//    ImageView image;
//    @BindView(R.id.contentview)
//    TextView contentview;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.wwatch)
    TextView wwatch;
    @BindView(R.id.time)
    TextView timer;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.visible_layout)
    RelativeLayout visibleLayout;
    private QMUITipDialog tipDialog;

    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_detail, null);
        RudenessScreenHelper.resetDensity(MyApplication.getContext(), 1080);
        ButterKnife.bind(this, layout);
        Bundle bundle = getArguments();
        String id = bundle.getString("id");
        Log.e(TAG, "onCreateView: " + id);
        getPresenter().clickRequestNews(id);
        initTopBar();
        return layout;
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        mTopBar.setTitle("宴快报详情");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void requestLoading() {
//        tipDialog = new QMUITipDialog.Builder(MyApplication.getContext())
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .setTipWord("Loading..")
//                .create();
//        tipDialog.show();
    }

    @Override
    public void resultFailure(String result) {
        Log.e(TAG, "resultFailure: " + result);
    }

    @Override
    public void resultUserSuccess(NewsDetailBean newsDetailBean) {
//        Glide.with(MyApplication.getContext()).load(Constants.BaseUrl + newsDetailBean.getData().get(0).getNewsimg()).into(image);
        title.setText(newsDetailBean.getData().get(0).getNewstitle());
        wwatch.setText(newsDetailBean.getData().get(0).getClick() + "人浏览过");
        author.setText("文章作者：" + newsDetailBean.getData().get(0).getNewsauthor());
        Long time = new Long(newsDetailBean.getData().get(0).getCreatetime());
        timer.setText(timestamp2Date(String.valueOf(time), "yyyy-MM-dd HH:mm:ss"));
//        contentview.setText(newsDetailBean.getData().get(0).getNewscontent());
        StringBuilder sb = new StringBuilder();
        // 拼接一段HTML代码
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title> 欢迎您 </title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(newsDetailBean.getData().get(0).getNewscontent());
        sb.append("</body>");
        sb.append("</html>");

        // 使用简单的loadData方法会导致乱码，可能是Android API的Bug
        //  show.loadData(sb.toString() , "text/html" , "utf-8");

//        WebSettings webSettings = webview.getSettings();//获取webview设置属性
//        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        //  加载、并显示HTML代码
//        webview.getSettings().setUseWideViewPort(true);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.setHorizontalScrollBarEnabled(false);//禁止水平滚动
//        webview.loadDataWithBaseURL(null, sb.toString(), "text/html" , "utf-8", null);


//        webview.loadData(newsDetailBean.getData().get(0).getNewscontent(), HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);

        webview.setDrawingCacheEnabled(true);
        webview.loadDataWithBaseURL(null, getHtmlData(newsDetailBean.getData().get(0).getNewscontent()), "text/html", "utf-8", null);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
//                    tipDialog.dismiss();
                    visibleLayout.setVisibility(View.VISIBLE);
//                    laout.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


    public static String timestamp2Date(String str_num, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(Long.parseLong(str_num)));
//            LogUtil.debug("timestamp2Date" + "将13位时间戳:" + str_num + "转化为字符串:", date);
            return date;
        } else {
            String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));
//            LogUtil.debug("timestamp2Date" + "将10位时间戳:" + str_num + "转化为字符串:", date);
            return date;
        }
    }


    @Override
    public NewsDetailPresenter createPresenter() {
        return new NewsDetailPresenter();
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}
