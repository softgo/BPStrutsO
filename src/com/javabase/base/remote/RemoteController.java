package com.javabase.base.remote;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javabase.base.Constant;
import com.javabase.base.util.HttpParameterUtil;
import com.javabase.base.util.SpringContextUtil;


/**
 * 远程访问的控制器.
 * 
 * @author bruce
 *
 */

@Controller
public class RemoteController {

    private static final String SERVICE_SUFFIX = "Service";

    @RequestMapping("/remote")
    @ResponseBody
    public Object remote(HttpServletRequest request) {
        try {
            String m = HttpParameterUtil.getParameter(request, IRemoteService.PARAMETER_M);
            String p = HttpParameterUtil.getParameter(request, IRemoteService.PARAMETER_P);

            int status = validate(m, p);
            if (status != JsonResponseHeader.STATUS_OK) {
                return JsonResponseCreator.createInvalidJsonResponse(status);
            }

            int lastIndex = m.lastIndexOf(".");
            String serviceSimpleName = m.substring(0, lastIndex);
            String methodName = m.substring(lastIndex + 1);

            IRemoteService service = null;
            try {
            	service = SpringContextHolder.getBean(serviceSimpleName + SERVICE_SUFFIX);
			}
			catch (Exception e) {
				service = SpringContextUtil.getBean(serviceSimpleName + SERVICE_SUFFIX);
			}
            
            Method method = service.getClass().getMethod(methodName);
            addParameters2ServiceContext(p);
            return method.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponseCreator.createInvalidJsonResponse(JsonResponseHeader.STATUS_ERROR);
        }
    }

    private void addParameters2ServiceContext(String p) throws UnsupportedEncodingException {
    	RemoteServerContext ctx = RemoteServerContext.getContext();
        String parameters = new String(Base64.decodeBase64(p.getBytes(Constant.UTF8)), Constant.UTF8);
        ctx.addValue(IRemoteService.PARAMETER_P, parameters);
    }

    private int validate(String m, String p) {
        int lastIndex = m.lastIndexOf(".");
        if (lastIndex == -1) {
            return JsonResponseHeader.STATUS_INVALID_PARAMETER;
        }
        return JsonResponseHeader.STATUS_OK;
    }


}
