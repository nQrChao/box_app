package com.zqhy.app.core.data.model.tryplay;

import android.os.Parcel;
import android.os.Parcelable;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
public class TryGameInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private GameInfoVo  gameinfo;
        private TrialInfoVo trial_info;
        private TaskListVo  task_list;

        private RankingListVo ranking_list;

        private List<CompetitionInfoVo> competition_list;

        public GameInfoVo getGameinfo() {
            return gameinfo;
        }

        public TrialInfoVo getTrial_info() {
            return trial_info;
        }

        public TaskListVo getTask_list() {
            return task_list;
        }

        public RankingListVo getRanking_list() {
            return ranking_list;
        }

        public List<CompetitionInfoVo> getCompetition_list() {
            return competition_list;
        }

        /***********2020.11.19新增*****************************************************************************************************************/

        private TryTopInfoVo top_info;

        private List<TrialItemInfoVo> list;

        public TryTopInfoVo getTop_info() {
            return top_info;
        }

        public List<TrialItemInfoVo> getList() {
            return list;
        }
    }

    public static class TryTopInfoVo extends GameInfoVo {

        /**
         * 2020.11.19 新增************************************************
         ****/
        private long endtime;

        public long getEndtime() {
            return endtime;
        }

        /**
         * 0:未参与, 1:已参与
         */
        private int is_join;


        public boolean getIs_join() {
            return is_join == 1;
        }

    }

    public static class TrialItemInfoVo {

        /**
         * id : 1
         * tid : 1
         * title : 试玩时长满30分钟
         * task_type : playtime
         * stat_type : 0
         * condition_num : 30
         * integral : 10
         * total_integral : 500
         * receive_integral : 0
         * finished_num : 0
         * status : 1
         */

        private int    id;
        private int    tid;
        private String title;
        private String task_type;
        private String stat_type;
        private int    condition_num;
        private int    integral;
        private int    total_integral;
        private int    receive_integral;
        private int    finished_num;
        private int    status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTask_type() {
            return task_type;
        }

        public void setTask_type(String task_type) {
            this.task_type = task_type;
        }

        public String getStat_type() {
            return stat_type;
        }

        public void setStat_type(String stat_type) {
            this.stat_type = stat_type;
        }

        public int getCondition_num() {
            return condition_num;
        }

        public void setCondition_num(int condition_num) {
            this.condition_num = condition_num;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getTotal_integral() {
            return total_integral;
        }

        public void setTotal_integral(int total_integral) {
            this.total_integral = total_integral;
        }

        public int getReceive_integral() {
            return receive_integral;
        }

        public void setReceive_integral(int receive_integral) {
            this.receive_integral = receive_integral;
        }

        public int getFinished_num() {
            return finished_num;
        }

        public void setFinished_num(int finished_num) {
            this.finished_num = finished_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class CompetitionInfoVo implements Parcelable {

        /**
         * get_time : 0
         * get_uid : 0
         * id : 8
         * is_reward : 0
         * ranking : 8
         * reward_integral : 300
         * tid : 1
         */

        private int    ranking;
        private int    reward_integral;
        private int    tid;
        private String username;
        private String get_time;

        protected CompetitionInfoVo(Parcel in) {
            ranking = in.readInt();
            reward_integral = in.readInt();
            tid = in.readInt();
            username = in.readString();
            get_time = in.readString();
        }

        public static final Creator<CompetitionInfoVo> CREATOR = new Creator<CompetitionInfoVo>() {
            @Override
            public CompetitionInfoVo createFromParcel(Parcel in) {
                return new CompetitionInfoVo(in);
            }

            @Override
            public CompetitionInfoVo[] newArray(int size) {
                return new CompetitionInfoVo[size];
            }
        };

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public int getReward_integral() {
            return reward_integral;
        }

        public void setReward_integral(int reward_integral) {
            this.reward_integral = reward_integral;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGet_time() {
            return get_time;
        }

        public void setGet_time(String get_time) {
            this.get_time = get_time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ranking);
            dest.writeInt(reward_integral);
            dest.writeInt(tid);
            dest.writeString(username);
            dest.writeString(get_time);
        }
    }

    public static class RankingListVo implements Serializable {

        private List<DataBean> attach;
        private List<DataBean> level;

        public List<DataBean> getAttach() {
            return attach;
        }

        public List<DataBean> getLevel() {
            return level;
        }

        public static class DataBean implements Serializable {

            /**
             * add_time : 1555294646
             * level_name :
             * level_val : 20
             * role_name : 深蓝屠龙
             * servername : 32服
             */

            private String level_name;
            private String role_name;
            private String servername;

            public String getLevel_name() {
                return level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name;
            }

            public String getRole_name() {
                return role_name;
            }

            public void setRole_name(String role_name) {
                this.role_name = role_name;
            }

            public String getServername() {
                return servername;
            }

            public void setServername(String servername) {
                this.servername = servername;
            }
        }
    }

    public static class TaskListVo implements Serializable {

        private List<DataBean> attach;
        private List<DataBean> level;
        private List<DataBean> pay;


        public List<DataBean> getAttach() {
            return attach;
        }

        public List<DataBean> getLevel() {
            return level;
        }

        public List<DataBean> getPay() {
            return pay;
        }

        public static class DataBean implements Serializable {

            /**
             * max_v : 0
             * status : 1
             * task_got_num : 0
             * task_integral : 100
             * task_level : 2
             * task_stock : 10
             * task_type : 3
             * task_val : 100
             * tid : 1
             * ttid : 6
             */

            private int    max_v;
            /**
             * 状态.
             * 1:不可领取;
             * 2:可领取;
             * 3:已领取;
             * 4:已抢完;
             */
            private int    status;
            /**
             * 已领取数量
             */
            private int    task_got_num;
            /**
             * 奖励积分数量
             */
            private int    task_integral;
            /**
             * 任务说明,
             * 例如 “1装10级”, “20万”,
             * 当task_type=3 即充值任务时,task_level=1:表示 单笔充值, 2表示累计充值
             */
            private String task_level;
            /**
             * 总库存
             */
            private int    task_stock;
            /**
             * 任务类型 1:等级; 2:战力; 3:充值
             */
            private int    task_type;
            /**
             * 任务条件, 例如等级 50 战力 10000.
             */
            private int    task_val;
            /**
             * 试玩ID
             */
            private int    tid;
            /**
             * 任务ID
             */
            private int    ttid;

            public int getMax_v() {
                return max_v;
            }

            public void setMax_v(int max_v) {
                this.max_v = max_v;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTask_got_num() {
                return task_got_num;
            }

            public void setTask_got_num(int task_got_num) {
                this.task_got_num = task_got_num;
            }

            public int getTask_integral() {
                return task_integral;
            }

            public void setTask_integral(int task_integral) {
                this.task_integral = task_integral;
            }

            public String getTask_level() {
                return task_level;
            }

            public void setTask_level(String task_level) {
                this.task_level = task_level;
            }

            public int getTask_stock() {
                return task_stock;
            }

            public void setTask_stock(int task_stock) {
                this.task_stock = task_stock;
            }

            public int getTask_type() {
                return task_type;
            }

            public void setTask_type(int task_type) {
                this.task_type = task_type;
            }

            public int getTask_val() {
                return task_val;
            }

            public void setTask_val(int task_val) {
                this.task_val = task_val;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public int getTtid() {
                return ttid;
            }

            public void setTtid(int ttid) {
                this.ttid = ttid;
            }
        }
    }

    public static class TrialInfoVo {

        /**
         * tid : 1
         * gameid : 1903
         * server_desc : 30服以后
         * total : 2940
         * got_total : 0
         * begintime : 2019-04-15 10:00:00
         * endtime : 2019-04-22 12:00:00
         * competition_type : 1
         * user_status : 1
         */

        private int    tid;
        private int    gameid;
        private String server_desc;
        private int    total;
        private int    got_total;
        private String begintime;
        private String endtime;
        /**
         * 冲级赛标记
         * 0:没有冲级赛;
         * 1:等级冲级赛;
         * 2:战力冲级赛
         */
        private int    competition_type;
        /**
         * 用户当前试玩状态
         * 0:未参与;
         * 1:已参与
         */
        private int    user_status;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getServer_desc() {
            return server_desc;
        }

        public void setServer_desc(String server_desc) {
            this.server_desc = server_desc;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getGot_total() {
            return got_total;
        }

        public void setGot_total(int got_total) {
            this.got_total = got_total;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getCompetition_type() {
            return competition_type;
        }

        public void setCompetition_type(int competition_type) {
            this.competition_type = competition_type;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }
    }


}
