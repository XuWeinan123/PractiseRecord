package com.xwn.practiserecord;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/*
普通RecyclerView没有提供像ListView那样方便的Item点击事件，依靠这个代码可以很方便的实现
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener mOnItemClickListener) {
        onItemClickListener = mOnItemClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childViewUnder = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childViewUnder != null && onItemClickListener != null) {
                    onItemClickListener.onItemLongClick(childViewUnder, recyclerView.getChildPosition(childViewUnder));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View childViewUnder = rv.findChildViewUnder(e.getX(), e.getY());
        if (childViewUnder != null && onItemClickListener != null && gestureDetector.onTouchEvent(e)) {
            onItemClickListener.onItemClick(childViewUnder, rv.getChildPosition(childViewUnder));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}