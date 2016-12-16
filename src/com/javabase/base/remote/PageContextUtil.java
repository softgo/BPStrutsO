package com.javabase.base.remote;

import javax.servlet.http.HttpServletRequest;

import com.javabase.base.util.HttpParameterUtil;

/**
 * Page 设置.
 * 
 * @author bruce.
 *
 */
public class PageContextUtil {

    public static void initPageContext(HttpServletRequest request) {
        int pageNo = HttpParameterUtil.getParameterInt(request, "pageNow"); //pageNo
        int pageSize = HttpParameterUtil.getParameterInt(request, "pageSize"); //pageSize
        initPageContext(pageNo, pageSize);
    }

    public static void initPageContext(int pageNo, int pageSize) {
        PageContext pageContext = PageContext.getContext();
        pageNo = pageNo > 0 ? pageNo : 1;
        pageSize = pageSize > 0 ? pageSize : 10;
        pageContext.setPageNow(pageNo);
        pageContext.setPageSize(pageSize);
    }

    public static void removePageContext() {
        PageContext.removeContext();
    }

}
