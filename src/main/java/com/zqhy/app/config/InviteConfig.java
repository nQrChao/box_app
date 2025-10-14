package com.zqhy.app.config;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class InviteConfig {

    public static int invite_type;

    public static boolean isJustShare() {
        return invite_type == 1;
    }
}
