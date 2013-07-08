package kr.co.namsang.mb.barista.app;

import kr.co.namsang.mb.barista.util.LogUtils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BNActivity extends FragmentActivity 
{
	// 태그
	@SuppressWarnings("unused")
	private static final String LOG_TAG = LogUtils.makeLogTag(BNActivity.class);
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
    }
	
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
}
