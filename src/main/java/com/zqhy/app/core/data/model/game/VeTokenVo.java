package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class VeTokenVo extends BaseVo{
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {
        ResultVo Result;
        String uid;
        String ws_url;//长链接地址
        long expiry_time;//到期时间

        public String getWs_url() {
            return ws_url;
        }

        public void setWs_url(String ws_url) {
            this.ws_url = ws_url;
        }

        public ResultVo getResult() {
            return Result;
        }

        public void setResult(ResultVo result) {
            Result = result;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public long getExpire_time() {
            return expiry_time;
        }

        public void setExpire_time(long expire_time) {
            this.expiry_time = expire_time;
        }
    }

    public class ResultVo implements Serializable{
        String create_at;
        String expire_at;
        String token;
        String ak;
        String sk;

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getExpire_at() {
            return expire_at;
        }

        public void setExpire_at(String expire_at) {
            this.expire_at = expire_at;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAk() {
            return ak;
        }

        public void setAk(String ak) {
            this.ak = ak;
        }

        public String getSk() {
            return sk;
        }

        public void setSk(String sk) {
            this.sk = sk;
        }
    }

}
