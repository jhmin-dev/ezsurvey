package io.ezsurvey.web.survey;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.ezsurvey.dto.survey.SurveyRequestDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.service.survey.SurveyCUDService;

@Controller
public class SurveyCUDController {
	private static final Logger logger = LoggerFactory.getLogger(SurveyCUDController.class);
	
	@Autowired
	SurveyCUDService surveyService;
	
	@ModelAttribute("requestDTO")
	public SurveyRequestDTO initCommand() {
		return new SurveyRequestDTO();
	}
	
	@GetMapping("/make/project")
	public String make(Model model) {
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "설문조사 생성");
		model.addAttribute("link", "make");
		
		return "/project/make_edit"; // Tiles 설정명 반환
	}
	
	@PostMapping("/make/project")
	public String make(@Valid @ModelAttribute("requestDTO") SurveyRequestDTO requestDTO, BindingResult result
			, Model model, HttpSession session) { // BindingResult가 마지막 인자인 경우 White Label로 이동
		logger.info(requestDTO.toString());

		if(result.hasErrors()) { // 폼에 오류가 있으면 다시 폼 호출
			return make(model);
		}
		
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		surveyService.insertSurvey(requestDTO.toServiceDTO(), sessionUser.getMember());
		
		return "redirect:/";
	}
}
