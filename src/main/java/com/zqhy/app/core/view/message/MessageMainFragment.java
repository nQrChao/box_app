package com.zqhy.app.core.view.message;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.adapter.MessageAdapter;
import com.zqhy.app.adapter.OnItemClickListener;
import com.zqhy.app.adapter.ViewPagerAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.message.MessageBannerVo;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.message.TabMessageVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.message.MessageViewModel;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.glide.GlideRoundTransform;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class MessageMainFragment extends BaseFragment<MessageViewModel> {

    public static final String SP_MESSAGE = "SP_MESSAGE";

    public static final String KEY_HAS_NEW_COMMENT_MESSAGE = "KEY_HAS_NEW_COMMENT_MESSAGE";

    public static final String TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME = "TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME";

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_MESSAGE_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message_main;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "消息页";
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getAdBannerData();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        bindView();
        initActionBackBarAndTitle("我的消息");
    }

    private XRecyclerView mXRecyclerView;
    private View          mHeaderView;

    private View         mHeaderRootView;
    private ViewPager    mViewpager;
    private LinearLayout mIndicator;

    private EditText       mEtMessageId;
    private Button         mBtnDeleteMessageId;
    private Button         mBtnUnreadMessageId;
    private TextView       mTvReadAllMessage;
    private MessageAdapter mAdapter;

    private boolean isClickReadAllMessage = false;

    private void bindView() {
        mXRecyclerView = findViewById(R.id.xRecyclerView);
        mEtMessageId = findViewById(R.id.et_message_id);
        mBtnDeleteMessageId = findViewById(R.id.btn_delete_message_id);
        mBtnUnreadMessageId = findViewById(R.id.btn_unread_message_id);
        mTvReadAllMessage = findViewById(R.id.tv_read_all_message);

        initHeaderView();
        initList();
        initData();

        mBtnDeleteMessageId.setOnClickListener(view -> {
            int rowId = Integer.parseInt(mEtMessageId.getText().toString().trim());
            MessageDbInstance.getInstance().deleteNewMessage(rowId);
        });

        mBtnUnreadMessageId.setOnClickListener(view -> {
            int rowId = Integer.parseInt(mEtMessageId.getText().toString().trim());
            MessageDbInstance.getInstance().readMessage(rowId);
        });

        mTvReadAllMessage.setOnClickListener(view -> {
            if (!isClickReadAllMessage) {
                Runnable runnable1 = () -> {
                    MessageDbInstance.getInstance().readAllMessage();
                };
                new Thread(runnable1).start();
                refreshUnReadCount();
                isClickReadAllMessage = true;
            }
        });
    }

    private void initData() {
        //广告
        getAdBannerData();

        //message_type = 1
        getKefuMessageData();

        //message_type = 4
        getDynamicGameMessageData();
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mXRecyclerView.setLayoutManager(layoutManager);


        List<TabMessageVo> tabMessageVoList = getTabMessageList();
        mAdapter = new MessageAdapter(_mActivity, tabMessageVoList);
        mXRecyclerView.setAdapter(mAdapter);

        mXRecyclerView.addHeaderView(mHeaderView);

        mXRecyclerView.setPullRefreshEnabled(true);
        mXRecyclerView.setLoadingMoreEnabled(false);

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getAdBannerData();
            }

            @Override
            public void onLoadMore() {

            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Object data) {
                TabMessageVo mTab = tabMessageVoList.get(position);
                startForResult(MessageListFragment.newInstance(mTab.getTabId()), 0x288);

                if (mTab.getTabId() == 2) {
                    SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
                    spUtils.remove(KEY_HAS_NEW_COMMENT_MESSAGE);
                }
            }
        });

        refreshUnReadCount();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x288) {
            if (data != null) {
                int message_type = data.getInt("message_type");
                if (message_type == 1) {
                    refreshTab1UnReadCount();
                } else if (message_type == 2) {
                    refreshTab2UnReadCount();
                } else if (message_type == 3) {
                    refreshTab3UnReadCount();
                } else if (message_type == 4) {
                    refreshTab4UnReadCount();
                }
            }
        }
    }


    ViewPagerAdapter mPagerAdapter;

    private void setViewPager(List<View> viewList) {
        if (viewList == null) {
            return;
        }
        mPagerAdapter = new ViewPagerAdapter(viewList);
        mViewpager.setAdapter(mPagerAdapter);

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        refreshIndicators(0);
        mViewpager.setOffscreenPageLimit(viewList.size());
        setHeaderViewVisibility(viewList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    private void setHeaderViewVisibility(int visibility) {
        mHeaderRootView.setVisibility(visibility);
        mViewpager.setVisibility(visibility);
        mIndicator.setVisibility(visibility);
    }

    private void refreshIndicators(int index) {
        int count = mPagerAdapter.getCount();
        if (mIndicator != null && count > 1) {
            int defaultBackgroundColor = Color.parseColor("#ffcccccc");
            int selectedBackgroundColor = Color.parseColor("#ffff8f19");

            float cornerRadius = 3 * density;

            int defaultWidth = (int) (3 * density);
            int selectedWidth = (int) (13 * density);

            int height = (int) (3 * density);


            mIndicator.removeAllViews();

            for (int i = 0; i < count; i++) {
                View view = new View(_mActivity);
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(cornerRadius);

                int viewHeight = height;
                int viewWidth = 0;
                int marginRight = (int) (3 * density);

                if (i == count - 1) {
                    marginRight = 0;
                }

                if (i == index) {
                    gd.setColor(selectedBackgroundColor);
                    viewWidth = selectedWidth;

                } else {
                    gd.setColor(defaultBackgroundColor);
                    viewWidth = defaultWidth;
                }
                view.setBackground(gd);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewWidth, viewHeight);
                params.setMargins(0, 0, marginRight, 0);
                int pageIndex = i;
                view.setOnClickListener(view1 -> {
                    mViewpager.setCurrentItem(pageIndex);
                });
                mIndicator.addView(view, params);
            }
        }
    }

    private View createLabelPage(MessageBannerVo.BannerListVo.ADINFO adinfo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_message_banner_page, null);

        ImageView mIvPageImage = itemView.findViewById(R.id.iv_page_image);
        TextView mTvPageTitle = itemView.findViewById(R.id.tv_page_title);

        GlideApp.with(_mActivity)
                .asBitmap()
                .load(adinfo.getAd_pic())
                .placeholder(R.mipmap.ic_placeholder)
                .transform(new GlideRoundTransform(_mActivity, 5))
                .centerCrop()
                .into(mIvPageImage);
        mTvPageTitle.setText(adinfo.getTitle());

        itemView.setOnClickListener(view -> {
            AppJumpAction jumpAction = new AppJumpAction(_mActivity);
            jumpAction.jumpAction(adinfo);
        });

        return itemView;
    }

    private void refreshUnReadCount() {
        refreshTab1UnReadCount();
        refreshTab2UnReadCount();
        refreshTab3UnReadCount();
        refreshTab4UnReadCount();
    }

    private void refreshTab1UnReadCount() {
        //message_type = 1
        Runnable runnable1 = () -> {
            int unReadCount = MessageDbInstance.getInstance().getUnReadMessageCount(1);
            _mActivity.runOnUiThread(() -> {
                TabMessageVo tabMessageVo = getTabMessageVoById(1);
                if (tabMessageVo != null) {
                    tabMessageVo.setUnReadCount(unReadCount);
                    mAdapter.notifyDataSetChanged();
                }
            });
        };
        new Thread(runnable1).start();
    }

    private void refreshTab2UnReadCount() {
        //message_type = 2
        SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
        boolean HAS_NEW_COMMENT_MESSAGE = spUtils.getBoolean(KEY_HAS_NEW_COMMENT_MESSAGE, false);

        TabMessageVo tabMessageVo = getTabMessageVoById(2);
        if (tabMessageVo != null) {
            if (HAS_NEW_COMMENT_MESSAGE) {
                tabMessageVo.setUnReadCount(1);
            } else {
                tabMessageVo.setUnReadCount(0);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void refreshTab3UnReadCount() {
        //message_type = 3
        Runnable runnable3 = () -> {
            int unReadCount = MessageDbInstance.getInstance().getUnReadMessageCount(3);
            _mActivity.runOnUiThread(() -> {
                TabMessageVo tabMessageVo = getTabMessageVoById(3);
                if (tabMessageVo != null) {
                    tabMessageVo.setUnReadCount(unReadCount);
                    mAdapter.notifyDataSetChanged();
                }
            });
        };
        new Thread(runnable3).start();
    }

    private void refreshTab4UnReadCount() {
        //message_type = 4
        Runnable runnable4 = () -> {
            int unReadCount = MessageDbInstance.getInstance().getUnReadMessageCount(4);
            _mActivity.runOnUiThread(() -> {
                TabMessageVo tabMessageVo = getTabMessageVoById(4);
                if (tabMessageVo != null) {
                    tabMessageVo.setUnReadCount(unReadCount);
                    mAdapter.notifyDataSetChanged();
                }
            });
        };
        new Thread(runnable4).start();
    }

    List<TabMessageVo> tabMessageVoList = new ArrayList<>();

    private List<TabMessageVo> getTabMessageList() {
        tabMessageVoList.clear();

        TabMessageVo tab1 = new TabMessageVo();
        tab1.setTabId(1);
        tab1.setIconRes(R.mipmap.ic_message_tab_common);
        tab1.setTitle("客服消息");
        tab1.setSubTitle("客服推送消息（返利码/反馈回复等）");
        tab1.setIsShowUnReadCount(1);
        tabMessageVoList.add(tab1);

        TabMessageVo tab2 = new TabMessageVo();
        tab2.setTabId(2);
        tab2.setIconRes(R.mipmap.ic_message_tab_comment);
        tab2.setTitle("互动消息");
        tab2.setSubTitle("和小伙伴们互动起来！");
        tabMessageVoList.add(tab2);

        TabMessageVo tab3 = new TabMessageVo();
        tab3.setTabId(3);
        tab3.setIconRes(R.mipmap.ic_message_tab_system);
        tab3.setTitle("系统消息");
        tab3.setSubTitle("通知类信息查看！");
        tabMessageVoList.add(tab3);

        TabMessageVo tab4 = new TabMessageVo();
        tab4.setTabId(4);
        tab4.setIconRes(R.mipmap.ic_message_tab_game);
        tab4.setTitle("游戏动态");
        tab4.setSubTitle("收藏的游戏动态，可在此处查看~");
        tabMessageVoList.add(tab4);

        return tabMessageVoList;
    }

    private TabMessageVo getTabMessageVoById(int id) {
        if (tabMessageVoList == null) {
            return null;
        }
        for (TabMessageVo tabMessageVo : tabMessageVoList) {
            if (id == tabMessageVo.getTabId()) {
                return tabMessageVo;
            }
        }

        return null;
    }

    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_main_message, null);

        mHeaderRootView = mHeaderView.findViewById(R.id.rootView);
        mViewpager = mHeaderView.findViewById(R.id.viewpager);
        mIndicator = mHeaderView.findViewById(R.id.indicator);

    }

    private void saveMessageToDb(int type, MessageListVo messageListVo) {
        Runnable runnable = () -> {
            for (MessageInfoVo messageInfoVo : messageListVo.getData()) {
                MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                MessageDbInstance.getInstance().saveMessageVo(messageVo);
            }
            _mActivity.runOnUiThread(() -> {
                if (type == 1) {
                    refreshTab1UnReadCount();
                } else if (type == 4) {
                    refreshTab4UnReadCount();
                }
            });
        };
        new Thread(runnable).start();
    }

    /**
     * banner数据
     */
    private void getAdBannerData() {
        if (mViewModel != null) {
            mViewModel.getAdBannerData(new OnBaseCallback<MessageBannerVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mXRecyclerView != null) {
                        mXRecyclerView.refreshComplete();
                    }
                }

                @Override
                public void onSuccess(MessageBannerVo messageBannerVo) {
                    if (messageBannerVo != null) {
                        if (messageBannerVo.isStateOK()) {
                            if (messageBannerVo.getData() != null && messageBannerVo.getData().getMsg_ad_list() != null) {
                                List<View> viewList = new ArrayList<>();
                                for (MessageBannerVo.BannerListVo.ADINFO adinfo : messageBannerVo.getData().getMsg_ad_list()) {
                                    viewList.add(createLabelPage(adinfo));
                                }
                                setViewPager(viewList);
                            } else {
                                setHeaderViewVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 客服消息数据
     */
    private void getKefuMessageData() {
        if (mViewModel != null) {
            Runnable runnable1 = () -> {
                MessageVo messageVo = MessageDbInstance.getInstance().getMaxIdMessageVo(1);
                _mActivity.runOnUiThread(() -> {
                    int maxID = 0;
                    if (messageVo != null) {
                        maxID = messageVo.getMessage_id();
                    }
                    mViewModel.getKefuMessageData(maxID, new OnBaseCallback<MessageListVo>() {
                        @Override
                        public void onSuccess(MessageListVo messageListVo) {
                            if (messageListVo != null) {
                                if (messageListVo.isStateOK() && messageListVo.getData() != null) {
                                    saveMessageToDb(1, messageListVo);
                                }
                            }
                        }
                    });
                });
            };
            new Thread(runnable1).start();
        }
    }

    /**
     * 游戏动态数据
     */
    private void getDynamicGameMessageData() {
        if (mViewModel != null) {
            SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
            long logTime = spUtils.getLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, 0);

            mViewModel.getDynamicGameMessageData(logTime, new OnBaseCallback<MessageListVo>() {
                @Override
                public void onSuccess(MessageListVo messageListVo) {
                    if (messageListVo != null) {
                        if (messageListVo.isStateOK() && messageListVo.getData() != null) {
                            saveMessageToDb(4, messageListVo);
                        }
                    }
                    SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
                    spUtils.putLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, System.currentTimeMillis() / 1000);
                }
            });
        }
    }
}
