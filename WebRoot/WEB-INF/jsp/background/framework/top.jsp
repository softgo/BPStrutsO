<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <LINK href="<%=request.getContextPath()%>/css/admin.css" type="text/css" rel="stylesheet">
      <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css" type="text/css" >
      <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" type="text/css" >
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.SuperSlide.js"></script>
<title>top页信息</title>
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
.STYLE1 {
    font-size: 12px;
    color: #2b7dbb;
}
-->
</style>

</head>

<body>
<div class="top">
     <div id="top_t">
        <div id="logo" class="fl"></div>
        <div id="photo_info" class="fr">
          <div id="photo" class="fl">
            <a href="<%=request.getContextPath()%>/background/sysUsers/findById.html?userId=${userSession.sysUserId}&type=5"  target="main" "><img src="<%=request.getContextPath()%>/images/a.png" alt="" width="60" height="60"></a>
          </div>
          <div id="base_info" class="fr">
            <div class="help_info">
              <a href="javascript:;" id="hp">&nbsp;</a>
              <a href="javascript:;" id="gy">&nbsp;</a>
              <a href="<%=request.getContextPath()%>/background/loginOut.html" title="${userSession.sysUserName}" target="_parent" id="out">&nbsp;</a>
              <a href="javascript:;" id="gy">&nbsp;</a>
            </div>
            
            <div class="info_center">
                ${userSession.sysUserName}
              <!-- 
              <span id="nt">通知</span><span><a href="#" id="notice">3</a></span>
               -->                          
            </div>
            
          </div>
        </div>
      </div>
      <div id="side_here">
        <div id="side_here_l" class="fl"></div>
        <div id="here_area" class="fl">当前位置：</div>
      </div>
    </div>
</body>
    