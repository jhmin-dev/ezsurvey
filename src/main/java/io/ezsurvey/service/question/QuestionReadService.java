package io.ezsurvey.service.question;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.repository.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class QuestionReadService {
	private final QuestionRepository questionRepository;
	
	public List<QuestionPaginationDTO> findPaginationDTOBySurveyId(Long surveyId, Long questionId, int pageSize) {
		return questionRepository.findPaginationDTOBySurveyId(surveyId, questionId, pageSize);
	}
	
	public boolean existsBySuveyAndId(Long surveyId, Long questionId) {
		return questionRepository.existsBySurveyIdAndIdGreaterThan(surveyId, questionId);
	}
	
	public QuestionServiceDTO getServiceDTOById(Long questionId) {
		return questionRepository.getServiceDTOById(questionId);
	}
}
