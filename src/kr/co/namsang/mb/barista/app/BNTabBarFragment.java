

package kr.co.namsang.mb.barista.app;

import java.util.HashMap;
import java.util.Map;

import kr.co.namsang.mb.barista.R;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class BNTabBarFragment extends BNFragment 
{ 
	// 태그		
	@SuppressWarnings("unused")
	private static final String TAG = BNTabBarFragment.class.getSimpleName();
    
    // UI 컨트롤
    protected View mRootView;   
    protected BNFragmentActivity mParentActivity;
    
    protected TabHost mTabHost;
	protected TabManager mTabManager;    
    
    protected int mCurrentTab;
        
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {		    	
    	super.onCreateView(inflater, container, savedInstanceState);	    	
    	
    	mRootView = inflater.inflate(R.layout.fragment_tabbar, null); 	
    	mParentActivity = (BNFragmentActivity)getActivity();
		
    	if (mRootView != null) {
			mTabHost = (TabHost)mRootView.findViewById(android.R.id.tabhost);
			mTabHost.setup();
			
	        mTabManager = new TabManager(getActivity(), mTabHost, R.id.realtabcontent);
    	}
		
		return mRootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{		
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		
        prepareToLoad(); 
	}
	
	public void initLayout() 
	{		

	}
	
	public void prepareToLoad()
	{
		
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

        public void addTab(TabHost.TabSpec tabSpec, BNFragment frag, Bundle args) 
        {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            final String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, frag.getClass(), args);             

            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment == null)
            {  
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
                //mActivity.getSupportFragmentManager().executePendingTransactions();
                new setFragmentTask().execute();  // TODO: 프래그먼트에서 동작할 때는 다른 쓰레드로 이유는 아직 알수 없음
            }
        }
        
        private class setFragmentTask extends AsyncTask<Void,Void,Void>
        {
        	protected Void doInBackground(Void... params) 
            {
                return null;
            }

        	@Override
			protected void onPostExecute(Void result) 
			{
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
        
        public Fragment getSelectedNavigationFragment()
        {
        	Fragment fragment = null;
        	if (mLastTab != null)
        	{
                if (mLastTab.fragment.getClass().isAssignableFrom(BNNavigationFragment.class))
                {
                	BNNavigationFragment navigationFragment = (BNNavigationFragment)mLastTab.fragment;
                	fragment = navigationFragment;
                }
                else
                {
                	fragment = mLastTab.fragment;
                }
        	}
            
            return fragment;
        }
    }
	
	public void setHideDefaultTabBar(boolean bHidden)
	{
		View tabBar = (View)mRootView.findViewById(android.R.id.tabs);
		
		if (!bHidden)
			tabBar.setVisibility(View.VISIBLE); 
		else
			tabBar.setVisibility(View.GONE); 
	}
}