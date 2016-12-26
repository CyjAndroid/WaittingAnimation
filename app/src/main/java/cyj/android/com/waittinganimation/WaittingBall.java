package cyj.android.com.waittinganimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chengyijun on 16/12/20.
 */

public class WaittingBall extends View {

    private int mRadius = 20;

    public WaittingBall(Context context) {
        this(context, null);
    }

    public WaittingBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaittingBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBallRadius(int radius){
        this.mRadius = radius;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(mRadius*2, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mRadius*2, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(mRadius, mRadius, mRadius, paint);
    }
}
