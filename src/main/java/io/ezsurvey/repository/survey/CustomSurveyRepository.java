package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.ezsurvey.dto.survey.SurveyAuthDTO;
import io.ezsurvey.dto.survey.SurveyIndexDTO;
import io.ezsurvey.dto.survey.SurveyPaginationDTO;
import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.user.User;

public interface CustomSurveyRepository {
	// 둘러보기
	Page<SurveyPaginationDTO> findByVisibility(SearchField field, String word, Pageable pageable);
	
	// 내 설문조사
	Page<SurveyPaginationDTO> findByUser(User u, SearchField field, String word, Pageable pageable);
	
	// 설문조사 상세
	SurveyServiceDTO getServiceDTOById(Long surveyId);
	
	// 문항 관리
	SurveyIndexDTO getIndexDTOById(Long surveyId);
	
	// 접근 권한 관련 정보를 DTO로 조회
	SurveyAuthDTO getAuthDTOById(Long surveyId);
}
