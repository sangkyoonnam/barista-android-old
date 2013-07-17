package kr.co.namsang.mb.barista.app;

import kr.co.namsang.mb.barista.util.LogUtils;

import android.support.v4.app.Fragment;

public class BNFragment extends Fragment 
{	
	// 태그
	@SuppressWarnings("unused")
	private static final String LOG_TAG = LogUtils.makeLogTag(BNFragment.class);
	
	public BNNavigationFragment navigationFragment;  // 네비게이션
	
	//-- --//	
    public void initLayout() {  // UI 오브젝트 연결과 이벤트를 분리
		retrieveUiObjRefs();
		registerUiActionHandler();
    }
    
    //-- --// 
    public void retrieveUiObjRefs() {}    
    public void registerUiActionHandler() {}    

    public void prepareToLoad() {}    

    public void reloadData() {}
}