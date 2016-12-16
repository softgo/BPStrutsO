<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/taglib.jsp"%>
<%@include file="../../common/common-css.jsp"%>
<%@include file="../../common/common-js.jsp"%>
    <style type="text/css">
      input{font-size: 12px}
    </style>
  </head>
  
  <script type="text/javascript">

	  function saveData(){
		 var sourceName = $("#sourceName").val();
    	 var resKey = $("#sourceKey").val();		  
    	 var resUrl = $("#sourceUrl").val();	
    	 if(sourceName=="" || sourceName==null){
    	 	$("#sourceNameTag").html("请填入资源名称!");
    	 	return false;
    	 }else{
    	 	$("#sourceNameTag").html("");
    	 }
    	 if( resKey=="" || resKey==null ){
    	 	$("#sourceKeyTag").html("请填入资源KEY!");
    	 	return false;
    	 }else{
    	 	$("#sourceKeyTag").html("");
    	 }
    	 if(resUrl=="" || resUrl==null){
    	 	 $("#sourceUrlTag").html("请填入资源URL!");
    	 	return false;
    	 }else{
    	 	$("#sourceUrlTag").html("");
    	 }
    	 //提交.
    	  document.sourceForm.submit();
   	  	  return true;
	  }
	  
	  //查找是否已经存在.
	  $(function (){
  		$("#sourceName").change(function(){
			var sourceName = $("#sourceName").val();
			if(sourceName != null && sourceName != ''){
				$.ajax({
					url:'<%=request.getContextPath()%>/background/sysSources/findByName.html',
					data:{"objName":sourceName},
					type : "POST",
					success:function(msg){
						var obj = eval(msg);
						if (obj.msg == "Y"){
							$("#sourceNameTag").html(obj.content);
							$("#sourceName").val("");
						}else{
							$("#sourceNameTag").html("");
						}
						return ;
					}
				});
			}
  		});
  		
  		$("#sourceKey").change(function(){
			var resKey = $("#sourceKey").val();
			if(resKey != null && resKey != ''){
				$.ajax({
					url:'<%=request.getContextPath()%>/background/sysSources/findByKey.html',
					data:{"resKey":resKey},
					type : "POST",
					success:function(msg){
						var obj = eval(msg);
						if (obj.msg == "Y"){
							$("#sourceKeyTag").html(obj.content);
							$("#sourceKey").val("");
						}else{
							$("#sourceKeyTag").html("");
						}
						return ;
					}
				});
			}
  		});
  		
  		$("#sourceUrl").change(function(){
			var resUrl = $("#sourceUrl").val();
			if(resUrl != null && resUrl != ''){
				$.ajax({
					url:'<%=request.getContextPath()%>/background/sysSources/findByUrl.html',
					data:{"resUrl":resUrl},
					type : "POST",
					success:function(msg){
						var obj = eval(msg);
						if (obj.msg == "Y"){
							$("#sourceUrlTag").html(obj.content);
							$("#sourceUrl").val("");
						}else{
							$("#sourceUrlTag").html("");
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
       <div class="box_top" ><b class="pl15" > 资源管理——>添加资源</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
		<form action="<%=request.getContextPath()%>/background/sysSources/addOne.html" method="post" name="sourceForm" id="sourceForm">
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="td_right">
                                                            上级资源 : 
                    </td>
                    <td class="td_left">
                          <select name="parentId" class="STYLE1" style="width:245px;">
                                <option value="1010">顶级菜单</option>
                                <c:forEach var="key" items="${resLists}">
                                    <option value="${key.sourceId}" >${key.sourceName}</option>
                                </c:forEach>
                           </select>
                        <font color="red"> <span id="parentIdTag"/></font>
                     </td>
                   </tr>
                   
                   <tr>
                    <td class="td_right">
                                                            资源名称 : 
                    </td>
                    <td class="td_left">
                      <input  class="input-text lh30" size="40"  name="sourceName" id="sourceName" />
                      <font color="red"><span id="sourceNameTag"/></font>
                     </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        资源KEY : 
                    </td>
                    <td class="td_left">
                        <input  class="input-text lh30" size="40"  name="sourceKey" id="sourceKey" />
                        <font color="red"><span id="sourceKeyTag"/></font>
                    </td>
                 </tr>
                 <tr>                        
                    <td class="td_right">
                                                        资源URL : 
                    </td>
                    <td class="td_left">
                         <input class="input-text lh30" size="40" name="sourceUrl" id="sourceUrl"/>
                         <font color="red"><span id="sourceUrlTag"/></font>
                    </td>
                </tr>
               
               <tr>                        
                    <td class="td_right">
                                                        资源类型 : 
                    </td>
                    <td class="td_left">
	                   <c:if test="${tag eq 'forSelf'}">
		                        <input name="sourceType" type="radio" value="0"/>目录　　
		                        <input name="sourceType" type="radio" value="1"/>菜单　　
		                        <input name="sourceType" type="radio" value="2" checked="checked"/>按扭
	                        </c:if>
	                        <c:if test="${tag eq 'new'}">
	                            <input name="sourceType" type="radio" value="0" checked="checked"/>目录　　
                        </c:if>
                         <font color="red"><span id="sourceTypeTag"/></font>
                    </td>
                </tr>
                <tr>                        
                    <td class="td_right">
                                                        优先级 : 
                    </td>
                    <td class="td_left">
                         <input class="input-text lh30" size="40" name="sourceLevel" id="sourceLevel"/>
                         <font color="red"><span id="sourceLevelTag"/></font>
                    </td>
                </tr>
                
                <tr>                        
                    <td class="td_right">
                                                        资源描述 : 
                    </td>
                    <td class="td_left">
                         <input class="input-text lh30" size="40" name="description" id="description"/>
                         <font color="red"><span id="descriptionTag"/></font>
                    </td>
                </tr>
				<tr>
					<td class="td_right">
                       
                    </td>
                    <td class="td_left">
                       <sec:authorize ifAnyGranted="ROLE_sys_sources_addUI">
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
