<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台管理系统</title>
</head>

<frameset rows="83,*" cols="*" framespacing="0" frameborder="no" border="0">
  <frame src="<%=request.getContextPath()%>/background/top.html" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" />
  <frame src="<%=request.getContextPath()%>/background/center.html" name="mainFrame" id="mainFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>