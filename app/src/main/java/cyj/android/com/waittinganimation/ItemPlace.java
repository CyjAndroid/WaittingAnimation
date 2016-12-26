package cyj.android.com.waittinganimation;

/**
 * Created by chengyijun on 16/12/23.
 */

public class ItemPlace {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    private void setTop(int top) {
        this.top = top;
    }

    private void setLeft(int left) {
        this.left = left;
    }

    private void setRight(int right) {
        this.right = right;
    }

    private void setBottom(int bottom) {
        this.bottom = bottom;
    }


    public void setItemPlace(int l, int t, int r, int b) {
        setLeft(l);
        setTop(t);
        setRight(r);
        setBottom(b);
    }
}
