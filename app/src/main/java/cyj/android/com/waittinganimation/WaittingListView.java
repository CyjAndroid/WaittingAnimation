package cyj.android.com.waittinganimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.delay;

/**
 * Created by chengyijun on 16/12/23.
 */

public class WaittingListView extends ListView {
    private float downY;
    private int headViewHeight;
    private int paddingTop;
    private WaittingLayout headView;
    private int START = 0;
    private OnDataListener dataListener;

    private final int START_REFRESH = 1;


    public WaittingListView(Context context) {
        this(context, null);
    }

    public WaittingListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaittingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHead(context);
    }

    private void initHead(Context context) {
        headView = new WaittingLayout(context);
        headView.measure(0, 0);
        headViewHeight = headView.getMeasuredHeight();
        setPadding(0, -(headViewHeight), 0, 0);

        this.addHeaderView(headView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                if (START == START_REFRESH) {
                    headView.endAnimator();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();

                int diffY = (int) (moveY - downY);
                paddingTop = (-headViewHeight + diffY);

                if (paddingTop <= 20 && getFirstVisiblePosition() == 0) {
                    setPadding(0, paddingTop, 0, 0);
                } else {
                    paddingTop = 0;
                }

                if (START == START_REFRESH) {
                    headView.endAnimator();
                }

                break;
            case MotionEvent.ACTION_UP:

                Log.e("top","top : "+getPaddingTop());
                if (getPaddingTop() < -10) {
                    scrollBy(0, headViewHeight + getPaddingTop());
                } else {
                    START = START_REFRESH;
                    headView.startAnimator();

                    updateNewData();
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    private void updateNewData() {

        new Handler().postDelayed(new Runnable() {

            public void run() {
                START = 0;
                smoothScrollBy(headViewHeight - getPaddingTop()+20, 500);
                headView.endAnimator();
                if (dataListener != null) {
                    dataListener.newData();
                }

            }

        }, 3000);
    }

    public void setDataListener(OnDataListener listener) {
        this.dataListener = listener;
    }

    interface OnDataListener {
        void newData();
    }


}

