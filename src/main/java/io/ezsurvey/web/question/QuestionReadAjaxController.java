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
	
	@PostMapping("/ajax/edit/project/{surveyId}/index")
	public Map<String, Object> index(@PathVariable(name = "surveyId") Long surveyId, Long lastQuestionId) {
		log.info("survey: {}, last: {}", surveyId, lastQuestionId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<QuestionPaginationDTO> list = questionReadService.findBySurvey(surveyId, lastQuestionId, 10);
		map.put("list", list);
		
		QuestionPaginationDTO newLastQuestion = list.stream().max(Comparator.comparing(QuestionPaginationDTO::getQuestionId)).orElse(null);
		if(newLastQuestion!=null) {
			Long newLastQuestionId = newLastQuestion.getQuestionId();
			map.put("last", newLastQuestionId);
			map.put("hasMore", questionReadService.existsBySuveyAndId(surveyId, newLastQuestionId));
		}
		
		map.put("category", enumMapper.get("Category"));
		map.put("result", "success");
		
		return map;
	}
}
