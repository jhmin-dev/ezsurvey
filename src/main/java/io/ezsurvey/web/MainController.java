package io.ezsurvey.web;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainController {
	@RequestMapping("/")
	public String main() {
		return "main";
	}
}
