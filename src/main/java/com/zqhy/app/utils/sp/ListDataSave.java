package com.zqhy.app.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * ListDataSave工具类，这里处理的方式时用gson把List转换成json类型，再利用SharedPreferences保存的。
 *
 * @author Administrator
 * @date 2018/11/8
 */

public class ListDataSave {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist) {
            return;
        }

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
//        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public <T> List<T> getDataList(String tag) {
        List<T> datalist = new ArrayList<T>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Log.d("Test", "strJson = " + strJson);
//        try {
//            Gson gson = new Gson();
//            datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
//            }.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            JSONArray array = new JSONArray(strJson);

            for (int i = 0; i < array.length(); i++) {
                datalist.add((T) array.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.d("Test", "datalist.size() = " + datalist.size());

        return datalist;

    }
}
