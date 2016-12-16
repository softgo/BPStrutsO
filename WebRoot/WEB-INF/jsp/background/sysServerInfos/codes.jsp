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
         var tableName = $("#tableName").val(); //对应表名.
         var codeName = $("#codeName").val(); //资源名称.
         var codePrefix = $("#codePrefix").val(); //资源前缀.
         var entityPackage = $("#entityPackage").val(); //存放包名.
         //jude it.
         if(tableName=="" || tableName==null){
            $("#tableNameTag").html("请填入表名称!");
            return false;
         }else{
            $("#tableNameTag").html("");
         }
         if(codeName=="" || codeName==null ){
            $("#codeNameTag").html("请填入资源名称!");
            return false;
         }else{
            $("#codeNameTag").html("");
         }
         if(codePrefix=="" || codePrefix==null){
             $("#codePrefixTag").html("请填入资源前缀!");
            return false;
         }else{
            $("#codePrefixTag").html("");
         }
         if(entityPackage=="" || entityPackage==null){
             $("#entityPackageTag").html("请填入存放包名!");
            return false;
         }else{
            $("#entityPackageTag").html("");
         }
         
         var isAuto = $("input[type='radio'][name='isAuto']").val();
         var dbTag = $("input[type='radio'][name='dbTag']").val();

         //提交请求.
         $.ajax({
            url:'<%=request.getContextPath()%>/background/sysServerInfos/generate.html',
            data:{"tableName":tableName,"codeName":codeName,"codePrefix":codePrefix,"entityPackage":entityPackage,"isAuto":isAuto,"dbTag":dbTag},
            type : "POST",
            success:function(msg){
                var obj = eval(msg);
                if (obj.msg == "Y"){
                    alert(obj.content);
                }else{
                    alert(obj.content);
                }
            }
         });
         //提交.
         document.sourceForm.submit();
         return true;
      }
      
  </script>
  
  <body>

<div style="height: 100%;overflow-y: auto;">
<div id="forms" class="mt10">
   <div class="box">
     <div class="box_border">
       <div class="box_top" ><b class="pl15" > 服务管理——>一键生成</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
        <form action="<%=request.getContextPath()%>/background/sysServerInfos/codes.html" method="post" name="sourceForm" id="sourceForm">
            <table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                   <tr>
                    <td class="td_right">
                                                            业务表名 : 
                    </td>
                    <td class="td_left">
                      <input  class="input-text lh30" size="40"  name="tableName" id="tableName" />
                      <font color="red"><span id="tableNameTag"/></font>
                     </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        资源名称 : 
                    </td>
                    <td class="td_left">
                        <input  class="input-text lh30" size="40"  name="codeName" id="codeName" />
                        <font color="red"><span id="codeNameTag"/></font>
                    </td>
                 </tr>
                 <tr>                        
                    <td class="td_right">
                                                        资源前缀 : 
                    </td>
                    <td class="td_left">
                         <input class="input-text lh30" size="40" name="codePrefix" id="codePrefix"/>
                         <font color="red"><span id="codePrefixTag"/></font>
                    </td>
                </tr>
               
               <tr>                        
                    <td class="td_right">
                                                       存放位置 : 
                    </td>
                    <td class="td_left">
                         <input class="input-text lh30" size="40" name="entityPackage" id="entityPackage"/>
                         <font color="red"><span id="entityPackageTag"/></font>
                    </td>
                </tr>
                
               <tr>                        
                    <td class="td_right">
                                                        主键生成策略 : 
                    </td>
                    <td class="td_left">
                         <input name="isAuto" type="radio" value="AUTO" checked="checked"/>自动生成
                         <input name="isAuto" type="radio" value="MAN" />人工生成
                    </td>
                </tr>
                
                <tr>                        
                    <td class="td_right">
                                                        数据库类型 : 
                    </td>
                    <td class="td_left">
                         <input name="dbTag" type="radio" value="COMMONDB" checked="checked"/>关系型数据库
                         <input name="dbTag" type="radio" value="MONGO" />NoSql MonGo库
                    </td>
                </tr>
                <tr>
                    <td class="td_right">
                       
                    </td>
                    <td class="td_left">
                      <input type="button" name="button" class="btn btn82 btn_save2" value="保存" onclick="return saveData()" > 
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
