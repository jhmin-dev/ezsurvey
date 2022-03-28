package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static io.ezsurvey.entity.survey.QSurvey.survey;
import static io.ezsurvey.entity.user.QUser.user;

import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.QuerydslUtil;

public class CustomSurveyRepositoryImpl implements CustomSurveyRepository {
	private final JPAQueryFactory jpaQueryFactory;

	
	public CustomSurveyRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Page<Survey> findByVisibility(SearchField field, String word, Pageable pageable) {
		JPAQuery<Survey> content = jpaQueryFactory
				.select(survey).from(survey)
				.innerJoin(survey.user, user).fetchJoin() // N+1 방지
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, SurveySearchCondition.contains(field, word));
		
		// 페이징과 정렬 처리
		QuerydslUtil.applySort(content, survey, pageable);
		
		JPAQuery<Long> count = jpaQueryFactory.select(survey.count()).from(survey)
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, SurveySearchCondition.contains(field, word));
		
		// 총 레코드 수가 페이지 크기보다 작거나, 마지막 페이지인 경우 마지막 인자의 함수(쿼리)를 실행하지 않음
		return PageableExecutionUtils.getPage(content.fetch(), pageable
				, () -> count.fetch().get(0));
	}

	@Override
	public Page<Survey> findByUser(User member, SearchField field, String word, Pageable pageable) {
		JPAQuery<Survey> content = jpaQueryFactory
				.select(survey).from(survey)
				.innerJoin(survey.user, user).fetchJoin() // N+1 방지
				.where(survey.visibility.ne(Visibility.DELETED)
						, survey.user.eq(member)
						, SurveySearchCondition.contains(field, word));
		
		// 페이징과 정렬 처리
		QuerydslUtil.applySort(content, survey, pageable);
		
		JPAQuery<Long> count = jpaQueryFactory.select(survey.count()).from(survey)
				.where(survey.visibility.ne(Visibility.DELETED)
						, survey.user.eq(member)
						, SurveySearchCondition.contains(field, word));
		
		// 총 레코드 수가 페이지 크기보다 작거나, 마지막 페이지인 경우 마지막 인자의 함수(쿼리)를 실행하지 않음
		return PageableExecutionUtils.getPage(content.fetch(), pageable
				, () -> count.fetch().get(0));
	}
}
