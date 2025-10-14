package com.zqhy.app.core.data.model.version;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/28
 */

public class VersionVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{

        /**
         * vercode : 1
         * isforce : 1
         * updateContent : 版本号：272|更新大小：16.69M|1、BT返利申请流程优化；|2、部分页面细节调整；|3、已知问题修复；|*温馨提示：如更新失败，请卸载APP后重新下载，或联系客服QQ：800185872咨询！()
         * appdir :
         */

        private int vercode;
        private int isforce;
        private String updateContent;
        private String appdir;
        private String version;
        private String package_size;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPackage_size() {
            return package_size;
        }

        public void setPackage_size(String package_size) {
            this.package_size = package_size;
        }

        public int getVercode() {
            return vercode;
        }

        public void setVercode(int vercode) {
            this.vercode = vercode;
        }

        public int getIsforce() {
            return isforce;
        }

        public void setIsforce(int isforce) {
            this.isforce = isforce;
        }

        public String getUpdateContent() {
            return updateContent.replace("|","\n");
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public String getAppdir() {
            return appdir;
        }

        public void setAppdir(String appdir) {
            this.appdir = appdir;
        }
    }
}
