package kr.co.namsang.mb.barista.io;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

public class FontUtils {

	public static void setBold(View view) {
		
		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			tv.setPaintFlags(tv.getPaintFlags() 
					| Paint.FAKE_BOLD_TEXT_FLAG);
		}
	}
}
