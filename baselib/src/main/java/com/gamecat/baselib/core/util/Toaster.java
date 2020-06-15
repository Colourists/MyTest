package com.gamecat.baselib.core.util;

import android.widget.Toast;

import com.bldby.baselibrary.app.GlobalUtil;

import java.lang.ref.WeakReference;

public class Toaster {

    private static WeakReference<Toast> lastToastRef = null;

    private static void cancelLastIfNeeded() {
        if (lastToastRef == null)
            return;
        Toast toast = lastToastRef.get();
        if (toast == null)
            return;
        toast.cancel();

        lastToastRef.clear();
        lastToastRef = null;
    }

    public static void show(Toast toast) {
        cancelLastIfNeeded();
        lastToastRef = new WeakReference<Toast>(toast);
        toast.show();
    }

    public static void showToast(int resId) {
        String text = GlobalUtil.getApplication().getString(resId);
        showToast(text);
    }

    public static void showToast(CharSequence text) {
        Toast toast = Toast.makeText(GlobalUtil.getApplication(), text,
                Toast.LENGTH_SHORT);
        show(toast);
    }

    public static void showToastLong(CharSequence text) {
        Toast toast = Toast.makeText(GlobalUtil.getApplication(), text,
                Toast.LENGTH_LONG);
        show(toast);
    }


    public static void showToastOrder(CharSequence text) {
        Toast toast = Toast.makeText(GlobalUtil.getApplication(), text,
                Toast.LENGTH_SHORT);
        toast.show();
    }

//	public static void showToastCenter(Context context, CharSequence text) {
//		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View layout = inflater.inflate(R.layout.view_sign_black_toast, null);
//		TextView toastText = (TextView) layout.findViewById(R.id.toastText);
//		toastText.setText(text);
//
//		Toast result = new Toast(context);
//		result.setView(layout);
//		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//		result.setDuration(Toast.LENGTH_LONG);
//		show(result);
//	}
}
