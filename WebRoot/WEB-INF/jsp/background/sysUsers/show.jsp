<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/taglib.jsp"%>
<%@include file="../../common/common-css.jsp"%>
<%@include file="../../common/common-js.jsp"%>
</head>

<body>
<div style="height: 100%;overflow-y: auto;">
<div id="forms" class="mt10">
   <div class="box">
     <div class="box_border">
       <div class="box_top" ><b class="pl15" >系统管理——>用户详情</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
		<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="td_right">
                                                            用户名 : 
                    </td>
                    <td class="td_left">
                        ${user.sysUserName}
                     </td>
                   </tr>
                   <c:if test="${userSession.sysUserName eq user.sysUserName }">
	                   <tr>
	                    <td class="td_right">
	                                                            用户密码 : 
	                    </td>
	                    <td class="td_left">
	                      ${user.sysUserPass}
	                     </td>
	                 </tr>
	              </c:if>
                 <tr>
                    <td class="td_right">
                                                        用户性别 : 
                    </td>
                    <td class="td_left">
                       <c:if test="${user.sysUserSex eq '1'}">男</c:if> 
                       <c:if test="${user.sysUserSex ne '1'}">女</c:if>
                    </td>
                 </tr>
                 
                  <tr>
                    <td class="td_right">
                                                        所属角色 : 
                    </td>
                    <td class="td_left">
                       	${user.roleName}
                    </td>
                 </tr>
                 
                 <tr>                        
                    <td class="td_right">
                                                        用户地址 : 
                    </td>
                    <td class="td_left">
                         ${user.sysUserAddress}
                    </td>
                </tr>
                
                <tr>
                    <td class="td_right">
                                                        联系电话 : 
                     </td>
                    <td class="td_left">
						${user.sysUserPhone}
                    </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        邮箱地址 : 
                     </td>
                    <td class="td_left">
						${user.sysUserMail}
                    </td>
                </tr>
                
                <tr>
                    <td class="td_right">
                        QQ : 
                    </td>
                    <td class="td_left">
                      ${user.sysUserQq}
                    </td>
                </tr>
                
                <tr>
                    <td class="td_right">
                        	是否超管 : 
                    </td>
                    <td class="td_left">
                      <c:if test="${user.parentId eq '-1'}">是</c:if> 
                      <c:if test="${user.parentId ne '-1'}">否</c:if>
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
