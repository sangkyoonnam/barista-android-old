package kr.co.namsang.mb.barista.ui.view;

import kr.co.namsang.mb.barista.data.IndexPath;
import kr.co.namsang.mb.barista.util.LogUtils;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

public class BNGridView extends GridView 
{
	private static final String LOG_TAG = LogUtils.makeLogTag(BNGridView.class);
	
	public BNGridView(Context context) {
		super(context);
	}
	
	public BNGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public BNGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		
		if (adapter instanceof BNBaseAdapter) {
			final BNBaseAdapter<?> _adapter = (BNBaseAdapter<?>) adapter;
			setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					int section = 0;
					int row = position;
					
					if (_adapter.onCellClickListener != null) {
						_adapter.onCellClickListener.onCellClick(parent, view, IndexPath.create(section, row));
					}
				}
			});
		}
	}
}
