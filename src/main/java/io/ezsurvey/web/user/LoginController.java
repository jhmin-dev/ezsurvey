package io.ezsurvey.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.repository.EnumMapper;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private EnumMapper enumMapper; // AppConfig에 등록
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("provider", enumMapper.get("Provider"));
		
		return "/login"; // Tiles 설정명 반환
	}
}
