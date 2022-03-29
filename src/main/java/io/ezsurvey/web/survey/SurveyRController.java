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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.service.survey.BookmarkSurveyRService;
import io.ezsurvey.service.survey.SurveyRService;
import io.ezsurvey.web.PagingUtil;

@Controller
public class SurveyRController {
	private static final Logger logger = LoggerFactory.getLogger(SurveyRController.class);
	
	@Autowired
	private BookmarkSurveyRService bookmarkSurveyService;
	
	@Autowired
	private SurveyRService surveyService;
	
	@Autowired @Qualifier("searchFieldSurvey")
	private List<EnumDTO> searchField;
	
	@RequestMapping("/project/{survey}")
	public String detail(@PathVariable(name = "survey") Long survey, Model model, HttpSession session) {
		SurveyResponseDTO responseDTO = surveyService.getResponseDTOById(survey);
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		boolean hasAccess = responseDTO.getVisibility().equals(Visibility.PUBLIC.getKey()); // 설문조사가 전체 공개인 경우		
		if(sessionUser!=null) { // 사용자가 로그인되어 있는 경우
			hasAccess |= (!responseDTO.getVisibility().equals(Visibility.DELETED.getKey()) // 설문조사가 삭제되지 않았고 
					&& responseDTO.getUserId()==sessionUser.getMember()); // 로그인한 사용자가 설문조사 생성자인 경우
		}
		
		if(responseDTO==null || !hasAccess) { // 잘못된 설문조사 번호로 접속했거나 권한이 없는 경우
			return "redirect:/";
		}
		
		if(sessionUser!=null) { // 로그인한 사용자가 현재 설문조사를 즐겨찾기했는지 확인
			responseDTO.setHasBookmarked(bookmarkSurveyService.existsBookmark(survey, sessionUser.getMember()));
		}
		model.addAttribute("responseDTO", responseDTO);
		
		return "/project/detail";
	}
	
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
		page = bookmarkSurveyService.getByVisibilityAndUser(sessionUser.getMember()
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
