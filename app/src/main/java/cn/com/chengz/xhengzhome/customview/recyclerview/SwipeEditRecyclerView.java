package cn.com.chengz.xhengzhome.customview.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Scroller;

/**
 * Created by xhengz on 2016/3/18.
 */
public class SwipeEditRecyclerView extends RecyclerView {

    /**
     * RecyclerView的LayoutManager的方向
     */
    private Orientation orientation = Orientation.VERTICAL;

    /**
     * 当前滑动的Position
     */
    private int slidePosition;

    private int downX;
    private int downY;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * item
     */
    private View itemView;

    private Scroller scroller;
    private static final int SNAP_VELOCITY = 600;

    /**
     * 速度追踪器
     */
    private VelocityTracker velocityTracker;

    private boolean isSlide = false;

    private int mTouchSlop;

    private RemoveListener removeListener;
    private RemoveDirection removeDirection;

    public SwipeEditRecyclerView(Context context) {
        super(context);

        init(context);
    }

    public SwipeEditRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        //获取屏幕的宽度，默认RecycerView是充满屏幕的，横排时也是。
        if (orientation == Orientation.VERTICAL) {
            screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        } else {
            screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        }

        scroller = new Scroller(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setRemoveListener(RemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addVelocityTracker(ev);

                if (!scroller.isFinished()) {
                    return super.dispatchTouchEvent(ev);
                }

                downX = (int) ev.getX();
                downY = (int) ev.getY();

                //此点击事件没有发生在任何item上
                itemView = findChildViewUnder(downX, downY);
                if (itemView == null) {
                    return super.dispatchTouchEvent(ev);
                }
                slidePosition = getChildAdapterPosition(itemView);
                if (slidePosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (orientation == Orientation.VERTICAL) {
                    if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY ||
                            (Math.abs(ev.getX() - downX) > mTouchSlop &&
                                    Math.abs(ev.getY() - downY) < mTouchSlop)) {
                        isSlide = true;
                    }
                } else {
                    if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY ||
                            (Math.abs(ev.getY() - downY) > mTouchSlop &&
                                    Math.abs(ev.getX() - downX) < mTouchSlop)) {
                        isSlide = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isSlide && slidePosition != AdapterView.INVALID_POSITION && itemView != null) {
            requestDisallowInterceptTouchEvent(true);
            addVelocityTracker(e);
            int x = (int) e.getX();
            int y = (int) e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    MotionEvent cancelEvent = MotionEvent.obtain(e);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (e.getAction() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                    if (orientation == Orientation.VERTICAL) {
                        int deltaX = downX - x;
                        downX = x;

                        itemView.scrollBy(0, deltaX);
                    } else {
                        int deltaY = downY - y;
                        downY = y;
                        itemView.scrollBy(0, deltaY);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    int velocityX = getScrollVelocity();
                    if (velocityX > SNAP_VELOCITY) {
                        scrollRight();
                    } else if (velocityX < -SNAP_VELOCITY) {
                        scrollLeft();
                    } else {
                        scrollByDistanceX();
                    }

                    recycleVelocityTracker();
                    isSlide = false;
                    break;
            }

        }
        return super.onTouchEvent(e);
    }

    private void scrollRight() {
        if (orientation == Orientation.VERTICAL) {
            removeDirection = RemoveDirection.RIGHT;
            final int delta = (screenWidth + itemView.getScrollX());

            scroller.startScroll(itemView.getScrollX(), 0, -delta, 0, Math.abs(delta));
            postInvalidate();
        } else {
            removeDirection = RemoveDirection.RIGHT;
            final int delta = (screenWidth + itemView.getScrollY());

            scroller.startScroll(0, itemView.getScrollY(), -delta, 0, Math.abs(delta));
            postInvalidate();
        }
    }

    private void scrollLeft() {
        if (orientation == Orientation.VERTICAL) {
            removeDirection = RemoveDirection.LEFT;
            final int delta = (screenWidth - itemView.getScrollX());

            scroller.startScroll(itemView.getScrollX(), 0, delta, 0, Math.abs(delta));
            postInvalidate();
        } else {
            removeDirection = RemoveDirection.LEFT;
            final int delta = (screenWidth - itemView.getScrollY());

            scroller.startScroll(0, itemView.getScrollY(), delta, 0, Math.abs(delta));
            postInvalidate();
        }
    }

    private void scrollByDistanceX() {
        //

        if (orientation == Orientation.VERTICAL) {
            if (itemView.getScrollX() >= screenWidth / 2) {
                scrollLeft();
            } else if (itemView.getScrollX() <= -screenWidth / 2) {
                scrollRight();
            } else {
                itemView.scrollTo(0, 0);
            }
        } else {
            if (itemView.getScrollY() >= screenWidth / 2) {
                scrollLeft();
            } else if (itemView.getScrollY() <= -screenWidth / 2) {
                scrollRight();
            } else {
                itemView.scrollTo(0, 0);
            }
        }
    }

    public interface RemoveListener {
        void removeItem(RemoveDirection direction, int position);
    }

    public enum RemoveDirection {
        RIGHT, LEFT;
    }

    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    private int getScrollVelocity() {
        if (orientation == Orientation.VERTICAL) {
            velocityTracker.computeCurrentVelocity(1000);
            int velocity = (int) velocityTracker.getXVelocity();
            return velocity;
        } else {
            velocityTracker.computeCurrentVelocity(1000);
            int velocity = (int) velocityTracker.getYVelocity();
            return velocity;
        }
    }

    /**
     * 枚举类型，表示滑动方向
     */
    public static enum Orientation {
        HORIZONTAL(0), VERTICAL(1);

        private int value;

        private Orientation(int i) {
            value = i;
        }

        public int value() {
            return value;
        }

        public static Orientation valueOf(int i) {
            switch (i) {
                case 0:
                    return HORIZONTAL;
                case 1:
                    return VERTICAL;
                default:
                    throw new RuntimeException("[0->HORIZONTAL, 1->VERTICAL]");
            }
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
            if (scroller.isFinished()) {
                if (removeListener == null) {
                    return;
                }

                itemView.scrollTo(0, 0);
                removeListener.removeItem(removeDirection, slidePosition);
            }
        }
    }
}
