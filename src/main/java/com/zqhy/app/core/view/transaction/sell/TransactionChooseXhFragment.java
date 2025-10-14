package com.zqhy.app.core.view.transaction.sell;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.transaction.GameXhInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.transaction.holder.TradeChooseGameXhItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 */
public class TransactionChooseXhFragment extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    public static TransactionChooseXhFragment newInstance(String gameid, String gamename, String gameicon) {
        return newInstance(gameid, gamename, gameicon, -1);
    }

    public static TransactionChooseXhFragment newInstance(String gameid, String gamename, String gameicon, int selectedItemId) {
        TransactionChooseXhFragment fragment = new TransactionChooseXhFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameid", gameid);
        bundle.putString("gamename", gamename);
        bundle.putString("gameicon", gameicon);
        bundle.putInt("selectedItemId", selectedItemId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private String gameid, gamename, gameicon;
    private int xh_id = -1;
    private int mSelectedItemId = -1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_choose_xh;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getString("gameid");
            gamename = getArguments().getString("gamename");
            gameicon = getArguments().getString("gameicon");
            xh_id = getArguments().getInt("selectedItemId", -1);
        }
        super.initView(state);
        showSuccess();
        mSelectedItemId = xh_id;

        initActionBackBarAndTitle("我要卖号");
        bindViews();

        initList();
        initData();
    }


    BaseRecyclerAdapter xhAccountSelectAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        xhAccountSelectAdapter = new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameXhInfoVo.DataBean.class, new TradeChooseGameXhItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(xhAccountSelectAdapter);

        xhAccountSelectAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof GameXhInfoVo.DataBean) {
                GameXhInfoVo.DataBean gameXhInfoVo = (GameXhInfoVo.DataBean) data;
                if (isSelectedItem(gameXhInfoVo.getId())) {
                    releaseSelected();
                } else {
                    selectById(gameXhInfoVo.getId());
                }
                setBtnConfirmStatus();
            }
        });

    }

    public boolean isSelectedItem(int id) {
        return mSelectedItemId == id;
    }

    public void releaseSelected() {
        mSelectedItemId = -1;
        xhAccountSelectAdapter.notifyDataSetChanged();
    }

    public void selectById(int id) {
        mSelectedItemId = id;
        xhAccountSelectAdapter.notifyDataSetChanged();
    }


    private ImageView mIvGameImage;
    private TextView mTvGameName;
    private RecyclerView mRecyclerView;
    private Button mBtnConfirm;

    private void bindViews() {
        mIvGameImage = findViewById(R.id.iv_game_image);
        mTvGameName = findViewById(R.id.tv_game_name);
        mRecyclerView = findViewById(R.id.recyclerView);
        mBtnConfirm = findViewById(R.id.btn_confirm);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(20 * density);
        gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        mBtnConfirm.setBackground(gd2);

        mBtnConfirm.setOnClickListener(this);

        mTvGameName.setText(gamename);
        GlideUtils.loadRoundImage(_mActivity, gameicon, mIvGameImage, R.mipmap.ic_placeholder);
        setBtnConfirmStatus();
    }


    private void setBtnConfirmStatus() {
        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(20 * density);
        gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_cccccc));
        if (xhAccountSelectAdapter != null) {
            GameXhInfoVo.DataBean xhInfoBean = getSelectData();
            if (xhInfoBean != null) {
                gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
            }
        }
        mBtnConfirm.setBackground(gd2);
    }

    private GameXhInfoVo.DataBean getSelectData() {
        if (xhAccountSelectAdapter != null) {
            List<Object> list = xhAccountSelectAdapter.getItems();

            for (Object item : list) {
                if (item instanceof GameXhInfoVo.DataBean) {
                    GameXhInfoVo.DataBean gameXhInfo = (GameXhInfoVo.DataBean) item;
                    if (gameXhInfo.getId() == mSelectedItemId) {
                        return gameXhInfo;
                    }
                }
            }
        }
        return null;
    }


    private void initData() {
        if (mViewModel != null) {
            mViewModel.getTransactionGameXh(gameid, new OnBaseCallback<GameXhInfoVo>() {

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }


                @Override
                public void onSuccess(GameXhInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            xhAccountSelectAdapter.clear();
                            if (data.getData() != null) {
                                for (int i = 0; i < data.getData().size(); i++) {
                                    data.getData().get(i).setId(i);
                                }
                                xhAccountSelectAdapter.addAllData(data.getData());
                            } else {
                                xhAccountSelectAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (xhAccountSelectAdapter != null) {
                    GameXhInfoVo.DataBean gameXhInfoVo = getSelectData();
                    if (gameXhInfoVo != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("gameid", gameid);
                        bundle.putString("gamename", gamename);
                        bundle.putString("gameicon", gameicon);

                        bundle.putString("xh_name", gameXhInfoVo.getXh_username());
                        bundle.putString("xh_nickname", gameXhInfoVo.getXh_showname());

                        bundle.putInt("xh_id", gameXhInfoVo.getId());

                        setFragmentResult(RESULT_OK, bundle);
                        pop();
                    }
                }
                break;
            default:
                break;
        }
    }
}
