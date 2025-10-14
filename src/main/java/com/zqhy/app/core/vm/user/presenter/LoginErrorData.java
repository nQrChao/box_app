package com.zqhy.app.core.vm.user.presenter;

import java.util.HashMap;
import java.util.Map;

public class LoginErrorData {
    private static Map<String, LoginErrorData> map  =new HashMap<>();
    private static final String[]              CODE ={
        "has_reg","mob_has_reg","mob_no_reg","no_reg","pass_err"
    };

    private static final String[] MSG={
        "此用户名已存在！\n",
        "此手机已被注册绑定！\n",
        "检测此账号未注册！请检查账号，或注册新账号",
        "检测此账号未注册！请检查账号，或注册新账号",
        "账号或密码不正确"
    };

    private static final String[] MSG_ADD={
            "* 若是你的账号请直接登录！如遗失密码可通过忘记密码验证找回",
            "* 若是你的账号请直接登录！如遗失密码可通过忘记密码验证找回",
            "",
            "",
            ""
    };

    private static final String[] BTN={
        "前往登录","前往登录","立即注册","立即注册","找回密码"
    };

    public String errorMsg;
    public String btnText;
    public String errorMsg2;
    public static LoginErrorData getMap(String key){
        return map.get(key);
    }

    static{
        for(int i=0;i<CODE.length;i++){
            LoginErrorData data=new LoginErrorData();
            data.errorMsg=MSG[i];
            data.errorMsg2=MSG_ADD[i];
            data.btnText=BTN[i];
            map.put(CODE[i],data);
        }
    }
}
