<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <title>左边显示</title>
      <LINK href="<%=request.getContextPath()%>/css/admin.css" type="text/css" rel="stylesheet">
      <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css" type="text/css" >
      <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" type="text/css" >
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.SuperSlide.js"></script>
        
<script language=javascript>
    function expand(el)
    {
        var childObj = document.getElementById("child" + el);
        if (childObj.style.display == 'none'){
            childObj.style.display = 'block';
        }else{
            childObj.style.display = 'none';
        }
        
        //不管用.
        var fatherObj = document.getElementById("father" + el);
        if (fatherObj.style.background == '<%=request.getContextPath()%>/images/side_h3_on.gif'){
            fatherObj.style.background = '<%=request.getContextPath()%>/images/side_h3.gif';
        }else{
            fatherObj.style.background = '<%=request.getContextPath()%>/images/side_h3_on.gif';
        }
        return;
    }
    
    function showMenu(){
        var childObj = document.getElementById("showMenu");
        if (childObj.style.display == 'none'){
            childObj.style.display = 'block';
        }else{
            childObj.style.display = 'none';
        }
    }
    
</script>

<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}

on{
      background: url('<%=request.getContextPath()%>/images/side_li_on.png') 0px 0px no-repeat;
      color: #fff;
}

.sideMenu{
    height: 38px;
    cursor: pointer;
    font: bold 14px/38px "Microsoft YaHei";
    background: transparent url("../images/side_h3.gif") no-repeat scroll 0px 0px;
    padding-left: 45px;
}

.sideMenu choose{
    height: 38px;
    cursor: pointer;
    font: bold 14px/38px "Microsoft YaHei";
    background: transparent url("../images/side_h3_on.gif") no-repeat scroll 0px 0px;
    padding-left: 45px;
}

</style>
    </head>
    <body>
        <table width="171" height="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td valign="top">
                    <table width="100%" height="100%" border="0" cellpadding="0"
                        cellspacing="0" style="table-layout: fixed;">
                        <tr>
                            <td style="width: 3px; background: #0a5c8e;">
                                &nbsp;
                            </td>
                            <td>
                                <table width="100%" height="100%" border="0" cellpadding="0"
                                    cellspacing="0" style="table-layout: fixed;">
                                    <tr>
                                        <td height="5" style="line-height: 5px; background: #0a5c8e;">
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="45" width="100%" bgcolor ="#176BAF">
                                            <a onclick="showMenu()">
                                                <img alt="功能菜单" src="<%=request.getContextPath()%>/images/side_top.gif">
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td bgcolor="#e5f4fd" id="showMenu" style="display:block;" >
                                            <div style="width:170px;height:800px; overflow-y:scroll; border:1px solid;">
                                            <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td valign="top">
                                                        <div align="center">
                                                            <TABLE height="100%" cellSpacing=0 cellPadding=0 width=170 background="" border=0>
                                                                <TR>
                                                                    <TD vAlign=top align=middle>
                                                                        <c:forEach items="${resources}" var="key" varStatus="stut">
                                                                        
                                                                            <c:if test="${key.sourceType eq 0}">
                                                                            
                                                                                <TABLE id="father${stut.index}" cellSpacing=0 cellPadding=0 width="100%" border=0 bgcolor="#176BAI" >
                                                                                    <TR height=40 >
                                                                                        <TD style="padding-left:40px;width:170px;height:38px; align:center;" >
                                                                                            <A class='menuParent' onclick='expand("${stut.index}")'
                                                                                                href="javascript:void(0);" ><font color="white" size="3">${key.sourceName}</font>
                                                                                            </A>
                                                                                        </TD>
                                                                                    </TR>
                                                                                </TABLE>
                                                                            
                                                                                <TABLE id='child${stut.index}' style="display: none;" cellSpacing=0 cellPadding=0 width="100%" border=0 >
                                                                                    <c:forEach items="${resources}" var="k">
                                                                                        <c:if test="${k.parentId eq key.sourceId}">
                                                                                            <TR style="padding-left:40px;width:100%;height:38px; align:center;text-align:center" >
                                                                                                <TD  background = "<%=request.getContextPath()%>/images/side_li_on.png" style="width:170px;height:38px;text-align:center">
                                                                                                       
                                                                                                        <c:if test="${k.isIframe == 1}">
                                                                                                            <A href="${k.sourceUrl}" target="main">
                                                                                                                <font color="white" size="3">${k.sourceName}</font>
                                                                                                            </A>
                                                                                                        </c:if>
                                                                                                        <c:if test="${k.isIframe == 0}">
                                                                                                            <A href="<%=request.getContextPath()%>${k.sourceUrl}" target="main">
                                                                                                                    <font color="white" size="3">${k.sourceName}</font>
                                                                                                               </A>
                                                                                                        </c:if>
                                                                                                </TD>
                                                                                            </TR>
                                                                                        </c:if>
                                                                                    </c:forEach>
                                                                                    <hr>
                                                                                </TABLE>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </TD>
                                                                </TR>
                                                            </TABLE>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
