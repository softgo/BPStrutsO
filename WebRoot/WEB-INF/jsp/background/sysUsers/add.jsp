<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/taglib.jsp"%>
<%@include file="../../common/common-css.jsp"%>
<%@include file="../../common/common-js.jsp"%>
</head>

 <script type="text/javascript">	
      //防止重复提交.  
	  function saveData(){
		 var userName = $("#sysUserName").val();
    	 var userPassword = $("#sysUserPass").val();		  
    	 if(userName=="" || userName==null){
    	 	$("#sysUserNameTag").html("请输入用户名称!");
    	 	return false;
    	 }else{
    	 	$("#sysUserNameTag").html("");
    	 }
    	 if( userPassword=="" || userPassword==null ){
    	 	$("#sysUserPassTag").html("请输入密码！");
    	 	return false;
    	 }else{
    	 	$("#sysUserPassTag").html("");
    	 }
    	 
    	 var sysUserPhone = $("#sysUserPhone").val(); 
         var sysUserMail = $("#sysUserMail").val(); 
         var sysUserQq =  $("#sysUserQq").val();
         if(sysUserPhone!=null && sysUserPhone!=""){
            if( !isPhone(sysUserPhone) ||sysUserPhone.length !=11 ){
                $("#sysUserPhoneTag").html("请输入有效的手机号码！");
                return false;
            }else{
                $("#sysUserPhoneTag").html("");
            }
         }
         if(sysUserMail!=null && sysUserMail!=""){
            if( !isEmail(sysUserMail)){
                $("#sysUserMailTag").html("请输入有效的邮箱！");
                return false;
            }else{
                $("#sysUserMailTag").html("");
            }
         }
         if(sysUserQq!=null && sysUserQq!=""){
            if( !isNum(sysUserQq)){
                $("#sysUserQqTag").html("请输入有效的 QQ号！");
                return false;
            }else{
                $("#sysUserQqTag").html("");
            }
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
								$("#sysUserNameTag").html(obj.content);
								$("#sysUserName").val("");
							}else{
								$("#sysUserNameTag").html("");
							}
							return ;
						}
					});
				}
	  		});
	  });
	  
  </script>
  
<body>

<div style="height: 100%;overflow-y: auto;">
<div id="forms" class="mt10">
   <div class="box">
     <div class="box_border">
       <div class="box_top" ><b class="pl15" >系统管理——>添加用户</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
		<form action="<%=request.getContextPath()%>/background/sysUsers/addOne.html" method="post" name="userForm" id="userForm">
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="td_right">
                                                            用户名 : 
                    </td>
                    <td class="td_left">
                        <input  class="input-text lh30" size="40" name="sysUserName" id="sysUserName" />
                        <font color="red"> <span id="sysUserNameTag"/></font>
                     </td>
                   </tr>
                   
                   <tr>
                    <td class="td_right">
                                                            用户密码 : 
                    </td>
                    <td class="td_left">
                      <input  class="input-text lh30" size="40"  name="sysUserPass" id="sysUserPass" />
                      <font color="red"><span id="sysUserPassTag"/></font>
                     </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        用户性别 : 
                    </td>
                    <td class="td_left">
                           <input type="radio" name="sysUserSex" value="1" checked="checked"/>男
                           <input type="radio" name="sysUserSex" value="0"/>女
                    </td>
                 </tr>
                 <tr>                        
                    <td class="td_right">
                                                        用户地址 : 
                    </td>
                    <td class="td_left">
                         <input class="input-text lh30" size="40" name="sysUserAddress" id="sysUserAddress"/>
                         <font color="red"><span id="sysUserAddressTag"/></font>
                    </td>
                </tr>
                
                <tr>
                    <td class="td_right">
                                                        联系电话 : 
                     </td>
                    <td class="td_left">
                       <input class="input-text lh30" size="40" name="sysUserPhone"  id="sysUserPhone"/>
                       <font color="red"><span id="sysUserPhoneTag"/></font>
                    </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        邮箱地址 : 
                     </td>
                    <td class="td_left">
                      <input class="input-text lh30" size="40" name="sysUserMail"  id="sysUserMail"/>
                      <font color="red"><span id="sysUserMailTag"/></font>
                    </td>
                </tr>
                
                <tr>
                    <td class="td_right">
                        QQ : 
                    </td>
                    <td class="td_left">
                      <input class="input-text lh30" size="40" name="sysUserQq"  id="sysUserQq"/>
                      <font color="red"><span id="sysUserQqTag"/></font>
                    </td>
                </tr>
				<tr>
					<td class="td_right">
                       
                    </td>
                    <td class="td_left">
                       <sec:authorize ifAnyGranted="ROLE_sys_users_addUI">
								<input type="button" name="button" class="btn btn82 btn_save2" value="保存" onclick="return saveData()" > 
				       </sec:authorize>
                      <input type="button" name="button" class="btn btn82 btn_res" value="重置" onclick="clearText()">
                      <input type="button"  class="btn btn82 btn_nochecked" value="返回" onclick="location.href='javascript:history.go(-1)'">
                    </td>
				</tr>
			</table>
		  </form>
		  </div>
          </div>
        </div>
     </div>
		
	</div>
</body>
</html>
