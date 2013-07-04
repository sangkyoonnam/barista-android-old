package kr.co.namsang.mb.barista.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

public class SharedUtils 
{
	private static final String LOG_TAG = LogUtils.makeLogTag(SharedUtils.class);
	
	private static Context sContext;
	public static void setContext(Context context) {
		sContext = context;
	}
	
	public static String getBaseIdentifier() {
    	return sContext.getPackageName();
    }
	
    public static String getBaseVersion() {
    	PackageManager packageManager = sContext.getPackageManager();
    	
    	String res = null;
    	try {
			res = packageManager.getPackageInfo(getBaseIdentifier(), 0).versionName;
		}
    	catch (NameNotFoundException e) {
//			BNLogger.e(TAG, e.getLocalizedMessage());
		}
    	
    	return res;
    }
    
	public static boolean isRunningProcess(Context context, String packageName) {		 
		ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RunningAppProcessInfo> list = manager.getRunningAppProcesses();

		for (RunningAppProcessInfo procInfo : list) {          
			if (procInfo.processName.equals(packageName))                                   
				return true;          
		}

		return false;
	}

	public static boolean isRunningService(Context context, String packageName) {		 
		ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RunningServiceInfo> list = manager.getRunningServices(1000);     

		for (RunningServiceInfo procInfo : list) {                                
			if (procInfo.service.getClassName().equals(packageName))                                   
				return true;
		}

		return false;
	}

	public static int getCallState(Context context) {
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getCallState();
	}
}
