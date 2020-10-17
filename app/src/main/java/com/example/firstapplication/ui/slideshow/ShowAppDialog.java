package com.example.firstapplication.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.firstapplication.R;
import com.example.firstapplication.control.Mode_e;

public class ShowAppDialog extends Dialog {
    Activity context;

    private Button btn_cancel;

    public Mode_e mode;
    private View.OnClickListener mClickListener;

    public TextView appStatus = null;

    public ShowAppDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public ShowAppDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.show_appliance);
        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btn_cancel = (Button) findViewById(R.id.btn_app_show_cancel);

        // 为按钮绑定点击事件监听器
        btn_cancel.setOnClickListener(mClickListener);

        //appStatus = findViewById(R.id.text_app_status);
        this.setCancelable(true);
    }
}
