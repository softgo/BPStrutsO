<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.lang.Exception"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误页面</title>
</head>
<body>
<h1>出错了</h1>
	<%
	Exception e = (Exception)request.getAttribute("exception");
	out.print(e.getMessage());
	%>
</body>
</html>