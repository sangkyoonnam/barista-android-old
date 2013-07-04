package kr.co.namsang.mb.barista.util;

import android.util.Log;

public class LogUtils 
{	
	private static final String LOG_PREFIX = "@@@@";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 64;
    
    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }
	
    @SuppressWarnings("rawtypes")
	public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }
    
public static String prefix = "@@@@ ";
	
	private static boolean useTraceLog = true;
	public static void setUseTraceLog(boolean enable) { useTraceLog = enable; }
	
	private static boolean useInfoLog = true;
	public static void setUseInfoLog(boolean enable) { useInfoLog = enable; }
	
	private static boolean useWarnLog = true;
	public static void setUseWarnLog(boolean enable) { useWarnLog = enable; }
	
	private static boolean useErrorLog = true;
	public static void setUseErrorLog(boolean enable) { useErrorLog = enable; }
	
	
	public static String getHashStr(Object arg) {
		
		if (arg == null) {
			return "null";
		} else {
			return Integer.toString(arg.hashCode());
		}
			
			
	}
	
	
	public static class CodePosition
	{
		public String	className;
		public String	methodName;
		public String	fileName;
		public int		lineNumber;
	}
	
	
	public static CodePosition getCodePosition()
	{
		return getCodePositionInternal();
	}
	
	
	private static CodePosition getCodePositionInternal()
	{
		StackTraceElement traceInfo = (new Throwable().getStackTrace())[2];
		
		CodePosition res = new CodePosition();

		res.className = traceInfo.getClassName();
		res.className = res.className.substring(res.className.lastIndexOf('.')+1);
		res.methodName = traceInfo.getMethodName();
		res.fileName = traceInfo.getFileName();
		res.lineNumber = traceInfo.getLineNumber();
		
		return res;
	}
	
	
	private static String combineArgumentString(Object...args)
	{
		StringBuilder sb = new StringBuilder(100);
		for (Object obj: args)
		{
			if (0 < sb.length())
				sb.append(", ");
			
			if (obj == null)
				sb.append("[null]");
			else
				sb.append("[" + obj.toString() + "]");
		}
		
		return sb.toString();
	}
	
	
	// 트레이스 로그 - 함수 불림 (시작 끝 구분없이 그냥 하나로 남기고 싶을 때)
	public static void FUNCTION_CALL(String tag, Object... args)
	{
		if (useTraceLog == false)
			return;
		
		
		CodePosition callerPos = getCodePositionInternal();
		
		if ((args == null) || (args.length <= 0))
		{
			Log.v(tag, String.format("\t>> FUNC CALL   << - %s.%s (%s, %d)",
					callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber));
			return;
		}
		

		Log.v(tag,
				String.format("\t>> FUNC CALL   << - %s.%s (%s, %d) [%s]",
						callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber, combineArgumentString(args)
				)
		);
	}
	
	
	// 트레이스 로그 - 함수 시작
	public static void FUNCTION_START(String tag, Object... args)
	{
		if (useTraceLog == false)
			return;
		
		
		CodePosition callerPos = getCodePositionInternal();
		
		Log.v(tag, " ");
		
		if ((args == null) || (args.length <= 0))
		{
			Log.v(tag, String.format("\t^^ FUNC START  >> - %s.%s (%s, %d)",
					callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber));
			return;
		}

		
		Log.v(tag,
				String.format("\t^^ FUNC START  >> - %s.%s (%s, %d) [%s]",
						callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber, combineArgumentString(args)
				)
		);
	}

	
	// 트레이스 로그 - 함수종료 반환, 반환할 결과값을 기록할 수 있음, 반환값은 보통 하나지만 필요에 따라 다른 값들을 기록 해 넣을 수도 있음.
	public static void FUNCTION_RETURN(String tag, Object... args)
	{
		if (useTraceLog == false)
			return;
		
		CodePosition callerPos = getCodePositionInternal();
		
		if ((args == null) || (args.length <= 0))
		{
			Log.v(tag, String.format("\t__ FUNC RETURN << - %s.%s (%s, %d)", callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber));
			return;
		}
		

		Log.v(tag,
				String.format("\t__ FUNC RETURN << - %s.%s (%s, %d) [%s]",
						callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber, combineArgumentString(args)
				)
		);
	}
	
	
	
	// 트레이스 로그 - 함수 종료 반환, 로그 기록 후 실제 반환 직전에 다른 함수가 호출되는 경우, 리턴 구조문은 그대로 지키려고 할때. (예: return core.getInfo(); )
	public static void FUNCTION_RETURN_NEXT(String tag)
	{
		if (useTraceLog == false)
			return;
		
		CodePosition callerPos = getCodePositionInternal();
		
		Log.v(tag, String.format("\t__ FUNC RETURN NEXT << - %s.%s (%s, %d)", callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber));				
	}

	
	// Log.i와 유사하나 포멧스트링을 지원
	public static void i(String tag, String msgFormat, Object... args)
	{
		if (useInfoLog == false)
			return;
		
		Log.i(tag, prefix + String.format(msgFormat, args));
	}
	
	
	// Log.w와 유사하나 포멧스트링을 지원
	public static void w(String tag, String msgFormat, Object... args)
	{
		if (useWarnLog == false)
			return;
		
		CodePosition callerPos = getCodePositionInternal();
		
		Log.w(tag, prefix + String.format("%s [%s.%s - %s, %d]", String.format(msgFormat, args), callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber));
	}
	
	
	// Log.e와 유사하나 포멧스트링을 지원
	public static void e(String tag, String msgFormat, Object... args)
	{
		if (useErrorLog == false)
			return;
		
		CodePosition callerPos = getCodePositionInternal();
		
		Log.e(tag, prefix + String.format("%s [%s.%s - %s, %d]", String.format(msgFormat, args), callerPos.className, callerPos.methodName, callerPos.fileName, callerPos.lineNumber));
	}
}
