
package kr.co.namsang.mb.barista.app;

import java.util.ArrayList;
import java.util.List;

import kr.co.namsang.mb.barista.R;
import kr.co.namsang.mb.barista.util.LogUtils;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BNNavigationFragment extends BNFragment 
{
	// 태그		
	@SuppressWarnings("unused")
	private static final String LOG_TAG = LogUtils.makeLogTag(BNNavigationFragment.class);
    
	public NavigationGroup navigationGroup = new NavigationGroup();	
	
	//-- --//
    private View mRootView;     
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		    	
    	super.onCreateView(inflater, container, savedInstanceState);	     
    	
    	mRootView = inflater.inflate(R.layout.fragment_navigation, null); 	

    	BNFragment frag = navigationGroup.getLastFragment();	
		if (frag != null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.navigation_content, frag, frag.getTag());
			ft.commit();
		}
		
		return mRootView;
	}
    
    // <Fixed> 중요 블랙아웃 방지    
    /*
    @Override 
    public void onDestroyView() {
        try {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            BNFragment fragment = navigationGroup.getLastFragment();
            if (fragment != null) {
                ft.remove(fragment);
                ft.commit();
            }
        }
        catch(Exception e) {
            BNLog.e(LOG_TAG, "error=%s", e.getMessage());
        }

        super.onDestroyView();
    }
    */
    
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		
        prepareToLoad(); 
	}
	
	public void initRootFragment(BNFragment fragment) {
		navigationGroup = new NavigationGroup();
		if (navigationGroup != null) {
			navigationGroup.addFragment(fragment);
			fragment.navigationFragment = this;
		}
	}
	
	public void pushFragment(BNFragment frag, boolean animated) { 	
		navigationGroup.addFragment(frag);
		frag.navigationFragment = this;
		
		if (navigationGroup.getLevel() > 0)
		{			
			BNFragment fragment = navigationGroup.getLastFragment();			
			if (fragment != null)
			{
				FragmentTransaction ft = getFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				if (animated)
					ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
				ft.replace(R.id.navigation_content, fragment, fragment.getTag());

				// Commit the transaction
				ft.commit();				
			}
		}
	}
	
	public void popFragment(boolean animated)
	{
		if (navigationGroup.getLevel() > 0) 
		{
//			BNLog.i(LOG_TAG, "pop to fragment");
			
			navigationGroup.removeLastFragment();	
			
			BNFragment fragment = navigationGroup.getLastFragment();	
//			BNLog.i(LOG_TAG, "level=%d, fragment=%s", navigationGroup.getLevel(), fragment.getClass());
			if (fragment != null)
			{
				FragmentTransaction ft = getFragmentManager().beginTransaction();

				if (animated)
					ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
				ft.replace(R.id.navigation_content, fragment, fragment.getTag());

				ft.commit();
			}
		}
	}
	
	/**
	 * 첫번쨰 프래그먼트로 네비게이션 이동
	 * 
	 * @param animated
	 */
	public void popRootFragment(boolean animated) {
		if (navigationGroup.getLevel() > 0)  {
			while (navigationGroup.getLevel() > 0)
				navigationGroup.removeLastFragment();	
			
			BNFragment fragment = navigationGroup.getLastFragment();			
			if (fragment != null)
			{
				FragmentTransaction ft = getFragmentManager().beginTransaction();

				if (animated)
					ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
				ft.replace(R.id.navigation_content, fragment, fragment.getTag());

				ft.commit();
			}
		}
	}
	
	// TODO: navigationGroup을 public으로 변경하였기 때문에 불필요, 추후 삭제
	public int getLevel() {
		return navigationGroup.getLevel();
	}
	
	/**
	 * 
	 * @author sangkyoonnam
	 *
	 */
	public class NavigationGroup 
	{
		protected List<BNFragment> stack = new ArrayList<BNFragment>();
		
		/**
		 * 프래그먼트를 스택에 추가
		 * add fragment to stack
		 * 
		 * @param fragment
		 */
		public void addFragment(BNFragment fragment) {
			stack.add(fragment);
		}
		
		/**
		 * 마지막에 추가된 프래그먼트를 스택에서 제거
		 * remove last fragment at stack
		 * 
		 * @param fragment
		 */
		public void removeLastFragment() {
			stack.remove(getLevel());
		}
		
		/**
		 * 스택의 현재 레벨을 반환
		 * get current stack level
		 * 
		 * @return level
		 */
		public int getLevel() {
			return stack.size() - 1;
		}
		
		/**
		 * 최신 프래그먼트 반환
		 * get latest added fragment
		 * 
		 * @return fragment
		 */
		public BNFragment getLastFragment() {
			int lvl = getLevel();
			if (lvl >= 0)
				return stack.get(lvl);
			
			return null;
		}
	}
}
