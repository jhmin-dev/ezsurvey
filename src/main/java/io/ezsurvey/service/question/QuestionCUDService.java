package io.ezsurvey.service.question;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.repository.question.QuestionRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class QuestionCUDService {
	private final QuestionRepository questionRepository;
	private final SurveyRepository surveyRepository;
	
	// 문항 추가
	public Long insert(QuestionServiceDTO serviceDTO, Long survey) {
		serviceDTO.setSurvey(surveyRepository.getById(survey));
		
		return questionRepository.save(serviceDTO.toEntity()).getId();
	}
	
	// 하위 문항 추가
	public Long insert(QuestionServiceDTO serviceDTO, Long survey, Long parent) {
		serviceDTO.setSurvey(surveyRepository.getById(survey));
		serviceDTO.setParent(questionRepository.getById(parent));
		
		return questionRepository.save(serviceDTO.toEntity()).getId();
	}
}
