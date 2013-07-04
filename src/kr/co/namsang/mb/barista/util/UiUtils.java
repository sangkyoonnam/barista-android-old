package kr.co.namsang.mb.barista.util;

import android.content.Context;
import android.os.Build;

public class UiUtils {
	
	public static Context context = null;
	
	public static void setContext(Context context) {
		UiUtils.context = context;
	}
	
    public static boolean isGingerbread() {
        // Can use static final constants like HONEYCOMB, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean isHoneycomb() {
        // Can use static final constants like HONEYCOMB, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }
	
    public static int dpToPx(int dp) {
    	return dpToPx(UiUtils.context, dp);
    }
    
    public static int dpToPx(Context context, int dp) {
    	if (context != null) {
    		float density = context.getResources().getDisplayMetrics().density;
    		return Math.round((float)dp * density);
    	}
    	
    	return 0;
    }
    
    public static int pxToDp(int px) {
    	return pxToDp(UiUtils.context, px);
    }
    
    public static int pxToDp(Context context, int px) {
    	if (context != null) {
    		float density = context.getResources().getDisplayMetrics().density;
    		return Math.round((float)px / density);
    	}
    	
    	return 0;
    }
}
