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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.ezsurvey.dto.survey.SurveyRequestDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.entity.survey.Visibility;
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
		
		surveyService.insert(requestDTO.toServiceDTO(), sessionUser.getMember());
		
		return "redirect:/";
	}
	
	@GetMapping("/edit/project/{survey}")
	public String edit(@PathVariable(name = "survey") Long survey, Model model, HttpSession session) {
		// 요청 URL로 첫 접속시 URL에 포함된 PK 값으로 설문조사 정보를 조회
		if(!model.containsAttribute("hasErrors")) { // 폼에 오류가 있어 메서드가 재호출된 경우에는 쿼리를 전송하지 않음; requestDTO는 initCommand()에 의해 빈 객체가 기본적으로 저장되기 때문에 containsAttribute() 결과가 항상 true임
			SurveyRequestDTO requestDTO = surveyService.getRequestDTOById(survey);
			SessionUser sessionUser = (SessionUser)session.getAttribute("user");
			
			boolean hasAccess = !requestDTO.getVisibility().equals(Visibility.DELETED.getKey()) // 설문조사가 삭제되지 않았고
					&& requestDTO.getUser().getId()==sessionUser.getMember(); // 로그인한 사용자가 설문조사 생성자인 경우
			
			if(requestDTO==null || !hasAccess) { // 잘못된 설문조사 번호로 접속했거나 권한이 없는 경우
				return "redirect:/";
			}
			
			model.addAttribute("requestDTO", requestDTO);
		}
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "설문조사 수정");
		model.addAttribute("link", "edit");
		
		return "/project/make_edit"; // Tiles 설정명 반환
	}
	
	@PostMapping("/edit/project/{survey}")
	public String edit(@PathVariable(name = "survey") Long survey
			, @Valid @ModelAttribute("requestDTO") SurveyRequestDTO requestDTO
			, BindingResult result, Model model, HttpSession session) {
		logger.info(requestDTO.toString());

		if(result.hasErrors()) { // 폼에 오류가 있으면 다시 폼 호출
			model.addAttribute("hasErrors", true); // edit() 메서드가 재호출되었는지 식별하기 위해 Model에 값 저장
			
			return edit(survey, model, session);
		}
		
		surveyService.update(requestDTO.toServiceDTO());
		
		return "redirect:/project/" + survey; // 설문조사 상세로 리다이렉트
	}
}
