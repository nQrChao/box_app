package com.zqhy.app.core.data.model.kefu;

import android.os.Parcel;
import android.os.Parcelable;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/11
 */

public class KefuInfoVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{
        /**
         * title : 关于返利
         * res : ic_new_kefu_1
         * items : [{"id":1,"title1":"如何申请返利","content":"① 什么是游戏返利：\n游戏返利是指当充值达到返利要求后，可以获得的游戏货币或道具的奖励。一般为BT游戏标配活动、部分常规游戏也有此类活动；\n②如何申请返利：\n方法一：打开乐嗨嗨APP，点击首页下方导航栏中间的十字按钮即可进行返利申请；\n方法二：打开乐嗨嗨APP，搜索当前游戏，在游戏详情页下方也可以申请返利；\n注：道具申请可以在申请时，将道具内容填写在返利申请备注栏（部分自选道具需要注明详细道具名称）"},{"id":2,"title1":"无法申请返利","content":"① 请确保当前APP登录账号和游戏充值账号一致；\n② 请核对您单日充值总额是否满足返利活动要求；\n③ 部分游戏返利仅限充值后48小时内申请，逾期无法申请，请联系客服；\n④ 请核对是否已通过其他方式申请过返利，可在申请记录查看；"},{"id":3,"title1":"返利到账时间","content":"返利一般会在申请后，24小时内完成发放；\n部分游戏周末或节假日返利会稍有延迟，如有延迟一般会在工作日尽快安排发放，无需担心；\n注意：当日的返利申请，建议在确认总金额后再提交，避免申请后继续充值错过更高的返还奖励；\n如若延期，确定返利依然未收到，可联系客服协助核实；"},{"id":4,"title1":"返利到账方式","content":"返利到账形式一般有以下几种：\n① 发放在游戏内邮件中，注意查收领取；\n② 直接发放在背包中，需要使用后可以获得奖励（如元宝卡、礼包等）\n③ 在游戏界面某个【领奖】图标中进行领取；\n④ 激活码形式发放，可以在乐嗨嗨APP消息中进行领取，然后在游戏内激活；\n⑤ 直接发放到角色，需要自己留意金额道具相关变化；"},{"id":5,"title1":"道具申请方式","content":"有线下道具返还的游戏，在申请返利时候，将要申请的道具内容一并写在备注栏中（部分自选道具需要注明详细道具名称）即可；如果已申请返利，但未备注道具，可联系客服协助；"},{"id":6,"title1":"返利未到求助","content":"① 请确认提交的申请信息是否正确，例如区服、角色、角色ID等是否对应正确。可在申请后关      注申请是否被驳回，以及原因。修正后重新提交即可；\n② 请勿在返利未发放前修改游戏内角色名称，角色名称不对可能导致返利发放失败，甚至发放到其他角色；\n③ 请确认在乐嗨嗨APP内已经提交了返利申请，如未申请，自然是无法发放哦；\n④ 确实未到账，请联系客服协助咨询；"},{"id":7,"title1":"返利到账不符","content":"一般原因如下：\n① 检查活动日期与充值时段是否匹配，如果不是活动期间的充值，那么是无法获得对应奖励的；\n② 检查返利申请时候是否忘记在备注栏填写道具奖励以及道具名称是否详细或特殊返利活动比例；\n③ 其他原因导致的发放错误；\n④ 根据活动返利比例和下方返利计算方法看是否计算有误；\n以上问题，如发现返利内容和应得内容不符，可及时联系客服反馈，协助补齐奖励或了解原因。\n\n返利计算方式：【充值金额】x【返利比例】x【游戏充值比例】=最终获取元宝数量\n举例：A游戏充值比例为1：300；您充值100元后，活动返利为10%，那么应得返利元宝：100x10%x300=3000元宝"},{"id":8,"title1":"如何查看角色ID","content":"不同游戏角色ID查看方法不同，但基本类型如下：\n① 点开游戏内角色头像，可查看角色ID；\n② 点击游戏内设置选项，可查看角色ID；"}]
         */

        private String title;
        private String res;
        private List<ItemsBean> items;

        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }

    public static class ItemsBean implements Parcelable {
        /**
         * id : 1
         * title1 : 如何申请返利
         * content : ① 什么是游戏返利：
         游戏返利是指当充值达到返利要求后，可以获得的游戏货币或道具的奖励。一般为BT游戏标配活动、部分常规游戏也有此类活动；
         ②如何申请返利：
         方法一：打开乐嗨嗨APP，点击首页下方导航栏中间的十字按钮即可进行返利申请；
         方法二：打开乐嗨嗨APP，搜索当前游戏，在游戏详情页下方也可以申请返利；
         注：道具申请可以在申请时，将道具内容填写在返利申请备注栏（部分自选道具需要注明详细道具名称）
         */

        private int id;
        private String title1;
        private String content;

        protected ItemsBean(Parcel in) {
            id = in.readInt();
            title1 = in.readString();
            content = in.readString();
        }

        public static final Creator<ItemsBean> CREATOR = new Creator<ItemsBean>() {
            @Override
            public ItemsBean createFromParcel(Parcel in) {
                return new ItemsBean(in);
            }

            @Override
            public ItemsBean[] newArray(int size) {
                return new ItemsBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(title1);
            parcel.writeString(content);
        }
    }



}
