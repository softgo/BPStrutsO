<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
        
        /*
		 var propsName1 = $("#propsName1").val();
    	 var propsName2 = $("#propsName2").val();		  
    	 if(propsName1=="" || propsName1==null){
    	 	$("#propsName1Tag").html("请输入XXX!");
    	 	return false;
    	 }else{
    	 	$("#propsName1Tag").html("");
    	 }
    	 if( propsName2=="" || propsName2==null ){
    	 	$("#propsName2Tag").html("请输入XXX！");
    	 	return false;
    	 }else{
    	 	$("#propsName2Tag").html("");
    	 }
    	 */
    	 
    	 //提交.
    	 document.userForm.submit();
   	  	 return true;
	  }
	  
	  //查找是否已经存在.
	  $(function (){
	  		$("#propsName1").change(function(){
				var propsName1 = $("#propsName1").val();
				if(propsName1 != null && propsName1 != ''){
					$.ajax({
						url:'<%=request.getContextPath()%>/background/sysUsers/findByName.html',
						data:{"objName":propsName1},
						type : "POST",
						success:function(msg){
							var obj = eval(msg);
							if (obj.msg == "Y"){
								$("#propsName1Tag").html(obj.content);
								$("#propsName1").val("");
							}else{
								$("#propsName1Tag").html("");
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
       <div class="box_top" ><b class="pl15" >系统管理——>修改信息</b></div>
        <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
		<form action="<%=request.getContextPath()%>/background/productInfoDesc/updateById.html" method="post" name="userForm" id="userForm">

			<input type="hidden" name="productCode" id="productCode" value="${objId}"><!-- 修改时候自动添加 -->
			<input type="hidden" name="propsName" id="propsName"  value="${ojbect.propsName}"><!-- 修改时候检查用的,根据实际情况添加 -->
			
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                 <!-- 修改的信息 -->
                			<tr>
					<td class="td_right">
						产品的型号,NN0001,...NN0006:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="productCode" id="productCode" value="${object.productCode}"></input>
					<font color="red"> <span id="productCodeTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						产品的年化利率:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="productRate" id="productRate" value="${object.productRate}"></input>
					<font color="red"> <span id="productRateTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户临时利率:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="tempRate" id="tempRate" value="${object.tempRate}"></input>
					<font color="red"> <span id="tempRateTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户临时利率描述:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="tempRateDesc" id="tempRateDesc" value="${object.tempRateDesc}"></input>
					<font color="red"> <span id="tempRateDescTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						投资期限(多少天):
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="investDays" id="investDays" value="${object.investDays}"></input>
					<font color="red"> <span id="investDaysTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						剩余金额:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="totalAmount" id="totalAmount" value="${object.totalAmount}"></input>
					<font color="red"> <span id="totalAmountTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						万元收益(元):
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="wanyuanIncome" id="wanyuanIncome" value="${object.wanyuanIncome}"></input>
					<font color="red"> <span id="wanyuanIncomeTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						还款方式描述:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="repayTypeDesc" id="repayTypeDesc" value="${object.repayTypeDesc}"></input>
					<font color="red"> <span id="repayTypeDescTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						起息描述:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="incomeDesc" id="incomeDesc" value="${object.incomeDesc}"></input>
					<font color="red"> <span id="incomeDescTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						投资要求:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="investRequirement" id="investRequirement" value="${object.investRequirement}"></input>
					<font color="red"> <span id="investRequirementTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						资金锁定期:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="amountInRate" id="amountInRate" value="${object.amountInRate}"></input>
					<font color="red"> <span id="amountInRateTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						保障方式:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="securityMode" id="securityMode" value="${object.securityMode}"></input>
					<font color="red"> <span id="securityModeTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						项目介绍:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="productDesc" id="productDesc" value="${object.productDesc}"></input>
					<font color="red"> <span id="productDescTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						状态：1正常;0弃用:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="status" id="status" value="${object.status}"></input>
					<font color="red"> <span id="statusTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						创建时间:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="createDate" id="createDate" value="${fn:substring(object.createDate,0,19)}"></input>
					<font color="red"> <span id="createDateTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						更新时间:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="updateDate" id="updateDate" value="${fn:substring(object.updateDate,0,19)}"></input>
					<font color="red"> <span id="updateDateTag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="str1" id="str1" value="${object.str1}"></input>
					<font color="red"> <span id="str1Tag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="str2" id="str2" value="${object.str2}"></input>
					<font color="red"> <span id="str2Tag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="str3" id="str3" value="${object.str3}"></input>
					<font color="red"> <span id="str3Tag" /></font>
					</td>
				</tr>
				<tr>
					<td class="td_right">
						备用字段:
					</td>
					<td class="td_left" >
					<input class="input-text lh30" size="40" name="str4" id="str4" value="${object.str4}"></input>
					<font color="red"> <span id="str4Tag" /></font>
					</td>
				</tr>
	     
                                   
				<tr>
					<td class="td_right">
                    </td>
                    <td class="td_left">
                       <sec:authorize ifAnyGranted="ROLE_product_info_desc_edit">
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
