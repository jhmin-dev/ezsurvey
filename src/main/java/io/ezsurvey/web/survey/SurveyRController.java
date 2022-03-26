package io.ezsurvey.web.survey;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.service.survey.SurveyRService;
import io.ezsurvey.web.PagingUtil;

@Controller
public class SurveyRController {
	private static final Logger logger = LoggerFactory.getLogger(SurveyRController.class);
	
	@Autowired
	private SurveyRService surveyService;
	
	@Autowired @Qualifier("searchFieldSurvey")
	private List<EnumDTO> searchField;
	
	@RequestMapping("/project")
	public String list(@PageableDefault(page = 0, sort = "id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model) {
		// 전체 공개인 설문조사 목록 가져오기
		Page<SurveyResponseDTO> page = Page.empty();
		page = surveyService.getSurveyByVisibility(EnumBase.findByKey(SearchField.class, field)
					, word, pageable);

		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "둘러보기");
		model.addAttribute("link", "list");
		
		return "/list"; // Tiles 설정명 반환
	}

	@RequestMapping("/my/project")
	public String my(@PageableDefault(page = 0, sort = "id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 로그인한 사용자가 생성한 설문조사 목록 가져오기
		Page<SurveyResponseDTO> page = Page.empty();
		page = surveyService.getSurveyByUser(sessionUser.getMember()
				, EnumBase.findByKey(SearchField.class, field), word, pageable);
		
		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "내 설문조사");
		model.addAttribute("link", "my");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	// BookmarkSurvey에서 검색하기 때문에 sort 기준 프로퍼티명은 survey. 또는 user.으로 접근해야 함
	@RequestMapping("/bookmark/project")
	public String bookmark(@PageableDefault(page = 0, sort = "survey.id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 로그인한 사용자가 즐겨찾기한 설문조사 중 전체 공개인 설문조사 목록 가져오기
		Page<SurveyResponseDTO> page = Page.empty();
		page = surveyService.getByVisibilityAndUser(sessionUser.getMember()
				, EnumBase.findByKey(SearchField.class, field), word, pageable);
		
		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "즐겨찾기: 설문조사");
		model.addAttribute("link", "bookmark");
		
		return "/list"; // Tiles 설정명 반환
	}

	public void setSurveyAttributes(Model model, Page<SurveyResponseDTO> page) {
		model.addAttribute("page", page);
		PagingUtil.setPageAttributes(model, page, 5);
		model.addAttribute("searchField", searchField);
		model.addAttribute("type", "survey");
	}
}