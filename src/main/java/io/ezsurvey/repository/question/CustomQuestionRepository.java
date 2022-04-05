package io.ezsurvey.repository.question;

import java.util.List;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;

public interface CustomQuestionRepository {
	// 문항 관리
	List<QuestionPaginationDTO> findPaginationDTOBySurveyId(Long surveyId, Long lastQuestionId, int pageSize);
	
	// 1건을 DTO로 조회
	QuestionServiceDTO getServiceDTOById(Long questionId);
}
