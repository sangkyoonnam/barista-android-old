package kr.co.namsang.mb.barista.app;

import kr.co.namsang.mb.barista.util.LogUtils;

import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;

public class BNFragmentActivity extends FragmentActivity
{
	// 태그
	@SuppressWarnings("unused")
	private static final String LOG_TAG = LogUtils.makeLogTag(BNFragmentActivity.class);
	
	//-- --//
    public void initLayout() {
		retrieveUiObjRefs();
		registerUiActionHandler();
    }
    
    //-- --// 
    public void retrieveUiObjRefs() {}    
    public void registerUiActionHandler() {}    

    public void prepareToLoad() {}    

    public void reloadData() {}
	
    /**
     * 현재 액티비티에 키보드가 표시된 경우 숨김
     */
	public void hideKeyboard() {  
		if (getCurrentFocus() != null) {
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}
}
