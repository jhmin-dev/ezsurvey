package io.ezsurvey.web.question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionCUDController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionCUDController.class);
	
	@GetMapping("/make/question")
	public String make(Model model) {
		Long survey = (Long)model.getAttribute("survey");
		if(survey==null) return "redirect:/";
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "문항 추가");
		model.addAttribute("link", "make");
		
		return "/question/make_edit"; // Tiles 설정명 반환
	}
	
	@GetMapping("/make/test/question")
	public String maketest(Long survey, Model model) {
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("survey", survey);
		model.addAttribute("title", "문항 추가");
		model.addAttribute("link", "make");
		
		return "/question/make_edit"; // Tiles 설정명 반환
	}
}
