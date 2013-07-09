package kr.co.namsang.mb.barista.io;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;

public class MemoryCache 
{
    private HashMap<String, SoftReference<Bitmap>> cache = new HashMap<String, SoftReference<Bitmap>>();
    
    public Bitmap get(String id) {
        if (id != null
        		&& cache.containsKey(id) == true) {
        	SoftReference<Bitmap> ref = cache.get(id);
        	return ref.get();
        }
        
        return null;
    }
    
    public void put(String id, Bitmap bitmap) {
        cache.put(id, new SoftReference<Bitmap>(bitmap));
    }

    public void clear() {
        cache.clear();
    }
}
