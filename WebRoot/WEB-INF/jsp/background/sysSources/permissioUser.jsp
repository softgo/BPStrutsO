<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="../../common/taglib.jsp" %>
	<%@include file="../../common/common-css.jsp" %>
	<%@include file="../../common/common-js.jsp" %>
    <title>分配权限</title>
     <script src="<%=request.getContextPath()%>/js/jquery_1_7_2_min.js" type="text/javascript"></script>
     <script src="<%=request.getContextPath()%>/js/ligerUI/base.js" type="text/javascript"></script>
    <link href="<%=request.getContextPath()%>/js/ligerUI/ligerui-tree.css" rel="stylesheet" type="text/css" />
    <script src="<%=request.getContextPath()%>/js/ligerUI/ligerTree.js" type="text/javascript"></script>
    
    <style type="text/css">
      input{font-size: 12px}
    </style>
    
  <script type="text/javascript">
      	var tree;
        var manager=null;
        $(function ()
        {
            ${resources}
            tree=$("#tree1").ligerTree({ 
            	data:data, 
                textFieldName: 'fname', 
                attribute: ['fid', 'pfid', 'fname'],  
                idFieldName :'fid',
                parentIDFieldName :'pfid',
                checkbox: true,
                autoCheckboxEven:true,
                onCheck:onCheck,
                onSelect: onSelect

            });
            manager = $("#tree1").ligerGetTreeManager();
        });
        
        function onCheck(d,checked){
			var children = d.data.children;
			if(children && children.length){ 
			    $(children).each(function(){
			           if(checked) {
                            tree.selectNode(this);
			           }else{ 
        	                tree.cancelSelect(this);
                       }
			     });
			} 
		}
        
        function onSelect(){
            autoCheckboxEven:true
        };
        
        function getfun() //这里的异步加载逻辑取决于你的数据库设计，把选中节点的id传回去，传回子节点json然后加载  
        {
 			var fids="";
 			var notes = manager.getChecked();
             for (var i = 0; i < notes.length; i++)
             {
             	fids +=notes[i].data.fid+",";
             	if(notes[i].data.pfid != "" && notes[i].data.pfid != "null"){
             		fids +=notes[i].data.pfid+",";
             	};
             }
             if(fids == ""){
                 alert("还没有分配权限!!");
                 return;
             }
             if("${roleId}" != ""){
            	 $.ajax({
               		async:false, //请勿改成异步，下面有些程序依赖此请数据
               		type : "POST",
               		data:{roleId:"${roleId}",rescId:fids},
               		url: "<%=request.getContextPath()%>/background/sysSources/saveSysSourcesRoles.html",
               		dataType:'json',
               		success: function(json){
               			if(json == "1000"){
     						alert("分配成功!");
     						location.href="<%=request.getContextPath()%>/background/sysRoles/find.html";
                       	}else if(json == "1001"){
                       		alert("分配失败!!");
                         };
                	}
               	});
             }else{
				alert("该用户还没有分配角色或用户的角色被删除了，请重新分配角色!!");
             }
        }
        
        $(function() {
        	$.ajaxSetup ({
                cache: false //关闭AJAX相应的缓存
             });
        });
        
    </script>
</head>
<body style="padding:10px">   
<div style="overflow-y:scroll;overflow-x:scroll; height:75%;">
    <div style="width:100%;height:500px; margin:5px; float:left; border:1px solid #ccc; overflow-y:scroll; overflow-x:scroll;align:center;">
    <ul id="tree1" style="font-size: 13px; align:center;align-text:center;"></ul>
    </div> 
     <div align="center">
	    <sec:authorize ifAnyGranted="ROLE_sys_roles_permissions">
	       <input type="button" name="button" class="btn btn82 btn_save2" value="保存"  onclick="return getfun();"/>　　
	    </sec:authorize>
	    <input type="button"  class="btn btn82 btn_nochecked" value="返回" onclick="location.href='javascript:history.go(-1)'">
    </div>
</div>   
</body>
</html>
