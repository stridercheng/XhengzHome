package cn.com.chengz.xhengzhome.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/3/18.
 */
public class ViewGroupB extends RelativeLayout {
    private final static String TAG = ViewGroupB.class.getSimpleName();


    public ViewGroupB(Context context) {
        super(context);
    }

    public ViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent--->ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent--->ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent--->ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
