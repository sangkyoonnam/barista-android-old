/*
 * 
 * Copyright (c) 2010-2011 Cyrups Co.,Ltd. All Rights Reserved.
 */

package kr.co.namsang.mb.barista.app;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;

import kr.co.namsang.mb.barista.R;
import kr.co.namsang.mb.barista.ui.view.BNTabBarItem;

public class BNTabBarFragmentActivity extends BNFragmentActivity 
{
	private static final String TAG = BNTabBarFragmentActivity.class.getSimpleName();
	
	private static final String CURRENT_TAB = "kCurrentTab"; 
	
	protected TabHost mTabHost;
	protected TabManager mTabManager;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		if (getWindow().getDecorView().getRootView() == null) {
			setContentView(R.layout.fragment_tabbar);
		}	
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);        
	}
	
	// This is supposed to work around a bug causing crashes for
	// java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
//		super.onSaveInstanceState(outState);
//	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// No call for super(). Bug on API Level > 11.
	}
	
	public void initLayout() 
	{		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		//mTabHost.setBackgroundColor(Color.TRANSPARENT);
		
        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
	}
	
	public static class TabManager implements TabHost.OnTabChangeListener 
	{
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final Map<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        
        public TabInfo mLastTab;

        public static final class TabInfo 
        {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            public TabInfo(String _tag, Class<?> _class, Bundle _args) 
            {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        public static class DummyTabFactory implements TabHost.TabContentFactory 
        {
            private final Context mContext;

            public DummyTabFactory(Context context) 
            {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) 
            {
                View view = new View(mContext);
                
                view.setMinimumWidth(0);
                view.setMinimumHeight(0);
                
                return view;
            }
        }

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) 
        {
            mActivity = activity;
            mTabHost = tabHost;                        
            mContainerId = containerId;            
            
            mTabHost.setOnTabChangedListener(this);
        }
        
        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) 
        {
        	tabSpec.setContent(new DummyTabFactory(mActivity));
            final String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) 
            {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
            
            int index = mTabHost.getTabWidget().getChildCount() - 1;
        	mTabHost.getTabWidget().getChildAt(index).setOnClickListener(new View.OnClickListener() 
        	{					
				@Override
				public void onClick(View v) 
				{
	            	onTabSelected(tag);
				}
			});
        }

        public void addTab(TabHost.TabSpec tabSpec, BNFragment frag, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            final String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, frag.getClass(), args);             

            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment == null) {  
            	info.fragment = frag;
            	
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            	ft.add(mContainerId, info.fragment, info.tag);
                ft.commit();
            }
            if (info.fragment != null && !info.fragment.isDetached()) {  
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);

            int index = mTabHost.getTabWidget().getChildCount() - 1;
        	mTabHost.getTabWidget().getChildAt(index).setOnClickListener(new View.OnClickListener() 
        	{					
				@Override
				public void onClick(View v) {
	            	onTabSelected(tag);
				}
			});
        }
        
        public void addTab(BNTabBarItem tabBarItem, BNFragment frag, Bundle args) {
        	
        	final Integer index = mTabHost.getTabWidget().getChildCount();

            TabInfo info = new TabInfo(index.toString(), frag.getClass(), args);
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(index.toString());
            if (info.fragment == null) {  
            	info.fragment = frag;
            	
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            	ft.add(mContainerId, info.fragment, info.tag);
                ft.commit();
            }
            if (info.fragment != null && !info.fragment.isDetached()) 
            {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }
            mTabs.put(index.toString(), info);
            
            //-- --//
            if (index > 0) {
            	tabBarItem.showDivider();
            }
            
        	TabHost.TabSpec tabSpec = mTabHost.newTabSpec(index.toString())
        			.setIndicator(tabBarItem)
        			.setContent(new DummyTabFactory(mActivity));        	
            mTabHost.addTab(tabSpec);           
        	mTabHost.getTabWidget().getChildAt(index).setOnClickListener(new View.OnClickListener() 
        	{					
				@Override
				public void onClick(View v) {
	            	onTabSelected(index.toString());
				}
			});
        }        
        
        public void onTabSelected(String tabId)
        {						
			if (mTabHost.getCurrentTabTag().equals(tabId))
			{
	            if (mLastTab.fragment.getClass().isAssignableFrom(BNNavigationFragment.class))
	            {
	            	BNNavigationFragment navigationFragment = (BNNavigationFragment)mLastTab.fragment;
	            	if (navigationFragment.navigationGroup.getLevel() > 0)
	            	{ 
	            		// 네비게이션 프래그먼트일 경우 루트로 이동
	            		navigationFragment.popRootFragment(true);
	            	}
	            }
			}	
			else
			{
				mTabHost.setCurrentTabByTag(tabId);
			}
        }

        @Override
        public void onTabChanged(String tabId) 
        {
        	//BNLogger.i(TAG, "%s: tabId:%s", new Throwable().getStackTrace()[0].getMethodName(), tabId);
        	
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) 
            {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) 
                {
                    if (mLastTab.fragment != null) 
                    {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) 
                {          	
                    if (newTab.fragment == null) 
                    {
                        newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } 
                    else 
                    {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }
                
        public Fragment getSelectedFragment()
        {
        	Fragment fragment = null;
        	if (mLastTab != null)
        	{
                if (mLastTab.fragment.getClass().isAssignableFrom(BNNavigationFragment.class))
                {
                	BNNavigationFragment navigationFragment = (BNNavigationFragment)mLastTab.fragment;
                	fragment = navigationFragment.navigationGroup.getLastFragment();
                }
                else
                {
                	fragment = mLastTab.fragment;
                }
        	}
            
            return fragment;
        }
    }
}
