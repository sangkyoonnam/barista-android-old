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

/**
 * Convenience class for making a static inner {@link Handler} class that keeps
 * a {@link WeakReference} to its parent class. If the reference is cleared, the
 * {@link WeakReferenceHandler} will stop handling {@link Message}s.
 * <p>
 * Example usage:
 * <pre>
 * private final MyHandler mHandler = new MyHandler(this);
 *
 * private static class MyHandler extends WeakReferenceHandler<MyClass> {
 *     protected void handleMessage(Message msg, MyClass parent) {
 *         parent.onMessageReceived(msg.what, msg.arg1);
 *     }
 * }
 * </pre>
 * </p>
 *
 * @param <T> The handler's parent class.
 */
public abstract class WeakReferenceHandler<T> extends Handler 
{
    private final WeakReference<T> mParentRef;

    /**
     * Constructs a new {@link WeakReferenceHandler} with a reference to its
     * parent class.
     *
     * @param parent The handler's parent class.
     */
    public WeakReferenceHandler(T parent) {
    	mParentRef = new WeakReference<T>(parent);
    }
	
    @Override
    public void handleMessage(Message msg) {
        T reference = getParent();		
        if (reference != null)
            handleMessage(reference, msg);
    }

    /**
     * @return The parent class, or {@code null} if the reference has been
     *         cleared.
     */
    protected T getParent() {
        return mParentRef.get();
    }

    /**
     * Subclasses must implement this to receive messages.
     */
    protected abstract void handleMessage(T reference, Message msg);
}
