/**
 * 
 * Nam Sangkyoon
 * e-mail: sknam@namsangmobile.com
 * 
 */

package kr.co.namsang.mb.barista.data;

import java.util.regex.Pattern;

import kr.co.namsang.mb.barista.util.LogUtils;

public class Version implements Comparable<Version>
{
	@SuppressWarnings("unused")
	private static final String LOG_TAG = LogUtils.makeLogTag(Version.class);
	
	private static final int MAX_VERSION_WIDTH = 4;
	
	private String version;
	
	public Version(String version)
			throws IllegalArgumentException {
		if (version == null)
			throw new IllegalArgumentException("version can not be null");
		if (!version.matches("[0-9]+(\\.[0-9]+)*")) 
			throw new IllegalArgumentException("invalid format");

		this.version = version;
	}
	
	@Override
	public String toString() {
		return version;
	}

	@Override
	public int compareTo(Version that) {
//		if (that == null)
//			return 1;
//		String[] thisParts = this.toString().split("\\.");
//		String[] thatParts = this.toString().split("\\.");
//		int length = Math.max(thisParts.length, thatParts.length);
//		for (int i = 0; i < length; i++) {
//			int thisPart = i < thisParts.length ? 
//					Integer.parseInt(thisParts[i]) : 0;
//			int thatPart = i < thatParts.length ? 
//					Integer.parseInt(thatParts[i]) : 0;	
//			if (thatPart < thisPart)  //  현재버전이 더 높은 경우
//				return  1;
//			else if (thisPart < thatPart)  // 현재버전이 더 낮은 경우
//				return -1;
//		}
//		
//		return 0;  // 버전이 같은 경우
		
		if (that == null)
			return 1;
		String s1 = normalisedVersion(version);
		String s2 = normalisedVersion(that.toString());
		if (s1.compareTo(s2) > 0)  // 현재 버전이 더 높은 경우
			return  1;
		else if (s1.compareTo(s2) < 0)  // 현재 버전이 더 낮은 경우
			return -1;
		else   // 버전이 같은 경우
			return  0;
	}
	
	public static Version parse(String version) {
		return new Version(version);
	}
	
	public static String normalisedVersion(String version) {
        return normalisedVersion(version, ".", MAX_VERSION_WIDTH);
    }

    public static String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }

}
