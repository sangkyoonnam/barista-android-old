package kr.co.namsang.mb.barista.ui.view;


import kr.co.namsang.mb.barista.data.IndexPath;
import kr.co.namsang.mb.barista.util.LogUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class BNListView extends ListView
{
	private static final String LOG_TAG = LogUtils.makeLogTag(BNListView.class);
	
	public BNListView(Context context) {
		super(context);
	}
	
	public BNListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public BNListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		
		if (adapter instanceof BNBaseSectionAdapter) {
			final BNBaseSectionAdapter<?> _adapter = (BNBaseSectionAdapter<?>) adapter;
			setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {					
					BNListView listView = (BNListView) parent;
					position = position - listView.getHeaderViewsCount();
//					if (position >= 0) {
//						int section = _adapter.getSectionForPosition(position);
//						int row = _adapter.getRowForPosition(position);
//						
//						if (_adapter.onCellClickListener != null) {
//							_adapter.onCellClickListener.onCellClick(parent, view, IndexPath.create(section, row));
//						}
//						
//					}
					
					int section = _adapter.getSectionForPosition(position);
			     	int row = _adapter.getRowForPosition(position);  
					if (position == _adapter.getPositionForSection(section)) {
						if (_adapter.onCellClickListener != null) {
							_adapter.onCellClickListener.onSectionHeaderClick(parent, view, section);
						}
					}
					else if (position == _adapter.getPositionForSection(section) + _adapter.getNumberOfRowsInSection(section) + 1) {
						
					}
					else {
						if (_adapter.onCellClickListener != null) {
							_adapter.onCellClickListener.onCellClick(parent, view, IndexPath.create(section, row));
						}
					}
				}
			});
		}
		else if (adapter instanceof BNBaseAdapter) {
			final BNBaseAdapter<?> _adapter = (BNBaseAdapter<?>) adapter;
			setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					BNListView listView = (BNListView) parent;
					position = position - listView.getHeaderViewsCount();
					if (position >= 0) {
						int section = 0;
						int row = position;
						
						if (_adapter.onCellClickListener != null) {
							_adapter.onCellClickListener.onCellClick(parent, view, IndexPath.create(section, row));
						}
					}
				}
			});
		}
	}
}
