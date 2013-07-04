package kr.co.namsang.mb.barista.util;

import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils 
{
	private static final int BUFFER_SIZE = 1024;
	
    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = BUFFER_SIZE;
        try {
            byte[] bytes = new byte[buffer_size];
            for(;;) {
            	int count = is.read(bytes, 0, buffer_size);
            	if (count == -1)
            		break;
            	os.write(bytes, 0, count);
            }
        }
        catch (Exception ex) {}
    }
}
