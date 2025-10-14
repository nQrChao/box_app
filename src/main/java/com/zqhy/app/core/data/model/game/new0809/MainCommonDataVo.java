package com.zqhy.app.core.data.model.game.new0809;

import com.google.gson.Gson;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/8/23-16:44
 * @description
 */
public class MainCommonDataVo extends BaseVo {

    public MainCommonDataVo.DataBean data;

    public static class DataBean {
        public List<String>                        module;
        public Map<String, Object>                 data;
        public List<MainTuiJianDataVo.ModuleStyle> module_style;

        public <T> T getItemData(Class<T> clazz, String module) {
            for (String key : data.keySet()) {
                if (key.equals(module)) {
                    Object value = data.get(key);
                    Gson gson = new Gson();
                    String result = gson.toJson(value);
                    return gson.fromJson(result, clazz);
                }
            }
            return null;
        }


        /**
         * module里数据
         *
         * @return
         */
        public List<String> getApiModule() {
            List<String> apiModule = new ArrayList<>();
            final String regex = "__";

            if (module_style != null) {
                for (MainTuiJianDataVo.ModuleStyle moduleStyle : module_style) {
                    String key = moduleStyle.api;
                    if (key != null) {
                        if (key.lastIndexOf(regex) != -1) {
                            apiModule.add(key.substring(0, key.lastIndexOf(regex)));
                        } else {
                            apiModule.add(key);
                        }
                    }
                }
            } else if (module != null) {
                for (String key : module) {
                    if (key.lastIndexOf(regex) != -1) {
                        apiModule.add(key.substring(0, key.lastIndexOf(regex)));
                    } else {
                        apiModule.add(key);
                    }
                }
            }
            return apiModule;
        }


        public void printData() {
            List<String> apiModule = new ArrayList<>();
            final String regex = "__";
            for (String key : module) {
                if (key.lastIndexOf(regex) != -1) {
                    apiModule.add(key.substring(0, key.lastIndexOf(regex)));
                } else {
                    apiModule.add(key);
                }
            }
            for (int i = 0; i < apiModule.size(); i++) {
                String key = module.get(i);
                String api = apiModule.get(i);
                switch (api) {
                    case "xy_lunbo":
                        LunboDataBeanVo xy_lunbo = getItemData(LunboDataBeanVo.class, key);
                        break;
                    case "xy_menu":
                        MainMenuVo xy_menu = getItemData(MainMenuVo.class, key);
                        break;
                    case "xy_renqi":
                        MainXingYouDataVo.RenQiDataBeanVo xy_renqi = getItemData(MainXingYouDataVo.RenQiDataBeanVo.class, key);
                        break;
                    case "xy_zhongbang":
                        MainPageItemVo xy_zhongbang = getItemData(MainPageItemVo.class, key);
                        break;
                    case "xy_zuixinshangjia":
                        MainXingYouDataVo.ZuiXingShangJiaDataBeanVo xy_zuixinshangjia = getItemData(MainXingYouDataVo.ZuiXingShangJiaDataBeanVo.class, key);
                        break;
                    case "xy_chaping":
                        LunboDataBeanVo xy_chaping = getItemData(LunboDataBeanVo.class, key);
                        break;
                    case "xy_haoyoutuijian":
                        MainXingYouDataVo.HaoYouTuiJianDataBeanVo xy_haoyoutuijian = getItemData(MainXingYouDataVo.HaoYouTuiJianDataBeanVo.class, key);
                        break;
                    default:
                        break;
                }
            }
        }
    }


}
