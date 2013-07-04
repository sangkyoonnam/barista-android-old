package kr.co.namsang.mb.barista.app;

import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;

public class BNFragmentActivity extends FragmentActivity
{
	public void hideKeyboard() {
		if (getCurrentFocus() != null) {
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}
}
