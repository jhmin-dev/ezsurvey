package io.ezsurvey.web.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.dto.question.QuestionWebDTO;

public class QuestionController {
	@RequestMapping("/bookmark/question")
	public String bookmark(@PageableDefault(page = 0, sort = "question", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model) {
		Page<QuestionWebDTO> page = Page.empty();
		
		// model.addAttribute("page", page.map(QuestionWebDTO::new));
		// model.addAttribute("count", page.getTotalElements());
		model.addAttribute("title", "즐겨찾기: 문항");
		model.addAttribute("type", "question");
		model.addAttribute("link", "bookmark");
		
		return "/list"; // Tiles 설정명 반환
	}
}
