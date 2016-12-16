<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/taglib.jsp"%>
<%@include file="../../common/common-css.jsp"%>
<%@include file="../../common/common-js.jsp"%>
    <style type="text/css">
      input{font-size: 12px}
    </style>
  </head>
  
  <body>

<div style="height: 100%;overflow-y: auto;">
<div id="forms" class="mt10">
   <div class="box">
     <div class="box_border">
       <div class="box_top" ><b class="pl15" > 资源管理——>修改资源</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
			<input type="hidden" name="sourceId" value="${resources.sourceId}">
	    	<input type="hidden" name="sourceStatus" value="${resources.sourceStatus}">
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="td_right">
                                                            上级资源 : 
                    </td>
                    <td class="td_left">
                          <select name="parentId" class="STYLE1" style="width:245px;">
	                           <option value="1010">顶级菜单</option>
	                            <c:forEach var="key" items="${resLists}">
	                                <option value="${key.sourceId}" <c:if test="${key.sourceId eq resources.parentId}">selected="selected"</c:if>>${key.sourceName}</option>
	                            </c:forEach>
                           </select>
                        <font color="red"> <span id="parentIdTag"/></font>
                     </td>
                   </tr>
                   
                   <tr>
                    <td class="td_right">
                                                            资源名称 : 
                    </td>
                    <td class="td_left">
                      ${resources.sourceName}
                     </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        资源KEY : 
                    </td>
                    <td class="td_left">
                        ${resources.sourceKey}
                    </td>
                 </tr>
                 <tr>                        
                    <td class="td_right">
                                                        资源URL : 
                    </td>
                    <td class="td_left">
                         ${resources.sourceUrl}
                    </td>
                </tr>
               
               <tr>                        
                    <td class="td_right">
                                                        资源类型 : 
                    </td>
                    <td class="td_left">
	                    <input name="sourceType" type="radio" value="0" <c:if test="${resources.sourceType eq '0'}">checked="checked"</c:if>/>目录　　
						<input name="sourceType" type="radio" value="1" <c:if test="${resources.sourceType eq '1'}">checked="checked"</c:if>/>菜单　　
						<input name="sourceType" type="radio" value="2" <c:if test="${resources.sourceType eq '2'}">checked="checked"</c:if>/>按扭
                        <font color="red"><span id="sourceTypeTag"/></font>
                    </td>
                </tr>
                <tr>                        
                    <td class="td_right">
                                                        优先级 : 
                    </td>
                    <td class="td_left">
                        ${resources.sourceLevel}
                    </td>
                </tr>
                
                <tr>                        
                    <td class="td_right">
                                                        资源描述 : 
                    </td>
                    <td class="td_left">
                        ${resources.description}
                    </td>
                </tr>
				<tr>
					<td class="td_right">
                       
                    </td>
                    <td class="td_left">
                      <input type="button"  class="btn btn82 btn_nochecked" value="返回" onclick="location.href='javascript:history.go(-1)'">
                    </td>
				</tr>
			</table>
		  </div>
          </div>
        </div>
     </div>	
	</div>
  </body>
</html>
