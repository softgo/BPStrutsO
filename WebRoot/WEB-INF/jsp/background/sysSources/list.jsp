<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="../../common/taglib.jsp" %>
	<%@include file="../../common/common-css.jsp" %>
	<%@include file="../../common/common-js.jsp" %>

	<style type="text/css">
	  input{font-size: 12px}
	</style>
</head>

<body>
<form id="fenye" name="fenye" action="<%=request.getContextPath()%>/background/sysSources/find.html" method="post">
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
                <b>&nbsp;&nbsp;资源管理——>资源列表 </b>
            </div>
            <div class="box_center pt10 pb10">
              <!-- 查询条件添加 -->
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td>&nbsp;&nbsp;资源名称</td>
                  <td><input type="text" name="sourceName" value="${param.sourceName}"  class="input-text lh25"/></td>
                    <td>
	                    <div class="box_bottom pb5 pt5 pr10" style="border-top:0px solid #dadada;">
			              <div class="search_bar_btn" style="text-align:right;">
			              
			                 <input type="submit" name="button" class="btn btn82 btn_search" value="查询">   
	                         
	                         <sec:authorize ifAnyGranted="ROLE_sys_sources_addUI">
	                            <input type="button" name="button" class="btn btn82 btn_add" value="新增" onclick="GOTO('<%=request.getContextPath()%>/background/sysSources/addUI.html?tag=forSelf')">
	                         </sec:authorize>
	                         
	                         <sec:authorize ifAnyGranted="ROLE_sys_sources_delete">
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
			            <th >资源名</th>
			            <th >资源KEY</th>
			            <th >资源URL</th>
			            <th >资源类型</th>
			            <th >优先级</th>
			            <th >上级资源</th>
			            <th>是否嵌套</th>
			            <th>操作</th>
			          </tr>
			          
			          <c:forEach var="resources" items="${pageView.records}">
				          <tr class="tr">
				            <td align="center" hight="30px">
				              <input type="checkbox" name="check" value="${resources.sourceId}" />
				            </td>
				            
				            <td align="center" >${resources.sourceName}</td>
				            <td align="center" >${resources.sourceKey}</td>
				            <td align="center" >${resources.sourceUrl}</td>
				            <td align="center" >
				            <c:if test="${resources.sourceType eq '0'}">
				            <font color="red">目录</font>
				            </c:if>
				            <c:if test="${resources.sourceType eq '1'}">
				            <font color="blue">菜单</font>
				            </c:if>
				            <c:if test="${resources.sourceType eq '2'}">
				         				按扭
				            </c:if>
				            </td>
				            <td align="center" >${resources.sourceLevel}</td>
				            <td align="center" >
				            <c:if test="${empty resources.parentName}">
				            	功能菜单
				            </c:if>
				            <c:if test="${not empty resources.parentName}">
				            	 ${resources.parentName}
				            </c:if>
				            </td>
				            <td align="center" >
				            <c:if test="${resources.isIframe eq '1'}">
				                                            是
				            </c:if>
				            <c:if test="${resources.isIframe eq '0'}">
				                                          否
				            </c:if>
				            </td>
				            <td align="center" >
					            <sec:authorize ifAnyGranted="ROLE_sys_sources_info">
					            <img src="<%=request.getContextPath()%>/images/admin.gif" width="12" height="12" />
					            	<a href="<%=request.getContextPath()%>/background/sysSources/findById.html?sourcesId=${resources.sourceId}&type=0">
					            	显示详细信息</a>
								</sec:authorize>
								
					            <sec:authorize ifAnyGranted="ROLE_sys_sources_edit">
					            <img src="<%=request.getContextPath()%>/images/edt.gif" width="12" height="12" />
					            <a href="<%=request.getContextPath()%>/background/sysSources/findById.html?sourcesId=${resources.sourceId}&type=1">
					            	    编辑
					            </a>
					            </sec:authorize>
					            
					            <sec:authorize ifAnyGranted="ROLE_sys_sources_delete">
					            <img src="<%=request.getContextPath()%>/images/del.gif" width="12" height="12" />
					            	<a href="javascript:void(0);" onclick="deleteId('<%=request.getContextPath()%>/background/sysSources/deleteById.html','${resources.sourceId}')">
					            	删除</a>
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