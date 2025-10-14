package com.zqhy.app.core.view.community.task.dialog;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.core.data.model.community.task.TaskItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;

/**
 * @author Administrator
 */
public class TaskDialogHelper {

    private Context mContext;
    private OnTaskClickListener onTaskClickListener;

    public TaskDialogHelper(Context mContext, OnTaskClickListener onTaskClickListener) {
        this.mContext = mContext;
        this.onTaskClickListener = onTaskClickListener;
    }


    /**
     * @param taskAction 14 bt  15 折扣  18 十字键
     */
    public void showTaskDialog(int taskAction, TaskItemVo.DataBean taskInfoBean) {
        CustomDialog taskDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_task_dialog, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        ImageView mIvImage = taskDialog.findViewById(R.id.iv_image);
        TextView mBtnCommit = taskDialog.findViewById(R.id.btn_commit);

        switch (taskAction) {
            case 14:
                mIvImage.setImageResource(R.mipmap.img_task_dialog_about_bt);
                break;
            case 15:
                mIvImage.setImageResource(R.mipmap.img_task_dialog_about_discount);
                break;
            case 18:
                mIvImage.setImageResource(R.mipmap.img_task_dialog_about_main_page);
                break;
            default:
                break;
        }


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.getScreenDensity(mContext) * 48);
        gd.setColor(ContextCompat.getColor(mContext, R.color.color_eeeeee));
        mBtnCommit.setBackground(gd);
        mBtnCommit.setTextColor(ContextCompat.getColor(mContext, R.color.color_818181));
        mBtnCommit.setText("关闭（10S）");
        mBtnCommit.setOnClickListener(v -> taskDialog.dismiss());

        CountDownTimerCopyFromAPI26 timer = new CountDownTimerCopyFromAPI26(1000 * 10, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int leftSecond = (int) (millisUntilFinished / 1000);
                mBtnCommit.setText("关闭（" + leftSecond + "S）");
            }

            @Override
            public void onFinish() {
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.getScreenDensity(mContext) * 48);
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
                mBtnCommit.setBackground(gd);
                mBtnCommit.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                mBtnCommit.setText("已了解");

                mBtnCommit.setOnClickListener(v -> {
                    taskDialog.dismiss();
                    if (onTaskClickListener != null) {
                        onTaskClickListener.onClick(taskInfoBean);
                    }
                });
            }
        }.start();


        taskDialog.setOnDismissListener(dialog -> timer.cancel());

        taskDialog.show();
    }
}
