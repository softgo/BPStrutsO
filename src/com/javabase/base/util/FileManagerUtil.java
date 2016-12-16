package com.javabase.base.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 主要是对文件的管理
 * 
 * @author bruce.
 *
 */
public class FileManagerUtil {

	private static Logger log = Logger.getLogger(FileManagerUtil.class);
	
	/**
	 * 读取文件
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFile(String filename) {
		File file = new File(filename);
		StringBuffer str = new StringBuffer();
//		FileReader reader = null;
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader (new FileInputStream(file),"UTF-8");
//
//			reader = new FileReader(file);
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				String s = new String(buff, 0, len);
				str.append(s);
			}
			return str.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}

	}
	/**
	 * 指定编码读取文件  encode文件编码
	 */
	public static String readFile(String path, String encode){
		String content="";
		String line="";
		StringBuffer buffer = new StringBuffer();
		try {
			InputStream inputstream = new FileInputStream(path);//读取模块文件
			BufferedReader  reader  =  new  BufferedReader(new  InputStreamReader(inputstream,encode)); 
			line  =  reader.readLine();              //  读取第一行 
			while  (line  !=  null)  {                    //  如果  line  为空说明读完了 
				buffer.append(line);                //  将读到的内容添加到  buffer  中 
				buffer.append("\n");                //  添加换行符 
				line  =  reader.readLine();      //  读取下一行 
			} 
			content = buffer.toString();
			reader.close();
			inputstream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 写文件
	 * 
	 * @param filename
	 *            文件名称
	 * @param str
	 *            文件内容
	 * @return
	 */
	public static boolean writer(String filename, String str) {
		File file = new File(filename);
		// System.out.println(file.getAbsolutePath());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(str.getBytes());
			out.flush();
			Thread.sleep(10);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}
	/**
	 * 写文件 true追加  false覆盖 
	 * @param str
	 * @param path
	 * @param flag
	 */
	public static boolean writer(String path,String str,Boolean flag) {
		FileWriter fw = null;
    	PrintWriter out = null;
    	try {
    		fw = new FileWriter(new File(path),flag);  //true追加  false覆盖
    		out = new PrintWriter(fw);
			out.print(str);
			out.flush();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally { 
			try {
				fw.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 写文件
	 * true追加  false覆盖  encode文件编码
	 */
	public static boolean writer(String path,String str,String encode ,Boolean append) {
		
		// 创建写文件对象
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        byte[] bs = null;
        // 按照指定字符集进行编码得到字符数组
        try {
        	fos = new FileOutputStream(path,append);  //true追加  false覆盖
        	bos = new BufferedOutputStream(fos);
        	if(encode == null || encode.trim().equals("")){
        		encode = "GBK";
        	}
			bs = str.getBytes(encode);
			// 字节数组写入文件
			bos.write(bs);
			bos.close();
			fos.close();
			return true;
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 写文件 设置编码encode
	 * @author akang
	 */
	public static boolean writer(String path,String str,String encode) {
		
		// 创建写文件对象
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        byte[] bs = null;
        // 按照指定字符集进行编码得到字符数组
        try {
        	fos = new FileOutputStream(path);
        	bos = new BufferedOutputStream(fos);
        	if(encode == null || encode.trim().equals("")){
        		encode = "GBK";
        	}
			bs = str.getBytes(encode);
			// 字节数组写入文件
			bos.write(bs);
			//System.out.println("str="+str);
			bos.close();
			fos.close();
			return true;
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 格式化字符串为xml
	 * 
	 * @param str
	 *            要格式化的字符
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static String format(String str)
			throws UnsupportedEncodingException, IOException, DocumentException {
		SAXReader reader = new SAXReader();
		StringReader in = null;

		OutputFormat formater = null;
		StringWriter out = null;
		XMLWriter writer = null;
		try {
			in = new StringReader(str);
			Document doc = reader.read(in);
			formater = OutputFormat.createPrettyPrint();
			out = new StringWriter();

			writer = new XMLWriter(out, formater);
			writer.write(doc);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (writer != null) {
				writer.close();
			}
		}

		return out.toString();
	}
	
	/**
	 * 
	 * 把源文件对象复制成目标文件对象
	 * @param src：源文件
	 * @param dst: 目标文件
	 * @return
	 */
	public static boolean copy(File src, File dst) {
		final int BUFFER_SIZE = 16 * 1024;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 修改图片大小 按比例修改 file:图片来源 filepath:图片修改后的输出位置 wideth:图片宽(像素) height:图片高(像素)
	 */
	public static void cutPhoto(File file, String filepath, int wideth, int height)
			throws IOException {
		Image src = ImageIO.read(file);

		if(!(height < src.getHeight(null) || wideth < src.getWidth(null))){return;}
		int heights = wideth * src.getHeight(null) / src.getWidth(null); // 按比例缩放
		int wideths = height * src.getWidth(null) / src.getHeight(null);
		if (heights > height) { // 如果高度超过规定按高度计算宽度
			wideth = wideths;
		} else if (wideths > wideth) { // 如果宽度超过规定按宽度计算高度
			height = heights;
		}
		BufferedImage tag = new BufferedImage(wideth, height,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(src, 0, 0, wideth, height, null);
		FileOutputStream outs = new FileOutputStream(filepath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outs);
		encoder.encode(tag);
		outs.close();
	}
	
//*********************************************************************I/O经典使用介绍***********************************************//

	/**
	 * 缓存输入文件
	 * @param fileString
	 * @return
	 * @throws IOException
	 */
	public static String read(String fileString) throws IOException{
		BufferedReader in =new BufferedReader(new FileReader(fileString));
		String string=null;
		StringBuilder builder=new StringBuilder();
		while ((string=in.readLine())!=null) {
			builder.append(string+"\n");
		}
		in.close();
		System.out.println("结果是："+builder.toString());
		return builder.toString();
	}
	/**
	 * reader file
	 * 
	 * @param filename
	 *            the file name
	 * @return
	 */
	public static String readerFile(String filename) {

		File file = new File(filename);

		InputStreamReader reader = null;

		StringBuffer returnValue = new StringBuffer();

		char[] str = new char[1024];

		int len = 0;

		try {
			// reader = new InputStreamReader(new FileInputStream(file));
			reader = new InputStreamReader(new FileInputStream(file), "utf-8");

			while ((len = reader.read(str)) != -1) {

				String s = new String(str, 0, len);

				returnValue.append(s);

			}

		} catch (UnsupportedEncodingException e) {
			log.debug("readerFile" + e.toString());
		} catch (FileNotFoundException e) {
			log.debug("readerFile" + e.toString());
		} catch (IOException e) {
			log.debug("readerFile" + e.toString());
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					log.debug("readerFile" + e.toString());
				}
		}

		return returnValue.toString();

	}

	/**
	 * 写带有bom头的文件
	 * 
	 * @param filename
	 *            文件名称
	 * @param data
	 *            数据内容
	 * @param iswriter
	 *            文件权限
	 */
	public static void writeFile(String filename, String data, boolean iswrite) {

		File file = new File(filename);

		File path = new File(file.getParent());

		FileOutputStream out = null;

		OutputStreamWriter outwriter = null;

		BufferedWriter bufwriter = null;

		try {

			if (!path.exists()) {

				boolean re = path.mkdirs();
				if (re == true)
					log.debug("[级联上报]创建文件路径[ " + file.getParent() + " ]成功!");
				else
					log.error("[级联上报]创建文件路径[ " + file.getParent() + " ]失败!");
			}

			System.out.println(filename);
			if (!file.exists()) {
				boolean re = file.createNewFile();
				if (re == true)
					log.debug("[级联上报]创建新文件[ " + filename + " ]成功!");
			}

			out = new FileOutputStream(file);
			if (file.length() < 1) {
				final byte[] bom = new byte[] { (byte) 0xEF, (byte) 0xBB,
						(byte) 0xBF };

				out.write(bom);
			}
			outwriter = new OutputStreamWriter(out, "utf-8");
			bufwriter = new BufferedWriter(outwriter);
			if (data != null) {
				bufwriter.write(data);
				bufwriter.flush();
				log.debug("[级联上报]文件写数据完成!");
			}

			path.setWritable(iswrite);
			log.debug("[级联上报]目录读写权限设置为[ " + iswrite + " ]!");
			file.setWritable(iswrite);
			log.debug("[级联上报]文件读写权限设置为[ " + iswrite + " ]!");
		} catch (FileNotFoundException e) {
			log.debug("writeFile" + e.toString());
		} catch (IOException e1) {
			log.debug("writeFile" + e1.toString());
		} finally {
			try {
				if (bufwriter != null)
					bufwriter.close();
				if (outwriter != null)
					outwriter.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.debug("writeFile" + e.toString());
			}
		}
	}

	/**
	 * 修改生成文件长度
	 * 
	 * @param filename
	 * @return
	 */
	public static String modifyFile(String filename) {

		File file = new File(filename);
		String str = readerFile(filename);

		long l = file.length();
		int size = (int) (l - 27); // 第一版计算文件大小

		log.debug("文件长度为" + l);

		String newstr = str.replaceFirst("-"
				+ str.substring(str.indexOf("") + 1,
						str.indexOf("|", str
								.indexOf("") + 1)), "-");
		log.debug("新串为:   " + newstr);
		// writeFile(filename, newstr);
		writer(filename, newstr);

		return "ok";
	}

	/**
	 * 读取带有utf-8 bom头的文件
	 * 
	 * @param filename
	 * @return
	 */
	public static String readerFileByUTF(String filename) {

		File file = new File(filename);

		InputStreamReader reader = null;

		PushbackInputStream ps = null;

		StringBuffer returnValue = new StringBuffer();

		FileInputStream fs = null;

		char[] str = new char[1024];

		int len = 0;

		try {
			// reader = new InputStreamReader(new FileInputStream(file));

			fs = new FileInputStream(file);

			ps = new PushbackInputStream(fs);

			int title1 = ps.read();
			int title2 = ps.read();
			int title3 = ps.read();

			if (title1 != 0xEF || title2 != 0xBB || title3 != 0xBF) {

				System.out
						.println("**************************************************");
				System.out.println("[级联上报]读取文件[" + filename + "]为非utf-8");

				return "-1";
			} else {
				reader = new InputStreamReader(fs, "utf-8");

				while ((len = reader.read(str)) != -1) {

					String s = new String(str, 0, len);

					returnValue.append(s);

				}
			}

		} catch (UnsupportedEncodingException e) {
			log.debug("readerFileByUTF" + e.toString());
		} catch (FileNotFoundException e) {
			log.debug("readerFileByUTF" + e.toString());
		} catch (IOException e) {
			log.debug("readerFileByUTF" + e.toString());
		} finally {

			if (ps != null) {
				try {
					ps.close();
				} catch (IOException e) {
					log.error("writer" + e.toString());
				}
			}
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e1) {
					log.error("readerFileByUTF" + e1.toString());
				}
			}
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					log.error("readerFileByUTF" + e.toString());
				}
		}

		return returnValue.toString();

	}

	/**
	 * 文件拷贝
	 * 
	 * @param f1
	 *            拷贝数据源
	 * @param f2
	 *            拷贝目标
	 * @return
	 * @throws Exception
	 */
	public synchronized static long forChannelCopy(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		File path = new File(f2.getParent());
		if (!path.exists()) {
			boolean result = path.mkdirs();
			if (result == true) {
				log.debug("[级联上报]创建目录[" + f2.getParent() + "]成功");
			} else {
				log.debug("[级联上报]创建目录[" + f2.getParent() + "]失败");
			}
		}
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		ByteBuffer b = null;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return new Date().getTime() - time;
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else
				length = 2097152;
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}

	/**
	 * 
	 * @param fileFrom:File from
	 * @param fileTo:File To
	 * @throws Exception
	 */
	public static void readWriteZip(String fileFrom , String fileTo) throws Exception{
		
		//create a zipOutputStream.
		ZipOutputStream zipStream=new ZipOutputStream(new FileOutputStream(fileTo));
		
		zipStream.setMethod(ZipOutputStream.DEFLATED);
		
		File file=new File(fileFrom);
		
		byte[] bytes=new byte[1024];
		
		int index;
		//create a zipEntry .
		ZipEntry zipEntry=new ZipEntry(fileTo);
		//add the connect between zipStream and zipEntry .
		zipStream.putNextEntry(zipEntry);
		
		FileInputStream fileInputStream=new FileInputStream(file);

		while ((index=fileInputStream.read(bytes))!=-1) {
			zipStream.write(bytes,0 ,index);
		}
		
		fileInputStream.close();
		
		zipStream.closeEntry();
		zipStream.close();
		
		//release the resource .
		fileInputStream=null;
		zipStream=null;		
	}
	
	
	public synchronized static String readFile(String fileName) {
		StringBuffer sbuff = new StringBuffer();
		File file = new File(fileName);
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] buff = new char[1024];
			int len = 0;
			while ((len = reader.read(buff)) != -1) {
				sbuff.append(new String(buff, 0, len));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// InputStream in = new InputStream(file);
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
		return sbuff.toString();
	}

	public synchronized static String readFile(File file) {
		StringBuffer sbuff = new StringBuffer();
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] buff = new char[1024];
			int len = 0;
			while ((len = reader.read(buff)) != -1) {
				sbuff.append(new String(buff, 0, len));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// InputStream in = new InputStream(file);
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
		return sbuff.toString();
	}

	
	public synchronized static void writeFile(String fileName, String matter) {
		File file = new File(fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(matter.getBytes());
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}

	}
	
	public synchronized static void writeFile(File file, String matter) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(matter.getBytes());
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	//全局的变量。
	public static final boolean RESULT=false;
	
	/**
	 * 拷贝文件。
	 * @param sou
	 * @param des
	 */
	public void copyFile(String source, String dest){
		try {
			BufferedReader reader=new BufferedReader(new FileReader(new File(source)));
			BufferedWriter writer=new BufferedWriter(new FileWriter(new File(dest)));
			String temp;
			while((temp=reader.readLine())!=null){
				writer.write(temp+"\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("not find the file .");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("take place the I/O exception .");
			e.printStackTrace();
		}
	}
	

	/**
	 * 文件的拷贝。
	 * @param source
	 * @param dest
	 */
	public void doCopyFile(String source , String dest) throws IOException {
		File srcFile=new File(source);
		File desFile=new File(dest);
		//判断是不是存在。
		if (desFile.exists()) {
			desFile.delete();
		}
		BufferedInputStream input=new BufferedInputStream(new FileInputStream(srcFile));
		try {
			BufferedOutputStream output=new BufferedOutputStream(new FileOutputStream(desFile));
			try {
				byte[] buffer=new byte[4096];
				int len=0;
				while ((len=input.read(buffer))!=-1) {
					//写入。
					output.write(buffer,0,len);
				}
				System.out.println("copy file is success .");
			} catch (Exception e) {
				System.out.println("something is break out .");
			}finally{
				if (output!=null) {
					try {
						output.close();
					} catch (IOException e) {
						System.out.println("close the outputstrem is wrong .");
						e.printStackTrace();
					}
				}				
			}
		} catch (FileNotFoundException e) {
			System.out.println("not find the file .");
			e.printStackTrace();
		}finally{
			if (input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("close the inputstream is wrong .");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 以BufferReader方式读取数据。
	 * @return
	 */
	public String readByBufferReader(String file){
		StringBuffer sb=new StringBuffer();
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(file)));
			String line;
			long count=0;
			while ((line=br.readLine())!=null) {
				if (RESULT) {
					System.out.println(line);
				}
				//追加
				sb.append(line);
				count+=line.length();
			}
			//关闭
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取数据出错啦.");
		}
		return sb.toString();
	}
	
	/**
	 * 以DataInputStream方式读取数据。
	 * @throws IOException 
	 * @throws Exception 
	 */
	 public void readByDataInputStream(String file) throws IOException{
		DataInputStream dis=new DataInputStream(new ByteArrayInputStream(new FileManagerUtil().readByBufferReader(file).getBytes()));
		while (dis.available()>0) {
			char c=dis.readChar();
			String s=dis.readUTF();
			if (RESULT) {
				System.out.println(c+s);
			}
		}
	 } 
	 
	 /**
	  * 不以byte的方式来读取值。
	  * @param file
	  * @return
	  */
	 public String readByBufferedInputStreamNoArray(String file){
		 try {
			 InputStream is=new BufferedInputStream(new FileInputStream(new File(file)));
			 while (is.available()>0) {
				 char c=(char) is.read();
				 if (RESULT) {
					System.out.println(c);
				}
			}
		 } catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 
	 /**
	  * use byte array to get bytes from file  
	  * 用字节来获取文件。
	  * @param file
	  * @return
	  */
	 public void readByBufferedInputStream(String file){
		 try {
			BufferedInputStream input=new BufferedInputStream(new FileInputStream(file));
			byte[] bytes=new byte[1024];
			while (input.available()>0) {
				input.read(bytes);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("not find out the file .");
		} catch (IOException e) {
			System.out.println("read I/O wrong .");
			e.printStackTrace();
		}
	 }
}
