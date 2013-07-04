package kr.co.namsang.mb.barista.util;

import java.util.List;

import kr.co.namsang.mb.barista.data.Version;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

public class CommonUtils 
{
	private static final String LOG_TAG = LogUtils.makeLogTag(CommonUtils.class);
	
	private static Context sContext;
	public static void setContext(Context context) {
		sContext = context;
	}
	
	public static String getPackageName() {
    	return sContext.getPackageName();
    }
    
    public static Version getVersion() {
    	PackageManager packageManager = sContext.getPackageManager();
    	Version version = null;
    	try {
			String versionName = packageManager.getPackageInfo(getPackageName(), 0).versionName;
			version = new Version(versionName);
		}
    	catch (NameNotFoundException e) {
			LogUtils.e(LOG_TAG, "error=%s", e.getMessage());
		}    	
    	catch (IllegalArgumentException e) {
    		LogUtils.e(LOG_TAG, "error=%s", e.getMessage());
    	}
    	return version;
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
