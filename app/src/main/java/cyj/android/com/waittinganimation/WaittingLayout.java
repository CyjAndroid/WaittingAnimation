package cyj.android.com.waittinganimation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengyijun on 16/12/18.
 */

public class WaittingLayout extends ViewGroup {

    private int childSpace = 0;
    private int childRadius = 100;
    private int ballTop = childRadius / 2;
    private int marginTop = 10;
    private int halfWidth;
    private View leftChild;
    private View middleChild;
    private View rightChid;
    private int spaceTime = 3000;
    private boolean isStartAnimation = false;
    private boolean isEndAnimation = false;
    private List<View> ballViews = new ArrayList<View>();
    private List<ItemPlace> points = new ArrayList<ItemPlace>();
    private Handler handler = new Handler();
    private List<ObjectAnimator> animatorList = new ArrayList<ObjectAnimator>();


    private Runnable task = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void run() {
            if (isEndAnimation) {
                isStartAnimation = true;
                isEndAnimation = false;
                startAnimator();
            }
            handler.postDelayed(this, 10);
        }
    };

    public WaittingLayout(Context context) {
        this(context, null);
    }

    public WaittingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaittingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initChildView(context);
    }

    private void initChildView(Context context) {
        WaittingItem item1 = new WaittingItem(context);
        item1.setRadius(childRadius);
        item1.setAngle(10, 230);
        addView(item1);


        WaittingItem item2 = new WaittingItem(context);
        item2.setRadius(childRadius);
        item2.setAngle(0, 180);
        addView(item2);

        WaittingItem item3 = new WaittingItem(context);
        item3.setRadius(childRadius);
        item3.setAngle(-60, 230);
        addView(item3);

        WaittingBall ball1 = new WaittingBall(context);
        ball1.setBallRadius(8);
        addView(ball1);

        WaittingBall ball2 = new WaittingBall(context);
        ball2.setBallRadius(10);
        addView(ball2);

        WaittingBall ball3 = new WaittingBall(context);
        ball3.setBallRadius(12);
        addView(ball3);

        WaittingBall ball4 = new WaittingBall(context);
        ball4.setBallRadius(15);
        addView(ball4);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaittingLayout);
            childSpace = (int) typedArray.getDimension(R.styleable.WaittingLayout_space, 50);
            childRadius = (int) typedArray.getDimension(R.styleable.WaittingLayout_radius, 200);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(0, 0);
        setMeasuredDimension(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(getChildAt(0).getMeasuredHeight() + ballTop, MeasureSpec.EXACTLY));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onLayout(boolean b, int n, int i1, int i2, int i3) {
        if (points.size() > 0) {
            return;
        }
        halfWidth = getMeasuredWidth() / 2;
        leftChild = getChildAt(0);
        leftChild.measure(0, 0);
        int top = ballTop;
        int bottom = leftChild.getMeasuredHeight() + ballTop;

        middleChild = getChildAt(1);
        middleChild.measure(0, 0);
        int middleChildleft = halfWidth - middleChild.getMeasuredWidth() / 2;
        int middleChildright = halfWidth + middleChild.getMeasuredWidth() / 2;
        middleChild.layout(middleChildleft, top, middleChildright, bottom);

        leftChild.layout(middleChildleft - childSpace - leftChild.getMeasuredWidth(),
                top, middleChildleft - childSpace, bottom);

        rightChid = getChildAt(2);
        rightChid.measure(0, 0);
        rightChid.layout(middleChildright + childSpace, top,
                middleChildright + childSpace + rightChid.getMeasuredWidth(), bottom);

        for (int i = getChildCount() - 1; i >= 3; i--) {
            getChildAt(i).measure(0, 0);
            getChildAt(i).layout(halfWidth - getChildAt(i).getMeasuredWidth() / 2, marginTop,
                    halfWidth + getChildAt(i).getMeasuredWidth() / 2, marginTop + getChildAt(i).getMeasuredHeight());
            ItemPlace place = new ItemPlace();
            place.setItemPlace(halfWidth - getChildAt(i).getMeasuredWidth() / 2, marginTop,
                    halfWidth + getChildAt(i).getMeasuredWidth() / 2, marginTop + getChildAt(i).getMeasuredHeight());
            points.add(place);
            ballViews.add(getChildAt(i));
        }

        initAnimation();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initAnimation() {

        initBallsPlace();

        for (int i = 0; i < ballViews.size(); i++) {
            View ball = ballViews.get(i);
            Path path = new Path();
            path.moveTo(ball.getX(), ball.getY());
            path.lineTo(middleChild.getRight() - ball.getMeasuredWidth() - 10,
                    middleChild.getBottom() - middleChild.getMeasuredHeight() / 2 - 10);

            RectF rectMiddle = new RectF();
            rectMiddle.left = middleChild.getLeft() + ball.getMeasuredWidth() - 10;
            rectMiddle.top = middleChild.getTop() + ball.getMeasuredHeight() - 10;
            rectMiddle.right = middleChild.getRight() - ball.getMeasuredWidth() - 10;
            rectMiddle.bottom = middleChild.getBottom() - ball.getMeasuredHeight() - 10;
            path.addArc(rectMiddle, 0, 180);

            path.lineTo(ball.getX(), ball.getY());


            path.lineTo(rightChid.getLeft() + 10,
                    rightChid.getBottom() - rightChid.getMeasuredHeight() / 2 - 10);

            RectF rectRight = new RectF();
            rectRight.left = rightChid.getLeft() + ball.getMeasuredWidth() / 2 - 10;
            rectRight.top = rightChid.getTop() + ball.getMeasuredHeight() - 10;
            rectRight.right = rightChid.getRight() - ball.getMeasuredWidth() - 10;
            rectRight.bottom = rightChid.getBottom() - ball.getMeasuredHeight() - 10;
            path.addArc(rectRight, 170, -230);


            path.quadTo(ball.getX(), ball.getY(), leftChild.getLeft() + ball.getMeasuredWidth() / 2 + 10,
                    leftChild.getTop() + ball.getMeasuredWidth() / 2 + 10);


            RectF rectLeft = new RectF();
            rectLeft.left = leftChild.getLeft() + ball.getMeasuredWidth() - 10;
            rectLeft.top = leftChild.getTop() + ball.getMeasuredHeight() - 10;
            rectLeft.right = leftChild.getRight() - ball.getMeasuredWidth() - 10;
            rectLeft.bottom = leftChild.getBottom() - ball.getMeasuredHeight() - 10;
            path.addArc(rectLeft, 230, -220);

            path.lineTo(ball.getX(), ball.getY());

            ObjectAnimator traslateAnimator = ObjectAnimator.ofFloat(ball, "x", "y", path);
            traslateAnimator.setDuration(spaceTime);
            traslateAnimator.setStartDelay(i * 80);
            if (i == ballViews.size() - 1) {
                traslateAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isEndAnimation = true;
                        if (!isStartAnimation) {
                            task.run();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            }
            animatorList.add(traslateAnimator);
        }
    }

    public void startAnimator() {
        for (ObjectAnimator animator : animatorList) {
            animator.start();
        }
    }

    public void cancelAnimator() {
        for (ObjectAnimator animator : animatorList) {
            animator.cancel();
        }
    }

    public void endAnimator() {
        for (ObjectAnimator animator : animatorList) {
            animator.end();
        }
        handler.removeCallbacks(task);

        initBallsPlace();
    }

    private void initBallsPlace() {
        for (int j = 0; j < ballViews.size(); j++) {
            View item = ballViews.get(j);
            ItemPlace place = points.get(j);
            item.layout(place.getLeft(), place.getTop(), place.getRight(), place.getBottom());
        }
    }
}
