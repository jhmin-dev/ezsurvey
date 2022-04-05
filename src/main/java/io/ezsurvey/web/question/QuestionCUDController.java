package io.ezsurvey.web.question;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.repository.EnumMapper;
import io.ezsurvey.service.survey.SurveyReadService;
import io.ezsurvey.web.SurveyAuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class QuestionCUDController {
	private final EnumMapper enumMapper; // AppConfig에 등록
	private final SurveyReadService surveyReadService;
	
	@GetMapping("edit/project/{surveyId}/make/question")
	public String make(@PathVariable(name = "surveyId") Long surveyId, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 설문조사 접근 권한 검사
		SurveyAuthUtil.hasEditAuthOrThrowException(surveyReadService.getAuthDTOById(surveyId), sessionUser.getUserId());

		// 설문조사 정보 가져오기
		SurveyResponseDTO responseDTO = new SurveyResponseDTO(surveyReadService.getIndexDTOById(surveyId));
		model.addAttribute("survey", responseDTO);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "문항 추가");
		model.addAttribute("link", "make");
		model.addAttribute("category", enumMapper.get("Category"));
		
		return "/question/make_edit"; // Tiles 설정명 반환
	}
}
