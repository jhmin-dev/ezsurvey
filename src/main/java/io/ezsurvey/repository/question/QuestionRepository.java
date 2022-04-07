package io.ezsurvey.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.question.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository {
	// 더보기 가능 여부를 확인하기 위해 설문조사 내에 현재 문항보다 PK가 큰 문항이 있는지 조회
	boolean existsBySurveyIdAndIdGreaterThan(Long surveyId, Long questionId);
	
	// 부모 문항 PK로 자식 문항 목록을 일괄 삭제
	Long deleteByParentId(Long questionId);
}
