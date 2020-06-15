package com.gamecat.baselib.core.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bldby.baselibrary.R;
import com.bldby.baselibrary.app.GlobalUtil;

import static com.bldby.baselibrary.app.GlobalUtil.getStringSafe;


/**
 * 顶部栏提示 toast会弹一下
 */
public class ToastUtil extends Toast {

    public ToastUtil(Context context) {
        super(context);
    }

    public static Toast yToast(CharSequence text, int duration) {
        Toast result = new Toast(GlobalUtil.getApplication());

        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) GlobalUtil.getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.toast, null);
        TextView toastText = (TextView) layout.findViewById(R.id.textViewPrompt);
        if (text.toString().replace("0", "").length() > 20) {
            String showText = text.toString().replace(",", ",\n");
            toastText.setText(showText);
        } else {
            toastText.setText(text);
        }

        result.setView(layout);

        float density = DeviceUtil.getScreenDensity(GlobalUtil.getApplication());
        result.setGravity(Gravity.TOP, 0, (int) (density * 200));
        result.setDuration(duration);

        return result;
    }

    public static void show(CharSequence text) {
        Toaster.show(yToast(text, Toast.LENGTH_SHORT));
    }

    public static void show(int resId) {
        show(getStringSafe(resId));
    }

}