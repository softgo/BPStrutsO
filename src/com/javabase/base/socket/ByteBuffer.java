package com.javabase.base.socket;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * 字节buffer处理.
 * 
 * @author bruce
 *
 */
public class ByteBuffer
{
	byte[] bytes;

	int used;

	public ByteBuffer()
	{
		bytes = new byte[1024];
		used = 0;
	}

	public ByteBuffer(int size)
	{
		bytes = new byte[size];
		used = 0;
	}

	public int getUsed()
	{
		return used;
	}

	public ByteBuffer append(byte b)
	{
		if (used + 1 >= bytes.length)
		{
			byte[] bb = new byte[bytes.length << 1];
			copy(bb, 0, bytes, 0, used);
			bytes = bb;
		}
		bytes[used] = b;
		used++;
		return this;
	}

	public ByteBuffer append(byte[] b)
	{
		return append(b, 0, b.length);
	}

	public ByteBuffer append(byte[] b, int begin, int len)
	{
		if (b == null)
			return this;
		if (bytes.length < used + len)
		{
			byte[] bb = new byte[(used + len) << 1];
			copy(bb, 0, bytes, 0, used);
			bytes = bb;
		}
		copy(bytes, used, b, begin, len);
		used += len;
		return this;
	}

	public ByteBuffer append(short v)
	{
		byte[] b = new byte[2];
		for (int i = 0; i < 2; i++)
			b[i] = (byte) ((v >> (i * 8)) & 0x00ff);
		return append(b, 0, 2);
	}

	public ByteBuffer append(int v)
	{
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++)
			b[i] = (byte) ((v >> (i * 8)) & 0x00ff);
		return append(b, 0, 4);
	}

	public ByteBuffer append(long v)
	{
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++)
			b[i] = (byte) ((v >> (i * 8)) & 0x00ff);
		return append(b, 0, 8);
	}

	public ByteBuffer append(boolean v)
	{
		if (true == v)
			return append((byte) 1);
		else
			return append((byte) 0);
	}

	public ByteBuffer append(String str, String charset)
	{
		byte[] b;
		try
		{
			b = str.getBytes(charset);
		} catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(charset + "error !");
		}
		return append(b, 0, b.length);
	}

	public ByteBuffer append(String str)
	{
		byte[] b = str.getBytes();
		return append(b, 0, b.length);
	}

	public ByteBuffer append(ByteBuffer bb)
	{
		return append(bb.array(), 0, bb.used);
	}

	private final void copy(byte[] dest, int destStart, byte[] src, int srcBegin, int len)
	{
		for (int i = 0; i < len; i++)
			dest[destStart + i] = src[srcBegin + i];
	}

	public byte[] array()
	{
		return bytes;
	}

	public String toString(String encoding) throws UnsupportedEncodingException
	{
		return new String(bytes, 0, used, encoding);
	}

	public String toString()
	{
		return new String(bytes, 0, used);
	}

	public String toString(int size, String encoding) throws UnsupportedEncodingException
	{
		return new String(bytes, 0, (used > size) ? size : used, encoding);
	}

	/**
	 * ungzip
	 */
	public void ungzip() throws IOException
	{
		ByteArrayInputStream bIn = new ByteArrayInputStream(bytes);
		GZIPInputStream gIn = new GZIPInputStream(bIn);
		ByteBuffer bb = new ByteBuffer(bytes.length * 2);
		byte[] bs = new byte[1024];
		int len = 0;
		while ((len = gIn.read(bs, 0, 1024)) > 0)
			bb.append(bs, 0, len);
		bytes = bb.bytes;
	}

	public void clear()
	{
		this.used = 0;
	}

}
