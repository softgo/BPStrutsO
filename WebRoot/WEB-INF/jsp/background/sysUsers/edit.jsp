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
         if(userName=="" || userName==null){
            $("#sysUserNameTag").html("请输入用户名称!");
            return false;
         }else{
            $("#sysUserNameTag").html("");
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
                var tempName = $("#tempName").val();
                if(tempName==userName){
                    return ;
                }
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
       <div class="box_top" ><b class="pl15" >系统管理——>修改用户</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
        <form action="<%=request.getContextPath()%>/background/sysUsers/updateById.html" method="post" name="userForm" id="userForm">
            <input type="hidden" name="sysUserId" value="${user.sysUserId}"/>
            <input type="hidden" name="parentId" value="${user.parentId}"/>
            <input type="hidden" name="sysUserSex" value="${user.sysUserSex}"/>
            <input type="hidden" name="registerTime" value="${user.registerTime}"/>
            <input type="hidden" name="sysStatus" value="${user.sysStatus}"/>
            <input type="hidden" name="tempName" id="tempName" value="${user.sysUserName}"/>
            <table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="td_right">
                                                            用户名 : 
                    </td>
                    <td class="td_left">
                        <input  class="input-text lh30" size="40" name="sysUserName" id="sysUserName" value="${user.sysUserName}"/>
                        <font color="red"><span id="sysUserNameTag"/></font>
                     </td>
                   </tr>
                   
                   <c:if test="${userSession.sysUserName eq user.sysUserName }">
                       <tr>
                        <td class="td_right">
                                                                用户密码 : 
                        </td>
                        <td class="td_left">
                          <input  class="input-text lh30" size="40"  name="sysUserPass" id="sysUserPass" value="${user.sysUserPass}" />
                          <font color="red"><span id="sysUserPassTag"/></font>
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
                       <select name="roleName" class="STYLE1" style="width:270px; ">
                        <option value="">选择角色</option>
                        <c:forEach var="key" items="${roles}">
                            <option value="${key.roleName}" <c:if test="${key.roleName eq user.roleName}">selected="selected"</c:if>>${key.roleName}</option>
                        </c:forEach>
                       </select>
                    </td>
                 </tr>
                 
                 <tr>                        
                    <td class="td_right">
                                                        用户地址 : 
                    </td>
                    <td class="td_left">
                         <input class="input-text lh30" size="40" name="sysUserAddress" id="sysUserAddress" value="${user.sysUserAddress}"/>
                         <font color="red"><span id="sysUserAddressTag"/></font>
                    </td>
                </tr>
                
                <tr>
                    <td class="td_right">
                                                        联系电话 : 
                     </td>
                    <td class="td_left">
                       <input class="input-text lh30" size="40" name="sysUserPhone"  id="sysUserPhone" value="${user.sysUserPhone}"/>
                       <font color="red"><span id="sysUserPhoneTag"/></font>
                    </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        邮箱地址 : 
                     </td>
                    <td class="td_left">
                      <input class="input-text lh30" size="40" name="sysUserMail"  id="sysUserMail" value="${user.sysUserMail}"/>
                      <font color="red"><span id="sysUserMailTag"/></font>
                    </td>
                </tr>
                
                <tr>
                    <td class="td_right">
                        QQ : 
                    </td>
                    <td class="td_left">
                      <input class="input-text lh30" size="40" name="sysUserQq"  id="sysUserQq" value="${user.sysUserQq}"/>
                      <font color="red"><span id="sysUserQqTag"/></font>
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
                       <sec:authorize ifAnyGranted="ROLE_sys_users_edit">
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
