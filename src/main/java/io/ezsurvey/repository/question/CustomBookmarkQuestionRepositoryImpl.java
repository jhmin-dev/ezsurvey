package io.ezsurvey.repository.question;

import static io.ezsurvey.entity.question.QBookmarkQuestion.bookmarkQuestion;
import static io.ezsurvey.entity.question.QQuestion.question;
import static io.ezsurvey.entity.survey.QSurvey.survey;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.question.Category;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.QuerydslUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class CustomBookmarkQuestionRepositoryImpl implements CustomBookmarkQuestionRepository {
	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public Page<QuestionPaginationDTO> findPaginationDTOByVisibilityAndUser(User u
			, Category category, SearchField field, String word, Pageable pageable) {
		JPAQuery<QuestionPaginationDTO> content = jpaQueryFactory
				.select(Projections.fields(QuestionPaginationDTO.class
						, bookmarkQuestion.id.as("bookmarkId"), survey.id.as("surveyId")
						, question.id.as("questionId"), question.category
						, question.branched, question.randomized
						, question.idx, question.subidx
						, question.varlabel
						, question.bookmarks, question.items, question.subquestions))
				.from(bookmarkQuestion)
				.innerJoin(bookmarkQuestion.question, question) // where절에서 question과 survey의 필드로 검색하기 때문에 innerJoin 필요
				.innerJoin(bookmarkQuestion.question.survey, survey)
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, bookmarkQuestion.user.eq(u)
						, QuestionSearchCondition.eqCategory(category)
						, QuestionSearchCondition.contains(field, word));
		
		// 페이징과 정렬 처리
		QuerydslUtil.applySort(content, bookmarkQuestion, pageable);
		
		JPAQuery<Long> count = jpaQueryFactory
				.select(bookmarkQuestion.count())
				.from(bookmarkQuestion)
				.innerJoin(bookmarkQuestion.question, question) // where절에서 question과 survey의 필드로 검색하기 때문에 innerJoin 필요
				.innerJoin(bookmarkQuestion.question.survey, survey)
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, bookmarkQuestion.user.eq(u)
						, QuestionSearchCondition.eqCategory(category)
						, QuestionSearchCondition.contains(field, word));
		
		// 총 레코드 수가 페이지 크기보다 작거나, 마지막 페이지인 경우 마지막 인자의 함수(쿼리)를 실행하지 않음
		return PageableExecutionUtils.getPage(content.fetch(), pageable
				, () -> count.fetchOne());
	}
	
	@Override
	public Long deleteByIdIn(List<Long> bookmarkIds) {
		return jpaQueryFactory.delete(bookmarkQuestion)
				.where(bookmarkQuestion.id.in(bookmarkIds))
				.execute();
	}
	
	@Override
	public Long deleteByUserId(Long userId) {
		return jpaQueryFactory.delete(bookmarkQuestion)
				.where(bookmarkQuestion.user.id.eq(userId))
				.execute();
	}
}
