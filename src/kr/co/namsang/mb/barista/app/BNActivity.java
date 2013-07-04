package kr.co.namsang.mb.barista.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BNActivity extends FragmentActivity 
{

	@Override
    public void onCreate(Bundle savedInstanceState) 
	{		
        super.onCreate(savedInstanceState);
        
        //init();  // 액티비티 초기화              
        //initLayout();  // 액티비티 레이아웃 초기화
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
		
        //prepareToLoad();  // 		
	}
	
	public void init() 
	{   
		// 멤버 변수 등 초기화
	}
	
    public void initLayout()
    {
    	// 레리아웃 초기화 
    }
    
    public void prepareToLoad()
    {
    	
    }
}
