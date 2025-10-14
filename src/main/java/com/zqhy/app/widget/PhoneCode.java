package com.zqhy.app.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author leeham2734
 * @date 2021/6/17-17:15
 * @description
 */
public class PhoneCode extends RelativeLayout {

    private Context            context;
    private TextView           tv_code1;
    private TextView           tv_code2;
    private TextView           tv_code3;
    private TextView           tv_code4;
    private TextView           tv_code5;
    private TextView           tv_code6;
    private EditText           et_code;
    private List<String>       codes = new ArrayList<>();
    private InputMethodManager imm;


    public PhoneCode(Context context) {
        this(context, null);
    }

    public PhoneCode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_phone_code, this);
        initView(view);
        initEvent();

    }

    private void initView(View view) {
        tv_code1 = (TextView) view.findViewById(R.id.tv_code1);
        tv_code2 = (TextView) view.findViewById(R.id.tv_code2);
        tv_code3 = (TextView) view.findViewById(R.id.tv_code3);
        tv_code4 = (TextView) view.findViewById(R.id.tv_code4);
        tv_code5 = (TextView) view.findViewById(R.id.tv_code5);
        tv_code6 = (TextView) view.findViewById(R.id.tv_code6);
        et_code = (EditText) view.findViewById(R.id.et_code);
        et_code.requestFocus();
        et_code.setLongClickable(true);//支持长按
    }

    private void initEvent() {
        //验证码输入
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    et_code.setText("");
                    if (codes.size() < 6) {
                        String data = editable.toString().trim();
                        if (data.length() >= 6) {
                            //将string转换成List
                            List<String> list = Arrays.asList(data.split(""));
                            //Arrays.asList没有实现add和remove方法，继承的AbstractList，需要将list放进java.util.ArrayList中
                            codes = new ArrayList<>(list);
                            if (isNotEmpty(codes) && codes.size() > 6) {
                                //使用data.split("")方法会将""放进第一下标里需要去掉
                                codes.remove(0);
                            }
                        } else {
                            codes.add(data);
                        }
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        et_code.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (isNotEmpty(codes)) {
                    if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size() > 0) {
                        codes.remove(codes.size() - 1);
                        showCode();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        if (codes.size() >= 1) {
            code1 = codes.get(0);
        }
        if (codes.size() >= 2) {
            code2 = codes.get(1);
        }
        if (codes.size() >= 3) {
            code3 = codes.get(2);
        }
        if (codes.size() >= 4) {
            code4 = codes.get(3);
        }
        if (codes.size() >= 5) {
            code5 = codes.get(4);
        }
        if (codes.size() >= 6) {
            code6 = codes.get(5);
        }
        tv_code1.setText(code1);
        tv_code2.setText(code2);
        tv_code3.setText(code3);
        tv_code4.setText(code4);
        tv_code5.setText(code5);
        tv_code6.setText(code6);
        //setColor();//设置高亮颜色
        callBack();//回调
    }

    /**
     * 显示输入的验证码
     */
    public void showEmptyCode() {
        tv_code1.setText("");
        tv_code2.setText("");
        tv_code3.setText("");
        tv_code4.setText("");
        tv_code5.setText("");
        tv_code6.setText("");
        codes.clear();
        et_code.setText("");
    }


    /**
     * 回调
     */
    private void callBack() {
        if (onInputListener == null) {
            return;
        }
        if (codes.size() == 6) {
            onInputListener.onSuccess(getPhoneCode());
        } else {
            onInputListener.onInput();
        }
    }

    //定义回调
    public interface OnInputListener {
        void onSuccess(String codes);

        void onInput();
    }

    private OnInputListener onInputListener;

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    /**
     * 显示键盘
     */
    public void showSoftInput() {
        //显示软键盘
        if (imm != null && et_code != null) {
            et_code.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imm.showSoftInput(et_code, 0);
                }
            }, 1500);
        }
    }

    /**
     * 获得手机号验证码
     *
     * @return 验证码
     */
    public String getPhoneCode() {
        StringBuilder sb = new StringBuilder();
        for (String codes : codes) {
            sb.append(codes);
        }
        return sb.toString();
    }

    private static boolean isNotEmpty(List<?> codes) {
        return codes != null && codes.size() > 0;
    }
}
