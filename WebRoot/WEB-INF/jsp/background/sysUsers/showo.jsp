<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@include file="../../common/taglib.jsp" %>
    <%@include file="../../common/common-css.jsp" %>
  </head>
  <body>
  <div style="height: 100%;overflow-y: auto;">
<br/>
<br/>  
		<table class="ttab" height="100" width="60%" border="0" cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td height="30" colspan="4">
						<div align="center">
							<font color="blue" size="4"><b>系统用户信息</b>
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
						<div align="right" class="STYLE1">用户性别：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<c:if test="${user.sysUserSex eq '1'}">男</c:if> 
                            <c:if test="${user.sysUserSex ne '1'}">女</c:if>
						</div></td>
				</tr>
				
				<tr>
					<td height="30" width="20%">
						<div align="right" class="STYLE1">所属角色：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${user.roleName}
						</div></td>
					<td height="30" width="20%">
						<div align="right" class="STYLE1">联系地址：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${user.sysUserAddress}
						</div></td>
				</tr>
				
				<tr>
					<td height="30" width="20%">
						<div align="right" class="STYLE1">联系电话：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${user.sysUserPhone}
						</div>
				     </td>
					<td height="30" width="20%">
                        <div align="right" class="STYLE1">邮箱：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            ${user.sysUserMail}
                        </div></td>
				</tr>
				<tr>
					<td height="30" width="20%">
						<div align="right" class="STYLE1">QQ：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${user.sysUserQq}
						</div></td>
						
					<td height="30" width="20%">
						<div align="right" class="STYLE1">是否是超级管理员：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<c:if test="${user.parentId eq '-1'}">是</c:if> 
                            <c:if test="${user.parentId ne '-1'}">否</c:if>
						</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="4" style="padding: 10px">
						<div align="center">
							<input id="backBt" type="button" value="　返　回　" class="input_btn_style1" onclick="javascript:window.location.href='javascript:history.back()'" />
						</div></td>
				</tr>
			</table>
</div>
  </body>
</html>
