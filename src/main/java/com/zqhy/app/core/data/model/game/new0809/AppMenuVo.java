package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.annotations.KeyParamVo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/8/16-10:04
 * @description
 */
public class AppMenuVo {

    public String     api;
    public int        id;
    public String     name;
    public ParamsBean params;
    public String icon;
    public String icon_active;

    private boolean labelSelect;

    public boolean isLabelSelect() {
        return labelSelect;
    }

    public void setLabelSelect(boolean labelSelect) {
        this.labelSelect = labelSelect;
    }


    public static class ParamsBean implements Serializable {

        @KeyParamVo(name = "url")
        public String url;
        @KeyParamVo(name = "container_id")
        public String container_id;

        private Map<String, String> mMap;

        public Map<String, String> getParams() {
            if (mMap == null) {
                mMap = new HashMap<>();
            }
            mMap.put("url", url);
            mMap.put("container_id", url);

            return getParamsByReflect(this);
        }

        public Map<String, String> getParamsByReflect(ParamsBean bean) {
            if (bean == null) {
                return null;
            }

            Class<? extends ParamsBean> beanClass = bean.getClass();
            Field[] fields = beanClass.getFields();
            Map<String, String> params = new HashMap<>();
            for (Field field : fields) {
                if (field.isAnnotationPresent(KeyParamVo.class)) {
                    KeyParamVo keyParamVo = field.getAnnotation(KeyParamVo.class);
                    try {
                        String key = keyParamVo.name();
                        Object value = field.get(bean);
                        if (value != null) {
                            params.put(key, String.valueOf(value));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return params;
        }
    }


}
