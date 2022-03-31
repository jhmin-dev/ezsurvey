package io.ezsurvey.service.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.ItemServiceDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.entity.question.Item;
import io.ezsurvey.entity.question.Question;
import io.ezsurvey.repository.question.QuestionRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class QuestionCUDService {
	private final ItemCUDService itemService;
	private final QuestionRepository questionRepository;
	private final SurveyRepository surveyRepository;
	
	// 문항 추가
	public Map<String, Object> insert(QuestionServiceDTO serviceDTO, List<ItemServiceDTO> itemServiceDTOs, Long survey) {
		serviceDTO.setSurvey(surveyRepository.getById(survey));
		
		Question question = questionRepository.save(serviceDTO.toEntity());
		
		List<Item> items = itemService.insertList(itemServiceDTOs.stream()
				.map(item -> item.updateQuestion(question)).collect(Collectors.toList()));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question", question.getId());
		map.put("items", items.size());
		
		return map;
	}
	
	// 하위 문항 추가
	public Long insert(QuestionServiceDTO serviceDTO, Long survey, Long parent) {
		serviceDTO.setSurvey(surveyRepository.getById(survey));
		serviceDTO.setParent(questionRepository.getById(parent));
		
		return questionRepository.save(serviceDTO.toEntity()).getId();
	}
}
