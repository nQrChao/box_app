package com.zqhy.app.core.view.chat.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.chat.ChatTeamNoticeListVo;
import com.zqhy.app.core.view.chat.ChatListActivity;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class ChatMemberItemHolder{}/* extends AbsItemHolder<UserInfoWithTeam, ChatMemberItemHolder.ViewHolder> {

    public ChatMemberItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull UserInfoWithTeam item) {
        UserInfo userInfo = item.getUserInfo();
        holder.mIvIcon.setData(userInfo.getAvatar(), item.getName(), ColorUtils.avatarColor(userInfo.getAccount()));
        holder.mTvName.setText(!TextUtils.isEmpty(userInfo.getName())? userInfo.getName(): userInfo.getAccount());

        holder.mTvName.setOnClickListener(view -> {
            if (TextUtils.equals(userInfo.getAccount(), IMKitClient.account())) {
                XKitRouter.withKey(RouterConstant.PATH_MINE_INFO_PAGE).withContext(mContext).withParam(RouterConstant.KEY_ACCOUNT_ID_KEY, userInfo.getAccount()).navigate();
            } else {
                XKitRouter.withKey(RouterConstant.PATH_USER_INFO_PAGE).withContext(mContext).withParam(RouterConstant.KEY_ACCOUNT_ID_KEY, userInfo.getAccount()).navigate();
            }
        });
        holder.mIvIcon.setOnClickListener(view -> {
            if (TextUtils.equals(userInfo.getAccount(), IMKitClient.account())) {
                XKitRouter.withKey(RouterConstant.PATH_MINE_INFO_PAGE).withContext(mContext).withParam(RouterConstant.KEY_ACCOUNT_ID_KEY, userInfo.getAccount()).navigate();
            } else {
                XKitRouter.withKey(RouterConstant.PATH_USER_INFO_PAGE).withContext(mContext).withParam(RouterConstant.KEY_ACCOUNT_ID_KEY, userInfo.getAccount()).navigate();
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_chat_member;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlRoot;
        private ContactAvatarView mIvIcon;
        private TextView mTvName;

        public ViewHolder(View view) {
            super(view);
            mLlRoot = view.findViewById(R.id.ll_root);
            mIvIcon = view.findViewById(R.id.cavUserIcon);
            mTvName = view.findViewById(R.id.tv_name);
        }
    }
}
*/