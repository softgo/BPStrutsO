package com.javabase.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UpLoadFileServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//字符编码级设置
		request.setCharacterEncoding("UTF-8");
		String fileLocal=request.getParameter("fileLocal");
		
		if ("".equals(fileLocal)) {
			System.out.println("长传的文件路径是空的,是否要重新添加!");
			fileLocal=null;
		}
		
		if (fileLocal.lastIndexOf("\\")==-1) {
			System.out.println("选择上传的路径有误！");
			fileLocal=null;
		}
		
		try {
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置上传的位置
			ServletFileUpload upload = new ServletFileUpload(factory);
			boolean rest=upload.isMultipartContent(request);
			//文件头信息
			upload.setHeaderEncoding("UTF-8");
			//设置上传的监听器
			upload.setProgressListener(new ProgressListener() {
		        long num = 0;
		        public void update(long bytesRead, long contentLength, int items) {
	               long progress = bytesRead*100/contentLength;
	               if(progress==num){ return;}
	               num = progress;
	               System.out.println("上传进度:" + progress + "%");
		         }
	        });
			if(!rest) {
			    // 不是文件上传
				request.setAttribute("message", "对不起，不是文件上传表单！");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			    return;
			}
			
			//文件上传的信息。
			List<FileItem> fileItems = upload.parseRequest(request);
			File file =null;
			for (FileItem item : fileItems) {
				if(item.isFormField()) {
				    String name = item.getFieldName();
				    String value = item.getString();
				    // 手工的转换了
				    value = new String(value.getBytes("iso-8859-1"),"utf-8");
				    System.out.println(name + "=" + value);
				} else {
				    // 文件上传字段
				    // 获得文件名
				    String filename = item.getName();
				    // 创建文件
				    if (fileLocal=="" || fileLocal==null) {
				    	//默认路径
				    	file = new File("E:\\"+filename);
					}else{
						//用户填写的路径
						file=new File(fileLocal+filename);						
					}
				    file.createNewFile();
				    // 获得流，读取数据写入文件
				    InputStream in = item.getInputStream();
				    FileOutputStream fos = new FileOutputStream(file);
				    int length=0;
				    byte[] buffer = new byte[1024];
				    while((length=in.read(buffer))>0){
				    	fos.write(buffer,0,length);
				    }
				    fos.close();
				    in.close();
				    item.delete();    // 删除临时文件
				}
			}
		} catch (Exception e) {
			request.setAttribute("result", "上传失败，请查找原因，重新再试！");
			System.out.println("上传文件失败了,错误原因是："+e.getMessage());
		}
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}

  
