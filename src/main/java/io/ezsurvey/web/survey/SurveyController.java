package io.ezsurvey.web.survey;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.dto.survey.SurveyRequestDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.service.survey.SurveyService;
import io.ezsurvey.web.PagingUtil;

@Controller
public class SurveyController {
	private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired @Qualifier("searchFieldSurvey")
	private List<EnumDTO> searchFieldSurvey;
	
	@ModelAttribute("requestDTO")
	public SurveyRequestDTO initCommand() {
		return new SurveyRequestDTO();
	}
	
	@GetMapping("/make/project")
	public String make() {
		return "/project/make"; // Tiles 설정명 반환
	}
	
	@PostMapping("/make/project")
	public String make(@Valid @ModelAttribute("requestDTO") SurveyRequestDTO requestDTO, BindingResult result
			, HttpSession session) { // BindingResult가 마지막 인자인 경우 White Label로 이동
		logger.info(requestDTO.toString());

		if(result.hasErrors()) { // 폼에 오류가 있으면 다시 폼 호출
			return make();
		}
		
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		surveyService.insertSurvey(requestDTO.toServiceDTO(), sessionUser.getMember());
		
		return "redirect:/";
	}

	@RequestMapping("/project")
	public String list(@PageableDefault(page = 0, sort = "survey", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model) {
		Page<SurveyServiceDTO> page = Page.empty();
		
		if(word==null || word.isBlank()) { // 검색어를 입력하지 않은 경우
			page = surveyService.getSurveyByVisibility(null, null, pageable);
		}
		else { // 검색어를 입력한 경우
			page = surveyService.getSurveyByVisibility(EnumBase.findByKey(SearchField.class, field)
					, word, pageable);
		}
		
		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "둘러보기");
		model.addAttribute("link", "list");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	@RequestMapping("/my/project")
	public String my(@PageableDefault(page = 0, sort = "survey", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		Page<SurveyServiceDTO> page = Page.empty();
		
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		if(word==null || word.isBlank()) { // 검색어를 입력하지 않은 경우
			page = surveyService.getSurveyByUser(sessionUser.getMember(), null, null, pageable);
		}
		else { // 검색어를 입력한 경우
			page = surveyService.getSurveyByUser(sessionUser.getMember()
					, EnumBase.findByKey(SearchField.class, field), word, pageable);
		}
		
		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "내 설문조사");
		model.addAttribute("link", "my");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	@RequestMapping("/bookmark/project")
	public String bookmark(@PageableDefault(page = 0, sort = "survey", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		Page<SurveyServiceDTO> page = Page.empty();
		
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		if(word==null || word.isBlank()) { // 검색어를 입력하지 않은 경우
			page = surveyService.getBookmarkSurveyByUser(sessionUser.getMember(), null, null, pageable);
		}
		else { // 검색어를 입력한 경우
			page = surveyService.getBookmarkSurveyByUser(sessionUser.getMember()
					, EnumBase.findByKey(SearchField.class, field), word, pageable);
		}
		
		// 설문조사 목록 관련 정보 저장
		setSurveyAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "즐겨찾기: 설문조사");
		model.addAttribute("link", "bookmark");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	public void setSurveyAttributes(Model model, Page<SurveyServiceDTO> page) {
		model.addAttribute("page", page.map(SurveyResponseDTO::new));
		PagingUtil.setPageAttributes(model, page, 5);
		model.addAttribute("searchField", searchFieldSurvey);
		model.addAttribute("type", "survey");
	}
}
