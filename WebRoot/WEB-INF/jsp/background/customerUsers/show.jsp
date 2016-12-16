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
       <div class="box_top" ><b class="pl15" >系统管理——>信息展示</b></div>
       <div class="box_center" style="overflow-y:scroll;align:center; height:75%;">
		<form action="" method="post" name="userForm" id="userForm">
			<input type="hidden" name="user_id" value="${objId}"><!-- 修改时候自动添加 -->
			<table class="form_table pt15 pb15 list_table" width="100%" border="1" cellpadding="0" cellspacing="0">
                 <!-- 展示信息 -->
                			<tr>
					<td class="td_right">
						用户id:
					</td>
					<td class="td_left" >
					${object.userId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户名:
					</td>
					<td class="td_left" >
					${object.userName}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户密码:
					</td>
					<td class="td_left" >
					${object.userPass}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户手机号:
					</td>
					<td class="td_left" >
					${object.userPhone}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户邮箱:
					</td>
					<td class="td_left" >
					${object.userMail}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户QQ:
					</td>
					<td class="td_left" >
					${object.userQq}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户性别;1:男,0:女:
					</td>
					<td class="td_left" >
					${object.userGender}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户地址:
					</td>
					<td class="td_left" >
					${object.userAddress}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户微信号:
					</td>
					<td class="td_left" >
					${object.userWechat}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						微信公众号ID:
					</td>
					<td class="td_left" >
					${object.publicWechatId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						微信公众号key:
					</td>
					<td class="td_left" >
					${object.publicWechatKey}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						微信公众号token:
					</td>
					<td class="td_left" >
					${object.publicWechatToken}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						微信公众号秘钥:
					</td>
					<td class="td_left" >
					${object.publicWechatSkey}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户主页url地址:
					</td>
					<td class="td_left" >
					${object.userUrl}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户头像:
					</td>
					<td class="td_left" >
					${object.userImage}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户所属行业ids:
					</td>
					<td class="td_left" >
					${object.userIndustryId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户所属套餐ids:
					</td>
					<td class="td_left" >
					${object.userPackageId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户所属平台ids:
					</td>
					<td class="td_left" >
					${object.userPlatformId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户应用ids:
					</td>
					<td class="td_left" >
					${object.userAppId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户频道ids:
					</td>
					<td class="td_left" >
					${object.userChannelId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户商铺ids:
					</td>
					<td class="td_left" >
					${object.userShopId}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						用户状态;1:启用,0:弃用:
					</td>
					<td class="td_left" >
					${object.userStatus}
					</td>
				</tr>
				<tr>
					<td class="td_right">
						注册时间:
					</td>
					<td class="td_left" >
					${fn:substring(object.registerTime,0,19)}"
					</td>
				</tr>
				<tr>
					<td class="td_right">
						修改时间:
					</td>
					<td class="td_left" >
					${fn:substring(object.lastUpdateTime,0,19)}"
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
		  </form>
		  </div>
          </div>
        </div>
     </div>
	</div>
</body>
</html>
