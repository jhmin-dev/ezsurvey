package io.ezsurvey.web.survey;

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

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.service.survey.BookmarkSurveyReadService;
import io.ezsurvey.service.survey.SurveyReadService;
import io.ezsurvey.web.PagingUtil;
import io.ezsurvey.web.SurveyAuthUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SurveyReadController {
	private final BookmarkSurveyReadService bookmarkSurveyService;
	private final SurveyReadService surveyReadService;
	private final List<EnumDTO> searchField;
	
	// 생성자 방식 의존성 주입
	public SurveyReadController(BookmarkSurveyReadService bookmarkSurveyService
			, SurveyReadService surveyReadService, @Qualifier("searchFieldSurvey") List<EnumDTO> searchField) {
		this.bookmarkSurveyService = bookmarkSurveyService;
		this.surveyReadService = surveyReadService;
		this.searchField = searchField;
	}
	
	/* 설문조사 상세 */
	// 전체 공개 경로이므로 sessionUser의 null 검사 필수
	@RequestMapping("/project/{surveyId}")
	public String detail(@PathVariable(name = "surveyId") Long surveyId, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 설문조사 접근 권한 검사
		SurveyAuthUtil.hasDetailAuthOrThrowException(surveyReadService.getAuthDTOById(surveyId), sessionUser);
		
		// 설문조사 정보 가져오기
		SurveyResponseDTO responseDTO = new SurveyResponseDTO(surveyReadService.getServiceDTOById(surveyId));
		
		// 로그인되어 있는 경우 현재 설문조사를 즐겨찾기했는지 확인
		if(sessionUser!=null) {
			responseDTO.setHasBookmarked(bookmarkSurveyService.existsBookmark(surveyId, sessionUser.getUserId()));
		}
		model.addAttribute("responseDTO", responseDTO);
		
		return "/project/detail";
	}
	
	/* 미리보기 */
	// 전체 공개 경로이므로 sessionUser의 null 검사 필수
	@RequestMapping(value = {"/project/{surveyId}/preview", "/project/{surveyId}/preview/{shared}"})
	public String preview(@PathVariable(name = "surveyId") Long surveyId
			, @PathVariable(name = "shared", required = false) String shared, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 설문조사 접근 권한 검사
		SurveyAuthUtil.hasPreviewAuthOrThrowException(surveyReadService.getAuthDTOById(surveyId), shared, sessionUser);
		
		// 설문조사 정보 가져오기
		SurveyResponseDTO responseDTO = new SurveyResponseDTO(surveyReadService.getIndexDTOById(surveyId));
		
		model.addAttribute("responseDTO", responseDTO);
		
		return "/question/preview";
	}
	
	/* 둘러보기 */
	@RequestMapping("/project")
	public String list(@PageableDefault(page = 0, sort = "id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model) {
		// 전체 공개인 설문조사 목록 가져오기
		Page<SurveyResponseDTO> page = Page.empty();
		page = surveyReadService.findSurveyByVisibility(EnumBase.findByKey(SearchField.class, field), word, pageable)
				.map(SurveyResponseDTO::new);

		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "둘러보기");
		model.addAttribute("link", "list");
		
		return "/list"; // Tiles 설정명 반환
	}

	/* 내 설문조사 */
	// Spring Security에서 인증을 요구하므로 sessionUser의 null 검사 불필요
	@RequestMapping("/my/project")
	public String my(@PageableDefault(page = 0, sort = "id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 로그인한 사용자가 생성한 설문조사 목록 가져오기
		Page<SurveyResponseDTO> page = Page.empty();
		page = surveyReadService.findSurveyByUser(sessionUser.getUserId()
				, EnumBase.findByKey(SearchField.class, field), word, pageable)
				.map(SurveyResponseDTO::new);
		
		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "내 설문조사");
		model.addAttribute("link", "my");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	/* 즐겨찾기: 설문조사 */
	// Spring Security에서 인증을 요구하므로 sessionUser의 null 검사 불필요
	// BookmarkSurvey에서 검색하기 때문에 sort 기준 프로퍼티명은 survey. 또는 user.으로 접근해야 함
	@RequestMapping("/bookmark/project")
	public String bookmark(@PageableDefault(page = 0, sort = "survey.id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 로그인한 사용자가 즐겨찾기한 설문조사 중 전체 공개인 설문조사 목록 가져오기
		Page<SurveyResponseDTO> page = Page.empty();
		page = bookmarkSurveyService.getByVisibilityAndUser(sessionUser.getUserId()
				, EnumBase.findByKey(SearchField.class, field), word, pageable)
				.map(SurveyResponseDTO::new);
		
		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "즐겨찾기: 설문조사");
		model.addAttribute("link", "bookmark");
		
		return "/list"; // Tiles 설정명 반환
	}

	private void setSurveyAttributes(Model model, Page<SurveyResponseDTO> page) {
		model.addAttribute("page", page);
		PagingUtil.setPageAttributes(model, page, 5);
		model.addAttribute("searchField", searchField);
		model.addAttribute("type", "survey");
	}
}
