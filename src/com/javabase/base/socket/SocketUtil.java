package com.javabase.base.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.javabase.base.socket.test.ComsystemUtil;
import com.javabase.base.util.DataConvert;

/**
 * socket 通信的util类.
 * 
 * @date 2014-11-28.
 * 
 * @author admin
 * 
 */

public class SocketUtil {

	protected static Logger logger = Logger.getLogger(SocketUtil.class);

	private String ip;

	private int port;

	private String charset;

	private Socket socket;

	private InputStream in;

	private OutputStream out;

	private int socketWateServerTime = 30 * 60 * 1000; // socket超时时间

	public SocketUtil(String ip, int port, String charset) {
		this.ip = ip;
		this.port = port;
		this.charset = charset;
	}

	public void connect() throws IOException {
		socket = new Socket(ip, port);
		logger.info("IP : " + ip + " PORT : [" + port + "]");
		socket.setSoTimeout(socketWateServerTime);
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	public void setKeepAlive(boolean alive) throws Exception {
		socket.setKeepAlive(alive);
	}

	public void send(String str) throws IOException {
		byte[] tmp = str.getBytes(charset);
		out.write(tmp, 0, tmp.length);
		out.flush();
	}

	public void sendByte(byte[] arry) throws IOException {
		out.write(arry, 0, arry.length);
		out.flush();
	}

	public void close() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();

			if (socket != null)
				socket.close();
		} catch (IOException e) {

		} finally {

			if (socket != null)
				socket = null;
		}
	}

	public SocketUtil() {
	};

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	/**
	 * 集成系统返回的状态码：>0表示成功, <=0失败
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	public String recvStatusCode() throws IOException {
		// 2014-8-21
		BufferedInputStream bis = new BufferedInputStream(in, 1024 * 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		byte[] byte_identifyCode = null; // 头文件.
		String resStr = "";
		try {
			byte_identifyCode = new byte[13]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】
			int nresult = bis.read(byte_identifyCode, 0, 13);
			if (nresult < 0) {
				logger.error("读取标准头的前13字节时报错!!");
				return null;
			}

			// 返回结果.
			byte[] back_result = new byte[4];// 后续内容长度
			bis.read(back_result, 0, 4);
			int result = DataConvert.bytesToInt(back_result);
			logger
					.info("------------------------------------------------------------------");
			logger.info("返回的结果是：" + result);
			logger
					.info("------------------------------------------------------------------");
			if (result > 0) {
				logger.info("通信成功,返回的结果是：" + result);
				resStr = "Y";
			} else {
				logger.info("通信失败,返回的结果是：" + result);
				resStr = "N";
			}
		} catch (Exception e) {
			logger.error("协议解析发生异常：" + e.getMessage());
			resStr = null;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
		return resStr;
	}
	/**
	 * 返回ID分配到管理系统的数据处理结果.
	 * 
	 * @return
	 */
	public String returnRemoeRelationData() {
		// 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		BufferedInputStream reader = new BufferedInputStream(in,
				1024 * 1024 * 4);
		// 8字节【验证码】
		byte[] byte_identifying_code = new byte[8];
		int identifying_code_result = 0;
		try {
			identifying_code_result = reader.read(byte_identifying_code, 0, 8);
		} catch (IOException e) {
			logger.error("通过socket流获取验证码信息出错了." + e.getMessage());
			return null;
		}
		if (identifying_code_result < 0) {
			logger.error(("对不起，读取标准头的前8字节时报错!!"));
			return null;
		}
		String identifying_code = new String(byte_identifying_code); // 解析出验证码信息.
		logger.info("通过socket读取流信息得到的验证码是：" + identifying_code);

		// 后续的数据包总长度
		byte[] byte_next_allldata = new byte[4];
		int next_data_result = 0;
		try {
			next_data_result = reader.read(byte_next_allldata, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了." + e.getMessage());
			return null;
		}
		if (next_data_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
			return null;
		}
		int next_data_len = DataConvert.bytesToInt(byte_next_allldata);
		logger.info("通过socket读取流信息得到的随后的数据包长度：" + next_data_len);

		// 协议类型.
		byte[] byte_protocol_type = new byte[2];
		int protocol_data_len = 0;
		try {
			protocol_data_len = reader.read(byte_protocol_type, 0, 2);
		} catch (IOException e) {
			logger.error("读取协议类型异常了." + e.getMessage());
			return null;
		}
		if (protocol_data_len < 0) {
			logger.error("对不起，读取协议类型的2个标识长度字节时报错！！");
			return null;
		}
		short protocol_result = DataConvert.bytesToShort(byte_protocol_type);
		logger.info("通过socket读取协议类型得到的值是：" + protocol_result);

		// 保留字段
		byte[] byte_retain_hands = new byte[12];
		int retain_hands_result = 0;
		try {
			retain_hands_result = reader.read(byte_retain_hands, 0, 12);
		} catch (IOException e) {
			logger.error("通过socket流获保留字段信息出错了." + e.getMessage());
			return null;
		}
		if (retain_hands_result < 0) {
			logger.error(("对不起，读取保留字段的12字节时报错!!"));
			return null;
		}
		String retain_hands = new String(byte_retain_hands);
		logger.info("通过socket读取保留字段得到的是：" + retain_hands);

		// 状态码.
		byte[] byte_status_code = new byte[4];
		int status_code_result = 0;
		try {
			status_code_result = reader.read(byte_status_code, 0, 4);
		} catch (Exception e) {
			logger.error("通过socket流获状态码信息出错了." + e.getMessage());
			return null;
		}
		if (status_code_result < 0) {
			logger.error(("对不起，读取状态码的4字节时报错!!"));
			return null;
		}
		int status_code = DataConvert.bytesToInt(byte_status_code);
		logger.info("通过socket读取流信息得到的状态码数据是：" + status_code);
		// 如果有错，打印报错信息.
		StatusCode.getCodes(status_code);
		// 子数据处理.
		return String.valueOf(status_code);
	}
	/**
	 * 返回ID分配到管理系统的数据处理结果.
	 * 
	 * @return
	 */
	public String returnMSSocketBackData() {
		// 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		BufferedInputStream reader = new BufferedInputStream(in,
				1024 * 1024 * 4);
		// 8字节【验证码】
		byte[] byte_identifying_code = new byte[8];
		int identifying_code_result = 0;
		try {
			identifying_code_result = reader.read(byte_identifying_code, 0, 8);
		} catch (IOException e) {
			logger.error("通过socket流获取验证码信息出错了." + e.getMessage());
			return null;
		}
		if (identifying_code_result < 0) {
			logger.error(("对不起，读取标准头的前8字节时报错!!"));
			return null;
		}
		String identifying_code = new String(byte_identifying_code); // 解析出验证码信息.
		logger.info("通过socket读取流信息得到的验证码是：" + identifying_code);

		// 后续的数据包总长度
		byte[] byte_next_allldata = new byte[4];
		int next_data_result = 0;
		try {
			next_data_result = reader.read(byte_next_allldata, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了." + e.getMessage());
			return null;
		}
		if (next_data_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
			return null;
		}
		int next_data_len = DataConvert.bytesToInt(byte_next_allldata);
		logger.info("通过socket读取流信息得到的随后的数据包长度：" + next_data_len);

		// 协议类型.
		byte[] byte_protocol_type = new byte[2];
		int protocol_data_len = 0;
		try {
			protocol_data_len = reader.read(byte_protocol_type, 0, 2);
		} catch (IOException e) {
			logger.error("读取协议类型异常了." + e.getMessage());
			return null;
		}
		if (protocol_data_len < 0) {
			logger.error("对不起，读取协议类型的2个标识长度字节时报错！！");
			return null;
		}
		short protocol_result = DataConvert.bytesToShort(byte_protocol_type);
		logger.info("通过socket读取协议类型得到的值是：" + protocol_result);

		// 类型.
		byte[] byte_type = new byte[2];
		int data_len = 0;
		try {
			data_len = reader.read(byte_type, 0, 2);
		} catch (IOException e) {
			logger.error("读取类型异常了." + e.getMessage());
			return null;
		}
		if (data_len < 0) {
			logger.error("对不起，读取类型的2个标识长度字节时报错！！");
			return null;
		}
		short type = DataConvert.bytesToShort(byte_type);
		logger.info("通过socket读取类型得到的值是：" + type);

		// 保留字段
		byte[] byte_retain_hands = new byte[12];
		int retain_hands_result = 0;
		try {
			retain_hands_result = reader.read(byte_retain_hands, 0, 12);
		} catch (IOException e) {
			logger.error("通过socket流获保留字段信息出错了." + e.getMessage());
			return null;
		}
		if (retain_hands_result < 0) {
			logger.error(("对不起，读取保留字段的12字节时报错!!"));
			return null;
		}
		String retain_hands = new String(byte_retain_hands);
		logger.info("通过socket读取保留字段得到的是：" + retain_hands);

		// 状态码.
		byte[] byte_status_code = new byte[4];
		int status_code_result = 0;
		try {
			status_code_result = reader.read(byte_status_code, 0, 4);
		} catch (Exception e) {
			logger.error("通过socket流获状态码信息出错了." + e.getMessage());
			return null;
		}
		if (status_code_result < 0) {
			logger.error(("对不起，读取状态码的4字节时报错!!"));
			return null;
		}
		int status_code = DataConvert.bytesToInt(byte_status_code);
		logger.info("通过socket读取流信息得到的状态码数据是：" + status_code);
		// 如果有错，打印报错信息.
		StatusCode.getCodes(status_code);
		// 子数据处理.
		try {
			if (status_code == 0) {
				// 后续的数据包总长度
				byte[] children_data_len = new byte[4];
				int children_data_result = 0;
				try {
					children_data_result = reader.read(children_data_len, 0, 4);
				} catch (IOException e) {
					logger.error("读取子数据长度异常了." + e.getMessage());
					return null;
				}
				if (children_data_result < 0) {
					logger.error("对不起，读取子数据长度字节时报错！！");
					return null;
				}
				int children_len = DataConvert.bytesToInt(children_data_len);
				logger.info("通过socket读取流信息得到的子数据包长度：" + children_len);

				/**
				 * 针对命令类型3，4,5,8，数据长度都为0，只有命令类型2数据长度不为0.
				 */
				String return_main_data = null;
				if (children_len != 0 && status_code == 0) {
					// 得到子数据中的数据内容：

					try {
						return_main_data = new String(ComsystemUtil
								.getReturnStr(reader, children_len), "GBK")
								.trim();
					} catch (IOException e) {
						logger.error("通过socket流获子数据中的数据内容信息出错了."
								+ e.getMessage());
						return null;
					}
					logger.info("通过socket读取子数据中的数据内容得到的是：" + return_main_data);
					return return_main_data;
				} else if (children_len == 0 && status_code == 0) {
					status_code = 0;
				}

			} else {
				return String.valueOf(status_code);

			}

		} catch (Exception e) {
			logger.error("处理子数据操作出错了." + e.getMessage());
			return null;
		}
		return String.valueOf(status_code);
	}

	/**
	 * 解析存储系统到ID分配系充的返回结果
	 * 
	 * @return
	 */
	public int returnVideoData() {
		// 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		BufferedInputStream reader = new BufferedInputStream(in, 1024 * 1024);
		// 8字节【验证码】
		byte[] byte_identifying_code = new byte[8];
		int identifying_code_result = 0;
		try {
			identifying_code_result = reader.read(byte_identifying_code, 0, 8);
		} catch (IOException e) {
			logger.error("通过socket流获取验证码信息出错了." + e.getMessage());
			return -1;
		}
		if (identifying_code_result < 0) {
			logger.error(("对不起，读取标准头的前8字节时报错!!"));
			return -1;
		}
		String identifying_code = new String(byte_identifying_code); // 解析出验证码信息.
		logger.info("通过socket读取流信息得到的验证码是：" + identifying_code);

		// 后续的数据包总长度
		byte[] byte_next_allldata = new byte[4];
		int next_data_result = 0;
		try {
			next_data_result = reader.read(byte_next_allldata, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了." + e.getMessage());
			return -1;
		}
		if (next_data_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
			return -1;
		}
		int next_data_len = DataConvert.bytesToInt(byte_next_allldata);
		logger.info("通过socket读取流信息得到的随后的数据包长度：" + next_data_len);

		// 协议类型.
		byte[] byte_protocol_type = new byte[2];
		int protocol_data_len = 0;
		try {
			protocol_data_len = reader.read(byte_protocol_type, 0, 2);
		} catch (IOException e) {
			logger.error("读取协议类型异常了." + e.getMessage());
			return -1;
		}
		if (protocol_data_len < 0) {
			logger.error("对不起，读取协议类型的2个标识长度字节时报错！！");
			return -1;
		}
		short protocol_result = DataConvert.bytesToShort(byte_protocol_type);
		logger.info("通过socket读取协议类型得到的值是：" + protocol_result);

		// 保留字段
		byte[] byte_retain_hands = new byte[12];
		int retain_hands_result = 0;
		try {
			retain_hands_result = reader.read(byte_retain_hands, 0, 12);
		} catch (IOException e) {
			logger.error("通过socket流获保留字段信息出错了." + e.getMessage());
			return -1;
		}
		if (retain_hands_result < 0) {
			logger.error(("对不起，读取保留字段的12字节时报错!!"));
			return -1;
		}
		String retain_hands = new String(byte_retain_hands);
		logger.info("通过socket读取保留字段得到的是：" + retain_hands);

		// 状态码.
		byte[] byte_status_code = new byte[4];
		int status_code_result = 0;
		try {
			status_code_result = reader.read(byte_status_code, 0, 4);
		} catch (Exception e) {
			logger.error("通过socket流获状态码信息出错了." + e.getMessage());
			return -1;
		}
		if (status_code_result < 0) {
			logger.error(("对不起，读取状态码的4字节时报错!!"));
			return -1;
		}
		int status_code = DataConvert.bytesToInt(byte_status_code);
		logger.info("通过socket读取流信息得到的状态码数据是：" + status_code);
		// 如果有错，打印报错信息.
		StatusCode.getCodes(status_code);
		return status_code;
	}

	/**
	 * 接收视频图片返回信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String recvImageInfo() throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in, 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		byte[] byte_identifyCode = null;
		String resStr = "";
		String title = "";
		try {
			byte_identifyCode = new byte[8]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】，共占16字节
			int nresult = bis.read(byte_identifyCode, 0, 8);
			if (nresult < 0) {
				logger.error(("image 对不起，读取标准头的前8字节时报错!!").getBytes());
			}
			title = new String(byte_identifyCode); // 连接标准头的前8个字节
			logger.info("image: verification code：" + title);
			// 除验证码外包的长度
			byte[] byte_alllen = new byte[4];// 后续内容长度
			int nresult2 = bis.read(byte_alllen, 0, 4);
			int length = DataConvert.bytesToInt(byte_alllen);
			int dataLength = length - 20;// 真正数据长度
			logger.info("image: all the video length===" + length);
			if (nresult2 < 0) {
				logger.error(("image:对不起，读取标准头的后4个标识长度字节时报错！！").getBytes());
				return null;
			}
			byte[] otherArr = new byte[14];
			bis.read(otherArr, 0, 14);
			byte[] status = new byte[4];// 状态码
			int statusRes = bis.read(status, 0, 4);
			int statusInt = DataConvert.bytesToInt(status);
			byte[] other = new byte[2];// 命令类型
			bis.read(other, 0, 2);
			if (statusInt == 0) {// 返回成功状态码
				int resCount = 0;
				byte[] byte_total = null;// 每个视频xml的byte数组
				byte[] contentLength = new byte[4];// 内容后续长度
				bis.read(contentLength, 0, 4);// 或者从偏移量开始bis.read(contentLength,
												// resCount, 4);
				int fLength = DataConvert.bytesToInt(contentLength);
				try {
					resStr = new String(QueryRead.getResXml(bis, fLength),
							"GBK").trim();
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage());
					return null;
				}
			} else {
				return String.valueOf(statusInt);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			resStr = "协议解析发生异常：" + e.getMessage();
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
		return resStr;
	}
}