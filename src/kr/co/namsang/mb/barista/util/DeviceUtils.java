package kr.co.namsang.mb.barista.util;

public class DeviceUtils 
{
	private static final String LOG_TAG = LogUtils.makeLogTag(DeviceUtils.class);
	
	/**
	 * 디바이스에 따라 
	 * 
	 * @param imei
	 * @return
	 */
	public static String getIMEI(String imei) { 
		int[] imei_check = new int[15];		
		String str = "";
		int digit = 0;
		for (int i = 0; i < imei_check.length; i++) {
			if (i < imei_check.length - 1) {
				imei_check[i] = Integer.parseInt(imei.substring(i, i + 1));
				int temp = imei_check[i];
				if (i % 2 != 0) 
					temp = eachSum(temp * 2);				
				digit += temp;
			}
			else {
				digit %= 10;
				imei_check[imei_check.length - 1] = 10 - digit;
			}
			
			str += imei_check[i];			
		}
		
		return str;
	}
	
	// TODO: 위치 변경
	public static int eachSum(int num) {		
		int total = 0;
		while (num > 0) {
			total += num % 10;
			num /= 10;
		}
		
		return total;
	}
}
