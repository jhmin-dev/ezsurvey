package io.ezsurvey.web;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.service.survey.SurveyReadService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Controller
public class MainController {
	private final SurveyReadService surveyReadService;
	
	@RequestMapping("/")
	public String main(Model model) {
		Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
		
		List<SurveyResponseDTO> page = surveyReadService.findPaginationDTOByVisibility(null, null, pageable)
				.map(SurveyResponseDTO::new).toList();
		
		model.addAttribute("page", page);
		
		return "layout-banner"; // Tiles 설정명 반환
	}
}
