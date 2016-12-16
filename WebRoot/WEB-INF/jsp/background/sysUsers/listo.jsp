<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
<%@include file="../../common/taglib.jsp" %>
<%@include file="../../common/common-css.jsp" %>
<%@include file="../../common/common-js.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/fenyecss.css" />
	
<script type="text/javascript">

function userRole(id){
	 var url = "<%=request.getContextPath()%>/background/sysUsers/userRole.html?userId="+id;
	 var h_sp1 = 420;
	 var w_sp1 = 600;
	//兼容IE，firefox,google.模态窗口居中问题
	 var iTop2 = (window.screen.availHeight - 20 - h_sp1) / 2;
	 var iLeft2 = (window.screen.availWidth - 10 - w_sp1) / 2;
	 var params = 'menubar:no;dialogHeight=' + h_sp1 + 'px;dialogWidth=' + w_sp1 + 'px;dialogLeft=' + iLeft2 + 'px;dialogTop=' + iTop2 + 'px;resizable=yes;scrollbars=0;resizeable=0;center=yes;location:no;status:no;scroll:no'
	 window.showModalDialog(url, window, params);
	 //location.href=url;
}

</script>
</head>
<body>
<form id="fenye" name="fenye" action="<%=request.getContextPath()%>/background/sysUsers/find.html" method="post">
<table width="100%">
  <tr>
    <td height="30" background="<%=request.getContextPath()%>/images/tab_05.gif"><table width="100%">
      <tr>
        <td width="12" height="30"><img src="<%=request.getContextPath()%>/images/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%">
          <tr>
            <td width="46%" valign="middle"><table width="100%">
              <tr>
                <td width="5%"><div align="center"><img src="<%=request.getContextPath()%>/images/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：系统管理-用户列表</td>
              </tr>
            </table></td>
            <td width="54%"><table align="right" >
              <tr>
                <td width="60">
		                <table width="87%" >
		                  <tr>
		                    <td class="STYLE1"><div align="center">
		                      <input type="checkbox" name="checkbox11" id="choseAll" onclick="selectAllCheckBox()" />
		                    </div></td>
		                    <td class="STYLE4">全选</td>
		                  </tr>
		                </table>
                </td>
                <td width="52">
		                <table width="88%">
		                  <tr>
		                    <sec:authorize ifAnyGranted="ROLE_sys_users_delete">
		                    <td class="STYLE1"><div align="center"><img src="<%=request.getContextPath()%>/images/11.gif" width="14" height="14" /></div></td>
		                    <td class="STYLE4">
		                    <a href="javascript:void(0);"  onclick="return deleteAll()">
		                    	删除
		                    </a>
		                    	</td>
		                    </sec:authorize>
		                  </tr>
		                </table>
                </td>
                <td width="60">
		                <table width="90%">
		                  <tr>
		                    	<sec:authorize ifAnyGranted="ROLE_sys_users_addUI">
		                    <td class="STYLE1"><div align="center"><img src="<%=request.getContextPath()%>/images/22.gif" width="14" height="14" /></div></td>
		                    <td class="STYLE1"><div align="center">
			                    	<a href="<%=request.getContextPath()%>/background/sysUsers/addUI.html">
				                    	新增
			                    	</a>
		                    </div></td>
		                    	</sec:authorize>
		                  </tr>
		                </table>
                </td>
              </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=request.getContextPath()%>/images/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
  <td align="center">
  <!-- 这里的表单 name 必须是fenye -->
  	<div class="search_k" align="left">
		<fieldset class="search">
			<legend><img src="<%=request.getContextPath()%>/images/search_btn.gif" align="middle"/>&nbsp;<span class="STYLE1" style="color: blue;">高级查找</span></legend>
			<div class="search_content">
				用户名：<input type="text" name="sysUserName" value="${param.sysUserName}" style="height: 20px"/>　　
				<input type="submit" value="开始查询" class="input_btn_style1"/>&nbsp;&nbsp;
				<input type="reset" value="重置" class="input_btn_style1"  onclick="clearText()" />
			</div>
		</fieldset>
	</div>
  </td>
  </tr>
  <tr>
    <td><table class="listtable" width="100%">
      <tr>
        <td width="8" background="<%=request.getContextPath()%>/images/tab_12.gif">&nbsp;</td>
        <td><table class="ttab" width="100%" cellspacing="1" onmouseover="changeto()"  onmouseout="changeback()">
          <tr>
            <td width="3%" height="22" background="<%=request.getContextPath()%>/images/bg.gif" >
              <input id="chose" type="checkbox" name="checkbox" onclick="selectAllCheckBox()" />
            </td>
            <td width="15%" height="22" background="<%=request.getContextPath()%>/images/bg.gif" ><span class="STYLE1">用户名</span></td>
            <td width="15%" height="22" background="<%=request.getContextPath()%>/images/bg.gif" ><span class="STYLE1">所属角色</span></td>
            <td width="15%" height="22" background="<%=request.getContextPath()%>/images/bg.gif"  class="STYLE1">手机号码</td>
            <td width="15%" height="22" background="<%=request.getContextPath()%>/images/bg.gif"  class="STYLE1">邮箱</td>
            <td width="15%" height="22" background="<%=request.getContextPath()%>/images/bg.gif"  class="STYLE1">QQ</td>
            <td width="30%" height="22" background="<%=request.getContextPath()%>/images/bg.gif"  class="STYLE1">基本操作</td>
          </tr>
          
          <c:forEach var="key" items="${pageView.records}">
	          <tr>
	            <td height="20" >
	              <input type="checkbox" name="check" value="${key.sysUserId}" />
	            </td>
	            
	            <td height="20" ><span class="STYLE1"><a href="<%=request.getContextPath()%>/background/sysUsers/findById.html?userId=${key.sysUserId}&&type=0">${key.sysUserName}</a></span></td>
	            <td height="20" ><span class="STYLE1" style="color: blue;">${key.roleName}</span></td>
	            <td height="20" ><span class="STYLE1">${key.sysUserPhone}</span></td>
	            <td height="20" ><span class="STYLE1">${key.sysUserMail}</span></td>
	            <td height="20" ><span class="STYLE1">${key.sysUserQq}</span></td>
	            <td height="20" >
	            
	            <span class="STYLE4">
		             <sec:authorize ifAnyGranted="ROLE_sys_users_fenpeirole">
		             <img src="<%=request.getContextPath()%>/images/role.png" width="12" height="12" />
		             <a href="javascript:void(0);" onclick="userRole('${key.sysUserId}')">分配角色</a>
		             </sec:authorize>
            
	            	<sec:authorize ifAnyGranted="ROLE_sys_users_info">
		            <img src="<%=request.getContextPath()%>/images/admin.gif" width="10" height="10" />
		            <a href="<%=request.getContextPath()%>/background/sysUsers/findById.html?userId=${key.sysUserId}&type=0">
		            		详细信息</a>
		           </sec:authorize>
	            
		            <sec:authorize ifAnyGranted="ROLE_sys_users_edit">
		            <img src="<%=request.getContextPath()%>/images/edt.gif" width="12" height="12" />
		            <a href="<%=request.getContextPath()%>/background/sysUsers/findById.html?userId=${key.sysUserId}&type=1">编辑</a>
		           </sec:authorize>
		           
		           <c:if test="${userSession.sysUserName ne key.sysUserName }">
			           <sec:authorize ifAnyGranted="ROLE_sys_users_delete">
			            <img src="<%=request.getContextPath()%>/images/del.gif" width="16" height="16" />
			           	<a href="javascript:void(0);" onclick="deleteId('<%=request.getContextPath()%>/background/sysUsers/deleteById.html','${key.sysUserId}');">
			           	删除</a>
			           	</sec:authorize>
		           </c:if>
		           	
	            </span>
	            
	           </td>
	          </tr>
          </c:forEach>
          
        </table>
        </td>
        <td width="8" background="<%=request.getContextPath()%>/images/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="35" background="<%=request.getContextPath()%>/images/tab_19.gif">
    	<%@include file="../../common/webfenye.jsp" %>
    </td>
  </tr>
</table>
</form>
</body>
</html>