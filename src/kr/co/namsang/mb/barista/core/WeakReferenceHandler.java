/**
 *  WeakReferenceHandler.java
 *  Copyright (c) 2010-2013 Namsang Mobile Corp. All Rights Reserved.
 * 
 *  <pre>
 *      static class InnerHanler extends WeakReference<T>
 *      {
 *          public void handleMessage(T reference, Message msg) {
 *              ...
 *          }
 *      };
 *      InnerHanler handler = new InnerHanler(reference);
 *  </pre>
 *
 *  @author  Sangkyoon Nam
 *  @version 1.0
 *  @see     android.os.Handler
 *  @see     android.os.Message
 *					
 */

package kr.co.namsang.mb.barista.core;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

public abstract class WeakReferenceHandler<T> extends Handler 
{
    private WeakReference<T> mReference;

    public WeakReferenceHandler(T reference) {
        mReference = new WeakReference<T>(reference);
    }
	
    @Override
    public void handleMessage(Message msg) {
        T reference = mReference.get();		
        if (reference != null) {
            handleMessage(reference, msg);
        }
    }

    protected abstract void handleMessage(T reference, Message msg);
}
