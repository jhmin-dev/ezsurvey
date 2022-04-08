package io.ezsurvey.web.user;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.repository.EnumMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Controller
public class LoginController {
	private final EnumMapper enumMapper; // AppConfig에 등록
	
	@RequestMapping("/login")
	public String login(Model model) {
		// 사용자가 이미 로그인하였는지 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null || authentication instanceof AnonymousAuthenticationToken) { // 로그인되어 있지 않으면
        	model.addAttribute("provider", enumMapper.get("Provider")); // 로그인 화면 구성에 필요한 정보 저장
        	
        	return "/login"; // 로그인 화면으로 이동
        }
        
		return "redirect:/"; // 로그인되어 있으면 메인 화면으로 리다이렉트
	}
}
