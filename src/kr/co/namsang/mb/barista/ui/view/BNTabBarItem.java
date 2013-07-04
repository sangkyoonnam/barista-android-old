package kr.co.namsang.mb.barista.ui.view;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BNTabBarItem extends LinearLayout 
{	
	private static int n = 0;
	private static final int kPadding = 8;
	
	private Context context;

	private View _backgroundView;
	private View _contentView;
	private ImageView _dividerView;
	private ImageView _imageView;
	private TextView _titleLabel;
	
	public TextView getTitleLabel() {
		return _titleLabel;
	}
	
	public BNTabBarItem(Context context) {
		super(context);
		
		this.context = context;
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT, 
				1.0f));
		this.setOrientation(LinearLayout.HORIZONTAL);	
		this.setBackgroundColor(Color.TRANSPARENT);
		/*
		{
		_dividerView = new ImageView(context);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(1, (int)convertDpToPixel(47, context));
		lp.gravity = Gravity.CENTER_VERTICAL  | Gravity.LEFT;
		_dividerView.setLayoutParams(lp);
		_dividerView.setBackgroundColor(Color.GRAY);
		_dividerView.setVisibility(View.GONE);
		this.addView(_dividerView);		
		}
		*/
		FrameLayout frameLayout = new FrameLayout(context);
		if (frameLayout != null) {
			frameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					LayoutParams.MATCH_PARENT));
					
			_backgroundView = new FrameLayout(context);
			((FrameLayout) _backgroundView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	                LayoutParams.MATCH_PARENT));
			frameLayout.addView(_backgroundView);
			
			_contentView = new LinearLayout(context); 
			((LinearLayout) _contentView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	                LayoutParams.MATCH_PARENT));
			((LinearLayout) _contentView).setOrientation(LinearLayout.VERTICAL);	
			((LinearLayout) _contentView).setGravity(Gravity.CENTER);	
			//((LinearLayout) _contentView).setPadding(kPadding, kPadding, kPadding, kPadding);
			
			if (_contentView != null) {
				_imageView = new ImageView(context);
				_imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		                LayoutParams.WRAP_CONTENT));
				((LinearLayout) _contentView).addView(_imageView);
				
				_titleLabel = new TextView(context);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
		                LayoutParams.WRAP_CONTENT);
				lp.topMargin = 4;
				_titleLabel.setLayoutParams(lp);				
				_titleLabel.setGravity(Gravity.CENTER);
				((LinearLayout) _contentView).addView(_titleLabel);
				
				frameLayout.addView(_contentView);			
			}
			
			
			_dividerView = new ImageView(context);
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(1, (int)convertDpToPixel(47, context));
			lp.gravity = Gravity.CENTER_VERTICAL  | Gravity.LEFT;
			_dividerView.setLayoutParams(lp);
			_dividerView.setBackgroundColor(Color.GRAY);
			_dividerView.setVisibility(View.GONE);
			frameLayout.addView(_dividerView);
		
			
			this.addView(frameLayout);
		}
	}
	
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}
	
	public void showDivider() {
		_dividerView.setVisibility(View.VISIBLE);
	}
	
	public void setBackgroundImage(int drawable) {				
		Resources r = getResources();
		
		_backgroundView.setBackgroundDrawable(r.getDrawable(drawable));
		
		/*
		((FrameLayout) _backgroundView).removeAllViews();		

		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
		imageView.setBackgroundDrawable(r.getDrawable(drawable));
		
		((FrameLayout) _backgroundView).addView(imageView);
		*/
	}
	
	public void setIconImage(int drawable) {
		Resources r = getResources();
		_imageView.setImageDrawable(r.getDrawable(drawable));		
		//_imageView.setScaleType(ScaleType.CENTER_INSIDE);
	}
	
	public void setTitle(int resId) {
		Resources r = getResources();
		_titleLabel.setText(r.getString(resId));
	}
	
	public void setTitleColor(int resId) {		
		XmlResourceParser parser = getResources().getXml(resId);
		ColorStateList colors;
		try {
			colors = ColorStateList.createFromXml(getResources(), parser);
			_titleLabel.setTextColor(colors);			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public void setTitleFont(String fontName, float size) {
		_titleLabel.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName+".ttf"));
		_titleLabel.setTextSize(size);
	}
	*/
	
	
	public void setTitleFont(Typeface typeface, float size) {
		_titleLabel.setTypeface(typeface);
		_titleLabel.setTextSize(size);
	}
	
	public BNTabBarItem(Context context, int drawable, String title) {
		super(context);
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT, 
				1));
		
		FrameLayout layout = new FrameLayout(context);
		if (layout != null) {
			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					LayoutParams.MATCH_PARENT));				
			
			if (n % 2 == 0)
				layout.setBackgroundColor(Color.RED);
			else 
				layout.setBackgroundColor(Color.BLUE);
			
			n++;
			
			this.addView(layout);
		}		
		
		//-- --//
		Resources r = context.getResources();
		/*
		backgroundView = new ImageView(context);
		((ImageView) backgroundView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
		((ImageView) backgroundView).setImageDrawable(r.getDrawable(drawable));
		
		layout.addView(backgroundView);
		
		contentView = new LinearLayout(context);
		((LinearLayout) contentView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		((LinearLayout) contentView).setOrientation(LinearLayout.VERTICAL);
		((LinearLayout) contentView).setGravity(Gravity.CENTER);
		contentView.setBackgroundColor(Color.GREEN);
		
		layout.addView(contentView);
		
		/*
		contentView = new LinearLayout(context);
		((LinearLayout) contentView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1));
		((LinearLayout) contentView).setOrientation(LinearLayout.VERTICAL);
		((LinearLayout) contentView).setGravity(Gravity.CENTER);
		contentView.setBackgroundColor(Color.BLUE);
		
		Resources r = context.getResources();
		
		ImageView imageView = new ImageView(context);
		imageView.setImageDrawable(r.getDrawable(drawable));
		imageView.setAdjustViewBounds(true);
		imageView.setBackgroundColor(Color.TRANSPARENT);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setPadding(kPadding, kPadding + 12, kPadding, kPadding);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
	                                             LayoutParams.WRAP_CONTENT));
		((LinearLayout) contentView).addView(imageView);
		
		this.addView(contentView);
		*/
	}
	
	/*
	private ImageView imageView;
	
	private TextView titleLabel;
	private ImageView backgrundView;
	
	public BNTabBarItem(Context context) {
		super(context);
		
		this.setOrientation(LinearLayout.VERTICAL);
		this.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
	}
	
	public BNTabBarItem(Context context, int resId) {
		super(context);
		
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1));
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);
		
		imageView = new ImageView(context);
		imageView.setImageResource(resId);
		imageView.setAdjustViewBounds(true);
		imageView.setBackgroundColor(Color.TRANSPARENT);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setPadding(kPadding, kPadding + 12, kPadding, kPadding);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
	                                             LayoutParams.WRAP_CONTENT));
        addView(imageView);
	}
	
	public BNTabBarItem(Context context, String name) {
		super(context);
	}
	
	public BNTabBarItem(Context context, int drawable, String title) {
		super(context);
	}
	*/
}
