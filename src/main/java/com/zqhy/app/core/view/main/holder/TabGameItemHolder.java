package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumListVo;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumVo;
import com.zqhy.app.core.data.model.mainpage.tabgame.TabGameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.main.MainGameListFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class TabGameItemHolder extends AbsItemHolder<TabGameInfoVo, TabGameItemHolder.ViewHolder> {

    private float density;

    public TabGameItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    int tabPosition = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_main_tab;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    BaseRecyclerAdapter mAdapter;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull TabGameInfoVo tabGameInfoVo) {
        if (tabGameInfoVo.getGame_type() == 4) {
            viewHolder.mLlGameMainTab.setVisibility(View.GONE);
        } else {
            viewHolder.mLlGameMainTab.setVisibility(View.VISIBLE);
            mAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(GameInfoVo.class, new GameNormalItemHolder(mContext))
                    .bind(GameAlbumVo.class, new GameAlbumItemHolder(mContext))
                    .bind(GameAlbumListVo.class, new GameAlbumListItemHolder(mContext))
                    .bind(GameFigurePushVo.class, new GameFigurePushItemHolder(mContext))
                    .bind(GameAppointmentVo.class, new GameAppointmentTabItemHolder(mContext))
                    .bind(EmptyDataVo.class, new EmptyItemHolder(mContext))
                    .build().setTag(R.id.tag_fragment, _mFragment);
            viewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            viewHolder.mRecyclerView.setAdapter(mAdapter);

            setTabGameInfoEvent(tabGameInfoVo);

            viewHolder.llMainTab1.setOnClickListener(view -> {
                tab1Click(viewHolder, tabGameInfoVo);
            });
            viewHolder.llMainTab2.setOnClickListener(view -> {
                tab2Click(viewHolder, tabGameInfoVo);
            });
            viewHolder.llMainTab3.setOnClickListener(view -> {
                tab3Click(viewHolder, tabGameInfoVo);
            });
            viewHolder.llMainTab4.setOnClickListener(view -> {
                tab4Click(viewHolder, tabGameInfoVo);
            });
            if (tabPosition == 1) {
                tab1Click(viewHolder, tabGameInfoVo);
            } else if (tabPosition == 2) {
                tab2Click(viewHolder, tabGameInfoVo);
            } else if (tabPosition == 3) {
                tab3Click(viewHolder, tabGameInfoVo);
            }
            hasMaxGameId(viewHolder, tabGameInfoVo);
        }
    }


    private void setTabGameInfoEvent(TabGameInfoVo tabGameInfoVo) {
        if (tabGameInfoVo == null) {
            return;
        }
        int game_type = tabGameInfoVo.getGame_type();

        List<GameInfoVo> remenList = tabGameInfoVo.getRemen_list();
        List<GameInfoVo> zuixinList = tabGameInfoVo.getZuixin_list();

        if (remenList != null) {
            int index = 1;
            for (GameInfoVo gameInfoVo : remenList) {
                gameInfoVo.setEventPosition(index);
                switch (game_type) {
                    case 1:
                        gameInfoVo.addEvent(5);
                        break;
                    case 2:
                        gameInfoVo.addEvent(24);
                        break;
                    case 3:
                        gameInfoVo.addEvent(42);
                        break;
                    default:
                        break;
                }
                if (gameInfoVo.getTp_type() == 1) {
                    switch (game_type) {
                        case 1:
                            gameInfoVo.addEvent(9);
                            break;
                        case 2:
                            gameInfoVo.addEvent(28);
                            break;
                        case 3:
                            gameInfoVo.addEvent(46);
                            break;
                        default:
                            break;
                    }
                }
                index++;
            }
        }
        if (zuixinList != null) {
            int index = 1;
            for (GameInfoVo gameInfoVo : zuixinList) {
                gameInfoVo.setEventPosition(index);
                switch (game_type) {
                    case 1:
                        gameInfoVo.addEvent(6);
                        break;
                    case 2:
                        gameInfoVo.addEvent(25);
                        break;
                    case 3:
                        gameInfoVo.addEvent(43);
                        break;
                    default:
                        break;
                }

                if (gameInfoVo.getTp_type() == 1) {
                    switch (game_type) {
                        case 1:
                            gameInfoVo.addEvent(8);
                            break;
                        case 2:
                            gameInfoVo.addEvent(27);
                            break;
                        case 3:
                            gameInfoVo.addEvent(45);
                            break;
                        default:
                            break;
                    }
                }
                index++;
            }

        }
    }


    /**
     * Tab1点击事件
     *
     * @param viewHolder
     * @param tabGameInfoVo
     */
    private void tab1Click(ViewHolder viewHolder, TabGameInfoVo tabGameInfoVo) {
        clearStatus(viewHolder);
        tabPosition = 1;
        tab1Status(viewHolder, true);
        tab2Status(viewHolder, false);
        tab3Status(viewHolder, false);
        setSwitchTab(viewHolder, tabGameInfoVo);
    }

    /**
     * Tab2点击事件
     *
     * @param viewHolder
     * @param tabGameInfoVo
     */
    private void tab2Click(ViewHolder viewHolder, TabGameInfoVo tabGameInfoVo) {
        clearStatus(viewHolder);
        tabPosition = 2;
        tab1Status(viewHolder, false);
        tab2Status(viewHolder, true);
        tab3Status(viewHolder, false);
        setSwitchTab(viewHolder, tabGameInfoVo);
    }

    /**
     * Tab3点击事件
     *
     * @param viewHolder
     * @param tabGameInfoVo
     */
    private void tab3Click(ViewHolder viewHolder, TabGameInfoVo tabGameInfoVo) {
        clearStatus(viewHolder);
        tabPosition = 3;
        tab1Status(viewHolder, false);
        tab2Status(viewHolder, false);
        tab3Status(viewHolder, true);
        setSwitchTab(viewHolder, tabGameInfoVo);
    }

    /**
     * Tab4点击事件
     *
     * @param viewHolder
     * @param tabGameInfoVo
     */
    private void tab4Click(ViewHolder viewHolder, TabGameInfoVo tabGameInfoVo) {
        clearStatus(viewHolder);
        tabPosition = 4;
        setSwitchTab(viewHolder, tabGameInfoVo);
    }

    private void tab1Status(ViewHolder viewHolder, boolean isChecked) {
        viewHolder.tvMainTabTitle1.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_0052ef : R.color.color_232323));
        viewHolder.tvMainTabSubTitle1.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_5191ff : R.color.color_858585));
        viewHolder.tvMainTabImage1.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
    }

    private void tab2Status(ViewHolder viewHolder, boolean isChecked) {
        viewHolder.tvMainTabTitle2.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_0052ef : R.color.color_232323));
        viewHolder.tvMainTabSubTitle2.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_5191ff : R.color.color_858585));
        viewHolder.tvMainTabImage2.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
    }

    private void tab3Status(ViewHolder viewHolder, boolean isChecked) {
        viewHolder.tvMainTabTitle3.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_0052ef : R.color.color_232323));
        viewHolder.tvMainTabSubTitle3.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_5191ff : R.color.color_858585));
        viewHolder.tvMainTabImage3.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
        viewHolder.mTvGameAppointmentTips.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    private void tab4Status(ViewHolder viewHolder, boolean isChecked) {
    }

    private void clearStatus(ViewHolder viewHolder) {
    }

    protected void setSwitchTab(ViewHolder viewHolder, TabGameInfoVo tabGameInfoVo) {
        if (tabGameInfoVo == null) {
            return;
        }
        switch (tabPosition) {
            case 1:
                if (tabGameInfoVo.getRemen_list() != null) {
                    fillGameList(tabGameInfoVo.getRemen_list());
                }
                break;
            case 2:
                if (tabGameInfoVo.getZuixin_list() != null) {
                    fillGameList(tabGameInfoVo.getZuixin_list());
                }
                break;
            case 3:
                //新游预约
                if (tabGameInfoVo.getGame_appointment_list() != null && !tabGameInfoVo.getGame_appointment_list().isEmpty()) {
                    fillGameList(tabGameInfoVo.getGame_appointment_list());
                } else {
                    mAdapter.clear();
                    mAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_datagame_apppointment));
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case 4:
                //赚金
                if (_mFragment != null) {
                    _mFragment.startFragment(TaskCenterFragment.newInstance());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 是否有新上架游戏
     */
    private void hasMaxGameId(ViewHolder viewHolder, TabGameInfoVo tabGameInfoVo) {
        if (tabGameInfoVo == null) {
            return;
        }
        try {
            int maxGameId = tabGameInfoVo.getMax_gameid();
            if (viewHolder.mViewRedDot != null) {
                SPUtils spUtils = new SPUtils(mContext, MainGameListFragment.SP_MAIN_PAGER);
                String key = MainGameListFragment.MAX_GAME_ID + "_" + tabGameInfoVo.getGame_type();
                int lastMaxGameId = spUtils.getInt(key, 0);
                if (maxGameId > lastMaxGameId) {
                    spUtils.putInt(key, maxGameId);
                    //show Red dot
                    viewHolder.mViewRedDot.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.mViewRedDot.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fillGameList(List<GameInfoVo> gameInfoBeanList) {
        mAdapter.clear();
        for (GameInfoVo gameInfoVo : gameInfoBeanList) {
            if (gameInfoVo.getTp_type() == 1) {
                GameFigurePushVo gameFigurePushVo = gameInfoVo.getGameFigurePushVo();
                mAdapter.addData(gameFigurePushVo);
            } else if (gameInfoVo.getTp_type() == 2) {
                mAdapter.addData(gameInfoVo.getGameAlbumVo());
            } else if (gameInfoVo.getTp_type() == 3) {
                mAdapter.addData(gameInfoVo.getGameAlbumListVo());
            } else if (gameInfoVo.getTp_type() == 5) {
                mAdapter.addData(gameInfoVo.getGameAppointmentVo());
            } else {
                mAdapter.addData(gameInfoVo);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    public class ViewHolder extends AbsHolder {

        private RecyclerView mRecyclerView;
        private LinearLayout mLlGameMainTab;
        private LinearLayout mLlGameSelectTitle;

        private LinearLayout   mLlTabDiscount1;
        private TextView       mRbTabDiscount1;
        private LinearLayout   mLlTabDiscount2;
        private TextView       mRbTabDiscount2;
        private RelativeLayout mLlTabDiscount3;
        private TextView       mRbTabDiscount3;
        private View           mViewRedDot;
        private LinearLayout   llMainTab1;
        private TextView       tvMainTabTitle1;
        private TextView       tvMainTabSubTitle1;
        private ImageView      tvMainTabImage1;
        private LinearLayout   llMainTab2;
        private TextView       tvMainTabTitle2;
        private TextView       tvMainTabSubTitle2;
        private ImageView      tvMainTabImage2;
        private LinearLayout   llMainTab3;
        private TextView       tvMainTabTitle3;
        private TextView       tvMainTabSubTitle3;
        private ImageView      tvMainTabImage3;
        private LinearLayout   llMainTab4;
        private TextView       tvMainTabTitle4;
        private TextView       tvMainTabSubTitle4;
        private ImageView      tvMainTabImage4;
        private TextView       mTvGameAppointmentTips;

        public ViewHolder(View view) {
            super(view);
            mLlGameMainTab = findViewById(R.id.ll_game_main_tab);
            mLlGameSelectTitle = findViewById(R.id.ll_game_select_title);

            mLlTabDiscount1 = findViewById(R.id.ll_tab_discount_1);
            mRbTabDiscount1 = findViewById(R.id.rb_tab_discount_1);
            mLlTabDiscount2 = findViewById(R.id.ll_tab_discount_2);
            mRbTabDiscount2 = findViewById(R.id.rb_tab_discount_2);
            mLlTabDiscount3 = findViewById(R.id.ll_tab_discount_3);
            mRbTabDiscount3 = findViewById(R.id.rb_tab_discount_3);
            mViewRedDot = findViewById(R.id.view_red_dot);
            mRecyclerView = findViewById(R.id.recycler_view);
            llMainTab1 = findViewById(R.id.ll_main_tab_1);
            tvMainTabTitle1 = findViewById(R.id.tv_main_tab_title_1);
            tvMainTabSubTitle1 = findViewById(R.id.tv_main_tab_sub_title_1);
            tvMainTabImage1 = findViewById(R.id.tv_main_tab_image_1);
            llMainTab2 = findViewById(R.id.ll_main_tab_2);
            tvMainTabTitle2 = findViewById(R.id.tv_main_tab_title_2);
            tvMainTabSubTitle2 = findViewById(R.id.tv_main_tab_sub_title_2);
            tvMainTabImage2 = findViewById(R.id.tv_main_tab_image_2);
            llMainTab3 = findViewById(R.id.ll_main_tab_3);
            tvMainTabTitle3 = findViewById(R.id.tv_main_tab_title_3);
            tvMainTabSubTitle3 = findViewById(R.id.tv_main_tab_sub_title_3);
            tvMainTabImage3 = findViewById(R.id.tv_main_tab_image_3);
            llMainTab4 = findViewById(R.id.ll_main_tab_4);
            tvMainTabTitle4 = findViewById(R.id.tv_main_tab_title_4);
            tvMainTabSubTitle4 = findViewById(R.id.tv_main_tab_sub_title_4);
            tvMainTabImage4 = findViewById(R.id.tv_main_tab_image_4);
            mTvGameAppointmentTips = findViewById(R.id.tv_game_appointment_tips);

        }
    }


    /**
     * 设置RadioButton样式
     *
     * @param targetRadioButton
     * @param normalColor
     * @param selectedColor
     * @param drawableDefault
     * @param drawableChecked
     */
    public void setTabButtonBackgroundSelector(RadioButton targetRadioButton,
                                               int normalColor, int selectedColor,
                                               Drawable drawableDefault, Drawable drawableChecked) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_checked},
                drawableChecked);
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked},
                drawableDefault);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected},
                drawableChecked);
        stateListDrawable.addState(new int[]{-android.R.attr.state_selected},
                drawableDefault);

        targetRadioButton.setBackground(stateListDrawable);

        ColorStateList tabColorState = new ColorStateList(new int[][]{{android.R.attr.state_checked, android.R.attr.state_enabled}, {}},
                new int[]{selectedColor, normalColor});
        targetRadioButton.setTextColor(tabColorState);

    }
}
