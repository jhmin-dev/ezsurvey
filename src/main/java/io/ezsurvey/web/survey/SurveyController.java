package io.ezsurvey.web.survey;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import io.ezsurvey.dto.survey.SurveyWebDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.repository.user.UserRepository;
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
		
		// 필수 필드에 값 저장
		surveyWebDTO.setStatus("before"); // 설문조사 생성 시에는 항상 status가 배포 전으로 설정되어야 함
		if(surveyWebDTO.getVisibility()==null || surveyWebDTO.getVisibility().isBlank()) { // 사용자가 공개 여부를 별도로 설정하지 않은 경우
			surveyWebDTO.setVisibility("hidden"); // visibility를 비공개로 설정
		}
		
		surveyService.insertSurvey(surveyWebDTO.toServiceDTO());
		
		return "redirect:/";
	}

	@RequestMapping("/project")
	public String list(@PageableDefault(page = 0, sort = "survey", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model) {
		Page<SurveyServiceDTO> page = Page.empty();
		
		if(word==null || word.isBlank()) { // 검색어를 입력하지 않은 경우
			page = surveyService.find(pageable, null, null);
		}
		else { // 검색어를 입력한 경우
			page = surveyService.find(pageable, EnumBase.findByKey(SearchField.class, field), word);
		}
		
		model.addAttribute("page", page.map(SurveyWebDTO::new));
		model.addAttribute("count", page.getTotalElements());
		model.addAttribute("title", "둘러보기");
		model.addAttribute("type", "survey");
		model.addAttribute("link", "list");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	@RequestMapping("/my/project")
	public String my(@PageableDefault(page = 0, sort = "survey", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model) {
		Page<SurveyServiceDTO> page = Page.empty();
		
		model.addAttribute("page", page.map(SurveyWebDTO::new));
		model.addAttribute("count", page.getTotalElements());
		model.addAttribute("title", "내 설문조사");
		model.addAttribute("type", "survey");
		model.addAttribute("link", "my");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	@RequestMapping("/bookmark/project")
	public String bookmark(@PageableDefault(page = 0, sort = "survey", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model) {
		Page<SurveyServiceDTO> page = Page.empty();
		
		model.addAttribute("page", page.map(SurveyWebDTO::new));
		model.addAttribute("count", page.getTotalElements());
		model.addAttribute("title", "즐겨찾기: 설문조사");
		model.addAttribute("type", "survey");
		model.addAttribute("link", "bookmark");
		
		return "/list"; // Tiles 설정명 반환
	}
}
