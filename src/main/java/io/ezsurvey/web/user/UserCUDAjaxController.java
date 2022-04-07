package io.ezsurvey.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.service.user.UserCUDService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserCUDAjaxController {
	private final UserCUDService userCUDService;
	
	@PostMapping("/ajax/delete/user")
	public Map<String, String> delete(Long userId, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		if(sessionUser==null) { // 로그인하지 않은 경우
			map.put("result", "logout");
		}
		else if(!userId.equals(sessionUser.getUserId())) { // 버튼에 담긴 회원 번호와 로그인한 사용자 회원 번호가 불일치하는 경우
			map.put("result", "wrongAccess");
		}
		else {
			userCUDService.delete(userId);
			map.put("result", "success");
		}
		
		return map;
	}
}
