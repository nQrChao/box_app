package com.zqhy.app.adapter;

import android.content.Context;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameMainPageTodayVo;
import com.zqhy.app.core.data.model.game.GameSearchVo;
import com.zqhy.app.core.data.model.game.ServerListVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.kefu.KefuInfoVo;
import com.zqhy.app.core.data.model.mainpage.AntiAddictionVo;
import com.zqhy.app.core.data.model.mainpage.ChoiceListVo;
import com.zqhy.app.core.data.model.mainpage.GameTagVo;
import com.zqhy.app.core.data.model.mainpage.H5PlayedVo;
import com.zqhy.app.core.data.model.mainpage.boutique.BoutiqueGameListVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumListVo;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.data.model.mainpage.tabgame.TabGameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.MoreGameDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.data.model.video.GameVideoInfoVo;
import com.zqhy.app.core.view.kefu.holder.KefuMainHolder;
import com.zqhy.app.core.view.kefu.holder.KefuProItemHolder;
import com.zqhy.app.core.view.main.holder.AdBannerItemHolder;
import com.zqhy.app.core.view.main.holder.BoutiqueGameItemHolder;
import com.zqhy.app.core.view.main.holder.GameAlbumItemHolder;
import com.zqhy.app.core.view.main.holder.GameAlbumListItemHolder;
import com.zqhy.app.core.view.main.holder.GameAntiAddictionItemHolder;
import com.zqhy.app.core.view.main.holder.GameChoiceItemHolder;
import com.zqhy.app.core.view.main.holder.GameFigurePushItemHolder;
import com.zqhy.app.core.view.main.holder.GameH5PlayedItemHolder;
import com.zqhy.app.core.view.main.holder.GameMainPageTodayItemHolder;
import com.zqhy.app.core.view.main.holder.GameNavigationItemHolder;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.core.view.main.holder.GameSearchItemHolder;
import com.zqhy.app.core.view.main.holder.GameTagItemHolder;
import com.zqhy.app.core.view.main.holder.GameVideoJZItemHolder;
import com.zqhy.app.core.view.main.holder.MainPagerMoreGameItemHolder;
import com.zqhy.app.core.view.main.holder.TabGameItemHolder;
import com.zqhy.app.core.view.main.holder.TryGameItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTPageItemHolder;
import com.zqhy.app.core.view.message.holder.MessageItemInfoHolder;
import com.zqhy.app.core.view.server.holder.ServerListHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.widget.banner.BannerItemView;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class AdapterPool {

    private static AdapterPool adapterPool;

    public static AdapterPool newInstance() {
        if (adapterPool == null) {
            synchronized (AdapterPool.class) {
                if (adapterPool == null) {
                    adapterPool = new AdapterPool();
                }
            }
        }

        return adapterPool;
    }


    public BaseRecyclerAdapter getMainGameListAdapter(Context context) {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameSearchVo.class, new GameSearchItemHolder(context))
                .bind(BannerListVo.class, new BannerItemView(context))
                //                .bind(BannerListVo.class, new GameBannerItemView(context))
                .bind(BoutiqueGameListVo.class, new BoutiqueGameItemHolder(context))
                .bind(TabGameInfoVo.class, new TabGameItemHolder(context))
                .bind(GameInfoVo.class, new GameNormalItemHolder(context, true))
                .bind(AppJumpInfoBean.class, new AdBannerItemHolder(context))
                .bind(GameAlbumVo.class, new GameAlbumItemHolder(context))
                .bind(GameFigurePushVo.class, new GameFigurePushItemHolder(context))
                .bind(GameNavigationListVo.class, new GameNavigationItemHolder(context))
                .bind(GameAlbumListVo.class, new GameAlbumListItemHolder(context))
                .bind(H5PlayedVo.class, new GameH5PlayedItemHolder(context))
                .bind(TryGameItemVo.class, new TryGameItemHolder(context))
                .bind(GameVideoInfoVo.class, new GameVideoJZItemHolder(context))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(context))
                .bind(AntiAddictionVo.class, new GameAntiAddictionItemHolder(context))
                //2019.11.26 精选合辑
                .bind(ChoiceListVo.class, new GameChoiceItemHolder(context))
                //2019.11.27 更多游戏
                .bind(MoreGameDataVo.class, new MainPagerMoreGameItemHolder(context))
                .bind(GameTagVo.class, new GameTagItemHolder(context))
                //首页游戏样式模板
                .bind(MainBTPageGameVo.class, new GameBTPageItemHolder(context))
                .bind(GameMainPageTodayVo.class, new GameMainPageTodayItemHolder(context))
                .build();
    }


    public BaseRecyclerAdapter getServerListAdapter(Context context) {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(ServerListVo.DataBean.class, new ServerListHolder(context))
                .bind(EmptyDataVo.class, new EmptyItemHolder(context))
                .build();
    }


    public BaseRecyclerAdapter getGameListAdapter(Context context) {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameNormalItemHolder(context))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(context))
                .bind(EmptyDataVo.class, new EmptyItemHolder(context))
                .build();
    }


    public BaseRecyclerAdapter getKefuMainAdapter(Context context) {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(KefuInfoVo.DataBean.class, new KefuMainHolder(context))
                .build();
    }

    public BaseRecyclerAdapter getKefuProListAdapter(Context context) {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(KefuInfoVo.ItemsBean.class, new KefuProItemHolder(context))
                .build();
    }

    public BaseRecyclerAdapter getMessageListAdapter(Context context) {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(MessageVo.class, new MessageItemInfoHolder(context))
                .bind(EmptyDataVo.class, new EmptyItemHolder(context))
                .build();
    }
}
