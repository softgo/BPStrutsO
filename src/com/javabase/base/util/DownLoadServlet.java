package com.javabase.base.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/*
	 * 处理请求 (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest ,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收中文参数乱码处理
		response.setCharacterEncoding("utf-8");
		String fileName = new String(request.getParameter("fileName").getBytes("ISO-8859-1"), "utf-8");
		System.out.println("文件的路径是："+fileName);
		// 得到文件名
		// 设置为下载application/x-download
		response.setContentType("application/x-download");
		// 下载文件时显示的文件保存名称
		String filenamedisplay = fileName;
		// 中文编码转换
		filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="+ filenamedisplay);
		try {
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(fileName);
			byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = fis.read(bytes)) > 0) {
				os.write(bytes, 0, length);
			}
			fis.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			System.out.println("下载文件时候出现了问题,错误原因是："+e.getMessage());
		}
	}
}
