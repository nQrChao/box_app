package com.zqhy.app.core.view.transaction.sell;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.transaction.TradeHeaderVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.transaction.holder.TradeChooseGameItemHolder;
import com.zqhy.app.core.view.transaction.holder.TradeHeaderItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TransactionChooseGameFragment extends BaseListFragment<TransactionViewModel> {

    public static TransactionChooseGameFragment newInstance(String gameid, int xh_id) {
        TransactionChooseGameFragment fragment = new TransactionChooseGameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameid", gameid);
        bundle.putInt("xh_id", xh_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(TradeHeaderVo.class, new TradeHeaderItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameInfoVo.class, new TradeChooseGameItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private int selectXh_id = -1;
    private String gameid;

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }


    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getString("gameid");
            selectXh_id = getArguments().getInt("xh_id", -1);
        }
        super.initView(state);
        initActionBackBarAndTitle("选择游戏");
        setLoadingMoreEnabled(false);
        initData();
        setOnItemClickListener((v, position, data) -> {
            if (data != null) {
                if (data instanceof GameInfoVo) {
                    GameInfoVo gameInfoVo = (GameInfoVo) data;
                    int targetXh_id;
                    if (TextUtils.isEmpty(gameid) || (!gameid.equals(gameInfoVo.getGameid()))) {
                        targetXh_id = -1;
                    } else {
                        targetXh_id = selectXh_id;
                    }
                    startForResult(TransactionChooseXhFragment.newInstance(String.valueOf(gameInfoVo.getGameid()),
                            gameInfoVo.getGamename(),
                            gameInfoVo.getGameicon(),
                            targetXh_id),
                            0x8871);
                    targetGame_type = String.valueOf(gameInfoVo.getGame_type());
                } else if (data instanceof TradeHeaderVo) {
                    showQuestionDialog(_mActivity);
                }
            }
        });
    }

    private String targetGame_type;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 0x8871 && resultCode == RESULT_OK) {
            setFragmentResult(RESULT_OK, data);
            data.putString("game_type", targetGame_type);
            pop();
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getTransactionGame(new OnBaseCallback<GameListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameListVo data) {
                    showSuccess();
                    clearData();
                    addData(new TradeHeaderVo("部分游戏暂时无法提供角色出售服务，详情查看 >"));
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                addAllData(data.getData());
                            } else {
                                addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                setListNoMore(true);
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                    notifyData();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showErrorTag1();
                }
            });
        }
    }


    private CustomDialog questionDialog;

    private void showQuestionDialog(Context mContext) {
        if (questionDialog == null) {
            questionDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_transaction_choose_game, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            TextView mTvConfirm = questionDialog.findViewById(R.id.tv_confirm);
            mTvConfirm.setOnClickListener(view -> {
                if (questionDialog != null && questionDialog.isShowing()) {
                    questionDialog.dismiss();
                }
            });
        }
        questionDialog.show();
    }
}
