package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;

public interface SurveyRepository extends JpaRepository<Survey, Long>{
	Page<Survey> findByVisibility(Visibility visibility, Pageable pageable);
	Page<Survey> findByVisibilityAndTitleContaining(Visibility visibility, String title, Pageable pageable);
	Page<Survey> findByVisibilityAndContentContaining(Visibility visibility, String content, Pageable pageable);
	Page<Survey> findByVisibilityAndTitleContainingOrVisibilityAndContentContaining(Visibility visibility1, String title, Visibility visibility2, String content, Pageable pageable);
}
