package io.ezsurvey.repository.question;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static io.ezsurvey.entity.question.QItem.item;
import static io.ezsurvey.entity.survey.QSurvey.survey;

import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.entity.question.QQuestion;
import io.ezsurvey.entity.question.Question;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
	private final JPAQueryFactory jpaQueryFactory;
	QQuestion question = QQuestion.question; // 기본 인스턴스 변수
	QQuestion parent = new QQuestion("parent"); // 같은 자료형의 다른 인스턴스 변수
	
	@Override
	public List<Question> findBySurvey(Long surveyId, Long lastQuestionId, int pageSize) {
		return jpaQueryFactory.select(question).from(question)
				.innerJoin(question.survey, survey).fetchJoin() // survey는 Not Null이므로 innerJoin
				.leftJoin(question.parent, parent).fetchJoin() // parent는 Nullable이므로 leftJoin
				.where(survey.id.eq(surveyId), gtQuestionId(lastQuestionId))
				.orderBy(question.id.asc())
				.limit(pageSize)
				.fetch();
	}

	@Override
	public QuestionServiceDTO getServiceDTOById(Long questionId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private BooleanExpression gtQuestionId(Long questionId) {
		return questionId==null ? null : question.id.gt(questionId);
	}
}
