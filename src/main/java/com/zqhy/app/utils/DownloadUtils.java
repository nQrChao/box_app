package com.zqhy.app.utils;

import android.text.TextUtils;

import com.zqhy.app.Setting;
import com.zqhy.app.core.data.model.game.GameExtraVo;
import com.zqhy.app.subpackage.ApkSectionInfo;
import com.zqhy.app.subpackage.Util;

import java.io.File;

public class DownloadUtils {

    /**
     * 写入渠道号
     * @param targetFile
     * @param gameExtraVo
     * @return
     */
    public static boolean writeChannelForGameExtraVo(File targetFile, GameExtraVo gameExtraVo){
        try {
            if (!TextUtils.isEmpty(gameExtraVo.getChannel())){
                Setting.INPUT_CHANNEL_GAME_ID = gameExtraVo.getGameid();
                Util.writeChannel(targetFile, gameExtraVo.getChannel(), false, false);
                String s = Util.readChannel(targetFile);
                if (!TextUtils.isEmpty(s)){
                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }

    /**
     * 检查apk完整性
     * @param needCheck
     * @param targetFile
     * @return
     */
    public static boolean checkCompletenessForGameExtraVo(boolean needCheck, File targetFile, GameExtraVo gameExtraVo){
        if (targetFile.length() < 1024 * 100){ //判断文件是否小于100KB
            return false;
        }
        if (needCheck){
            String readChannel = Util.readChannel(targetFile);
            if (TextUtils.isEmpty(readChannel)){
                return writeChannelForGameExtraVo(targetFile, gameExtraVo);
            }
        }
        return true;
    }
}
