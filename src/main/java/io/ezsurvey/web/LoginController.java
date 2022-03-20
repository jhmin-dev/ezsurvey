package io.ezsurvey.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public String login() {
		return "/login"; // Tiles 설정명 반환
	}
	
	@RequestMapping("/my/project")
	public String test() {
		return "/project/my";
	}
	
	// 작동하지 않음
	@RequestMapping("/login/error")
	public String error() {
		return "/error/login";
	}
}
