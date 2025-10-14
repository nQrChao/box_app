package com.zqhy.app.db.table.message;

import com.box.other.blankj.utilcode.util.Logs;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.zqhy.app.model.UserInfoModel;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/11
 */

public class MessageDbInstance {

    private static volatile MessageDbInstance instance;

    private MessageDbInstance() {
    }

    public static MessageDbInstance getInstance() {
        if (instance == null) {
            synchronized (MessageDbInstance.class) {
                if (instance == null) {
                    instance = new MessageDbInstance();
                }
            }
        }
        return instance;
    }


    /**
     * 保存消息
     *
     * @param messageVo
     */
    public boolean saveMessageVo(MessageVo messageVo) {
        if (messageVo == null) {
            Logs.i("messageVo == null");
            return false;
        }
        ModelAdapter<MessageVo> adapter = FlowManager.getModelAdapter(MessageVo.class);
        adapter.insert(messageVo);
        return true;
    }


    /**
     * 获取全部消息
     *
     * @return
     */
    public List<MessageVo> getAllMessages() {
        int uid = 0;
        if (UserInfoModel.getInstance().isLogined()) {
            uid = UserInfoModel.getInstance().getUserInfo().getUid();
        }

        List<MessageVo> messageVoList = SQLite.select().from(MessageVo.class)
                .where(MessageVo_Table.uid.eq(uid))
                .or(MessageVo_Table.uid.eq(0))
                .orderBy(OrderBy.fromNameAlias(NameAlias.of("message_time")).descending())
                .queryList();
        return messageVoList;
    }


    /**
     * 获取全部消息
     *
     * @param message_type
     * @return
     */
    public List<MessageVo> getAllMessages(int message_type) {
        int uid = 0;
        if (UserInfoModel.getInstance().isLogined()) {
            uid = UserInfoModel.getInstance().getUserInfo().getUid();
        }

        List<MessageVo> messageVoList = SQLite.select()
                .from(MessageVo.class)
                .where(MessageVo_Table.message_type.eq(message_type),
                        OperatorGroup.clause().and(MessageVo_Table.uid.eq(uid)).or(MessageVo_Table.uid.eq(0)))
                .orderBy(OrderBy.fromNameAlias(NameAlias.of("message_time")).descending())
                .queryList();
        return messageVoList;
    }

    /**
     * 获取message_id最大的条目
     *
     * @param message_type
     * @return
     */
    public MessageVo getMaxIdMessageVo(int message_type) {
        int uid = 0;
        if (UserInfoModel.getInstance().isLogined()) {
            uid = UserInfoModel.getInstance().getUserInfo().getUid();
        }
        MessageVo messageVo = SQLite.select()
                .from(MessageVo.class)
                .where(MessageVo_Table.message_type.eq(message_type),
                        MessageVo_Table.uid.eq(uid))
                .orderBy(OrderBy.fromNameAlias(NameAlias.of("message_id")).descending())
                .querySingle();
        return messageVo;
    }


    /**
     * 设置消息已读
     *
     * @param messageVo
     * @return
     */
    public boolean readMessage(MessageVo messageVo) {
        if (messageVo == null) {
            return false;
        }
        int ROWID = messageVo.get_id();
        if (ROWID < 1) {
            return false;
        }
        Logs.e("已读ROWID = " + ROWID + "的消息");

        messageVo.setMessage_is_read(1);

        ModelAdapter<MessageVo> adapter = FlowManager.getModelAdapter(MessageVo.class);
        return adapter.update(messageVo);
    }

    /**
     * 设置消息已读
     *
     * @param RowId
     * @return
     */
    public boolean readMessage(int RowId) {
        ModelAdapter<MessageVo> adapter = FlowManager.getModelAdapter(MessageVo.class);
        MessageVo messageVo = SQLite.select().from(MessageVo.class).where(MessageVo_Table._id.eq(RowId)).querySingle();
        messageVo.setMessage_is_read(1);
        Logs.e("已读ROWID = " + RowId + "的消息");
        return adapter.update(messageVo);
    }

    /**
     * 消息全部已读
     *
     * @return
     */
    public boolean readAllMessage() {
        int uid = 0;
        if (UserInfoModel.getInstance().isLogined()) {
            uid = UserInfoModel.getInstance().getUserInfo().getUid();
        }
        List<MessageVo> messageVoList = SQLite.select()
                .from(MessageVo.class)
                .where(OperatorGroup.clause().and(MessageVo_Table.uid.eq(uid)).or(MessageVo_Table.uid.eq(0)))
                .queryList();

        for (MessageVo messageVo : messageVoList) {
            messageVo.setMessage_is_read(1);
            messageVo.save();
        }
        return true;
    }

    /**
     * 从数据库删除消息
     *
     * @param messageVo
     */
    public boolean deleteNewMessage(MessageVo messageVo) {
        ModelAdapter<MessageVo> adapter = FlowManager.getModelAdapter(MessageVo.class);
        return adapter.delete(messageVo);
    }

    /**
     * 从数据库删除消息
     *
     * @param RowId
     */
    public void deleteNewMessage(int RowId) {
        SQLite.delete().from(MessageVo.class).where(MessageVo_Table._id.eq(RowId)).query();
    }

    /**
     * 获取未读消息条数
     *
     * @return
     */
    public int getUnReadMessageCount(int message_type, int lastOfDays) {
        int uid = 0;
        if (UserInfoModel.getInstance().isLogined()) {
            uid = UserInfoModel.getInstance().getUserInfo().getUid();
        }
        Long queryTime = System.currentTimeMillis() - lastOfDays * 24 * 3600;

        List<MessageVo> messageVoList = SQLite.select()
                .from(MessageVo.class)
                .where(MessageVo_Table.message_type.eq(message_type),
                        MessageVo_Table.uid.eq(uid),
                        MessageVo_Table.message_time.lessThan(queryTime),
                        MessageVo_Table.message_is_read.eq(0))
                .queryList();
        return messageVoList == null ? 0 : messageVoList.size();
    }

    /**
     * 获取未读消息条数
     *
     * @param message_type
     * @return
     */
    public int getUnReadMessageCount(int message_type) {
        //默认取30天
        return getUnReadMessageCount(message_type, 30);
    }

    /**
     * 获取未读消息条数
     * @return
     */
    public int getUnReadMessageCount() {
        int uid = 0;
        if (UserInfoModel.getInstance().isLogined()) {
            uid = UserInfoModel.getInstance().getUserInfo().getUid();
        }
        Long queryTime = System.currentTimeMillis() - 30 * 24 * 3600;

        List<MessageVo> messageVoList = SQLite.select()
                .from(MessageVo.class)
                .where(MessageVo_Table.message_type.notEq(5),
                        MessageVo_Table.uid.eq(uid),
                        MessageVo_Table.message_time.lessThan(queryTime),
                        MessageVo_Table.message_is_read.eq(0))
                .queryList();
        return messageVoList == null ? 0 : messageVoList.size();
    }
}
