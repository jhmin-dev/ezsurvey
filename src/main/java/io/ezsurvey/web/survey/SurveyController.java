package io.ezsurvey.web.survey;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.ezsurvey.dto.survey.SurveyWebDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.repository.UserRepository;
import io.ezsurvey.service.survey.SurveyService;

@Controller
public class SurveyController {
	private static final Logger logger = LoggerFactory.getLogger(SurveyController.class); 
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute
	public SurveyWebDTO initCommand() {
		return new SurveyWebDTO();
	}
	
	@GetMapping("/make/project")
	public String form_make() {
		return "/project/make"; // Tiles 설정명 반환
	}
	
	@PostMapping("/make/project")
	public String submit_make(@Valid SurveyWebDTO surveyWebDTO, BindingResult result, HttpSession session) { // BindingResult가 마지막 인자인 경우 White Label로 이동
		logger.info(surveyWebDTO.toString());
		
		if(result.hasErrors()) { // 오류가 있으면 폼 호출
			return form_make();
		}
		
		// 세션에 저장된 회원 번호를 이용하여 WebDTO에 User 객체 저장
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		surveyWebDTO.setUser(userRepository.getById(sessionUser.getMember()));
		
		surveyService.insertSurvey(surveyWebDTO.toServiceDTO());
		
		return "redirect:/";
	}
}
