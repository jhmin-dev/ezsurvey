package io.ezsurvey.web.survey;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.service.survey.SurveyCUDService;

@Controller
public class SurveyAjaxController {
	private static final Logger logger = LoggerFactory.getLogger(SurveyAjaxController.class);
	
	@Autowired
	SurveyCUDService surveyService;
	
	@ResponseBody
	@PostMapping("/ajax/delete/project")
	public Map<String, String> delete(Long survey, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		if(sessionUser==null) { // 로그인되어 있지 않은 경우
			map.put("result", "logout");
		}
		else if(sessionUser.getMember()!=surveyService.getRequestDTOById(survey).getUser().getId()) { // 로그인한 사용자와 설문조사 생성자가 불일치하는 경우
			map.put("result", "wrongAccess");
		}
		else {
			surveyService.delete(survey);
			map.put("result", "success");
		}
		
		return map;
	}
}
