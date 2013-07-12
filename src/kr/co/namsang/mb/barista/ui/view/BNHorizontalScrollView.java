package kr.co.namsang.mb.barista.ui.view;

import kr.co.namsang.mb.barista.data.IndexPath;
import kr.co.namsang.mb.barista.ui.view.BNBaseAdapter.OnCellClickListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;

public class BNHorizontalScrollView extends HorizontalScrollView 
{
    
    public BNHorizontalScrollView(Context context) {
		super(context);
	}
    
    public BNHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
    
    public BNHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
     *	
     */
    public OnScrollViewListener onScrollViewListener = null;    

    public interface OnScrollViewListener 
    {
    	public abstract void onScrollChanged(int x, int y, int oldx, int oldy);
    }

    public void setOnScrollViewListener(OnScrollViewListener listener) {
    	onScrollViewListener = listener;
    }
    
    //-- --//
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
    	super.onScrollChanged(x, y, oldx, oldy);
    	if (onScrollViewListener != null)
    		onScrollViewListener.onScrollChanged(x, y, oldx, oldy);
    }    
}
