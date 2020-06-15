package com.gamecat.baselib.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

import com.bldby.baselibrary.R;


/**
 *
 * <p>
 * 将以下代码复制到 Style.xml
 * <p>
 * <declare-styleable name="CornerButton">
 * <attr name="cornerRadius" format="dimension|reference" />
 * </declare-styleable>
 */

@SuppressLint("AppCompatCustomView")
public class CornerButton extends Button {
    private boolean mTopLeftCornerRadius;
    private boolean mTopRightCornerRadius;
    private boolean mBottomLeftCornerRadius;
    private boolean mBottomRightCornerRadius;

    public CornerButton(Context context) {
        super(context);
        init();
    }

    public CornerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerButton);
        cornerRadius = typedArray.getDimension(R.styleable.CornerButton_cornerRadius, 0.f);
        mBottomLeftCornerRadius = typedArray.getBoolean(R.styleable.CornerButton_isCornerButtonBottomLeftRadius, true);

        mBottomRightCornerRadius = typedArray.getBoolean(R.styleable.CornerButton_isCornerButtonBottomRightRadius, true);

        mTopLeftCornerRadius = typedArray.getBoolean(R.styleable.CornerButton_isCornerButtonTopLeftRadius, true);
        mTopRightCornerRadius = typedArray.getBoolean(R.styleable.CornerButton_isCornerButtonTopRightRadius, true);
        typedArray.recycle();

        init();
    }

    private final RectF roundRect = new RectF();
    private float cornerRadius = 0.f;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();

    private void init() {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
    }

    /**
     * please set dp
     */
    public void setcornerRadius(float cornerRadius) {
        float density = getResources().getDisplayMetrics().density;
        this.cornerRadius = cornerRadius * density;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, cornerRadius, cornerRadius, zonePaint);
        //哪个角不是圆角我再把你用矩形画出来
        if (!mTopLeftCornerRadius) {
            canvas.drawRect(0, 0, cornerRadius, cornerRadius, zonePaint);
        }
        if (!mTopRightCornerRadius) {
            canvas.drawRect(roundRect.right - cornerRadius, 0, roundRect.right, cornerRadius, zonePaint);
        }
        if (!mBottomLeftCornerRadius) {
            canvas.drawRect(0, roundRect.bottom - cornerRadius, cornerRadius, roundRect.bottom, zonePaint);
        }
        if (!mBottomRightCornerRadius) {
            canvas.drawRect(roundRect.right - cornerRadius, roundRect.bottom - cornerRadius, roundRect.right, roundRect.bottom, zonePaint);
        }
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }
}
