package com.javabase.base.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.javabase.base.util.DataConvert;

/**
 * 字节读取类
 * 
 * @author bruce
 *
 */
public class QueryRead extends ByteManageAbstractUtil {
	protected static Logger logger = Logger.getLogger(QueryRead.class);
	public static final String SYN_KEYWORD_SPLIT = ";";
	private String readStr;
	private int errorCode = 0;
	/**
	 * 解析输入流 BufferedInputStream
	 * 
	 * @param reader
	 * @return
	 */
	public static String parseReader(BufferedInputStream reader) {
		byte[] byte_total = null;
		byte[] byte_identifyCode = null;
		String result = "";
		QueryRead readObj = new QueryRead();
		try {
			// 用文件随时记录
			FileOutputStream fos = new FileOutputStream(readObj.getClass().getClassLoader().getResource("socketRes.dat").getPath());
			
			// BBSDP1.13005 5680 === 先读取前十六个字节：5574之前的
			byte_identifyCode = new byte[16]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】，共占16字节
			int nresult = reader.read(byte_identifyCode, 0, 16);
			if (nresult < 0) {
				fos.write(("对不起，读取标准头的前16字节时报错：QueryRead-->parseReader() ERROR: the second package did not post!!").getBytes());
				logger.info("ERROR:the package title error;");
				return "";
			}
			fos.write(byte_identifyCode);
			result += new String(byte_identifyCode); // 连接标准头的前16个字节
			// 除验证码外包的长度 5680
			byte[] byte_alllen = new byte[8];
			int nresult2 = reader.read(byte_alllen, 0, 8);
			fos.write(byte_alllen);
			if (nresult2 < 0) {
				fos.write(("对不起，读取标准头的后8个字节时报错：QueryRead-->parseReader() ERROR: the second package did not post!!").getBytes());
				logger.error("ERROR: the second package did not post");
				return "";
			}
			result += new String(byte_alllen); // 连接标准头的后8个字节

			int rlen = new Integer(new String(byte_alllen).trim()).intValue();
			byte_total = new byte[rlen];

			int resCount = 0;
			while (resCount < rlen) {
				int aa = reader.read(byte_total, resCount, rlen - resCount);
				if (aa == -1) {
					break;
				}
				resCount += aa;
			}
			result += new String(byte_total, "GBK").trim(); // 连接后面所有的内容字节
			fos.write(byte_total);
			fos.flush();
			fos.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static byte[] getResXml(BufferedInputStream reader,int length){
		logger.debug("getResXml length==="+length);
		String res = "";
		byte[] xmlArr = null;
		byte[] allArr = null;
		List<byte[]> alist = new ArrayList();
		int bytesRead = 0;
		int cur_read_len = 4096;
		try { //如果出现异常 退出循环
         while (true) {
        	if(length - bytesRead > 4096)
        	{
        		cur_read_len = 4096;
        	}
        	else
        		cur_read_len = length - bytesRead;
        	
        	xmlArr = new byte[cur_read_len];
            int bytesReadLength = 0;
			bytesReadLength = reader.read(xmlArr, 0, cur_read_len);
			
			
			if(bytesReadLength==-1)
				break;
			alist.add(xmlArr);
			bytesRead+=bytesReadLength;
			logger.debug("bytesRead==="+bytesRead);
			
			if (bytesRead >= length) {// end of InputStream
				break;
			}
			logger.debug("also in getResXml while(true)");
          }
           logger.debug("finish in getResXml while");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} 
		allArr = DataConvert.sysCopy(alist);
		
		
	
		
		return allArr;
	}
	/**视频库同步接收返回信息
	 * @param reader
	 * @return
	 */
	public static String parseByteVideosReader(BufferedInputStream reader) {
		byte[] byte_identifyCode = null;
		String resStr = "";
		String title = "";
		try {
			byte_identifyCode = new byte[8]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】，共占16字节
			int nresult = reader.read(byte_identifyCode, 0, 8);
			if (nresult < 0) {
				logger.error(("对不起，读取标准头的前8字节时报错!!").getBytes());
				logger.info("ERROR:the package title error;");
			}
			title = new String(byte_identifyCode); // 连接标准头的前8个字节
			logger.info("verification code："+title);
			// 除验证码外包的长度 
			byte[] byte_alllen = new byte[4];//后续内容长度
			int nresult2 = reader.read(byte_alllen, 0, 4);
			int length = DataConvert.bytesToInt(byte_alllen);
			int dataLength = length -20;//真正数据长度
			logger.info("all the video length==="+length);
			if (nresult2 < 0) {
				logger.error(("对不起，读取标准头的后4个标识长度字节时报错！！").getBytes());
			}
			byte[] otherArr = new byte[14];
			reader.read(otherArr, 0, 14);
			byte[] status = new byte[4];//状态码
			int statusRes =  reader.read(status, 0, 4);
			int statusInt =  DataConvert.bytesToInt(status);
			byte[] other = new byte[2];//命令类型
			reader.read(other, 0, 2);
			if(statusInt==0){//返回成功状态码
				int resCount = 0;
				byte[] byte_total = null;//每个视频xml的byte数组
				while (resCount < dataLength) {
					byte[] contentLength = new byte[4];//内容后续长度
					reader.read(contentLength, 0, 4);
					int fLength = DataConvert.bytesToInt(contentLength);
					byte_total = new byte[fLength];
					int aa = reader.read(byte_total, 0, fLength);
					resStr += new String(byte_total, "GBK").trim(); // 每一个视频库内容
					
					logger.debug("every video resStr=="+resStr);
					
					if (aa == -1) {
						break;
					}
					resCount += aa;
				}
				logger.debug("all video resStr=="+resStr);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return resStr;
	}
	/**
	 * 
	 * @return
	 */
	public static String parserRead_bak(BufferedInputStream reader) {
		byte[] byte_total = null;
		byte[] byte_identifyCode = null;
		String result = "";
		try {
			// BBSDP1.13005 5680 === 先读取前十六个字节：5574之前的
			byte_identifyCode = new byte[16]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】，共占16字节
			int nresult = reader.read(byte_identifyCode, 0, 16);
			if (nresult < 0) {
				System.out
						.println("对不起，读取标准头的前16字节时报错：QueryRead-->parseReader() ERROR: the second package did not post!!");
				return "";
			}
			result += new String(byte_identifyCode); // 连接标准头的前16个字节
			// 除验证码外包的长度 5680
			byte[] byte_alllen = new byte[8];
			int nresult2 = reader.read(byte_alllen, 0, 8);
			if (nresult2 < 0) {
				System.out
						.println("对不起，读取标准头的后8个字节时报错：QueryRead-->parseReader() ERROR: the second package did not post!!");
				return "";
			}
			result += new String(byte_alllen); // 连接标准头的后8个字节

			int rlen = new Integer(new String(byte_alllen).trim()).intValue();
			// System.out.println("除验证码之外的长度totalLen=" + rlen);
			byte_total = new byte[rlen];
			int nresult3 = reader.read(byte_total, 0, rlen);
			if (nresult3 < 0) {
				System.out
						.println("对不起，读取标准头后的内容"
								+ rlen
								+ "字节时报错：QueryRead-->parseReader() ERROR: the second package did not post!!");
				return "";
			}
			result += new String(byte_total, "GBK").trim(); // 连接后面所有的内容字节
			// System.out.println("【恭喜】读取内容成功=" + result + "====");
			// 用文件随时记录
			FileOutputStream fos = new FileOutputStream(
					"D:\\Users_Folder\\guoy\\bbsV2Read.dat");
			fos.write(("恭喜：读取内容===" + result + "======\r\n\r\n").getBytes());
			fos.write(byte_total);
			fos.write("\r\n\r\n".getBytes());
			fos.flush();
			fos.close();

		} catch (Exception e) {
			System.out.println("catch捕捉到异常：QueryRead-->parseReader() Error:"
					+ e.getMessage());
			e.printStackTrace();
			return "";
		}
		return result;
	}
	/**
	 * 成功或失败发送的同步响应串
	 * @return
	 */
	public static void reSendMsg(Socket client,byte[] protocol){
		try {
			OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议	
			osw.write(new String(protocol));
			osw.flush();
			if (client != null) {
				client.close();
			}
			logger.info("reSendMsg 成功 ");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 拼接返回的协议
	 * @param status
	 * @param command_type
	 * @param protocol_type
	 * @return
	 */
	public static byte[] assembleProtocol(int status,short command_type,short protocol_type) {
		List<byte[]> protoList = new ArrayList<byte[]>();
		int total_data_len = 0;
		byte[] validateCode = new String("WEB2X1.0").getBytes();//验证码
		total_data_len += validateCode.length;
		byte[] protocolType = DataConvert.shortToBytes(protocol_type);//协议类型
		total_data_len += protocolType.length;
		byte[] commandType = DataConvert.shortToBytes(command_type);//命令类型
		total_data_len += commandType.length;
		byte[] b = new byte[12];//保留字段
		total_data_len += 12;
		int statusVal = status;
		byte[] statusByte = DataConvert.intToByteArray(statusVal);//状态码
		total_data_len += statusByte.length;
		
		byte[] lenByte = DataConvert.intToByteArray(total_data_len);//数据总长
		
		protoList.add(validateCode);//验证码8
		protoList.add(lenByte);//数据总长4
		protoList.add(protocolType);//协议类型2
		protoList.add(commandType);//命令类型2
		protoList.add(b);//保留字段12
		protoList.add(statusByte);//状态码4
		
		byte[] headProto = DataConvert.sysCopy(protoList);
		return headProto;
	}
	public static void writeToDatFile(BufferedInputStream reader,Socket client) {
		List<byte[]> byteList = new ArrayList<byte[]>();
		try {
//			String path = "/home/kaifa/zs2x/zs2xlogs";
			String path = "D:\\User_dat\\zs2x";
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String filename = "recvAppLog.dat";
			File file = new File(path,filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(os);
			String result = "";
			int len = 0;
			byte[] byte_total = null;
			byte[] verifyArr = new byte[8]; // 验证码
			byte[] lengthArr = new byte[4]; // 数据总长数组
			byte[] protoArr = new byte[2]; // 协议类型
			byte[] command = new byte[2]; // 命令类型
			byte[] keep = new byte[16]; //保留字段
			byte[] count = new byte[4];//条目数
			
			reader.read(verifyArr,0,8);
			result += new String(verifyArr);
			logger.info("读取验证码：" + result);
			byteList.add(verifyArr);
			
			reader.read(lengthArr,0,4);
			result += new String(lengthArr);
			logger.info("数据总长：" + DataConvert.bytesToInt(lengthArr));
			byteList.add(lengthArr);
			
			reader.read(protoArr,0,2);
			result += new String(protoArr);
			logger.info("协议类型：" + protoArr.length + "：" + DataConvert.bytesToShort(protoArr));
			byteList.add(protoArr);
			
			reader.read(command, 0, 2);
			result += new String(command);
			logger.info("命令类型：" + DataConvert.bytesToShort(command));
			byteList.add(command);

			reader.read(keep,0,16);
			result += new String(keep);
			byteList.add(keep);
			
			reader.read(count, 0, 4);
			result += new String(count);
			len = DataConvert.bytesToInt(count);
			logger.info("条目数：：" + len);
			byteList.add(count);
			
			len *= 4;
			logger.info("LEN-l--|--总记录数：：" + len);
			
			byte_total = readLogData(reader, len);
			result += new String(byte_total,"GBK");
			logger.info("result is ::::" + result);
			byteList.add(byte_total);
			
			byte[] headProtocol = DataConvert.sysCopy(byteList);
			dos.write(headProtocol);
			dos.flush();
			dos.close();
		} catch (FileNotFoundException e1) {
			logger.error(e1.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getReadStr() {
		return readStr;
	}

	public void setReadStr(String readStr) {
		this.readStr = readStr;
	}
	public static byte[] readLogData(BufferedInputStream reader, int length) {
		logger.info("读取后续条目总长度为：" + length);
		byte[] xmlArr = null;
		byte[] allArr = null;
		List<byte[]> alist = new ArrayList();
		int bytesRead = 0;
		int cur_read_len = 2048;
		try {
			while (true) {
				if(length - bytesRead > 2048)
	        	{
	        		cur_read_len = 2048;
	        	}
	        	else
	        		cur_read_len = length - bytesRead;
	        	
	        	xmlArr = new byte[cur_read_len];
	            int bytesReadLength = 0;
				bytesReadLength = reader.read(xmlArr, 0, cur_read_len);
				
				if(bytesReadLength==-1)
					break;
				byte[] newArr = new byte[bytesReadLength];
				for (int i = 0; i < bytesReadLength; i++) {
					newArr[i] = xmlArr[i];
				}
				alist.add(newArr);
				bytesRead+=bytesReadLength;
				logger.debug("bytesRead==="+bytesRead);
				
				if (bytesRead >= length) {// end of InputStream
					break;
				}
				logger.debug("also in readLogData while(true)");
	          }
	           logger.debug("finish in readLogData while");
		} catch (Exception e) {
			e.printStackTrace();
		}
		allArr = DataConvert.sysCopy(alist);
		return allArr;
	}
	
	/**
	 * 后台系统推送协议解析
	 * @param reader
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public static  void parseByteReader(BufferedInputStream reader,Socket client) {
		byte[] byte_identifyCode = new byte[8]; // 8字节【验证码】LOTTERYS
		int nresult = 0;
		try {
			nresult = reader.read(byte_identifyCode, 0, 8);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (nresult < 0) {
			logger.error(("对不起，读取标准头的前8字节时报错!!").getBytes());
			logger.info("ERROR:the package title error;");
		}
		String identifyCode  = new String(byte_identifyCode); // 
		logger.info("验证码：" + identifyCode);
		
		byte[] byte_alllen = new byte[4];// 随后的数据包长度
		int nresult2 = 0;
		try {
			nresult2 = reader.read(byte_alllen, 0, 4);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (nresult2 < 0) {
			logger.error(("对不起，读取标准头的后4个标识长度字节时报错！！").getBytes());
		}
		int length = DataConvert.bytesToInt(byte_alllen);//随后的数据包长度
		logger.info("随后的数据包长度：" + length);
		
		byte[] protoArr = new byte[4];//协议类型
		try {
			reader.read(protoArr, 0, 4);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		int protoType = DataConvert.bytesToInt(protoArr);//协议类型
		logger.info("协议类型：" + protoType);
		
	}
	
	/**
	 * 成功发送的同步响应串
	 * @return
	 */
	private static void resSuccess(Socket client){
		try {
			BufferedOutputStream bfOut = new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议	
			bfOut.write(getResSuccessProto(1));
			bfOut.flush();
			bfOut.close();
			if (client != null && !client.isClosed()) {
				client.close();
				client = null;
			}
			logger.info("给抽奖系统返回成功！");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 拼装接收的返回协议
	 * @param order:命令类型,content:命令主体
	 * @return
	 */
	public static  byte[] getResSuccessProto(int status){
		byte[] verfy = "LOTTERYS".getBytes();
		int length = 12;//数据总长,后台不解析，故可以用0
		int proto = 3;//协议类型
		int extLen = 0;
		byte[] protoArr = DataConvert.intToByteArray(proto);
		byte[] statusArr = DataConvert.intToByteArray(status);
		byte[] extArr = DataConvert.intToByteArray(extLen);
		
		List<byte[]> alist = new ArrayList<byte[]>();
		alist.add(verfy);//验证码
		alist.add(DataConvert.intToByteArray(length));//数据总长
		alist.add(protoArr);//协议类型3
		alist.add(statusArr);//状态字段
		alist.add(extArr);//扩展字段长度
		
		byte[] headProto = DataConvert.sysCopy(alist);
		return headProto;
	}
	
	/**
	 * 失败发送的同步响应串
	 * @return
	 */
	private static void resFailed(Socket client){
		try {
			BufferedOutputStream bfOut =  new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议	
			bfOut.write(getResSuccessProto(0));
			bfOut.flush();
			bfOut.close();
			if (client != null && !client.isClosed()) {
				client.close();
				client = null;
			}
			logger.info("给抽奖系统返回失败状态码！");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * @param proto
	 * @param client
	 * @param type 1 奖品 非1虚拟用户
	 */
	private static void resBack(byte[] proto, Socket client, int type) {
		try {
			BufferedOutputStream bfOut =  new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议	
			bfOut.write(proto);
			bfOut.flush();
			bfOut.close();
			if (client != null && !client.isClosed()) {
				client.close();
				client = null;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * @param proto
	 * @param client
	 * @param type 1 奖品 非1虚拟用户
	 */
	private static void resBackForMoreData(byte[] proto, Socket client, int type, int closeFlag) {
		try {
			BufferedOutputStream bfOut =  new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议	
			bfOut.write(proto);
			bfOut.flush();
			bfOut.close();
			if (closeFlag == 1) {
				if (client != null && !client.isClosed()) {
					client.close();
					client = null;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}