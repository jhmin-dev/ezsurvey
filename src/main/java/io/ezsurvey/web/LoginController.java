package io.ezsurvey.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {	
	@RequestMapping("/my/project")
	public String test() {
		return "/my/project";
	}
	
	// 작동하지 않음
	@RequestMapping("/login/error")
	public String error() {
		return "/login/error";
	}
}
