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
<form id="fenye" name="fenye" action="<%=request.getContextPath()%>/background/productInfoDesc/find.html" method="post">
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
                <b>&nbsp;&nbsp;系统管理——>风险用户管理列表 </b>
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
	                         <sec:authorize ifAnyGranted="ROLE_product_info_desc_addUI">
	                            <input type="button" name="button" class="btn btn82 btn_add" value="新增" onclick="GOTO('<%=request.getContextPath()%>/background/productInfoDesc/addUI.html')">
	                         </sec:authorize>
	                         <sec:authorize ifAnyGranted="ROLE_product_info_desc_delete">
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
              									<th>产品的型号,NN0001,...NN0006</th>
							<th>产品的年化利率</th>
							<th>用户临时利率</th>
							<th>用户临时利率描述</th>
							<th>投资期限(多少天)</th>
							<th>剩余金额</th>
							<th>万元收益(元)</th>
							<th>还款方式描述</th>
							<th>起息描述</th>
							<th>投资要求</th>
							<th>资金锁定期</th>
							<th>保障方式</th>
							<th>项目介绍</th>
							<th>状态：1正常;0弃用</th>
							<th>创建时间</th>
							<th>更新时间</th>
							<th>备用字段</th>
							<th>备用字段</th>
							<th>备用字段</th>
							<th>备用字段</th>
	
              			<th >基本操作</th>
			          </tr>
			          <c:forEach var="key" items="${pageView.records}">
			              <tr class="tr">
							<!-- 显示的内容 -->
           										<td align="center" >
						<input type="checkbox" name="check" value="${key.productCode}" />
						</td>
						<td align="center" ><a href="<%=request.getContextPath()%>/background/productInfoDesc/findById.html?objId=${key.productCode}&type=0">${key.productCode}</a></td>
						<td align="center" >${key.productRate}</td>
						<td align="center" >${key.tempRate}</td>
						<td align="center" >${key.tempRateDesc}</td>
						<td align="center" >${key.investDays}</td>
						<td align="center" >${key.totalAmount}</td>
						<td align="center" >${key.wanyuanIncome}</td>
						<td align="center" >${key.repayTypeDesc}</td>
						<td align="center" >${key.incomeDesc}</td>
						<td align="center" >${key.investRequirement}</td>
						<td align="center" >${key.amountInRate}</td>
						<td align="center" >${key.securityMode}</td>
						<td align="center" >${key.productDesc}</td>
						<td align="center" >${key.status}</td>
						<td align="center" >${fn:substring(key.createDate,0,19)}</td>
						<td align="center" >${fn:substring(key.updateDate,0,19)}</td>
						<td align="center" >${key.str1}</td>
						<td align="center" >${key.str2}</td>
						<td align="center" >${key.str3}</td>
						<td align="center" >${key.str4}</td>
						<td align="center" >
						
						<sec:authorize ifAnyGranted="ROLE_product_info_desc_info">
							<img src="<%=request.getContextPath()%>/images/admin.gif" width="10" height="10" />
							<a href="<%=request.getContextPath()%>/background/productInfoDesc/findById.html?objId=${key.productCode}&type=0">
							详细信息</a>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_product_info_desc_edit">
							<img src="<%=request.getContextPath()%>/images/edt.gif" width="12" height="12" />
							<a href="<%=request.getContextPath()%>/background/productInfoDesc/findById.html?objId=${key.productCode}&type=1">编辑</a>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_product_info_desc_delete">
							<img src="<%=request.getContextPath()%>/images/del.gif" width="16" height="16" />
							<a href="javascript:void(0);" onclick="deleteId('<%=request.getContextPath()%>/background/productInfoDesc/deleteById.html','${key.productCode}')">删除</a>
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
