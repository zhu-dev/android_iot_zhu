package com.example.huaweiot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.annotation.Nullable;

import com.example.huaweiot.R;

import java.util.List;

public class CustomMultiButton extends LinearLayout implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "CustomMultiButton";

    private OnButtonItemChecked onButtonItemChecked;

    private RadioGroup radioGroup;
    private RadioButton btn_1;
    private RadioButton btn_2;
    private RadioButton btn_3;
    private RadioButton btn_4;

    public CustomMultiButton(Context context) {
        this(context,null);

    }

    public CustomMultiButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomMultiButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_content_custom_multi_button, this);

        radioGroup = contentView.findViewById(R.id.btn_group);
        btn_1 = contentView.findViewById(R.id.btn_1);
        btn_2 = contentView.findViewById(R.id.btn_2);
        btn_3 = contentView.findViewById(R.id.btn_3);
        btn_4 = contentView.findViewById(R.id.btn_4);

        btn_1.setChecked(true);

        radioGroup.setOnCheckedChangeListener(this);

    }

    /**
     * @param textList 每个选项显示的文本，总共需要四个
     */
    public void setText(List<String> textList) {

        btn_1.setText(textList.get(0));
        btn_2.setText(textList.get(1));
        btn_3.setText(textList.get(2));
        btn_4.setText(textList.get(3));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.btn_1:
                onButtonItemChecked.onItemChecked(1);
                break;
            case R.id.btn_2:
                onButtonItemChecked.onItemChecked(2);
                break;
            case R.id.btn_3:
                onButtonItemChecked.onItemChecked(3);
                break;
            case R.id.btn_4:
                onButtonItemChecked.onItemChecked(4);
                break;
        }
    }

    public void setOnButtonItemChecked(OnButtonItemChecked onButtonItemChecked) {
        this.onButtonItemChecked = onButtonItemChecked;
    }

    public interface OnButtonItemChecked {
        void onItemChecked(int id);
    }
}
