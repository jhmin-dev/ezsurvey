package io.ezsurvey.repository.survey;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.survey.BookmarkSurvey;

public interface BookmarkSurveyRepository extends JpaRepository<BookmarkSurvey, Long>, CustomBookmarkSurveyRepository {
	boolean existsBySurveyIdAndUserId(Long surveyId, Long userId);
}
