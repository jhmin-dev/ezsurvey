package io.ezsurvey.repository.question;

import java.util.List;

import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.entity.question.Question;

public interface CustomQuestionRepository {
	// 문항 관리
	public List<Question> findBySurvey(Long surveyId, Long lastQuestionId, int pageSize);
	
	// 1건을 DTO로 조회
	public QuestionServiceDTO getServiceDTOById(Long questionId);
}
