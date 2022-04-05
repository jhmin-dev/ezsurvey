package io.ezsurvey.web.question;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.repository.EnumMapper;
import io.ezsurvey.service.question.QuestionReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@RestController
public class QuestionReadAjaxController {
	private final QuestionReadService questionReadService;
	private final EnumMapper enumMapper; // AppConfig에 등록
	
	@PostMapping("/ajax/edit/project/{survey}/index")
	public Map<String, Object> index(@PathVariable(name = "survey") Long survey, Long lastQuestion) {
		log.info("survey: {}, last: {}", survey, lastQuestion);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<QuestionPaginationDTO> list = questionReadService.findBySurvey(survey, lastQuestion, 10);
		map.put("list", list);
		
		QuestionPaginationDTO newLast = list.stream().max(Comparator.comparing(QuestionPaginationDTO::getQuestionId)).orElse(null);
		if(newLast!=null) {
			Long newLastQuestion = newLast.getQuestionId();
			map.put("last", newLastQuestion);
			map.put("hasMore", questionReadService.existsBySuveyAndId(survey, newLastQuestion));
		}
		
		map.put("category", enumMapper.get("Category"));
		map.put("result", "success");
		
		return map;
	}
}
