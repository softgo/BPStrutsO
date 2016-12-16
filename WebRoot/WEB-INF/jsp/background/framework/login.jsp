<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理系统</title>
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
    overflow:hidden;
}
</style>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>

<script type="text/javascript">
  
  function checkUserForm(){
    var userName = $("#name").val();
    var userPassword = $("#pass").val();
    var autocode = $("#autocode").val();
    if(userName=="" || userName==null){
      alert("用户名不能为空");
      return false;
    }else if(userPassword=="" || userPassword==null){
      alert("密码不能为空");
      return false;
    }else if (autocode=="" || autocode==null ){
      alert("验证码不能为空");
      return false;
    }else{
      document.loginForm.submit();
      return true;
    }
  }
      
    function reSetVl(){
      $("#name").val("");
      $("#pass").val("");   
    }
     
      //按回车键
    document.onkeydown=function(event){ 
        var e = event ? event : (window.event ? window.event : null); 
        if(e.keyCode==13){ 
            checkUserForm();
        } 
    }
    
    function reloadImage(){
          var getimagecode=document.getElementById("codeimg");
          getimagecode.src= "<%=request.getContextPath()%>/background/code.html?id=" + Math.random();
    }
     
</script>
</head>

<body>
    <div id="login_top">
        <div id="welcome">
                                  欢迎使用青云(Bruce)后台管理系统       
        </div>
        <div id="back">
            <a href="#">返回首页</a>&nbsp;&nbsp; | &nbsp;&nbsp;
            <a href="#">帮助</a>
        </div>
    </div>
    <div id="login_center">
        <div id="login_area">
            <div id="login_form">
                <form action="<%=request.getContextPath()%>/background/loginCheck.html" method="post">
                    <div id="login_tip">
                                                                        后台用户登录     <span style="color: red; text-align: center">${error}</span>
                    </div>
                    <div><input type="text" class="username"  name="username" id="name"></div>
                    <div><input type="password" class="pwd"  name="password" id="pass"></div>

                    <div id="btn_area">
                        <input type="submit" name="submit" id="sub_btn" value="登&nbsp;&nbsp;录">&nbsp;&nbsp;
                        <input type="text" class="verify" name="autocode" id="autocode" >
                        <img src="<%=request.getContextPath()%>/background/code.html" id="codeimg" alt="验证码" width="80" height="40">
					    <a href="javascript:reloadImage()">看不清</a><br/>
                    </div>

                </form>
            </div>
        </div>
    </div>
    <div id="login_bottom">
                                                                北京青云科技技术股份有限公司 @ 版权所有---Bruce Made
    </div>
</body>

</html>
