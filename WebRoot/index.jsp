<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <title>系统引入页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>

<script type="text/javascript">
    function into(){
	    window.location = '<%=basePath%>background/index.html';
    }
</script>
  <body onload="into()" ></body>
</html>
