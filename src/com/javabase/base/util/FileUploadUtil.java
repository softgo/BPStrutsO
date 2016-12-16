package com.javabase.base.util;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传
 * 
 * @author bruce.
 *
 */
public class FileUploadUtil {

	/**
	 * SpringMVC 多文件上传
	 * 
	 * @param files
	 * @param request
	 * @param response
	 * @param model
	 * @param teacherName
	 * @return
	 */
	public String addUploadFile(@RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request,HttpServletResponse response,Model model,String teacherName) {
    	String returnFileName = null;    	
        try {
        	//创建一个通用的多部分解析器  
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
            //判断 request 是否有文件上传,即多部分请求  
            if(multipartResolver.isMultipart(request)){  
                //转换成多部分request    
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
                //取得request中的所有文件名  
                Iterator<String> iter = multiRequest.getFileNames();  
                while(iter.hasNext()){  
                    //记录上传过程起始时的时间，用来计算上传时间  
                    int pre = (int) System.currentTimeMillis();  
                    //取得上传文件  
                    MultipartFile file = multiRequest.getFile(iter.next());  
                    if(file != null){  
                        //取得当前上传文件的文件名称  
                        String myFileName = file.getOriginalFilename();  
                        //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                        if(myFileName.trim() !=""){  
                            System.out.println(myFileName);  
                            //重命名上传后的文件名  
                            String fileName = "teacher"+ System.currentTimeMillis() + file.getOriginalFilename();  
                            //定义上传路径  
                            String path = "E:/" + fileName;  
                            returnFileName = path;
                            File localFile = new File(path);  
                            file.transferTo(localFile);
                        }  
                    }  
                    //记录上传该文件后的时间  
                    int finaltime = (int) System.currentTimeMillis();  
                    System.out.println(finaltime - pre);  
                }  
            }
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
        model.addAttribute("fileName", returnFileName); //返回的文件路径.
        return "filePath"; //返回的地址.
    }    
}
