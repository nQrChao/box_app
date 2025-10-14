package com.zqhy.app.core.data.model.game.detail;

import android.os.Build;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameServerListVo {


    private List<GameInfoVo.ServerListBean> serverlist;

    public List<GameInfoVo.ServerListBean> getServerlist() {
        if (serverlist == null) {
            return serverlist;
        }
        Collections.sort(serverlist, (t1, t2) -> (int) (t1.getBegintime() - t2.getBegintime()));
        long todayStartMs = CommonUtils.getTodayStartMs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return serverlist.stream().filter(serverListBean -> serverListBean.getBegintime() * 1000 > todayStartMs).collect(Collectors.toList());
        } else {
            for (int i = serverlist.size() - 1; i > 0; i--) {
                GameInfoVo.ServerListBean item = serverlist.get(i);
                if (item.getBegintime() * 1000 > todayStartMs) {
                    continue;
                } else {
                    serverlist.remove(i);
                }
            }
            return serverlist;
        }
    }

    public void setServerlist(List<GameInfoVo.ServerListBean> serverlist) {
        this.serverlist = serverlist;
    }


    /**
     * 获取最近未开服的区服信息
     *
     * @return
     */
    public GameInfoVo.ServerListBean getNewestServerItem() {
        List<GameInfoVo.ServerListBean> serverlist = getServerlist();
        if (serverlist == null || serverlist.isEmpty()) {
            return null;
        }
        int index = -1;

        for (int i = 0; i < serverlist.size(); i++) {
            GameInfoVo.ServerListBean item = serverlist.get(i);
            if (item.getBegintime() * 1000 > System.currentTimeMillis()) {
                index = i;
                break;
            }
        }
        return index == -1 ? serverlist.get(serverlist.size() - 1) : serverlist.get(index);
    }
}
