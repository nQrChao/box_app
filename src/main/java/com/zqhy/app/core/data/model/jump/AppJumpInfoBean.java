package com.zqhy.app.core.data.model.jump;


/**
 * @author Administrator
 * @date 2018/11/7
 */

public class AppJumpInfoBean extends AppBaseJumpInfoBean {

    public String pic;

    public AppJumpInfoBean(String page_type, ParamBean param) {
        super(page_type, param);
    }

    public String getPic() {
        return pic;
    }
}
