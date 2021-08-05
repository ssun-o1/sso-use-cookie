package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 页面跳转逻辑
 *
 * @author sunshaocong
 */
@Controller
@RequestMapping("/view")
public class ViewController {

	/**
	 * 跳转到登录界面
	 *
	 * @return
	 */
	@GetMapping("/login")
	public String toLogin(@RequestParam(required = false, defaultValue = "") String target,
			HttpSession session, @CookieValue(required = false, value = "TOKEN") Cookie cookie) {
		if (StringUtils.isEmpty(target)) {
			target = "http://www.codeshop.com:9010";
		}
		if (cookie != null) {
			String value = cookie.getValue();
			User user = LoginCacheUtil.loginUser.get(value);
			if (user != null) {
				return "redirect:" + target;
			}
		}
		// TODO：还需要做重定向地址校验
		// 重定向地址
		session.setAttribute("target", target);
		return "login";
	}
}
