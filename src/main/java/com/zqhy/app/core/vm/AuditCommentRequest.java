package com.zqhy.app.core.vm;

/**
 * [功能说明]
 *
 * @author 韩国桐
 * @version [0.1, 2020/6/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuditCommentRequest {

    public interface OnActionDo{
        void onStart();
        void onSuccess();
        void onError(String error);
    }

}
