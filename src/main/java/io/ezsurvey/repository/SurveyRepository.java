package io.ezsurvey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.survey.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long>{

}
