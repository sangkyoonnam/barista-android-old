package kr.co.namsang.mb.barista.data;

import android.graphics.Point;

public class Size 
{
	public double width = 0;
	public double height = 0;

	// Constructor
	public Size() {
		
	}
	
	public Size(double[] vals) {
		
	}
	
	public Size(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public Size(Point p) {
		
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
}
