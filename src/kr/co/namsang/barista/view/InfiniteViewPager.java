/*
 * Copyright (C) 2010 Namsang ICT Group
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package kr.co.namsang.barista.view;

import java.util.LinkedList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class InfiniteViewPager extends ViewPager
{
	private final int MAX_VIEW_GROUP_CNT = 3;
	
	private boolean bEnabled = false;	
	
	private LinkedList<View> mViews = new LinkedList<View>();
	private ViewGroup[] mCompenstateViews = new ViewGroup[MAX_VIEW_GROUP_CNT];
	
	private int mCompensateModeCount = 0;
	
	private InfiniteOnPageChangeListener mInfiniteOnPageChangeListener;
	
	public InfiniteViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setAdapter(mPageViewAdapter);
		this.setOnPageChangeListener(mOnPageChangeListener);
		if (bEnabled)
			this.setCurrentItem(1, false);
	}
	
	public void addPage(View view) {		
		mViews.add(view);
	}
	
	public void setListener(InfiniteOnPageChangeListener listener) {
		mInfiniteOnPageChangeListener = listener;
	}
	
	public int getCompensate(int position) {
		if (position < 0)
			position = mViews.size() - 1;
		else if (position > mViews.size() - 1)
			position = 0;
		
		return position;
	}
	
	public void setEnabledInfinite(boolean enabled) {
		bEnabled = enabled; 
		mPageViewAdapter.notifyDataSetChanged();
	}
	
	public void reloadData() {
		mPageViewAdapter.notifyDataSetChanged();
	}
	
	// cnt가 3개 이하인 경우가 고려되지 않음 
	public void setView() {
		if (bEnabled) {			
			for (int i = 0; i < MAX_VIEW_GROUP_CNT; i++) {
				if (mCompenstateViews[i] == null) 
					mCompenstateViews[i] = new FrameLayout(getContext());
				mCompenstateViews[i].removeAllViews();
			}								
			int MAX_VIEW_SIZE = mViews.size() - 1;
			int MIN_VIEW_SIZE = 0;			
			if (mCompensateModeCount < MIN_VIEW_SIZE)
				mCompensateModeCount = MAX_VIEW_SIZE;
			else if (mCompensateModeCount > MAX_VIEW_SIZE)
				mCompensateModeCount = MIN_VIEW_SIZE;			
			int curr = mCompensateModeCount;
			int prev = mCompensateModeCount - 1 < MIN_VIEW_SIZE ? 
					MAX_VIEW_SIZE : mCompensateModeCount - 1;
			int next = mCompensateModeCount + 1 > MAX_VIEW_SIZE ? 
					MIN_VIEW_SIZE : mCompensateModeCount + 1;						
			mCompenstateViews[0].addView(mViews.get(prev));
			mCompenstateViews[1].addView(mViews.get(curr));
			mCompenstateViews[2].addView(mViews.get(next));
		}
	}
	
	/**
	 * 
	 */
	private PagerAdapter mPageViewAdapter = new PagerAdapter() {
		@Override 
		public int getCount() {
			if (!bEnabled) { 
				return mViews.size();
			}
			else {
				if (mViews.size() == 1 
						|| mViews.size() == 2) {
					// 지원하지 않음
				}				
				return 3;
			}
		}

		@Override 
		public Object instantiateItem(View collection, int position) {
			if (mViews.size() == 0) return null;			
			Object view = null;		
			if (!bEnabled)
				view = mViews.get(position);	
			else 
				view = mCompenstateViews[position];				
			((ViewPager) collection).addView((View) view);		
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object view) {
			container.removeView((View) view);
		}

		@Override 
		public boolean isViewFromObject(View view, Object object) {
			return view == ((View) object);
		}

		@Override 
		public void finishUpdate(View arg0) {}

		@Override 
		public void restoreState(Parcelable arg0, ClassLoader arg1) {}

		@Override 
		public Parcelable saveState() {
			return null;
		}

		@Override 
		public void startUpdate(View arg0) {}
	}; 
	
	private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() 
	{
		private int mDirection;
		private int mCurrent = (bEnabled) ? 1 : 0;
		
		private final int LEFT = 0;
		private final int RIGHT = 2;
		
		@Override
		public void onPageScrollStateChanged(int state) {
			if (!bEnabled) return;			
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				int flag = 0;
				
				
				
				
//				do {
					switch (mDirection) {
					case LEFT:
						mCompensateModeCount -= 1;
//						LogUtils.e(VIEW_LOG_TAG, "LEFT");
//						View lastView = mViews.getLast().getChildAt(0);
//						mViews.getLast().removeAllViews();
//						for (int i = mViews.size() - 1; i > 0; i--) {
//							View view = mViews.get(i - 1).getChildAt(0);
//							mViews.get(i - 1).removeAllViews();
//							mViews.get(i).addView(view);
//						}
//						mViews.getFirst().addView(lastView);
						
						
						break;
					case RIGHT:
						mCompensateModeCount += 1;
//						LogUtils.e(VIEW_LOG_TAG, "RIGHT");
//						View firstView = mViews.getFirst().getChildAt(0);
//						mViews.getFirst().removeAllViews();
//						for (int i = 0; i < mViews.size() - 1; i++) {
//							View view = mViews.get(i + 1).getChildAt(0);
//							mViews.get(i + 1).removeAllViews();
//							mViews.get(i).addView(view);
//						}
//						mViews.getLast().addView(firstView);
					}
//				} while (mViews.get(1).getChildAt(0).getTag() != null);
								
//				for (int i = 0; i < 3; i++) {
//					mCompenstateViews[i].removeAllViews();
//				}
//								
//				mCompensateModeCount = getCompensate(mCompensateModeCount);
//				
//				mCompenstateViews[0].addView(mViews.get(getCompensate(mCompensateModeCount - 1)));
//				mCompenstateViews[1].addView(mViews.get(mCompensateModeCount));
//				mCompenstateViews[2].addView(mViews.get(getCompensate(mCompensateModeCount + 1)));
//				
//				
				
					setView();
					InfiniteViewPager.this.setCurrentItem(1, false);
				
				
			}
			else if (state == ViewPager.SCROLL_STATE_DRAGGING && mCompensateModeCount > 0) {
//				LogUtils.e(VIEW_LOG_TAG, "SCROLL_STATE_DRAGGING");
				for (int i = 0; i < 2; i++) {
//					Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
//					mViews.get(i).getChildAt(0).layout(0, 0, display.getWidth(), display.getHeight());
//					mViews.get(i).getChildAt(0).setDrawingCacheEnabled(true);
//					mViews.get(i).getChildAt(0).buildDrawingCache();
//					Bitmap b = mViews.get(i).getChildAt(0).getDrawingCache();
//					String path = CACHE_DIR + i + ".jpg";
//					try {
//						b.compress(CompressFormat.JPEG, 95, new FileOutputStream(path));
//					}
//					catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
//					mCompensateImageViews[i].setImageDrawable(Drawable.createFromPath(path));
				}
			}
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		@Override
		public void onPageSelected(int position) {
			mDirection = position;
//			LogUtils.e(VIEW_LOG_TAG, "mDirection=%d", mDirection);
			if (bEnabled) {
				mCurrent += (mDirection == LEFT) ? -1 : 1;
				if (mCurrent != 0
						&& mViews.size() != 0)
					mCurrent %= mViews.size();
			}
			else {
				mCurrent = position;
			}
//			LogUtils.e(VIEW_LOG_TAG, "mCurrent=%d", mCurrent);
			if (mInfiniteOnPageChangeListener != null) {
				mInfiniteOnPageChangeListener.onPageChanged(mCurrent);
			}	
		}
	};
	
	public interface InfiniteOnPageChangeListener {
		public abstract void onPageChanged(int position);
	}
}
