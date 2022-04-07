package io.ezsurvey.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserCUDController {
	@RequestMapping("/my/profile")
	public String my() {
		return "/profile/my";
	}
	
	@RequestMapping("/settings/profile")
	public String settings() {
		return "/profile/settings";
	}
	
	@RequestMapping("/delete/profile")
	public String delete() {
		return "/profile/delete";
	}
}
