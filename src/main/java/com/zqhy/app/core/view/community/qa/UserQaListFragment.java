package com.zqhy.app.core.view.community.qa;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.community.qa.QaCenterQuestionListVo;
import com.zqhy.app.core.data.model.community.qa.UserQaCenterInfoVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.qa.holder.QaCenterItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.List;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Administrator
 */
public class UserQaListFragment extends BaseListFragment<QaViewModel> {

    public static UserQaListFragment newInstance(int type, int user_id) {
        UserQaListFragment fragment = new UserQaListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("user_id", user_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(UserQaCenterInfoVo.QaCenterQuestionVo.class, new QaCenterItemHolder(_mActivity, type))
                .build()
                .setTag(R.id.tag_fragment, getParentFragment());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }


    private int type, user_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            user_id = getArguments().getInt("user_id");
        }
        super.initView(state);
        initData();
    }


    public void setCanAnswerGameList(List<GameInfoVo> gameInfoVos, int more_answer_game_list, int user_answer_question_list_size) {
        if (type == 1) {
            if (gameInfoVos == null || gameInfoVos.size() == 0) {
                return;
            }
            View headerView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_user_qa_center_answer_game_list, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(layoutParams);
            LinearLayout mLlItemList = headerView.findViewById(R.id.ll_item_list);
            TextView mTvListTitle = headerView.findViewById(R.id.tv_list_title);
            mTvListTitle.setVisibility(user_answer_question_list_size > 0 ? View.VISIBLE : View.GONE);

            for (int i = 0; i < gameInfoVos.size(); i++) {
                GameInfoVo gameInfoBean = gameInfoVos.get(i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int leftMargin = i == 0 ? (int) (5 * density) : 0;
                int rightMargin = (int) (10 * density);
                params.setMargins(leftMargin, 0, rightMargin, 0);

                mLlItemList.addView(createGameInfoView(gameInfoBean), params);
            }
            if (more_answer_game_list == 1) {
                GameInfoVo gameInfoBean = new GameInfoVo();
                gameInfoBean.setGameid(-1);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int leftMargin = 0;
                int rightMargin = (int) (10 * density);
                params.setMargins(leftMargin, 0, rightMargin, 0);
                mLlItemList.addView(createGameInfoView(gameInfoBean), params);
            }
            addHeaderView(headerView);
        }
    }

    private View createGameInfoView(GameInfoVo gameInfoVo) {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.item_user_qa_game_list, null);

        LinearLayout mLlRootview = view.findViewById(R.id.ll_rootview);
        ImageView mIvGameIcon = view.findViewById(R.id.iv_game_icon);
        TextView mTvTxt = view.findViewById(R.id.tv_txt);
        TextView mTvSubTxt = view.findViewById(R.id.tv_sub_txt);
        TextView mTvBtn = view.findViewById(R.id.tv_btn);
        FrameLayout mFlOtherIcon = view.findViewById(R.id.fl_other_icon);

        boolean isTagMore = -1 == gameInfoVo.getGameid();

        if (!isTagMore) {
            GlideUtils.loadRoundImage(_mActivity, gameInfoVo.getGameicon(), mIvGameIcon);
            mTvTxt.setText(gameInfoVo.getGamename());
            String strCount = String.valueOf(gameInfoVo.getQuestion_count());
            if (gameInfoVo.getQuestion_count() > 99) {
                strCount = "99+";
            }
            mTvSubTxt.setTextColor(Color.parseColor("#999999"));
            SpannableString sp = new SpannableString("有" + strCount + "条求助");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#11A8FF"));
            sp.setSpan(colorSpan, 1, 1 + strCount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvSubTxt.setText(sp);

            mTvBtn.setText("帮帮TA");

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(24 * density);
            gd.setColor(Color.parseColor("#ff8f19"));
            mTvBtn.setBackground(gd);
            mTvBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            mIvGameIcon.setVisibility(View.GONE);
            mFlOtherIcon.setVisibility(View.VISIBLE);
            ImageView image = new ImageView(_mActivity);
            image.setImageResource(R.mipmap.ic_user_qa_more);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            mFlOtherIcon.addView(image, params);

            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(5 * density);
            gd1.setColor(Color.parseColor("#DBDBDB"));
            mFlOtherIcon.setBackground(gd1);

            mTvTxt.setText("更多问答");
            mTvSubTxt.setText("快去帮助TA");
            mTvSubTxt.setTextColor(Color.parseColor("#878787"));
            mTvBtn.setText("查看");

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadius(24 * density);
            gd2.setColor(Color.parseColor("#ffeddb"));
            mTvBtn.setBackground(gd2);
            mTvBtn.setTextColor(Color.parseColor("#ff8f19"));
        }

        mLlRootview.setOnClickListener(v -> {
            if (isTagMore) {
                start(UserPlayGameListFragment.newInstance(user_id));
            } else {
                start(GameQaListFragment.newInstance(gameInfoVo.getGameid()));
            }
        });
        return view;
    }

    @Override
    public void start(ISupportFragment toFragment) {
        if (getParentFragment() != null) {
            ((SupportFragment) getParentFragment()).start(toFragment);
        } else {
            super.start(toFragment);
        }
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        page++;
        getUserQaListData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getUserQaListData();
    }

    private void getUserQaListData() {
        if (mViewModel != null) {
            mViewModel.getUserQaListData(type, user_id, page, pageCount, new OnBaseCallback<QaCenterQuestionListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(QaCenterQuestionListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    clearData();
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                            .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                            .setPaddingTop((int) (24 * density)));
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

}
