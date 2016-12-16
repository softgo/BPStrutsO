<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="../../common/taglib.jsp" %>
	<%@include file="../../common/common-css.jsp" %>
	<%@include file="../../common/common-js.jsp" %>
<!-- 写需要的 js 文件 -->
<script type="text/javascript">
	
</script>
	
</head>
<body>
<form id="fenye" name="fenye" action="<%=request.getContextPath()%>/background/customerUsers/find.html" method="post">
<div style="overflow-y:scroll;overflow-x:scroll; height:75%;">
<table width="100%" cellpadding="0" cellspacing="0" >
  <!-- 搜索页面展示 -->
  <tr>
     <td align="left" >
       <div id="search_bar" class="mt10">
       <div class="box">
          <div class="box_border">
          	<!-- 表头显示 -->
            <div class="box_top">
                <b>&nbsp;&nbsp;系统管理——>前台用户管理 </b>
            </div>
            <div class="box_center pt10 pb10">
              <!-- 查询条件添加 -->
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td>&nbsp;&nbsp;查询条件1</td>
                  <td><input type="text" name="propsName1" value="${propsName1}" class="input-text lh25"/></td>
                  <td>&nbsp;&nbsp;查询条件2</td>
                  <td><input type="text" name="propsName2" value="${propsName2}" class="input-text lh25"/></td>
                    <td>
	                    <div class="box_bottom pb5 pt5 pr10" style="border-top:0px solid #dadada;">
			              <div class="search_bar_btn" style="text-align:right;">
			                 <input type="submit" name="button" class="btn btn82 btn_search" value="查询">   
	                         <sec:authorize ifAnyGranted="ROLE_customer_users_addUI">
	                            <input type="button" name="button" class="btn btn82 btn_add" value="新增" onclick="GOTO('<%=request.getContextPath()%>/background/customerUsers/addUI.html')">
	                         </sec:authorize>
	                         <sec:authorize ifAnyGranted="ROLE_customer_users_delete">
	                            <input type="button" name="button" class="btn btn82 btn_del" value="删除" onclick="return deleteAll()">
	                         </sec:authorize>
	                         <input type="button" name="button" class="btn btn82 btn_res" value="重置" onclick="clearText()">
			              </div>
			            </div>
                    </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        </div>
     </td>
  </tr>
  <!-- 主页面展示 -->
  <tr>
    <td>
        <div id="table" class="mt10">
            <div class="box span10 oh">
              <table class="list_table" width="100%" cellspacing="1" onmouseover="changeto()"  onmouseout="changeback()">
			          <tr>
			            <th >
			              <input id="chose" type="checkbox" name="checkbox" onclick="selectAllCheckBox()" />
			            </th>
			            <!-- 显示的列 -->
              									<th>用户id</th>
							<th>用户名</th>
							<th>用户密码</th>
							<th>用户手机号</th>
							<th>用户邮箱</th>
							<th>用户QQ</th>
							<th>用户性别;1:男,0:女</th>
							<th>用户地址</th>
							<th>用户微信号</th>
							<th>微信公众号ID</th>
							<th>微信公众号key</th>
							<th>微信公众号token</th>
							<th>微信公众号秘钥</th>
							<th>用户主页url地址</th>
							<th>用户头像</th>
							<th>用户所属行业ids</th>
							<th>用户所属套餐ids</th>
							<th>用户所属平台ids</th>
							<th>用户应用ids</th>
							<th>用户频道ids</th>
							<th>用户商铺ids</th>
							<th>用户状态;1:启用,0:弃用</th>
							<th>注册时间</th>
							<th>修改时间</th>
	
              			<th >基本操作</th>
			          </tr>
			          <c:forEach var="key" items="${pageView.records}">
			              <tr class="tr">
							<!-- 显示的内容 -->
           										<td align="center" >
						<input type="checkbox" name="check" value="${key.userId}" />
						</td>
						<td align="center" ><a href="<%=request.getContextPath()%>/background/customerUsers/findById.html?objId=${key.userId}&type=0">${key.userId}</a></td>
						<td align="center" >${key.userName}</td>
						<td align="center" >${key.userPass}</td>
						<td align="center" >${key.userPhone}</td>
						<td align="center" >${key.userMail}</td>
						<td align="center" >${key.userQq}</td>
						<td align="center" >${key.userGender}</td>
						<td align="center" >${key.userAddress}</td>
						<td align="center" >${key.userWechat}</td>
						<td align="center" >${key.publicWechatId}</td>
						<td align="center" >${key.publicWechatKey}</td>
						<td align="center" >${key.publicWechatToken}</td>
						<td align="center" >${key.publicWechatSkey}</td>
						<td align="center" >${key.userUrl}</td>
						<td align="center" >${key.userImage}</td>
						<td align="center" >${key.userIndustryId}</td>
						<td align="center" >${key.userPackageId}</td>
						<td align="center" >${key.userPlatformId}</td>
						<td align="center" >${key.userAppId}</td>
						<td align="center" >${key.userChannelId}</td>
						<td align="center" >${key.userShopId}</td>
						<td align="center" >${key.userStatus}</td>
						<td align="center" >${fn:substring(key.registerTime,0,19)}</td>
						<td align="center" >${fn:substring(key.lastUpdateTime,0,19)}</td>
						<td align="center" >
						
						<sec:authorize ifAnyGranted="ROLE_customer_users_info">
							<img src="<%=request.getContextPath()%>/images/admin.gif" width="10" height="10" />
							<a href="<%=request.getContextPath()%>/background/customerUsers/findById.html?objId=${key.userId}&type=0">
							详细信息</a>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_customer_users_edit">
							<img src="<%=request.getContextPath()%>/images/edt.gif" width="12" height="12" />
							<a href="<%=request.getContextPath()%>/background/customerUsers/findById.html?objId=${key.userId}&type=1">编辑</a>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_customer_users_delete">
							<img src="<%=request.getContextPath()%>/images/del.gif" width="16" height="16" />
							<a href="javascript:void(0);" onclick="deleteId('<%=request.getContextPath()%>/background/customerUsers/deleteById.html','${key.userId}')">删除</a>
						</sec:authorize>
						
						</td>	
			              </tr>
			          </c:forEach>
              </table>
        </div>
      </div>
    </td>
  </tr>
   <!-- 分页 -->
   <tr>
    <td>
        <div id="table" class="mt10">
            <div class="box span10 oh">
              <%@include file="../../common/webfenye.jsp" %>
            </div>
      </div>
    </td>
  </tr>
  
</table>
</div>
</form>
</body>
</html>
