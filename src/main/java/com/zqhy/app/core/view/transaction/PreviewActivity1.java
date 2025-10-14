package com.zqhy.app.core.view.transaction;

import static android.animation.ObjectAnimator.ofFloat;
import static com.donkingliang.imageselector.R.id.btn_confirm;
import static com.donkingliang.imageselector.R.id.rl_bottom_bar;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.donkingliang.imageselector.adapter.ImagePagerAdapter;
import com.donkingliang.imageselector.constant.Constants;
import com.donkingliang.imageselector.entry.Image;
import com.donkingliang.imageselector.progress.DownloadListener;
import com.donkingliang.imageselector.view.CustomDialog;
import com.donkingliang.imageselector.view.MyViewPager;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;

public class PreviewActivity1 extends BaseActivity<TransactionViewModel> {
    private MyViewPager vpImage;
    private TextView tvIndicator;
    private TextView tv_indicator_1;
    private TextView tvConfirm;
    private FrameLayout btnConfirm;
    private TextView tvSelect;
    private ImageView close;
    private ImageView download;
    private RelativeLayout rlTopBar;
    private RelativeLayout rlBottomBar;

    //tempImages和tempSelectImages用于图片列表数据的页面传输。
    //之所以不要Intent传输这两个图片列表，因为要保证两位页面操作的是同一个列表数据，同时可以避免数据量大时，
    // 用Intent传输发生的错误问题。
    private static ArrayList<Image> tempImages;
    private static ArrayList<Image> tempSelectImages;

    private ArrayList<Image> mImages;
    private ArrayList<Image> mSelectImages;
    private boolean isShowBar = true;
    private boolean isConfirm = false;
    private boolean isSingle;
    private int mMaxCount;
    private boolean isOnlyPreview = false;
    private boolean canSave = false;
    private boolean clickOnceBack = false;

    int mPosition = 0;
    private BitmapDrawable mSelectDrawable;
    private BitmapDrawable mUnSelectDrawable;

    public static void openActivity(Activity activity, ArrayList<Image> images, boolean isOnlyPreview, int position, Boolean clickOnceBack, String gid, String gameid) {
        tempImages = images;
        Intent intent = new Intent(activity, PreviewActivity1.class);
        intent.putExtra(Constants.IS_ONLY_PREVIEW, isOnlyPreview);
        intent.putExtra(Constants.POSITION, position);
        intent.putExtra(Constants.Click_ONCE_BACK, clickOnceBack);
        intent.putExtra("gid", gid);
        intent.putExtra("gameid", gameid);
        activity.startActivityForResult(intent, Constants.RESULT_CODE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_1;
    }

    @Override
    protected Object getStateEventKey() {
        return null;
    }

    private String gid;
    private String gameid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_1);

        setStatusBarVisible(true);
        mImages = tempImages;
        tempImages = null;
        mSelectImages = tempSelectImages;
        tempSelectImages = null;

        Intent intent = getIntent();
        mMaxCount = intent.getIntExtra(Constants.MAX_SELECT_COUNT, 0);
        isSingle = intent.getBooleanExtra(Constants.IS_SINGLE, false);
        isOnlyPreview = intent.getBooleanExtra(Constants.IS_ONLY_PREVIEW, false);
        canSave = intent.getBooleanExtra(Constants.CAN_SAVE, false);
        clickOnceBack = intent.getBooleanExtra(Constants.Click_ONCE_BACK, false);
        gid = intent.getStringExtra("gid");
        gameid = intent.getStringExtra("gameid");

        Resources resources = getResources();
        Bitmap selectBitmap = BitmapFactory.decodeResource(resources, com.donkingliang.imageselector.R.drawable.icon_image_select);
        mSelectDrawable = new BitmapDrawable(resources, selectBitmap);
        mSelectDrawable.setBounds(0, 0, selectBitmap.getWidth(), selectBitmap.getHeight());

        Bitmap unSelectBitmap = BitmapFactory.decodeResource(resources, com.donkingliang.imageselector.R.drawable.icon_image_un_select);
        mUnSelectDrawable = new BitmapDrawable(resources, unSelectBitmap);
        mUnSelectDrawable.setBounds(0, 0, unSelectBitmap.getWidth(), unSelectBitmap.getHeight());

        setStatusBarColor();
        initView();
        initListener();
        initViewPager();

