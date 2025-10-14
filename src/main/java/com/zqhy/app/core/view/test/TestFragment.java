package com.zqhy.app.core.view.test;

import android.Manifest;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.im.sdk.ImSDK;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.gism.sdk.GismSDK;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.zqhy.app.App;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.core.pay.alipay.AliPayInstance;
import com.zqhy.app.core.tool.utilcode.DeviceUtils;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.glide.GlideRoundTransform;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.HuiChuanDataReportAgency;
import com.zqhy.app.report.KsDataReportAgency;
import com.zqhy.app.report.TencentDataAgency;
import com.zqhy.app.report.TtDataReportAgency;
import com.zqhy.app.utils.CalendarReminderUtils;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.PermissionHelper;
import com.zqhy.app.utils.TsDeviceUtils;
import com.zqhy.app.utils.UmengUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 测试页面
 * release版本不会进入
 *
 * @author Administrator
 */
public class TestFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("测试页面");
        bindViews();

        String devicesInfo = UmengUtils.getDeviceInfo(_mActivity);
        Logs.e("devicesInfo = " + devicesInfo);
    }

    private Button mBtnApiActivation;
    private Button mBtnApiRegister;
    private Button mBtnApiPay;

    private Button   mBtnImei1;
    private TextView mTvValueImei1;
    private Button   mBtnImei2;
    private TextView mTvValueImei2;

    private AppCompatEditText mEtTitle;
    private AppCompatEditText mEtDescription;
    private AppCompatEditText mEtReminderTime;
    private AppCompatEditText mEtPreviousDate;
    private Button            mBtnAddCalendarEvent;

    private AppCompatEditText mEtDeleteTitle;
    private Button            mBtnDeleteCalendarEvent;

    private Button mBtnGetApkFiles;

    private TextView mTvUmengDeviceInfo;

    private TextView mTvAlipayVersion;

    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;

    private AppCompatEditText mEtBrowserUrl;
    private Button            mBtnJumpBrowser;
    private AppCompatCheckBox mIsH5game;
    private AppCompatEditText mEtGameId;
    private AppCompatEditText mEtGameName;

    private Button            mBtnTtEventRegister;
    private Button            mBtnTtEventPurchase;
    private AppCompatEditText mEtTtEventPurchase;
    private Button            mBtnModelTest;

    private Button mBtnKuaishouActivation;
    private Button mBtnKuaishouRegister;
    private Button mBtnKuaishouPay;

    private Button mBtnHuiChuanActivation;
    private Button mBtnHuiChuanRegister;
    private Button mBtnHuiChuanPay;

    private Button mBtnOaid;

    private void bindViews() {
        mBtnApiActivation = findViewById(R.id.btn_api_activation);
        mBtnApiRegister = findViewById(R.id.btn_api_register);
        mBtnApiPay = findViewById(R.id.btn_api_pay);

        mBtnApiActivation.setOnClickListener(this);
        mBtnApiRegister.setOnClickListener(this);
        mBtnApiPay.setOnClickListener(this);


        mBtnImei1 = findViewById(R.id.btn_imei_1);
        mTvValueImei1 = findViewById(R.id.tv_value_imei_1);
        mBtnImei2 = findViewById(R.id.btn_imei_2);
        mTvValueImei2 = findViewById(R.id.tv_value_imei_2);


        mBtnImei1.setOnClickListener(this);
        mBtnImei2.setOnClickListener(this);

        mTvValueImei1.setOnClickListener(this);
        mTvValueImei2.setOnClickListener(this);


        mEtTitle = findViewById(R.id.et_title);
        mEtDescription = findViewById(R.id.et_description);
        mEtReminderTime = findViewById(R.id.et_reminder_time);
        mEtPreviousDate = findViewById(R.id.et_previous_date);
        mBtnAddCalendarEvent = findViewById(R.id.btn_add_calendar_event);

        mBtnAddCalendarEvent.setOnClickListener(this);

        mEtDeleteTitle = findViewById(R.id.et_delete_title);
        mBtnDeleteCalendarEvent = findViewById(R.id.btn_delete_calendar_event);

        mBtnDeleteCalendarEvent.setOnClickListener(this);

        // add on 2019.06.04
        mBtnGetApkFiles = findViewById(R.id.btn_get_apk_files);
        mBtnGetApkFiles.setOnClickListener(this);

        // add on 2019.06.26
        mTvUmengDeviceInfo = findViewById(R.id.tv_umeng_device_info);
        setUmengDeviceInfo();


        // add on  2019.08.07
        mTvAlipayVersion = findViewById(R.id.tv_alipay_version);
        setAlipayVerion();


        // add on 2019.12.04
        mImage1 = findViewById(R.id.image1);
        mImage2 = findViewById(R.id.image2);
        mImage3 = findViewById(R.id.image3);
        mImage4 = findViewById(R.id.image4);
        setImageTest();


        //add on 2020.01.20
        mEtBrowserUrl = findViewById(R.id.et_browser_url);
        mBtnJumpBrowser = findViewById(R.id.btn_jump_browser);
        mIsH5game = findViewById(R.id.is_h5game);
        mEtGameId = findViewById(R.id.et_game_id);
        mEtGameName = findViewById(R.id.et_game_name);

        mIsH5game.setOnCheckedChangeListener((compoundButton, b) -> {
            mEtGameId.setEnabled(b);
            mEtGameName.setEnabled(b);
        });

        setBrowserTest();

        //add on 2020.03.16
        mBtnTtEventRegister = findViewById(R.id.btn_tt_event_register);
        mBtnTtEventPurchase = findViewById(R.id.btn_tt_event_purchase);
        mEtTtEventPurchase = findViewById(R.id.et_tt_event_purchase);
        setTtTest();


        mBtnModelTest = findViewById(R.id.btn_model_test);
        mBtnModelTest.setOnClickListener(view -> {
            setModelTest();
        });

        // add on 2021.08.26
        mBtnKuaishouActivation = findViewById(R.id.btn_kuaishou_activation);
        mBtnKuaishouRegister = findViewById(R.id.btn_kuaishou_register);
        mBtnKuaishouPay = findViewById(R.id.btn_kuaishou_pay);

        mBtnKuaishouActivation.setOnClickListener(view -> {

        });
        mBtnKuaishouRegister.setOnClickListener(view -> {
            KsDataReportAgency.getInstance().register("kuaishou", "000001", "tsyule001", "ea0000001");
        });
        mBtnKuaishouPay.setOnClickListener(view -> {
            PayResultVo payResultVo = new PayResultVo("NO." + System.currentTimeMillis(), "100");
            KsDataReportAgency.getInstance().purchase("kuaishou", "alipay", payResultVo, "000001", "ea0000001");
        });

        // add on 2021.12.22
        mBtnHuiChuanActivation = findViewById(R.id.btn_huichuan_activation);
        mBtnHuiChuanRegister = findViewById(R.id.btn_huichuan_register);
        mBtnHuiChuanPay = findViewById(R.id.btn_huichuan_pay);
        mBtnHuiChuanActivation.setOnClickListener(view -> {
            if (AppConfig.isNeedShenMa()){
                GismSDK.onLaunchApp();
                Log.e("TestFragment", "onLaunchApp");
            }
        });
        mBtnHuiChuanRegister.setOnClickListener(view -> {
            HuiChuanDataReportAgency.getInstance().register("huichuan", "000001", "tsyule001", "ea0000001");
        });
        mBtnHuiChuanPay.setOnClickListener(view -> {
            PayResultVo payResultVo = new PayResultVo("NO." + System.currentTimeMillis(), "100");
            HuiChuanDataReportAgency.getInstance().purchase("huichuan", "alipay", payResultVo, "000001", "ea0000001");
        });

        mBtnOaid = findViewById(R.id.btn_oaid);
        mBtnOaid.setText("oaid = " + ImSDK.appViewModelInstance.getOaid());

        mBtnOaid.setOnClickListener(view -> {
            if (CommonUtils.copyString(_mActivity, mBtnOaid.getText().toString())){
                Toaster.show("已复制");
            }
        });

        /*if (BuildConfig.BAIDU_APP_ID != 0 && !TextUtils.isEmpty(BuildConfig.BAIDU_APP_KEY)){
            BaiduAction.init(getActivity().getApplicationContext(), BuildConfig.BAIDU_APP_ID, BuildConfig.BAIDU_APP_KEY);
        }*/
    }

    private void setModelTest() {
        try {
            String json = CommonUtils.getJsonDataFromAsset(_mActivity, "data/new0823_1715.json");

            Type listType = new TypeToken<MainCommonDataVo>() {
            }.getType();
            Gson gson = new Gson();
            MainCommonDataVo dataVo = gson.fromJson(json, listType);
            if (dataVo != null && dataVo.data != null) {
                dataVo.data.printData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTtTest() {
        mBtnTtEventRegister.setOnClickListener(view -> {
            TtDataReportAgency.getInstance().register("mobile", "user_id_xxxxxx", "username_xxxxxx", "xxxx");
        });

        mBtnTtEventPurchase.setOnClickListener(view -> {
            String amount = mEtTtEventPurchase.getText().toString().trim();
            if (TextUtils.isEmpty(amount)) {
                return;
            }
            String orderID = "order_id_" + System.currentTimeMillis();
            OnPayConfig.setPayConfig(orderID, String.valueOf(amount), "unknown");
            PayResultVo payResultVo = new PayResultVo(orderID, amount);
            if (isAlipay) {
                TtDataReportAgency.getInstance().purchase("mobile", OnPayConfig.PAY_TYPE_ALI, payResultVo, "user_id_xxxxxx", "0000001");
            } else {
                TtDataReportAgency.getInstance().purchase("mobile", OnPayConfig.PAY_TYPE_WECHAT, payResultVo, "user_id_xxxxxx", "0000001");
            }
            isAlipay = !isAlipay;
        });

    }

    private void setBrowserTest() {
        mBtnJumpBrowser.setOnClickListener(v -> {
            String testUrl = mEtBrowserUrl.getText().toString().trim();
            if (TextUtils.isEmpty(testUrl)) {
                return;
            }
            boolean isH5Game = mIsH5game.isChecked();
            if (isH5Game) {
                String gameid = mEtGameId.getText().toString();
                String gamename = mEtGameName.getText().toString();
                BrowserActivity.newInstance(_mActivity, testUrl, true, gamename, gameid);
            } else {
                BrowserActivity.newInstance(_mActivity, testUrl);
            }
        });
    }

    private void setImageTest() {
        String imageUrl1 = "http://p1.tsyule.cn//2019//12//03//5de5fcaf92e0d.png";
        String imageUrl2 = "http://p1.tsyule.cn//2019//12//02//5de4de0dbc6ad.png";
        String imageUrl3 = "http://p1.tsyule.cn//2019//11//27//5dde1f828cf19.png";

        GlideApp.with(_mActivity)
                .asBitmap()
                .load(imageUrl2)
                .into(mImage1);

        GlideApp.with(_mActivity)
                .asBitmap()
                .load(imageUrl1)
                .transform(new GlideRoundTransform(_mActivity, 5))
                .into(mImage2);

        GlideApp.with(_mActivity)
                .asBitmap()
                .load(imageUrl3)
                .transform(new GlideRoundTransform(_mActivity, 66))
                .into(mImage3);


        //        String imageUrl4 = "http://p1.tsyule.cn/2019/11/16/5dcfac8a1ee1f.jpg";

        String imageUrl4 = "http://p1.tsyule.cn//2019//11//22//5dd7467566df9.jpg";

        GlideApp.with(_mActivity)
                .asBitmap()
                .load(imageUrl4)
                .transform(new GlideRoundTransform(_mActivity, 0))
                .into(mImage4);
    }

    private void setAlipayVerion() {
        String alipayVersion = AliPayInstance.getInstance().getAlipayVersion(_mActivity);
        mTvAlipayVersion.setText("支付宝版本信息：" + alipayVersion);
    }

    private void setUmengDeviceInfo() {
        String[] deviceInfo = new String[2];
        deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(_mActivity);
        deviceInfo[1] = DeviceConfig.getMac(_mActivity);

        if (deviceInfo != null) {
            String device_id = "";
            String mac = "";
            if (deviceInfo.length >= 1) {
                device_id = deviceInfo[0];
            }
            if (deviceInfo.length >= 2) {
                mac = deviceInfo[1];
            }

            StringBuilder sb = new StringBuilder();

            sb.append("{\"device_id\":\"")
                    .append(device_id)
                    .append("\",\"mac\":\"")
                    .append(mac)
                    .append("\"}");

            String IMEI = DeviceUtils.getIMEI_1(_mActivity);
            sb.append("\n").append(IMEI);

            String ts_imei = TsDeviceUtils.getDeviceIMEI(App.instance());
            String ts_device_id = TsDeviceUtils.getUniqueId(App.instance());
            String ts_device_id_2 = TsDeviceUtils.getDeviceSign(App.instance());

            sb.append("\nts_imei = " + ts_imei);
            sb.append("\nts_device_id = " + ts_device_id);
            sb.append("\nts_device_id_2 = " + ts_device_id_2);


            mTvUmengDeviceInfo.setVisibility(View.VISIBLE);
            mTvUmengDeviceInfo.setText(sb.toString());
        }
        mTvUmengDeviceInfo.setOnClickListener(v -> {
            String value = mTvUmengDeviceInfo.getText().toString().trim();
            if (CommonUtils.copyString(_mActivity, value)) {
                Toast.makeText(_mActivity, "已复制", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isAlipay = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_api_activation:
                TencentDataAgency.getInstance().startApp(_mActivity.getApplication());
                break;
            case R.id.btn_api_register:
                TencentDataAgency.getInstance().register("mobile", "user_id_xxxxxx", "username_xxxxxx", "xxxx");
                break;
            case R.id.btn_api_pay:
                String orderID = "order_id_xxxxxxxxxxxxxxxxxx";
                int amount = 100;
                OnPayConfig.setPayConfig(orderID, String.valueOf(amount), "unknown");
                PayResultVo payResultVo = new PayResultVo(orderID, String.valueOf(amount));
                if (isAlipay) {
                    TencentDataAgency.getInstance().purchase("mobile", OnPayConfig.PAY_TYPE_ALI, payResultVo, "1", "1");
                } else {
                    TencentDataAgency.getInstance().purchase("mobile", OnPayConfig.PAY_TYPE_WECHAT, payResultVo, "1", "1");
                }
                isAlipay = !isAlipay;
                break;
            case R.id.btn_imei_1: {
                String valueImei = TsDeviceUtils.getDeviceIMEI(_mActivity);
                mTvValueImei1.setText(valueImei);
            }
            break;
            case R.id.btn_imei_2: {
                String valueImei = DeviceUtils.getImeiOrMeid(_mActivity);
                mTvValueImei2.setText(valueImei);
            }
            break;
            case R.id.tv_value_imei_1:
                if (CommonUtils.copyString(_mActivity, mTvValueImei1.getText().toString().trim())) {
                    Toast.makeText(_mActivity, "复制成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_value_imei_2:
                if (CommonUtils.copyString(_mActivity, mTvValueImei2.getText().toString().trim())) {
                    Toast.makeText(_mActivity, "复制成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_add_calendar_event: {
                //添加日历事件
                String title = mEtTitle.getText().toString().trim();

                String description = mEtDescription.getText().toString().trim();

                String reminderTime = mEtReminderTime.getText().toString().trim();

                String previousMinute = mEtPreviousDate.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(reminderTime) || TextUtils.isEmpty(previousMinute)) {
                    Toaster.show( "请完善内容。。。");
                    return;
                }
                addCalendarEvent(title, description, Long.parseLong(reminderTime), 5, Integer.parseInt(previousMinute));
            }
            break;
            case R.id.btn_delete_calendar_event: {
                //删除日历事件
                String title = mEtDeleteTitle.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    Toaster.show( "请完善内容。。。");
                    return;
                }
                deleteCalendarEvent(title);
            }
            break;
            case R.id.btn_get_apk_files: {
                start(new ApkFileListFragment());
            }
            break;
            default:
                break;
        }
    }


    interface OnPermissionGrantedListener {
        void onPermissionGranted();
    }

    private String[] permissions = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};

    private void checkCalendarPermission(OnPermissionGrantedListener onPermissionGrantedListener) {
        PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
            @Override
            public void onGranted() {
                if (onPermissionGrantedListener != null) {
                    onPermissionGrantedListener.onPermissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                Toaster.show( "请授权后再尝试操作哦~");
            }
        }, permissions);
    }


    private void addCalendarEvent(String title, String description, long reminderTime, int previousMinute) {
        checkCalendarPermission(() -> {
            boolean result = CalendarReminderUtils.addCalendarEvent(_mActivity, title, description, reminderTime, previousMinute);
            if (result) {
                Toaster.show( "日历添加成功");
            } else {
                Toaster.show( "日历添加失败");
            }
        });
    }

    private void addCalendarEvent(String title, String description, long reminderTime, int continuousMinute, int previousMinute) {
        checkCalendarPermission(() -> {
            boolean result = CalendarReminderUtils.addCalendarEvent(_mActivity, title, description, reminderTime, continuousMinute, previousMinute);
            if (result) {
                Toaster.show( "日历添加成功");
            } else {
                Toaster.show( "日历添加失败");
            }
        });
    }

    private void deleteCalendarEvent(String title) {
        checkCalendarPermission(() -> {
            boolean result = CalendarReminderUtils.deleteCalendarEvent(_mActivity, title);
            if (result) {
                Toaster.show( "日历删除成功");
            } else {
                Toaster.show( "日历删除失败");
            }
        });
    }

}
