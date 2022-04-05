package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.ezsurvey.dto.survey.SurveyAuthDTO;
import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.user.User;

public interface CustomSurveyRepository {
	// 둘러보기
	Page<Survey> findByVisibility(SearchField field, String word, Pageable pageable);
	
	// 내 설문조사
	Page<Survey> findByUser(User u, SearchField field, String word, Pageable pageable);
	
	// 1건을 DTO로 조회
	SurveyServiceDTO getServiceDTOById(Long surveyId);
	
	// 1건의 접근 권한 관련 정보를 DTO로 조회
	SurveyAuthDTO getAuthDTOById(Long surveyId);
}
