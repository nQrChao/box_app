package com.zqhy.app.core.view.login;

import static com.chaoji.mod.game.ModManager.LOGIN_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.login.event.AuthLoginEvent;
import com.zqhy.app.core.vm.login.LoginViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.utils.PermissionHelper;
import com.zqhy.app.utils.sp.SPUtils;
import com.zqhy.app.widget.scroll.ScrollLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/2
 */

public class LoginFragment extends BaseFragment<LoginViewModel> implements View.OnClickListener {

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ts_login;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("");
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setTitleBottomLine(View.GONE);
        bindView();
    }

    private EditText mEtUsername;
    private ImageView mIvPhoneDelete;
    private EditText mEtPassword;
    private ImageView mIvPwdDelete;
    private ImageView mBtnHistoryAccount;
    private CheckBox mCbPasswordVisiblePhone;
    private Button mBtnLogin;
    private TextView mBtnRegister;
    private TextView mBtnForgetPassword;
    private RecyclerView mMRecyclerView;

    private TextView mTvAutoLogin;
    private TextView mTvLine;
    private TextView mTvVerifyLogin;
    private ImageView mIvAgree;
    private TextView mTvAgree;

    private boolean isChecked = false;

    @SuppressLint("WrongViewCast")
    private void bindView() {
        mEtUsername = findViewById(R.id.et_username);
        mIvPhoneDelete = findViewById(R.id.iv_phone_delete);
        mEtPassword = findViewById(R.id.et_password);
        mIvPwdDelete = findViewById(R.id.iv_pwd_delete);
        mBtnHistoryAccount = findViewById(R.id.btn_history_account);
        mCbPasswordVisiblePhone = findViewById(R.id.cb_password_visible_phone);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnForgetPassword = findViewById(R.id.btn_forget_password);

        mMRecyclerView = findViewById(R.id.mRecyclerView);

        mTvAutoLogin = findViewById(R.id.tv_auto_login);
        mTvLine = findViewById(R.id.tv_line);
        mTvVerifyLogin = findViewById(R.id.tv_verify_login);
        mIvAgree = findViewById(R.id.iv_agree);
        mTvAgree = findViewById(R.id.tv_agree);

        if (AuthLoginEvent.instance().isSupport) {//支持终端认证
            mTvAutoLogin.setVisibility(View.VISIBLE);
            mTvLine.setVisibility(View.VISIBLE);
        } else {
            mTvAutoLogin.setVisibility(View.GONE);
            mTvLine.setVisibility(View.GONE);
        }

        mEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtUsername.getText().toString().trim())) {
                    mIvPhoneDelete.setVisibility(View.GONE);
                } else {
                    mIvPhoneDelete.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mIvPhoneDelete.getLayoutParams();
                    if (mBtnHistoryAccount.getVisibility() == View.GONE) {
                        layoutParams.setMargins(0, 0, ScreenUtil.dp2px(_mActivity, 10), 0);
                    } else {
                        layoutParams.setMargins(0, 0, 0, 0);
                    }
                    mIvPhoneDelete.setLayoutParams(layoutParams);
                }
            }
        });

        findViewById(R.id.iv_login_back).setOnClickListener(v -> {
            _mActivity.finish();
        });
        mIvPhoneDelete.setOnClickListener(v -> {
            mEtUsername.setText("");
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtPassword.getText().toString().trim())) {
                    mIvPwdDelete.setVisibility(View.GONE);
                } else {
                    mIvPwdDelete.setVisibility(View.VISIBLE);
                }
            }
        });

        mIvPwdDelete.setOnClickListener(v -> {
            mEtPassword.setText("");
        });

        mTvAutoLogin.setOnClickListener(view -> {
            AuthLoginEvent.instance().oneKMeyLogin(_mActivity, new AuthLoginEvent.OneKeyLogin() {

                @Override
                public void onSuccess(TokenRet dataBean) {
                    Logs.d("OneKeyLogin", dataBean.toString());
                    oneKeyLogin(dataBean.getToken());
                }

                @Override
                public void onError(String error) {
                    Toaster.show(error);
                }
            });
        });

        mTvVerifyLogin.setOnClickListener(view -> {
            //((LoginActivity) _mActivity).setFragment(new PhoneLoginFragment());
            startFragmentForResult(new PhoneLoginFragment(), 0x0002);
        });

        mIvAgree.setOnClickListener(view -> {
            if (!isChecked) {
                isChecked = true;
                mIvAgree.setImageResource(R.mipmap.ic_login_checked);
            } else {
                isChecked = false;
                mIvAgree.setImageResource(R.mipmap.ic_login_un_check);
            }
        });
        SpannableString spannableString = new SpannableString("我已阅读并同意用户协议、隐私协议接受免除或者限制责任、诉讼管辖约定等粗体标示条款");

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //用户协议
                goUserAgreement();
            }
        }, 7, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                goPrivacyAgreement();
            }
        }, 12, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvAgree.setText(spannableString);
        mTvAgree.setMovementMethod(LinkMovementMethod.getInstance());
        mTvAgree.setOnClickListener(view -> {
            if (!isChecked) {
                isChecked = true;
                mIvAgree.setImageResource(R.mipmap.ic_login_checked);
            } else {
                isChecked = false;
                mIvAgree.setImageResource(R.mipmap.ic_login_un_check);
            }
        });

        initRecyclerView();

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

        mBtnHistoryAccount.setOnClickListener(this);
        mBtnForgetPassword.setOnClickListener(this);

        mCbPasswordVisiblePhone.setOnCheckedChangeListener(onCheckedChangeListener);

        String lastUsername = UserInfoModel.getInstance().getLastLoggedAccount();
        if (!TextUtils.isEmpty(lastUsername)) {
            mEtUsername.setText(lastUsername.split(";")[0]);
            mEtUsername.setSelection(lastUsername.split(";")[0].length());
            if (lastUsername.split(";").length > 1) mEtPassword.setText(lastUsername.split(";")[1]);
        }

        List<String> list = UserInfoModel.getInstance().getLoggedAccount();
        if (list == null || list.size() == 0) {
            mBtnHistoryAccount.setVisibility(View.GONE);
        } else {
            mBtnHistoryAccount.setVisibility(View.VISIBLE);
        }
        if (BuildConfig.IS_AUTOMATION_TEST) {
            mEtUsername.setText("tsyule001");
            mEtPassword.setText("123456");
        }
    }


    private void initRecyclerView() {
        mMRecyclerView.setAdapter(new SplashAdapter());
        mMRecyclerView.setLayoutManager(new ScrollLinearLayoutManager(_mActivity));
        //smoothScrollToPosition滚动到某个位置（有滚动效果）
        mMRecyclerView.smoothScrollToPosition(Integer.MAX_VALUE / 2);
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_LOGIN_STATE;
    }


    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {
        switch (compoundButton.getId()) {
            case R.id.cb_password_visible_phone:
                CBCheckChange(mEtPassword, b);
                break;
            default:
                break;

        }
    };

    private void CBCheckChange(EditText et, boolean b) {
        if (b) {
            //选择状态 显示明文--设置为可见的密码
            et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            //默认状态显示密码--设置文本 要一起写才能起作用  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        et.setSelection(et.getText().length());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = mEtUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toaster.show( mEtUsername.getHint());
                    return;
                }
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toaster.show( mEtPassword.getHint());
                    return;
                }
                if (!isChecked) {
                    showVipTipsDialog(username, password);
                } else {
                    login(username, password);
                }
                break;
            case R.id.btn_register:
                start(new RegisterFragment());
                break;
            case R.id.btn_history_account:
                showLoggedAccount();
                break;
            case R.id.btn_forget_password:
                start(new ResetPasswordFragment());
                break;
            default:
                break;
        }
    }

    /*@Override
    public void start(ISupportFragment toFragment) {
        if (toFragment instanceof SupportFragment) {
            FragmentHolderActivity.startFragmentInActivity(_mActivity, (SupportFragment) toFragment);
        } else {
            super.start(toFragment);
        }
    }*/

    private void login(String username, String password) {
        if (mViewModel != null) {
            loading("正在登录...");
            mViewModel.login(username, password, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在登录...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(UserInfoVo baseVo) {
                    if (baseVo != null && baseVo.getData() != null && !TextUtils.isEmpty(baseVo.getData().getToken())) {
                        Logs.e(baseVo.getData().toString());
                        Toaster.show("登录成功");
                        if (BuildConfig.IS_RETURN_REPORT) {//是否为回归上报注册（回归用户登录上报为注册）2023-12-07
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else if (!TextUtils.isEmpty(baseVo.getData().getAct()) && baseVo.getData().getElevate() == 1 && "login".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        }
                        saveUserInfo(baseVo.getLoginAccount(), password, baseVo.getData());
                        //_mActivity.finish();
                    } else {
                        Toaster.show( baseVo.getMsg());
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                }
            });
        }
    }

    private void oneKeyLogin(String one_key_token) {
        if (mViewModel != null) {
            loading("正在登录...");
            mViewModel.oneKeyLogin(one_key_token, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在登录...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(UserInfoVo baseVo) {
                    if (baseVo != null && baseVo.getData() != null) {
                        Logs.e(baseVo.getData().toString());
                        Toaster.show("登录成功");
                        if (BuildConfig.IS_RETURN_REPORT) {//是否为回归上报注册（回归用户登录上报为注册）2023-12-07
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else if (!TextUtils.isEmpty(baseVo.getData().getAct()) && baseVo.getData().getElevate() == 1 && "login".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        }
                        if (baseVo.getData().isCan_bind_password()) {
                            showCommentTipsDialog();
                        } else {
                            Intent intent = new Intent();
                            _mActivity.setResult(LOGIN_OK, intent);
                            _mActivity.finish();
                        }
                    } else {
                        Toaster.show( baseVo.getMsg());
                    }
                }
            });
        }
    }

    private void bindPassword(String password) {
        if (mViewModel != null) {
            mViewModel.bindPassword(password, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onSuccess(UserInfoVo baseVo) {
                    if (baseVo != null && baseVo.getData() != null && !TextUtils.isEmpty(baseVo.getData().getToken())) {
                        if (setPwdDialog != null && setPwdDialog.isShowing()) {
                            setPwdDialog.dismiss();
                        }
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    } else {
                        Toaster.show( baseVo.getMsg());
                    }
                }
            });
        }
    }

    private void saveUserInfo(String loginAccount, String password, UserInfoVo.DataBean dataBean) {
        if (mViewModel != null) {
            mViewModel.saveUserInfo(loginAccount, password, dataBean);
        }
        if (!BuildConfig.GET_PERMISSIONS_FIRSR) {
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
            boolean hasShow = spUtils.getBoolean("IS_PERMISSIONS_DIALOG", false);
            if (!hasShow) {
                spUtils.putBoolean("IS_PERMISSIONS_DIALOG", true);
                showPermissionsTipDialog(loginAccount, dataBean);
            } else {
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            }
        } else {
            Intent intent = new Intent();
            _mActivity.setResult(LOGIN_OK, intent);
            _mActivity.finish();
        }
    }

    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void showPermissionsTipDialog(String loginAccountm, UserInfoVo.DataBean dataBean) {
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_login_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvContent = authorityDialog.findViewById(R.id.tv_content);
            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvContent.setText("\"" + _mActivity.getResources().getString(R.string.app_name) + "\"" + "正在向您获取“存储”权限，同意后，将用于本APP账号缓存，以后登录均可快速选择缓存的账号直接登录。");
            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        mViewModel.saveAccountToSDK(loginAccountm, dataBean.getPassword());
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    }
                }, permissions);
            });
            authorityDialog.show();
        }
    }

    public void showLoggedAccount() {
        List<String> list = UserInfoModel.getInstance().getLoggedAccount();
        if (list == null || list.size() == 0) {
            return;
        }

        for (String account : list) {
            Logs.e("account:" + account);
        }
        String[] items = list.toArray(new String[list.size()]);
        List<String> itemList = new ArrayList<String>();
        for (int i = 0; i < items.length; i++) {
            itemList.add(items[i]);
        }

       /* AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("请选择账号")
                .setItems(items, (DialogInterface dialogInterface, int i) -> {
                    mEtUsername.setText(items[i]);
                    mEtUsername.setSelection(mEtUsername.getText().length());
                })
                .create();
        dialog.show();*/
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_login, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        dialog.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new UserAdapter(_mActivity, itemList, new OnItemClickListener() {
            @Override
            public void onItemDelete(String username) {
                if (username.split(";")[0].equals(mEtUsername.getText().toString().trim())) {
                    mEtUsername.setText("");
                    mEtPassword.setText("");
                }
                List<String> list = UserInfoModel.getInstance().getLoggedAccount();
                if (list == null || list.size() == 0) {
                    mBtnHistoryAccount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemClick(String username) {
                mEtUsername.setText(username.split(";")[0]);
                if (username.split(";").length > 1)
                    mEtPassword.setText(username.split(";")[1]);
                mEtUsername.setSelection(username.split(";")[0].length());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }));
        dialog.setCancelable(true);
        dialog.show();
    }


    class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ts_splash, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

        private List<String> mList;
        private Context mContext;
        private OnItemClickListener mOnItemClickListener;

        public UserAdapter(Context context, List<String> list, OnItemClickListener mOnItemClickListener) {
            this.mList = list;
            this.mContext = context;
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_login, parent, false);
            return new UserHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.mTvUserName.setText(mList.get(position).split(";")[0]);
            holder.mIvDelete.setOnClickListener(view -> {
                UserInfoModel.getInstance().deleteLoggedAccount(mList.get(position));
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemDelete(mList.get(position));
                }
                mList.remove(position);
                notifyDataSetChanged();

            });
            holder.itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class UserHolder extends RecyclerView.ViewHolder {
            private TextView mTvUserName;
            private ImageView mIvDelete;

            public UserHolder(View itemView) {
                super(itemView);
                mTvUserName = itemView.findViewById(R.id.tv_username);
                mIvDelete = itemView.findViewById(R.id.iv_delete);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemDelete(String username);

        void onItemClick(String username);
    }

    private CustomDialog setPwdDialog;

    public void showCommentTipsDialog() {
        if (setPwdDialog == null) {
            setPwdDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_login_set_pwd, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        }
        setPwdDialog.setCancelable(false);

        EditText mEtPassword1 = setPwdDialog.findViewById(R.id.et_password);
        EditText mEtRePassword = setPwdDialog.findViewById(R.id.et_repassword);
        setPwdDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (setPwdDialog != null && setPwdDialog.isShowing()) {
                setPwdDialog.dismiss();
            }
            Intent intent = new Intent();
            _mActivity.setResult(LOGIN_OK, intent);
            _mActivity.finish();
        });
        setPwdDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            String pwd = mEtPassword1.getText().toString().trim();
            String repwd = mEtRePassword.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                Toaster.show("请输入密码");
                return;
            }
            if (TextUtils.isEmpty(repwd)) {
                Toaster.show("请输入密码");
                return;
            }
            if (!pwd.equals(repwd)) {
                Toaster.show("两次密码不一致");
                return;
            }
            bindPassword(pwd);
        });

        setPwdDialog.show();
    }

    private void showVipTipsDialog(String username, String password) {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_agreement_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        SpannableString spannableString = new SpannableString("进入下一步前，请先阅读并同意" + getString(R.string.app_name) + "的《服务条款》、《隐私政策》");
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //用户协议
                goUserAgreement();
            }
        }, 15 + getString(R.string.app_name).length(), 21 + getString(R.string.app_name).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                goPrivacyAgreement();
            }
        }, 22 + getString(R.string.app_name).length(), 28 + getString(R.string.app_name).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) tipsDialog.findViewById(R.id.tv_content)).setText(spannableString);
        ((TextView) tipsDialog.findViewById(R.id.tv_content)).setMovementMethod(LinkMovementMethod.getInstance());
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            if (!isChecked) {
                mIvAgree.performClick();
            }
            login(username, password);
        });
        tipsDialog.show();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 0x0002) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            }
        }
    }
}
