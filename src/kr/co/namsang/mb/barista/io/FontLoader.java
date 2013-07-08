package kr.co.namsang.mb.barista.io;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

public class FontLoader 
{
	private static SparseArray<Typeface> fonts = new SparseArray<Typeface>();
	
//	/**
//	 * Returns a loaded custom font based on it's identifier. 
//	 * 
//	 * @param context - the current context
//	 * @param fontIdentifier = the identifier of the requested font
//	 * 
//	 * @return Typeface object of the requested font.
//	 */
//	public static Typeface getTypeface(Context context, int fontIdentifier) {
//	    if (!fontsLoaded) {
//	        loadFonts(context);
//	    }
//	    return fonts[fontIdentifier];
//	}
//	
//	private static void loadFonts(Context context) {
//	    for (int i = 0; i < NUM_OF_CUSTOM_FONTS; i++) {
//	        fonts[i] = Typeface.createFromAsset(context.getAssets(), fontPath[i]);
//	    }
//	    fontsLoaded = true;
//
//	}
//	
//	private static void loadFonts(Context context, String path) {
//	    Typeface tf = Typeface.createFromAsset(context.getAssets(), path);
//	    fonts.put(idetifier, tf);
//	}
}
