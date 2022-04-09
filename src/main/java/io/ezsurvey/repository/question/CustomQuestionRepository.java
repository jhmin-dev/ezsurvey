package io.ezsurvey.repository.question;

import java.util.List;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;

public interface CustomQuestionRepository {
	// 문항 관리 목록을 DTO로 조회
	List<QuestionPaginationDTO> findPaginationDTOBySurveyId(Long surveyId, Long lastQuestionId, int pageSize);
	
	// 1건을 DTO로 조회
	QuestionServiceDTO getServiceDTOById(Long questionId);
	
	// 설문조사에 포함된 부모 문항 목록의 PK를 조회
	List<Long> findParentIdBySurveyId(Long surveyId);
	
	// 부모 문항 PK로 자식 문항 목록의 PK를 조회
	List<Long> findIdByParentId(Long parentId);
	
	// 문항 일괄 삭제
	Long deleteByIdIn(List<Long> questionIds);
	
	// 설문조사 내 idx의 최댓값 구하기
	Integer getMaxIdxBySurveyId(Long surveyId);
}
