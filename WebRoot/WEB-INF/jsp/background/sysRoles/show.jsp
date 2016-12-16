<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/taglib.jsp"%>
<%@include file="../../common/common-css.jsp"%>
<%@include file="../../common/common-js.jsp"%>
</head>
   
<body>

<div style="height: 100%;overflow-y: auto;">
<div id="forms" class="mt10">
   <div class="box">
     <div class="box_border">
       <div class="box_top" ><b class="pl15" > 角色管理——>查看角色</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="td_right">
                                                            角色名 : 
                    </td>
                    <td class="td_left">
                        ${role.roleName}
                     </td>
                   </tr>
                   
                   <tr>
                    <td class="td_right">
                                                            角色roleKey : 
                    </td>
                    <td class="td_left">
                      ${role.roleKey}
                     </td>
                 </tr>
                 <tr>
                    <td class="td_right">
                                                        是否禁用 : 
                    </td>
                    <td class="td_left">
                        <input name="roleStatus" type="radio" <c:if test="${role.roleStatus eq '0'}">checked="checked"</c:if> value="0"/>禁用　　
						<input name="roleStatus" type="radio" <c:if test="${role.roleStatus eq '1'}">checked="checked"</c:if> value="1"/>正常
                    </td>
                 </tr>
                 <tr>                        
                    <td class="td_right">
                                                        描述 : 
                    </td>
                    <td class="td_left">
                         ${role.description}
                    </td>
                </tr>
               
				<tr>
					<td class="td_right">
                       
                    </td>
                    <td class="td_left">
                      <input type="button"  class="btn btn82 btn_nochecked" value="返回" onclick="location.href='javascript:history.go(-1)'">
                    </td>
				</tr>
			</table>
		  </div>
          </div>
        </div>
     </div>	
</div>
	
  </body>
</html>
