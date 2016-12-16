package com.javabase.base.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 写到磁盘上要注意的地方.
 * 
 * @author bruce.
 *
 */
public interface WriteableObject extends Serializable {

	public void read(InputStream inputStream) throws IOException;
	
	public boolean write(OutputStream outputStream) throws IOException;
	
}