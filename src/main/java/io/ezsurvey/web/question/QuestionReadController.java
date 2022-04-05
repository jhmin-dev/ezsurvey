package io.ezsurvey.web.question;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.repository.EnumMapper;
import io.ezsurvey.service.survey.SurveyReadService;
import io.ezsurvey.web.PagingUtil;
import io.ezsurvey.web.SurveyAuthUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class QuestionReadController {
	private final SurveyReadService surveyReadService;
	private final EnumMapper enumMapper; // AppConfig에 등록
	private final List<EnumDTO> searchField;
	
	// 생성자 방식 의존성 주입
	public QuestionReadController(SurveyReadService surveyReadService, EnumMapper enumMapper
			, @Qualifier("searchFieldQuestion") List<EnumDTO> searchField) {
		this.surveyReadService = surveyReadService;
		this.enumMapper = enumMapper;
		this.searchField = searchField;
	}
	
	@RequestMapping("/edit/project/{surveyId}/index")
	public String index(@PathVariable(name = "surveyId") Long surveyId, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 설문조사 접근 권한 검사
		SurveyAuthUtil.hasEditAuthOrThrowException(surveyReadService.getAuthDTOById(surveyId), sessionUser.getUserId());
		
		// 설문조사 정보 가져오기
		SurveyResponseDTO responseDTO = new SurveyResponseDTO(surveyReadService.getIndexDTOById(surveyId));
		
		model.addAttribute("survey", responseDTO);
		model.addAttribute("title", "문항 관리");
		
		return "/question/index"; // Tiles 설정명 반환		
	}
	
	// BookmarkQuestion에서 검색하기 때문에 sort 기준 프로퍼티명은 question. 또는 user.으로 접근해야 함
	@RequestMapping("/bookmark/question")
	public String bookmark(@PageableDefault(page = 0, sort = "question.id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 로그인한 사용자가 즐겨찾기한 문항 중 전체 공개인 문항 목록 가져오기
		Page<Object> page = Page.empty();
		
		
		// 문항 목록 관련 정보 저장
		setQuestionAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "즐겨찾기: 문항");
		model.addAttribute("link", "bookmark");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	private void setQuestionAttributes(Model model, Page<Object> page) {
		model.addAttribute("page", page);
		PagingUtil.setPageAttributes(model, page, 5);
		model.addAttribute("searchField", searchField);
		model.addAttribute("category", enumMapper.get("Category"));
		model.addAttribute("type", "question");
	}
}
