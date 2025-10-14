package com.zqhy.app.core.data.model.game.new0809.item;

import android.graphics.Color;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.CommonDataBeanVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/14 0014-9:44
 * @description
 */
public class LunboGameDataBeanVo extends CommonDataBeanVo {

    public List<DataBean> data;

    public static class DataBean extends GameInfoVo {
        public List<String> bg_colors;
        public boolean      tuijian_flag = true;
        public boolean      isSelected = false;

        public int[] getBgColors() {
            if (bg_colors == null || bg_colors.isEmpty()) {
                return new int[]{Color.parseColor("#2B64FF"), Color.parseColor("#3E60FF"), Color.parseColor("#8B1965")};
            }
            int[] res = new int[bg_colors.size()];

            for (int i = 0; i < res.length; i++) {
                try {
                    res[i] = Color.parseColor(bg_colors.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return res;
        }
    }
}
