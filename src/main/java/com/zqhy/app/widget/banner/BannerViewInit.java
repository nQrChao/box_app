package com.zqhy.app.widget.banner;

import android.view.View;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.vm.main.data.MainPageData;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.widget.banner.newtype.NewBannerView;

import java.util.List;

/**
 * BannerView 单独初始化
 * @author 韩国桐
 * @version [0.1,2019-12-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BannerViewInit {
    private BaseActivity                  activity;
    private List<MainPageData.BannerData> bannerAdListVo;
    private NewBannerView                 mBannerView;
    private boolean                       showFlag;

    public BannerViewInit(BaseActivity activity, List<MainPageData.BannerData> bannerAdListVo, NewBannerView bannerView, boolean showFlag) {
        this.showFlag=showFlag;
        this.activity=activity;
        this.bannerAdListVo=bannerAdListVo;
        this.mBannerView=bannerView;
    }

    public void init() {
        if (bannerAdListVo!= null && !bannerAdListVo.isEmpty()) {
            mBannerView.delayTime(5).setBannerView().build(bannerAdListVo,showFlag);
            mBannerView.setOnBannerItemClickListener(index -> {
                try {
                    MainPageData.BannerData bannerVo = bannerAdListVo.get(index);
                    int gameType = 0;
                    if (bannerVo != null) {
                        appJump(bannerVo);
                        gameType = bannerVo.game_type;
                    }
                    switch (gameType) {
                        case 1:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(1, 3, (index + 1));
                            break;
                        case 2:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(2, 21, (index + 1));
                            break;
                        case 3:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(3, 40, (index + 1));
                            break;
                        case 4:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(4, 58, (index + 1));
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }else{
            mBannerView.setVisibility(View.GONE);
        }
    }

    public void init(NewBannerView.onBannerItemClickListener mOnBannerItemClickListener) {
        if (bannerAdListVo!= null && !bannerAdListVo.isEmpty()) {
            mBannerView.delayTime(5).setBannerView().build(bannerAdListVo,showFlag);
            mBannerView.setOnBannerItemClickListener(mOnBannerItemClickListener);
        }else{
            mBannerView.setVisibility(View.GONE);
        }
    }

    /**
     * App动态跳转
     * @param data 跳转数据
     */
    protected void appJump(MainPageData.BannerData data) {
        AppBaseJumpInfoBean.ParamBean paramBean=new AppBaseJumpInfoBean.ParamBean();
        if(data.param!=null){
            paramBean.setGame_list_id(data.param.game_list_id);
            paramBean.setGame_type(data.param.game_type);
            paramBean.setGameid(data.param.gameid);
            paramBean.setNewsid(data.param.newsid);
            paramBean.setTarget_url(data.param.target_url);
        }
        AppBaseJumpInfoBean appBaseJumpInfoBean =new AppBaseJumpInfoBean(data.page_type,paramBean);
        if (activity != null) {
            AppJumpAction appJumpAction = new AppJumpAction(activity);
            appJumpAction.jumpAction(appBaseJumpInfoBean);
        }
    }
}
