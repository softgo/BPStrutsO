<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="../../common/common-css.jsp" %>
	<%@include file="../../common/taglib.jsp" %>
	<%@include file="../../common/common-js.jsp" %>
  
  <script type="text/javascript">
  
  function allocation(){
       $.ajax({
      		async:false, //请勿改成异步，下面有些程序依赖此请数据
      		type : "POST",
      		data:{userId:"${user.sysUserId}",roleId:$('input[name="roleId"]:checked').val()},
      		url: "<%=request.getContextPath()%>/background/sysUsers/allocation.html",
      		dataType:'json',
      		success: function(json){
      			if(json == "1000"){
			        alert("分配成功!");
			        location.href="<%=request.getContextPath()%>/background/sysUsers/find.html";
              	}else if(json == "1001"){
              		alert("分配失败,请联系管理员!");
                };
       		}
      	});
      }
  </script>
  
</head>
  
<body>
<div style="overflow-y:scroll; overflow-x:scroll; align:center; height:75%;">
<div id="forms" class="mt10">
   <div class="box">
     <div class="box_border">
       <div class="box_top" ><b class="pl15" >系统管理——>分配角色</b></div>
       <div class="box_center" >
		  <form action="<%=request.getContextPath()%>/background/sysUsers/userRole.html?userId=${user.sysUserId}" method="post" name="userForm" id="userForm">
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr>	
					<td class="td_right">
                                                            用户名 : 
                    </td>
                    <td class="td_left">
                       ${user.sysUserName}
                     </td>
					<td class="td_right">
                                                            所属角色 : 
                    </td>
                    <td class="td_left">
	                     <c:if test="${empty user.roleName}">
							<font color="red">没有分配角色</font>
						</c:if>
						<c:if test="${not empty user.roleName}">
							<font color="blue">${user.roleName}</font>
						</c:if>
                     </td>
                </tr>
                
			<tr>
			<td colspan="4">
				<table class="list_table" width="100%" cellspacing="1" onmouseover="changeto()"  onmouseout="changeback()">
			          <tr>
			            <th >
			                                     选择
			            </th>
			            <th >角色名</th>
			            <th >是否禁用</th>
			            <th >描述</th>
			          </tr>
			          
			          <c:forEach var="role" items="${pageView.records}">
	                     <tr class="tr">
                               <td  align="center" hight="30px" >
                               <c:if test="${user.roleName != role.roleName }">
                                   <input type="radio" name="roleId" value="${role.roleId}"/>
                               </c:if>
                               <c:if test="${user.roleName == role.roleName}">
                                   <input type="radio" name="roleId" value="${role.roleId}" checked="checked" />
                               </c:if>
                               </td>
                               <td align="center" >${role.roleName}</td>
                               <td align="center" >
                                <c:if test="${role.roleStatus eq '0'}">
                               <font color="red">禁用</font>
                               </c:if>
                               <c:if test="${role.roleStatus eq '1'}">
                               <font color="blue">正常</font>
                               </c:if></td>
                               <td align="center" >${role.description}</td>
                             </tr>
			          </c:forEach>
                 </table>
				</td>
				</tr>
				<tr>
					<td class="td_center" colspan="4">
                       <sec:authorize ifAnyGranted="ROLE_sys_users_addUI">
								<input type="button" name="button" class="btn btn82 btn_save2" value="保存" onclick="allocation()" > 
				       </sec:authorize>
                      <input type="button"  class="btn btn82 btn_nochecked" value="返回" onclick="location.href='javascript:history.go(-1)'">
                    </td>
				</tr>
 			</table>
			   <!-- 分页 -->
			     <div id="table" class="mt10">
			            <div class="box span10 oh">
			              <%@include file="../../common/webfenye.jsp" %>
			            </div>
			      </div>
		   </form>
		  </div>
          </div>
        </div>
     </div>
	</div>
</body>
</html>

