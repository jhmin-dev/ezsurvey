package io.ezsurvey.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.question.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository {
	public boolean existsBySurveyIdAndIdGreaterThan(Long surveyId, Long questionId);
}
