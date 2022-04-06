package io.ezsurvey.repository.question;

import java.util.List;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;

public interface CustomQuestionRepository {
	// 문항 관리
	List<QuestionPaginationDTO> findPaginationDTOBySurveyId(Long surveyId, Long lastQuestionId, int pageSize);
	
	// 1건을 DTO로 조회
	QuestionServiceDTO getServiceDTOById(Long questionId);
	
	// 설문조사에 포함된 부모 문항 PK 목록
	List<Long> findParentIdBySurveyId(Long surveyId);
	
	// 자식 문항 PK 목록
	List<Long> findChildIdByParentId(Long parentId);
}
