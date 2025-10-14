package com.zqhy.app.core.data.model.community.task;

/**
 * @author Administrator
 */
public class TaskInfoVo {

    private int          taskId;
    private int          iconRes;
    private String       taskTitle;
    private CharSequence taskDescription;
    private String       taskTag;


    public TaskInfoVo(int taskId, int iconRes, String taskTitle) {
        this.taskId = taskId;
        this.iconRes = iconRes;
        this.taskTitle = taskTitle;
    }

    public TaskInfoVo(int taskId, int iconRes, String taskTitle, CharSequence taskDescription) {
        this.taskId = taskId;
        this.iconRes = iconRes;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
    }

    public TaskInfoVo(int taskId, int iconRes, String taskTitle, CharSequence taskDescription, String taskTag) {
        this.taskId = taskId;
        this.iconRes = iconRes;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskTag = taskTag;
    }

    private int taskStatus = -1;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public CharSequence getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public TaskInfoVo setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }


    public static class Module extends TaskInfoVo {

        private String bg_color;

        public Module(int taskId, int iconRes, String taskTitle) {
            super(taskId, iconRes, taskTitle);
        }

        public Module(int taskId, int iconRes, String taskTitle, CharSequence taskDescription) {
            super(taskId, iconRes, taskTitle, taskDescription);
        }

        public Module(int taskId, int iconRes, String taskTitle, CharSequence taskDescription, String taskTag, String bg_color) {
            super(taskId, iconRes, taskTitle, taskDescription, taskTag);
            this.bg_color = bg_color;
        }


        public String getBg_color() {
            return bg_color;
        }

        public void setBg_color(String bg_color) {
            this.bg_color = bg_color;
        }
    }
}
