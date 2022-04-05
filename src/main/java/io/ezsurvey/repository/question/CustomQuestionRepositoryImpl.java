package io.ezsurvey.repository.question;

import java.util.List;

import com.querydsl.core.types.Projections;
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
				.leftJoin(question.parent, parent).fetchJoin() // parent는 Nullable이므로 leftJoin
				.where(question.survey.id.eq(surveyId), gtQuestionId(lastQuestionId))
				.orderBy(question.id.asc())
				.limit(pageSize)
				.fetch();
	}

	@Override
	public QuestionServiceDTO getServiceDTOById(Long questionId) {
		return jpaQueryFactory.select(Projections.fields(QuestionServiceDTO.class
				, question.id.as("questionId") // 설문조사 정보 및 부모 문항 정보 생략
				, question.category, question.startFromOne
				, question.branched, question.randomized
				, question.idx, question.subidx // 문항 수정시 불필요, 미리보기시 필요
				, question.varlabel, question.content, question.article, question.picture
				, question.items, question.subquestions)) // 문항 수정이나 미리보기시 즐겨찾기 수 불필요
				.from(question)
				.where(question.id.eq(questionId))
				.fetchOne();
	}
	
	private BooleanExpression gtQuestionId(Long questionId) {
		return questionId==null ? null : question.id.gt(questionId);
	}
}
