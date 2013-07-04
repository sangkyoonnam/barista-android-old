
package kr.co.namsang.mb.barista.ui.view;

import java.util.ArrayList;
import java.util.List;

import kr.co.namsang.mb.barista.R;
import kr.co.namsang.mb.barista.data.IndexPath;
import kr.co.namsang.mb.barista.io.ImageLoader;
import kr.co.namsang.mb.barista.ui.view.BNBaseAdapter.OnCellClickListener;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public abstract class BNBaseSectionAdapter<T> extends BaseAdapter implements SectionIndexer
{	
	// 태그
	@SuppressWarnings("unused")		
	private static final String LOG_TAG = BNBaseSectionAdapter.class.getSimpleName();
	
	// UI 컨트롤
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int mLayout;
    
    protected ImageLoader mImageLoader; 
    
	//-- --//
    //protected SparseArray<Section> mSections = new SparseArray<Section>();
	//protected List<Pair<Integer, List<T>>> listData = new ArrayList();
    protected List<T> listData; // = new ArrayList<T>();
    
    /**
     *	accessory view click listener
     */
    public OnCellClickListener onCellClickListener = null;    

    public interface OnCellClickListener 
    {
    	public abstract void onCellClick(final AdapterView<?> parent, final View view, final IndexPath indexPath);    	
    	public abstract void onSectionHeaderClick(final AdapterView<?> parent, final View view, final int section);
    }

    public void setOnCellClickListener(OnCellClickListener listener) {
    	onCellClickListener = listener;
    }   
 
    /**
     *	accessory view click listener
     */
    public OnAccessoryViewClickListener onAccessoryViewClickListener = null;    

    public interface OnAccessoryViewClickListener 
    {
    	public abstract void onAccessoryViewClick(View view, IndexPath indexPath);
    }

    public void setOnAccessoryViewClickListener(OnAccessoryViewClickListener listener) {
    	onAccessoryViewClickListener = listener;
    }
    
    /*
	protected OnCellClickListener onCellClickListener = null;   
	
	public interface OnCellClickListener {
		public void onCellClick(AdapterView<?> parent, View view, BNIndexPath indexPath, long id); 
	}
	
    public void setOnCellClickListener(OnCellClickListener listener) {
    	onCellClickListener = listener;
    }
    */

    // 생성자
    public BNBaseSectionAdapter(Context context) {    	
    	this(context, R.layout.list_item_default);
    }    
    
    public BNBaseSectionAdapter(Context context, int layout) {    	
        this.mContext = context;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mLayout = layout; 
        
        this.mImageLoader = new ImageLoader(context);
    }
    
    public BNBaseSectionAdapter(Context context, int layout, List listData) {

        this(context, layout);
        
        this.listData = listData;
    }
         
    public void setListData(List listData) 
    {    	
        this.listData = listData;
    }
    
	public View getFooterViewForSection(int section, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.list_item_empty, parent, false);
		return convertView;
	}
	
	public View getHeaderViewForSection(int section, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.list_item_empty, parent, false);
		return convertView;
	}
    
    public abstract View getCellForRow(IndexPath indexPath, View convertView, ViewGroup parent);
    
	/**
	 * baristar only
	 * @return
	 */
	public int getNumberOfSections() {
		/*
		int res = listData.size();
		if (res > 0)
			return res;
		
		return 1;
		*/
		
		if (listData != null)
			return 1;
	
		return 0;
	}
	
	/**
	 * baristar only
	 * @return
	 */
	public int getNumberOfRowsInSection(int section) {		
		//return listData.get(section).second.size();
		if (listData != null)
			return listData.size();
		
		return 0;
	}
    
    public int getCount() {    	
    	int res = 0;
		for (int i = 0; i < getNumberOfSections(); i++) {
			res += getNumberOfRowsInSection(i) + 2;
		}			
		return res; 
    }
    
	/**
	 * baristar only
	 * @return
	 */
    
    public T getItem(IndexPath indexPath) {		
		return getItem(indexPath.section, indexPath.row);
	}    
    
	public T getItem(int section, int row) {		
		/*
		Pair<Integer, List<T>> pair = listData.get(section);
		return pair.second.get(row);
		*/
		if (listData != null
				&& row < listData.size())
			return listData.get(row);
		
		return null;
	}    

	//@deprecated
	@Override	
    public T getItem(int position) {    			
		return null;
    }

	@Override    
    public long getItemId(int position) {    	
        return position;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int section = getSectionForPosition(position);
     	int row = getRowForPosition(position);     	
     	IndexPath indexPath = IndexPath.create(section, row);

     	if (position == getPositionForSection(section)) {  // 세션 헤더
//     		BNLog.i(LOG_TAG, "[HEADER] section=%d, row= %d, position=%d", section, row, position);
     		return getHeaderViewForSection(section, convertView, parent);
     	}     	
     	else if (position == getPositionForSection(section) + getNumberOfRowsInSection(section) + 1) {  // 세션 푸터
//     		BNLog.i(LOG_TAG, "[FOOTER] section=%d, row= %d, position=%d", section, row, position);
     		return getFooterViewForSection(section, convertView, parent);
     	}
     	else {
//     		BNLog.i(LOG_TAG, "section=%d, row= %d, position=%d", section, row, position);
     		return getCellForRow(indexPath, convertView, parent);
     	}
	}
	
	@Override
	public int getPositionForSection(int section) {		
		int res = 0;
		for (int i = 0; i < getNumberOfSections(); i++) {
			if (i == section) {
				return res;
			}
			res += getNumberOfRowsInSection(i) + 2;			
		}
		
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {		
		int res = 0;
		for (int i = 0; i < getNumberOfSections(); i++) {
			int max_num_of_section = getNumberOfRowsInSection(i) + 2;
			if (position >= res 
					&& position < res + max_num_of_section) {
				return i;
			}
			res += max_num_of_section;
		}
		
		return -1;
	}
	
	/**
	 * baristar only
	 * @return
	 */
	public int getRowForPosition(int position) {
		
		int res = 0;
		for (int i = 0; i < getNumberOfSections(); i++) {
			int max_num_of_section = getNumberOfRowsInSection(i) + 2;
			if (position >= res 
					&& position < res + max_num_of_section) {
				if (position == res
						|| position == res + max_num_of_section - 1)
					return -1;
				else
					return position - res - 1;
			}
			res += max_num_of_section;			
		}
		
		return -1;
	}

	@Override
	public Object[] getSections() {		
		if (listData != null)
			return listData.toArray();
		
		return null;
	}
	
/*
    @SuppressWarnings("unchecked")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {    	
        ViewHolder holder;        
        
        if (convertView == null)  
        {
            convertView = mInflater.inflate(mLayout, parent, false);            
            holder = new ViewHolder();
            
            //holder.imageView = (ImageView)convertView.findViewById(kImageView);
            //holder.title = (TextView)convertView.findViewById(kTitleLabel);
            //holder.accesoryButton = (ImageButton)convertView.findViewById(kAccesoryButton); 
            //holder.accesoryButton.setTag((Integer)position);
              
            convertView.setTag(holder);            
        } 
        else
        {        	
            holder = (ViewHolder)convertView.getTag();
        }
        
        return convertView;
    }
    */
    
    public void reloadData() {    	
        ((Activity)mContext).runOnUiThread(new Runnable() 
        {
            public void run() {
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