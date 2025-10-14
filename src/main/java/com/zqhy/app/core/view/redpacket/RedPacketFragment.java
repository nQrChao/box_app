package com.zqhy.app.core.view.redpacket;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.tool.utilcode.DeviceUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class RedPacketFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_red_packet;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("抢红包");
        bindViews();
    }


    private EditText mTvInputTotal;
    private EditText mTvEnterNumber;
    private Button mBtnPrepareRedPacket;
    private LinearLayout mLlRedPacket;
    private ImageView mIvRedPacket;
    private TextView mTvRedPacketTips;
    private Button mBtnReset;
    private TextView mTvRedPacketLog;

    private TextView mTvUmengDeviceInfo;

    private void bindViews() {
        mTvInputTotal = findViewById(R.id.tv_input_total);
        mTvEnterNumber = findViewById(R.id.tv_enter_number);
        mBtnPrepareRedPacket = findViewById(R.id.btn_prepare_red_packet);
        mLlRedPacket = findViewById(R.id.ll_red_packet);
        mIvRedPacket = findViewById(R.id.iv_red_packet);
        mTvRedPacketTips = findViewById(R.id.tv_red_packet_tips);
        mBtnReset = findViewById(R.id.btn_reset);
        mTvRedPacketLog = findViewById(R.id.tv_red_packet_log);

        mTvUmengDeviceInfo = findViewById(R.id.tv_umeng_device_info);

        mBtnPrepareRedPacket.setOnClickListener(this);
        mIvRedPacket.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);

        mTvUmengDeviceInfo.setOnClickListener(this);

        //限制输入最多2位小数
        mTvInputTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot < 0) {
                    return;
                }
                if (temp.startsWith(".") && posDot == 0) {
                    //输入以 “ . ”开头的情况，自动在.前面补0
                    s.insert(0, "0");
                    return;
                }
                if (temp.startsWith("0") && temp.length() > 1 && (posDot == -1 || posDot > 1)) {
                    //输入"08" 等类似情况
                    s.delete(0, 1);
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {
                    //小数点后面只能有两位小数
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
        //限制输入0.01-200.00
        setRegion(mTvInputTotal, 0.01, 200.00);
        //限制输入1-500
        setRegion(mTvEnterNumber, 1, 500);

        //获取友盟设备信息
        setUmengDeviceInfo();
    }

    /**
     * 获取友盟设备信息
     */
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

            mTvUmengDeviceInfo.setVisibility(View.VISIBLE);
            mTvUmengDeviceInfo.setText(sb.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prepare_red_packet:
                //包红包
                prepareRedPacket();
                break;
            case R.id.iv_red_packet:
                //抢红包
                redPacketWars();
                break;
            case R.id.btn_reset:
                //重置
                resetRedPacket();
                break;
            case R.id.tv_umeng_device_info:
                String deviceInfo = mTvUmengDeviceInfo.getText().toString().trim();
                if (CommonUtils.copyString(_mActivity, deviceInfo)) {
                    Toast.makeText(_mActivity, "已复制", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    RedPacketVo redPacketVo;

    /**
     * 包红包
     */
    public void prepareRedPacket() {
        String mStrTotal = mTvInputTotal.getText().toString().trim();
        if (TextUtils.isEmpty(mStrTotal)) {
            toast("Please Input Total");
            return;
        }

        String mStrNumber = mTvEnterNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mStrNumber)) {
            toast("Please Input Enter Number");
            return;
        }

        float moneyOfYuan = 0;
        int count = 0;

        try {
            moneyOfYuan = Float.parseFloat(mStrTotal);
            count = Integer.parseInt(mStrNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!checkRedPacket(moneyOfYuan, count)) {
            toast("There is something wrong in Total and Quantity");
            return;
        }

        double[] redPackets = RedPacket.getRedPackets(moneyOfYuan, count);
        if (redPackets != null) {
            redPacketVo = new RedPacketVo(redPackets);
            finishPrepareRedPacket();
        } else {
            toast("some bugs happened!!!!!");
        }
    }

    private boolean checkRedPacket(float moneyOfYuan, int count) {
        if (moneyOfYuan == 0 || count == 0) {
            return false;
        }
        return (count * 0.01f) < moneyOfYuan;
    }


    public void finishPrepareRedPacket() {
        mTvInputTotal.setEnabled(false);
        mTvEnterNumber.setEnabled(false);

        mBtnPrepareRedPacket.setEnabled(false);
        mLlRedPacket.setVisibility(View.VISIBLE);
        mBtnReset.setVisibility(View.VISIBLE);
        printRedPacketTips(redPacketVo);
    }

    /**
     * 充值红包
     */
    public void resetRedPacket() {
        mTvInputTotal.getText().clear();
        mTvInputTotal.setEnabled(true);
        mTvEnterNumber.getText().clear();
        mTvEnterNumber.setEnabled(true);

        mBtnPrepareRedPacket.setEnabled(true);
        mLlRedPacket.setVisibility(View.GONE);
        mBtnReset.setVisibility(View.GONE);
        mTvRedPacketLog.setText("");

        redPacketVo = null;
    }


    /**
     * 抢红包
     */
    public void redPacketWars() {
        if (redPacketVo == null) {
            toast("Oh shit , The RedPacket is null");
            return;
        }

        if (!redPacketVo.isEmpty()) {
            redPacketVo.openRedPacket();

            printRedPacketTips(redPacketVo);
            printRedPacketLog(redPacketVo);
        } else {
            toast("红包已经领完了");
        }
    }


    private void printRedPacketTips(RedPacketVo redPacketVo) {
        if (redPacketVo == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        int index = redPacketVo.getRedPacketIndex();
        double openedRedPacket = redPacketVo.getOpenedRedPacketAmount();
        double unOpenedRedPacket = redPacketVo.getUnOpenedRedPacketAmount();

        sb.append("已有")
                .append(index)
                .append("位用户领取了")
                .append(openedRedPacket)
                .append("元，还剩")
                .append(unOpenedRedPacket)
                .append("元待领取");

        mTvRedPacketTips.setText(sb.toString());
    }

    private void printRedPacketLog(RedPacketVo redPacketVo) {
        if (redPacketVo == null) {
            return;
        }

        String newLog = redPacketVo.getLastRedPacketLog();
        String oldLog = mTvRedPacketLog.getText().toString();

        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(oldLog)) {
            sb.append(oldLog);
        }

        sb.append(newLog);
        mTvRedPacketLog.setText(sb.toString());
    }

    private void toast(String toast) {
        Toast.makeText(_mActivity, toast, Toast.LENGTH_LONG).show();
    }


    /**
     * edittext只能输入数值的时候做最大最小的限制
     *
     * @param edit
     * @param MIN_MARK
     * @param MAX_MARK
     */
    public static void setRegion(final EditText edit, final double MIN_MARK, final double MAX_MARK) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 1) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double num = Double.parseDouble(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            edit.setText(s);
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            edit.setText(s);
                        }
                        edit.setSelection(s.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double markVal = 0;
                        try {
                            markVal = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            edit.setText(String.valueOf(MAX_MARK));
                            edit.setSelection(String.valueOf(MAX_MARK).length());
                        }
                        return;
                    }
                }
            }
        });
    }

    /**
     * edittext只能输入数值的时候做最大最小的限制
     *
     * @param edit
     * @param MIN_MARK
     * @param MAX_MARK
     */
    public static void setRegion(final EditText edit, final int MIN_MARK, final int MAX_MARK) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 1) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double num = Double.parseDouble(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            edit.setText(s);
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            edit.setText(s);
                        }
                        edit.setSelection(s.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double markVal = 0;
                        try {
                            markVal = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            edit.setText(String.valueOf(MAX_MARK));
                            edit.setSelection(String.valueOf(MAX_MARK).length());
                        }
                        return;
                    }
                }
            }
        });
    }
}
