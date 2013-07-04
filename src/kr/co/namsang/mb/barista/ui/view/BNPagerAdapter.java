package kr.co.namsang.mb.barista.ui.view;

import java.util.ArrayList;
import java.util.List;

import kr.co.namsang.mb.barista.util.LogUtils;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class BNPagerAdapter<T> extends PagerAdapter 
{
	private static final String TAG = LogUtils.makeLogTag(BNPagerAdapter.class);
	
	// UI 컨트롤
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int layoutId;
    
    // 
    protected List<T> mArrayList = new ArrayList<T>();
    
    public BNPagerAdapter(Context context) {
        this.mContext = context;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);         
    }
    
    public BNPagerAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        
        this.layoutId = layoutId; 
    }
    
    public void setListData(List<T> arrayList) {  	  	
    	this.mArrayList = arrayList;
    }
	
	@Override
	public int getCount() {
		return mArrayList.size();
	}
	
    public T getItem(int position) {    	
        return mArrayList.get(position);
    }
    
    public long getItemId(int position) {    	
        return position;
    }

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

    public void reloadData() 
    {    	
        ((Activity)mContext).runOnUiThread(new Runnable() 
        {
            public void run() 
            {
                notifyDataSetChanged();
            }   
        });
    }
    
    public class ViewHolder 
    {    	
        public View imageView;
        public View accesoryView;
        public View contentView;
        public TextView textLabel;
        public TextView detailTextLabel;      
    }  
}
