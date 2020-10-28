package com.example.firstapplication.ui.slideshow;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstapplication.R;
import com.example.firstapplication.control.Mode_e;

public class EmulateFinishDialog extends Dialog {
    Activity context;

    private Button btn_cancel;

    private float fee;
    private float PVGeneration;
    private View.OnClickListener mClickListener;

    public TextView textFee = null;
    public TextView textPV = null;

    public EmulateFinishDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public EmulateFinishDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_emulate_finish);
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btn_cancel = (Button) findViewById(R.id.btn_finish_cancel);

        // 为按钮绑定点击事件监听器
        btn_cancel.setOnClickListener(mClickListener);

        textFee = (TextView) findViewById(R.id.text_fee);
        textPV = (TextView) findViewById(R.id.text_PV);

        this.setCancelable(true);
    }

    public void SetText(float fee, float PVGeneration) {
        textFee.setText("Fee ￥" + String.valueOf(fee));
        textPV.setText("PowCons " + String.valueOf(PVGeneration) + "kWh");
    }
}
