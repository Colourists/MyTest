package com.gamecat.baselib.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * Created by gundam on 2018/1/7.
 *
 * 长按添加 长按时会一直调用点击事件,
 */

@SuppressLint("AppCompatCustomView")
public class LongClickButton extends Button {


    /**
     * 间隔时间（ms）
     */
    private long intervalTime = 50;

    private MyHandler handler;

    public LongClickButton(Context context) {
        super(context);

        init();
    }

    public LongClickButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public LongClickButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化监听
     */
    private void init() {
        handler = new MyHandler(this);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Thread(new LongClickThread()).start();
                return true;
            }
        });
    }

    /**
     * 长按时，该线程将会启动
     */
    private class LongClickThread implements Runnable {
        private int num;

        @Override
        public void run() {
            while (LongClickButton.this.isPressed()) {
                num++;
                if (num % 5 == 0) {
                    handler.sendEmptyMessage(1);
                }

                SystemClock.sleep(intervalTime / 5);
            }
        }
    }

    /**
     * 通过handler，使监听的事件响应在主线程中进行
     */
    private static class MyHandler extends Handler {
        private WeakReference<LongClickButton> ref;

        MyHandler(LongClickButton button) {
            ref = new WeakReference<>(button);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            LongClickButton button = ref.get();
            if (button != null) {
                //直接调用普通点击事件  
                button.performClick();
            }
        }
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }


}
