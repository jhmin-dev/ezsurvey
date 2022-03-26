package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.user.User;

public interface CustomBookmarkSurveyRepository {
	// 즐겨찾기: 설문조사
	Page<Survey> findByVisibilityAndUser(User user, SearchField field, String word, Pageable pageable);
}
