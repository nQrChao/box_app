package com.zqhy.app.core.data.model;

import com.box.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * @author Administrator
 * @date 2018/11/9
 */

public class Conversion<T> {
    public T convert(BaseResponseVo baseResponseVo) {
        Gson gson = new Gson();
        String result = gson.toJson(baseResponseVo);
        Logs.e("convert:" + result);
        Type type = new TypeToken<T>() {
        }.getType();

        return gson.fromJson(result, type);
    }
}
