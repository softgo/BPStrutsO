<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="../../common/taglib.jsp"%>
<%@include file="../../common/common-css.jsp"%>
</head>

 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.min.js"></script>
 <script type="text/javascript">	
      //防止重复提交.  
	  function saveData(){
		 var userName = $("#sysUserName").val();
    	 var userPassword = $("#sysUserPass").val();		  
    	 if(userName=="" || userName==null){
    	 	alert("请填入用户名称!");
    	 	return false;
    	 }
    	 if( userPassword=="" || userPassword==null ){
    	 	alert("请填入用户密码!");
    	 	return false;
    	 }
    	 //提交.
    	  document.userForm.submit();
   	  	  return true;
	  }
	  
	  //查找是否已经存在.
	  $(function (){
	  		$("#sysUserName").change(function(){
				var userName = $("#sysUserName").val();
				if(userName != null && userName != ''){
					$.ajax({
						url:'<%=request.getContextPath()%>/background/sysUsers/findByName.html',
						data:{"objName":userName},
						type : "POST",
						success:function(msg){
							var obj = eval(msg);
							if (obj.msg == "Y"){
								alert(obj.content);
								$("#userName").val("");
								return ;
							}
						}
					});
				}
	  		});
	  });
	  
  </script>
  
<body>
	<div style="height: 100%;overflow-y: auto;">
		<br /> <br />
		<form action="<%=request.getContextPath()%>/background/sysUsers/addOne.html" method="post" name="userForm" id="userForm">
		    
			<table class="ttab" height="100" width="85%" border="0" cellpadding="0" cellspacing="1" align="center">
                <tr>
                    <td height="30" colspan="4">
                        <div align="center">
                            <font color="blue" size="4"><b>添加用户信息</b>
                            </font>
                        </div></td>
                </tr>
                
                <tr>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">用户名：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <input style="height: 20px;width: 200px" name="sysUserName" id="sysUserName" />
                        </div></td>
                   
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">用户密码：</div></td>
                    <td>
                       <div align="left" class="STYLE1" style="padding-left:10px;">
                            <input style="height: 20px;width: 200px" name="sysUserPass" id="sysUserPass" />
                        </div></td>
                </tr>
                
                 <tr>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">用户性别：</div></td>
                    <td>
                       <div align="left" class="STYLE1" style="padding-left:10px;">
                           <input type="radio" name="sysUserSex" value="1" checked="checked"/>男
                           <input type="radio" name="sysUserSex" value="0"/>女
                       </div></td>
                        
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">用户地址：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <input style="height: 20px;width: 200px" name="sysUserAddress" id="sysUserAddress"/>
                        </div></td>
                   
                </tr>
                
                <tr>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">联系电话：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                           <input style="height: 20px;width: 200px" name="sysUserPhone"  id="sysUserPhone"/>
                        </div></td>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">邮箱地址：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                            <input style="height: 20px;width: 200px" name="sysUserMail"  id="sysUserMail"/>
                        </div>
                    </td>
                </tr>
                
                <tr>
                    <td height="30" width="20%">
                        <div align="right" class="STYLE1">QQ：</div></td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                             <input style="height: 20px;width: 200px" name="sysUserQq"  id="sysUserQq"/>
                        </div></td>
                        
                    <td height="30" width="20%">
                        </td>
                    <td>
                        <div align="left" class="STYLE1" style="padding-left:10px;">
                        
                        </div>
                    </td>
                </tr>
                
				<tr>
					<td colspan="4" style="padding: 10px">
						
						<div align="center">
						   <sec:authorize ifAnyGranted="ROLE_sys_users_addUI">
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
