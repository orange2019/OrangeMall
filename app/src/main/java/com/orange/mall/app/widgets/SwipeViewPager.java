package com.orange.mall.app.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SwipeViewPager extends ViewPager {


  public SwipeViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SwipeViewPager(Context context) {

    super(context);
  }

  //是否可以滑动
  private boolean swipeable = true;

  //----------禁止左右滑动------------------
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    if (swipeable) {
      return super.onTouchEvent(ev);
    } else {
      return false;
    }

  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent arg0) {
    if (swipeable) {
      return super.onInterceptTouchEvent(arg0);
    } else {
      return false;
    }

  }
  //-------------------------------------------

  /**
   * 设置 是否可以滑动
   *
   * @param
   */
  public void setSwipeable(boolean swipeable_) {
    this.swipeable = swipeable_;
  }
}