        try{
            tvIndicator.setText(1 + "/" + mImages.size());
            tv_indicator_1.setText(1 + "/" + mImages.size());
            changeSelect(mImages.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }
        vpImage.setCurrentItem(intent.getIntExtra(Constants.POSITION, 0));

        if (clickOnceBack) {
            setStatusBarVisible(false);
            rlTopBar.setVisibility(View.GONE);
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 0X00000011;
    private void initView() {
        vpImage = (MyViewPager) findViewById(com.donkingliang.imageselector.R.id.vp_image);
        tvIndicator = (TextView) findViewById(com.donkingliang.imageselector.R.id.tv_indicator);
        tv_indicator_1 = (TextView) findViewById(com.donkingliang.imageselector.R.id.tv_indicator_1);
        tvConfirm = (TextView) findViewById(com.donkingliang.imageselector.R.id.tv_confirm);
        btnConfirm = (FrameLayout) findViewById(btn_confirm);
        tvSelect = (TextView) findViewById(com.donkingliang.imageselector.R.id.tv_select);
        rlTopBar = (RelativeLayout) findViewById(com.donkingliang.imageselector.R.id.rl_top_bar);
        rlBottomBar = (RelativeLayout) findViewById(rl_bottom_bar);

        close =  findViewById(com.donkingliang.imageselector.R.id.close);
        download =  findViewById(com.donkingliang.imageselector.R.id.download);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rlTopBar.getLayoutParams();
        lp.topMargin = getStatusBarHeight(this);
        rlTopBar.setLayoutParams(lp);

        if (isOnlyPreview) {
            btnConfirm.setVisibility(View.GONE);
            rlBottomBar.setVisibility(View.GONE);
        }

        if (canSave){
            close.setVisibility(View.VISIBLE);
            //tv_indicator_1.setVisibility(View.VISIBLE);
            download.setVisibility(View.VISIBLE);

            close.setOnClickListener(v -> {
                finish();
            });

            download.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        showReadPermissionsTipDialog();
                    } else {
                        if (downloadListener!=null){
                            downloadListener.save(mPosition);
                        }
                    }
                } else {
                    // 不需要运行时权限，直接保存图片
                    if (downloadListener!=null){
                        downloadListener.save(mPosition);
                    }
                }

            });
        }

        findViewById(R.id.tv_action).setOnClickListener(v -> {
            FragmentHolderActivity.startFragmentInActivity(PreviewActivity1.this, TransactionGoodDetailFragment.newInstance(String.valueOf(gid), String.valueOf(gameid)));
            finish();
        });
    }
    DownloadListener downloadListener;

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @SuppressLint("WrongConstant")
    public void showReadPermissionsTipDialog(){
        final CustomDialog authorityDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(com.donkingliang.imageselector.R.layout.dialog_image_read_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        authorityDialog.setCancelable(false);
        authorityDialog.setCanceledOnTouchOutside(false);

        TextView mTvCancel = authorityDialog.findViewById(com.donkingliang.imageselector.R.id.tv_cancel);
        TextView mTvConfirm = authorityDialog.findViewById(com.donkingliang.imageselector.R.id.tv_confirm);

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                finish();
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                ActivityCompat.requestPermissions(PreviewActivity1.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        });
        authorityDialog.show();
    }
    private void initListener() {
        findViewById(com.donkingliang.imageselector.R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConfirm = true;
                finish();
            }
        });
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSelect();
            }
        });
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, mImages);
        vpImage.setAdapter(adapter);
        adapter.setOnItemClickListener(new ImagePagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Image image) {
                if (clickOnceBack) {
                    finish();
                } else {
                    if (isShowBar) {
                        hideBar();
                    } else {
                        showBar();
                    }
                }
            }
        });
        vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvIndicator.setText(position + 1 + "/" + mImages.size());
                tv_indicator_1.setText(position + 1 + "/" + mImages.size());
                changeSelect(mImages.get(position));
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#373c3d"));
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 显示和隐藏状态栏
     *
     * @param show
     */
    private void setStatusBarVisible(boolean show) {
        if (show) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    /**
     * 显示头部和尾部栏
     */
    private void showBar() {
        isShowBar = true;
        setStatusBarVisible(true);
        //添加延时，保证StatusBar完全显示后再进行动画。
        rlTopBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rlTopBar != null) {
                    ObjectAnimator animator = ofFloat(rlTopBar, "translationY",
                            rlTopBar.getTranslationY(), 0).setDuration(300);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            if (rlTopBar != null) {
                                rlTopBar.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    animator.start();
                    if (!isOnlyPreview) {
                        ofFloat(rlBottomBar, "translationY", rlBottomBar.getTranslationY(), 0)
                                .setDuration(300).start();
                    }
                }
            }
        }, 100);
    }

    /**
     * 隐藏头部和尾部栏
     */
    private void hideBar() {
        isShowBar = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlTopBar, "translationY",
                0, -rlTopBar.getHeight()).setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (rlTopBar != null) {
                    rlTopBar.setVisibility(View.GONE);
                    //添加延时，保证rlTopBar完全隐藏后再隐藏StatusBar。
                    rlTopBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setStatusBarVisible(false);
                        }
                    }, 5);
                }
            }
        });
        animator.start();
        if (!isOnlyPreview) {
            ofFloat(rlBottomBar, "translationY", 0, rlBottomBar.getHeight())
                    .setDuration(300).start();
        }
    }

    private void clickSelect() {
        int position = vpImage.getCurrentItem();
        if (mImages != null && mImages.size() > position) {
            Image image = mImages.get(position);
            if (mSelectImages.contains(image)) {
                mSelectImages.remove(image);
            } else if (isSingle) {
                mSelectImages.clear();
                mSelectImages.add(image);
            } else if (mMaxCount <= 0 || mSelectImages.size() < mMaxCount) {
                mSelectImages.add(image);
            }
            changeSelect(image);
        }
    }

    private void changeSelect(Image image) {
        if (isOnlyPreview) return;
        tvSelect.setCompoundDrawables(mSelectImages.contains(image) ?
                mSelectDrawable : mUnSelectDrawable, null, null, null);
        setSelectImageCount(mSelectImages.size());
    }

    private void setSelectImageCount(int count) {
        if (count == 0) {
            btnConfirm.setEnabled(false);
            tvConfirm.setText("确定");
        } else {
            btnConfirm.setEnabled(true);
            if (isSingle) {
                tvConfirm.setText("确定");
            } else if (mMaxCount > 0) {
                tvConfirm.setText("确定(" + count + "/" + mMaxCount + ")");
            } else {
                tvConfirm.setText("确定(" + count + ")");
            }
        }
    }

    @Override
    public void finish() {
        //Activity关闭时，通过Intent把用户的操作(确定/返回)传给ImageSelectActivity。
        Intent intent = new Intent();
        intent.putExtra(Constants.IS_CONFIRM, isConfirm);
        setResult(Constants.RESULT_CODE, intent);
        super.finish();
    }
}
