package com.sso.login.controller;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunshaocong
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	private static final Set<User> dbUser;

	static {
		dbUser = new HashSet<>();
		dbUser.add(new User(0, "zhangsan", "123"));
		dbUser.add(new User(1, "lisi", "123"));
		dbUser.add(new User(2, "wangwu", "123"));
	}


	@PostMapping
	public String toLogin(User user, HttpSession session, HttpServletResponse response) {
		String target = (String) session.getAttribute("target");
		// 模拟从数据库中通过登录的用户名和密码去查找数据库中的用户
		Optional<User> first = dbUser.stream()
				.filter(dbUser -> dbUser.getUsername().equals(user.getUsername()) && dbUser
						.getPassword().equals(user.getPassword())).findFirst();
		if (first.isPresent()) {
			//保存用户
			String token = UUID.randomUUID().toString();
			Cookie cookie = new Cookie("TOKEN", token);
			cookie.setMaxAge(60);
			cookie.setDomain("codeshop.com");
			response.addCookie(cookie);
			LoginCacheUtil.loginUser.put(token, first.get());
		} else {
			session.setAttribute("msg", "用户名或密码错误");
			return "login";
		}
		// 重定向地址
		return "redirect:" + target;
	}


	@GetMapping("info")
	@ResponseBody
	public ResponseEntity<?> getUserInfo(String token) {
		if (!StringUtils.isEmpty(token)) {
			User user = LoginCacheUtil.loginUser.get(token);
			return ResponseEntity.ok(user);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
