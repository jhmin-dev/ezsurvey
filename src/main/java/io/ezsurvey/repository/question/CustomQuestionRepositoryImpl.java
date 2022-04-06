package io.ezsurvey.repository.question;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static io.ezsurvey.entity.survey.QSurvey.survey;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.entity.question.QQuestion;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
	private final JPAQueryFactory jpaQueryFactory;
	QQuestion question = QQuestion.question; // 기본 인스턴스 변수
	QQuestion parent = new QQuestion("parent"); // 같은 자료형의 다른 인스턴스 변수
	
	@Override
	public List<QuestionPaginationDTO> findPaginationDTOBySurveyId(Long surveyId, Long lastQuestionId, int pageSize) {
		return jpaQueryFactory.select(Projections.fields(QuestionPaginationDTO.class
				, question.id.as("questionId"), question.category
				, parent.id.as("parentId"), question.branched, question.randomized
				, question.idx, question.subidx
				, question.varlabel, question.content
				, question.bookmarks, question.items, question.subquestions))
				.from(question)
				.leftJoin(question.parent, parent) // parent는 Nullable이므로 leftJoin
				.innerJoin(question.survey, survey)
				.where(survey.id.eq(surveyId), gtQuestionId(lastQuestionId))
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
	
	@Override
	public List<Long> findParentIdBySurveyId(Long surveyId) {
		return jpaQueryFactory.select(question.id)
				.from(question)
				.innerJoin(question.survey, survey)
				.where(survey.id.eq(surveyId), question.parent.isNull()) // 특정 설문조사에서 자식 문항이 아닌 문항들만 조회
				.fetch();
	}
	
	@Override
	public List<Long> findChildIdByParentId(Long parentId) {
		return jpaQueryFactory.select(question.id)
				.from(question)
				.where(question.parent.id.eq(parentId)) // 특정 문항의 자식 문항들만 조회
				.fetch();
	}
	
	private BooleanExpression gtQuestionId(Long questionId) {
		return questionId==null ? null : question.id.gt(questionId);
	}
}
