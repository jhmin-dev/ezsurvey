package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.user.User;

public interface CustomSurveyRepository {
	// 둘러보기
	Page<Survey> findByVisibility(SearchField field, String word, Pageable pageable);
	
	// 내 설문조사
	Page<Survey> findByUser(User user, SearchField field, String word, Pageable pageable);
}
