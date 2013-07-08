package kr.co.namsang.mb.barista.ui.view;

import kr.co.namsang.mb.barista.data.IndexPath;
import kr.co.namsang.mb.barista.ui.view.BNBaseAdapter.OnCellClickListener;
import kr.co.namsang.mb.barista.util.LogUtils;
import kr.co.namsang.mb.barista.util.UiUtils;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

public class BNPagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener
{
    private ViewPager mPager;
    private boolean mNeedsRedraw = false;
    
    /**
     *	
     */
//    public OnPageChangeListener onPageChangeListener = null;    
//    
//    public interface OnPageChangeListener 
//    {
//    	public abstract void onPageSelected(int position);
//    }
//    
//    public void setOnPageChangeListener(OnPageChangeListener listener) {
//    	onPageChangeListener = listener;
//    }      
 
    /**
     * 
     * @param context
     */
    public BNPagerContainer(Context context) {
        super(context);
        init();
    }
 
    public BNPagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public BNPagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
 
    private void init() {
        // Disable clipping of children so non-selected pages are visible
        setClipChildren(false);
 
        // Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        // You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        if (UiUtils.isHoneycomb())
        	setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
 
    @Override
    protected void onFinishInflate() {
        try {
            mPager = (ViewPager) getChildAt(0);
            mPager.setOnPageChangeListener(this);
        } 
        catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }
 
    public ViewPager getViewPager() {
        return mPager;
    }
 
    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int)ev.getX();
                mInitialTouch.y = (int)ev.getY();
            default:
                ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
        }
 
        return mPager.dispatchTouchEvent(ev);
    }
 
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (mNeedsRedraw) invalidate();
    }
 
    // TODO: 왜 동작하지 않는지 확인
    @Override
    public void onPageSelected(int position) {    	
//    	if (onPageChangeListener != null)
//    		onPageChangeListener.onPageSelected(position);
    }
 
    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}
