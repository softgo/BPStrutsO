package com.javabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javabase.base.BaseController;
import com.javabase.base.Constant;
import com.javabase.base.util.Common;
import com.javabase.base.util.MD5Util;
import com.javabase.base.util.VerifyImageUtil;
import com.javabase.entity.LoginLogs;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysUsers;
import com.javabase.service.LoginLogsService;
import com.javabase.service.SysRolesService;
import com.javabase.service.SysSourcesService;
import com.javabase.service.SysUsersService;

/**
 * 进行管理后台框架界面的类.(到background文件夹下去)
 * 
 * @author admin
 * 
 * @version 1.0v
 */
@Controller
@RequestMapping("/background/")
public class BackgroundController extends BaseController {
	@Autowired
	private SysUsersService sysUsersService;
	@Autowired
	private SysRolesService sysRolesService;
	@Autowired
	private LoginLogsService loginLogsService;
	@Autowired
	private SysSourcesService sysSourcesService;
	@Autowired
	private AuthenticationManager myAuthenticationManager;

	/**
	 * 验证码
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("code")
	public void loginIn(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//VerifyImageUtil.createVerify(request, response,"hzzm");
		VerifyImageUtil.createEasy(request, response, "zm");
	}
	
	/**
	 * @return
	 */
	@RequestMapping("loginOut")
	public String loginOut(Model model, HttpServletRequest request) {
		// 重新登录时销毁该用户的Session
		Object object = request.getSession().getAttribute(
				"SPRING_SECURITY_CONTEXT");
		if (object!=null ) {
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}
		
		SysUsers users = (SysUsers) request.getSession().getAttribute("userSession");
		if (users!=null) {
			request.getSession().removeAttribute("userSession");
			Constant.constMap.remove(users.getSysUserId());
			Constant.userMap.remove(users.getSysUserName());
		}
		SysRoles roles = (SysRoles) request.getSession().getAttribute("roleSession");
		if (roles!=null) {
			request.getSession().removeAttribute("roleSession");
		}
		// session 失效.
		request.getSession().invalidate();
		
		return "redirect:index.html";
	}

	/**
	 * @return
	 */
	@RequestMapping("login")
	public String login(Model model, HttpServletRequest request) {
		// 重新登录时销毁该用户的Session
		Object object = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if ( object != null) {
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}else {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; cookies != null && i < cookies.length; i++) {
				if (cookies[i].getName().equals("loginName")) {
					request.setAttribute("error", "登录超时,请按F5手动刷新,再次登录!");
				}
			}
		}
		return "/background/framework/login";
	}

	@RequestMapping("loginCheck")
	public String loginCheck(String username, String password,String autocode,HttpServletRequest request) {
		try {
			if (!request.getMethod().equals("POST")) {
				request.setAttribute("error", "支持POST方法提交!");
			}
			if (Common.isEmpty(username) || Common.isEmpty(password)) {
				request.setAttribute("error", "用户名或密码不能为空!");
				return "/background/framework/login";
			}
			String sessionCode = (String) request.getSession().getAttribute("AutoCode"); 
			if (!sessionCode.equalsIgnoreCase(autocode)) {
				request.setAttribute("error", "验证码输入有误!");
				return "/background/framework/login";
			}
			
			// 验证用户账号与密码是否正确
			SysUsers user = sysUsersService.findByName(username);
			password = MD5Util.encrypt(password);
			if (user == null || !user.getSysUserPass().equals(password)) {
				request.setAttribute("error", "用户名或密码不正确!");
				return "/background/framework/login";
			}

			Authentication authentication = myAuthenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							username, password));

			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			securityContext.setAuthentication(authentication);
			HttpSession session = request.getSession(false);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

			// 当验证都通过后，把用户信息放在session里
			request.getSession().setAttribute("userSession", user);
			//加到cookie中去.
			Cookie userCookie = new Cookie("loginName", username);
			userCookie.setMaxAge(3610);//一个小时.
			response.addCookie(userCookie);
			//常量设值.
			Constant.constMap.put(user.getSysUserId(), user);
			Constant.userMap.put(user.getSysUserName(), user);
			SysRoles role = this.sysRolesService.findByName(username);
			if (role != null) {
				request.getSession().setAttribute("roleSession", role);
			}

			// 记录登录信息
			LoginLogs logs = new LoginLogs();
			logs.setSysUserId(user.getSysUserId());
			logs.setSysUserName(user.getSysUserName());
			logs.setLoginIp(Common.toIpAddr(request));
			loginLogsService.addOne(logs);
		}
		catch (AuthenticationException ae) {
			request.setAttribute("error", "登录异常，请联系管理员！");
			return "/background/framework/login";
		}

		return "redirect:index.html";
	}

	/**
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		return "/background/framework/main";
	}

	@RequestMapping("top")
	public String top(Model model) {
		return "/background/framework/top";
	}

	@RequestMapping("left")
	public String left(Model model, HttpServletRequest request) {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = request.getUserPrincipal().getName();
			List<SysSources> resources = sysSourcesService.getSysSourcesByUserName(username);
			model.addAttribute("resources", resources);
		}
		catch (Exception e) {
			// 重新登录时销毁该用户的Session
			e.printStackTrace();
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}
		return "/background/framework/left";
	}

	@RequestMapping("tab")
	public String tab(Model model) {
		return "/background/framework/tab/tab";
	}

	@RequestMapping("center")
	public String center(Model model) {
		return "/background/framework/center";
	}

}
