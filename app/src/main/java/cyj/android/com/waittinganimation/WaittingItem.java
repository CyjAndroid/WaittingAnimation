package cyj.android.com.waittinganimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chengyijun on 16/12/19.
 */

public class WaittingItem extends View {
    private float mStartAngle = 0;
    private float mEndAngle = 360;
    private float mRadius = 200;
    private float mRectMargin = 10;


    public WaittingItem(Context context) {
        this(context, null);
    }

    public WaittingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaittingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setAngle(float startAngle, float endAngle) {
        mStartAngle = startAngle;
        mEndAngle = endAngle;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(MeasureSpec.makeMeasureSpec((int)(mRadius+mRectMargin), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int)(mRadius+mRectMargin), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth((float) 5.0);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        RectF oval = new RectF();
        oval.left = mRectMargin;
        oval.top = mRectMargin;
        oval.right = mRadius;
        oval.bottom = mRadius;
        canvas.drawArc(oval, mStartAngle, mEndAngle, false, paint);

    }
}
