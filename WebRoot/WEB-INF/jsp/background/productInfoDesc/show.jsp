<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
       <div class="box_top" ><b class="pl15" >系统管理——>信息展示</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
		<form action="" method="post" name="userForm" id="userForm">
			<input type="hidden" name="productCode" value="${objId}"><!-- 修改时候自动添加 -->
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                 <!-- 展示信息 -->
                			<tr>
					<td class="td_right">
						产品的型号,NN0001,...NN0006:
					</td>
					<td class="td_left" >
					${object.productCode}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						产品的年化利率:
					</td>
					<td class="td_left" >
					${object.productRate}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户临时利率:
					</td>
					<td class="td_left" >
					${object.tempRate}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户临时利率描述:
					</td>
					<td class="td_left" >
					${object.tempRateDesc}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						投资期限(多少天):
					</td>
					<td class="td_left" >
					${object.investDays}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						剩余金额:
					</td>
					<td class="td_left" >
					${object.totalAmount}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						万元收益(元):
					</td>
					<td class="td_left" >
					${object.wanyuanIncome}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						还款方式描述:
					</td>
					<td class="td_left" >
					${object.repayTypeDesc}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						起息描述:
					</td>
					<td class="td_left" >
					${object.incomeDesc}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						投资要求:
					</td>
					<td class="td_left" >
					${object.investRequirement}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						资金锁定期:
					</td>
					<td class="td_left" >
					${object.amountInRate}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						保障方式:
					</td>
					<td class="td_left" >
					${object.securityMode}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						项目介绍:
					</td>
					<td class="td_left" >
					${object.productDesc}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						状态：1正常;0弃用:
					</td>
					<td class="td_left" >
					${object.status}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						创建时间:
					</td>
					<td class="td_left" >
					${fn:substring(object.createDate,0,19)}"
					</td>
				</tr>
				<tr>
					<td class="td_right">
						更新时间:
					</td>
					<td class="td_left" >
					${fn:substring(object.updateDate,0,19)}"
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					${object.str1}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					${object.str2}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					${object.str3}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					${object.str4}
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
		  </form>
		  </div>
          </div>
        </div>
     </div>
	</div>
</body>
</html>
