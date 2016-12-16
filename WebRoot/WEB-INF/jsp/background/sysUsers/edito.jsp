<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@include file="../../common/taglib.jsp" %>
    <%@include file="../../common/common-css.jsp" %>
  </head>
  
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.min.js"></script>
 
  <script type="text/javascript">	  
	  function saveData(){
    	 //提交.
    	  document.userForm.submit();
   	  	  return true;
	  }
  </script>
  
  <body>
  <div style="height: 100%;overflow-y: auto;">
<br/>
<br/>  
<form action="<%=request.getContextPath()%>/background/sysUsers/updateById.html" method="post" name="userForm" id="userForm">
		<input type="hidden" name="sysUserId" value="${user.sysUserId}">
		<input type="hidden" name="sysUserName" value="${user.sysUserName}">
		<input type="hidden" name="parentId" value="${user.parentId}">
		<input type="hidden" name="sysUserSex" value="${user.sysUserSex}">
		<input type="hidden" name="registerTime" value="${user.registerTime}">
		<input type="hidden" name="sysStatus" value="${user.sysStatus}">
		
		<table class="ttab" height="100" width="85%" border="0" cellpadding="0" cellspacing="1" align="center">
				<tr>
                    <td height="30" colspan="4">
                        <div align="center">
                            <font color="blue" size="4"><b>修改用户信息</b>
                            </font>
                        </div></td>
                </tr>
                
                <tr>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">用户名：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            ${user.sysUserName}
                        </div></td>
                        
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">用户密码：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                         <input type="password" style="height: 20px;width: 200px" name="sysUserPass"  value="${user.sysUserPass}"/>
                        </div></td>
                </tr>
                
                <tr>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">用户性别：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <c:if test="${user.sysUserSex eq '1'}">男</c:if> 
                            <c:if test="${user.sysUserSex ne '1'}">女</c:if>
                        </div></td>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">所属角色：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <select name="roleName" class="STYLE1" >
	                            <option value="">选择角色</option>
	                            <c:forEach var="key" items="${roles}">
	                                <option value="${key.roleName}" <c:if test="${key.roleName eq user.roleName}">selected="selected"</c:if>>${key.roleName}</option>
	                            </c:forEach>
                            </select>
                        </div></td>
                </tr>
                
                <tr>
                 <td height="30" width="20%">
                        <div align="right" class="STYLE1">联系地址：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <input style="height: 20px;width: 200px" name="sysUserAddress"  value="${user.sysUserAddress}"/>
                        </div>
                    </td>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">联系电话：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                          <input style="height: 20px;width: 200px" name="sysUserPhone"  value="${user.sysUserPhone}"/>
                        </div>
                     </td>
                   
                </tr>
                
                
                <tr>
                     <td height="30" width="20%">
                        <div align="right" class="STYLE1">邮箱：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <input style="height: 20px;width: 200px" name="sysUserMail"  value="${user.sysUserMail}"/>
                        </div></td>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">QQ：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                             <input style="height: 20px;width: 200px" name="sysUserQq"  value="${user.sysUserQq}"/>
                        </div></td>
                </tr>
				
				 <tr>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">是否是超级管理员：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <c:if test="${user.parentId eq '-1'}">是</c:if> 
                            <c:if test="${user.parentId ne '-1'}">否</c:if>
                        </div>
                    </td>
                    <td height="30" width="20%"></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                        </div>
                     </td>
                </tr>
                
				<tr>
					<td colspan="4" style="padding: 10px">
						<div align="center">
						   <sec:authorize ifAnyGranted="ROLE_sys_users_edit">
							<input type="button" value="　保　存　" class="input_btn_style1" onclick="return saveData()" />　　　
				           </sec:authorize>
							<input id="backBt" type="button" value="　返　回　" class="input_btn_style1" onclick="javascript:window.location.href='javascript:history.back()'" />
						</div>
					</td>
				</tr>
			</table>
</form>
</div>
  </body>
</html>
