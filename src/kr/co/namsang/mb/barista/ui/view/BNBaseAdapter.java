/*
 * 
 * Copyright (c) 2010-2011 Cyrups Co.,Ltd. All Rights Reserved.
 */

package kr.co.namsang.mb.barista.ui.view;

import java.util.ArrayList;
import java.util.List;

import kr.co.namsang.mb.barista.data.IndexPath;
import kr.co.namsang.mb.barista.io.ImageLoader;
import kr.co.namsang.mb.barista.util.LogUtils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BNBaseAdapter<T> extends BaseAdapter 
{	
	private static final String TAG = LogUtils.makeLogTag(BNBaseAdapter.class);
	
	// UI 컨트롤
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int mLayout;
    
    protected ImageLoader mImageLoader; 
 
    // 
    protected List<T> mArrayList = new ArrayList<T>();
    
    /**
     *	accessory view click listener
     */
    public OnCellClickListener onCellClickListener = null;    

    public interface OnCellClickListener 
    {
    	public abstract void onCellClick(final AdapterView<?> parent, final View view, final IndexPath indexPath);
    }

    public void setOnCellClickListener(OnCellClickListener listener) {
    	onCellClickListener = listener;
    }  
          
    /**
     *	accessory view click listener
     */
    protected OnAccessoryViewClickListener onAccessoryViewClickListener = null;    

    public interface OnAccessoryViewClickListener 
    {
    	public abstract void onAccessoryViewClick(int position);
    }

    public void setOnAccessoryViewClickListener(OnAccessoryViewClickListener listener) 
    {
    	onAccessoryViewClickListener = listener;
    }    

    // 생성자
    public BNBaseAdapter(Context context, int layout) 
    {    	
        this.mContext = context;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        
        //this.mInflater = LayoutInflater.from(context);
        this.mLayout = layout; 
        
        this.mImageLoader = new ImageLoader(context);
    }
    
    public BNBaseAdapter(Context context, int layout, ArrayList<T> arrayList) {

        this(context, layout);
        
        this.mArrayList = arrayList;
    }
         
    public void setListData(List<T> arrayList) 
    {    	
        this.mArrayList = arrayList;
    }
    
    public int getCount() 
    {    	
        return mArrayList.size();
    }

    public T getItem(int position) 
    {    	
        return mArrayList.get(position);
    }

    public long getItemId(int position) 
    {    	
        return position;
    }

//	@Override
//    public View getView(int position, View convertView, ViewGroup parent) 
//    {    	
//        ViewHolder holder;        
//        
//        if (convertView == null)  
//        {
//            convertView = mInflater.inflate(mLayout, parent, false);            
//            holder = new ViewHolder();
//            
//            //holder.imageView = (ImageView)convertView.findViewById(kImageView);
//            //holder.title = (TextView)convertView.findViewById(kTitleLabel);
//            //holder.accesoryButton = (ImageButton)convertView.findViewById(kAccesoryButton); 
//            //holder.accesoryButton.setTag((Integer)position);
//              
//            convertView.setTag(holder);            
//        } 
//        else
//        {        	
//            holder = (ViewHolder)convertView.getTag();
//        }
//        
//        return convertView;
//    }
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int section = 0;
     	int row = position; // TODO: 헤더가 있는 경우 다시 계산     	
     	IndexPath indexPath = IndexPath.create(section, row);

     	return getCellForRow(indexPath, convertView, parent);
	}
	
	public abstract View getCellForRow(IndexPath indexPath, View convertView, ViewGroup parent);
    
    public void accesoryButtonClicked(int position) 
    {
    	// do nothing yet
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
    
    public void displayImage(String url, ImageView imageView)
    {	
    	mImageLoader.displayImage(url, imageView);
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