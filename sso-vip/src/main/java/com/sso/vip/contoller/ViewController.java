package com.sso.vip.contoller;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * @author sunshaocong
 */
@Controller
@RequestMapping("/view")
public class ViewController {
	private final String LOGIN_INFO_ADDRESS = "http://login.codeshop.com:9000/login/info?token=";
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/index")
	public String toIndex(@CookieValue(required = false, value = "TOKEN") Cookie cookie,
			HttpSession session) {
		if (cookie != null) {
			String token = cookie.getValue();
			if (!StringUtils.isEmpty(token)) {
				Map result = restTemplate.getForObject(LOGIN_INFO_ADDRESS + token,
						Map.class);
				session.setAttribute("loginUser",result);
			}
		}
		return "index";
	}
}
