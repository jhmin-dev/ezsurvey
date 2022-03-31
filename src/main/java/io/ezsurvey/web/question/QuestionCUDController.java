package io.ezsurvey.web.question;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.exception.InvalidSurveyOwnerException;
import io.ezsurvey.exception.InvalidSurveyStatusException;
import io.ezsurvey.service.survey.SurveyReadService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionCUDController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionCUDController.class);
	
	private final SurveyReadService surveyService;
	
	@GetMapping("edit/project/{survey}/make/question")
	public String make(@PathVariable(name = "survey") Long survey, Model model, HttpSession session) {
		SurveyResponseDTO responseDTO = surveyService.getResponseDTOById(survey);
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 로그인한 사용자와 설문조사 생성자가 불일치하는 경우
		if(responseDTO.getUserId()!=sessionUser.getMember()) {
			throw new InvalidSurveyOwnerException();
		}
		
		// 현재 시각이 배포 시작 시각 이후인 경우
		String distributed = responseDTO.getDistributed();
		if(!StringUtils.equalsIgnoreCase(distributed, "null") && LocalDateTime.now().isAfter(LocalDateTime.parse(distributed))) {
			throw new InvalidSurveyStatusException();
		}
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("survey", responseDTO);
		model.addAttribute("title", "문항 추가");
		model.addAttribute("link", "make");
		
		return "/question/make_edit"; // Tiles 설정명 반환
	}
}
